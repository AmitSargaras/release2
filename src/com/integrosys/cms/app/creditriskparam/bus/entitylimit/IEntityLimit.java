/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.entitylimit;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface of data model holds Entity Limit
 *
 * @author  $Author: skchai $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface IEntityLimit extends Serializable, IValueObject {

	/**
	 * Get the entity limit ID
	 * @return
	 */
	public long getEntityLimitID();
	
	/**
	 * Setting the entity limit id
	 * @param id
	 */
	public void setEntityLimitID(long id);
	
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
	  * Set entity limit amount 
	  * @param limit
	  */
	 public void setLimitAmount(Amount limitAmount);
	 
	 /**
	  * Get entity limit amount
	  * @return double
	  */
	 public Amount getLimitAmount();
	 
	 /**
	  * Get entity limit last review date
	  * @param limitLastReviewedDate
	  * @return
	  */
	 public Date getLimitLastReviewDate();
	 
	 /**
	  * Set Entity Limit last review date
	  * @param limitLastReviewDate
	  */
	 public void setLimitLastReviewDate(Date limitLastReviewDate);
	 
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

    public String getLimitCurrency();

    public void setLimitCurrency(String limitCurrency);
}
