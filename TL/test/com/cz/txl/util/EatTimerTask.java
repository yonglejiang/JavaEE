package com.cz.txl.util;


import java.util.Timer;
import java.util.TimerTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/*
 * ��ʱ�Է�
 * */
public class EatTimerTask extends TimerTask {
    
    //�Է�ʱ��
    private static List<Integer> eatTimes;
    /*
     * ��̬��ʼ��
     * */
    static {
        initEatTimes();
    }
    
    /*
     * ��ʼ���Է�ʱ��
     * */
    private static void initEatTimes(){
        eatTimes = new ArrayList<Integer>();
        eatTimes.add(8);
        eatTimes.add(12);
        eatTimes.add(17);
    }

    /*
     * ִ��
     * */
    @Override
    public void run() {
        // TODO Auto-generated method stub
        Calendar calendar = Calendar.getInstance();
        System.out.println("����Ƿ��˳Է��ĵ�");
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(eatTimes.contains(hour))
        {
            System.out.println("���ˣ��Է�...");
        }
    }

    public static void main(String[] arg){
        TimerTask task = new EatTimerTask();
        Calendar  calendar = Calendar.getInstance();    
        
        
        Date firstTime = calendar.getTime();
        //�����1Сʱ
        long period = 1000;    
        //����ʱ��ÿ����һ��
        //period = 1000 * 60;        
        
        Timer timer = new Timer();        
        timer.schedule(task, firstTime, period);
    }
}