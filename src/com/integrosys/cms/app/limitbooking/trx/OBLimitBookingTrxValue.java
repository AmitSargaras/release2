/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Contains actual and staging Limit Booking for transaction usage.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class OBLimitBookingTrxValue extends OBCMSTrxValue implements ILimitBookingTrxValue
{
    private ILimitBooking actual;
    private ILimitBooking staging;

    /**
     * Default constructor.
     */
    public OBLimitBookingTrxValue() {
        super();
        super.setTransactionType(ICMSConstant.INSTANCE_LIMIT_BOOKING);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ILimitBookingTrxValue
     */
    public OBLimitBookingTrxValue (ILimitBookingTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
     * Construct an object from its interface
     *
     * @param obj is of type ICMSTrxValue
     */
    public OBLimitBookingTrxValue (ICMSTrxValue obj) {
        this();
        AccessorUtil.copyValue(obj, this);
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue#getLimitBooking
    */
    public ILimitBooking getLimitBooking() {
        return this.actual;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue#setLimitBooking
    */
    public void setLimitBooking (ILimitBooking value) {
        this.actual = value;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue#getStagingLimitBooking
    */
    public ILimitBooking getStagingLimitBooking() {
        return this.staging;
    }

    /**
    * @see com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue#setStagingLimitBooking
    */
    public void setStagingLimitBooking (ILimitBooking value) {
        this.staging = value;
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
