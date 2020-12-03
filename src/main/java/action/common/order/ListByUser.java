package action.common.order;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import domain.OrderStatus;
import entity.Order;
import entity.PaginationFilter.orders;
import service.OrderService;

public class ListByUser extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		setDefaultPaginationOrder();
		List<Order> orders = getServiceFactory()
				.getService(OrderService.class)
				.listByUser(ActionUtil.getSessionLoggedUser(getRequest()).getUser(), paginationFilter);
		
		listOrderStatus();
		
		getRequest().setAttribute("orders", orders);
		foward("/common/user-orders.jsp");
	}
	
	public void setDefaultPaginationOrder() {
		if (StringUtils.isAllBlank(getRequest().getParameter("sortBy"))) {
			paginationFilter.setSortProperty("id");
			paginationFilter.setOrder(orders.DESC);
		}
	}
	
	public void listOrderStatus() {
		for (OrderStatus status : OrderStatus.values()) {
			getRequest().setAttribute(status.name(), status.getValue());
		}
	}

}
