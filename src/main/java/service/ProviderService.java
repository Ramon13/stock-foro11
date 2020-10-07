package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.ProviderDAO;
import domain.util.ExceptionMessageUtil;
import entity.Provider;

//TODO: abstract common methods on an interface
public class ProviderService extends Service{

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
}
