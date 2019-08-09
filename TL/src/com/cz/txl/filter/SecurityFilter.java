package com.cz.txl.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.common.Constants;
import com.cz.txl.model.Menu;
import com.cz.txl.model.User;
import com.cz.txl.util.CheckLoginUtil;

public class SecurityFilter implements Filter {
	Log log = LogFactory.getLog(SecurityFilter.class);
	//������֤Ȩ�޵�Action,����б�
	final String[] needNotCheckDo = {"LoginAction","imageCode","LogoutAction","UserRegCheckAction",
			"UserRegAction"};
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		
		//������������������ǹ涨�����û�����ֱ��ͨ������.jsp�����ж�ҳ��ķ���,�����������˰�ȫ��
		//Ϊ�˹��˵�����login.jsp��error.jsp֮�������,������Ҫ�ж��û����͵�����·��
		String requestPath = httpServletRequest.getServletPath();
		log.info("���������յ����û�����:"+requestPath);
		//����������·����localhost:8080/txl/login.jsp ��ô���淵�ص���login.jsp
		
		//����һ��������httpServletRequest.getRequestURI();������صĽ����txl/login.jsp
		//��������ʹ��getServletPath();����
		//*******************��һ��У��Start,��ֹ�û�ֱ��ͨ��.jsp���ʵ�ҳ��******************
		//����û������·������null,��������.jsp��β
		if (requestPath != null && requestPath.endsWith(".jsp")) {
			int index = requestPath.indexOf(".");//�õ�"."�ڸ��ַ�����λ��
			String requestName = requestPath.substring(1,index);//��ȡ�ַ���ȥ��.�������jsp
			//�������loginҲ����error
			if (!(requestName.equals("login")||requestName.equals("error")||requestName.equals("index"))) {
				if (CheckLoginUtil.isLogin(httpServletRequest)) {
					//���û�Ҫ���ʵ�.jspҳ����һ��ӳ��
					//�����ѵ�¼�û�����main.jsp ��ô������forward��LoginAction.do
					//���ѵ�¼�û�������ת��LoginAction.do ��ʱ��.���Բ��ý�����֤����ж�
					switch(requestName){
						case "main":
							httpServletResponse.sendRedirect("/user/LoginAction.do");
							break;
						case "userManage":
							httpServletRequest.getRequestDispatcher("/user/UserManageAction.do")
							.forward(httpServletRequest, httpServletResponse);
							break;
					}
					//�������userManage.jsp ��ô����forwar��UserManageAction.do 
				} else{
					//���Ŀ¼��û��Ĭ�ϵ���ʾҳ��,����:index.jsp��.���û��ῴ��404ҳ��
					//httpServletRequest.setAttribute("errorMsg", "��Ч����");
					httpServletResponse.sendRedirect("404.html");
					return;
				}
			}
		} 
		//*******************��һ��У��END******************
		
