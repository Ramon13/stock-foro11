package action.restrict.item;

import br.com.javamon.action.Action;
import entity.Item;
import service.ItemService;

public class ItemInfo extends Action{

	@Override
	public void process() throws Exception {
		try {
			//TODO: only send EntryItems after 2013
			Item item = getServiceFactory()
					.getService(ItemService.class)
					.validateAndFindById(getRequest().getParameter("itemId"));
//			getRequest().setAttribute("packets", 
//					getServiceFactory().getService(PacketService.class).list());
//			getRequest().setAttribute("categories", 
//					getServiceFactory().getService(CategoryService.class).list());
//			getRequest().setAttribute("orderItems", getServiceFactory()
//					.getService(OrderItemService.class)
//					.listByItem(item));
//			getRequest().setAttribute("entryItems", getServiceFactory()
//					.getService(EntryItemService.class)
//					.listByItem(item));
//			
			getRequest().setAttribute("item", item);
			foward("/restrict/item-info.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			getResponse().sendError(500, e.getMessage());
		}
	}

}
