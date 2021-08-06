package action.restrict.provider;

import action.ApplicationAction;
import service.ProviderService;

public class ProviderList extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		getRequest().setAttribute("providers", getServiceFactory().getService(ProviderService.class).list());
		foward("/restrict/provider-list.jsp");
	}

}
