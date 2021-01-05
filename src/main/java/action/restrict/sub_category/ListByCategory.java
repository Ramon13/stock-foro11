package action.restrict.sub_category;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
			
			List<JsonObject> ob = new ArrayList<JsonObject>();
			
			for (SubCategory sc : subCategories) {
				JsonObject job = new JsonObject();
				job.addProperty("id", sc.getId());
				job.addProperty("name", sc.getName());
				ob.add(job);
				
			}
			//serialize the list and sends to client with 200 HTTP code
			responseToClient(200, new Gson().toJson(ob));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	
}
