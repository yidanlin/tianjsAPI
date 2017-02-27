package com.tianan.service;

import java.util.List;

import com.tianan.model.User;

public interface UserService {

	List<User> likeName(String name);

	User getById(Long id);

	List<User> getUsers();
	
	String insert(User user);
}
