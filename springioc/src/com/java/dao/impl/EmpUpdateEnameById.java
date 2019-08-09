package com.java.dao.impl;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;

public class EmpUpdateEnameById extends SqlUpdate{

	public EmpUpdateEnameById(DataSource dataSource){
		this.setDataSource(dataSource);
		String sql = "update emp set ename = ? where empno =?"; // insert , delete
		this.setSql(sql);
		this.declareParameter(new SqlParameter(Types.VARCHAR));
		this.declareParameter(new SqlParameter(Types.INTEGER));
		this.compile();
	}
	
}
