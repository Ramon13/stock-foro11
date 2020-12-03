package action.common.shopping_cart;

import java.util.List;

import action.ActionUtil;
import action.ApplicationAction;
import domain.LoggedUser;
import entity.OrderItem;
import service.OrderItemService;

public class ListUserCart extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.listByShoppingCart(loggedUser.getUser().getCart());
		
		getRequest().setAttribute("orderItems", orderItems);
		foward("/common/cart.jsp");
	}

}
