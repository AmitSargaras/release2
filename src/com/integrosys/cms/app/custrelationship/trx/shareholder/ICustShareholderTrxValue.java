/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;

/**
 * Contains actual Customer Shareholder and staging Customer Shareholder for
 * transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface ICustShareholderTrxValue extends ICMSTrxValue
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
	     * Gets the actual Customer Shareholder objects in this transaction.
	     *
	     * @return The actual ICustShareholder
	     */
    public ICustShareholder[] getCustShareholder();

    /**
	     * Sets the actual Customer Shareholder objects for this transaction.
	     *
	     * @param value the actual ICustShareholder
	     */
    public void setCustShareholder (ICustShareholder[] value);

    /**
	     * Gets the staging Customer Shareholder objects in this transaction.
	     *
	     * @return the staging ICustShareholder
	     */
    public ICustShareholder[] getStagingCustShareholder();

    /**
	     * Sets the staging Customer Shareholder objects for this transaction.
	     *
	     * @param value the staging ICustShareholder
	     */
	 public void setStagingCustShareholder (ICustShareholder[] value);

}
