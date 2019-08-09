package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class UserRegCheckAction implements Action {
	Log log = LogFactory.getLog(UserRegCheckAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String username = request.getParameter("username");
			
			// �����û�����ѯ�û��Ƿ����
			UserDAO userDao = DaoFactory.getUserDao();
			User user = userDao.queryByName(username);
			
			//�����з��ص�json����
			JSONObject result = new JSONObject();
			if (user != null ) {
				result.put("success", false);
			} else{
				result.put("success", true);	
			}
			ResponseUtil.write(response, result);
			
			
		} catch (Exception e) {
			log.error("��ѯ�û��Ƿ���ڷ����쳣"+e.getMessage());
		}
		
		return null;
	}

}
