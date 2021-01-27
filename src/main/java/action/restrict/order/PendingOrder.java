package action.restrict.order;

import action.ApplicationAction;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Order;
import service.OrderService;

public class PendingOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		changeOrderStatus();
	}
	
	public void changeOrderStatus() throws ServiceException, ValidationException {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		
		Order order = orderSvc.validateAndFindById(getRequest().getParameter("order"));
		
		if (orderSvc.isValidForPending(order)) {
			orderSvc.pendingOrder(order);
		}
	}
}
