package com.ucar.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ucar.dao.UserDaoImpl;
import com.ucar.model.User;
import com.ucar.model.service.UserService;
import com.ucar.util.Encrypt;
import com.ucar.util.MD5Encrypt;
@Service("userService")
@Scope("prototype")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDaoImpl userDao;

	public User loginValidate(User user) {
		try {
			user.setPassword(Encrypt.sha1Encode(user.getPassword()));
			return userDao.loginValidate(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	/* 根据cookie登录
	 */
	@Override
	public User cookieLoginValidate(User user) {
		User result = null;
		try {
			user.setPassword(new String(Encrypt.decryptBASE64(user.getPassword())));
			result = userDao.loginValidate(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
