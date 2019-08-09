package com.cz.txl.util;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.cz.txl.task.SendMailTask;

import junit.framework.TestCase;

public class TestSendMailTask extends TestCase{
	public void testSendMain() throws AddressException, IOException, MessagingException{
//		SendMailTask task = new SendMailTask();
//		task.send_email("810390405@qq.com", "你好", "我是你哥");;
	}
	
	public static void main(String[] args)
	{
		SendMailTask task = new SendMailTask();
		try {
			task.send_email("810390405@qq.com", "你好", "我是奥斑马");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}
}
