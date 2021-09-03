package report;

import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import action.model.ItemSumWrapper;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.ItemPaginationFilter;

public class ItemReport {

	public void sendPdfItemReport(ItemPaginationFilter itemPaginationFilter,
			Collection<ItemSumWrapper> itemSumWrapperList, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "home_item_report");
			Map<String, Object> parameters = new HashMap<>();
			
			parameters.put("startDate", DateConvert.parseLocalDateToString(itemPaginationFilter.getStartDate(), "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(itemPaginationFilter.getEndDate(), "dd/MM/yyyy") );
			
			report.exportReportToPdf(parameters, itemSumWrapperList, out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
	
	public void sendXlsItemReport(ItemPaginationFilter itemPaginationFilter,
			Collection<ItemSumWrapper> itemSumWrapperList, OutputStream out, Path reportsPath) throws ReportException{
		try {
			Report report = ReportFactory.getInstance().getReport(reportsPath, "home_item_report");
			Map<String, Object> parameters = new HashMap<>();
			
			
			parameters.put("startDate", DateConvert.parseLocalDateToString(itemPaginationFilter.getStartDate(), "dd/MM/yyyy") );
			parameters.put("endDate",DateConvert.parseLocalDateToString(itemPaginationFilter.getEndDate(), "dd/MM/yyyy") );
			
			
			report.exportReportToXls(parameters, itemSumWrapperList, out);
			
		} catch (ServiceException | ConvertException e) {
			e.printStackTrace();
			throw new ReportException(e);
		}
	}
}
