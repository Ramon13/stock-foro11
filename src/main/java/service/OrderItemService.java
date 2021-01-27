package service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.OrderItemDAO;
import domain.OrderStatus;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;
import entity.ShoppingCart;

public class OrderItemService extends ApplicationService<OrderItem, OrderItemDAO>{

	public OrderItemService() {
		super(OrderItemDAO.class);
	}
	
	public List<OrderItem> list() throws ServiceException{
		try {
			return getDAO().listAll();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public List<OrderItem> listByItem(Item item) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderItemDAO.class).listOrdersByItem(item);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<OrderItem> listByItem(Item item, PaginationFilter filter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderItemDAO.class)
					.list(item, filter);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<OrderItem> listByItem(Item item, PaginationFilter filter, OrderStatus status) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderItemDAO.class)
					.list(item, filter, status);
		}catch(DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<OrderItem> listOrderItemsByOrder(Order order) throws ServiceException{
		try {
			return getDaoFactory().getDAO(OrderItemDAO.class).listOrderItemsByOrder(order);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST, e);
		}
	}
	
	public void editAmount(OrderItem orderItem, Integer newAmount) throws ServiceException{
		orderItem.setAmount(newAmount);
		update(orderItem);
	}
	
	public boolean isValidForRelease(OrderItem orderItem, BigDecimal itemAmount) {
		return orderItem.getAmount() <= itemAmount.longValue();	
	}
	
	public boolean isValidForChangeAmount(OrderItem orderItem, Integer newAmount) throws ServiceException, ValidationException {
		BigDecimal currAmount = getServiceFactory()
				.getService(ItemService.class)
				.getItemCurrentAmount(orderItem.getItem());
		
		if (newAmount <= currAmount.longValue())
			return true;
		
		throw new ValidationException(ValidationMessageUtil.EMPTY_STOCK);  
	}
	
	public List<OrderItem> searchOnOrderItem(Item item, PaginationFilter filter) throws ServiceException{
		try {
			return getDAO(OrderItemDAO.class).searchByItem(item, filter);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public List<OrderItem> listByShoppingCart(ShoppingCart cart) throws ServiceException{
		try {
			return getDAO().listByShoppingCart(cart);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
	
	public void updateItemAmount(Collection<OrderItem> orderItems) throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		for (OrderItem oi : orderItems) {
			itemSvc.updateItemCurrentAmount(oi.getItem());
		}
	}
}
