package entity;

import java.util.HashSet;
import java.util.Set;

import dao.Search;

public class Category extends Entity{
	
	public Category() {}
	
	public Category(Long id) {
		setId(id);
	}
	
	//TODO this field must be changed by String type (reason: compatibility with older db structure)
	@Search
	private Integer name;

	private Set<SubCategory> subCategories = new HashSet<SubCategory>(0);
	
	public Set<SubCategory> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(Set<SubCategory> subCategories) {
		this.subCategories = subCategories;
	}

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}
}
