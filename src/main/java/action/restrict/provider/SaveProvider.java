package action.restrict.provider;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Provider;
import service.ProviderService;

public class SaveProvider extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		ProviderService providerSvc = getServiceFactory().getService(ProviderService.class);
		Provider provider = validateFields();
		Long newProviderId = providerSvc.save(provider);
		Provider persistedProvider = providerSvc.findById(newProviderId);
		responseToClient(200, new Gson().toJson(persistedProvider));
	}

	private Provider validateFields() throws ValidationException, ServiceException{
		
		Provider provider = new Provider();
		
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
		
		if (!getServiceFactory().getService(ProviderService.class).isValidProviderName(provider))
			formValidationList.add(new FormValidateJSON("pname", ValidationMessageUtil.PROVIDER_NAME_EXISTS));
		
		if (!getServiceFactory().getService(ProviderService.class).isValidProviderCNPJ(provider))
			formValidationList.add(new FormValidateJSON("pcnpj", ValidationMessageUtil.PROVIDER_CNPJ_EXISTS));
		
		if (!formValidationList.isEmpty()) {
			throw new ValidationException();
		}
		
		return provider;
	}
}
