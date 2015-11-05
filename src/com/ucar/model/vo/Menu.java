package com.ucar.model.vo;


/**
 * @author wuyinggui
 *
 */
public class Menu {
	private int id;
	private String menuid;
	private String menu_name;
	private String menu_url;
	private String menu_name_zh_cn;
	private int is_leaf;
	private String parent_id;
	private int seq;
	private boolean checked;
	private String is_leaf_cn;
	private int is_del;
	private String control;
	private String ace;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getMenu_url() {
		return menu_url;
	}
	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}
	
	public int getIs_leaf() {
		return is_leaf;
	}
	public void setIs_leaf(int is_leaf) {
		this.is_leaf = is_leaf;
	}
	public String getMenu_name_zh_cn() {
		return menu_name_zh_cn;
	}
	public void setMenu_name_zh_cn(String menu_name_zh_cn) {
		this.menu_name_zh_cn = menu_name_zh_cn;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getIs_leaf_cn() {
		return is_leaf_cn;
	}
	public void setIs_leaf_cn(String is_leaf_cn) {
		this.is_leaf_cn = is_leaf_cn;
	}
	public int getIs_del() {
		return is_del;
	}
	public void setIs_del(int is_del) {
		this.is_del = is_del;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getAce() {
		return ace;
	}
	public void setAce(String ace) {
		this.ace = ace;
	}
	
}
