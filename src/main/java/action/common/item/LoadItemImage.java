package action.common.item;

import java.nio.file.Path;
import java.nio.file.Paths;

import action.ActionUtil;
import br.com.javamon.action.Action;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ValidationException;
import domain.ValidImageExtensions;
import domain.util.ValidationMessageUtil;
import entity.Item;
import service.ImageService;
import service.ItemService;

public class LoadItemImage extends Action {

	@Override
	public void process() throws Exception {

		try {
			Long itemId = NumberConvert.stringToLong(getRequest().getParameter("item"));
			Long imageId = NumberConvert.stringToLong(getRequest().getParameter("image"));

			Item item = getServiceFactory().getService(ItemService.class).findById(itemId);

			Path imgPath = getAppImagesPath(item, imageId);
			getResponse().setContentType("image/*");

			getServiceFactory().getService(ImageService.class).loadImageStream(imgPath,
					getResponse().getOutputStream());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private Path getAppImagesPath(Item item, Long imageId) throws ValidationException {
		Path path;
		for (ValidImageExtensions vie : ValidImageExtensions.values()) {
			path = Paths.get(ActionUtil.getimagesPath(getRequest()), item.getId().toString(), imageId.toString() + vie.getValue());
			if (path.toFile().exists())
				return path;
		}
		
		throw new ValidationException(ValidationMessageUtil.INVALID_IMAGE_ID);
	}
}
