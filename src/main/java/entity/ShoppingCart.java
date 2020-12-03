package entity;

import java.util.HashSet;
import java.util.Set;

public class ShoppingCart extends Entity{

	private User customer;
	
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();
	
	
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
	
	
}
