/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/NameValuePair.java,v 1.4 2004/07/10 02:39:16 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/10 02:39:16 $ Tag: $Name: $
 */
public abstract class NameValuePair implements Serializable {

	private String myName;

	private String myValue;

	public NameValuePair() {
	}

	public NameValuePair(String aName, String aValue) {
		myName = aName;
		myValue = aValue;
	}

	public String getName() {
		return myName;
	}

	public String getValue() {
		return myValue;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof NameValuePair)) {
			return false;
		}

		final NameValuePair nameValuePair = (NameValuePair) o;

		if (myName != null ? !myName.equals(nameValuePair.myName) : nameValuePair.myName != null) {
			return false;
		}
		if (myValue != null ? !myValue.equals(nameValuePair.myValue) : nameValuePair.myValue != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (myName != null ? myName.hashCode() : 0);
		result = 29 * result + (myValue != null ? myValue.hashCode() : 0);
		return result;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(myName).append(" - ").append(myValue);
		return buf.toString();
	}

}
