package entity;

import dao.Search;

public class Locale extends Entity{

	@Search
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
