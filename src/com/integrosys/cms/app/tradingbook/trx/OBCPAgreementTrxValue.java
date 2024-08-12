/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.tradingbook.bus.ICPAgreementDetail;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains counter party and agreement details for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCPAgreementTrxValue extends OBCMSTrxValue implements ICPAgreementTrxValue {
	private long limitProfileID;

	private long agreementID;

	private ICPAgreementDetail cpAgreementDetail;

	/**
	 * Default constructor.
	 */
	public OBCPAgreementTrxValue() {
		super();
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICPAgreementTrxValue
	 */
	public OBCPAgreementTrxValue(ICPAgreementTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct an object from its interface
	 * 
	 * @param obj is of type ICMSTrxValue
	 */
	public OBCPAgreementTrxValue(ICMSTrxValue obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#getLimitProfileID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#setLimitProfileID
	 */
	public void setLimitProfileID(long value) {
		this.limitProfileID = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#getAgreementID
	 */
	public long getAgreementID() {
		return this.agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#setAgreementID
	 */
	public void setAgreementID(long value) {
		this.agreementID = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#getCPAgreementDetail
	 */
	public ICPAgreementDetail getCPAgreementDetail() {
		return this.cpAgreementDetail;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.trx.ICPAgreementTrxValue#setCPAgreementDetail
	 */
	public void setCPAgreementDetail(ICPAgreementDetail value) {
		this.cpAgreementDetail = value;
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
