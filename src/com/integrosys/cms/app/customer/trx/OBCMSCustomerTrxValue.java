/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/trx/OBCMSCustomerTrxValue.java,v 1.2 2003/07/04 07:55:04 kllee Exp $
 */
package com.integrosys.cms.app.customer.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * This class represents a CMS Customer trx value.
 * 
 * @author kllee
 * @author Chong Jun Yong
 * @since 2003/07/04
 */
public class OBCMSCustomerTrxValue extends OBCMSTrxValue implements ICMSCustomerTrxValue {

	private static final long serialVersionUID = -8925595229657400462L;

	private ICMSCustomer actualCustomer = null;

	private ICMSCustomer stagingCustomer = null;

	/**
	 * Default Constructor
	 */
	public OBCMSCustomerTrxValue() {
		super.setTransactionType(ICMSConstant.INSTANCE_CUSTOMER);
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITrxValue
	 */
	public OBCMSCustomerTrxValue(ITrxValue value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	/**
	 * Get the customer busines entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getCustomer() {
		return actualCustomer;
	}

	/**
	 * Get the staging customer business entity
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getStagingCustomer() {
		return stagingCustomer;
	}

	/**
	 * Set the customer busines entity
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setCustomer(ICMSCustomer value) {
		actualCustomer = value;
	}

	/**
	 * Set the staging customer business entity
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setStagingCustomer(ICMSCustomer value) {
		stagingCustomer = value;
	}

}