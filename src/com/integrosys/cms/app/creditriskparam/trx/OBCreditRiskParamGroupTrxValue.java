/**
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */

package com.integrosys.cms.app.creditriskparam.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * OBCreditRiskParamGroupTrxValue Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class OBCreditRiskParamGroupTrxValue extends OBCMSTrxValue implements ICreditRiskParamGroupTrxValue {

	/**
	 * Get the ICreditRiskParamGroupTrxValue busines entity
	 * 
	 * @return ICreditRiskParamGroupTrxValue
	 */
	public ICreditRiskParamGroup getCreditRiskParamGroup() {
		return actual;
	}

	/**
	 * Construct the object based on its parent info
	 * @param anICMSTrxValue - ICMSTrxValue
	 */
	public OBCreditRiskParamGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	public OBCreditRiskParamGroupTrxValue(ITrxValue anICMSTrxValue) {
		AccessorUtil.copyValue(anICMSTrxValue, this);
	}

	/**
	 * Default constructor.
	 */
	public OBCreditRiskParamGroupTrxValue() {
		// Follow "limit".
		//super.setTransactionType(ICMSConstant.INSTANCE_UNIT_TRUST_FEED_GROUP);
	}

	/**
	 * Get the staging ICreditRiskParamGroupTrxValue business entity
	 * 
	 * @return ICreditRiskParamGroupTrxValue
	 */
	public ICreditRiskParamGroup getStagingCreditRiskParamGroup() {
		return staging;
	}

	/**
	 * Set the ICreditRiskParamGroupTrxValue busines entity
	 * 
	 * @param value is of type ICreditRiskParamGroupTrxValue
	 */
	public void setCreditRiskParamGroup(ICreditRiskParamGroup value) {
		actual = value;
	}

	/**
	 * Set the staging ICreditRiskParamGroupTrxValue business entity
	 * 
	 * @param value is of type ICreditRiskParamGroupTrxValue
	 */
	public void setStagingCreditRiskParamGroup(ICreditRiskParamGroup value) {
		staging = value;
	}

	private ICreditRiskParamGroup actual;

	private ICreditRiskParamGroup staging;
}
