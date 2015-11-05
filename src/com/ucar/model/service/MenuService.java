package com.ucar.model.service;

import java.util.List;
import java.util.Map;

import com.ucar.model.User;
import com.ucar.model.vo.City;
import com.ucar.model.vo.Menu;
import com.ucar.model.vo.UserMenu;

public interface MenuService {
	public List<Menu> getMenusByMenuIds(List<String> list);

	public String getMenuIdsByUser(User user);

	public List<User> getUserList();

	public List<Menu> getAllMenu();

	public void updateUserInfo(UserMenu usermenu);

	public List<City> getAllCity();

	public String getCityIdsByUser(User user);

	public List<Menu> getSubMenuList(String parentMenuid);

	public List<Menu> getAllMenuList();

	public Menu getMenuByMenuID(String menuid);

	public void saveMenu(Menu entity);

	public void updateMenu(Menu entity);

	public Menu getMenuById(int id);

	public List<Menu> getAllMenuListNew();

	public void deleteMenu(Menu entity);

	public int getAllMenuListCount(String sSearch);

	public List<User> findMenuToPage(int iDisplayStart, int iDisplayLength, String sSearch);

	public int getMenusCount(String sSearch);

	public List<Menu> findMenus(int parseInt, int parseInt2, String sSearch);

	public void updateSubMenu(Menu entity);

	public int getNextSeqByParentId(String parent_id);

	public void updateLastMenu(Menu old);

	public void updateBrotherMenu(Menu entity);
}
