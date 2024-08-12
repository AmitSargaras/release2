/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/tag/TextTag.java,v 1.3 2004/11/20 04:47:58 lyng Exp $
 */

package com.integrosys.base.uiinfra.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.base.uiinfra.tag.TextAreaFormatter;

/**
 * TextAreaWrapperTag
 * @author $Author: lyng $
 * @version $
 * @since Aug 20, 2003 4:44:30 PM$
 */
public class TextTag extends TagSupport {
	protected String value;

	protected int lineLength = 60;

	public int getLineLength() {
		return lineLength;
	}

	public void setLineLength(int lineLength) {
		this.lineLength = lineLength;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int doStartTag() throws JspException {
		try {
			String retValue = "-";

			if (this.getValue() != null) {
				String ss = TextAreaFormatter.formatString(this.getValue(), getLineLength());
				retValue = convertIntoHtmlContent(ss);
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

	protected String convertIntoHtmlContent(String inStr) {
		String outStr = null;
		if (inStr != null) {
			StringBuffer buffer = new StringBuffer();
			char[] allChars = inStr.toCharArray();
			for (int x = 0; x < allChars.length; x++) {
				char aChar = allChars[x];
				if (aChar != '\r') {
					if (aChar == '\n') {
						buffer.append("<br>");
					}
					else if (aChar == '<') {
						buffer.append("&lt;");
					}
					else if (aChar == '>') {
						buffer.append("&gt;");
					}
					else if (aChar == ' ') {
						buffer.append("&nbsp;");
					}
					else {
						buffer.append(aChar);
					}
				}
			}
			outStr = buffer.toString();
		}
		return outStr;
	}
}