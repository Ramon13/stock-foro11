package action.restrict.category;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import service.CategoryService;

public class SaveCategory extends ApplicationAction{

	private CategoryService categorySvc;
	
	@Override
	public void processAction() throws Exception {
		categorySvc = getServiceFactory().getService(CategoryService.class);
		
		Category category = validateFields();	
		Long categoryId = categorySvc.save(category);
		category = categorySvc.findById(categoryId);
		responseToClient(200, new Gson().toJson(category));
	}

	private Category validateFields() throws ValidationException, ServiceException{
		Category category = new Category();
		
		String cName = getRequest().getParameter("cname");
		
		if (StringValidator.isEmpty(cName))
			formValidationList.add(new FormValidateJSON("cname", 
					ValidationMessageUtil.EMPTY_CATEGORY_NAME));
		
		if (!StringValidator.isValidLen(255, cName))
			formValidationList.add(new FormValidateJSON("cname", 
					ValidationMessageUtil.CATEGORY_MAX_LEN));
		
		//Just for this application TODO remove this
		if (!StringValidator.isValidIntegerParse(cName) || NumberConvert.stringToInteger(cName) > 1000)
			formValidationList.add(new FormValidateJSON("cname", 
					ValidationMessageUtil.INVALID_CATEGORY_TYPE));
		
		else if (!categorySvc.isValidNewCategoryName(cName))
			formValidationList.add(new FormValidateJSON("cname", 
					ValidationMessageUtil.CATEGORY_NAME_EXISTS));
		
		if (!formValidationList.isEmpty())
			throw new ValidationException();
		
		category.setName(NumberConvert.stringToInteger(cName));
		return category;
	}
}
