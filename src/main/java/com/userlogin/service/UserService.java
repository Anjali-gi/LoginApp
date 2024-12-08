package com.userlogin.service;

import java.util.Optional;

import com.userlogin.entity.User;

public interface UserService {

	 void saveUser(User user);
	    Optional<User> findByUsername(String username);
	    Optional<User> findById(Long id);
		User validateUser(String username, String password);

}
