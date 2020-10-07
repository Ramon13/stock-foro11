package entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import dao.Search;

public class Order extends Entity{

	@Search
	private LocalDate finalDate;
	
	private LocalDate releaseDate;
	
	private Character status;
	
	@Search
	private String receivedPersonName;
	
	@Search (getMarckedFields = true)
	private User customer;
	
	private User releaseAdministrator;
	
	private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);

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
		return this.status;
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

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public User getReleaseAdministrator() {
		return releaseAdministrator;
	}

	public void setReleaseAdministrator(User releaseAdministrator) {
		this.releaseAdministrator = releaseAdministrator;
	}
	
}
