package com.integrosys.base.uiinfra.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Tan Kien Leong Date: May 3, 2007 Time:
 * 4:07:45 PM To change this template use File | Settings | File Templates.
 */
public class EmptyIfInvalidTag extends TagSupport {
	protected String value;

	protected String displayForInvalid;

	public String getDisplayForInvalid() {
		return displayForInvalid;
	}

	public void setDisplayForInvalid(String displayForInvalid) {
		this.displayForInvalid = displayForInvalid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(long value) {
		this.value = String.valueOf(value);
	}

	public void setValue(double value) {
		this.value = String.valueOf(value);
	}

	public void setValue(float value) {
		this.value = String.valueOf(value);
	}

	public void setValue(int value) {
		this.value = String.valueOf(value);
	}

	public int doStartTag() throws JspException {
		try {
			String retValue = "";

			if (!(this.getValue().equals(String.valueOf(ICMSConstant.LONG_INVALID_VALUE))
					|| this.getValue().equals(String.valueOf(ICMSConstant.DOUBLE_INVALID_VALUE))
					|| this.getValue().equals(String.valueOf(ICMSConstant.FLOAT_INVALID_VALUE)) || this.getValue()
					.equals(String.valueOf(ICMSConstant.INT_INVALID_VALUE)))) {
				retValue = this.getValue();
			}
			else {
				if (this.getDisplayForInvalid() != null) {
					retValue = this.getDisplayForInvalid();
				}
			}

			pageContext.getOut().print(retValue);

		}
		catch (IOException e) {
			e.printStackTrace();
			throw new JspException("IOException");
		}
		return SKIP_BODY;
	}
}
