package com.integrosys.cms.app.cci.trx;


import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public class MakerUpdateOperation extends AbstractCounterpartyDetailsTrxOperation {

    /**
     * Defaulc Constructor
     */
    public MakerUpdateOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY;
    }


    /**
     * The following tasks are performed:
     * <p/>
     * 1. create staging Counter party Details record
     * 2. create staging transaction record if the status is ND, otherwise
     * update transaction record.
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws TrxOperationException on error updating the transaction
     */

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        try {
            ICCICounterpartyDetailsTrxValue trxValue =      (ICCICounterpartyDetailsTrxValue)(value);
            trxValue = createStagingCounterpartyDetails(trxValue);
            trxValue = super.updateTransaction(trxValue);
         return prepareResult(trxValue);
        } catch (TrxOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }


    }


}