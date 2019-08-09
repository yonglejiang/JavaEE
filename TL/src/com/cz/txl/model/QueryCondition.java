package com.cz.txl.model;

import com.cz.txl.type.QueryConditionType;
//��ѯ������װ��
public class QueryCondition {
	
	private String fieldName;//����
	private Object fieldValue;//������ֵ
	private QueryConditionType conditionType;//�����ıȽ�����
	
	//�������������췽����Ĭ������������Ƚ�����Ϊ���
	public QueryCondition(String fieldName, Object fieldValue) {
		this(fieldName, fieldValue, QueryConditionType.Equals);
	}
	public QueryCondition(String fieldName, Object fieldValue, QueryConditionType conditionType) {
		super();
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
		this.conditionType = conditionType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public Object getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public QueryConditionType getConditionType() {
		return conditionType;
	}
	public void setConditionType(QueryConditionType conditionType) {
		this.conditionType = conditionType;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conditionType == null) ? 0 : conditionType.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((fieldValue == null) ? 0 : fieldValue.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryCondition other = (QueryCondition) obj;
		if (conditionType != other.conditionType)
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (fieldValue == null) {
			if (other.fieldValue != null)
				return false;
		} else if (!fieldValue.equals(other.fieldValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QueryCondition [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", conditionType="
				+ conditionType + "]";
	}
	
}

