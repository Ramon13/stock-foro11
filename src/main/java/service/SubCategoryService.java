package service;

import java.util.ArrayList;
import java.util.List;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.service.Service;
import dao.SubCategoryDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.SubCategory;

public class SubCategoryService extends Service{
	
	public SubCategory save(SubCategory subCategory) throws ServiceException, ValidationException{
		if (!isValidName(subCategory.getName()))
			throw new ValidationException(ValidationMessageUtil.SUBCATEGORY_NAME_EXISTS);
		
		Category category = null;
		if ((category = getServiceFactory().getService(CategoryService.class).findById(subCategory.getCategory().getId())) == null)
			throw new ValidationException(ValidationMessageUtil.INVALID_CATEGORY_ID);
		
		subCategory.setCategory(category);
		try {
			Long newId = (Long) getDaoFactory().getDAO(SubCategoryDAO.class).save(subCategory);
			return findById(newId);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public SubCategory findById(Long id) throws ServiceException{
		try {
			return getDaoFactory().getDAO(SubCategoryDAO.class).load(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public List<SubCategory> list() throws ServiceException{
		try {
			return getDaoFactory().getDAO(SubCategoryDAO.class).list();
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LIST);
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
	
	private boolean isValidName(String name) throws ServiceException{
		try {
			return getDaoFactory().getDAO(SubCategoryDAO.class).findByName(name) == null;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD, e);
		}
	}
}
