package action.restrict.locale;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Locale;
import service.LocaleService;

public class SaveLocale extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		validateFields();
	}

	private Locale validateFields() throws ValidationException, ServiceException{
		
		String localeName = getRequest().getParameter("name");
		
		if (StringUtils.isAllBlank(localeName))
			formValidationList.add(
					new FormValidateJSON("lname", ValidationMessageUtil.LOCALE_NAME_EMPTY));
		
		else if (!StringUtils.isAsciiPrintable(localeName))
			formValidationList.add(
					new FormValidateJSON("lname", ValidationMessageUtil.INVALID_LOCALE));
		
		else if (!StringValidator.isValidLen(255, localeName))
			formValidationList.add(
					new FormValidateJSON("lname", ValidationMessageUtil.LOCALE_NAME_MAX_LEN));
		
		if (!getServiceFactory().getService(LocaleService.class).isValidNewLocaleName(localeName))
			formValidationList.add(
					new FormValidateJSON("lname", ValidationMessageUtil.LOCALE_NAME_ALREADY_EXISTS));
		
		if (!formValidationList.isEmpty())	
			throw new ValidationException();
		
		return null;
	}
}
