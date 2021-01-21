package action.restrict.order;

import action.ApplicationAction;
import entity.Order;
import service.OrderService;

public class PendingOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		Order order = orderSvc.validateAndFindById(getRequest().getParameter("order"));
		
		if (orderSvc.isValidForPending(order)) {
			orderSvc.pendingOrder(order);
		}
	}
}
