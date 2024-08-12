/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;

/**
 * Contains actual and staging Entity Limit for
 * transaction usage.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface IEntityLimitTrxValue extends ICMSTrxValue
{
    /**
     * Gets the actual entityLimits objects in this transaction.
     *
     * @return The actual entityLimits objects
     */
    public IEntityLimit[] getEntityLimit();

    /**
     * Sets the actual entityLimits objects for this transaction.
     *
     * @param entityLimits the actual entityLimits objects
     */
    public void setEntityLimit(IEntityLimit[] entityLimits);

    /**
     * Gets the staging entityLimits objects in this transaction.
     *
     * @return the staging entityLimits objects
     */
    public IEntityLimit[] getStagingEntityLimit();

    /**
     * Sets the staging entityLimits objects for this transaction.
     *
     * @param entityLimits the staging entityLimits objects
     */
    public void setStagingEntityLimit(IEntityLimit[] entityLimits);
	
    /**
     * Get the temp entityLimits objects in this transaction, used in edit page
     * 
     * @return the temp entityLimits object
     */
    public IEntityLimit[] getTempEntityLimit();

    /**
     * Sets the temp entityLimits objects for this transaction, used in edit page
     *
     * @param entityLimits the staging entityLimits objects
     */
    public void setTempEntityLimit(IEntityLimit[] entityLimits);
    
    /**
     * Get Edited Remarks
     * @return
     */
    public String getEditedRemarks();

    /**
     * Set Edited Remarks
     * @param editedRemarks
     */
	public void setEditedRemarks(String editedRemarks);

}
