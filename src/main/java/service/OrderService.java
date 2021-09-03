package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.OrderDAO;
import domain.LoggedUser;
import domain.OrderStatus;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;
import entity.User;

public class OrderService extends ApplicationService<Order, OrderDAO>{
	
	public OrderService() {
		super(OrderDAO.class);
	}

	public Order findByUser(String orderId, User user) throws ServiceException{
		try {
			return getDAO().findByUser(Long.parseLong(orderId), user);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ServiceException(ValidationMessageUtil.INVALID_ORDER_ID, e);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public Integer countUnfinishedOrders() throws ServiceException{
		try {
			return getDAO().listByStatus(new PaginationFilter(), OrderStatus.PENDING, OrderStatus.RELEASED).size();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public List<Order> listByStatus(OrderStatus...status) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).listByStatus(status);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Order> listByStatus(OrderStatus status, PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).listByStatus(filter, status);
		}catch(DAOException ex) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public List<Order> listByItem(Item item, PaginationFilter filter) throws ServiceException{
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.listByItem(item, filter, OrderStatus.FINALIZED);
		
		return listOrderFromOrderItems(orderItems);
	}
	
	public List<Order> listByUser(User user, PaginationFilter filter) throws ServiceException{
		try {
			return getDAO().listByUser(user, filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void cancelOrder(Order order, OrderStatus status) throws ServiceException{
		order.setFinalDate(LocalDate.now());
		order.setStatus(status.getValue());
		update(order);
	}
	
	public void cancelOrder(Order order) throws ServiceException{
		order.setFinalDate(LocalDate.now());
		order.setStatus(OrderStatus.CANCELED_BY_ADMIN.getValue());
		update(order);
	}

	public boolean isValidForRelease(Order order) throws ServiceException, ValidationException{
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		Long itemAmount;
		for (OrderItem orderItem : order.getOrderItems()) {
			itemAmount = itemSvc.getItemCurrentAmount(orderItem.getItem()).longValue();
			itemAmount += orderItem.getAmount();
			if (!orderItemSvc.isValidForRelease(orderItem, itemAmount)) {
				throw new ValidationException(ValidationMessageUtil.EMPTY_STOCK);
			}
		}
		
		return order.getStatus() == OrderStatus.PENDING.getValue();
	}
	
	public boolean isValidForFinish(Order order) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue();
	}
	
	public boolean isValidForPending(Order order) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue();
	}
	
	public boolean isValidForCancellation(Order order) {
		return order.getStatus() == OrderStatus.PENDING.getValue() 
				|| order.getStatus() == OrderStatus.RELEASED.getValue();
	}
	
	public void finishOrder(Order order) throws ServiceException {
		order.setStatus(OrderStatus.FINALIZED.getValue());
		order.setFinalDate(order.getRequestDate());
		order.setReleaseDate(order.getRequestDate());
		//order.setFinalDate(LocalDate.now());
		getServiceFactory().getService(OrderItemService.class).updateItemAmount(order.getOrderItems());
		update(order);
	}
	
	public void releaseOrder(Order order, LoggedUser loggedUser) throws ServiceException {
		order.setStatus(OrderStatus.RELEASED.getValue());
		
		User persistentUser = getServiceFactory()
				.getService(UserService.class)
				.findById(loggedUser.getUser().getId());
		order.setReleaseAdministrator(persistentUser);
		order.setReleaseDate(LocalDate.now());
		update(order);
		
		getServiceFactory().getService(OrderItemService.class).updateItemAmount(order.getOrderItems());
	}
	
	public void pendingOrder(Order order) throws ServiceException {
		order.setStatus(OrderStatus.PENDING.getValue());
		getServiceFactory().getService(OrderItemService.class).updateItemAmount(order.getOrderItems());
		update(order);
	}
	
	public void editFinalDate(Order order, LocalDate newDate) throws ServiceException{
		order.setFinalDate(newDate);
		update(order);
	}
	
	public void editPendingDate(Order order, LocalDate newDate) throws ServiceException{
		order.setRequestDate(newDate);
		update(order);
	}
	
	public boolean isValidToCheckOut(Order order) throws ServiceException{
		return order != null && (order.getStatus() == OrderStatus.FINALIZED.getValue() 
				|| order.getStatus() == OrderStatus.RELEASED.getValue());
	}
	
	private List<Order> listOrderFromOrderItems(List<OrderItem> orderItems){ 
		List<Order> orders = new ArrayList<Order>();

		for (OrderItem oi : orderItems) {
			orders.add(oi.getOrder());
		}
		
		return orders;	
	}
	
	public List<Order> searchOrdersByItem(Item item, PaginationFilter filter) throws ServiceException{
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.searchOnOrderItem(item, filter);
		
		return listOrderFromOrderItems(orderItems);
	}
	
	public boolean isValidOrder(Order order) {
		return order != null && order.getStatus() != null;
	}
	
	public List<Order> searchOnOrders(PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).search(filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<Order> searchOnOrders(OrderStatus status, PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).search(status, filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
