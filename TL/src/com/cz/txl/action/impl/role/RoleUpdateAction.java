package com.cz.txl.action.impl.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.model.Role;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class RoleUpdateAction implements Action {
	Log log = LogFactory.getLog(RoleUpdateAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//����requestת������Ҫ�����role
		Role role = null;
		try {
			role = RequestFormConvertModelUtil.parseRequestToEntity(request, Role.class);
		} catch (Exception e) {
			log.error("����ת���쳣"+e.getMessage());
		}
		
		//��DaoFactory���roledao
		RoleDAO roleDao = DaoFactory.getRoleDao();
		
		//ִ��update����
		
		//����Ҫ���ظ�ǰ̨�Ľ��,JSONObject ���Բ���json����
		JSONObject result = new JSONObject();
		try {
			roleDao.update(role);
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			log.error("�޸Ľ�ɫ�쳣"+e.getMessage());
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
