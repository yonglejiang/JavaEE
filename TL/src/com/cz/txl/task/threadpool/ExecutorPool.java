package com.cz.txl.task.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//�̳߳���,��Ҫ��������,һ�����������,һ�����̳߳ط���ӿ�
public class ExecutorPool {
	Log log = LogFactory.getLog(ExecutorPool.class);
	
	private static ExecutorPool instance;//����
	
	BlockingQueue<Runnable> taskQueue;//�������,���������ύ������,�γ�һ���������
	ExecutorService service;//�̳߳ط���ӿ�,

	private ExecutorPool() {
		super();
	}

	private ExecutorPool(BlockingQueue<Runnable> taskQueue, ExecutorService service) {
		super();
		this.taskQueue = taskQueue;
		this.service = service;
	}
	
	//�÷�����һ�α��뱻����������.��ȷ�������ĵ���ʱ�Ѿ�����ʼ��
	public static synchronized ExecutorPool getInstance(BlockingQueue<Runnable> taskQueue, ExecutorService service){
		if (instance == null) {
			instance = new ExecutorPool(taskQueue, service);
		}
		return instance;
	}
	//��Ϊ����ķ����Ѿ�����������ʱͨ����������ʼ�����.���������ϸ÷������Է��ĵ���
	public static ExecutorPool getInstance(){
		return instance;
	}
	
	//�첽�������ķ���,��ν�첽���.���ǽ�������ӵ����������.��û������ִ��
	public void addTask(Runnable task){
		log.info("���̳߳����������");
		service.submit(task);//�÷�����������ִ��task
	}
	
	public void close() throws InterruptedException{
		log.info("�̳߳��յ��˹رյ�����...��ʼ�ر�");
		//��Ӧ�ùرյ�ʱ��,��Ҫ���̳߳ؽ��а�ȫ�Ĺر�
		//������Ҫ�ж���taskQueue���Ƿ���δ��ɵ�����
		if (taskQueue != null && taskQueue.size() > 0) {
			//����if����ʾ����δ��ɵ�����.
			log.info("�̳߳��л���("+taskQueue.size()+")��δ��ɵ�����");
			log.info("��������δ��ɵ����������......");
			Thread.sleep(5000);//�������ʱ�����̼߳���ִ��
		}
		
		log.info("������...��ʼ�ر�");
		//Ȼ��ʼ�ر��̳߳�
		if (service != null) {
			log.info("...���ڹر�");
			service.shutdown();//�ر��̳߳�
			log.info("�ر���...");
		}
		taskQueue = null;//����������
		log.info("�̳߳عر����");
	}
	
	
	
	
}
