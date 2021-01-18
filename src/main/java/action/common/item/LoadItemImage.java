package action.common.item;

import java.nio.file.Path;
import java.nio.file.Paths;

import action.ActionUtil;
import br.com.javamon.action.Action;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import entity.Image;
import entity.Item;
import service.ImageService;
import service.ItemService;

public class LoadItemImage extends Action {

	@Override
	public void process() throws Exception {

		try {
			String itemId = getRequest().getParameter("item");
			String imageId = getRequest().getParameter("image");
			
			Item item = getServiceFactory().getService(ItemService.class).validateAndFindById(itemId);
			Image image = getServiceFactory().getService(ImageService.class).validateAndFindById(imageId);
			
			Path imgPath = getAppImagesPath(item, image);
			getResponse().setContentType("image/*");

			getServiceFactory().getService(ImageService.class).loadImageStream(imgPath,
					getResponse().getOutputStream());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private Path getAppImagesPath(Item item, Image image) throws ServiceException, ValidationException{
		return Paths.get(ActionUtil.getimagesPath(getRequest()), item.getId().toString(), image.getName());
	}
}
