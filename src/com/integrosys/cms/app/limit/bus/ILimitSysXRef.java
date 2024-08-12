/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ILimitSysXRef.java,v 1.3 2004/03/15 02:01:30 hltan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;

/**
 * This interface represents a Limit System X-Ref record
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/03/15 02:01:30 $ Tag: $Name: $
 */
public interface ILimitSysXRef extends java.io.Serializable {
	// Getters
	/**
	 * Get the LimitSystem XRef ID
	 * 
	 * @return long
	 */
	public long getXRefID();

	/**
	 * Get the SID
	 * 
	 * @return long
	 */
	public long getSID();

	/**
	 * Get the Customer System XRef
	 * 
	 * @return ICustomerSysXRef
	 */
	public ICustomerSysXRef getCustomerSysXRef();

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	public String getXRefRef();

	public String getXRefLegalRef();

	public String getXRefCustomerRef();

	public String getXRefBCARef();

	public String getXRefLimitRef();

	public String getXRefCustomerXRef();

	// Setters
	/**
	 * Set the LimitSystem XRef ID
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value);

	/**
	 * Set the SID
	 * 
	 * @param value is of type long
	 */
	public void setSID(long value);

	/**
	 * Set the Customer System XRef
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public void setCustomerSysXRef(ICustomerSysXRef value);

	/**
	 * Set status
	 * 
	 * @param value is of type String
	 */
	public void setStatus(String value);

	public void setXRefRef(String value);

	public void setXRefLegalRef(String value);

	public void setXRefCustomerRef(String value);

	public void setXRefBCARef(String value);

	public void setXRefLimitRef(String value);

	public void setXRefCustomerXRef(String value);

	public void setLimitFk(long limitId);

	public long getLimitFk();
}