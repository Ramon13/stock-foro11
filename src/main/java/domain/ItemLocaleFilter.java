package domain;

import java.time.LocalDate;

public class ItemLocaleFilter {

	private LocalDate startDate;
	private LocalDate endDate;
	private boolean calcItemAmountByDate = false;

	public ItemLocaleFilter(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public ItemLocaleFilter(LocalDate startDate, LocalDate endDate, boolean calcItemAmountByDate) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.calcItemAmountByDate = calcItemAmountByDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isCalcItemAmountByDate() {
		return calcItemAmountByDate;
	}

	public void setCalcItemAmountByDate(boolean calcItemAmountByDate) {
		this.calcItemAmountByDate = calcItemAmountByDate;
	}
}
