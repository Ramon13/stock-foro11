package action.common.order_item;

import action.ActionUtil;
import action.ApplicationAction;
import domain.OrderStatus;
import entity.Order;
import service.OrderService;

public class ListByOrder extends ApplicationAction{

	@Override
	public void processAction() throws Exception {		
		Order order = getServiceFactory()
				.getService(OrderService.class)
				.validateAndFindById(getRequest().getParameter("order"));
		
		if (ActionUtil.getSessionLoggedUser(getRequest()).getUser().getId() == order.getCustomer().getId()) {
			getRequest().setAttribute(OrderStatus.PENDING.name(), OrderStatus.PENDING.getValue());
			getRequest().setAttribute("order", order);
			foward("/common/order.jsp");
		
		}else {
			redirect("/common/orders.action");
		}
		
	}

}
