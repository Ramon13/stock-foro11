package domain;

import java.time.LocalDate;

public class ItemLocaleFilters {

	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private char status;

	public ItemLocaleFilters(LocalDate startDate, LocalDate endDate, char status) {
	
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
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

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
}
