package action.restrict.item;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import entity.Entry;
import entity.Item;
import service.EntryService;
import service.ItemService;

public class ItemInfo extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		Item item = getServiceFactory()
				.getService(ItemService.class)
				.validateAndFindById(getRequest().getParameter("itemId"));
		
		if (!StringUtils.isAllBlank(getRequest().getParameter("listProp"))) {
			sendEntriesByItem(item);
		}
		
		getRequest().setAttribute("item", item);
		foward("/restrict/item-info.jsp");
		
	}
	
	private void sendEntriesByItem(Item item) throws Exception {
		if (getRequest().getParameter("listProp").equals("entry")) {
			EntryService entrySvc = getServiceFactory().getService(EntryService.class);
			List<Entry> entries;
			
			if (isSearchAction())
				entries = entrySvc.searchByItem(item, paginationFilter);
			else
				entries = entrySvc.listByItem(item, paginationFilter);
			
			getRequest().setAttribute("entries", entries);
		}
	} 
}
