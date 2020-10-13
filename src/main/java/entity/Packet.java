package entity;

import java.io.Serializable;

import dao.Search;

public class Packet extends Entity implements Serializable {
	
	public Packet() {}
	
	public Packet(Long id) {
		setId(id);
	};
	
	@Search
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
