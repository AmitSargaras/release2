package com.integrosys.cms.businfra;

import java.io.Serializable;

public class LabelValue implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String label;
	private String value;
	private String description;
	
	public LabelValue() {
	}
	
	public LabelValue(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
