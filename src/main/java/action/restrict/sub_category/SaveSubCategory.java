package action.restrict.sub_category;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.SubCategory;
import service.CategoryService;
import service.SubCategoryService;

public class SaveSubCategory extends ApplicationAction{

	private SubCategoryService subCategorySvc;
	
	@Override
	public void processAction() throws Exception {
		subCategorySvc = getServiceFactory().getService(SubCategoryService.class);
		SubCategory subCategory = validateFields();
		
		subCategorySvc.save(subCategory);
	}

	private SubCategory validateFields() throws ValidationException, ServiceException{
		SubCategory subCategory = new SubCategory();
		
		subCategory.setName(getRequest().getParameter("name"));
		String categoryId = getRequest().getParameter("category");
		
		if (StringValidator.isEmpty(subCategory.getName()))
			formValidationList.add(new FormValidateJSON("scName", ValidationMessageUtil.EMPTY_SUBCATEGORY_NAME));
		
		else if (!StringValidator.isValidLen(255, subCategory.getName()))
			formValidationList.add(new FormValidateJSON("scName", ValidationMessageUtil.SUBCATEGORY_MAX_LEN));
		
		else if (!subCategorySvc.isValidNewSubCategoryName(subCategory.getName()))
			formValidationList.add(new FormValidateJSON("scName", ValidationMessageUtil.SUBCATEGORY_NAME_EXISTS));
				
		Category category = null;
		try {
			category = getServiceFactory().getService(CategoryService.class).validateAndFindById(categoryId);
		} catch (ValidationException e) {
			formValidationList.add(new FormValidateJSON("scCategory", ValidationMessageUtil.INVALID_CATEGORY_ID));
		}
		
		if (!formValidationList.isEmpty()) 
			throw new ValidationException();
		
		subCategory.setCategory(category);
		return subCategory;
	}
}
