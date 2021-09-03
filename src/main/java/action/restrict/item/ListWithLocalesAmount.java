package action.restrict.item;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.ApplicationAction;
import action.model.ItemSumWrapper;
import action.model.SumOrderType;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.DateUtil;
import domain.ItemPaginationFilter;
import domain.OrderStatus;
import entity.Entry;
import entity.EntryItem;
import entity.Item;
import entity.Locale;
import entity.Order;
import entity.OrderItem;
import report.ItemReport;
import report.ReportException;
import report.ReportType;
import service.EntryItemService;
import service.ItemService;
import service.LocaleService;
import service.OrderItemService;

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
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list(7);
		
		List<Item> items = getItems();
		
		getDisplayAmountType(getRequest().getSession());
		Map<Long, ItemSumWrapper> itemSumWrapperMap = getItemAmountSum(items, locales);
		
		getRequest().setAttribute("itemSumWrapperMap", itemSumWrapperMap);
		getRequest().setAttribute("lastYear", DateUtil.firstDayOfPreviousYear().getYear());
		getRequest().setAttribute("locales", locales);
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("primaryDate", getPrimaryDate());
		getRequest().setAttribute("secondDate", getSecondDate());	
	}
	
	private Map<Long, ItemSumWrapper> getItemAmountSum(List<Item> items, List<Locale> locales) throws ServiceException, ConvertException {
		Map<Long, ItemSumWrapper> itemSumWrapperMap = new HashMap<>();
		
		OrderItemService orderItemSvc = getServiceFactory().getService(OrderItemService.class);
		List<OrderItem> orderItems = orderItemSvc.list();
		
		ItemPaginationFilter previousYearFilter = getPreviousYearFilter();
		ItemPaginationFilter betweenDatesFilter = getBetweenDatesFilter();
		
		for (Item item : items) {
			itemSumWrapperMap.put(item.getId(), new ItemSumWrapper(item, locales));	
		}
		
		boolean isPreviousYear, isBetweenDates;
		long itemId;
		int amount;
		Order order;
		Locale locale;
		ItemSumWrapper itemSumWrapper;
		for (OrderItem orderItem : orderItems) {
			order = orderItem.getOrder();
			itemId = orderItem.getItem().getId();
			itemSumWrapper = itemSumWrapperMap.get(itemId);
			
			if (order != null && itemSumWrapper != null && OrderStatus.isFinalizedOrReleased(order)) {
				locale = order.getCustomer().getLocale();
				amount = orderItem.getAmount();
				
				if (DateUtil.isPrevious(order.getReleaseDate(), betweenDatesFilter.getStartDate())) {
					itemSumWrapper.incrementCustomStartStock(amount * -1);
				}
				
				if (DateUtil.isPrevious(order.getReleaseDate(), betweenDatesFilter.getEndDate())) {
					itemSumWrapper.incrementCustomEndStock(amount * -1);
				}
				
				isPreviousYear = DateUtil.isBetweenOrEqual(order.getReleaseDate(),
						previousYearFilter.getStartDate(),
						previousYearFilter.getEndDate());
				
				if (isPreviousYear) {
					itemSumWrapper.incrementPreviousYearSum(amount);
					itemSumWrapper.incremmentPreviousYearLocaleSum(locale.getId(), amount);
				}
				
				isBetweenDates = DateUtil.isBetweenOrEqual(order.getReleaseDate(),
						betweenDatesFilter.getStartDate(), 
						betweenDatesFilter.getEndDate());
				
				if (!isBetweenDates && !isPreviousYear)
					continue;
				
				if (isBetweenDates) {
					itemSumWrapper.incrementBetweenSum(amount);
					itemSumWrapper.incremmentBetweenDatesLocaleSum(locale.getId(), amount);
				}
			}
		}
		
		EntryItemService entryItemSvc = getServiceFactory().getService(EntryItemService.class);
		List<EntryItem> entryItems = entryItemSvc.list();
		Entry entry;
		for (EntryItem entryItem : entryItems) {
			entry = entryItem.getEntry();
			itemId = entryItem.getItem().getId();
			itemSumWrapper = itemSumWrapperMap.get(itemId);
			
			if (entry != null && itemSumWrapper != null) {
				if (DateUtil.isPrevious(entry.getDate(), betweenDatesFilter.getStartDate())) {
					itemSumWrapper.incrementCustomStartStock(entryItem.getAmount());
				}
				
				if (DateUtil.isPrevious(entry.getDate(), betweenDatesFilter.getEndDate())) {
					itemSumWrapper.incrementCustomEndStock(entryItem.getAmount());
				}
			}
		}
		
		return itemSumWrapperMap;
	}
	
	private List<Item> getItems() throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			return itemSvc.list(paginationFilter);
		}
		
		return itemSvc.searchOnItems(paginationFilter);
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
		
		ItemPaginationFilter betweenDatesFilter = getBetweenDatesFilter();
		Map<Long, ItemSumWrapper> itemSumWrapperMap = getItemAmountSum(items, locales);
		
		ReportType reportType = ReportType.of(getRequest().getParameter("reportType"));
		ItemReport itemReport = new ItemReport();
		
		getResponse().setHeader("Content-disposition", String.format("inline; filename=consumo-por-periodo.%s", reportType.getValue()));
		if (reportType == ReportType.PDF) {
			getResponse().setContentType("application/pdf");	
			itemReport.sendPdfItemReport(betweenDatesFilter, itemSumWrapperMap.values(), 
					getResponse().getOutputStream(), Paths.get(ActionUtil.getReportsPath(getRequest())));
		
		}else if (reportType == ReportType.XLS) {
			getResponse().setContentType("application/xls");
			itemReport.sendXlsItemReport(betweenDatesFilter, itemSumWrapperMap.values(), 
					getResponse().getOutputStream(), Paths.get(ActionUtil.getReportsPath(getRequest())));
		}
		
	}
	
	private SumOrderType getDisplayAmountType(HttpSession session) {
		SumOrderType type = SumOrderType.BY_ITEM;
		if (!StringUtils.isAllBlank(getRequest().getParameter("displayAmountType"))) {
			type = SumOrderType.fromString(getRequest().getParameter("displayAmountType"));
		
		}else if ((SumOrderType) session.getAttribute("displayAmountType") != null) {
			type = (SumOrderType) session.getAttribute("displayAmountType");
		}
		
		session.setAttribute("displayAmountType", type);
		return type;
	}
}
