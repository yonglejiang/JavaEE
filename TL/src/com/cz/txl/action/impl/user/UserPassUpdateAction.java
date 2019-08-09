package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.util.EncryptUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

//�޸��û������Action
public class UserPassUpdateAction implements Action {
	Log log = LogFactory.getLog(UserPassUpdateAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//TODO ���û���������뼰ȷ��������ж�����֤
		String userId = request.getParameter("id");
		String password = request.getParameter("password");
		
		//����daoִ���޸Ĳ���
		UserDAO userDao = DaoFactory.getUserDao();
		JSONObject result = new JSONObject();
		try {
			userDao.updatePass(Integer.parseInt(userId), EncryptUtil.encryptMd5(password));
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("�޸����뷢���쳣"+e.getMessage());
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("�������쳣"+e.getMessage());
		}
		return null;
	}

}
