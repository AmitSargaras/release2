/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.custrelationship.bus.ICustShareholder;

/**
 * This operation class is invoked by a checker to approve Customer Shareholder updated by a maker.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class CheckerApproveUpdateCustShareholderOperation extends AbstractCustShareholderTrxOperation
{
    /**
     * Default constructor.
     */
    public CheckerApproveUpdateCustShareholderOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_SHAREHOLDER;
    }

    /**
     * The following tasks are performed:
     *
     * 1. update actual Customer Shareholder record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
	        ICustShareholderTrxValue trxValue = super.getCustShareholderTrxValue (value);

            ICustShareholder[] actual = trxValue.getCustShareholder();
			if( actual == null || actual.length == 0)
			{
	        	trxValue = super.createActualCustShareholder (trxValue);
	        }
	        else
	        {
	        	trxValue = super.updateActualCustShareholder (trxValue);
			}
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
