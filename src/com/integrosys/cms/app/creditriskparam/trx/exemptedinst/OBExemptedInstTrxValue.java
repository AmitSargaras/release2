/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.creditriskparam.bus.exemptedinst.IExemptedInst;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

import java.util.List;

/**
 * Contains actual and staging exemptInsts for transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBExemptedInstTrxValue extends OBCMSTrxValue implements IExemptedInstTrxValue
{
    private IExemptedInst[] actual;
    private IExemptedInst[] staging;

    /**
     * Default constructor.
     */
    public OBExemptedInstTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_EXEMPT_INST);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type IExemptedInstTrxValue
     */
    public OBExemptedInstTrxValue (IExemptedInstTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBExemptedInstTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue#getExemptedInst
    */
    public IExemptedInst[] getExemptedInst() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue#setExemptedInst
    */
    public void setExemptedInst (IExemptedInst[] exemptInsts) {
        this.actual = exemptInsts;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue#getStagingExemptedInst
    */
    public IExemptedInst[] getStagingExemptedInst() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.exemptedinst.IExemptedInstTrxValue#setStagingExemptedInst
    */
    public void setStagingExemptedInst (IExemptedInst[] exemptInsts) {
        this.staging = exemptInsts;
    }

    /**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }
}
