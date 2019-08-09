package com.java.server.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.java.dao.IEmpDAO;
import com.java.server.IEmpService;

public class EmpServiceImpl implements IEmpService {

	private IEmpDAO empDao;

	public void setEmpDao(IEmpDAO empDao) {
		this.empDao = empDao;
	}

	
	public void test() {
		//empDao.testUpdate("huang", 3001);
		
		//empDao.testFindEmp();
		
		empDao.testSelect();
	}
	
	public static void main(String[] args) {
		ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
		IEmpService empService = (IEmpService)act.getBean("empService");
		empService.test();
	}
	
}
