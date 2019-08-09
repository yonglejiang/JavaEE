package com.cz.txl.type;

/*
 * 所谓枚举类,是这样一种特殊的类型:
 * 首先它里面的属性和get、set方法及构造方法的理解与普通类是一致的。
 * 但是,枚举类将该类可能生成的实例对象都在该类内部一一列举出来了
 * 也就是说枚举是可以查清的。该类可能产生的对象是实例是确定.
 * 而普通类理论上可以产生无数的实例对象
 */
public enum QueryConditionType {
	
	Equals("equals","="),Like("like","like"),Bigger("bigger",">"),Less("less","<");
	
	private String type;
	private String value;
	//枚举类的构造方法只能有private修饰.
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
