/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;

/**
 * This operation class is invoked by a maker to update a Limit Booking and system auto approve it.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerApproveUpdateLimitBookingOperation extends AbstractLimitBookingTrxOperation
{
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Default constructor.
     */
    public MakerApproveUpdateLimitBookingOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_APPROVE_LIMIT_BOOKING;
    }

    /**
     * The following tasks are performed:
     *
     * 1. update actual Limit Booking record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
	        ILimitBookingTrxValue trxValue = super.getLimitBookingTrxValue (value);

            ILimitBooking actual = trxValue.getLimitBooking();
			if( actual == null )
			{
	        	trxValue = super.createActualLimitBooking (trxValue);
				
	        }
	        else
	        {
	        	trxValue = super.updateActualLimitBooking (trxValue);
			}
			trxValue = super.createStagingLimitBooking (trxValue);
			
			if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
			    trxValue = super.createTransaction (trxValue);
		    else
				trxValue = super.updateTransaction(trxValue);
				
			
	        return super.prepareResult(trxValue);
	    }
	    catch (TrxOperationException e) {
	        throw e;
	    }
	    catch (Exception e) {
	        throw new TrxOperationException ("Exception caught!", e);
	    }
    }
}
