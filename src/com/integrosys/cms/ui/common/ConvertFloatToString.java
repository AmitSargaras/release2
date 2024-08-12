/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/ConvertFloatToString.java,v 1.2 2003/09/02 07:28:18 pooja Exp $
 */
package com.integrosys.cms.ui.common;

/**
 * This interface defines the constant specific to the collateral task table and
 * the methods required by the document
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/02 07:28:18 $ Tag: $Name: $
 */

public class ConvertFloatToString {
	public ConvertFloatToString() {
	}

	private static ConvertFloatToString thisInstance;

	public synchronized static ConvertFloatToString getInstance() {
		if (thisInstance == null) {
			thisInstance = new ConvertFloatToString();
		}
		return thisInstance;
	}

	/**
	 * To convert the amount to the currency code specified based on the forex
	 * rates
	 * 
	 */
	public String convertFloat(float f) throws Exception {
		String value = Float.toString(f);
		int index = value.indexOf(".");
		value = value.substring(0, index);
		return (value);
	}

	public String convertDouble(double d) throws Exception {
		String value = Double.toString(d);
		int index = value.indexOf(".");
		value = value.substring(0, index);
		return (value);
	}
}