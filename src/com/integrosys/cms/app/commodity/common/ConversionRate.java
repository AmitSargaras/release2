/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/ConversionRate.java,v 1.5 2004/08/19 04:46:08 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.math.BigDecimal;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the conversion rate used to convert from one unit to
 * another unit.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/19 04:46:08 $ Tag: $Name: $
 */
public abstract class ConversionRate implements IConversionRate {

	private IConversionKey myKey;

	private BigDecimal myRate;

	/**
	 * Constructor.
	 * 
	 * @param aKey - IConversionKey
	 * @param aRate - BigDecimal
	 */
	public ConversionRate(IConversionKey aKey, BigDecimal aRate) {
		this.myKey = aKey;
		this.myRate = aRate;
	}

	/**
	 * Constructor.
	 * 
	 * @param aKey - IConversionKey
	 * @param aRate - double
	 */
	public ConversionRate(IConversionKey aKey, double aRate) {
		this.myKey = aKey;
		this.myRate = new BigDecimal(aRate);
	}

	/**
	 * Gets the key for the conversion rate.
	 * 
	 * @return IConversionKey
	 */
	public IConversionKey getKey() {
		return myKey;
	}

	/**
	 * Gets the conversion rate.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getRate() {
		return myRate;
	}

	/**
	 * Sets the conversion rate.
	 * 
	 * @param aRate
	 */
	public void setRate(BigDecimal aRate) {
		this.myRate = aRate;
	}

	/**
	 * Test equality for object.
	 * 
	 * @param o is of type Object
	 * @return boolean
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ConversionRate)) {
			return false;
		}

		final ConversionRate conversionRate = (ConversionRate) o;

		if (myKey != null ? !myKey.equals(conversionRate.myKey) : conversionRate.myKey != null) {
			return false;
		}
		if (myRate != null ? !myRate.equals(conversionRate.myRate) : conversionRate.myRate != null) {
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
		result = (myKey != null ? myKey.hashCode() : 0);
		result = 29 * result + (myRate != null ? myRate.hashCode() : 0);
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
