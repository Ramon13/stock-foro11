package action.restrict.order_item;

import java.util.List;
import java.util.Locale;

import action.ActionUtil;
import br.com.javamon.action.Action;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.validation.StringValidator;
import domain.DateUtil;
import entity.Order;
import entity.OrderItem;
import service.ItemService;
import service.UserService;
import service.OrderItemService;
import service.OrderService;

public class ListOrderItemsByOrder extends Action{

	@Override
	public void process() throws Exception {
		String orderIdParam = getRequest().getParameter("order");
		if (StringValidator.isValidLongParse(orderIdParam)) {
			
			Long orderId = NumberConvert.stringToLong(orderIdParam);
			Order order = getServiceFactory().getService(OrderService.class).findById(orderId);
			List<OrderItem> orderItems = getServiceFactory()
					.getService(OrderItemService.class)
					.listOrderItemsByOrder(order);
			
			getServiceFactory().getService(ItemService.class).setItemsCurrentAmount(orderItems);
			String[] monthNames = DateUtil.lastMonthsNames(12, new Locale("pt"));
			
			getRequest().setAttribute("firstDayOfCurrentYear", DateUtil.firstDayOfCurrentYear());
			getRequest().setAttribute("today", DateUtil.today());
			getRequest().setAttribute("order", order);
			getRequest().setAttribute("orderItems", orderItems);
			getRequest().setAttribute("monthNames", monthNames);
			ActionUtil.addOrderStatusOnRequest(getRequest(), order);
			ActionUtil.setSuperAdminUser(getRequest(), getServiceFactory().getService(UserService.class));
			foward("/restrict/order-item-ajax.jsp");
		}
		
	}


}
