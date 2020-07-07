package admin.action;

import service.EntryService;

public class ListEntriesAction extends AdminAction{

	@Override
	public void processAction() throws Exception {
		
		getRequest().setAttribute("entries", getServiceFactory()
				.getService(EntryService.class)
				.list());
		foward("/restrict/entries.jsp");
	}

}
