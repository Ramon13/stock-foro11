package action.restrict.item;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import action.ApplicationAction;
import entity.Item;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import service.ItemService;

public class Print extends ApplicationAction {

	@Override
	public void processAction() throws Exception {
		
		String sourceFileName =getRequest()
				.getServletContext()
				.getResource("/WEB-INF/classes/jrreport.jrxml").getPath();
		String compiledFile = "jrreport.jasper";
		
		String printFileName = null;
		
		List<Item> dataList = getServiceFactory().getService(ItemService.class).list();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "Controle De Estoque - Material De Consumo");
		parameters.put("Author", "Almoxarifado Do Foro 11ª CJM");
		
		getResponse().setContentType("application/pdf;charset=UTF-8");
		getResponse().addHeader("Content-Disposition", "inline; filename=" + "cities.pdf");
		
		try {
			JasperCompileManager.compileReportToFile(sourceFileName, compiledFile);
			printFileName = JasperFillManager.fillReportToFile(compiledFile, parameters, beanColDataSource);
			
			if (printFileName != null) {
				JasperExportManager.exportReportToPdfStream(new FileInputStream(new File(printFileName)),
						getResponse().getOutputStream());
//				JRXlsExporter jrXlsExporter = new JRXlsExporter();
//				jrXlsExporter.setParameter(JRXlsExporterParameter.INPUT_FILE_NAME, printFileName);
//				jrXlsExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,
//						"/home/ramon/tmp/sample_report.xsl");
//				jrXlsExporter.exportReport();
				// JasperPrintManager.printReport(printFileName, true);
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}
