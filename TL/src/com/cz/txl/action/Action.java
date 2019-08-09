package com.cz.txl.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * Action �ӿڣ����е�ҵ��action�඼��Ҫʵ�ִ˽ӿ� 
 * execute��������view��Դ�������ַ��������硰admin/login.jsp�� 
 * ��ν��ҵ��action��ָ,��ҵ��ĸ��Ӵ�����㼰�Ա����ɾ�Ĳ�
 * eg:��¼ҵ��  ��LoginAction���ﴦ��
 * ע��ҵ��  ��RegisteAction�ദ��
 * �����û� ��AddUserAction�ദ��
 * �޸��û� ��UpdateUserAction �ദ��
 * ...
 */  
public interface Action {
	 public String execute(HttpServletRequest request, HttpServletResponse response);  
}
