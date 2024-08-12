/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ICollateralSubTypeTrxValue.java,v 1.5 2003/08/14 07:48:41 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual collateral subtypes and staging collateral subtypes for
 * transaction usage.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/14 07:48:41 $ Tag: $Name: $
 */
public interface ICollateralSubTypeTrxValue extends ICMSTrxValue {
	/**
	 * Gets the actual collateral subtype objects in this transaction.
	 * 
	 * @return The actual collateral subtype objects
	 */
	public ICollateralSubType[] getCollateralSubTypes();

	/**
	 * Sets the actual collateral subtype objects for this transaction.
	 * 
	 * @param subTypes the actual collateral subtype objects
	 */
	public void setCollateralSubTypes(ICollateralSubType[] subTypes);

	/**
	 * Gets the staging collateral subtype objects in this transaction.
	 * 
	 * @return the staging collateral subtype objects
	 */
	public ICollateralSubType[] getStagingCollateralSubTypes();

	/**
	 * Sets the staging collateral subtype objects for this transaction.
	 * 
	 * @param subTypes the staging collateral subtype objects
	 */
	public void setStagingCollateralSubTypes(ICollateralSubType[] subTypes);

	/**
	 * Get collateral type code.
	 * 
	 * @return String
	 */
	public String getCollateralTypeCode();

	/**
	 * Set collateral type code.
	 * 
	 * @param collateralTypeCode of type String
	 */
	public void setCollateralTypeCode(String collateralTypeCode);
}
