/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.entitylimit;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Contains actual and staging entityLimits for transaction usage.
 *
 * @author   $Author: skchai $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBEntityLimitTrxValue extends OBCMSTrxValue implements IEntityLimitTrxValue
{

	private static final long serialVersionUID = 1L;
	private IEntityLimit[] actual;
    private IEntityLimit[] staging;
    private IEntityLimit[] temp;
    private String editedRemarks;

    /**
     * Get Edited Remarks
     * @return
     */
    public String getEditedRemarks() {
		return editedRemarks;
	}

    /**
     * Set Edited Remarks
     * @param editedRemarks
     */
	public void setEditedRemarks(String editedRemarks) {
		this.editedRemarks = editedRemarks;
	}

	/**
     * Default constructor.
     */
    public OBEntityLimitTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_ENTITY_LIMIT);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type IEntityLimitTrxValue
     */
    public OBEntityLimitTrxValue (IEntityLimitTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBEntityLimitTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#getEntityLimit
    */
    public IEntityLimit[] getEntityLimit() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#setEntityLimit
    */
    public void setEntityLimit (IEntityLimit[] entityLimits) {
        this.actual = entityLimits;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#getStagingEntityLimit
    */
    public IEntityLimit[] getStagingEntityLimit() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#setStagingEntityLimit
    */
    public void setStagingEntityLimit (IEntityLimit[] entityLimits) {
        this.staging = entityLimits;
    }

    
    /**
     * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#getTempEntityLimit
     */
    public IEntityLimit[] getTempEntityLimit() {
		return this.temp;
	}

    /**
     * @see com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue#setTempEntityLimit
     */
	public void setTempEntityLimit(IEntityLimit[] entityLimits) {
		this.temp = entityLimits;
		
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
