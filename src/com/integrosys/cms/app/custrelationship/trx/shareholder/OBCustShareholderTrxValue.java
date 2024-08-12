/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Contains actual and staging Customer Shareholder for transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBCustShareholderTrxValue extends OBCMSTrxValue implements ICustShareholderTrxValue
{
	private long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private ICustShareholder[] actual;
    private ICustShareholder[] staging;

    /**
     * Default constructor.
     */
    public OBCustShareholderTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_SHAREHOLDER );
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICustShareholderTrxValue
     */
    public OBCustShareholderTrxValue (ICustShareholderTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBCustShareholderTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }
	
	/**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#getParentSubProfileID
    */
	public long getParentSubProfileID() {
		return this.parentSubProfileID;
	}
	
	/**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#setParentSubProfileID
    */
	public void setParentSubProfileID(long value) {
		this.parentSubProfileID = value;
	}	
	
    /**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#getCustShareholder
    */
    public ICustShareholder[] getCustShareholder() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#setCustShareholder
    */
    public void setCustShareholder (ICustShareholder[] value) {
        this.actual = value;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#getStagingCustShareholder
    */
    public ICustShareholder[] getStagingCustShareholder() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.shareholder.ICustShareholderTrxValue#setStagingCustShareholder
    */
    public void setStagingCustShareholder (ICustShareholder[] value) {
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
