
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
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
public class SendMailTask {
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
 public static void send_email(String to, String subject, String content)
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
//ͨ�����캯�����������֤��Ϣ
class Email_Authenticator extends Authenticator {
    String userName = null;
    String password = null;
    public Email_Authenticator() {
    }
    public Email_Authenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}