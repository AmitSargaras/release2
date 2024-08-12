/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.SBLimitBookingBusManager;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingBusManagerFactory;


/**
 * The operation is to read Limit Booking by transaction ID.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class ReadLimitBookingByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation
{
	/**
  	 * Default Constructor
 	 */
	public ReadLimitBookingByTrxIDOperation() {
		super();
	}

	/**
 	 * Get the operation name of the current operation.
     *
 	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_LIMIT_BOOKING_BY_TRXID;
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
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

			OBLimitBookingTrxValue trxVal = new OBLimitBookingTrxValue (cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			
			DefaultLogger.debug (this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
                SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getStagingLimitBookingBusManager();
                ILimitBooking limitBooking = mgr.getLimitBooking (Long.parseLong (stagingRef));
                trxVal.setStagingLimitBooking (limitBooking);
              
            }

			if (actualRef != null) {
                SBLimitBookingBusManager mgr = LimitBookingBusManagerFactory.getActualLimitBookingBusManager();
                ILimitBooking limitBooking = mgr.getLimitBooking (Long.parseLong (actualRef));
                trxVal.setLimitBooking (limitBooking);
               
            }
           
            return trxVal;

        }
        catch (Exception e) {
            throw new TrxOperationException (e);
        }
    }
	
	

}