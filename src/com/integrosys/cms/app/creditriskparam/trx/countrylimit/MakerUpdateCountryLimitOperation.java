/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by maker to update a Country Limit.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerUpdateCountryLimitOperation extends AbstractCountryLimitTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerUpdateCountryLimitOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_COUNTRY_LIMIT;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Country Limit record
     * 2. update transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            ICountryLimitTrxValue trxValue = super.getCountryLimitTrxValue (value);

            trxValue = super.createStagingCountryLimit(trxValue);

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
