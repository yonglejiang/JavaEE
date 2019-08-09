package com.cz.txl.listener;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import com.cz.txl.task.DoTask;
import com.cz.txl.task.ScheduleTask;
import com.cz.txl.task.threadpool.ExecutorPool;

/**
 * Application Lifecycle Listener implementation class ThreadPoolSendMailListener
 *
 */
@WebListener
public class ThreadPoolSendMailListener implements ServletContextListener {
	Log log = LogFactory.getLog(ThreadPoolSendMailListener.class);
	private ExecutorPool pool;
	// ��ʱ��ʵ��
    Timer t = new Timer();
    public ThreadPoolSendMailListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
        BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
       
        //��õ�ǰ���Ե�cup����
        int cupCounts = Runtime.getRuntime().availableProcessors();
        //TODO 4,8,30��Щ����Ӧ�ô�web.xml����properties�ļ���ȡ.
        ExecutorService service = new ThreadPoolExecutor(
        		cupCounts, //4��ʾ�½����̳߳���ʼ�����ĸ�����߳�
        		cupCounts * 2, //8��ʾ���̳߳����֧�ֵ��̲߳�������
        		30, //30��.����4���߳�ʱ,��Щ�̵߳Ĵ��ʱ��
        		TimeUnit.SECONDS, //���������������30�ĵ�λ,��������
        		taskQueue,//����̳߳���Ҫִ�е��������ڵ��������
        		new ThreadPoolExecutor.CallerRunsPolicy()//ʧ��ʱ���õĲ���,��������������
        		);
        //����������������󹹽��̳߳�
        pool = ExecutorPool.getInstance(taskQueue, service);
        
        //����DoTask����Ҫִ��������в����ύ����
        DoTask dt = new DoTask(pool);
        
   	   // ʱ����
       Calendar startDate = Calendar.getInstance();
       //���ÿ�ʼִ�е�ʱ��Ϊ ĳ��-ĳ��-ĳ�� 00:00:00
       startDate.set(startDate.get(Calendar.YEAR), 
    		   startDate.get(Calendar.MONTH), 
    		   startDate.get(Calendar.DATE),
    		   12, 26, 0);//�ֱ��ʾ18��,14��0��. TODO ����������Ӧ�ô������ļ���ȡ
       // 1Сʱ�ĺ����趨
       long timeInterval = 60 * 60 * 1000 * 24;
       // �趨�Ķ�ʱ����15:10�ֿ�ʼִ��,ÿ�� 1Сʱִ��һ��.
       //timeInterval ��һ��ĺ�������Ҳ��ִ�м��
       //���ȶ�ʱ����������ִ��
       t.schedule(dt, startDate.getTime(),timeInterval); 
    }
	
}
