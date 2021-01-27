package domain;

import entity.Item;

public class ItemStockForecast {

	private Item item;
	private double amount;
	private double twoYearsAverage;
	private double twelveMonthsAverage;
	private double minRecommendedStock;
	private double maxAvg;
	private double forecastedStock;
	private double bidAmount;
	private double lastEntryValue;
	private double total;
	
	public ItemStockForecast() {}
	
	public ItemStockForecast(Item item) {
		this.item = item;
	}
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public double getTwoYearsAverage() {
		return twoYearsAverage;
	}
	public void setTwoYearsAverage(double twoYearsAverage) {
		this.maxAvg = Math.max(twoYearsAverage, twelveMonthsAverage);
		this.twoYearsAverage = twoYearsAverage;
	}
	public double getTwelveMonthsAverage() {
		return twelveMonthsAverage;
	}
	public void setTwelveMonthsAverage(double twelveMonthsAverage) {
		this.maxAvg = Math.max(twoYearsAverage, twelveMonthsAverage);
		this.twelveMonthsAverage = twelveMonthsAverage;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getMinRecommendedStock() {
		return minRecommendedStock;
	}
	public void setMinRecommendedStock(double minRecommendedStock) {
		this.minRecommendedStock = minRecommendedStock;
	}
	public double getMaxAvg() {
		return maxAvg;
	}
	public void setForecastedStock(double forecastedStock) {
		this.forecastedStock = forecastedStock;
	}
	public double getForecastedStock() {
		return forecastedStock;
	}
	public double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
	public double getLastEntryValue() {
		return lastEntryValue;
	}
	public void setLastEntryValue(double lastEntryValue) {
		this.lastEntryValue = lastEntryValue;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
}
