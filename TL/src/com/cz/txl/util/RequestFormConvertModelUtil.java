package com.cz.txl.util;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

public class RequestFormConvertModelUtil {
	/**
	 * ��������ת���ɶ���
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T parseRequestToEntity(HttpServletRequest request, Class<T> clazz) throws Exception{
		T entity = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			String fieldType = field.getType().getName();
			String value = request.getParameter(fieldName);
			
			if(value == null || value.trim().length() == 0){
//				if(isFile(fieldName, request)){//���ϴ��ļ������Ҳ�Ϊ��
//					//������ϴ��ļ������沢���ؿ�ֱ�ӷ��ʵ�html·��
//					field.set(entity, saveFile(request, request.getPart(fieldName)));
//				}
				continue;
			}
			if(fieldType.equalsIgnoreCase("java.lang.String")){
				field.set(entity, value);
			}else if(fieldType.equalsIgnoreCase("byte")){
				field.setByte(entity, Byte.parseByte(value));
			}else if(fieldType.equalsIgnoreCase("short")){
				field.setShort(entity, Short.parseShort(value));
			}else if(fieldType.equalsIgnoreCase("int")){
				field.setInt(entity, Integer.parseInt(value));
			}else if(fieldType.equalsIgnoreCase("long")){
				field.setLong(entity, Long.parseLong(value));
			}else if(fieldType.equalsIgnoreCase("float")){
				field.setFloat(entity, Float.parseFloat(value));
			}else if(fieldType.equalsIgnoreCase("double")){
				field.setDouble(entity, Double.parseDouble(value));
			}else if(fieldType.equalsIgnoreCase("boolean")){
				field.setBoolean(entity, Boolean.parseBoolean(value));
			}else{
				field.set(entity, value);
			}
		}
		return entity;
	}
}
