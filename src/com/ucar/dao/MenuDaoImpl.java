package com.ucar.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.ucar.model.User;
import com.ucar.model.vo.City;
import com.ucar.model.vo.Menu;
import com.ucar.model.vo.UserMenu;
@Repository("menuDao")
@Scope("prototype")
public class MenuDaoImpl extends BaseDao{
	public final String namespace = "com.ucar.dao.MenuDao.";
	public List<Menu> getMenusByMenuIds(List<String> list) {
		return getSqlSession().selectList(namespace+"selectMenusByMenuIds", list);
	}

	public String getMenuIdsByUser(User user) {
		return getSqlSession().selectOne(namespace+"selectMenuIdsByUser", user);
	}


	public List<User> getUserList() {
		return getSqlSession().selectList(namespace+"selectUserList");
	}

	public List<Menu> getAllMenu() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(namespace+"selectAllMenu");
	}

	public void updateUserInfo(UserMenu usermenu) {
		getSqlSession().update(namespace+"updateUserInfo",usermenu);
	}

	public UserMenu getUserMenu(UserMenu usermenu) {
		return getSqlSession().selectOne(namespace+"selectOldUserMenu",usermenu);
	}

	public void saveUserInfo(UserMenu usermenu) {
		getSqlSession().insert(namespace+"saveUserInfo", usermenu);
	}

	public List<City> getAllCity() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(namespace+"selectAllCity");
	}

	public String getCityIdsByUser(User user) {
		
		return getSqlSession().selectOne(namespace+"selectCityIdsByUser", user);
	}

	public List<Menu> getSubMenuList(String parentMenuid) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(namespace+"selectSubMenuList", parentMenuid);
	}

	public List<Menu> getAllMenuList() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(namespace+"selectAllMenuList");
	}

	public Menu getMenuByMenuID(String menuid) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(namespace+"selectMenuByMenuID",menuid);
	}

	public void saveMenu(Menu entity) {
		getSqlSession().insert(namespace+"insertMenu", entity);
	}

	public void updateMenu(Menu entity) {
		getSqlSession().update(namespace+"updateMenu", entity);
	}

	public Menu getMenuById(int id) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(namespace+"selectMenuById", id);
	}

	public List<Menu> getAllMenuListNew() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(namespace+"selectAllMenuListNew");
	}

	public Object deleteMenu(Menu entity) {
		// TODO Auto-generated method stub
		return getSqlSession().delete(namespace+"deleteMenu", entity);
	}

	public int getAllMenuListCount(String sSearch) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("sSearch", sSearch);
		return  getSqlSession().selectOne(namespace+"getAllMenuListCount", param);
	}

	public List<User> findMenuToPage(int iDisplayStart, int iDisplayLength,
			String sSearch) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start", iDisplayStart);
		param.put("end", iDisplayLength);
		param.put("sSearch", sSearch);
		List<User> users = getSqlSession().selectList(namespace+"findMenuToPage", param);
		List<User> result = new ArrayList<User>();
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			user.setMenu("<a  onclick='setMenu(\""+user.getUsername()+"\",this)'>权限设置</a>");
			user.setCity("<a  onclick='setCity(\""+user.getUsername()+"\",this)'>城市设置</a>");
			result.add(user);
		}
		return result;
	}

	public int getMenusCount(String sSearch) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("sSearch", sSearch);
		return getSqlSession().selectOne(namespace+"getMenusCount", param);
	}

	public List<Menu> findMenus(int iDisplayStart, int iDisplayLength,
			String sSearch) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("start", iDisplayStart);
		param.put("end", iDisplayLength);
		param.put("sSearch", sSearch);
		List<Menu> menus = getSqlSession().selectList(namespace+"findMenus", param);
		List<Menu> result = new ArrayList<Menu>();
		for (int i = 0; i < menus.size(); i++) {
			Menu menu = menus.get(i);
			StringBuffer buffer = new StringBuffer();
			buffer.append("<div class='visible-md visible-lg hidden-sm hidden-xs action-buttons'>");
				//buffer.append("<a class='blue' href='javascript:void(0)' onclick='addMenu()'><i class='icon-zoom-in bigger-130'></i></a>");
				buffer.append("<a class='green' href='javascript:void(0)' onclick='editMenu(\""+menu.getId()+"\")'><i class='icon-pencil bigger-130'></i></a>");
				buffer.append("<a class='red' href='javascript:void(0)' onclick='delMenu(\""+menu.getId()+"\")'><i class='icon-trash bigger-130'></i></a>");
			buffer.append("</div>");
					buffer.append("<div class='visible-xs visible-sm hidden-md hidden-lg'>");
							buffer.append("<div class='inline position-relative'>");
								buffer.append("<button class='btn btn-minier btn-yellow dropdown-toggle' data-toggle='dropdown'>");
									buffer.append("<i class='icon-caret-down icon-only bigger-120'></i>");
								buffer.append("</button>");	
								buffer.append("<ul class='dropdown-menu dropdown-only-icon dropdown-yellow pull-right dropdown-caret dropdown-close'>");
								buffer.append("<li>");
									buffer.append("<a href='javascript:void(0)' name=\""+menu.getId()+"\" class='tooltip-info' data-rel='tooltip' title='View'>");
										buffer.append("<span class='blue'>");	
											buffer.append("<i class='icon-zoom-in bigger-120' ></i>");
										buffer.append("</span>");							
									buffer.append("</a>");								
								buffer.append("</li>");			
								buffer.append("<li>");
									buffer.append("<a href='javascript:void(0)' name=\""+menu.getId()+"\" class='tooltip-success' data-rel='tooltip' title='Edit'>");
										buffer.append("<span class='green'>");
											buffer.append("<i class='icon-edit bigger-120'></i>");
										buffer.append("</span>");
									buffer.append("</a>");
								buffer.append("</li>");
								buffer.append("<li>");
									buffer.append("<a href='javascript:void(0)' name=\""+menu.getId()+"\" class='tooltip-error' data-rel='tooltip' title='Delete'>");
										buffer.append("<span class='red'>");										
											buffer.append("<i class='icon-trash bigger-120'></i>");		
										buffer.append("</span>");
									buffer.append("</a>");
								buffer.append("</li>");
							buffer.append("</ul>");
						buffer.append("</div>");
					buffer.append("</div>");
			menu.setControl(buffer.toString());		
			menu.setIs_leaf_cn(menu.getIs_leaf() == 1 ? "是" : "否");
			buffer = new StringBuffer();
			buffer.append("<label><input type='checkbox' class='ace' value=\""+menu.getId()+"\"/><span class='lbl'></span></label>");
			menu.setAce(buffer.toString());
			result.add(menu);
		}
		return result;
	}

	public void updateSubMenu(Menu entity) {
		getSqlSession().update(namespace+"updateSubMenu",entity);
	}

	public int getNextSeqByParentId(String parent_id) {
		
		return getSqlSession().selectOne(namespace+"selectNextSeqByParentId", parent_id);
	}

	public void updateLastMenu(Menu old) {
		getSqlSession().update(namespace+"updateLastMenu", old);
	}

	public void updateBrotherMenu(Menu entity) {
		getSqlSession().update(namespace+"updateBrotherMenu", entity);
	}

}
