package action.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.FormValidateJSON;
import br.com.javamon.action.Action;
import br.com.javamon.exception.ValidationException;
import domain.PermissionRoles;
import domain.util.ValidationMessageUtil;
import entity.User;
import service.UserService;

public class ResetPasswordAction extends Action{

	@Override
	public void process() throws Exception {
		try {
			User loggedUser = ActionUtil.getSessionLoggedUser(getRequest()).getUser();
			String newPassword = validateFields(loggedUser);
			getServiceFactory().getService(UserService.class).setNewPassword(loggedUser, newPassword);
			sendToHomePage(loggedUser);
		} catch (ValidationException e) {
			foward("/public/new-password-form.jsp");
			e.printStackTrace();
		}
	}

	public String validateFields(User loggedUser) throws ValidationException{
		List<FormValidateJSON> formValidationList = new ArrayList<FormValidateJSON>();
		
		String currentPass = getRequest().getParameter("currentPass");
		String newPass = getRequest().getParameter("newPass");
		String newPassTwice = getRequest().getParameter("newPassTwice");
		
		if (loggedUser.getPassword().equals(currentPass) == Boolean.FALSE)
			formValidationList.add(
					new FormValidateJSON("incorrectPass", ValidationMessageUtil.INVALID_PASSWORD));
		
		if (newPass.equals(newPassTwice) == Boolean.FALSE)
			formValidationList.add(
					new FormValidateJSON("generalError", ValidationMessageUtil.DIFF_PASSWORDS));
			
		if (newPass.length() < 8 || newPass.length() > 16 || StringUtils.isAnyBlank(newPass))
			formValidationList.add(
					new FormValidateJSON("invalidNewPassLen", ValidationMessageUtil.INVALID_PASSWORD_LEN));
		
		if (!formValidationList.isEmpty()) {
			for (FormValidateJSON fvJSON : formValidationList) {
				getRequest().setAttribute(fvJSON.getDivName(), fvJSON.getMessage());
			}
			throw new ValidationException();
		}
		
		return newPass;
	}
	
	public void sendToHomePage(User user) throws Exception {
		if (user.getPermission().getLevel() == PermissionRoles.USER.getValue()) { 
			redirect("/common/home.action");
			return;
		}
		
		redirect("/restrict/home.action");
	}
}
