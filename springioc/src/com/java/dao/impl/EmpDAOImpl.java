package com.java.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.java.bean.Emp;
import com.java.dao.IEmpDAO;

public class EmpDAOImpl extends JdbcDaoSupport implements IEmpDAO {

	
	public void testUpdate(String ename, Integer empno) {
		EmpUpdateEnameById empUpdate = new EmpUpdateEnameById(this.getDataSource());
		int i = empUpdate.update(ename, empno);
		System.out.println(i);
	}
	
	public void testFindEmp() {
		EmpQuery query = new EmpQuery(this.getDataSource());
		List<Emp> elist = query.execute(7844);
		for(Emp emp : elist){
			System.out.println(emp);
		}
	}

	public void testSelect() {
		EmpSelect es = new EmpSelect(this.getDataSource());
		List list = es.execute(30);
		System.out.println(list);
		
	}
	
	
}
