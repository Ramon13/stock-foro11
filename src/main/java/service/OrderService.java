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

	public List<Order> listByStatus(OrderStatus status) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).listByStatus(status);
		}catch(DAOException ex) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public List<Order> listByItem(Item item, PaginationFilter filter) throws ServiceException{
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.listByItem(item, filter);
		
		return listOrderFromOrderItems(orderItems);
	}
	
	public void cancelOrder(Order order) throws ServiceException{
		order.setStatus(OrderStatus.CANCELED_BY_ADMIN.getValue());
		update(order);
	}
	
	public boolean isValidCancelationOrder(Order order, LoggedUser loggedUser) throws ServiceException{
		return isValidForCancellation(order) 
				&& getServiceFactory()
					.getService(UserService.class).isAnyAdmin(loggedUser);
	}
	
	public boolean isValidForRelease(Order order, LoggedUser loggedUser) throws ServiceException, ValidationException{
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		itemSvc.setItemsCurrentAmount(order.getOrderItems());
		for (OrderItem orderItem : order.getOrderItems()) {
			if (!orderItemSvc.isValidForRelease(orderItem))
				throw new ValidationException(ValidationMessageUtil.EMPTY_STOCK);
		}
		
		return order.getStatus() == OrderStatus.PENDING.getValue() 
				&& getServiceFactory()
					.getService(UserService.class).isSuperAdminLoggedUser(loggedUser);
	}
	
	public boolean isValidForFinish(Order order, LoggedUser loggedUser) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue() 
				&& getServiceFactory()
					.getService(UserService.class).isAnyAdmin(loggedUser);
	}
	
	public boolean isValidForPending(Order order, LoggedUser loggedUser) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue() 
				&& getServiceFactory()
					.getService(UserService.class).isAnyAdmin(loggedUser);
	}
	
	public boolean isValidForCancellation(Order order) {
		return order.getStatus() == OrderStatus.PENDING.getValue() 
				|| order.getStatus() == OrderStatus.RELEASED.getValue();
	}
	
	public void finishOrder(Order order) throws ServiceException {
		order.setStatus(OrderStatus.FINALIZED.getValue());
		update(order);
	}
	
	public void releaseOrder(Order order, LoggedUser loggedUser) throws ServiceException {
		order.setStatus(OrderStatus.RELEASED.getValue());
		
		User persistentUser = getServiceFactory()
				.getService(UserService.class)
				.findById(loggedUser.getUser().getId());
		order.setReleaseAdministrator(persistentUser);
		update(order);
	}
	
	public void pendingOrder(Order order) throws ServiceException {
		order.setStatus(OrderStatus.PENDING.getValue());
		update(order);
	}
	
	public void editFinalDate(Order order, LocalDate newDate) throws ServiceException{
		order.setFinalDate(newDate);
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
	
	public List<Order> searchOrdersByItem(Item item, String word) throws ServiceException{
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.searchOnOrderItem(item, word);
		
		return listOrderFromOrderItems(orderItems);
	}
	
	public boolean isValidOrder(Order order) {
		return order != null && order.getStatus() != null;
	}
}
