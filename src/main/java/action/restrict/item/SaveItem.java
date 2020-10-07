package action.restrict.item;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import action.ApplicationAction;
import action.FormValidateJSON;
import br.com.javamon.convert.NumberConvert;
import br.com.javamon.convert.StringConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.validation.StringValidator;
import domain.util.ValidationMessageUtil;
import entity.Category;
import entity.Item;
import entity.Packet;
import entity.SubCategory;
import service.ImageService;
import service.ItemService;

public class SaveItem extends ApplicationAction{

	private List<FileItem> fileItems;
	
	//TODO solve URL rewriting problems
	@Override
	public void processAction() throws Exception {			
		this.fileItems = getMultipartFormData();
		
		Item item = validateFields();
		item.setMultipartFiles(fileItems);
		Path imagePath = Paths.get(getRequest()
				.getServletContext()
				.getInitParameter("itemImagePath"));
		getServiceFactory().getService(ItemService.class).save(item, imagePath);
		
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		responseToClient(200, gson.toJson(item));
	}
		
	private Item validateFields() throws UnsupportedEncodingException, SQLException, ValidationException, ConvertException{
		Item item = new Item();
		
		String itemId = getParameterValue("id");
		String itemName = getParameterValue("name");
		String packetId = getParameterValue("packet");
		String categoryId = getParameterValue("category");
		String subCategoryId = getParameterValue("subCategory");
		String description = getParameterValue("description");
		
		if (!StringUtils.isBlank(itemId) && !StringValidator.isValidLongParse(itemId)) {
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.INVALID_ITEM_ID));
		}
		if (StringValidator.isEmpty(itemName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.EMPTY_ITEM_NAME));
		
		else if (!StringValidator.isValidLen(255, itemName))
			formValidationList.add(
					new FormValidateJSON("name", ValidationMessageUtil.ITEM_MAX_LEN));
		
		if (!StringValidator.isValidLen(1000, description))
			formValidationList.add(
					new FormValidateJSON("description", ValidationMessageUtil.ITEM_DESCRIPTION_MAX_LEN));
		
		if (!StringValidator.isValidLongParse(packetId))
			formValidationList.add(
					new FormValidateJSON("packet", ValidationMessageUtil.INVALID_PACKET_ID));
		
		if (!StringValidator.isValidLongParse(categoryId))
			formValidationList.add(
					new FormValidateJSON("category", ValidationMessageUtil.INVALID_CATEGORY_ID));
		
		if (!StringValidator.isValidLongParse(subCategoryId))
			formValidationList.add(
					new FormValidateJSON("subCategory", ValidationMessageUtil.INVALID_SUBCATEGORY_ID));
		
		
		if (!formValidationList.isEmpty()) {
			getRequest().setAttribute("formValidationList", formValidationList);
			throw new ValidationException();
		}
		
		if (!StringUtils.isBlank(itemId))
			item.setId(StringConvert.stringToLong(itemId));
		item.setName(itemName);
		item.setPacket(new Packet(NumberConvert.stringToLong(packetId)));
		item.setCategory(new Category(NumberConvert.stringToLong(categoryId)));
		item.setSubCategory(new SubCategory(NumberConvert.stringToLong(subCategoryId)));
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
			String imagePath = getRequest()
					.getServletContext()
					.getInitParameter("itemImagePath");
			
			
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
