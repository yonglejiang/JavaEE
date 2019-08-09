package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class UserUpdateBaseAction implements Action {
	Log log = LogFactory.getLog(UserUpdateBaseAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//����requestת������Ҫ�����user
		User user = null;
		try {
			user = RequestFormConvertModelUtil.parseRequestToEntity(request, User.class);
		} catch (Exception e) {
			log.error("����ת���쳣"+e.getMessage());
		}
		
		//��DaoFactory���userdao
		UserDAO userDao = DaoFactory.getUserDao();
		
		//ִ��update����
		
		//����Ҫ���ظ�ǰ̨�Ľ��,JSONObject ���Բ���json����
		JSONObject result = new JSONObject();
		try {
			userDao.update(user);
			int userId = Integer.parseInt(request.getParameter("id"));
			userDao.deleteUserRole(userId);
			
			//2,�����µ��û���ɫ��ϵ
			String roleId = request.getParameter("roleno");
			int roleNo = Integer.parseInt(roleId);
			System.out.println(userId+"-=======");;
			userDao.addUserRole(userId, roleNo);
			
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("�޸Ĳ˵��쳣"+e.getMessage());
		}
		
		//����ͳһ������󽫽�����
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("�������쳣:"+e.getMessage());
		}
		
		return null;
	}

}
