/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Customer Relationship.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBCustRelationship extends OBCommonCustRelationship implements ICustRelationship
{	
	private String relationshipValue;
	private String remarks;
    private String customerName="";
	   	
    /**
	     * Default Constructor.
	     */
    public OBCustRelationship() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type ICustRelationship
	     */
    public OBCustRelationship (ICustRelationship obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
    
    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustRelationship#getRelationshipValue
	    */
    public String getRelationshipValue() {
        return this.relationshipValue;
    }

    /**
    * @see com.integrosys.cms.app.custrelationship.bus.ICustRelationship#setRelationshipValue
    */
    public void setRelationshipValue(String relationshipValue) {
        this.relationshipValue = relationshipValue;
    }
   
    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustRelationship#getRemarks
	    */
	public String getRemarks() {
		return this.remarks;
	}

    /**
	    * @see com.integrosys.cms.app.custrelationship.bus.ICustRelationship#setRemarks
	    */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
	     * Return a String representation of this object.
	     *
	     * @return String
	     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

    /**
	     * Test for equality.
	     *
	     * @param obj is of type Object
	     * @return boolean
	     */
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBCustRelationship))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }

}