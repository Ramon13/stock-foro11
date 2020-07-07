package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.Service;
import dao.CategoryDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Category;

public class CategoryService extends Service{

	public Category save(Category category) throws ServiceException, ValidationException{
		if (!isValidCategoryName(category.getName()))
			throw new ValidationException(ValidationMessageUtil.CATEGORY_NAME_EXISTS);
		
		try {
			Long newId = (Long) getDaoFactory().getDAO(CategoryDAO.class).save(category);
			return getDaoFactory().getDAO(CategoryDAO.class).load(newId);
		} catch (DAOException e) {
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE, e.getCause());
		}
	}
	
	public List<Category> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public Category findById(long id) throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).load(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	private boolean isValidCategoryName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).findByName(name) == null;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
	
	//TODO just for this application - Remove later
	@Deprecated
	private boolean isValidCategoryName(Integer name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).findByName(name) == null;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
}
