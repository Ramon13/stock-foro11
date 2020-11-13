package service;

import java.util.ArrayList;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import dao.SubCategoryDAO;
import domain.util.ExceptionMessageUtil;
import entity.Category;
import entity.SubCategory;

public class SubCategoryService extends ApplicationService<SubCategory, SubCategoryDAO>{
	
	public SubCategoryService() {
		super(SubCategoryDAO.class);
	}
	
	public List<SubCategory> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(SubCategoryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
	}
	
	public SubCategory findByName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(SubCategoryDAO.class).findByName(name);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
	
	/**
	 * From the current DAO source, loads a list with all SubCategories that have the same Category
	 * @param categoryId The id of an Category object
	 * @return a list of SubCategory
	 * @throws ServiceException encapsulates any data source exception
	 */
	public List<SubCategory> listByCategory(long categoryId) throws ServiceException{
		try {
			Category category = null;
			if ((category = getServiceFactory().getService(CategoryService.class).findById(categoryId)) != null) {
				return getDaoFactory().getDAO(SubCategoryDAO.class).listSubCategoryByCategory(category);
			}
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
		}
		
		return new ArrayList<SubCategory>(0);
	}
	
	public boolean isValidNewSubCategoryName(String name) throws ServiceException{
		return findByName(name) == null;
	}
}
