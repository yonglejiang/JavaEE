package com.cz.txl.action.impl.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.common.Constants;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.user.UserDAO;
import com.cz.txl.model.User;
import com.cz.txl.task.SendTask;
import com.cz.txl.task.threadpool.ExecutorPool;
import com.cz.txl.util.EncryptUtil;
import com.cz.txl.util.RequestFormConvertModelUtil;

import net.sf.json.JSONObject;

public class UserRegAction implements Action {
	Log log = LogFactory.getLog(UserRegAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//1,ȡ��request�еı�,Ȼ�󽫱��Զ�ת��Ϊ��Ҫ����Ķ���
		User user = null;
		try {
			user = RequestFormConvertModelUtil.parseRequestToEntity(request, User.class);
		} catch (Exception e) {
			log.error("����ת���쳣>>>>>>>"+e.getMessage());
		}
		
		//2,��daoFactory�еõ�UserDao
		UserDAO userDao = DaoFactory.getUserDao();
		
		//������Ҫǰ̨���ص�json��Ϣ
		JSONObject result = new JSONObject();
		
		try {
			//�����ݿ�������û�ǰ,��Ҫ����������м���
			user.setPassword(EncryptUtil.encryptMd5(user.getPassword()));
			
			user.setUserStatus(2);//��ע����û�״̬Ĭ����2,���
			
			//�����²������ݵ�id
			int userId = userDao.reg(user);
			
			//�����û��ɹ���,���û����ɫ��������,ִ��
			//1,�Ƚ��ԭ�����û�-��ɫ��ϵ
			//������ʱ����Ϊ�û�û�н�ɫ,���Բ���ɾ��
			//userDao.deleteUserRole(userId);
			
			result.put("success", true);//���success����ǰ̨��Ҫ���ܵ���Ϣ
			
			//TODO �����Ա����һ�������ʼ�,��֪����Ҫ������û�
			//1.����ע����û�����һ���ʼ�
			ExecutorPool pool = ExecutorPool.getInstance();
			
			SendTask task = new SendTask(
					user.getEmail(), 
					"ͨ��¼����ϵͳע��ɹ�֪ͨ��Ϣ,����ظ�!", 
					"��ϲ��,�ɹ�ע��ͨ��¼����ϵͳ,�����û�����:"+user.getUsername()
					+"\n����������:"+user.getPassword()
					);
			pool.addTask(task);//���ʼ�����������ӵ��ʼ����Ͷ�����,�̳߳ػ��Զ������ö���.
			
			
			//2,�����Ա����һ���ʼ�,���ѹ���Ա�����û�����
			List<User> adminUserList = userDao.queryAllAdminUser(Constants.SUPER_ADMIN_ROLE_NO);//TODO ���ݽ�ɫ��ѯ����Ա�б�
			for (int i = 0;i<adminUserList.size();i++) {
				User adminUser = adminUserList.get(i);
				SendTask tipTask = new SendTask(
						adminUser.getEmail(), 
						"���û�ע��֪ͨ!", 
						"�����û�:" + user.getUsername() + "ע��"
						+ ",������ʱ���!"
						);
				pool.addTask(tipTask);//��������Ա���͵���ʾ������ӵ�������
				//TODO �����Ա����һ����Ϣ����,��֪�������û�ע��
			}
		} catch (Exception e) {
			log.error("ִ�в����쳣======"+e.getMessage());
		}
		
		try {
			response.getWriter().print(result.toString());
			
		} catch (IOException e) {
			log.error("����쳣......."+e.getMessage());
		}
		
		return null;
	}

}
