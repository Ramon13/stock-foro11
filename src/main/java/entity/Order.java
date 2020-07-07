package entity;

import java.time.LocalDate;

public class Order extends Entity{

	private LocalDate finalDate;
	
	private LocalDate releaseDate;
	
	private Character status;
	
	private String receivedPersonName;
	
	private Customer customer;

	public LocalDate getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public String getReceivedPersonName() {
		return receivedPersonName;
	}

	public void setReceivedPersonName(String receivedPersonName) {
		this.receivedPersonName = receivedPersonName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
