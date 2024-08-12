/*
Copyright Integro Technologies Pte Ltd
$Header: $
*/
package com.integrosys.cms.app.creditriskparam.trx.exemptedinst;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by maker to create Exempted Institution.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCreateExemptedInstOperation extends AbstractExemptedInstTrxOperation {

    /**
     * Default constructor.
     */
    public MakerCreateExemptedInstOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_INST;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Exempted Institution record
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
            IExemptedInstTrxValue trxValue =
                    (IExemptedInstTrxValue)(value);

            trxValue = createStagingExemptedInst(trxValue);

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
