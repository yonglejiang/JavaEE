package com.cz.txl.action.impl.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.util.EncryptUtil;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class UserAddAction implements Action{
	Log log = LogFactory.getLog(UserAddAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,ȡ��request�еı�,Ȼ�󽫱��Զ�ת��Ϊ��Ҫ����Ķ���
		User user = null;
		try {
			user = RequestFormConvertModelUtil.parseRequestToEntity(request, User.class);
		} catch (Exception e) {
			log.error("����ת���쳣>>>>>>>"+e.getMessage());
		}
		
		//2,��daoFactory�еõ�UserDao
		UserDAO userDao = DaoFactory.getUserDao();
		
		//������Ҫǰ̨���ص�json��Ϣ
		JSONObject result = new JSONObject();
		
		try {
			//�����ݿ�������û�ǰ,��Ҫ����������м���
			user.setPassword(EncryptUtil.encryptMd5(user.getPassword()));
			
			//�����²������ݵ�id
			int userId = userDao.insert(user);
			
			//�����û��ɹ���,���û����ɫ��������,ִ��
			//1,�Ƚ��ԭ�����û�-��ɫ��ϵ
			//������ʱ����Ϊ�û�û�н�ɫ,���Բ���ɾ��
			//userDao.deleteUserRole(userId);
			
			//2,�����µ��û���ɫ��ϵ
			String roleId = request.getParameter("roleno");
			int roleNo = Integer.parseInt(roleId);
			System.out.println(userId+"-=======");;
			userDao.addUserRole(userId, roleNo);
			//�������ɹ�,��Ҫ��ǰ̨���سɹ�����Ϣ
			result.put("success", true);//���success����ǰ̨��Ҫ���ܵ���Ϣ
		} catch (Exception e) {
			log.error("ִ�в����쳣======"+e.getMessage());
		}
		
		try {
			response.getWriter().print(result.toString());
			
		} catch (IOException e) {
			log.error("����쳣......."+e.getMessage());
		}
		
		return null;
	}

}
