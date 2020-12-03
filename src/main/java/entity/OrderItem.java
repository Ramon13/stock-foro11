package entity;

import dao.Search;

public class OrderItem extends Entity{

	private Integer amount;
	
	private Item item;
	
	@Search (getMarckedFields = true)
	private Order order;
	
	private ShoppingCart cart;

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}
	
	
}
