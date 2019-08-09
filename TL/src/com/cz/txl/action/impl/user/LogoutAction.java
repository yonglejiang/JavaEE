package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cz.txl.action.Action;

public class LogoutAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//ע���Ķ���
		//��session������Ѿ���¼���û�.������ڵĻ�
		request.getSession().removeAttribute("user");
		return "login.jsp";
	}

}
