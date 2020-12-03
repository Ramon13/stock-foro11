package action.common.shopping_cart;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Item;
import entity.OrderItem;
import entity.ShoppingCart;
import service.ItemService;
import service.OrderItemService;
import service.ShoppingCartService;

public class AddOrderItem extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		try {
			getServiceFactory().getService(OrderItemService.class).save(createOrderItem());
		} catch (ValidationException e) {
			responseToClient(230, e.getMessage());
		}
	}

	public OrderItem createOrderItem() throws ValidationException, ServiceException{
		Item item = getServiceFactory().getService(ItemService.class)
		.validateAndFindById(getRequest().getParameter("item"));
		
		ShoppingCart cart = ActionUtil.getSessionLoggedUser(getRequest()).getUser().getCart();
		ShoppingCartService shoppingCartSvc = getServiceFactory().getService(ShoppingCartService.class);
		
		if (!shoppingCartSvc.isItemOnCart(cart, item)) {
			OrderItem orderItem = new OrderItem();
			orderItem.setAmount(1);
			orderItem.setItem(item);			
			orderItem.setCart(cart);
			return orderItem;
		
		}else {
			throw new ValidationException("This item is already in the cart");
		}
	}
}
