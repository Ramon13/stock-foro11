package domain;

import java.util.ArrayList;
import java.util.List;

import entity.Item;

public class ItemLocale {

	/**
	 * An item That Is Linked With n locale Elements 
	 */
	private Item item;
	
	/**
	 * An array That Stores The Sum Amount Output By locale
	 */
	private List<Integer> sumLocales = new ArrayList<Integer>();
	
	private Integer startDateAmount = 0;
	private Integer endDateAmount = 0;
		
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

	public Integer getStartDateAmount() {
		return startDateAmount;
	}

	public void setStartDateAmount(Integer startDateAmount) {
		this.startDateAmount = startDateAmount;
	}

	public Integer getEndDateAmount() {
		return endDateAmount;
	}

	public void setEndDateAmount(Integer endDateAmount) {
		this.endDateAmount = endDateAmount;
	}

}
