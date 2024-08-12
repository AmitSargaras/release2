/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/tag/TextTag.java,v 1.3 2004/11/20 04:47:58 lyng Exp $
 */

package com.integrosys.base.uiinfra.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * YesNoTag
 * @author $Author: lyng $
 * @version $
 * @since Aug 20, 2003 4:44:30 PM$
 */
public class YesNoTag extends TagSupport {
	protected String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValue(char value) {
		this.value = String.valueOf(value);
	}

	public int doStartTag() throws JspException {
		try {
			String retValue = "-";

			if (this.getValue() != null) {
				if (this.getValue().equals(ICMSConstant.TRUE_VALUE)) {
					retValue = "Yes";
				}
				else if (this.getValue().equals(ICMSConstant.FALSE_VALUE)) {
					retValue = "No";
				}
			}
			else {
				retValue = "-";
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