package action.restrict.stock_forecast;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.ItemStockForecast;
import entity.Item;
import report.ReportException;
import report.ReportType;
import report.StockForecastReport;
import service.ItemService;
import service.ItemStockForecastService;

public class ListItems extends ApplicationAction {
	
	@Override
	public void processAction() throws Exception {
		if (!StringUtils.isBlank(getRequest().getParameter("reportType"))) {
			sendReport();
			return;
		}
		
		putContentOnRequest();
		
		foward("/restrict/stock-forecast.jsp");
	}
	
	private void putContentOnRequest() throws ServiceException, ConvertException {
		List<ItemStockForecast> itemStockForecasts = getItemStockForecasts();
		
		LocalDate startDate = getStartDate();
		LocalDate endDate = getEndDate();
		
		getRequest().setAttribute("startDate", startDate);
		getRequest().setAttribute("endDate", endDate);
		getRequest().setAttribute("itemStockForecasts", itemStockForecasts);
	}
	
	private List<ItemStockForecast> getItemStockForecasts() throws ServiceException, ConvertException{
		ItemStockForecastService itemStockForecastSvc = getServiceFactory().getService(ItemStockForecastService.class);
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		List<Item> items;
		List<ItemStockForecast> itemStockForecasts = new ArrayList<>(0);
		
		
		paginationFilter.setMaxResults(null);
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			items = itemSvc.list(paginationFilter);
		
		}else {
			items = itemSvc.searchOnItems(paginationFilter);
		}
		
		for (Item item : items) {
			itemStockForecasts.add(new ItemStockForecast(item));
		}
		
		itemStockForecastSvc.setOrderAverages(itemStockForecasts, getStartDate(), getEndDate());
		sortItemStockForecasts(itemStockForecasts);
		
		return itemStockForecasts;
	}
	
	private LocalDate getStartDate() throws ConvertException {
		String paramDate = null;
		if (!StringUtils.isAllBlank((paramDate = getRequest().getParameter("startDate"))) ) {
			return DateConvert.stringToLocalDate(paramDate, "yyyy-MM-dd");
		}
		
		return LocalDate.now();
	}
	
	private LocalDate getEndDate() throws ConvertException {
		String paramDate = null;
		if (!StringUtils.isAllBlank((paramDate = getRequest().getParameter("endDate"))) ) {
			return DateConvert.stringToLocalDate(paramDate, "yyyy-MM-dd");
		}
		
		return LocalDate.now().plusYears(1);
	}
	
	private void sortItemStockForecasts(List<ItemStockForecast> itemStockForecasts) throws ServiceException{
		if (!StringUtils.isBlank(paginationFilter.getAppSortProperty())) {
			getServiceFactory().getService(ItemStockForecastService.class).sortItemStockForecasts(paginationFilter, itemStockForecasts);
		}
	}
	
	private void sendReport() throws ServiceException, ConvertException, ReportException, IOException {
		List<ItemStockForecast> itemStockForecasts = getItemStockForecasts();
		LocalDate startDate = getStartDate();
		LocalDate endDate = getEndDate();
		ServletOutputStream out = getResponse().getOutputStream();
		Path reportsPath = Paths.get(ActionUtil.getReportsPath(getRequest()));
		ReportType reportType = ReportType.of(getRequest().getParameter("reportType"));
		StockForecastReport stockForecastReport = new StockForecastReport();
		
		getResponse().setHeader("Content-disposition", String.format("inline; filename=relatorio-previsao-estoque.%s", reportType.getValue()));
		if (reportType == ReportType.PDF) {
			getResponse().setContentType("application/pdf");	
			stockForecastReport.sendPdfReport(itemStockForecasts, startDate, endDate, out, reportsPath);
		
		}else if (reportType == ReportType.XLS) {
			getResponse().setContentType("application/xls");
			stockForecastReport.sendXlsReport(itemStockForecasts, startDate, endDate, out, reportsPath);
		}
	}
}
