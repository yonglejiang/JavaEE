package com.cz.txl.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;

public class ScheduleTask {
	Log log = LogFactory.getLog(ScheduleTask.class);
	public void execute() throws Exception{
		//1,��ѯ������Ҫ���͵��û������б�
		ScheduleDAO schDao = DaoFactory.getScheduleDao();
		List<Map> mapList = schDao.getMapList();
		SendMailTask task = new SendMailTask();
		for (int i = 0;i<mapList.size();i++) {
			Map map = mapList.get(i);
			log.info("��ǰ�������û�["+map.get("username")+"]�����ʼ�...");
			
			task.send_email((String)map.get("email"), (String)map.get("schName"), (String)map.get("schDesc"));
		}
	}
}
