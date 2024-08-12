package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagReplicationUtils;
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
public class MakerEditRejectedCreateImageTagOperation extends AbstractImageTagTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateImageTagOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_IMAGE_TAG;
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
        IImageTagTrxValue idxTrxValue = getImageTagTrxValue(anITrxValue);
        IImageTagDetails stage = idxTrxValue.getStagingImageTagDetails();
        IImageTagDetails replicateIImageTagDetails = ImageTagReplicationUtils.replicateImageTagForCreateStagingCopy(stage);
        idxTrxValue.setStagingImageTagDetails(replicateIImageTagDetails);

        IImageTagTrxValue trxValue = createStagingImageTagDetails(idxTrxValue);
        trxValue = updateImageTagTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
