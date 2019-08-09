package com.cz.txl.action.impl.schedule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.PageBean;
import com.cz.txl.util.RequestFormConvertModelUtil;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

//���Action���������з���schedule�б��
public class ScheduleListAction implements Action{
	Log log = LogFactory.getLog(ScheduleListAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//��request�л�÷�ҳ�Ĳ���
		PageBean pageBean = new PageBean(1,10);//��ҳ��Ĭ������,�ӵ�һ��ʼ,ÿҳʮ��
		try {
			pageBean = RequestFormConvertModelUtil.parseRequestToEntity(request, PageBean.class);
		} catch (Exception e) {
			log.error("ת������pageBeanʱ�쳣"+e.getMessage());
		}
		
		//����scheduleDao ��ȡscheduleList
		ScheduleDAO scheduleDao = DaoFactory.getScheduleDao();
		
		//����һ������Ϊ0��list��ֹǰ̨ת��ʱ��Ϊ��ָ���׳��쳣
		List<Schedule> scheduleList = new ArrayList<Schedule>();
		try {
			scheduleList = scheduleDao.queryPage(pageBean.getStart(), pageBean.getRows());
		} catch (Exception e) {
			log.error("��ȡ�˵��б��쳣>>>>>>>"+e.getMessage());
		}
		
		//��ѯ���ܼ�¼��
		int total = 0;
		try {
			total = scheduleDao.queryTotal();
		} catch (Exception e) {
			log.error("��ȡ�ܼ�¼���쳣>>>>>>>"+e.getMessage());
		}
		
		//����Ҫ���͵�ǰ�ε�json��ʽ����
		JSONObject result = new JSONObject();
		JSONArray  jsonArray = JSONArray.fromObject(scheduleList);
		result.put("rows", jsonArray);//ǰ�λ��Զ���result�ж���rows��ʾ���б�
		result.put("total", total);//���ܼ�¼�����ݵ�ǰ̨ҳ��
		//����������������ͳһ���
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			log.error("��ҳ��������ʱ�����쳣:"+e.getMessage());
		}
		return null;
	}

}
