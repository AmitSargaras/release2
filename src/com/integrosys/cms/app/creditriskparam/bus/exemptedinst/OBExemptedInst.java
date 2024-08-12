/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Data model holds Exempted Institution for GP5.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBExemptedInst implements IExemptedInst {

	private static final long serialVersionUID = 1L;
	
	private String customerName;
	private String lEReference;
	private String custIDSource;
	private long exemptedInstID = ICMSConstant.LONG_INVALID_VALUE;
	private long versionTime;
	private long groupID;
	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;
	private long customerID = ICMSConstant.LONG_INVALID_VALUE;
	private String status;
	
	 /**
	     * Default Constructor.
	     */
    public OBExemptedInst() {
        super();
    }

    /**
	     * Construct the object from its interface.
	     *
	     * @param obj is of type IExemptedInst
	     */
    public OBExemptedInst (IExemptedInst obj) {       
		this();		
        AccessorUtil.copyValue (obj, this);
    }
	
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getLEReference() {
		return this.lEReference;
	}
	
	public void setLEReference(String lEReference) {
		this.lEReference = lEReference;
	}
	
	public String getCustIDSource() {
		return this.custIDSource;
	}
	
	public void setCustIDSource(String custIDSource) {
		this.custIDSource = custIDSource;
	}
	
	public long getCustomerID() {
		return this.customerID;
	}
	
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public long getExemptedInstID() {
		return this.exemptedInstID;
	}
	
	public void setExemptedInstID(long exemptedInstID) {
		this.exemptedInstID = exemptedInstID;
		
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;		
	}
	
	public long getGroupID() {
		return this.groupID;
	}
	
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}	
  	
	public long getCommonRef() {
		return this.commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}
	
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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
        else if (!(obj instanceof OBExemptedInst))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
