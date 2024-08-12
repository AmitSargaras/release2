/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBLimitSysXRef.java,v 1.4 2004/03/15 02:01:31 hltan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;

/**
 * This class represents a Limit System X-Ref record
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/03/15 02:01:31 $ Tag: $Name: $
 */
public class OBLimitSysXRef implements ILimitSysXRef {
	private long xRefID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long sID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ICustomerSysXRef customerSysXRef = null;

	private String status = null;

	private String xRefRef = null;

	private String xRefLegalRef = null;

	private String xRefCustomerRef = null;

	private String xRefBCARef = null;

	private String xRefLimitRef = null;

	private String xRefCustomerXRef = null;

	private long limitFk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBLimitSysXRef() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimitSysXRef
	 */
	public OBLimitSysXRef(ILimitSysXRef value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters
	/**
	 * Get the LimitSystem XRef ID
	 * 
	 * @return long
	 */
	public long getXRefID() {
		return xRefID;
	}

	/**
	 * Get the SID
	 * 
	 * @return long
	 */
	public long getSID() {
		return sID;
	}

	/**
	 * Get the Customer System XRef
	 * 
	 * @return ICustomerSysXRef
	 */
	public ICustomerSysXRef getCustomerSysXRef() {
		return customerSysXRef;
	}

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	public String getXRefRef() {
		return xRefRef;
	}

	public String getXRefLegalRef() {
		return xRefLegalRef;
	}

	public String getXRefCustomerRef() {
		return xRefCustomerRef;
	}

	public String getXRefBCARef() {
		return xRefBCARef;
	}

	public String getXRefLimitRef() {
		return xRefLimitRef;
	}

	public String getXRefCustomerXRef() {
		return xRefCustomerXRef;
	}

	// Setters
	/**
	 * Set the LimitSystem XRef ID
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value) {
		xRefID = value;
	}

	/**
	 * Set the SID
	 * 
	 * @param value is of type long
	 */
	public void setSID(long value) {
		sID = value;
	}

	/**
	 * Set the Customer System XRef
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public void setCustomerSysXRef(ICustomerSysXRef value) {
		customerSysXRef = value;
	}

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value) {
		status = value;
	}

	public void setXRefRef(String value) {
		xRefRef = value;
	}

	public void setXRefLegalRef(String value) {
		xRefLegalRef = value;
	}

	public void setXRefCustomerRef(String value) {
		xRefCustomerRef = value;
	}

	public void setXRefBCARef(String value) {
		xRefBCARef = value;
	}

	public void setXRefLimitRef(String value) {
		xRefLimitRef = value;
	}

	public void setXRefCustomerXRef(String value) {
		xRefCustomerXRef = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.limit.bus.ILimitSysXRef#setLimitFk(long)
	 */
	public void setLimitFk(long limitId) {
		limitFk = limitId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.integrosys.cms.app.limit.bus.ILimitSysXRef#getLimitFk()
	 */
	public long getLimitFk() {
		return limitFk;
	}
}