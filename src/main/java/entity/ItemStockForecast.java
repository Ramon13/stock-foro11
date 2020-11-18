package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import entity.Item;

public class ItemStockForecast {

	private Item item;
	private LocalDate previousDate;
	private Long average1Year;
	private Long average2Years;
	private LocalDate forecastDate;
	private Integer bidAmount;
	private BigDecimal lastAcquisitionValue;
	private BigDecimal total;
	
	public ItemStockForecast(Item item, LocalDate previousDate, Long average1Year, Long average2Years,
			LocalDate forecastDate, Integer bidAmount, BigDecimal lastAcquisitionValue, BigDecimal total) {
		this.item = item;
		this.previousDate = previousDate;
		this.average1Year = average1Year;
		this.average2Years = average2Years;
		this.forecastDate = forecastDate;
		this.bidAmount = bidAmount;
		this.lastAcquisitionValue = lastAcquisitionValue;
		this.total = total;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public LocalDate getPreviousDate() {
		return previousDate;
	}

	public void setPreviousDate(LocalDate previousDate) {
		this.previousDate = previousDate;
	}

	public Long getAverage1Year() {
		return average1Year;
	}

	public void setAverage1Year(Long average1Year) {
		this.average1Year = average1Year;
	}

	public Long getAverage2Years() {
		return average2Years;
	}

	public void setAverage2Years(Long average2Years) {
		this.average2Years = average2Years;
	}

	public LocalDate getForecastDate() {
		return forecastDate;
	}

	public void setForecastDate(LocalDate forecastDate) {
		this.forecastDate = forecastDate;
	}

	public Integer getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Integer bidAmount) {
		this.bidAmount = bidAmount;
	}

	public BigDecimal getLastAcquisitionValue() {
		return lastAcquisitionValue;
	}

	public void setLastAcquisitionValue(BigDecimal lastAcquisitionValue) {
		this.lastAcquisitionValue = lastAcquisitionValue;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
