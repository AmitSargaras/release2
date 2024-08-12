package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationReplicationUtils;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedCreateCaseCreationOperation extends AbstractCaseCreationTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateCaseCreationOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_CASECREATION;
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
        ICaseCreationTrxValue idxTrxValue = getCaseCreationTrxValue(anITrxValue);
     /*   ICaseCreation stage = idxTrxValue.getStagingCaseCreation();
        ICaseCreation replicatedCaseCreation = CaseCreationReplicationUtils.replicateCaseCreationForCreateStagingCopy(stage);
        idxTrxValue.setStagingCaseCreation(replicatedCaseCreation);*/

        ICaseCreationTrxValue trxValue = createStagingCaseCreation(idxTrxValue);
        trxValue = updateCaseCreationTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
