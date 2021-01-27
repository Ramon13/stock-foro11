package action.auth;

import java.util.ArrayList;
import java.util.List;

import action.ActionUtil;
import action.FormValidateJSON;
import br.com.javamon.action.Action;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.LoggedUser;
import domain.PermissionRoles;
import domain.util.ValidationMessageUtil;
import entity.User;
import service.UserService;

public class Login extends Action {

	@Override
	public void process() throws Exception {
		try {
			User user = validateFields();
			user = validateCredentials(user);
			
			getRequest().getSession().setAttribute("loggedUser", new LoggedUser(user));

			if (PermissionRoles.isAnyAdmin(user.getPermission())) {
				ActionUtil.addValidWriteRoleOnSession(getRequest());
				redirect("restrict/home.action");
			
			}else {
				redirect("common/home.action");
			}
		} catch (ValidationException e) {
			foward("/public/login.jsp");
			e.printStackTrace();
		}
		
	}
	
	private User validateFields() throws ValidationException{
		List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
		User user = new User();
		
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		
		if (StringValidator.isEmpty(username)) {
			formValidationList.add(
					new FormValidateJSON("usernameError", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		}else if (!StringValidator.isValidLen(255, username)) {
			formValidationList.add(
					new FormValidateJSON("usernameError", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		}
		
		if (StringValidator.isEmpty(password)) {
			formValidationList.add(
					new FormValidateJSON("passwordError", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		}else if (!StringValidator.isValidLen(255, password))
			formValidationList.add(
					new FormValidateJSON("passwordError", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		
		if (!formValidationList.isEmpty()) {
			for (FormValidateJSON fvJSON : formValidationList) {
				getRequest().setAttribute(fvJSON.getDivName(), fvJSON.getMessage());
			}
			throw new ValidationException();
		}

		user.setName(username);
		user.setPassword(password);
		
		return user;
	}
	
	private User validateCredentials(User user) throws ValidationException, ServiceException{
		UserService userSvc = getServiceFactory().getService(UserService.class);
		
		if (!userSvc.isValidLogin(user)) {
			getRequest().setAttribute("generalError", ValidationMessageUtil.ILLEGAL_CREDENTIALS);
			throw new ValidationException();
		}
		
		user = userSvc.findByUserName(user.getName());
		if (user.getActive() == false) {
			getRequest().setAttribute("generalError", ValidationMessageUtil.BLOCKED_USER);
			throw new ValidationException();
		}
		
		return user;
	}
	
}
