package action.common.shopping_cart;

import action.ActionUtil;
import action.ApplicationAction;
import entity.User;
import service.ShoppingCartService;

public class CountCart extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		User user = ActionUtil.getSessionLoggedUser(getRequest()).getUser();
		
		Integer cartCount = getServiceFactory()
				.getService(ShoppingCartService.class)
				.countItemsOnCart(user);
		
		responseToClient(200, cartCount.toString());
	}

	
}
