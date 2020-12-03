package action.common.shopping_cart;

import action.ApplicationAction;
import entity.OrderItem;
import service.OrderItemService;

public class DeleteCartItem extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		OrderItem orderItem = orderItemSvc.validateAndFindById(getRequest().getParameter("orderItem"));
		orderItemSvc.delete(orderItem);
	}

}
