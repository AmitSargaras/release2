/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;

import java.util.Date;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Interface of data model holds common value for Customer Relationship and Shareholder.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface ICommonCustRelationship extends java.io.Serializable
{
	
    /**
	     * Get custRelatnshipID
	     *
	     * @return long
	     */
    public long getCustRelationshipID();

    /**
	     * Set the custRelatnshipID.
	     *
	     * @param custRelatnshipID is of type long
	     */
	public void setCustRelationshipID(long custRelatnshipID);

    /**
	     * Get parentSubProfileID
	     *
	     * @return long
	     */
    public long getParentSubProfileID();

    /**
	     * Set the parentSubProfileID.
	     *
	     * @param parentSubProfileID is of type long
	     */
    public void setParentSubProfileID(long parentSubProfileID);
	
	/**
	     * Get subProfileID
	     *
	     * @return long
	     */
	public long getSubProfileID();

    /**
	     * Set the subProfileID.
	     *
	     * @param subProfileID is of type long
	     */
    public void setSubProfileID(long subProfileID);
    
	/**
	     * Get customerDetails
	     *
	     * @return ICMSCustomer
	     */
    public ICMSCustomer getCustomerDetails();

    /**
	     * Set the customerDetails.
	     *
	     * @param customerDetails is of type ICMSCustomer
	     */
    public void setCustomerDetails(ICMSCustomer customerDetails);

	/**
	     * Get lastUpdateDate
	     *
	     * @return Date
	     */
    public Date getLastUpdateDate();

   /**
	     * Set the lastUpdateDate.
	     *
	     * @param lastUpdateDate is of type Date
	     */
    public void setLastUpdateDate(Date lastUpdateDate);

   /**
	     * Get lastUpdateUser
	     *
	     * @return String
	     */
    public String getLastUpdateUser();

    /**
	     * Set the lastUpdateUser.
	     *
	     * @param lastUpdateUser is of type String
	     */
    public void setLastUpdateUser(String lastUpdateUser);

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
	     * Get refID
	     *
	     * @return long
	     */
    public long getRefID();

     /**
	     * Set the refID.
	     *
	     * @param refID is of type long
	     */
    public void setRefID(long refID);

	/**
	     * Get status
	     *
	     * @return String
	     */
    public String getStatus();

    /**
	     * Set status
	     *
	     * @param status of type String
	     */
    public void setStatus (String status);

    /**
	     * Get the version time.
	     *
	     * @return long
	     */
    public long getVersionTime();

    /**
	     * Set the version time.
	     *
	     * @param versionTime is of type long
	     */
    public void setVersionTime(long versionTime);
  

}