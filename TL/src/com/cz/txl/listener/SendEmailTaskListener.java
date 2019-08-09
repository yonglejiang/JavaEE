package com.cz.txl.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.task.ScheduleTask;

/**
 * Application Lifecycle Listener implementation class SendEmailTaskListener
 *
 */
//@WebListener
public class SendEmailTaskListener implements ServletContextListener {
	Log log = LogFactory.getLog(SendEmailTaskListener.class);
	// ��ʱ��ʵ��
    Timer t = new Timer();
    /**
     * Default constructor. 
     */
    public SendEmailTaskListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  {
    	//Ӧ�ùرյ�ʱ��,��ֹ��ʱ��
        t.cancel();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    	 // ʱ����
        Calendar startDate = Calendar.getInstance();

        //���ÿ�ʼִ�е�ʱ��Ϊ ĳ��-ĳ��-ĳ�� 00:00:00
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 18, 14, 0);

        // 1Сʱ�ĺ����趨
        long timeInterval = 60 * 60 * 1000 * 24;


        t.schedule(new TimerTask() {

            public void run() {

                // ��ʱ����Ҫִ�еĴ����
                log.info("��ʼִ���ʼ���������");
                
                //��ʼ�����ʼ���������
                ScheduleTask task = new ScheduleTask();
                try {
					task.execute();
				} catch (Exception e) {
					log.error("�ʼ�����ʧ��"+e.getMessage());
				}
                log.info("�ʼ������������");
                
            }

        // �趨�Ķ�ʱ����15:10�ֿ�ʼִ��,ÿ�� 1Сʱִ��һ��.
        }, startDate.getTime(), timeInterval ); //timeInterval ��һ��ĺ�������Ҳ��ִ�м��
    }
	
}
