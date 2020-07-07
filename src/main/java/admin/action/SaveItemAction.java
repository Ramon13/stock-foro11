package admin.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import br.com.javamon.convert.NumberConvert;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.Item;
import entity.Packet;
import entity.SubCategory;
import service.CategoryService;
import service.ItemService;
import service.PacketService;

public class SaveItemAction extends AdminAction{

	@Override
	public void processAction() throws Exception {		
		//If is a form submit request
		if (hasParameters()) {
			Item item = validateFields();
			
			try {
				getServiceFactory().getService(ItemService.class).save(item);
				responseToClient(200, new Gson()
						.toJson(new FormValidateJSON(null, ValidationMessageUtil.SAVE_ITEM_SUCCESS)));
			} catch (ValidationException e) {
				getRequest().setAttribute("formValidationList", Arrays.asList(new FormValidateJSON("igeneral", e.getMessage())));
				throw new ValidationException();
			}
		}
		else {
			getRequest().setAttribute("packets", getServiceFactory().getService(PacketService.class).list());
			getRequest().setAttribute("categories", getServiceFactory().getService(CategoryService.class).list());
			foward("/restrict/saveItem.jsp");
		}
	}
		
	private boolean hasParameters() {
		return getRequest().getParameter("save") != null;
	}
	
	private Item validateFields() throws ValidationException{
		Item item = new Item();
		List<FormValidateJSON> fvJSONList = new ArrayList<FormValidateJSON>();
		
		String itemName = getRequest().getParameter("iname");
		String packetId = getRequest().getParameter("ipacket");
		String categoryId = getRequest().getParameter("icategory");
		String subCategoryId = getRequest().getParameter("isubCategory");
		
		if (StringValidator.isEmpty(itemName))
			fvJSONList.add(new FormValidateJSON("iname", ValidationMessageUtil.EMPTY_ITEM_NAME));
		
		if (!StringValidator.isValidLen(255, itemName))
			fvJSONList.add(new FormValidateJSON("iname", ValidationMessageUtil.ITEM_MAX_LEN));
		
		if (!StringValidator.isValidLongParse(packetId))
			fvJSONList.add(new FormValidateJSON("ipacket", ValidationMessageUtil.INVALID_PACKET_ID));
		
		if (!StringValidator.isValidLongParse(categoryId))
			fvJSONList.add(new FormValidateJSON("icategory", ValidationMessageUtil.INVALID_CATEGORY_ID));
		
		if (!StringValidator.isValidLongParse(subCategoryId))
			fvJSONList.add(new FormValidateJSON("isubCategory", ValidationMessageUtil.INVALID_SUBCATEGORY_ID));
		
		if (fvJSONList.size() != 0) {
			getRequest().setAttribute("formValidationList", fvJSONList);
			throw new ValidationException();
		}
		
		item.setName(itemName);
		item.setPacket(new Packet(NumberConvert.stringToLong(packetId)));
		item.setCategory(new Category(NumberConvert.stringToLong(categoryId)));
		item.setSubCategory(new SubCategory(NumberConvert.stringToLong(subCategoryId)));
		
		return item;
	}
}
