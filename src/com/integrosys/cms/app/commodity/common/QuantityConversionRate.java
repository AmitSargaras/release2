/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/QuantityConversionRate.java,v 1.3 2004/08/19 04:46:08 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.math.BigDecimal;

/**
 * The class represents the quantity conversion rate used to convert from one
 * unit of measure to another unit of measure.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 04:46:08 $ Tag: $Name: $
 */
public class QuantityConversionRate extends ConversionRate {

	/**
	 * Construct the object with the specified parameters.
	 * 
	 * @param aKey - IConversionKey which indicates the conversion is to be done
	 *        from one unit to another unit.
	 * @param aRate - BigDecimal
	 */
	public QuantityConversionRate(IConversionKey aKey, BigDecimal aRate) {
		super(aKey, aRate);
	}

	/**
	 * Construct the object with the specified parameters.
	 * 
	 * @param aKey - IConversionKey which indicates the conversion is to be done
	 *        from one unit to another unit.
	 * @param aRate - BigDecimal
	 */
	public QuantityConversionRate(IConversionKey aKey, double aRate) {
		super(aKey, aRate);
	}

	/**
	 * Converts obj using rate.
	 * 
	 * @param qty - Object to be converted
	 * @return Object after conversion
	 */
	public Object convert(Object qty) throws ConversionException {
		if (!(qty instanceof Quantity)) {
			throw new ConversionException("Quantity to be converted is not an instance of Quantity. qty : "
					+ qty.getClass().getName());
		}
		Quantity theQty = (Quantity) qty;
		BigDecimal rate = getRate();
		return (theQty == null) ? null : new Quantity(theQty.getQuantity().multiply(rate), UOMWrapperFactory
				.getInstance().valueOf(getKey().getToUnit()));
	}
}
