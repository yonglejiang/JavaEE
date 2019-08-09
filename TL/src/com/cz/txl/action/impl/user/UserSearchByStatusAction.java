package com.cz.txl.action.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.PageBean;
import com.cz.txl.model.User;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserSearchByStatusAction implements Action {
	Log log = LogFactory.getLog(UserSearchByStatusAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		try{
			String status = request.getParameter("status");
			//��request�л�÷�ҳ�Ĳ���
			PageBean pageBean = new PageBean(1,10);//��ҳ��Ĭ������,�ӵ�һ��ʼ,ÿҳʮ��
			try {
				pageBean = RequestFormConvertModelUtil.parseRequestToEntity(request, PageBean.class);
			} catch (Exception e) {
				log.error("ת������pageBeanʱ�쳣"+e.getMessage());
			}
			
			//����userDao ��ȡuserList
			UserDAO userDao = DaoFactory.getUserDao();
			
			//����һ������Ϊ0��list��ֹǰ̨ת��ʱ��Ϊ��ָ���׳��쳣
			List<User> userList = new ArrayList<User>();
			try {
				String[] fiedList = {"user_status"};
				String[] fiedValue = {status};
				userList = userDao.queryPage(fiedList,fiedValue,pageBean.getStart(), pageBean.getRows());
			} catch (Exception e) {
				log.error("��ȡ�˵��б��쳣>>>>>>>"+e.getMessage());
			}
			
			//��ѯ���ܼ�¼��
			int total = 0;
			try {
				total = userDao.queryTotal();
			} catch (Exception e) {
				log.error("��ȡ�ܼ�¼���쳣>>>>>>>"+e.getMessage());
			}
			
			//����Ҫ���͵�ǰ�ε�json��ʽ����
			JSONObject result = new JSONObject();
			JSONArray  jsonArray = JSONArray.fromObject(userList);
			result.put("rows", jsonArray);//ǰ�λ��Զ���result�ж���rows��ʾ���б�
			result.put("total", total);//���ܼ�¼�����ݵ�ǰ̨ҳ��
			//����������������ͳһ���
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				log.error("��ҳ��������ʱ�����쳣:"+e.getMessage());
			}
			return null;
		}catch (Exception e) {
			
		}
		
		return null;
	}

}
