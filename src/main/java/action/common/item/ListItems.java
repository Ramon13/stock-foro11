package action.common.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ApplicationAction;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Item;
import entity.SubCategory;
import service.ItemService;
import service.SubCategoryService;

public class ListItems extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		getRequest().setAttribute("subCategories", getServiceFactory().getService(SubCategoryService.class).list());
		
		SubCategory[] subCategories = getSubCategories();
		
		if (subCategories.length == 0) {
			getRequest().setAttribute("items", getItems());
			foward("/common/home.jsp");
			return;
		}

		getRequest().setAttribute("items", getServiceFactory().getService(ItemService.class).listBySubCategory(paginationFilter, subCategories));
		foward("/common/home.jsp");
	}

	private SubCategory[] getSubCategories() throws ServiceException, ValidationException{
		SubCategoryService subCategorySvc = getServiceFactory().getService(SubCategoryService.class);
		
		String[] subCategoryIds = getRequest().getParameterValues("subcategory");
		List<SubCategory> subCategoryList = new ArrayList<>();
		
		if (subCategoryIds != null) {
			for (int i = 0; i < subCategoryIds.length; i++) {
				subCategoryList.add(subCategorySvc.validateAndFindById(subCategoryIds[i]));
			}
		}
		
		return subCategoryList.toArray(new SubCategory[0]);
	}
	
	private List<Item> getItems() throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			return itemSvc.list(paginationFilter);
		}
		
		return itemSvc.searchOnItems(paginationFilter);
	}
}
