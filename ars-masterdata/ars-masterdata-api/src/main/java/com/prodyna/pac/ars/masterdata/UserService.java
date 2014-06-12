package com.prodyna.pac.ars.masterdata;

import java.util.List;

import com.prodyna.pac.ars.masterdata.model.User;

public interface UserService {

	User addUser(User user);
	
	void updateUser(User user);
	
	List<User> readAllUsers();
	
	User readUser(long id);
	
	void removeUser(long id);
}
