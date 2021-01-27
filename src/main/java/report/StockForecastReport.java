package report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.ItemStockForecast;

public class StockForecastReport {

	public void sendPdfReport(List<ItemStockForecast> itemStockForecasts, LocalDate startDate, LocalDate endDate, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "forecast_stock_report");
			Map<String, Object> parameters = new HashMap<>();
			
			
			parameters.put("startDate", DateConvert.parseLocalDateToString(startDate, "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(endDate, "dd/MM/yyyy") );
			
			report.exportReportToPdf(parameters, itemStockForecasts, out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
	
	public void sendXlsReport(List<ItemStockForecast> itemStockForecasts, LocalDate startDate, LocalDate endDate, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "forecast_stock_report");
			Map<String, Object> parameters = new HashMap<>();
			
			
			parameters.put("startDate", DateConvert.parseLocalDateToString(startDate, "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(endDate, "dd/MM/yyyy") );
			
			report.exportReportToXls(parameters, itemStockForecasts, out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
}
