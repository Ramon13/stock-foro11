package entity;

import com.google.gson.annotations.Expose;

import dao.Search;

public abstract class Entity {

	@Search
	@Expose
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
