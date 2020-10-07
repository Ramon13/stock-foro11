package domain;

import entity.User;

public class LoggedUser {

	private User user;

	public LoggedUser() {}
	
	public LoggedUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
