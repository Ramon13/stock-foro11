package action.restrict.sub_category;

import java.util.List;

import com.google.gson.Gson;

import br.com.javamon.action.Action;
import br.com.javamon.convert.NumberConvert;
import entity.SubCategory;
import service.SubCategoryService;

/**
 * @author ramon
 *	This action loads and return, as json, a list of SubCategory
 */
public class ListByCategory extends Action{

	@Override
	public void process() throws Exception {
		
		//If an invalid parameter is passed, returns nothing to client
		try {
			String categoryId = getRequest().getParameter("category");
			
			List<SubCategory> subCategories = getServiceFactory()
					.getService(SubCategoryService.class)
					.listByCategory(NumberConvert.stringToLong(categoryId));
			
			//serialize the list and sends to client with 200 HTTP code
			responseToClient(200, new Gson().toJson(subCategories));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	
}
