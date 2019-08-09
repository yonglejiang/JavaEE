package com.cz.txl.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//ֻ��ʵ����Runnable�ӿڲ��ǿ����ύ��������е�����
public class SendTask implements Runnable {
	Log log = LogFactory.getLog(SendTask.class);
	private String to;
	private String subject;
	private String content;
	
	public SendTask(String to, String subject, String content) {
		super();
		this.to = to;
		this.subject = subject;
		this.content = content;
	}

	@Override
	public void run() {
		try {
			send_email();//�����ʼ�
		} catch (AddressException e) {
			log.error("���û�:"+to+"�����ʼ�ʱ,��ַ����:"+e.getMessage());
		} catch (IOException e) {
			log.error("���û�:"+to+"�����ʼ�ʱ,IO����:"+e.getMessage());
		} catch (MessagingException e) {
			log.error("���û�:"+to+"�����ʼ�ʱ,��Ϣ��ʽ����:"+e.getMessage());
		}
	}

	 /**
	  * �����ʼ�
	  * @param to 
	  *   ������
	  * @param subject
	  *   �ʼ�����
	  * @param content
	  *   �ʼ�����
	  * @throws IOException
	  * @throws AddressException
	  * @throws MessagingException
	  */
	 public void send_email()
	               throws IOException, AddressException, MessagingException {
	     Properties properties = new Properties();
	     InputStream resourceAsStream = null;
	     try {
	         // ��ȡ�����ļ�
	         resourceAsStream = SendMailTask.class.getClassLoader().getResourceAsStream("email.properties");
	         properties.load(resourceAsStream);
	     } finally {
	          if (resourceAsStream != null) {
	              resourceAsStream.close();
	          }
	     }
	     // System.err.println("properties:"+properties);
	     properties.put("mail.smtp.host", properties.get("mail.smtp.host"));
	     properties.put("mail.smtp.port", properties.get("mail.smtp.port"));
	     properties.put("mail.smtp.auth", "true");
	     Authenticator authenticator = new Email_Authenticator(properties.get(
	             "username").toString(), properties.get("password").toString());
	     javax.mail.Session sendMailSession = javax.mail.Session
	              .getInstance(properties, authenticator);
	     MimeMessage mailMessage = new MimeMessage(sendMailSession);
	     //���÷�����
	     mailMessage.setFrom(new InternetAddress(properties.get("username")
	              .toString(),"***"));
	     // ����������,Message.RecipientType.TO �����ˣ�Message.RecipientType.CC������
	     mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(
	               to,properties.get("username").toString()));
	     //����
	     mailMessage.setSubject(subject, "UTF-8");
	     //�����ʼ���������
	     mailMessage.setSentDate(new Date());
	     // MiniMultipart����һ��������
	     Multipart mainPart = new MimeMultipart();
	     // ����һ���ʼ������
	     BodyPart html = new MimeBodyPart();
	     html.setContent(content.trim(), "text/html; charset=utf-8");
	     mainPart.addBodyPart(html);
	     mailMessage.setContent(mainPart);
	     Transport.send(mailMessage);
	  }
}
