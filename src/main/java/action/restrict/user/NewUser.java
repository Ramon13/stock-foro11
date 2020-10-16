package action.restrict.user;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import domain.LoggedUser;
import entity.Locale;
import entity.Permission;
import service.LocaleService;
import service.PermissionService;

public class NewUser extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		LoggedUser loggedUser = ActionUtil.getSessionLoggedUser(getRequest());
		
		List<Permission> permissions = getServiceFactory()
				.getService(PermissionService.class)
				.listLessOrEqualsLevel(loggedUser.getUser().getPermission().getLevel());
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		
		getRequest().setAttribute("tmpPass", RandomStringUtils.randomAlphanumeric(7));
		getRequest().setAttribute("permissions", permissions);
		getRequest().setAttribute("locales", locales);
		foward("/restrict/save-user.jsp");		
	}

}
