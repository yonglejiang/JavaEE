package com.cz.txl.action.impl.schedule;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class ScheduleAddAction implements Action{
	Log log = LogFactory.getLog(ScheduleAddAction.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,ȡ��request�еı�,Ȼ�󽫱��Զ�ת��Ϊ��Ҫ����Ķ���
		Schedule schedule = null;
		try {
			schedule = RequestFormConvertModelUtil.parseRequestToEntity(request, Schedule.class);
			System.out.println(schedule.getSchTime());
		} catch (Exception e) {
			log.error("����ת���쳣>>>>>>>"+e.getMessage());
		}
		
		//2,��daoFactory�еõ�ScheduleDao
		ScheduleDAO scheduleDao = DaoFactory.getScheduleDao();
		
		//������Ҫǰ̨���ص�json��Ϣ
		JSONObject result = new JSONObject();
		
		try {
			int schId = scheduleDao.insert(schedule);
			
			//���ݲ���ɹ���schId��t_user_schedule���в���һ����¼
			User user = (User)request.getSession().getAttribute("user");
			//����������һ���¼�¼,ɾ���ճ̵�ʱ��,��Ҫһ��ɾ���������еļ�¼
			scheduleDao.addUserSchedul(schId, user.getId());
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
