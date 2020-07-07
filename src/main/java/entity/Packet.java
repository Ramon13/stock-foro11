package entity;

public class Packet extends Entity {
	
	public Packet() {}
	
	public Packet(Long id) {
		setId(id);
	};
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
