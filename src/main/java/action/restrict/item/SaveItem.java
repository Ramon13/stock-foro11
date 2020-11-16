package action.restrict.item;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import action.ActionUtil;
import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.Item;
import entity.Packet;
import entity.SubCategory;
import service.CategoryService;
import service.ImageService;
import service.ItemService;
import service.PacketService;
import service.SubCategoryService;

public class SaveItem extends ApplicationAction{

	private List<FileItem> fileItems;
	private ItemService itemSvc;
	
	//TODO solve URL rewriting problems
	@Override
	public void processAction() throws Exception {
		itemSvc = getServiceFactory().getService(ItemService.class);
		this.fileItems = getMultipartFormData();
		
		Item item = validateFields();
		item.setMultipartFiles(fileItems);
		
		Path imagePath = Paths.get(ActionUtil.getimagesPath(getRequest()));
		
		if (item.getId() != null)
			itemSvc.edit(item, imagePath);
		else
			itemSvc.save(item, imagePath);
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		responseToClient(200, gson.toJson(item));
	}
		
	private Item validateFields() throws ValidationException, ServiceException, UnsupportedEncodingException, SerialException, SQLException{
		Item item;
		
		try {
			item = itemSvc.validateAndFindById(getParameterValue("item"));
		} catch (ValidationException | ServiceException e1) {
			item = new Item();
		}
		String itemName = getParameterValue("name");
		String packetId = getParameterValue("packet");
		String categoryId = getParameterValue("category");
		String subCategoryId = getParameterValue("subCategory");
		String description = getParameterValue("description");
		
		if (StringValidator.isEmpty(itemName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.EMPTY_ITEM_NAME));
		
		else if (!StringValidator.isValidLen(255, itemName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.ITEM_MAX_LEN));
		
		else if (!itemSvc.isValidNewItemName(itemName, item))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.ITEM_NAME_EXISTS));
			
		if (!StringValidator.isValidLen(1000, description))
			formValidationList.add(
					new FormValidateJSON("description", ValidationMessageUtil.ITEM_DESCRIPTION_MAX_LEN));
		
		try {
			Packet packet = getServiceFactory()
					.getService(PacketService.class)
					.validateAndFindById(packetId);
			item.setPacket(packet);
		} catch (ValidationException e) {
			formValidationList.add(
					new FormValidateJSON("packet", ValidationMessageUtil.INVALID_PACKET_ID));
		}
		
		try {
			Category category = getServiceFactory()
					.getService(CategoryService.class)
					.validateAndFindById(categoryId);
			item.setCategory(category);
		} catch (ValidationException e) {
			formValidationList.add(
					new FormValidateJSON("category", ValidationMessageUtil.INVALID_CATEGORY_ID));
		}
			
		try {
			SubCategory subCategory = getServiceFactory()
					.getService(SubCategoryService.class)
					.validateAndFindById(subCategoryId);
			item.setSubCategory(subCategory);
		} catch (ValidationException e) {
			formValidationList.add(
					new FormValidateJSON("subCategory", ValidationMessageUtil.INVALID_SUBCATEGORY_ID));
		}
		
		
		if (!formValidationList.isEmpty())
			throw new ValidationException();
		
		item.setName(itemName);
		item.setDescription(new SerialBlob(description.getBytes("ISO-8859-1")));
		
		return item;
	}
	
	private String getParameterValue(String key) throws UnsupportedEncodingException {
		for (FileItem fi : fileItems) 
			if (fi.isFormField() && fi.getFieldName().equals(key))
		        return new String(fi.getString().getBytes("ISO-8859-1"),"UTF-8");
	
		return "";
	}
	
	private List<FileItem> getMultipartFormData() throws FileUploadException, ValidationException, ServiceException{
		ImageService imgService = getServiceFactory().getService(ImageService.class);
		try {
			String imagePath = ActionUtil.getimagesPath(getRequest());
			
			boolean isMultipart = ServletFileUpload.isMultipartContent(getRequest());
			if (!isMultipart)
				throw new ValidationException("Error to process images. Incorrect enctype");
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			factory.setSizeThreshold( (int) imgService.MAX_FILE_SIZE);
			factory.setRepository(new File(imagePath));
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(imgService.MAX_FILE_SIZE);
			return upload.parseRequest(getRequest());
		} catch (SizeLimitExceededException e) {
			e.printStackTrace();
			throw new ValidationException(
					String.format("Tamanho limite da imagem excedido. Tamanho máximo de imagem: %.2f MB" , (imgService.MAX_FILE_SIZE / 1024 / 1024.0)));
		}
	}
}
