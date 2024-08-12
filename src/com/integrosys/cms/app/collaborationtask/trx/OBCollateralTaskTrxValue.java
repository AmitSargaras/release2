/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/OBCollateralTaskTrxValue.java,v 1.1 2003/08/14 13:25:31 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class provides the implementation for ICollateralTaskTrxValue
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:25:31 $ Tag: $Name: $
 */
public class OBCollateralTaskTrxValue extends OBCMSTrxValue implements ICollateralTaskTrxValue {
	private ICollateralTask collateralTask = null;

	private ICollateralTask stagingCollateralTask = null;

	/**
	 * Default Constructor
	 */
	public OBCollateralTaskTrxValue() {
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSValue - ICMSTrxValue
	 */
	public OBCollateralTaskTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Get the collateral task business entity
	 * 
	 * @return ICollateralTask
	 */
	public ICollateralTask getCollateralTask() {
		return this.collateralTask;
	}

	/**
	 * Get the staging collateral task business entity
	 * 
	 * @return ICollateralTask
	 */
	public ICollateralTask getStagingCollateralTask() {
		return this.stagingCollateralTask;
	}

	/**
	 * Set the collateral task business entity
	 * 
	 * @param value is of type ICollateralTask
	 */
	public void setCollateralTask(ICollateralTask value) {
		this.collateralTask = value;
	}

	/**
	 * Set the staging Collateral task business entity
	 * 
	 * @param value is of type ICollateralTask
	 */
	public void setStagingCollateralTask(ICollateralTask value) {
		this.stagingCollateralTask = value;
	}
}