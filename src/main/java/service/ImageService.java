package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.fileupload.FileItem;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import dao.ImageDAO;
import domain.util.ExceptionMessageUtil;
import domain.util.FileType;
import entity.Image;
import entity.Item;

public class ImageService extends ApplicationService<Image, ImageDAO>{

	public ImageService() {
		super(ImageDAO.class);
	}

	public final long MAX_FILE_SIZE = 500 * 1024;
	
	public Long saveImages(Item item, Path imagePath) throws ServiceException{
		try {
			Image image;
			Long mainImageId = null;
			
			for (FileItem fi : item.getFormImages()) {
				image = new Image();
				getDaoFactory().getDAO(ImageDAO.class).save(image);
				
				image.setName(String.format("%04d.%s", image.getId(), FileType.of(fi.getContentType()).getExtension()));
				
				Path p = imagePath.resolve(image.getName());
				fi.write(Files.createFile(p).toFile());
				
				image.setItem(item);
				mainImageId = image.getId();
			}
			
			return mainImageId;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(ExceptionMessageUtil.DAO_ERR_SAVE);
		}
	}
	
	public void removeImage(Path imagePath, Item item, Image...images) throws ServiceException{
		try {
			for (Image image : images) {
				imagePath = Paths.get(imagePath.toString(), item.getId().toString(), image.getName());
				Files.delete(imagePath);
				getDAO().delete(image);
			}
		} catch (DAOException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public String fixImageName(Image image, Path imagesPath) throws ServiceException, ValidationException{
		try {
			Path p;
			String name = null;
			File file = null;
			
			for (FileType type : FileType.values()) {
				p = Paths.get(imagesPath.toString(), String.format("%d.%s", image.getId(), type.getExtension()));
				
				if ((file = p.toFile()).exists()) {
					name = String.format("%04d.%s", image.getId(), type.getExtension());
					break;
				}
			}
			
			if (name == null) {
				name = String.format("%04d.%s", image.getId(), FileType.WEBP.getExtension());
				file = Paths.get(imagesPath.toString(), String.format("%d", image.getId())).toFile();
			}
			
			image.setName(name);
			getDAO().update(image);
			
			file.renameTo(Paths.get(imagesPath.toString(), name).toFile());
			return name;
			
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
