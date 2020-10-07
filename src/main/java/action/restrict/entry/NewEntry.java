package action.restrict.entry;

import br.com.javamon.action.Action;
import service.ItemService;
import service.ProviderService;

public class NewEntry extends Action{

	@Override
	public void process() throws Exception {
		getRequest().setAttribute("items", getServiceFactory()
				.getService(ItemService.class)
				.list());
		getRequest().setAttribute("providers", getServiceFactory()
				.getService(ProviderService.class)
				.list());
		
		foward("/restrict/add-entry-ajax.jsp");
	}
}
