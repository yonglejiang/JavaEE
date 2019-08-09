package com.cz.txl.common;

import java.util.HashMap;
import java.util.Map;

//���þ�̬����,�Թ�ȫ��ʹ��
public final class Constants {
	public static final String PACKAGE_PATH = "com.cz.txl.action.impl.";
	
	public static final String SESSEION_USER = "user";
	
	//��������Ա��ɫ���
	public static final int SUPER_ADMIN_ROLE_NO = 1;
	//���������������ݱ����ֶ�����ӳ��
	public static Map<String,String> fieldToColumnsMap = new HashMap<String,String>();
	
	static{
		fieldToColumnsMap.put("userStatus", "user_status");
		fieldToColumnsMap.put("menuNo", "menu_no");
	}
}
