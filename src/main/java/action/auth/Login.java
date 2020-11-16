package action.auth;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.LoggedUser;
import domain.util.ValidationMessageUtil;
import entity.User;
import service.UserService;

public class Login extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		User user = validateFields();
		user = getServiceFactory()
		.getService(UserService.class)
		.validateCredentials(user.getName(), user.getPassword());
		
		getRequest().getSession().setAttribute("loggedUser", new LoggedUser(user));

		foward("/restrict/template.jsp");
	}
	
	private User validateFields() throws ValidationException{
		User user = new User();
		
		String username = getRequest().getParameter("username");
		String password = getRequest().getParameter("password");
		
		if (StringValidator.isEmpty(username))
			formValidationList.add(
					new FormValidateJSON("username", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		else if (!StringValidator.isValidLen(255, username))
			formValidationList.add(
					new FormValidateJSON("username", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		
		if (StringValidator.isEmpty(password))
			formValidationList.add(
					new FormValidateJSON("password", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		else if (!StringValidator.isValidLen(255, password))
			formValidationList.add(
					new FormValidateJSON("password", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		
		if (!formValidationList.isEmpty()) {
			throw new ValidationException();
		}
		
		user.setName(username);
		user.setPassword(password);
		
		return user;
	}
	
}
