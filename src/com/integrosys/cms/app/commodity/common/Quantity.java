/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/Quantity.java,v 1.8 2004/07/22 17:21:24 lyng Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;
import java.math.BigDecimal;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author $Author: lyng $
 * @version $Revision: 1.8 $
 * @since $Date: 2004/07/22 17:21:24 $ Tag: $Name: $
 */
public class Quantity implements Serializable {
	private BigDecimal myQty;

	private UOMWrapper myUOM;

	public Quantity() {
	}

	public Quantity(BigDecimal qty, UOMWrapper uom) {
		myQty = qty;
		myUOM = uom;
	}

	public BigDecimal getQuantity() {
		return myQty;
	}

	public UOMWrapper getUnitofMeasure() {
		return myUOM;
	}

	/**
	 * Converts this quantity using the conversion rate.
	 * 
	 * @param conversionRate IConversionRate
	 * @return Quantity
	 * @throws ConversionException
	 */
	public Quantity convert(IConversionRate conversionRate) throws ConversionException {
		return (Quantity) conversionRate.convert(this);
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Quantity)) {
			return false;
		}

		final Quantity quantity = (Quantity) o;

		if (myQty != quantity.myQty) {
			return false;
		}
		if (myUOM != null ? !myUOM.equals(quantity.myUOM) : quantity.myUOM != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		long temp;
		temp = myQty.doubleValue() != +0.0d ? Double.doubleToLongBits(myQty.doubleValue()) : 0l;
		result = (int) (temp ^ (temp >>> 32));
		result = 29 * result + (myUOM != null ? myUOM.hashCode() : 0);
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
