package com.cz.txl.util;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class GuDingTimer {

	public void testTimer() throws Exception  {

        // ʱ����
        Calendar startDate = Calendar.getInstance();

        //���ÿ�ʼִ�е�ʱ��Ϊ ĳ��-ĳ��-ĳ�� 00:00:00
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE), 17, 20, 0);

        // 1Сʱ�ĺ����趨
        long timeInterval = 60 * 60 * 1000;

        // ��ʱ��ʵ��
        Timer t = new Timer();

        t.schedule(new TimerTask() {

            public void run() {

                // ��ʱ����Ҫִ�еĴ����
                System.out.println("��ʱ����Ҫִ�еĴ���!"+DateUtil.getStringDate());
            }

        // �趨�Ķ�ʱ����15:10�ֿ�ʼִ��,ÿ�� 1Сʱִ��һ��.
        }, startDate.getTime(), timeInterval ); //timeInterval ��һ��ĺ�������Ҳ��ִ�м��

    }
	
	public static void main(String[] args) throws Exception {
		GuDingTimer gd = new GuDingTimer();
		gd.testTimer();
    }
}
