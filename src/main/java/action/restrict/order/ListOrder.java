package action.restrict.order;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import domain.OrderStatus;
import entity.Item;
import entity.Order;
import service.ItemService;
import service.OrderService;

public class ListOrder extends ApplicationAction{

	private OrderService orderSvc;
	
	@Override
	public void processAction() throws Exception {
		 orderSvc = getServiceFactory().getService(OrderService.class);
		 String strStatus = getRequest().getParameter("status");
		 
		 if (!StringUtils.isAllBlank(strStatus)) {
			 sendOrders(OrderStatus.getByValue(strStatus.charAt(0)));
		 
		 }else {
			 sendOrdersByItem();
		 }
	}
	
	private void sendOrders(OrderStatus status) throws Exception{
		List<Order> orders = getServiceFactory()
				.getService(OrderService.class)
				.listByStatus(status);
		
		getRequest().setAttribute("status", status.getValue().toString());
		getRequest().setAttribute("orders", orders);
		foward("/restrict/user-orders.jsp");
	}

	private void sendOrdersByItem() throws Exception{
		Item item = getServiceFactory().getService(ItemService.class)
				.validateAndFindById(getRequest().getParameter("item"));
		
		List<Order> orders;
		if (StringUtils.isAllBlank(paginationFilter.getSearchWord())) 
			orders = orderSvc.listByItem(item, paginationFilter);
		
		else
			orders = orderSvc.searchOrdersByItem(item, paginationFilter.getSearchWord());
			
		getRequest().setAttribute("orders", orders);
		getRequest().setAttribute("item", item);
		foward("/restrict/item-info-orders.jsp");
		
	}
}
