package action.restrict.item;

import action.ActionUtil;
import br.com.javamon.action.Action;
import br.com.javamon.convert.StringConvert;
import br.com.javamon.exception.ConvertException;
import entity.Item;
import service.CategoryService;
import service.ItemService;
import service.PacketService;
import service.SubCategoryService;

public class EditItem extends Action{

	@Override
	public void process() throws Exception {
		
		try {
			String itemId = getRequest().getParameter("item");
			Item item = getServiceFactory()
					.getService(ItemService.class)
					.validateAndFindById(itemId);
					
			getRequest().setAttribute("item", item);
			ActionUtil.setEditMode(getRequest(), true);
			
			ActionUtil.addPacketsToRequest(getRequest(),
					getServiceFactory().getService(PacketService.class));
			
			ActionUtil.addCategoryToRequest(getRequest(), 
					getServiceFactory().getService(CategoryService.class));
			
			getRequest().setAttribute("subCategories", 
					getServiceFactory().
					getService(SubCategoryService.class)
					.listByCategory(item.getCategory().getId()));

			foward("/restrict/save-edit-item.jsp");
		} catch (ConvertException e) {
			e.printStackTrace();
		}

	}

}
