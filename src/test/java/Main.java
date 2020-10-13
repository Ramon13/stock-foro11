import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String sourceFileName = Main.class.getResource("jrreport.jrxml").getPath();
		String compiledFile = "jrreport.jasper";
		
		String printFileName = null;
		DataBeanList DataBeanList = new DataBeanList();
		ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("ReportTitle", "List of Contacts");
		parameters.put("Author", "Prepared By Ramonny  (>:3)>");
		
		try {
			JasperCompileManager.compileReportToFile(sourceFileName, compiledFile);
			printFileName = JasperFillManager.fillReportToFile(compiledFile, parameters, beanColDataSource);
			
			if (printFileName != null) {
				
				try(FileOutputStream fout = new FileOutputStream(new File("/home/ramon/tmp/sample_report.pdf"))){
					JasperExportManager.exportReportToPdfStream(new FileInputStream(new File(printFileName)),
							fout);
				}
				
				//JasperExportManager.exportReportToPdfFile(printFileName, "/home/ramon/tmp/sample_report.pdf");
				
				
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