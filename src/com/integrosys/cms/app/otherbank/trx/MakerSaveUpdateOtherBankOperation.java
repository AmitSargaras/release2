package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 * Maker save Update operation to  save record in draft 
 */
public class MakerSaveUpdateOtherBankOperation extends AbstractOtherBankTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerSaveUpdateOtherBankOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
     public String getOperationName()
     {
         return ICMSConstant.ACTION_MAKER_UPDATE_SAVE_OTHER_BANK;
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
         IOtherBankTrxValue idxTrxValue = getOtherBankTrxValue(anITrxValue);
         IOtherBank stage = idxTrxValue.getStagingOtherBank();
         idxTrxValue.setStagingOtherBank(stage);

         IOtherBankTrxValue trxValue = createStagingOtherBank(idxTrxValue);
         trxValue = updateOtherBankTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
