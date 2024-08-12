/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by maker to update a Exempted Institution.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerUpdateExemptedInstOperation extends AbstractExemptedInstTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerUpdateExemptedInstOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_INST;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Exempted Institution record
     * 2. update transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            IExemptedInstTrxValue trxValue = super.getExemptedInstTrxValue (value);

            trxValue = super.createStagingExemptedInst(trxValue);

            trxValue = super.updateTransaction(trxValue);

            return super.prepareResult(trxValue);
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch(Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
	

}
