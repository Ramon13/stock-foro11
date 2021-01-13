package action.restrict.item;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import entity.Entry;
import entity.Item;
import entity.Order;
import entity.PaginationFilter.orders;
import service.EntryService;
import service.OrderService;

public class ItemInfo extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		Item item = ActionUtil.getRequestItem(getRequest());
		
		if (!StringUtils.isAllBlank(getRequest().getParameter("listProp"))) {
			if (getRequest().getParameter("listProp").equals("entries")) {
				getRequest().setAttribute("entries", sendEntriesByItem(item));
			
			}else {
				getRequest().setAttribute("orders", sendOrdersByItem(item));
			}
		}
		
		getRequest().setAttribute("item", item);
		foward("/restrict/item-info.jsp");
		
	}
	
	private List<Entry> sendEntriesByItem(Item item) throws Exception {
		if (StringUtils.isAllBlank(getRequest().getParameter("sortBy"))) {
			 paginationFilter.setSortProperty("entry.id");
			 paginationFilter.setOrder(orders.DESC);
		}
		EntryService entrySvc = getServiceFactory().getService(EntryService.class);
		
		if (isSearchAction())
			return entrySvc.searchByItem(item, paginationFilter);
		
		return entrySvc.listByItem(item, paginationFilter);
	}
	
	private List<Order> sendOrdersByItem(Item item) throws Exception {
		if (StringUtils.isAllBlank(getRequest().getParameter("sortBy"))) {
			 paginationFilter.setSortProperty("order.id");
			 paginationFilter.setOrder(orders.DESC);
		}	
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		if (isSearchAction())
			return orderSvc.searchOrdersByItem(item, paginationFilter);
		
		return orderSvc.listByItem(item, paginationFilter);
	} 
}
