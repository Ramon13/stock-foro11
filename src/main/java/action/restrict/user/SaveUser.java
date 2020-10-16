package action.restrict.user;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Locale;
import entity.Permission;
import entity.User;
import service.LocaleService;
import service.PermissionService;
import service.UserService;

public class SaveUser extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		User newUser = validateFields();
		getServiceFactory().getService(UserService.class).saveUser(newUser);
	}
	
	private User validateFields() throws ValidationException, ServiceException{
		
		String userName = getRequest().getParameter("name");
		String password = getRequest().getParameter("password");
		String strPermission = getRequest().getParameter("permission");
		String strLocale = getRequest().getParameter("locale");
		
		if (StringUtils.isAllBlank(userName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.USER_NAME_EMPTY));
		
		else if (!StringUtils.isAsciiPrintable(userName) || StringUtils.containsWhitespace(userName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.INVALID_USERNAME));
		
		else if (!StringValidator.isValidLen(255, userName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.USER_NAME_MAX_LEN));
		
		if (!getServiceFactory().getService(UserService.class).isValidNewUserName(userName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.USER_NAME_ALREADY_EXISTS));
		
		
		if (StringUtils.isAllBlank(password))
			formValidationList.add(
					new FormValidateJSON("password", ValidationMessageUtil.GENERIC_EMPTY_FIELD));
		
		else if (!StringUtils.isAsciiPrintable(password) || StringUtils.containsWhitespace(password))
			formValidationList.add(
					new FormValidateJSON("password", ValidationMessageUtil.INVALID_PASSWORD));
		
		else if (!StringValidator.isValidLen(255, userName))
			formValidationList.add(
					new FormValidateJSON("password", ValidationMessageUtil.GENERIC_MAX_LEN_255));
		
		
		if (!getServiceFactory().getService(UserService.class).isValidNewUserName(userName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.USER_NAME_ALREADY_EXISTS));
		
		
		Permission permission = null;
		try {
			 permission = getServiceFactory()
					.getService(PermissionService.class)
					.validateAndFindById(strPermission);
		} catch (ValidationException | ServiceException e) {
			formValidationList.add(
					new FormValidateJSON("permission", ValidationMessageUtil.ID_NOT_FOUND));
		}
		
		
		Locale locale = null;
		try {
			 locale = getServiceFactory()
					.getService(LocaleService.class)
					.validateAndFindById(strLocale);
		} catch (ValidationException | ServiceException e) {
			formValidationList.add(
					new FormValidateJSON("locale", ValidationMessageUtil.ID_NOT_FOUND));
		}
		
		if (!formValidationList.isEmpty())	
			throw new ValidationException();
		
		
		User user = new User();
		user.setName(userName);
		user.setPassword(password);
		user.setLocale(locale);
		user.setPermission(permission);
		
		return user;
	}
}
