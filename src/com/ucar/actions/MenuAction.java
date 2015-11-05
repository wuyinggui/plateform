package com.ucar.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;
import com.ucar.model.User;
import com.ucar.model.bean.LBSUser;
import com.ucar.model.service.LBSUserService;
import com.ucar.model.service.MenuService;
import com.ucar.model.service.UserService;
import com.ucar.model.vo.City;
import com.ucar.model.vo.Menu;
import com.ucar.model.vo.UMenu;
import com.ucar.model.vo.UserMenu;
import com.ucar.util.RedisUtil;

/**
 * @author root
 *
 */
@Controller("menuAction")
@Scope("prototype")
public class MenuAction extends BaseAction{
	
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;
	private String username;
	private List<User> userMenus;
	private String result;
	private UserMenu usermenu;
	private Menu entity;
	private List<Menu> menus;
	private String oper;
	private String aoData;
	public String getMenu(){
		User user = new User();
		if(username == null || "".equals(username)){
			username = (String) RedisUtil.getDataFromRedis(LBSUserService.AUTH_REQUEST_IP+getIpAddr(ServletActionContext.getRequest()), String.class);
		}
		user.setUsername(username);
		UMenu umenu = new UMenu();
		List<Menu> menus = null;
		try {
			String menuids = menuService.getMenuIdsByUser(user);
			List<String> list = Arrays.asList(menuids.split(","));
			menus = menuService.getMenusByMenuIds(list);
			String cityIds = menuService.getCityIdsByUser(user);
			umenu.setCityIds(cityIds);
			umenu.setMenus(menus);
			umenu.setUname(username);
		} catch (Exception e) {
			menus = new ArrayList<Menu>();
		}
		result = JSON.toJSONString(umenu);
		return SUCCESS;
	}
	
