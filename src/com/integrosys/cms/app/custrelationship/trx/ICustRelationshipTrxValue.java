/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.custrelationship.bus.ICustRelationship;

/**
 * Contains actual Customer Relationship and staging Customer Relationship for
 * transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface ICustRelationshipTrxValue extends ICMSTrxValue
{
	/**
	     * Gets the parent subprofile ID in this transaction.
	     *
	     * @return The parent subprofile ID
	     */
	public long getParentSubProfileID();
	
	 /**
	     * Sets the parent subprofile ID  for this transaction.
	     *
	     * @param value the parent subprofile ID
	     */
	public void setParentSubProfileID(long value);
	
    /**
	     * Gets the actual Customer Relationship objects in this transaction.
	     *
	     * @return The actual ICustRelationship
	     */
    public ICustRelationship[] getCustRelationship();

    /**
	     * Sets the actual Customer Relationship objects for this transaction.
	     *
	     * @param value the actual ICustRelationship
	     */
    public void setCustRelationship (ICustRelationship[] value);

    /**
	     * Gets the staging Customer Relationship objects in this transaction.
	     *
	     * @return the staging ICustRelationship
	     */
    public ICustRelationship[] getStagingCustRelationship();

    /**
	     * Sets the staging Customer Relationship objects for this transaction.
	     *
	     * @param value the staging ICustRelationship
	     */
	 public void setStagingCustRelationship (ICustRelationship[] value);

}
