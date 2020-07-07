package admin.action;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletContext;

import br.com.javamon.action.Action;
import domain.ItemLocale;
import domain.ItemLocaleFilters;
import entity.Locale;
import service.ItemService;
import service.LocaleService;

public class ListItemsAction extends Action{

	@Override
	public void process() throws Exception {
		setContextData();
		
		List<Locale> locales = getServiceFactory().getService(LocaleService.class).list();
		ItemLocaleFilters filter1 = new ItemLocaleFilters(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 12, 31), 'F');
		ItemLocaleFilters filter2 = new ItemLocaleFilters(LocalDate.of(2018, 1, 1), LocalDate.of(2018, 12, 31), 'F');
		
		List<ItemLocale> itemLocales = getServiceFactory().getService(ItemService.class)
			.listAndSumLocales(filter1, filter2);
		
		getRequest().setAttribute("itemLocales", itemLocales);
		getRequest().setAttribute("locales", locales);
		getRequest().setAttribute("splitIndex", locales.size() - 1);
		
		
		foward("/restrict/home.jsp");
	}

	private void setContextData() {
		ServletContext context = getRequest().getServletContext();
		if(context.getAttribute(ListItemsAction.class.getCanonicalName()) != null)
			return;
		
		System.out.println("context");
		context.setAttribute(ListItemsAction.class.getCanonicalName(), "true");
		context.setAttribute("firstDayOfYear", LocalDate.ofYearDay(LocalDate.now().getYear(), 1));
		context.setAttribute("today", LocalDate.now());
		
	}
}
