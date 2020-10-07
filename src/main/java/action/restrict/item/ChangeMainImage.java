package action.restrict.item;

import action.ApplicationAction;
import br.com.javamon.convert.StringConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ValidationException;
import domain.util.ValidationMessageUtil;
import service.ItemService;

public class ChangeMainImage extends ApplicationAction{

	@Override
	public void processAction() throws Exception {		
		try {
			Long itemId = StringConvert.stringToLong(getRequest().getParameter("item"));
			Long imageId = StringConvert.stringToLong(getRequest().getParameter("image"));
			
			getServiceFactory().getService(ItemService.class).changeMainImage(itemId, imageId);
		
		} catch (ConvertException e) {
			throw new ValidationException(ValidationMessageUtil.ID_NOT_FOUND);
		}
		
	}

}
