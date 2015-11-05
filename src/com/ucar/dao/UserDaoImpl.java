package com.ucar.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ucar.model.User;
@Repository("userDao")
@Scope("prototype")
public class UserDaoImpl extends BaseDao{
	public User loginValidate(User user) {
		try {
			User old = getSqlSession().selectOne("com.ucar.dao.UserDao.selectUser", user);
			return old;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
