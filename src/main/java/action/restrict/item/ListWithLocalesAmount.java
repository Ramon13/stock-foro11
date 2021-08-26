package action.restrict.item;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.DateUtil;
import domain.ItemLocales;
import domain.ItemPaginationFilter;
import entity.Item;
import entity.Locale;
import report.ItemReport;
import report.ReportException;
import report.ReportType;
import service.ItemService;
import service.LocaleService;

public class ListWithLocalesAmount extends ApplicationAction{

	private ItemService itemSvc;
	
	@Override
	public void processAction() throws Exception {
		itemSvc = getServiceFactory().getService(ItemService.class);
		
		if (!StringUtils.isBlank(getRequest().getParameter("reportType"))) {
			sendReport();
			return;
		}
		
		putContentOnRequest();
		
		foward("/restrict/home.jsp");
	}
	
	private void putContentOnRequest() throws Exception {
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		
		List<Item> items = getItems();
		
		addAmountByLocaleToRequest(items);
		
		getRequest().setAttribute("lastYear", DateUtil.firstDayOfPreviousYear().getYear());
		getRequest().setAttribute("locales", locales);
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("primaryDate", getPrimaryDate());
		getRequest().setAttribute("secondDate", getSecondDate());
		
	}

	private void addAmountByLocaleToRequest(List<Item> items)
			throws ConvertException, ServiceException {
		ItemLocales itemLocalesFromPreviousYear = getItemLocalesFromPreviousYear();
		ItemLocales itemLocalesBetweenDates = getItemLocalesBetweenDates();
		itemSvc.sumLocales(items, itemLocalesFromPreviousYear, itemLocalesBetweenDates);
		
		getRequest().setAttribute("itemLocaleFromPreviousYear", itemLocalesFromPreviousYear);
		getRequest().setAttribute("itemLocaleBetweenDates", itemLocalesBetweenDates);
	}
	
	private List<Item> getItems() throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			return itemSvc.list(paginationFilter);
		}
		
		return itemSvc.searchOnItems(paginationFilter);
	}
	
	private ItemLocales getItemLocalesFromPreviousYear() {
		ItemPaginationFilter filter = getPreviousYearFilter();
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
	}

	private ItemLocales getItemLocalesBetweenDates() throws ConvertException {
		ItemPaginationFilter filter = getBetweenDatesFilter();
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
	}
	
	private ItemPaginationFilter getPreviousYearFilter() {
		return new ItemPaginationFilter(
				DateUtil.firstDayOfPreviousYear(), 
				DateUtil.firstDayOfCurrentYear());
	}
	
	private ItemPaginationFilter getBetweenDatesFilter() throws ConvertException {
		return new ItemPaginationFilter(getPrimaryDate(), getSecondDate(), true);
	}
	
	private LocalDate getPrimaryDate() throws ConvertException {
		String paramDate = null;
		if (!StringUtils.isAllBlank((paramDate = getRequest().getParameter("primaryDate"))) ) {
			return DateConvert.stringToLocalDate(paramDate, "yyyy-MM-dd");
		}
		
		return LocalDate.of(LocalDate.now().getYear(), 1, 1);
	}
	
	private LocalDate getSecondDate() throws ConvertException {
		String paramDate = null;
		if (!StringUtils.isAllBlank((paramDate = getRequest().getParameter("secondDate"))) ) {
			return DateConvert.stringToLocalDate(paramDate, "yyyy-MM-dd");
		}
		
		return LocalDate.now();
	}
	
	private void sendReport() throws ServiceException, ConvertException, ReportException, IOException {
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		paginationFilter.setMaxResults(null);
		List<Item> items = getItems();
		
		ItemLocales itemLocalesFromPreviousYear = getItemLocalesFromPreviousYear();
		ItemLocales itemLocalesBetweenDates = getItemLocalesBetweenDates();
		itemSvc.sumLocales(items, itemLocalesFromPreviousYear, itemLocalesBetweenDates);
		ReportType reportType = ReportType.of(getRequest().getParameter("reportType"));
		ItemReport itemReport = new ItemReport();
		
		getResponse().setHeader("Content-disposition", String.format("inline; filename=consumo-por-periodo.%s", reportType.getValue()));
		if (reportType == ReportType.PDF) {
			getResponse().setContentType("application/pdf");	
			itemReport.sendPdfItemReport(locales, itemLocalesBetweenDates, getResponse().getOutputStream(), Paths.get(ActionUtil.getReportsPath(getRequest())));
		
		}else if (reportType == ReportType.XLS) {
			getResponse().setContentType("application/xls");
			itemReport.sendXlsItemReport(locales, itemLocalesBetweenDates, getResponse().getOutputStream(), Paths.get(ActionUtil.getReportsPath(getRequest())));
		}
		
	}
}
