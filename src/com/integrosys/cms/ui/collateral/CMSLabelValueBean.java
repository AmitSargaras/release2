package com.integrosys.cms.ui.collateral;

import java.io.Serializable;

public class CMSLabelValueBean implements Serializable {
	private String label;

	private String value;

	public CMSLabelValueBean() {
	}

	public CMSLabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	public void setLable(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}