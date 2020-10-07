package domain;

public enum PermissionRoles {

	SUPER_ADMIN (2),
	ADMIN (1),
	USER (0);
	
	private int value;
	
	PermissionRoles(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
}
