/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/systemparameters/trx/OBSystemParametersTrxValue.java,v 1.1 2003/08/13 14:21:54 dayanand Exp $
 */
package com.integrosys.cms.app.systemparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.component.commondata.app.bus.IBusinessParameterGroup;
import com.integrosys.component.commondata.app.trx.IBusinessParameterGroupTrxValue;

/**
 * This class represents the transaction object for CMS.
 * 
 * @author Dayanand
 */
public class OBSystemParametersTrxValue extends OBCMSTrxValue implements IBusinessParameterGroupTrxValue {
	private IBusinessParameterGroup _businessParameterGroup = null;

	private IBusinessParameterGroup _stageBusinessParameterGroup = null;

	/**
	 * Default Constructor
	 */
	public OBSystemParametersTrxValue() {
		super();
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ICMSTrxValue object
	 */
	public OBSystemParametersTrxValue(ICMSTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the IBusinessParameterGroupTrxValue object
	 */
	public OBSystemParametersTrxValue(IBusinessParameterGroupTrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Constructr the OB from its interface
	 * 
	 * @param in is the ITrxValue object
	 */
	public OBSystemParametersTrxValue(ITrxValue in) {
		this();
		AccessorUtil.copyValue(in, this);
	}

	/**
	 * Get User
	 * 
	 * @return IBusinessParameterGroup
	 */
	public IBusinessParameterGroup getBusinessParameterGroup() {
		return _businessParameterGroup;
	}

	/**
	 * Get Staging User
	 * 
	 * @return IBusinessParameterGroup
	 */
	public IBusinessParameterGroup getStagingBusinessParameterGroup() {
		return _stageBusinessParameterGroup;
	}

	/**
	 * Set User
	 * 
	 * @param value is of type IBusinessParameterGroup
	 */
	public void setBusinessParameterGroup(IBusinessParameterGroup value) {
		_businessParameterGroup = value;
	}

	/**
	 * Set Staging User
	 * 
	 * @param value is of type IBusinessParameterGroup
	 */
	public void setStagingBusinessParameterGroup(IBusinessParameterGroup value) {
		_stageBusinessParameterGroup = value;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
