package com.cz.txl.model;

//���������������json����
public class ComboBoxData {
	private int id;//�������optionѡ���value
	private String desc;//
	private String text;//������optionѡ�����ʾ�ı�
	private boolean selected;//������option�Ƿ�ѡ��
	
	public ComboBoxData() {
		super();
	}
	public ComboBoxData(int id, String desc, String text, boolean selected) {
		super();
		this.id = id;
		this.desc = desc;
		this.text = text;
		this.selected = selected;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((desc == null) ? 0 : desc.hashCode());
		result = prime * result + id;
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		ComboBoxData other = (ComboBoxData) obj;
		if (desc == null) {
			if (other.desc != null)
				return false;
		} else if (!desc.equals(other.desc))
			return false;
		if (id != other.id)
			return false;
		if (selected != other.selected)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ComboBoxData [id=" + id + ", desc=" + desc + ", text=" + text + ", selected=" + selected + "]";
	}
	
	
}
