package entity;

import java.util.HashSet;
import java.util.Set;

import dao.Search;

public class User extends Entity {

	@Search
	private String name;
	
	private String password;
	
	private Boolean resetPassword;
	
	private Boolean active;
	
	@Search (getMarckedFields = true) 
	private Permission permission;
	
	@Search (getMarckedFields = true)
	private Locale locale;
	
	private ShoppingCart cart;
	
	private Set<Order> orders = new HashSet<Order>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getResetPassword() {
		return resetPassword;
	}

	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
