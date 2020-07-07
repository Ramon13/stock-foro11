package admin.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import service.CategoryService;

public class SaveCategoryAction extends AdminAction{

	@Override
	public void processAction() throws Exception {
		
		Category category = validateFields();
		try {
			category = getServiceFactory().getService(CategoryService.class).save(category);
			responseToClient(200, new Gson().toJson(category));
		} catch (ValidationException e) {
			getRequest().setAttribute("formValidationList", Arrays.asList(new FormValidateJSON("cgeneral", e.getMessage())));
			throw new ValidationException();
		}
	}

	private Category validateFields() throws ValidationException{
		Category category = new Category();
		List<FormValidateJSON> formValidateJSONs = new ArrayList<FormValidateJSON>(0);
		
		String cName = getRequest().getParameter("cname");
		
		if (StringValidator.isEmpty(cName))
			formValidateJSONs.add(new FormValidateJSON("cname", ValidationMessageUtil.EMPTY_CATEGORY_NAME));
		
		if (!StringValidator.isValidLen(255, cName))
			formValidateJSONs.add(new FormValidateJSON("cname", ValidationMessageUtil.CATEGORY_MAX_LEN));
		
		//Just for this application TODO remove this
		if (!StringValidator.isValidIntegerParse(cName) || NumberConvert.stringToInteger(cName) > 1000)
			formValidateJSONs.add(new FormValidateJSON("cname", ValidationMessageUtil.INVALID_CATEGORY_TYPE));
			
		if (formValidateJSONs.size() != 0) {
			getRequest().setAttribute("formValidationList", formValidateJSONs);
			throw new ValidationException();
		}
		
		category.setName(NumberConvert.stringToInteger(cName));
		return category;
	}
}
