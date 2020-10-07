package action.restrict.user;

import java.util.ArrayList;
import java.util.List;

import action.ApplicationAction;
import entity.User;
import service.UserService;

public class ListUser extends ApplicationAction{

	private UserService userSvc;
	
	@Override
	public void processAction() throws Exception {
		userSvc = getServiceFactory().getService(UserService.class);
		
		sendUsers();
	}

	public void sendUsers() throws Exception{
		List<User> users = new ArrayList<User>();
		String fowardPath;
		
		if (isSearchAction()) {
			users = userSvc.searchOnUser(paginationFilter);
			fowardPath = "/restrict/user-list.jsp";
		
		}else if (isListAll()) {
			users = userSvc.list(paginationFilter);
			fowardPath = "/restrict/user-list.jsp";
		
		}else {
			fowardPath = "/restrict/users.jsp";
		}
			
		getRequest().setAttribute("users", users);
		foward(fowardPath);
	}
}
