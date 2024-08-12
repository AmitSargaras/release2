package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.RegionReplicationUtils;

public class MakerCreateSavedRegionOperation extends AbstractRegionTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerCreateSavedRegionOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_REGION;
    }
    
    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
    	IRegionTrxValue idxTrxValue = getRegionTrxValue(anITrxValue);
    	IRegion stage = idxTrxValue.getStagingRegion();
    	IRegion replicatedRegion = RegionReplicationUtils.replicateRegionForCreateStagingCopy(stage);
        idxTrxValue.setStagingRegion(replicatedRegion);

        IRegionTrxValue trxValue = createStagingRegion(idxTrxValue);
        trxValue = updateRegionTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
