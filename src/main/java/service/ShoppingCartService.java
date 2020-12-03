package service;

import java.util.List;

import br.com.javamon.exception.ServiceException;
import dao.ShoppingCartDAO;
import entity.Item;
import entity.OrderItem;
import entity.ShoppingCart;
import entity.User;

public class ShoppingCartService extends ApplicationService<ShoppingCart, ShoppingCartDAO>{

	public ShoppingCartService() {
		super(ShoppingCartDAO.class);
	}

	public boolean isItemOnCart(ShoppingCart cart, Item item) throws ServiceException{
		List<OrderItem> orderItems = getServiceFactory()
				.getService(OrderItemService.class)
				.listByShoppingCart(cart);
		
		for (OrderItem oi : orderItems) {
			if (oi.getItem().getId() == item.getId()) {
				return true;
			}
		}
		
		return false;
	}
	
	public Integer countItemsOnCart(User user) throws ServiceException{
		if (!getServiceFactory().getService(UserService.class).hasCart(user)) {
			ShoppingCart shoppingCart = new ShoppingCart();
			shoppingCart.setId(user.getId());
			shoppingCart.setCustomer(user);
			save(shoppingCart);
			
			user.setCart(shoppingCart);
		}
		return getServiceFactory()
				.getService(OrderItemService.class)
				.listByShoppingCart(user.getCart()).size();
	}
}
