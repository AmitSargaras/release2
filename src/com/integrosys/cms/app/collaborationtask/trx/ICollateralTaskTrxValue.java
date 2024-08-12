/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/ICollateralTaskTrxValue.java,v 1.1 2003/08/14 13:25:31 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a collateral task trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:25:31 $ Tag: $Name: $
 */
public interface ICollateralTaskTrxValue extends ICMSTrxValue {
	/**
	 * Get the collateral task business entity
	 * 
	 * @return ICollateralTask
	 */
	public ICollateralTask getCollateralTask();

	/**
	 * Get the staging collateral task business entity
	 * 
	 * @return ICollateralTask
	 */
	public ICollateralTask getStagingCollateralTask();

	/**
	 * Set the collateral task business entity
	 * 
	 * @param value is of type ICollateralTask
	 */
	public void setCollateralTask(ICollateralTask value);

	/**
	 * Set the staging Collateral task business entity
	 * 
	 * @param value is of type ICollateralTask
	 */
	public void setStagingCollateralTask(ICollateralTask value);
}