package action.common.order;

import action.ActionUtil;
import action.ApplicationAction;
import domain.OrderStatus;
import entity.Order;
import service.OrderService;

public class CancelOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		Order order = orderSvc.findByUser(getRequest().getParameter("order"), ActionUtil.getSessionLoggedUser(getRequest()).getUser());
		
		if (order != null) {
			orderSvc.cancelOrder(order, OrderStatus.CANCELED_BY_USER);
		}
		
		redirect("common/orders.action");
	}

}
