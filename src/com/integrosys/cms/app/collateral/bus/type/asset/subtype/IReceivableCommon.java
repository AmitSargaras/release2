/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/IReceivableCommon.java,v 1.8 2005/08/12 04:39:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;

/**
 * This interface represents a common receivables for asset type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/12 04:39:17 $ Tag: $Name: $
 */
public interface IReceivableCommon extends IAssetBasedCollateral {
	/**
	 * Get invoice type.
	 * 
	 * @return String
	 */
	public String getInvoiceType();

	/**
	 * Set invoice type.
	 * 
	 * @param invoiceType is of type String
	 */
	public void setInvoiceType(String invoiceType);

	/**
	 * Get nominal Value.
	 * 
	 * @return Amount
	 */
	public Amount getNominalValue();

	/**
	 * Set nominal value.
	 * 
	 * @param nominalValue of type Amount
	 */
	public void setNominalValue(Amount nominalValue);
	
	/**
	 * Get description of the asset charge.
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Set description of the asset charge.
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks);

}
