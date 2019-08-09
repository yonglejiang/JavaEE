package com.cz.txl.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.db.JdbcUtil;

@WebListener
public class InitListener implements ServletContextListener {
	Log log = LogFactory.getLog(InitListener.class);//����log
    public InitListener() {
    }

    public void contextDestroyed(ServletContextEvent arg0)  { 
    	log.warn("--ϵͳ�����ر�,��Ҫ�ر�����Դ--");
    	//�ж�����Դ�Ƿ�Ϊ��
    	if (JdbcUtil.getDataSource() != null) {
    		try {
    			//����java�ķ�������ֹ�ǿ�ƹر�����Դ
				JdbcUtil.getDataSource().close();
			} catch (Exception e) {
				log.error("--�ر�����Դʱ�����쳣:--"+e.getMessage());
			} 
    	}
    	
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
         log.info("--ϵͳ������--");
         //��������ʱ�����һ�θ÷���,���ɵ����ŵ��ڴ���,�Ա�����ʹ��ʱ�����ٴ�ִ��
         JdbcUtil.getDataSource();
    }
	
}
