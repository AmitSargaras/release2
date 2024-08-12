package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryReplicationUtils;

/**
 * @author Govind.Sahu
 * Maker Edit Rejected Create RbiCategory Operation
 */
public class MakerEditRejectedCreateRbiCategoryOperation extends AbstractRbiCategoryTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateRbiCategoryOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_RBI_CATEGORY;
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
        IRbiCategoryTrxValue idxTrxValue = getRbiCategoryTrxValue(anITrxValue);
        IRbiCategory stage = idxTrxValue.getStagingRbiCategory();
        IRbiCategory replicatedRbiCategory = RbiCategoryReplicationUtils.replicateRbiCategoryForCreateStagingCopy(stage);
        idxTrxValue.setStagingRbiCategory(replicatedRbiCategory);

        IRbiCategoryTrxValue trxValue = createStagingRbiCategory(idxTrxValue);
        trxValue = updateRbiCategoryTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
