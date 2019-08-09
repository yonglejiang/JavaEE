package com.cz.txl.action.impl.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class MenuUpdateAction implements Action {
	Log log = LogFactory.getLog(MenuUpdateAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//����requestת������Ҫ�����menu
		Menu menu = null;
		try {
			menu = RequestFormConvertModelUtil.parseRequestToEntity(request, Menu.class);
		} catch (Exception e) {
			log.error("����ת���쳣"+e.getMessage());
		}
		
		//��DaoFactory���menudao
		MenuDAO menuDao = DaoFactory.getMenuDao();
		
		//ִ��update����
		
		//����Ҫ���ظ�ǰ̨�Ľ��,JSONObject ���Բ���json����
		JSONObject result = new JSONObject();
		try {
			menuDao.update(menu);
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
