package action.auth;

import br.com.javamon.action.Action;

public class Logout extends Action{

	@Override
	public void process() throws Exception {
		getRequest().getSession().invalidate();
		redirect(".");
	}
	
	
}
