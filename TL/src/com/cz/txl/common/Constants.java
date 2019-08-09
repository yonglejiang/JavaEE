package com.cz.txl.common;

import java.util.HashMap;
import java.util.Map;

//放置静态常量,以供全局使用
public final class Constants {
	public static final String PACKAGE_PATH = "com.cz.txl.action.impl.";
	
	public static final String SESSEION_USER = "user";
	
	//超级管理员角色编号
	public static final int SUPER_ADMIN_ROLE_NO = 1;
	//类中属性名与数据表中字段名的映射
	public static Map<String,String> fieldToColumnsMap = new HashMap<String,String>();
	
	static{
		fieldToColumnsMap.put("userStatus", "user_status");
		fieldToColumnsMap.put("menuNo", "menu_no");
	}
}
