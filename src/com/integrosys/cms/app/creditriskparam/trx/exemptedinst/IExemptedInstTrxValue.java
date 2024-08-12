/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;

import java.util.List;

/**
 * Contains actual and staging Exempted Institution for
 * transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface IExemptedInstTrxValue extends ICMSTrxValue
{
    /**
     * Gets the actual exemptInsts objects in this transaction.
     *
     * @return The actual exemptInsts objects
     */
    public IExemptedInst[] getExemptedInst();

    /**
     * Sets the actual exemptInsts objects for this transaction.
     *
     * @param exemptInsts the actual exemptInsts objects
     */
    public void setExemptedInst(IExemptedInst[] exemptInsts);

    /**
     * Gets the staging exemptInsts objects in this transaction.
     *
     * @return the staging exemptInsts objects
     */
    public IExemptedInst[] getStagingExemptedInst();

    /**
     * Sets the staging exemptInsts objects for this transaction.
     *
     * @param exemptInsts the staging exemptInsts objects
     */
    public void setStagingExemptedInst(IExemptedInst[] exemptInsts);
	

}
