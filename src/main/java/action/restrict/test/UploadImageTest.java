package action.restrict.test;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.com.javamon.action.Action;

public class UploadImageTest extends Action{

	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file;
	
	@Override
	public void process() throws Exception {
		filePath = getRequest()
				.getServletContext()
				.getInitParameter("file-upload");
		
		isMultipart = ServletFileUpload.isMultipartContent(getRequest());
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(maxMemSize);
		factory.setRepository(new File("/tmp"));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			List<FileItem> fileItems = upload.parseRequest(getRequest());
			Iterator<FileItem> i = fileItems.iterator();
			
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				
				if (!fi.isFormField()) {
					String fieldName = fi.getFieldName();
					String fileName = fi.getName();
					String contentType = fi.getContentType();
					boolean isInMemory = fi.isInMemory();
					long sizeInBytes = fi.getSize();
					
					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(
								filePath + fileName.substring(fileName.lastIndexOf("\\")));
					}else {
						file = new File(
								filePath + fileName.substring(fileName.lastIndexOf("\\")+1));
					}
					
					fi.write(file);
					System.out.println("Uploaded Filename: " + file.getAbsolutePath());
				}
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
