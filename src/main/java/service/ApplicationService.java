package service;

import java.util.List;

import br.com.javamon.convert.ConvertFactory;
import br.com.javamon.dao.DAOFactory;
import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.Service;
import br.com.javamon.service.ServiceFactory;
import br.com.javamon.validation.StringValidator;
import dao.ApplicationDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.PaginationFilter;

public class ApplicationService<R, T extends ApplicationDAO<R>> extends Service{

	private Class<T> clazz;
	
	private DAOFactory daoFactory;
	private ServiceFactory serviceFactory;
	private ConvertFactory convertFactory;
	private Integer maxItemsByPage = 50;
	
	public ApplicationService(Class<T> clazz) {
		this.clazz = clazz;
		daoFactory = DAOFactory.getInstance();
		serviceFactory = ServiceFactory.getInstance();
		convertFactory = ConvertFactory.getInstance();
	}
	
	public DAOFactory getDaoFactory() {
		return daoFactory;
	}
	
	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}
	
	public ConvertFactory getConvertFactory() {
		return convertFactory;
	}
	
	protected T getDAO(Class<T> clazz) throws DAOException{
		return getDaoFactory().getDAO(clazz);
	}
	
	protected T getDAO() throws DAOException{
		return getDaoFactory().getDAO(clazz);
	}
	
	public void save(R r) throws ServiceException {
		try {
			getDAO().save(r);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public R findById(Long id) throws ServiceException {
		try {
			R r = daoFactory.getDAO(clazz).load(id);
			
			if (r == null)
				 throw new ServiceException(ValidationMessageUtil.INVALID_ITEM_ID);
			
			return r;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
	
	public void update(R r) throws ServiceException {
		try {
			daoFactory.getDAO(clazz).update(r);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
	
	public List<R> list(PaginationFilter paginationFilter) throws ServiceException{
		try {
			return getDaoFactory().getDAO(clazz).list(paginationFilter);
		} catch (DAOException e) {
			throw new ServiceException("An exception ocourred in an attempt to recover data", e);
		}
	}
	
	public boolean isValidId(String id) {
		return StringValidator.isValidLongParse(id);
	}
	
	public R validateAndFindById(String id) throws ValidationException, ServiceException{
		R r;
		if (!isValidId(id) || (r = findById(Long.parseLong(id))) == null) {
			throw new ValidationException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
		return r; 
	}
	
	public Integer getMaxItemsByPage() {
		return maxItemsByPage;
	}
	
}
