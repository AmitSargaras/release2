/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IGeneralChargeSubType.java,v 1.4 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents subtypes of the Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public interface IGeneralChargeSubType extends IValuationDetails {

	/**
	 * Get ref ID for the subtype.
	 * 
	 * @return String
	 */
	public String getID();

	/**
	 * Get address.
	 * 
	 * @return String
	 */
	public String getAddress();

	/**
	 * Set address.
	 * 
	 * @param address is of type String
	 */
	public void setAddress(String address);

	/**
	 * Get Status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set Status.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);

	/**
	 * Get valuer name.
	 * 
	 * @return String
	 */
	public String getValuerName();

	/**
	 * Set valuer name.
	 * 
	 * @param valuerName is of type String
	 */
	public void setValuerName(String valuerName);

	public Amount getGrossValue();

	public void setGrossValue(Amount grossValue);

	public double getMargin();

	public void setMargin(double margin);

	public Amount getNetValue();

	public void setNetValue(Amount netValue);

	public String getValuationCurrency();

	public void setValuationCurrency(String valuationCurrency);
}
