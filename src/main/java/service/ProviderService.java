package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.ProviderDAO;
import domain.gson.ProviderJSON;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Provider;

//TODO: abstract common methods on an interface
public class ProviderService extends ApplicationService<Provider, ProviderDAO>{

	public ProviderService() {
		super(ProviderDAO.class);
	}

	public Provider saveProvider(Provider provider) throws ValidationException, ServiceException{
		if (!isValidProviderName(provider))
			throw new ValidationException(ValidationMessageUtil.PROVIDER_NAME_EXISTS);
		if (!isValidProviderCNPJ(provider))
			throw new ValidationException(ValidationMessageUtil.PROVIDER_CNPJ_EXISTS);
		
		Long id = provider.getId();
		if (provider.getId() != null)
			update(provider);
		else
			id = save(provider);
		return findById(id);
	}
	
	public List<Provider> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(ProviderDAO.class).list();
		}catch(DAOException ex) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST, ex);
		}
	}
	
	public Provider findById(Long id) throws ServiceException {
		try {
			return getDaoFactory().getDAO(ProviderDAO.class).load(id);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public boolean isValidProviderName(Provider provider) throws ServiceException{
		try {
			Provider providerDB = getDAO().findByName(provider.getName());
			
			if (providerDB != null)
				if (!providerDB.getId().equals(provider.getId()))
					return false;
			
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public boolean isValidProviderCNPJ(Provider provider) throws ServiceException{
		try {
			System.out.println(provider.getCnpj());
			Provider providerDB = getDAO().findByCNPJ(provider.getCnpj());
			
			if (providerDB != null)
				if (!providerDB.getId().equals(provider.getId()))
					return false;
			
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public ProviderJSON toProviderJSON(Provider provider) throws ServiceException{
		return new ProviderJSON(provider);
	}
}
