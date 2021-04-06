package action.restrict.provider;

import action.ApplicationAction;

public class NewProvider extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		redirect("/restrict/new-provider-ajax.jsp");
	}
}
