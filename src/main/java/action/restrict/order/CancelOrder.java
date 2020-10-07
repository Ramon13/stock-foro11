package action.restrict.order;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.exception.ValidationException;
import domain.LoggedUser;
import domain.util.ValidationMessageUtil;
import entity.Order;
import service.OrderService;

public class CancelOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		String orderIdParam = getRequest().getParameter("order");
		
		Order order = orderSvc.validateAndFindById(orderIdParam);
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		
		if (orderSvc.isValidCancelationOrder(order, loggedUser)) {
			orderSvc.cancelOrder(order);
		
		}else {
			throw new ValidationException(ValidationMessageUtil.ROLE_PERMISSION_DENIED);
		}
			
	}

}
