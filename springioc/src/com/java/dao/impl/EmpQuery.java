package com.java.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import com.java.bean.Emp;

public class EmpQuery extends MappingSqlQuery<Emp> {

	public EmpQuery(DataSource dataSource){
		this.setDataSource(dataSource);
		String sql = "select * from emp where empno = ?";
		this.setSql(sql);
		this.declareParameter(new SqlParameter(Types.INTEGER));
		this.compile();
	}
	
	protected Emp mapRow(ResultSet rs, int arg1) throws SQLException {
		Emp emp = new Emp();
		emp.setEmpno(rs.getInt("empno"));
		emp.setEname(rs.getString("ename"));
		emp.setJob(rs.getString("job"));
		emp.setMgr(rs.getInt("mgr"));
		emp.setHiredate(rs.getDate("hiredate"));
		emp.setSal(rs.getDouble("sal"));
		emp.setComm(rs.getDouble("comm"));
		emp.setDeptno(rs.getInt("deptno"));
		
		return emp;
	}

}
