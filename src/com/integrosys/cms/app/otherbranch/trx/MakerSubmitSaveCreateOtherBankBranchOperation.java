package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbranch.trx.AbstractOtherBankBranchTrxOperation;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 *Maker Submit Save operation to  create record saved in Draft by maker
 */
public class MakerSubmitSaveCreateOtherBankBranchOperation extends AbstractOtherBankBranchTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerSubmitSaveCreateOtherBankBranchOperation()
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
            return ICMSConstant.ACTION_MAKER_SUBMIT_SAVE_CREATE_OTHER_BANK_BRANCH;
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
        	IOtherBankBranchTrxValue idxTrxValue = getOtherBankBranchTrxValue(anITrxValue);
            IOtherBranch stage = idxTrxValue.getStagingOtherBranch();
            idxTrxValue.setStagingOtherBranch(stage);

            IOtherBankBranchTrxValue trxValue = createStagingOtherBankBranch(idxTrxValue);
            trxValue = updateOtherBankBranchTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
