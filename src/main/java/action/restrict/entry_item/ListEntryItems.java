package action.restrict.entry_item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.javamon.action.Action;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Entry;
import entity.EntryItem;
import entity.Item;
import service.EntryService;
import service.ItemService;

public class ListEntryItems extends Action {

	@Override
	public void process() throws Exception {
		List<EntryItem> entryItems = new ArrayList<EntryItem>();
		listByEntry(entryItems);
		listByItem(entryItems);
		
		getRequest().setAttribute("entryItems", entryItems);
		foward("/restrict/entry-item-ajax.jsp");
	}
	
	private void listByEntry(List<EntryItem> entryItems) throws ServiceException, ValidationException{
		if (!StringUtils.isAllBlank(getRequest().getParameter("entry"))) {
			Entry entry = getServiceFactory()
					.getService(EntryService.class)
					.validateAndFindById(getRequest().getParameter("entry"));
			
			entryItems.addAll(entry.getEntryItems());
		}
	}
	
	private void listByItem(List<EntryItem> entryItems) throws ServiceException, ValidationException{
		if (!StringUtils.isAllBlank(getRequest().getParameter("item"))) {
			Item item = getServiceFactory()
					.getService(ItemService.class)
					.validateAndFindById(getRequest().getParameter("item"));
			
			entryItems.addAll(item.getEntryItems());
		}
	}
}
