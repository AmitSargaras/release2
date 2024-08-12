/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/IInsuranceCollateral.java,v 1.6 2005/09/29 09:38:39 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Insurance
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/09/29 09:38:39 $ Tag: $Name: $
 */
public interface IInsuranceCollateral extends ICollateral {
	/**
	 * Return a array of CDS Items
	 * 
	 * @return ICDSItem[]
	 */
	public ICDSItem[] getCdsItems();

	/**
	 * Set CDS Items
	 * 
	 * @parameter cdsItems of type ICDSItem[]
	 */
	public void setCdsItems(ICDSItem[] cdsItems);
	
	
	public Amount getInsurancePremium();

	public void setInsurancePremium(Amount insurancePremium);
}