package com.prodyna.pac.ars.masterdata;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.prodyna.pac.ars.masterdata.model.User;

public interface UserService {

	User addUser(@NotNull @Valid User user);
	
	void updateUser(@NotNull @Valid User user);
	
	List<User> readAllUsers();
	
	User readUser(@Min(1) long id);
	
	void removeUser(@Min(1) long id);
	
	User readUserByName(@Size(min=2)String name);
}
