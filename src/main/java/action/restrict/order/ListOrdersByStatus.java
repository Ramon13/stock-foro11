package action.restrict.order;

import action.ActionUtil;
import br.com.javamon.action.Action;
import domain.OrderStatus;
import domain.util.ExceptionMessageUtil;
import service.OrderService;
import service.UserService;

public class ListOrdersByStatus extends Action{

	@Override
	public void process() throws Exception {
		OrderStatus status = getOrderStatus();
		getRequest().setAttribute("orders", getServiceFactory()
				.getService(OrderService.class)
				.listByStatus(status));
		
		getRequest().setAttribute("status", status.getValue().toString());
		
		UserService loginSvc = getServiceFactory().getService(UserService.class);
		ActionUtil.setSuperAdminUser(getRequest(), loginSvc);
		
		foward("/restrict/user-orders.jsp");
	}

	private OrderStatus getOrderStatus() {
		for (OrderStatus status : OrderStatus.values()) 
			if (getRequest().getParameter("status").equals(status.getValue().toString()))
				return status;
		
		throw new IllegalStateException(ExceptionMessageUtil.INVALID_URL);
	}

}
