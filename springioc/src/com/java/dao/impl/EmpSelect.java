package com.java.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlQuery;

public class EmpSelect extends SqlQuery {

	public EmpSelect(DataSource dataSource){
		this.setDataSource(dataSource);
		String sql = "select * from emp where deptno =?";
		this.setSql(sql);
		this.declareParameter(new SqlParameter(Types.INTEGER));
		this.compile();
	}
	
	protected RowMapper newRowMapper(Object[] objs, Map map) {

		RowMapper mapper = new RowMapper(){
			public Map mapRow(ResultSet rs, int index) throws SQLException {
				//�Զ��� ���ص�List�д�����ݣ�����ʹ���󣬿���ʹ���飬Ҳ����ʹMap,....
				System.out.println(index);
				Map map = new HashMap();
				map.put("EMPNO", rs.getInt("empno"));
				map.put("ENAME", rs.getString("ename"));
				//...
				return map;
			}
		};
		return mapper;
	}

}
