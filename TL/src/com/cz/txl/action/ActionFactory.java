package com.cz.txl.action;

import com.cz.txl.common.Constants;

/** 
 * �ù������ݴ����Action���ַ��ؾ����ҵ��actionʵ�� 
 * 
 */ 
public class ActionFactory {
	//����ģʽ������Ҫ��������  
    private ActionFactory(){  
    }  
    //��ʵ�����ʷ������õ�ActionFactory����  
    public static ActionFactory getActionFactory(){  
        if(af == null){  
            af = new ActionFactory();  
        }  
        return af;  
    }  
    /** 
     * ���ݾ����Action�����ִ���Action���� 
     * @param ActionClassName �������Action��ȫ�� 
     * @return��Action���Ͷ��� 
     */  
/*    public Action getAction(String actionName){
    	String actionClassName = Constants.PACKAGE_PATH + actionName;
    	//ʵ�ַ���
        Action action = null;  
        try{
            action = (Action) Class.forName(actionClassName).newInstance();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return action;  
    }  */
    
    public Action getAction(String actionPath,String actionName){
    	String actionClassName = Constants.PACKAGE_PATH + actionPath+ "." + actionName;
    	//ʵ�ַ���
        Action action = null;  
        try{
            action = (Action) Class.forName(actionClassName).newInstance();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return action;  
    }
 
    private static ActionFactory af; 
}
