package action.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Item;
import entity.Locale;

public class ItemSumWrapper {

	private Item item;
	private Integer previousYearOrderAmountSum = 0;
	private Integer betweenDatesOrderAmountSum = 0;
	private Integer customStartStock = 0;
	private Integer customEndStock = 0;
	private Map<Long, Integer> previousYearLocaleSum = new HashMap<Long, Integer>(0);
	private Map<Long, Integer> betweenDatesLocaleSum = new HashMap<Long, Integer>(0);
	
	public ItemSumWrapper() {
		
	}
	
	public ItemSumWrapper(Item item, List<Locale> locales) {
		this.item = item;
		for (Locale locale : locales) {
			previousYearLocaleSum.put(locale.getId(), 0);
			betweenDatesLocaleSum.put(locale.getId(), 0);
		}
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public Integer getPreviousYearOrderAmountSum() {
		return previousYearOrderAmountSum;
	}
	
	public void setPreviousYearOrderAmountSum(Integer previousYearOrderAmountSum) {
		this.previousYearOrderAmountSum = previousYearOrderAmountSum;
	}
	
	public Integer getBetweenDatesOrderAmountSum() {
		return betweenDatesOrderAmountSum;
	}
	
	public void setBetweenDatesOrderAmountSum(Integer betweenDatesOrderAmountSum) {
		this.betweenDatesOrderAmountSum = betweenDatesOrderAmountSum;
	}
	
	public Integer getCustomStartStock() {
		return customStartStock;
	}

	public void setCustomStartStock(Integer customStartStock) {
		this.customStartStock = customStartStock;
	}

	public Integer getCustomEndStock() {
		return customEndStock;
	}

	public void setCustomEndStock(Integer customEndStock) {
		this.customEndStock = customEndStock;
	}

	public Map<Long, Integer> getPreviousYearLocaleSum() {
		return previousYearLocaleSum;
	}

	public void setPreviousYearLocaleSum(Map<Long, Integer> previousYearLocaleSum) {
		this.previousYearLocaleSum = previousYearLocaleSum;
	}

	public Map<Long, Integer> getBetweenDatesLocaleSum() {
		return betweenDatesLocaleSum;
	}

	public void setBetweenDatesLocaleSum(Map<Long, Integer> betweenDatesLocaleSum) {
		this.betweenDatesLocaleSum = betweenDatesLocaleSum;
	}
	
	public void incrementPreviousYearSum(Integer amount) {
		this.previousYearOrderAmountSum += amount;
	}
	
	public void incrementBetweenSum(Integer amount) {
		this.betweenDatesOrderAmountSum += amount;
	}
	
	public void incrementCustomStartStock(Integer amount) {
		this.customStartStock += amount;
	}
	
	public void incrementCustomEndStock(Integer amount) {
		this.customEndStock += amount;
	}

	public void incremmentPreviousYearLocaleSum(Long localeId, Integer amount) {
		Integer value = previousYearLocaleSum.get(localeId);
		previousYearLocaleSum.put(localeId, value + amount);
	}
	
	public void incremmentBetweenDatesLocaleSum(Long localeId, Integer amount) {
		Integer value = betweenDatesLocaleSum.get(localeId);
		betweenDatesLocaleSum.put(localeId, value + amount);
	}
}
