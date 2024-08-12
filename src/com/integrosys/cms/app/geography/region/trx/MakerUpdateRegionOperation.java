package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.RegionReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerUpdateRegionOperation extends AbstractRegionTrxOperation{
	
	/**
     * Defaulc Constructor
     */
    public MakerUpdateRegionOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_REGION;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        IRegionTrxValue trxValue = getRegionTrxValue((anITrxValue));
        IRegion staging = trxValue.getStagingRegion();
        try {
            if (staging != null) {
                if (staging.getIdRegion() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getIdRegion()), ICMSConstant.INSTANCE_REGION);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }                
            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.toString());
        }
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
