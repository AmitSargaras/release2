package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author abhijit.rudrakshawar
 * Maker Close operation to remove  update rejected by checker
 */

public class MakerCloseDraftFCCBranchOperation extends AbstractFCCBranchTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_DRAFT_FCCBRANCH;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseDraftFCCBranchOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        IFCCBranchTrxValue trxValue = super.getFCCBranchTrxValue (anITrxValue);
        trxValue = updateFCCBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
