package com.autopartscrm.model;

public enum Role {
    ADMIN("ADMIN"),
    GERENTE("GERENTE"),
    VENDEDOR("VENDEDOR"),
    CLIENTE("CLIENTE");
	
	private String role;
	
	private Role(String role) {
		this.role = role;
	}

	public String asString() {
		return role;
	}
	
	public static Role of(String role) {
		if (role == null) return null;
		
		return Role.valueOf(role);
	}
}