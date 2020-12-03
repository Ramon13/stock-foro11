package entity;

import java.util.HashSet;
import java.util.Set;

import dao.Search;

public class SubCategory extends Entity{

	public SubCategory() {}
	
	public SubCategory(Long id) {
		setId(id);
	}
	
	@Search
	private String name;
	
	private Category category;

	private Set<Item> items = new HashSet<Item>(0);
	
	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
