/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/OBCollateralTrxValue.java,v 1.4 2003/07/11 10:10:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class defines transaction data for use with CMS.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/11 10:10:39 $ Tag: $Name: $
 */
public class OBCollateralTrxValue extends OBCMSTrxValue implements ICollateralTrxValue {
	private ICollateral collateral;

	private ICollateral stagingCollateral;

	/**
	 * Default Constructor
	 */
	public OBCollateralTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_COLLATERAL);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICollateralTrxValue
	 */
	public OBCollateralTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get actual collateral contained in this transaction.
	 * 
	 * @return object of collateral type
	 */
	public ICollateral getCollateral() {
		return collateral;
	}

	/**
	 * Set actual collateral to this transaction.
	 * 
	 * @param collateral of type ICollateral
	 */
	public void setCollateral(ICollateral collateral) {
		this.collateral = collateral;
	}

	/**
	 * Get staging collateral contained in this transaction.
	 * 
	 * @return staging collateral object
	 */
	public ICollateral getStagingCollateral() {
		return stagingCollateral;
	}

	/**
	 * Set staging collateral to this transaction.
	 * 
	 * @param stagingCollateral of type ICollateral
	 */
	public void setStagingCollateral(ICollateral stagingCollateral) {
		this.stagingCollateral = stagingCollateral;
	}
}