/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging MF Template for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFTemplateTrxValue extends OBCMSTrxValue implements IMFTemplateTrxValue {
	private IMFTemplate actual;

	private IMFTemplate staging;

	/**
	 * Default constructor.
	 */
	public OBMFTemplateTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_MF_TEMPLATE);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IMFTemplateTrxValue
	 */
	public OBMFTemplateTrxValue(IMFTemplateTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBMFTemplateTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue#getMFTemplate
	 */
	public IMFTemplate getMFTemplate() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue#setMFTemplate
	 */
	public void setMFTemplate(IMFTemplate value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue#getStagingMFTemplate
	 */
	public IMFTemplate getStagingMFTemplate() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue#setStagingMFTemplate
	 */
	public void setStagingMFTemplate(IMFTemplate value) {
		this.staging = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
