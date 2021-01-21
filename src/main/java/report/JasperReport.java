package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class JasperReport implements Report{

	private String reportFileName;
	private Path reportsPath;
	
	public JasperReport(Path reportsPath, String reportFileName) {
		this.reportFileName = reportFileName;
		this.reportsPath = reportsPath;
	}
	
	@Override
	public void exportReportToPdf(Map<String, Object> parameters, Collection<?> datasource, OutputStream out) {
		Path templatePath = Paths.get(reportsPath.toString(), reportFileName + ".jrxml");
		Path compiledTemplatePath = Paths.get(reportsPath.toString(), reportFileName + ".jasper");
		
		JRBeanCollectionDataSource JRDataSource = new JRBeanCollectionDataSource(datasource);
		
		try {
			if (!Files.exists(compiledTemplatePath)) {
				Files.createFile(compiledTemplatePath);
				JasperCompileManager.compileReportToFile(templatePath.toString(), compiledTemplatePath.toString());
			}
			
			String printFileName = JasperFillManager.fillReportToFile(compiledTemplatePath.toString(), parameters, JRDataSource);
				        
			JasperExportManager.exportReportToPdfStream(new FileInputStream(new File(printFileName)), out);
		}catch(JRException | IOException e) {
			e.printStackTrace();
		}
	}

}
