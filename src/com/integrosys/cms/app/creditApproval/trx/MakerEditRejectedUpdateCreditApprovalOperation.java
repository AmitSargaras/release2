package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalReplicationUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;

/**
 * @author govind.sahu
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateCreditApprovalOperation extends AbstractCreditApprovalTrxOperation {
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateCreditApprovalOperation()
        {
            super();
        }

        /**
        * Get the operation name of the current operation
        *
        * @return String - the operation name of the current operation
        */
        public String getOperationName()
        {
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_CREDIT_APPROVAL;
        }

        /**
        * Process the transaction
        * 1.    Create Staging record
        * 2.    Update the transaction record
        * @param anITrxValue - ITrxValue
        * @return ITrxResult - the transaction result
        * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
        */
        public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
        {
        	ICreditApprovalTrxValue idxTrxValue = getCreditApprovalTrxValue(anITrxValue);
        	ICreditApproval stage = idxTrxValue.getStagingCreditApproval();
        	
        	
            
            idxTrxValue.setStagingCreditApproval(CreditApprovalReplicationUtils.replicateCreditApprovalForCreateStagingCopy(stage));

            ICreditApprovalTrxValue trxValue = createStagingCreditApproval(idxTrxValue);
            trxValue = updateCreditApprovalTransaction(trxValue);
            return super.prepareResult(trxValue);
        }

}
