/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingBusManagerFactory;

/**
 * Operation to read Limit Booking.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadLimitBookingOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadLimitBookingOperation() {
		super();
	}

    /**
	     * Get the operation name of the current operation.
	     *
	     * @return the operation name of the current operation
	     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_LIMIT_BOOKING;
    }
	
    /**
	     * This method is used to read a transaction object.
	     *
	     * @param val transaction value required for retrieving transaction record
	     * @return transaction value
	     * @throws TransactionException on errors retrieving the transaction value
	*/
    public ITrxValue getTransaction (ITrxValue val) throws TransactionException
    {
        try
        {
            ICMSTrxValue cmsTrxValue = super.getCMSTrxValue (val);

			ILimitBooking stageLimitBooking = null;
			ILimitBooking actualLimitBooking = null;

			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType (
                			cmsTrxValue.getReferenceID(), ICMSConstant.INSTANCE_LIMIT_BOOKING);

			ILimitBookingTrxValue trxVal = new OBLimitBookingTrxValue (cmsTrxValue);

			String stagingRef = trxVal.getStagingReferenceID();
			String actualRef = trxVal.getReferenceID();

			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if(stagingRef!=null)
			{
				// get staging Limit Booking
				SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getStagingLimitBookingBusManager();
				stageLimitBooking = mgr.getLimitBooking (Long.parseLong (stagingRef));
				trxVal.setStagingLimitBooking (stageLimitBooking);
			}
			if(actualRef!=null)
			{
				// get actual Limit Booking
				SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();
				actualLimitBooking = mgr.getLimitBooking ( Long.parseLong (actualRef) );
				trxVal.setLimitBooking (actualLimitBooking);
			}

            return trxVal;
        }
        catch (Exception e) {
            throw new TrxOperationException(e);
        }
    }
	
	

}
