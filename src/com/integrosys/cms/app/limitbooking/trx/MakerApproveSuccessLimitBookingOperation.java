/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by a maker to update limit booking status to successful.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerApproveSuccessLimitBookingOperation extends AbstractLimitBookingTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerApproveSuccessLimitBookingOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SUCCESS_APPROVE_LIMIT_BOOKING;
    }

    /**
     * The following tasks are performed:
     *
     * 1. delete actual record
     * 2. update transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value)
    	throws TrxOperationException
    {
        try {

			ILimitBookingTrxValue trxValue = super.getLimitBookingTrxValue (value);
			trxValue = super.successActualLimitBooking (trxValue);
			trxValue = super.successStagingLimitBooking (trxValue);

			trxValue = super.updateTransaction (trxValue);

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
