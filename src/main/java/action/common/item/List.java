package action.common.item;

import java.util.ArrayList;

import action.ApplicationAction;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Item;
import entity.PaginationFilter;
import entity.SubCategory;
import service.ItemService;
import service.SubCategoryService;

public class List extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		java.util.List<SubCategory> subcategories = null;
		
		if ((subcategories = getSelectedSubCategories()).isEmpty()) {
			subcategories = getServiceFactory().getService(SubCategoryService.class).list(new PaginationFilter());
		}
		 
		SubCategory[] subArr = subcategories.toArray(new SubCategory[subcategories.size()]);
		
		java.util.List<Item> items = getServiceFactory()
				.getService(ItemService.class)
				.listBySubCategory(paginationFilter, subArr);
		
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("subCategories", subcategories);
		foward("/common/home.jsp");
	}

	private java.util.List<SubCategory> getSelectedSubCategories() throws ServiceException, ValidationException{
		SubCategoryService subCategorySvc = getServiceFactory().getService(SubCategoryService.class);
		
		String[] subcategoryIds = getRequest().getParameterValues("subcategory");
		java.util.List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		
		if (subcategoryIds == null || subcategoryIds.length == 0) {
			return subCategoryList;
		}
		
		for (String subCategoryId : subcategoryIds) {
			subCategoryList.add(subCategorySvc.validateAndFindById(subCategoryId));
		}
		
		return subCategoryList;
	}
}
