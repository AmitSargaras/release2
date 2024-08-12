/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.trx;

import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * ICreditRiskParamGroupTrxValue Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface ICreditRiskParamGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the ICreditRiskParamGroupTrxValue busines entity
	 * 
	 * @return ICreditRiskParamGroupTrxValue
	 */
	public ICreditRiskParamGroup getCreditRiskParamGroup();

	/**
	 * Get the staging ICreditRiskParamGroup business entity
	 * 
	 * @return ICreditRiskParamGroup
	 */
	public ICreditRiskParamGroup getStagingCreditRiskParamGroup();

	/**
	 * Set the ICreditRiskParamGroupTrxValue busines entity
	 * 
	 * @param value is of type ICreditRiskParamGroupTrxValue
	 */
	public void setCreditRiskParamGroup(ICreditRiskParamGroup value);

	/**
	 * Set the staging ICreditRiskParamGroupTrxValue business entity
	 * 
	 * @param value is of type ICreditRiskParamGroupTrxValue
	 */
	public void setStagingCreditRiskParamGroup(ICreditRiskParamGroup value);
}