package entity;

import dao.Search;

public class SubCategory extends Entity{

	public SubCategory() {}
	
	public SubCategory(Long id) {
		setId(id);
	}
	
	@Search
	private String name;
	
	private Category category;

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
