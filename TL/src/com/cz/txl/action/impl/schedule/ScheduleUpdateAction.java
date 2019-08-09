package com.cz.txl.action.impl.schedule;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.model.Schedule;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONObject;

public class ScheduleUpdateAction implements Action {
	Log log = LogFactory.getLog(ScheduleUpdateAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		//����requestת������Ҫ�����schedule
		Schedule schedule = null;
		try {
			schedule = RequestFormConvertModelUtil.parseRequestToEntity(request, Schedule.class);
		} catch (Exception e) {
			log.error("����ת���쳣"+e.getMessage());
		}
		
		//��DaoFactory���scheduledao
		ScheduleDAO scheduleDao = DaoFactory.getScheduleDao();
		
		//ִ��update����
		
		//����Ҫ���ظ�ǰ̨�Ľ��,JSONObject ���Բ���json����
		JSONObject result = new JSONObject();
		try {
			scheduleDao.update(schedule);
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
