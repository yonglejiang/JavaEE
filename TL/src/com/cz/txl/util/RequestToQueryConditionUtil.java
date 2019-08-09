package com.cz.txl.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cz.txl.model.QueryCondition;
import com.cz.txl.type.QueryConditionType;

public class RequestToQueryConditionUtil {
	/**
	 * 根据请求表单动态构造查询条件列表
	 * @param <T>
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> List<QueryCondition> parseRequestToQueryCondition(HttpServletRequest request, Class<T> clazz) throws Exception{
		List<QueryCondition> qcList = new ArrayList<QueryCondition>();
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			String fieldType = field.getType().getName();
			String value = request.getParameter(fieldName);
			
			if(value == null || value.trim().length() == 0){
				continue;
			}
			QueryCondition qc = new QueryCondition(fieldName, value);
			
			if(fieldType.equalsIgnoreCase("java.lang.String")){
				qc.setConditionType(QueryConditionType.Like);
			}else if(fieldType.equalsIgnoreCase("int")){
				qc.setConditionType(QueryConditionType.Equals);
			}
			qcList.add(qc);
		}
		return qcList;
	}
}
