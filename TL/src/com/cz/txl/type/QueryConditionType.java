package com.cz.txl.type;

/*
 * ��νö����,������һ�����������:
 * ��������������Ժ�get��set���������췽�����������ͨ����һ�µġ�
 * ����,ö���ཫ����������ɵ�ʵ�������ڸ����ڲ�һһ�оٳ�����
 * Ҳ����˵ö���ǿ��Բ���ġ�������ܲ����Ķ�����ʵ����ȷ��.
 * ����ͨ�������Ͽ��Բ���������ʵ������
 */
public enum QueryConditionType {
	
	Equals("equals","="),Like("like","like"),Bigger("bigger",">"),Less("less","<");
	
	private String type;
	private String value;
	//ö����Ĺ��췽��ֻ����private����.
	private QueryConditionType(String type, String value) {
		this.type = type;
		this.value = value;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
