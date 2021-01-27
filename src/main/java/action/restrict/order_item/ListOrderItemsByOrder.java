package action.restrict.order_item;

import java.time.LocalDate;
import java.util.List;

import action.ActionUtil;
import br.com.javamon.action.Action;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.validation.StringValidator;
import domain.DateUtil;
import entity.Item;
import entity.Locale;
import entity.Order;
import entity.OrderItem;
import service.ItemService;
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
			
			setItemAmount(orderItems, order);
			String[] monthNames = DateUtil.lastMonthsNames(12, new java.util.Locale("pt"));
		
			getRequest().setAttribute("firstDayOfCurrentYear", DateUtil.firstDayOfCurrentYear());
			getRequest().setAttribute("today", DateUtil.today());
			getRequest().setAttribute("order", order);
			getRequest().setAttribute("orderItems", orderItems);
			getRequest().setAttribute("monthNames", monthNames);
			ActionUtil.addOrderStatusOnRequest(getRequest(), order);
			
			foward("/restrict/order-item-ajax.jsp");
		}
		
	}


	private void setItemAmount(List<OrderItem> orderItems, Order order) throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		Locale locale = order.getCustomer().getLocale();
		Item item;
		
		for (OrderItem oi : orderItems) {
			item = oi.getItem();
			item.setCurrentYearAmount(
					itemSvc.getItemAmountByLocaleAndYear(locale, LocalDate.now().getYear(), item));
			
			item.setSumByMonth(itemSvc.getPreviousMonthsAmount(item, 12, locale));
		}
	}
}
