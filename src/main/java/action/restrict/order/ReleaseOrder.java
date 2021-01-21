package action.restrict.order;

import action.ActionUtil;
import action.ApplicationAction;
import entity.Order;
import service.OrderService;

public class ReleaseOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		Order order = orderSvc.validateAndFindById(getRequest().getParameter("order"));
		
		if (orderSvc.isValidForRelease(order)) {
			orderSvc.releaseOrder(order, ActionUtil.getSessionLoggedUser(getRequest()));
		}
		
		redirect("restrict/orders.action?status=P");
	}

}
