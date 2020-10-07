package entity;

import java.math.BigDecimal;

import dao.Search;

public class EntryItem extends Entity{

	private Integer amount;
	
	private BigDecimal value;
	
	private Double total;
	
	private Item item;
	
	@Search (getMarckedFields = true)
	private Entry entry;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public void calcTotal() {
		this.total = amount * value.doubleValue();
	}
	
}
