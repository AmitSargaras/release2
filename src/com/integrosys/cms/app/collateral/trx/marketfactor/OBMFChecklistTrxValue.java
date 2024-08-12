/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging MF Checklist for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBMFChecklistTrxValue extends OBCMSTrxValue implements IMFChecklistTrxValue {
	private IMFChecklist actual;

	private IMFChecklist staging;

	/**
	 * Default constructor.
	 */
	public OBMFChecklistTrxValue() {
		super();
		super.setTransactionType(ICMSConstant.INSTANCE_MF_CHECKLIST);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type IMFChecklistTrxValue
	 */
	public OBMFChecklistTrxValue(IMFChecklistTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBMFChecklistTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue#getMFChecklist
	 */
	public IMFChecklist getMFChecklist() {
		return this.actual;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue#setMFChecklist
	 */
	public void setMFChecklist(IMFChecklist value) {
		this.actual = value;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue#getStagingMFChecklist
	 */
	public IMFChecklist getStagingMFChecklist() {
		return this.staging;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue#setStagingMFChecklist
	 */
	public void setStagingMFChecklist(IMFChecklist value) {
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
