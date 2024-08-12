/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import java.io.Serializable;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface of data model holds Exempted Institution for GP5.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface IExemptedInst extends Serializable, IValueObject {

	/**
	 * Get the exempted instituition ID
	 * @return
	 */
	public long getExemptedInstID();
	
	/**
	 * Setting the exempted instituition id
	 * @param id
	 */
	public void setExemptedInstID(long id);
	
	/**
	 * Get Customer ID
	 * @return
	 */
	public long getCustomerID();
	
	/**
	 * Set Customer ID
	 */
	public void setCustomerID(long customerID);
	
	/**
	 * Get customer name
	 * @return
	 */
	public String getCustomerName();
	
	/**
	 * Set customer name
	 * @param customer
	 */
	public void setCustomerName(String customerName);
	
    /**
	     * Get the Legal Entity Reference.
	     * This is the legal identify as obtained from host.
	     *
	     * @return String
	     */
    public String getLEReference();
    
    /**
	     * Set the Legal Entity Reference.
	     * This is the legal identify as obtained from host.
	     *
	     * @param value is of type String
	     */
     public void setLEReference(String value);
	
	/**
	 * Get customer ID source ID
	 * @return
	 */
	 public String getCustIDSource();
	 
	 /**
	 * Set customer ID source ID
	 * @param customer
	 */
	 public void setCustIDSource(String custIDSource);
	
	 /**
	     * Get groupID
	     *
	     * @return long
	     */
	 public long getGroupID();

	 /**
	     * Set the groupID.
	     *
	     * @param groupID is of type long
	     */
	 public void setGroupID(long groupID);
	
    /**
	     * Get cms common reference id across actual and staging tables.
	     *
	     * @return long
	     */
    public long getCommonRef();

    /**
	     * Set cms common reference id across actual and staging tables.
	     *
	     * @param commonRef of type long
	     */
    public void setCommonRef(long commonRef);
    
    /**
	     * Get the record status
	     * @return
	     */
	public String getStatus();

	/**
	 * Set the record status
	 * @param status
	 */
	public void setStatus(String status);
}