	public String getSubMenu(){
		String parentMenuid = usermenu.getMenu_ids();
		List<Menu> subMenus = menuService.getSubMenuList(parentMenuid);
		result = JSON.toJSONString(subMenus);
		return SUCCESS;
	}
	public String getIpAddr(HttpServletRequest request) {
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	        ip = request.getRemoteAddr();
	    }
	    return ip;
	}

	public String menuList(){
		String sEcho = "";// 记录操作的次数  每次加1
		String iDisplayStart = "";// 起始
		String iDisplayLength = "";// size
		String sSearch = "";// 搜索的关键字
		int count = 0 ;  //查询出来的数量
		JSONArray ja = (JSONArray) JSONArray.parse(aoData);
		//分别为关键的参数赋值
		if(ja != null){
			for (int i = 0; i < ja.size(); i++) {
				JSONObject obj = (JSONObject) ja.get(i);
				if (obj.get("name").equals("sEcho"))
					sEcho = obj.get("value").toString();
				if (obj.get("name").equals("iDisplayStart"))
					iDisplayStart = obj.get("value").toString();
				if (obj.get("name").equals("iDisplayLength"))
					iDisplayLength = obj.get("value").toString();
				if (obj.get("name").equals("sSearch"))
					sSearch = obj.get("value").toString();
			}
			
			//为操作次数加1
			int  initEcho = Integer.parseInt(sEcho)+1;
			count = menuService.getAllMenuListCount(sSearch);
			List<User> managers = menuService.findMenuToPage(Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength),sSearch);
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("iTotalRecords", count);
			dataMap.put("sEcho",initEcho);
			dataMap.put("iTotalDisplayRecords", count);
			dataMap.put("aaData", managers);
			result = JSON.toJSONString(dataMap);
		}
		return SUCCESS;
		/*userMenus = menuService.getUserList();
		return "list";*/
	}
	
	public String getUserAllMenu(){
		User user = new User();
		user.setUsername(username);
		List<Menu> menuList = null;
		try {
			List<Menu> allMenus = menuService.getAllMenu();
			menuList = new ArrayList<Menu>(allMenus.size());
			String menuids = null;
			if(!"".equals(username)){
				menuids = menuService.getMenuIdsByUser(user);
			}
			if(menuids != null){
				List<String> list = Arrays.asList(menuids.split(","));
				for (Menu menu : allMenus) {
					boolean flag = false;
					inner: for (String umenuid : list) {
						if((menu.getId()+"").equals(umenuid)){
							flag = true;
							menu.setChecked(true);
							menuList.add(menu);
							break inner;
						}
					}
					if(!flag){
						menu.setChecked(false);
						menuList.add(menu);
					}
					
				}
			}else{
				for (Menu menu : allMenus) {
					menu.setChecked(false);
					menuList.add(menu);
				}
			}
		} catch (Exception e) {
			menuList = new ArrayList<Menu>();
		}
		result = JSON.toJSONString(menuList);
		return SUCCESS;
	}
	
	public String saveUserInfo(){
		String json = "{'success':";
		try {
			menuService.updateUserInfo(usermenu);
			json += true;
		} catch (Exception e) {
			e.printStackTrace();
			json += false;
		}
		json +="}";
		result = json;
		return SUCCESS;
	}
	
	public String getUserAllCity(){
		User user = new User();
		user.setUsername(username);
		List<City> cityList = null;
		try {
			List<City> allCitys = menuService.getAllCity();
			cityList = new ArrayList<City>(allCitys.size());
			String cityids = menuService.getCityIdsByUser(user);
			if(cityids != null){
				List<String> list = Arrays.asList(cityids.split(","));
				for (City city : allCitys) {
					boolean flag = false;
					inner: for (String ucityid : list) {
						if((city.getId()+"").equals(ucityid)){
							flag = true;
							city.setChecked(true);
							cityList.add(city);
							break inner;
						}
					}
					if(!flag){
						city.setChecked(false);
						cityList.add(city);
					}
					
				}
			}else{
				for (City city : allCitys) {
					city.setChecked(false);
					cityList.add(city);
				}
			}
		} catch (Exception e) {
			cityList = new ArrayList<City>();
		}
		result = JSON.toJSONString(cityList);
		return SUCCESS;
	}
	
	public String list(){
		String sEcho = "";// 记录操作的次数  每次加1
		String iDisplayStart = "";// 起始
		String iDisplayLength = "";// size
		String sSearch = "";// 搜索的关键字
		int count = 0 ;  //查询出来的数量
		JSONArray ja = (JSONArray) JSONArray.parse(aoData);
		//分别为关键的参数赋值
		for (int i = 0; i < ja.size(); i++) {
			JSONObject obj = (JSONObject) ja.get(i);
			if (obj.get("name").equals("sEcho"))
				sEcho = obj.get("value").toString();
			if (obj.get("name").equals("iDisplayStart"))
				iDisplayStart = obj.get("value").toString();
			if (obj.get("name").equals("iDisplayLength"))
				iDisplayLength = obj.get("value").toString();
			if (obj.get("name").equals("sSearch"))
				sSearch = obj.get("value").toString();
		}
		
		//为操作次数加1
		int  initEcho = Integer.parseInt(sEcho)+1;
		count = menuService.getMenusCount(sSearch);
		List<Menu> managers = menuService.findMenus(Integer.parseInt(iDisplayStart), Integer.parseInt(iDisplayLength),sSearch);
		Map<String,Object> dataMap = new HashMap<String, Object>();
		dataMap.put("iTotalRecords", count);
		dataMap.put("sEcho",initEcho);
		dataMap.put("iTotalDisplayRecords", count);
		dataMap.put("aaData", managers);
		result = JSON.toJSONString(dataMap);
		return SUCCESS;
	}
	public String allFirstLevelMenuList(){
		List<Menu> menus = menuService.getAllMenu();
		result = JSON.toJSONString(menus);
		return SUCCESS;
	}
	
	public String menuEdit(){
		if(entity != null){
			int id = entity.getId();
			entity = menuService.getMenuById(id);
		}
		return "menuEdit";
	}
	public String editMenu(){
		List<Menu> allMenus = menuService.getAllMenuListNew();
		boolean flag = checkEntity(allMenus);
		if(flag){
			int id = entity.getId();
			try {
				if(id == 0){
					//新增
					if("".equals(entity.getMenu_url())){
						entity.setMenu_url(null);
					}
					if("".equals(entity.getParent_id())){
						entity.setParent_id(null);
					}
					try {
						//先对兄弟菜单进行调整
						menuService.updateBrotherMenu(entity);
						menuService.saveMenu(entity);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					//更新
					Menu old = menuService.getMenuById(id);
					if(entity.getIs_del() == 1){
						menuService.updateLastMenu(old);
						menuService.deleteMenu(entity);
					}else{
						menuService.updateBrotherMenu(entity);
						menuService.updateMenu(entity);
						old.setParent_id(entity.getMenuid());
						menuService.updateSubMenu(old);
					}
				}
				result = "{'status':"+true+"}";
			} catch (Exception e) {
				e.printStackTrace();
				result = "{'status':"+false+",'msg':'系统异常！','type':1}";
			}
		}else{
			result =  "{'status':"+false+",'msg':'参数名称已存在，请检查！','type':2}";
		}
		return SUCCESS;
	}
	
	private boolean checkEntity(List<Menu> allMenus) {
		if(allMenus!= null && allMenus.size() > 0){
			for (Menu menu : allMenus) {
				//String menuid = menu.getMenuid();
				if(entity.getId() == menu.getId()){
					continue;
				}else{
					String menuid = menu.getMenuid();
					if(menuid.equals(entity.getMenuid())){
						return false;
					}
					String menuname = menu.getMenu_name();
					if(menuname.equals(entity.getMenu_name())){
						return false;
					}
					String menunamecn = menu.getMenu_name_zh_cn();
					if(menunamecn.equals(entity.getMenu_name_zh_cn())){
						return false;
					}
				}
			}
		}
		return true;
	}

	public String checkMenuIsLeaf(){
		int id = entity.getId();
		entity = menuService.getMenuById(id);
		if(entity != null){
			List<Menu> subms = menuService.getSubMenuList(entity.getMenuid());
			if(subms == null || subms.size() == 0){
				result = "{'status':"+true+"}";
			}else{
				result = "{'status':"+false+"}";
			}
		}
		return SUCCESS;
	}
	
	public String getNextSeq(){
		int seq = 0;
		try {
			seq = menuService.getNextSeqByParentId(entity.getParent_id());
		} catch (Exception e) {
			e.printStackTrace();
			result = "{'status':"+false+",\"errorMsg\":\""+e.getMessage()+"\"}";
		}
		result = "{'status':"+true+",\"nextSeq\":\""+seq+"\"}";
		return SUCCESS;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	 public String getResult() {
	        return result;
	 }
	 public void setResult(String result) {
	      this.result = result;
	 }

	public List<User> getUserMenus() {
		return userMenus;
	}

	public void setUserMenus(List<User> userMenus) {
		this.userMenus = userMenus;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public UserMenu getUsermenu() {
		return usermenu;
	}
	public void setUsermenu(UserMenu usermenu) {
		this.usermenu = usermenu;
	}


	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu getEntity() {
		return entity;
	}

	public void setEntity(Menu entity) {
		this.entity = entity;
	}

	public String getAoData() {
		return aoData;
	}

	public void setAoData(String aoData) {
		this.aoData = aoData;
	}
	
}
