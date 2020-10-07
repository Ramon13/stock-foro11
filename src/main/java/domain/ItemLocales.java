package domain;

import java.util.ArrayList;
import java.util.List;

/**
 * This class Handles With The Sum Of The amount Output Of Each locale
 * By item.
 * @author Ramon
 *
 */
public class ItemLocales {

	private List<ItemLocale> itemLocales = new ArrayList<ItemLocale>(0);
	private ItemLocaleFilter filter;

	public ItemLocaleFilter getFilter() {
		return filter;
	}

	public void setFilter(ItemLocaleFilter filter) {
		this.filter = filter;
	}
	
	public List<ItemLocale> getItemLocales() {
		return itemLocales;
	}
	
	public void setItemLocales(List<ItemLocale> itemLocales) {
		this.itemLocales = itemLocales;
	}
	
}
