package com.cz.txl.action.impl.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.common.Constants;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.model.Role;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.util.CheckLoginUtil;
import com.cz.txl.util.DateUtil;
import com.cz.txl.util.EncryptUtil;

/**
 * �����û���¼��action��
 * @author Administrator
 *
 */
public class LoginAction implements Action {
	Log log = LogFactory.getLog(LoginAction.class);
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String imageCode = request.getParameter("imageCode");
		
		//Ϊ�˷�ֹ�����ƹ�ǰ̨��js������ж����ύ
		//�����ж��Ƿ����ѵ�¼�û����͵�����,�����,ֱ��forward��main.jsp
		if (CheckLoginUtil.isLogin(request)) {
			return "main.jsp";//��ת��������ҳ�� 
		}else if (null == imageCode || "".equals(imageCode)) {
			request.setAttribute("errorMsg", "��֤�벻��Ϊ��");
			return "login.jsp";//��֤��Ϊ�շ��ص���¼ҳ��
		}  else {
			//��session�л����֤��,�����û��ϴ�����֤����бȽ�
			String sessionCode = 
					(String)request.getSession().getAttribute("imageCode");
			if (!imageCode.equals(sessionCode)) {
				request.setAttribute("errorMsg", "��֤����д����,����������");
				return "login.jsp";//��֤�����,���ص���¼ҳ�����µ�¼s
			} else {
				//�����û��������ݿ��в�ѯUser,Ȼ����ݻ��user���û��ϴ���password���бȽ�
				UserDAO userDao = DaoFactory.getUserDao();
				
				try {
					User user = userDao.queryByName(username);
					
					if (user == null) {
						request.setAttribute("errorMsg", "�û�������");
						return "login.jsp";//���ص���¼ҳ��
					} else {
						//1,���û����������������м���
						String encryptPass = EncryptUtil.encryptMd5(password);
						//2,�Ƚϼ��ܺ�����������ݿ��д洢�������Ƿ�һ��
						if (encryptPass.equals(user.getPassword())) {
							//��ѯ�û��Ľ�ɫ��Ȩ����Ϣ
							//�����û�id��ѯ��ɫ���
							int roleNo = userDao.getRoleNo(user.getId());
							//���ݽ�ɫ���,��ѯ��ɫ
							RoleDAO roleDao = DaoFactory.getRoleDao();
							Role role = roleDao.queryByNo(roleNo);
							//���ݽ�ɫid,��ѯ��ɫ��˵��Ĺ�����,�õ�menu_no,
							//��������menu_no ���menu�б�
							List<Menu> menuList = roleDao.queryMenusByRoleId(role.getId());
							
							//���õ�ǰ��¼�û���Ӧ�Ľ�ɫ�Ĺ����б�
							role.setMenuList(menuList);
							user.setRole(role);//��ֵ��ǰ��¼�û��Ľ�ɫ
							//1,�Ȱѵ�¼�ɹ����û���Ϣ(user)���õ�session��
							request.getSession().setAttribute(Constants.SESSEION_USER, user);
							
							//���ݵ�¼�û���userId ��ѯt_user_schedule
							//�ռ�Ҫ��ʾ����ҳ�ϵ���Ϣ,set��request��
							List<Schedule> schList = userDao.queryByUserId(user.getId());
							
							//ͨ����̨�޸����ݿ���б���ĺ����ճ�ʱ��Ϊ"YYYY-mm-dd"
							for (int i = 0;i<schList.size();i++) {
								Date date = new Date(Long.parseLong(schList.get(i).getSchTime()));
								schList.get(i).setSchTime(DateUtil.getDateDDMMYYYY(date));
							}
							
							request.setAttribute("schList", schList);
							return "main.jsp";//��ת��������ҳ�� 
						} else {
							request.setAttribute("errorMsg", "�������");
							return "login.jsp";//��ת����¼����
						}
					}
					
				} catch (Exception e) {
					log.error("��ѯ�û��쳣:"+e.getMessage());
				}
				
			}
		}
		
		return null;
	}

}
