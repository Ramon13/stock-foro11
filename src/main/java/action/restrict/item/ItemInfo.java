package action.restrict.item;

import br.com.javamon.action.Action;
import entity.Item;
import service.ItemService;

public class ItemInfo extends Action{

	@Override
	public void process() throws Exception {
		try {
			Item item = getServiceFactory()
					.getService(ItemService.class)
					.validateAndFindById(getRequest().getParameter("itemId"));

			getRequest().setAttribute("item", item);
			foward("/restrict/item-info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			getResponse().sendError(500, e.getMessage());
		}
	}

}
