package com.cz.txl.action.impl.role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class RoleMenuUpdateAction implements Action {
	Log log = LogFactory.getLog(RoleMenuUpdateAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//�õ���ѡ��Ȩ���б�id
		String menuIds = request.getParameter("menuIds");
		
		//�õ�Ҫ�޸ĵĽ�ɫ��id
		String roleId = request.getParameter("id");
		
		//����Ҫ���ظ�ǰ̨�Ľ��,JSONObject ���Բ���json����
		JSONObject result = new JSONObject();
		///1,��t_role_menu ��������,����roleIdɾ���ý�ɫ������������Ȩ��
		RoleDAO roleDao = DaoFactory.getRoleDao();
		try {
			roleDao.deleteRoleMenu(Integer.parseInt(roleId));
			//2,�����µ�Ȩ���б�,���²���������
			String[] ids = menuIds.split(",");
			roleDao.addRoleMenuBatch(Integer.parseInt(roleId), ids);
			result.put("success", true);
		} catch (Exception e) {
			log.error("�༭��ɫ�˵��������쳣"+e.getMessage());
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("��������쳣");
		}
		return null;
	}

}
