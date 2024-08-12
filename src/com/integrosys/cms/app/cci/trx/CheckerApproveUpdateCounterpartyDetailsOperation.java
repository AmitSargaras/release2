package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * This operation class is invoked by a checker to approve Counterparty Details updated by a maker.
 *
 * @author $Author: Jitu<br>
 * @version $Revision: $
 * @since $Date: $
 *        Tag:      $Name: $
 */
public class CheckerApproveUpdateCounterpartyDetailsOperation extends AbstractCounterpartyDetailsTrxOperation {
    /**
     * Default constructor.
     */
    public CheckerApproveUpdateCounterpartyDetailsOperation() {
    }

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE;
    }

    /**
     * The following tasks are performed:
     * <p/>
     * 1. update actual Counterparty Details record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error updating the transaction
     */
    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        try {
            ICCICounterpartyDetailsTrxValue trxValue = super.getICCICounterpartyDetailsTrxValue(value);
            //TODO  commented the to create again staging records
            //trxValue = super.createStagingCounterpartyDetails(trxValue);
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation = ");
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getReferenceID() = " + trxValue.getReferenceID());
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getStagingReferenceID() = " + trxValue.getStagingReferenceID());




            trxValue = super.updateActualCounterpartyDetails(trxValue);
            //TODO  tEMP COMMENTED TO TESTING
//            System.out.println("After");
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getReferenceID() = " + trxValue.getReferenceID());
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getStagingReferenceID() = " + trxValue.getStagingReferenceID());


            trxValue = updateTransaction(trxValue);

//            System.out.println("After updateTransaction");
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getReferenceID() = " + trxValue.getReferenceID());
//            System.out.println("CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getStagingReferenceID() = " + trxValue.getStagingReferenceID());

            return super.prepareResult(trxValue);
//            OBCMSTrxResult result = new OBCMSTrxResult();
//            result.setTrxValue(trxValue);
//            return trxValue;
        }
        catch (TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
}
