package com.cz.txl.action.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.ComboBoxData;
import com.cz.txl.model.Role;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;

public class UserRoleQueryAction implements Action{
	Log log = LogFactory.getLog(UserRoleQueryAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		RoleDAO roleDao = DaoFactory.getRoleDao();
		try {
			List<Role> roleList = roleDao.queryAll();
			
			int roleNo = 0;
			//�ж��Ƿ�request���в���id
			if (null != request.getParameter("id")) {
				int userId = Integer.parseInt(request.getParameter("id"));
				//����userId��t_user_role���в�ѯ��Ҫ�޸ĵ��û���ǰ�Ľ�ɫ
				UserDAO userDao = DaoFactory.getUserDao();
				roleNo = userDao.getRoleNo(userId);
			}
			System.out.println("===="+roleNo);
			List<ComboBoxData> cbdList = new ArrayList<ComboBoxData>();
			cbdList.add(new ComboBoxData(0, null, "--��ѡ���ɫ--", true));
			for (int i = 0;i<roleList.size();i++) {
				ComboBoxData cbd = new ComboBoxData();
				Role role = roleList.get(i);
				cbd.setId(role.getRoleNo());
				cbd.setText(role.getRoleName());
				//��Ҫ�����û��ĵ�ǰ��ɫ���ж��Ƿ���ѡ��
				if (roleNo>0 && role.getRoleNo() == roleNo) {
					cbd.setSelected(true);
				}
				cbdList.add(cbd);
			}
			
			JSONArray result = JSONArray.fromObject(cbdList);
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("�����쳣"+e.getMessage());
		}
		
		return null;
	}

}
