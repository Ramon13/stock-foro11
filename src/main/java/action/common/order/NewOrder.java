package action.common.order;

import java.time.LocalDate;
import java.util.List;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.OrderStatus;
import entity.Order;
import entity.OrderItem;
import entity.ShoppingCart;
import entity.User;
import service.OrderItemService;
import service.OrderService;

public class NewOrder extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		OrderService orderSvc = getServiceFactory().getService(OrderService.class);
		User user = ActionUtil.getSessionLoggedUser(getRequest()).getUser();
		
		Long orderId = orderSvc.save(createOrder(user));
		Order order = orderSvc.findById(orderId);
		updateOrderItemsAmount(user.getCart(), order);
	}

	public Order createOrder(User user) {
		Order order = new Order();
		order.setRequestDate(LocalDate.now());
		order.setCustomer(user);
		order.setStatus(OrderStatus.PENDING.getValue());
		
		return order;
	}
	
	public void updateOrderItemsAmount(ShoppingCart cart, Order order) throws ServiceException, ConvertException{
		String[] parameters = getRequest().getParameterValues("itemAmount");
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		List<OrderItem> orderItems = orderItemSvc.listByShoppingCart(cart);
		
		OrderItem orderItem = null;
		for (int i = 0; i < orderItems.size(); i++) {
			orderItem = orderItems.get(i);
			orderItem.setAmount(NumberConvert.stringToInteger(parameters[i]));
			orderItem.setOrder(order);
			orderItem.setCart(null);
			orderItemSvc.update(orderItems.get(i));
		}
	}
}
