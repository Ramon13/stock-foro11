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
import domain.PermissionRoles;
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

	public List<Order> listByStatus(OrderStatus status, PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderDAO.class).listByStatus(status, filter);
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
	
	public void cancelOrder(Order order) throws ServiceException{
		order.setStatus(OrderStatus.CANCELED_BY_ADMIN.getValue());
		update(order);
	}
	
	public boolean isValidCancelationOrder(Order order, LoggedUser loggedUser) throws ServiceException{
		return isValidForCancellation(order) 
				&& PermissionRoles.isAnyAdmin(loggedUser.getUser().getPermission());
	}
	
	public boolean isValidForRelease(Order order, LoggedUser loggedUser) throws ServiceException, ValidationException{
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		for (OrderItem orderItem : order.getOrderItems()) {
			itemSvc.getItemCurrentAmount(orderItem.getItem());
			if (!orderItemSvc.isValidForRelease(orderItem))
				throw new ValidationException(ValidationMessageUtil.EMPTY_STOCK);
		}
		
		return order.getStatus() == OrderStatus.PENDING.getValue() 
				&& PermissionRoles.isSuperAdmin(loggedUser.getUser().getPermission());
	}
	
	public boolean isValidForFinish(Order order, LoggedUser loggedUser) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue() 
				&& PermissionRoles.isAnyAdmin(loggedUser.getUser().getPermission());
	}
	
	public boolean isValidForPending(Order order, LoggedUser loggedUser) throws ServiceException{
		return order.getStatus() == OrderStatus.RELEASED.getValue() 
				&& PermissionRoles.isAnyAdmin(loggedUser.getUser().getPermission());
	}
	
	public boolean isValidForCancellation(Order order) {
		return order.getStatus() == OrderStatus.PENDING.getValue() 
				|| order.getStatus() == OrderStatus.RELEASED.getValue();
	}
	
	public void finishOrder(Order order) throws ServiceException {
		order.setStatus(OrderStatus.FINALIZED.getValue());
		order.setFinalDate(LocalDate.now());
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
