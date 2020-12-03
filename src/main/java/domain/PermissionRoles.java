package domain;

import entity.Permission;

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
	
	public static PermissionRoles getByValue(int value) {
		for (PermissionRoles status : PermissionRoles.values()) {
			if (status.getValue() == value)
				return status;
		}
		
		return USER;
	}
	
	public static boolean isAdmin(Permission p) {
		return p.getLevel() == ADMIN.value;
	}
	
	public static boolean isSuperAdmin(Permission p) {
		return p.getLevel() == SUPER_ADMIN.value;
	}
	
	public static boolean isUser(Permission p) {
		return p.getLevel() == USER.value;
	}
	
	public static boolean isAnyAdmin(Permission p) {
		return PermissionRoles.isSuperAdmin(p) 
				|| PermissionRoles.isAdmin(p);
	}
}
