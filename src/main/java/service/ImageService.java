package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.service.Service;
import dao.ImageDAO;
import domain.util.ExceptionMessageUtil;
import entity.Image;
import entity.Item;

public class ImageService extends Service{

	public final long MAX_FILE_SIZE = 500 * 1024;
	
	public Long saveImages(Item item, Path imagePath) throws ServiceException{
		try {
			Image image;
			Long mainImageId = null;
			for (FileItem fi : item.getMultipartFiles()) {
				if (!fi.isFormField() && fi.getFieldName().equalsIgnoreCase("images")
						&& !StringUtils.isEmpty(fi.getName())) {
					image = new Image();
					image.setItem(item);
					getDaoFactory().getDAO(ImageDAO.class).save(image);
					
					Path p = imagePath.resolve(image.getId().toString());
					fi.write(Files.createFile(p).toFile());
					mainImageId = image.getId();
				}
			}
			
			return mainImageId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public Image findById(Long id) throws ServiceException{
		try {
			return getDaoFactory().getDAO(ImageDAO.class).load(id);
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_LOAD);
		}
	}
	
	public void loadImageStream(Path systemImgPath, OutputStream out) throws ServiceException{
		try (FileInputStream fin = new FileInputStream(systemImgPath.toFile())){
			byte[] buffer = new byte[20000];
			while (fin.read(buffer) != -1)
				out.write(buffer);
		
		}catch (IOException e) {
		
			e.printStackTrace();
		
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
