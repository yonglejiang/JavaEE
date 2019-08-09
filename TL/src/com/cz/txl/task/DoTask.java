package com.cz.txl.task;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.schedule.ScheduleDAO;
import com.cz.txl.task.threadpool.ExecutorPool;

//���������иɻ��
public class DoTask extends TimerTask{
	Log log = LogFactory.getLog(DoTask.class);
	
	private ExecutorPool pool;//��Ҫ����DoTask��ִ���������Ҫ���ݽ���һ���̳߳ض��������

	public DoTask(ExecutorPool pool) {
		super();
		this.pool = pool;
	}

	@Override
	public void run(){
		// ��ʱ����Ҫִ�еĴ����
        log.info("��ʼִ���ʼ���������");
		//1,��ȡ��Ҫ���͵��ʼ��б�
        ScheduleDAO schDao = DaoFactory.getScheduleDao();
		try {
			List<Map> mapList = schDao.getMapList();
			
			//2,��Ҫ���͵��ʼ��б������������ܽ��ܵĲ���
			for (int i = 0;i<mapList.size();i++) {
				Map map = mapList.get(i);
				//����һ��sendTask����.�����ύ���̳߳���
				SendTask task = new SendTask(
								(String)map.get("email"), 
								(String)map.get("schName"), 
								(String)map.get("schDesc")
								);
				pool.addTask(task);//���̳߳�����ӿ�ִ�е�����
			}
			//3,�ύ����pool��
		} catch (Exception e) {
			log.error("�ύ����ʱ�����쳣"+e.getMessage());
		}
		
        log.info("�ʼ������������");
	}
	
}
