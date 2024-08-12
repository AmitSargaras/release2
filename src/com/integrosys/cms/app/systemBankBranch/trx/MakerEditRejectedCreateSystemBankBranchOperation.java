package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxReplicationUtils;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchReplicationUtils;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateSystemBankBranchOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_SYSTEM_BANK_BRANCH;
    }

    /**
    * Process the transaction
    * 1. Create Staging record
    * 2. Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        ISystemBankBranchTrxValue idxTrxValue = getSystemBankBranchTrxValue(anITrxValue);
        ISystemBankBranch stage = idxTrxValue.getStagingSystemBankBranch();
        ISystemBankBranch replicatedSystemBankBranch = SystemBankBranchReplicationUtils.replicateSystemBankBranchForCreateStagingCopy(stage);
        idxTrxValue.setStagingSystemBankBranch(replicatedSystemBankBranch);

        ISystemBankBranchTrxValue trxValue = createStagingSystemBankBranch(idxTrxValue);
        trxValue = updateSystemBankBranchTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
