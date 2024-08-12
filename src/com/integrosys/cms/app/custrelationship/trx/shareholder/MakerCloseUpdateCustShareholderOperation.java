/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by maker to close Customer Shareholder rejected by a checker.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCloseUpdateCustShareholderOperation extends AbstractCustShareholderTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerCloseUpdateCustShareholderOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_SHAREHOLDER;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Customer Shareholder record
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

			trxValue.setStagingCustShareholder (trxValue.getCustShareholder());

			trxValue = super.createStagingCustShareholder (trxValue);

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
