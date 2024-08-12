/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/ConversionKey.java,v 1.5 2004/08/19 04:46:08 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the conversion key used to represent the from and to
 * unit to be used in conversion.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/19 04:46:08 $ Tag: $Name: $
 */
public class ConversionKey implements IConversionKey {

	public String fromUnit;

	public String toUnit;

	/**
	 * Construct the object with the specified parameters.
	 * 
	 * @param fromUnit - String
	 * @param toUnit - String
	 */
	public ConversionKey(String fromUnit, String toUnit) {
		if ((fromUnit != null) && (toUnit != null)) {
			this.fromUnit = fromUnit;
			this.toUnit = toUnit;
		}
	}

	/**
	 * Get unit to convert from.
	 * 
	 * @return String - from unit.
	 */
	public String getFromUnit() {
		return fromUnit;
	}

	/**
	 * Get unit to convert to.
	 * 
	 * @return String - to unit.
	 */
	public String getToUnit() {
		return toUnit;
	}

	/**
	 * Test for equality.
	 * 
	 * @param o is of type Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ConversionKey)) {
			return false;
		}

		final ConversionKey conversionKey = (ConversionKey) o;

		if (!fromUnit.equals(conversionKey.fromUnit)) {
			return false;
		}
		if (!toUnit.equals(conversionKey.toUnit)) {
			return false;
		}

		return true;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		int result;
		result = fromUnit.hashCode();
		result = 29 * result + toUnit.hashCode();
		return result;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
