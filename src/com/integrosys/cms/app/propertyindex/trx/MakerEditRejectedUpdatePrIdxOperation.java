package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.bus.gold.GoldReplicationUtils;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxReplicationUtils;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerEditRejectedUpdatePrIdxOperation extends AbstractPropertyIdxTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdatePrIdxOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_PRIDX;
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
            IPropertyIdxTrxValue idxTrxValue = getPropertyIdxTrxValue(anITrxValue);
            IPropertyIdx stage = idxTrxValue.getStagingPrIdx();
            IPropertyIdx replicatedPrIdx = PropertyIdxReplicationUtils.replicatePropertyIdxForCreateStagingCopy(stage);
            idxTrxValue.setStagingPrIdx(replicatedPrIdx);

            IPropertyIdxTrxValue trxValue = createStagingPropertyIdx(idxTrxValue);
            trxValue = updatePropertyIdxTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
