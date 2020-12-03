package action.restrict.entry;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import entity.Entry;
import entity.Item;
import service.EntryService;
import service.ItemService;

public class ListEntries extends ApplicationAction{

	private EntryService entrySvc;
	
	@Override
	public void processAction() throws Exception {
		entrySvc = getServiceFactory().getService(EntryService.class);
		
		if (!StringUtils.isAllBlank(getRequest().getParameter("item")))
			sendEntriesByItem();
		else
			sendEntries();
	}

	private void sendEntries() throws Exception{
		List<Entry> entries;
		
		if (isSearchAction())
			entries = entrySvc.searchOnEntry(paginationFilter);
		else
			entries = entrySvc.list(paginationFilter);
		
		getRequest().setAttribute("entries", entries);
		foward("/restrict/entries-table.jsp");
	}
	
	private void sendEntriesByItem() throws Exception {
		String itemId = getRequest().getParameter("item");	
		Item item = getServiceFactory()
				.getService(ItemService.class)
				.validateAndFindById(itemId);
		List<Entry> entries;
		
		if (isSearchAction())
			entries = entrySvc.searchByItem(paginationFilter.getSearchWord(), item);
		else
			entries = entrySvc.listByItem(item, paginationFilter);
		
		getRequest().setAttribute("item", item);
		getRequest().setAttribute("entries", entries);
		foward("/restrict/entries-table.jsp");
	}
	
}
