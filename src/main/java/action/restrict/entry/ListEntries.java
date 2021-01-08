package action.restrict.entry;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import entity.Entry;
import entity.Item;
import service.EntryService;

public class ListEntries extends ApplicationAction{

	private EntryService entrySvc;
	
	@Override
	public void processAction() throws Exception {
		entrySvc = getServiceFactory().getService(EntryService.class);
		List<Entry> entries;
		
		if (!StringUtils.isAllBlank(getRequest().getParameter("itemId"))) {
			Item item = ActionUtil.getRequestItem(getRequest());
			entries = getEntriesByItem(item);
			getRequest().setAttribute("item", item);
		
		}else {
			entries = getEntries();
		}
		
		getRequest().setAttribute("entries", entries);
		foward("/restrict/entries-table.jsp");
	}

	private List<Entry> getEntries() throws Exception{
		if (isSearchAction()) {
			return entrySvc.searchOnEntry(paginationFilter);
		}
			
		return entrySvc.list(paginationFilter);
	}
	
	private List<Entry> getEntriesByItem(Item item) throws Exception {
		if (isSearchAction()) {
			return entrySvc.searchByItem(item, paginationFilter);
		}
		
		return entrySvc.listByItem(item, paginationFilter);
	}
	
}
