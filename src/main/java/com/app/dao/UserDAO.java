package com.app.dao;

import com.app.model.User;

public interface UserDAO {
	
	public User findUserByName(String username);

}
