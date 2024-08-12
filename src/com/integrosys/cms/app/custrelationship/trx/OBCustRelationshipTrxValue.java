/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Contains actual and staging Customer Relationship for transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBCustRelationshipTrxValue extends OBCMSTrxValue implements ICustRelationshipTrxValue
{
	private long parentSubProfileID = ICMSConstant.LONG_INVALID_VALUE;
    private ICustRelationship[] actual;
    private ICustRelationship[] staging;

    /**
     * Default constructor.
     */
    public OBCustRelationshipTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_CUST_RELNSHIP);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICustRelationshipTrxValue
     */
    public OBCustRelationshipTrxValue (ICustRelationshipTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBCustRelationshipTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }
	
	/**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#getParentSubProfileID
    */
	public long getParentSubProfileID() {
		return this.parentSubProfileID;
	}
	
	/**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#setParentSubProfileID
    */
	public void setParentSubProfileID(long value) {
		this.parentSubProfileID = value;
	}	
	
    /**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#getCustRelationship
    */
    public ICustRelationship[] getCustRelationship() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#setCustRelationship
    */
    public void setCustRelationship (ICustRelationship[] value) {
        this.actual = value;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#getStagingCustRelationship
    */
    public ICustRelationship[] getStagingCustRelationship() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.trx.ICustRelationshipTrxValue#setStagingCustRelationship
    */
    public void setStagingCustRelationship (ICustRelationship[] value) {
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
