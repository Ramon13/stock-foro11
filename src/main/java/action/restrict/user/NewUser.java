package action.restrict.user;

import action.ApplicationAction;

public class NewUser extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		foward("/restrict/save-user.jsp");
		
	}

}
