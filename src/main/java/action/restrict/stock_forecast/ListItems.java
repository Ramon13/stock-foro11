package action.restrict.stock_forecast;

import java.util.List;

import action.ApplicationAction;
import entity.Item;
import service.ItemService;

public class ListItems extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		List<Item> items = getServiceFactory().getService(ItemService.class).list();
		
		getRequest().setAttribute("items", items);
		foward("/restrict/stock-forecast.jsp");
	}
}
