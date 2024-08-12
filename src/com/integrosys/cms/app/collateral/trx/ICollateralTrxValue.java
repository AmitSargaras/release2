/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ICollateralTrxValue.java,v 1.4 2003/06/20 02:10:54 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface defines transaction data for use with CMS. It also contains
 * collateral entity.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/06/20 02:10:54 $ Tag: $Name: $
 */
public interface ICollateralTrxValue extends ICMSTrxValue {
	/**
	 * Get actual collateral contained in this transaction.
	 * 
	 * @return object of collateral type
	 */
	public ICollateral getCollateral();

	/**
	 * Set actual collateral to this transaction.
	 * 
	 * @param collateral of type ICollateral
	 */
	public void setCollateral(ICollateral collateral);

	/**
	 * Get staging collateral contained in this transaction.
	 * 
	 * @return staging collateral object
	 */
	public ICollateral getStagingCollateral();

	/**
	 * Set staging collateral to this transaction.
	 * 
	 * @param stagingCollateral of type ICollateral
	 */
	public void setStagingCollateral(ICollateral stagingCollateral);
}