		//*******************�ڶ���У��Start,���û���Action������У��******************
		if (requestPath.endsWith(".do")){
			//����û����͵���.do ��action����,����:AdduserAction��.��Ҫ����Ȩ���ж�
			//��νȨ���ж�,���ж��û����������ҵ��Action�Ƿ�����Ȩ���б���
			boolean flag = false;//Ĭ�ϱ�ʾ�û����߱���Ȩ��
		
		
			//�ֱ���pathName �� actionName
			String pathName = httpServletRequest.getServletPath();
			if (pathName.contains("imageCode")) {
				flag = true;
			} else {
				int theSecondXie = pathName.lastIndexOf("/");
		        int index = pathName.indexOf(".");  
		        String actionName = pathName.substring(theSecondXie+1,index);
		        //actionName �����û�Ҫ�����ĳ��ʵ����Action�ӿڵ�ʵ����
		        //������������ݿ���t_menu���action��һһ��Ӧ
		       
		        //***********************===========*************************
		        //1,�����LoginAction.do���������ص� ���� �����ж��û��Ƿ��¼���ɷ���
		        //�ж�actionName�Ƿ���needNotCheckDo������
		        if (Arrays.asList(needNotCheckDo).contains(actionName)) {
					flag = true;
				} else {//��������Action��������б�
					//2,�������LoginAction.do ��Ҫ���û��Ĳ���Ȩ�޽����ж�
		        	//2.1�����ж��û��Ƿ��¼,���û�е�¼,��ת����¼ҳ�沢����errormsg,��Ҫ��¼���ܷ���
					User user = null;
					if (CheckLoginUtil.isLogin(httpServletRequest)) {
						user = (User)httpServletRequest.getSession().getAttribute("user");
					} else {//����û�δ��¼
						//�û���¼��,����Ϣ������session��,session��һ����ʱ����Ч�ڵĴ����ں�̨
						//Ӧ�÷������ڴ��е�һ������.����ѵ�¼�û���ǰ̨��ʱ��δ���в���(û������ҳ��ִ��
						//�κ����̨�Ľ���).��̨Ӧ�÷�������Ϊ�ڴ�����,���Իᶨʱ���session���汣���
						//��ʱ��û�л���û���Ϣ.
						//���,ǰ̨���û���Ȼ��ͣ���ڵ�¼�ɹ���ҳ��,���Ǻ�̨�Ѿ���session�б���ĸ��û���Ϣע��.
						//�����������û�,���ڵ��ĳЩ�˵�ʱ,���Ǿ���������¼��ʧЧ,���µ�¼.
						//TODO ǰ̨Ч��Ӧ��д�ɵ���һ��С����(���û���д�û�������,Ȼ���ύ,��¼�ɹ���,���Լ�����
						//֮ǰ�ķ���)
						//,��������ת��login.jsp�������µ�¼.
						//�����Ajax��������Ĺ����з�����session����,�򵯿���ʾ�û����µ�¼
						String requestType = httpServletRequest.getHeader("X-Requested-With");
						
						if (requestType != null &&
								"XMLHttpRequest".equalsIgnoreCase(requestType)) {
							httpServletResponse.setStatus(911);//session��ʱ����״̬��911
							httpServletResponse.setHeader("sessionstatus", "timeout");
							System.out.println("������=======");
	//						httpServletResponse.addHeader("loginPath", "");
							return;
						} else {
							httpServletRequest.setAttribute("errorMsg", "����û�е�¼,���ȵ�¼!");
							httpServletRequest.getRequestDispatcher("login.jsp").
								forward(httpServletRequest, httpServletResponse);
							return;
						}
					}
					
					//����,�Գ�������Ա�������������
					if (user.getRole().getRoleNo() == Constants.SUPER_ADMIN_ROLE_NO) {
						flag = true;
					} else {
						//������߼��ǻ����û��ѵ�¼
						//��õ�ǰ�ѵ�¼�û��Ĺ����б�
						List<Menu> menuList = user.getRole().getMenuList();
						//System.out.println(menuList);
			        	//2.2 ������Ѿ���¼,���ж��û���Ȩ���б����Ƿ�����������Action,����Ѿ�����,����
			        	for (Menu menu : menuList) {
			        		if (actionName.equals(menu.getAction())) {
			        			flag = true;//��ʾ�û��߱��Ը������Ȩ��
			        			break;
			        		}
			        	}
					}
				}
			}
//	        System.out.println("&&&&&"+flag);
	      //***********************===========*************************
	        //����û���Ȩ���б���û�а�����ǰ������������Action,��֪�û��䲻�߱���Ȩ��
	        if (!flag) {
	        	//2.2.1 ���������,���ش���ҳ��,������errormsg,��֪�û�û�д�Ȩ��.(����Ϊ�˰�ȫ,������
				//��������֪�����Ӧ�����������,ֱ����ת��404ҳ��)
	        	httpServletRequest.setAttribute("errorMsg", "��û�д�Ȩ��");
	        	//�ض��򵽸�Ŀ¼,���Ŀ¼��û��Ĭ�ϵ���ʾҳ��,����:index.jsp��.���û��ῴ��404ҳ��
	        	httpServletRequest.getRequestDispatcher("error.jsp").forward(httpServletRequest, httpServletResponse);
				return;
	        } 
		}
		//*******************�ڶ���У��END******************
		
		//�������ǰ�������У����ͨ��,�����ִ��
		chain.doFilter(request, response);//������󴫵�
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
