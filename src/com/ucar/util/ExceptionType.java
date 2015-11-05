package com.ucar.util;

/**
 * 
 * @author xuuzhimei
 * @version 1.0
 */
public enum ExceptionType {
	/**
	 * 异常添加规则说明 
	 * 一、 编号命名
	 *  	1、 1开头代表是常规的异常
	 *  	2、 2开头代表是操作数据中的异常
	 *  	3、 5以后是业务异常(包含5)
	 *  	4、长度为8位
	 *  	5、不能以3、4开头（3、4是预留编号）
	 * 二、名称命名
	 *     1、以EXC_开头 ，全部大写
	 *     2、一个单词长度短的可以使用全称，否则使用前3个字符；多个单词使用单词首字段
	 *     3、业务异常可以自定义（名称要见）
	 *     
	 */
	EXC_NULL(10000001), //常规NULL异常
	EXC_DB_EXC(20000001),//数据库异常
	EXC_DB_CON(20000002),//数据库连接错误
	EXC_DB_UPDATE(20000003),//执行更新SQL错误
	EXC_REVIEW_INSERT(50000001),//执行添加审核失败
	EXC_PLAT_NULL(60000001);//添加的数据为空

	private int value;

	ExceptionType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
