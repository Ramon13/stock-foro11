package domain.issue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import br.com.javamon.exception.DAOException;
import br.com.javamon.exception.ServiceException;
import br.com.javamon.exception.ValidationException;
import br.com.javamon.util.HibernateUtil;
import domain.util.FileType;
import entity.Image;

public class FixImageNames {

	public static void main(String[] args) {
//		Path path = Paths.get("/home/ramon/tmp/app-images/");
//		File file = path.toFile();
//		
//		File[] itemsDir = file.listFiles();
//		
//		for (File itemDir : itemsDir) {
//			for (File image : itemDir.listFiles()) {
//				System.out.println(image.getName());
//			}
//		}
		try {
			HibernateUtil.beginTransaction();
			
			Session session = HibernateUtil.getCurrentSession();
			List<Image> images = session.createQuery("from Image i", Image.class).getResultList();
			
			Path path = Paths.get("/home/ramon/tmp/app-images/");
			for (Image image : images) {
				fixImageName(session, image, path);
			}
			
			HibernateUtil.commitTransaction();
		}catch(Exception e) {
			HibernateUtil.rollbackTransaction();
		}
	}
	
	public static String fixImageName(Session sess, Image image, Path imagesPath) throws ServiceException, ValidationException{
			Path p;
			String name = null;
			File file = null;
			
			for (FileType type : FileType.values()) {
				p = Paths.get(imagesPath.toString(), image.getItem().getId().toString(), String.format("%d.%s", image.getId(), type.getExtension()));
				
				if ((file = p.toFile()).exists()) {
					name = String.format("%04d.%s", image.getId(), type.getExtension());
					break;
				}
			}
			
			
			if (name == null) {
				name = String.format("%04d.%s", image.getId(), FileType.WEBP.getExtension());
				file = Paths.get(imagesPath.toString(), image.getItem().getId().toString(), String.format("%d", image.getId())).toFile();
			}
			
			System.out.println(name);
			
			image.setName(name);
			sess.update(image);
			
			file.renameTo(Paths.get(imagesPath.toString(), image.getItem().getId().toString(), name).toFile());
			return name;
	}
}
