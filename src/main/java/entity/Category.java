package entity;

public class Category extends Entity{
	
	public Category() {}
	
	public Category(Long id) {
		setId(id);
	}
	
	//TODO this field must be changed by String type (reason: compatibility with older db structure)
	private Integer name;

	public Integer getName() {
		return name;
	}

	public void setName(Integer name) {
		this.name = name;
	}
}
