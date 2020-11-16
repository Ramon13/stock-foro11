package service;

import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import dao.CategoryDAO;
import domain.util.ExceptionMessageUtil;
import entity.Category;

public class CategoryService extends ApplicationService<Category, CategoryDAO>{
	
	public CategoryService() {
		super(CategoryDAO.class);
	}

	public List<Category> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public Category findByName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(CategoryDAO.class).findByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
	
	public boolean isValidNewCategoryName(String name) throws ServiceException{
		return findByName(name) == null;
	}
}
