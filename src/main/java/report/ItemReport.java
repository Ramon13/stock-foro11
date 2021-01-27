package report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.ItemLocales;
import entity.Locale;

public class ItemReport {

	public void sendPdfItemReport(List<Locale> locales, ItemLocales itemLocales, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "home_item_report");
			Map<String, Object> parameters = new HashMap<>();
			
			parameters.put("locales", locales);
			parameters.put("startDate", DateConvert.parseLocalDateToString(itemLocales.getFilter().getStartDate(), "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(itemLocales.getFilter().getEndDate(), "dd/MM/yyyy") );
			
			report.exportReportToPdf(parameters, itemLocales.getItemLocales(), out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
	
	public void sendXlsItemReport(List<Locale> locales, ItemLocales itemLocales, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "home_item_report");
			Map<String, Object> parameters = new HashMap<>();
			
			parameters.put("locales", locales);
			parameters.put("startDate", DateConvert.parseLocalDateToString(itemLocales.getFilter().getStartDate(), "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(itemLocales.getFilter().getEndDate(), "dd/MM/yyyy") );
			
			report.exportReportToXls(parameters, itemLocales.getItemLocales(), out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
}
