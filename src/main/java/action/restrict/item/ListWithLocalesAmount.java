package action.restrict.item;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.AdminHomeDateType;
import action.ApplicationAction;
import br.com.javamon.convert.DateConvert;
import br.com.javamon.exception.ConvertException;
import br.com.javamon.exception.ServiceException;
import domain.DateUtil;
import domain.ItemLocaleFilter;
import domain.ItemLocales;
import entity.Item;
import entity.Locale;
import service.ItemService;
import service.LocaleService;

public class ListWithLocalesAmount extends ApplicationAction{

	@Override
	public void processAction() throws Exception {
		putContentOnRequest();
		
		foward("/restrict/home.jsp");
	}
	
	private void putContentOnRequest() throws Exception {
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		List<Item> items = getItems();
		ItemLocales itemLocalesFromPreviousYear = getItemLocalesFromPreviousYear();
		ItemLocales itemLocalesBetweenDates = getItemLocalesBetweenDates();
		itemSvc.sumLocales(items, itemLocalesFromPreviousYear, itemLocalesBetweenDates);
		
		getRequest().setAttribute("lastYear", DateUtil.firstDayOfPreviousYear().getYear());
		getRequest().setAttribute("locales", locales);
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("primaryDate", getPrimaryDate());
		getRequest().setAttribute("secondDate", getSecondDate());
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
		ItemLocaleFilter filter = new ItemLocaleFilter(
				DateUtil.firstDayOfPreviousYear(), 
				DateUtil.firstDayOfCurrentYear());
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
	}
	
	private ItemLocales getItemLocalesBetweenDates() throws ConvertException {
		ItemLocaleFilter filter = new ItemLocaleFilter(getPrimaryDate(), getSecondDate(), true);
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
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
}
