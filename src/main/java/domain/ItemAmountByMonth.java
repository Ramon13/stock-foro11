package domain;

public class ItemAmountByMonth {

	private String monthName;
	private Integer amount;
	
	public ItemAmountByMonth(String monthName, Integer amount) {
		this.setMonthName(monthName);
		this.setAmount(amount);
	}

	public ItemAmountByMonth() {
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
