package com.ucar.util;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.ucar.model.User;
import com.ucar.model.vo.UserMenu;

public class TaskSchedule{
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private SqlSession sqlSessionRemote;
	public void executeMethod(){
		System.out.println(sqlSession+"            "+sqlSessionRemote);
		List<User> users = sqlSessionRemote.selectList("com.ucar.dao.UserDao.selectUser");
		if(users != null && users.size() > 0){
			String createDate = String.format("%tF", new Date());
			String time = String.format("%1$tF %1$tT", new Date());
			for (User user : users) {
				try {
					user.setCreateDate(createDate);
					User old = sqlSession.selectOne("com.ucar.dao.UserDao.selectUserByUserName", user);
					if(old == null){
						sqlSession.insert("com.ucar.dao.UserDao.insertUser", user);
					}else{
						sqlSession.update("com.ucar.dao.UserDao.updateUser", user);
					}
					UserMenu menu = new UserMenu();
					menu.setUser_name(user.getUsername());
					menu.setCity_ids(user.getCity_scope_short());
					menu.setMenu_ids(null);
					menu.setTime(time);
					UserMenu oldUserMenu = sqlSession.selectOne("com.ucar.dao.MenuDao.selectOldUserMenu", menu);
					if(oldUserMenu == null){
						sqlSession.insert("com.ucar.dao.MenuDao.saveUserInfo",menu);
					}else{
						sqlSession.update("com.ucar.dao.MenuDao.updateUserInfo", menu);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
			}
		}
	}
}
