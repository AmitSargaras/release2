/*
Copyright Integro Technologies Pte Ltd
$Header: $
*/
package com.integrosys.cms.app.custrelationship.trx.shareholder;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;


/**
 * This operation class is invoked by maker to create Customer Shareholder.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCreateCustShareholderOperation extends AbstractCustShareholderTrxOperation {

    /**
     * Default constructor.
     */
    public MakerCreateCustShareholderOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_SHAREHOLDER ;
    }


    /**
     * The following tasks are performed:
     *
     * 1. create staging Customer Shareholder record
     * 2. create staging transaction record if the status is ND, otherwise
     *    update transaction record.
     *
     * @param value is of type ITrxValue
     * @return ITrxResult
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException on error
     */
    public ITrxResult performProcess(ITrxValue value)
            throws TrxOperationException {
        try {
            ICustShareholderTrxValue trxValue =
                    (ICustShareholderTrxValue)(value);

            trxValue = createStagingCustShareholder(trxValue);

			if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
			    trxValue = super.createTransaction (trxValue);
		    else
				trxValue = super.updateTransaction(trxValue);

            return prepareResult(trxValue);

        } catch (Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
}
