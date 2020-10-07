package action.restrict.user;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.exception.ValidationException;
import domain.LoggedUser;
import domain.util.ValidationMessageUtil;
import entity.User;
import service.UserService;
public class BlockUser extends ApplicationAction{


	@Override
	public void processAction() throws Exception {
		UserService userSvc = getServiceFactory().getService(UserService.class);
		User user = userSvc.validateAndFindById(getRequest().getParameter("user"));
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		
		if (userSvc.isValidToBlock(loggedUser, user)) {
			userSvc.blockUser(user);
		
		}else {
			throw new ValidationException(ValidationMessageUtil.ROLE_PERMISSION_DENIED);
		}
		
	}

}
