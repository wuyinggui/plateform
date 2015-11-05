package com.ucar.model.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ucar.dao.MenuDaoImpl;
import com.ucar.model.User;
import com.ucar.model.service.MenuService;
import com.ucar.model.vo.City;
import com.ucar.model.vo.Menu;
import com.ucar.model.vo.UserMenu;

@Service("menuService")
@Scope("prototype")
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuDaoImpl menuDao;
	@Override
	public String getMenuIdsByUser(User user) {
		
		return menuDao.getMenuIdsByUser(user);
	}
	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return menuDao.getUserList();
	}
	@Override
	public List<Menu> getAllMenu() {
		// TODO Auto-generated method stub
		return menuDao.getAllMenu();
	}
	@Override
	public void updateUserInfo(UserMenu usermenu) {
		UserMenu old = menuDao.getUserMenu(usermenu);
		String time = String.format("%1$tF %1$tT", new Date());
		usermenu.setTime(time);
		if(old == null && !"".equals(usermenu.getUser_name())){
			menuDao.saveUserInfo(usermenu);
		}else{
			menuDao.updateUserInfo(usermenu);
		}
	}
	@Override
	public List<City> getAllCity() {
		// TODO Auto-generated method stub
		return menuDao.getAllCity();
	}
	@Override
	public String getCityIdsByUser(User user) {
		// TODO Auto-generated method stub
		return menuDao.getCityIdsByUser(user);
	}
	@Override
	public List<Menu> getMenusByMenuIds(List<String> list) {
		// TODO Auto-generated method stub
		return menuDao.getMenusByMenuIds(list);
	}
	public List<Menu> getSubMenuList(String parentMenuid) {
		return menuDao.getSubMenuList(parentMenuid);
	}
	@Override
	public List<Menu> getAllMenuList() {
		// TODO Auto-generated method stub
		return menuDao.getAllMenuList();
	}
	@Override
	public Menu getMenuByMenuID(String menuid) {
		// TODO Auto-generated method stub
		return menuDao.getMenuByMenuID(menuid);
	}
	@Override
	public void saveMenu(Menu entity) {
		menuDao.saveMenu(entity);
	}
	@Override
	public void updateMenu(Menu entity) {
		menuDao.updateMenu(entity);
	}
	@Override
	public Menu getMenuById(int id) {
		// TODO Auto-generated method stub
		return menuDao.getMenuById(id);
	}
	@Override
	public List<Menu> getAllMenuListNew() {
		// TODO Auto-generated method stub
		return menuDao.getAllMenuListNew();
	}
	@Override
	public void deleteMenu(Menu entity) {
		menuDao.deleteMenu(entity);
	}
	@Override
	public int getAllMenuListCount(String sSearch) {
		return menuDao.getAllMenuListCount(sSearch);
	}
	@Override
	public List<User> findMenuToPage(int iDisplayStart, int iDisplayLength,
			String sSearch) {
		// TODO Auto-generated method stub
		return menuDao.findMenuToPage(iDisplayStart,iDisplayLength,sSearch);
	}
	@Override
	public int getMenusCount(String sSearch) {
		// TODO Auto-generated method stub
		return menuDao.getMenusCount(sSearch);
	}
	@Override
	public List<Menu> findMenus(int iDisplayStart, int iDisplayLength, String sSearch) {
		// TODO Auto-generated method stub
		return menuDao.findMenus(iDisplayStart,iDisplayLength,sSearch);
	}
	@Override
	public void updateSubMenu(Menu entity) {
		menuDao.updateSubMenu(entity);
	}
	@Override
	public int getNextSeqByParentId(String parent_id) {
		
		return menuDao.getNextSeqByParentId(parent_id);
	}
	@Override
	public void updateLastMenu(Menu old) {
		menuDao.updateLastMenu(old);
	}
	@Override
	public void updateBrotherMenu(Menu entity) {
		menuDao.updateBrotherMenu(entity);
	}
}
