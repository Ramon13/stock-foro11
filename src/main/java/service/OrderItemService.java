package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.OrderItemDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Item;
import entity.Order;
import entity.OrderItem;
import entity.PaginationFilter;

public class OrderItemService extends ApplicationService<OrderItem, OrderItemDAO>{

	public OrderItemService() {
		super(OrderItemDAO.class);
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
	
	public boolean isValidForRelease(OrderItem orderItem) {
		return orderItem.getAmount() <= orderItem.getItem().getAmount().intValue();	
	}
	
	public boolean isValidForChangeAmount(OrderItem orderItem, Integer newAmount) throws ServiceException, ValidationException {
		getServiceFactory()
		.getService(ItemService.class)
		.setItemCurrentAmount(orderItem.getItem());
		
		if (newAmount <= orderItem.getItem().getAmount().intValue())
			return true;
		
		throw new ValidationException(ValidationMessageUtil.EMPTY_STOCK);  
	}
	
	public List<OrderItem> searchOnOrderItem(Item item, String word) throws ServiceException{
		try {
			return getDAO(OrderItemDAO.class).searchByItem(item, word);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}
}
