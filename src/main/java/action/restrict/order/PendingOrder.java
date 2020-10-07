package action.restrict.order;

import action.ActionUtil;
import action.ApplicationAction;
import domain.LoggedUser;
import entity.Order;
import service.OrderService;

public class PendingOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		String orderIdParam = getRequest().getParameter("order");
		
		Order order = orderSvc.validateAndFindById(orderIdParam);
		
		if (orderSvc.isValidForPending(order, loggedUser)) {
			orderSvc.pendingOrder(order);
		}
	}

}
