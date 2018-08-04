package com.sl.lms.domain.service;

import com.sl.lms.domain.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}