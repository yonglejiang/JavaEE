package com.cz.txl.action.impl.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class UserActiveAction implements Action {
	Log log = LogFactory.getLog(UserActiveAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//���ǰ̨�������Ҫɾ�����ݵ�id�б�
		String ids = request.getParameter("ids");
		
		String[] idArray = ids.split(",");//��ǰ̨�������","�ŷָ����ַ���ת��Ϊid����
		
		//��DaoFactory ���userDao
		UserDAO userDao = DaoFactory.getUserDao();
		
		//����Ҫ���ظ�ǰ̨��json����
		JSONObject result = new JSONObject();
		try {
			userDao.activeBatch(idArray);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("����ɾ���쳣"+e.getMessage());
		}
		
		//����ͳһ������߽�������ص�ǰ̨
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("�������쳣"+e.getMessage());
		}
		
		return null;
	}

}
