package action.restrict.item;

import br.com.javamon.action.Action;
import service.CategoryService;
import service.PacketService;

public class NewItem extends Action{

	@Override
	public void process() throws Exception {
		getRequest().setAttribute("packets", getServiceFactory().getService(PacketService.class).list());
		getRequest().setAttribute("categories", getServiceFactory().getService(CategoryService.class).list());
		foward("/restrict/save-edit-item.jsp");	
	}

}
