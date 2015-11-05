package com.ucar.model.service;

import com.ucar.model.User;

public interface UserService {
	public User loginValidate(User user);
	public User cookieLoginValidate(User user);
}
