package com.cz.txl.action.impl.role;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.model.Role;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class RoleAddAction implements Action{
	Log log = LogFactory.getLog(RoleAddAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,ȡ��request�еı�,Ȼ�󽫱��Զ�ת��Ϊ��Ҫ����Ķ���
		Role role = null;
		try {
			role = RequestFormConvertModelUtil.parseRequestToEntity(request, Role.class);
		} catch (Exception e) {
			log.error("����ת���쳣>>>>>>>"+e.getMessage());
		}
		
		//2,��daoFactory�еõ�RoleDao
		RoleDAO roleDao = DaoFactory.getRoleDao();
		
		//������Ҫǰ̨���ص�json��Ϣ
		JSONObject result = new JSONObject();
		
		try {
			roleDao.insert(role);
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
