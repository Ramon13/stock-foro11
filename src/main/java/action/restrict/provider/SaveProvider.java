package action.restrict.provider;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;

import action.ActionUtil;
import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Provider;
import service.ProviderService;

public class SaveProvider extends ApplicationAction{

	enum OPTIONS {edit, save};
	
	@Override
	public void processAction() throws Exception {
		String option = getRequest().getParameter("option");
		
		if (StringUtils.isAllBlank(option)) {
			sendPage();
			return;
		}
		
		if (option.equals(OPTIONS.save.toString())) {
			save();
		
		}else {
			String providerId = getRequest().getParameter("provId");
			edit(providerId);
			sendPage();
		}
	}
	
	private void sendPage() throws Exception {
		foward("/restrict/save-provider.jsp");
	}
	
	private void edit(String providerId) throws Exception {
		ProviderService providerSvc = getServiceFactory().getService(ProviderService.class);
		
		if (!StringUtils.isAllBlank(providerId)) {
			ActionUtil.setEditMode(getRequest(), true);
			Provider provider = providerSvc.validateAndFindById(providerId);
			getRequest().setAttribute("provider", provider);
		}
	}
	
	private void save() throws ServiceException, ValidationException, IOException {
		ProviderService providerSvc = getServiceFactory().getService(ProviderService.class);
		Provider provider = validateFields();
		
		try {
			Provider persistedProvider = providerSvc.saveProvider(provider);
			responseToClient(200, new Gson().toJson(persistedProvider));
		} catch (ValidationException e) {
			formValidationList.add(new FormValidateJSON("pgeneral", e.getMessage()));
			throw new ValidationException();
		}
	}

	private Provider validateFields() throws ValidationException, ServiceException{
		
		Provider provider = new Provider();
		
		String provIdParam = getRequest().getParameter("provId");
		if (!StringUtils.isAllBlank(provIdParam))
			provider.setId(Long.parseLong(provIdParam));
		
		provider.setName(getRequest().getParameter("pname"));
		provider.setCnpj(getRequest().getParameter("pcnpj"));
		
		if (StringValidator.isEmpty(provider.getName()))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.EMPTY_PROVIDER_NAME));
		
		if (StringValidator.isEmpty(provider.getCnpj()))
			formValidationList.add(new FormValidateJSON("pcnpj", ValidationMessageUtil.EMPTY_PROVIDER_CNPJ));
		
		if (!StringValidator.isValidLen(255, provider.getName()))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.PROVIDER_MAX_LEN));
		
		if (!StringValidator.isValidLen(10, 20, provider.getCnpj()))
			formValidationList.add(new FormValidateJSON("pcnpj", ValidationMessageUtil.PROVIDER_CNPJ_INVALID_LEN));
		
		if (!formValidationList.isEmpty()) {
			throw new ValidationException();
		}
		
		return provider;
	}
}
