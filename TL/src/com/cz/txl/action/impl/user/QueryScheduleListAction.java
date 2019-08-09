package com.cz.txl.action.impl.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.util.DateUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QueryScheduleListAction implements Action {
	Log log = LogFactory.getLog(QueryScheduleListAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		try{
			UserDAO userDao = DaoFactory.getUserDao();
			User user = (User)request.getSession().getAttribute("user");
			//���ݵ�¼�û���userId ��ѯt_user_schedule
			//�ռ�Ҫ��ʾ����ҳ�ϵ���Ϣ,set��request��
			List<Schedule> schList = userDao.queryByUserId(user.getId());
			
			//ͨ����̨�޸����ݿ���б���ĺ����ճ�ʱ��Ϊ"YYYY-mm-dd"
			for (int i = 0;i<schList.size();i++) {
				Date date = new Date(Long.parseLong(schList.get(i).getSchTime()));
				schList.get(i).setSchTime(DateUtil.getDateDDMMYYYY(date));
			}
			
			JSONObject result = new JSONObject();
			result.put("success", true);
			
			JSONArray array = JSONArray.fromObject(schList);
			result.put("schList", array);
			ResponseUtil.write(response, result);
			
			
		} catch (Exception e) {
			log.error("��ѯ�ճ��쳣"+e.getMessage());
		}
		
		
		return null;
	}

}
