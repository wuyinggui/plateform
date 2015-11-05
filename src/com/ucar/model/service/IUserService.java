package com.ucar.model.service;

import com.ucar.model.User;

public interface IUserService {
	public boolean isUserExists(String username);
	public void addUser(User user);
}
