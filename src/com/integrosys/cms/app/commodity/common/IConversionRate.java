/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/IConversionRate.java,v 1.3 2004/08/19 05:08:54 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * This interface represents the conversion rate used to convert from one unit
 * to another unit.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 05:08:54 $ Tag: $Name: $
 */
public interface IConversionRate extends Serializable {

	/**
	 * Gets conversion key.
	 * 
	 * @return IConversionKey
	 */
	public IConversionKey getKey();

	/**
	 * Gets the conversion rate.
	 * 
	 * @return
	 */
	public BigDecimal getRate();

	/**
	 * Sets the conversion rate
	 * 
	 * @param rate - double
	 */
	public void setRate(BigDecimal rate);

	/**
	 * Converts obj using rate.
	 * 
	 * @param obj - Object to be converted
	 * @return Object after conversion
	 */
	public Object convert(Object obj) throws ConversionException;
}
