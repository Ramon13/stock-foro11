package action.restrict.item;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import action.ActionUtil;
import action.AdminHomeDateType;
import action.ApplicationAction;
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
		if (!StringUtils.isAllBlank(getRequest().getParameter("loadHomePage")))
			sendHomePage();
		else
			sendAjaxContent();
	}
	
	private void sendHomePage() throws Exception {
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		getRequest().setAttribute("lastYear", DateUtil.firstDayOfPreviousYear().getYear());
		getRequest().setAttribute("locales", locales);
		foward("/restrict/home.jsp");
	}
	
	private void sendAjaxContent() throws Exception {
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		List<Item> items = getItems();
		ItemLocales itemLocalesFromPreviousYear = getItemLocalesFromPreviousYear();
		ItemLocales itemLocalesBetweenDates = getItemLocalesBetweenDates();
		itemSvc.sumLocales(items, itemLocalesFromPreviousYear, itemLocalesBetweenDates);
		
		getRequest().setAttribute("locales", locales);
		getRequest().setAttribute("items", items);
		getRequest().setAttribute("itemLocaleFromPreviousYear", itemLocalesFromPreviousYear);
		getRequest().setAttribute("itemLocaleBetweenDates", itemLocalesBetweenDates);
		foward("/restrict/home-table-ajax.jsp");
	}
	
	private List<Item> getItems() throws ServiceException{
		ItemService itemSvc = getServiceFactory().getService(ItemService.class);
		
		if (StringUtils.isAllEmpty(paginationFilter.getSearchWord())) {
			return itemSvc.list(paginationFilter);
		}
		
		return itemSvc.searchOnItems(paginationFilter.getSearchWord());
	}
	
	private ItemLocales getItemLocalesFromPreviousYear() {
		ItemLocaleFilter filter = new ItemLocaleFilter(
				DateUtil.firstDayOfPreviousYear(), 
				DateUtil.firstDayOfCurrentYear());
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
	}
	
	private ItemLocales getItemLocalesBetweenDates() {
		ItemLocaleFilter filter = new ItemLocaleFilter(
			ActionUtil.putAdminHomeDateOnSession(AdminHomeDateType.PRIMARY_DATE, null, getRequest().getSession()), 
			ActionUtil.putAdminHomeDateOnSession(AdminHomeDateType.SECOND_DATE, null, getRequest().getSession()),
			true);
		
		ItemLocales itemLocales = new ItemLocales();
		itemLocales.setFilter(filter);
		return itemLocales;
	}
}
