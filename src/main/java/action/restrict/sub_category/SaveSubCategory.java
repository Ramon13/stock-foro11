package action.restrict.sub_category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.SubCategory;
import service.SubCategoryService;

public class SaveSubCategory extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		
		SubCategory subCategory = validateFields();
		try {
			subCategory = getServiceFactory().getService(SubCategoryService.class).save(subCategory);
			responseToClient(200, new Gson().toJson(subCategory));
		}catch(ValidationException ex) {
			getRequest().setAttribute("formValidationList", Arrays.asList(new FormValidateJSON("scgeneral", ex.getMessage())));
			throw new ValidationException();
		}
	}

	private SubCategory validateFields() throws ValidationException{
		SubCategory subCategory = new SubCategory();
		List<FormValidateJSON> formValidateJSONs = new ArrayList<FormValidateJSON>(0);
		
		subCategory.setName(getRequest().getParameter("scName"));
		String categoryId = getRequest().getParameter("scCategory");
		
		if (StringValidator.isEmpty(subCategory.getName()))
			formValidateJSONs.add(new FormValidateJSON("scName", ValidationMessageUtil.EMPTY_SUBCATEGORY_NAME));
		
		if (!StringValidator.isValidLen(255, subCategory.getName()))
			formValidateJSONs.add(new FormValidateJSON("scName", ValidationMessageUtil.SUBCATEGORY_MAX_LEN));
		
		if (!StringValidator.isValidLongParse(categoryId))
			formValidateJSONs.add(new FormValidateJSON("scCategory", ValidationMessageUtil.INVALID_CATEGORY_ID));
		
		if (formValidateJSONs.size() != 0) {
			getRequest().setAttribute("formValidationList", formValidateJSONs);
			throw new ValidationException();
		}
		
		subCategory.setCategory(new Category(NumberConvert.stringToLong(categoryId)));
		return subCategory;
	}
}
