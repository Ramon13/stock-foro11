package action.restrict.entry;

import java.util.List;

import action.ApplicationAction;
import entity.Entry;
import service.EntryService;
import service.ItemService;
import service.ProviderService;

public class ListEntries extends ApplicationAction{

	@Override
	public void processAction() throws Exception {	
		getRequest().setAttribute("entries", getEntries());
		
		getRequest().setAttribute("items", getServiceFactory()
				.getService(ItemService.class)
				.list());
		
		getRequest().setAttribute("providers", getServiceFactory()
				.getService(ProviderService.class)
				.list());
		
		foward("/restrict/entries.jsp");
	}

	private List<Entry> getEntries() throws Exception{
		EntryService entrySvc = getServiceFactory().getService(EntryService.class);
		if (isSearchAction()) {
			return entrySvc.searchOnEntry(paginationFilter);
		}
			
		return entrySvc.list(paginationFilter);
	}
}
