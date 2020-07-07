package domain;

import java.util.ArrayList;
import java.util.List;

import entity.Item;

/**
 * This class Handles With The Sum Of The amount Output Of Each locale
 * By item.
 * @author Ramon
 *
 */
public class ItemLocale {

	/**
	 * An item That Is Linked With n locale Elements 
	 */
	private Item item;
	
	/**
	 * An array That Stores The Sum Amount Output By locale
	 */
	private List<Integer> sumLocales = new ArrayList<Integer>();
		
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public List<Integer> getSumLocales() {
		return sumLocales;
	}

	public void setSumLocales(List<Integer> sumLocales) {
		this.sumLocales = sumLocales;
	}
}
