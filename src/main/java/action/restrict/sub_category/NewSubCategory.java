package action.restrict.sub_category;

import java.util.List;

import br.com.javamon.action.Action;
import entity.Category;
import service.CategoryService;

public class NewSubCategory extends Action {

	@Override
	public void process() throws Exception {
		List<Category> categories = getServiceFactory().getService(CategoryService.class).list();
		getRequest().setAttribute("categories", categories);
		foward("/restrict/new-subcategory-ajax.jsp");
	}

}
