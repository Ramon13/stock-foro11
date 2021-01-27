package action.restrict.order;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import br.com.javamon.exception.ServiceException;
import domain.OrderStatus;
import entity.Item;
import entity.Order;
import entity.PaginationFilter.orders;
import service.ItemService;
import service.OrderService;

public class ListOrder extends ApplicationAction{

	private OrderService orderSvc;
	
	@Override
	public void processAction() throws Exception {
		
		 orderSvc = getServiceFactory().getService(OrderService.class);
		 String strStatus = getRequest().getParameter("status");
		 
		 if (!StringUtils.isAllBlank(getRequest().getParameter("countUnfinished"))) {
			 sendUnfinishedOrdersNum();
			 
		 }else if (!StringUtils.isAllBlank(strStatus)) {
			 sendOrders(OrderStatus.getByValue(strStatus.charAt(0)));
		 
		 }else {
			 sendOrdersByItem();
		 }
	}
	
	private void sendOrders(OrderStatus status) throws Exception{
		 if (StringUtils.isAllBlank(getRequest().getParameter("sortBy"))) {
			 paginationFilter.setSortProperty("id");
			 paginationFilter.setOrder(orders.DESC);
		 }	
		List<Order> orders;
		
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			orders = orderSvc.listByStatus(status, paginationFilter);

		}else {
			orders = orderSvc.searchOnOrders(status, paginationFilter);
		}
		
		getRequest().setAttribute("status", status.getValue().toString());
		getRequest().setAttribute("orders", orders);
		foward("/restrict/orders.jsp");
	}

	private void sendOrdersByItem() throws Exception{
		if (StringUtils.isAllBlank(getRequest().getParameter("sortBy"))) {
			 paginationFilter.setSortProperty("order.id");
			 paginationFilter.setOrder(orders.DESC);
		 }	
		Item item = getServiceFactory().getService(ItemService.class)
				.validateAndFindById(getRequest().getParameter("item"));
		
		List<Order> orders;
		if (StringUtils.isAllBlank(paginationFilter.getSearchWord())) 
			orders = orderSvc.listByItem(item, paginationFilter);
		
		else
			orders = orderSvc.searchOrdersByItem(item, paginationFilter);
			
		getRequest().setAttribute("orders", orders);
		getRequest().setAttribute("fieldName", "order.");
		getRequest().setAttribute("item", item);
		foward("/restrict/orders.jsp");
		
	}
	
	private void sendUnfinishedOrdersNum() throws ServiceException, IOException {
		responseToClient(200, getServiceFactory().getService(OrderService.class).countUnfinishedOrders().toString());
	}
}
