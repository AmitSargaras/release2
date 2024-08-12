/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.countrylimit;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.ICountryLimitParam;
import com.integrosys.cms.app.creditriskparam.bus.countrylimit.OBCountryLimitParam;

/**
 * This operation class is invoked by maker to close Country Limit rejected by a checker.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCloseUpdateCountryLimitOperation extends AbstractCountryLimitTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerCloseUpdateCountryLimitOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_COUNTRY_LIMIT;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Country Limit record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            ICountryLimitTrxValue trxValue = super.getCountryLimitTrxValue (value);
			
			ICountryLimitParam newValue = new OBCountryLimitParam( trxValue.getCountryLimitParam() );
			
			trxValue.setStagingCountryLimitParam ( newValue );

			trxValue = super.createStagingCountryLimit (trxValue);

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
