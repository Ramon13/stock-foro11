package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class JasperReport implements Report{

	private String reportFileName;
	private Path reportsPath;
	
	public JasperReport(Path reportsPath, String reportFileName) {
		this.reportFileName = reportFileName;
		this.reportsPath = reportsPath;
	}
	
	@Override
	public void exportReportToPdf(Map<String, Object> parameters, Collection<?> datasource, OutputStream out) {
		try {
			URI templateURI = this.getClass().getResource(reportFileName + ".jrxml").toURI();
			String templatePath = Paths.get(templateURI).toString();
			Path compiledTemplatePath = Paths.get(reportsPath.toString(), reportFileName + ".jasper");
			
			JRBeanCollectionDataSource JRDataSource = new JRBeanCollectionDataSource(datasource);
			/**
			if (!Files.exists(compiledTemplatePath)) {
				Files.createFile(compiledTemplatePath);
				JasperCompileManager.compileReportToFile(templatePath.toString(), compiledTemplatePath.toString());
			}
			**/
			
			JasperCompileManager.compileReportToFile(templatePath, compiledTemplatePath.toString());
			
			String printFileName = JasperFillManager.fillReportToFile(compiledTemplatePath.toString(), parameters, JRDataSource);
			JasperExportManager.exportReportToPdfStream(new FileInputStream(new File(printFileName)), out);
			
		}catch(JRException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void exportReportToXls(Map<String, Object> parameters, Collection<?> datasource, OutputStream out) {
		try {
			URI templateURI = this.getClass().getResource(reportFileName + ".jrxml").toURI();
			String templatePath = Paths.get(templateURI).toString();
			Path compiledTemplatePath = Paths.get(reportsPath.toString(), reportFileName + ".jasper");
			
			JRBeanCollectionDataSource JRDataSource = new JRBeanCollectionDataSource(datasource);
			
			/**
			if (!Files.exists(compiledTemplatePath)) {
				Files.createFile(compiledTemplatePath);
				JasperCompileManager.compileReportToFile(templatePath.toString(), compiledTemplatePath.toString());
			}
			**/
			
			JasperCompileManager.compileReportToFile(templatePath, compiledTemplatePath.toString());
			
			String printFileName = JasperFillManager.fillReportToFile(compiledTemplatePath.toString(), parameters, JRDataSource);
			JRXlsExporter xlsExporter = new JRXlsExporter();
			xlsExporter.setParameter(JRExporterParameter.INPUT_FILE_NAME, printFileName);
			xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
			xlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		    xlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		    xlsExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		    
			xlsExporter.exportReport();
			
		}catch(JRException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
