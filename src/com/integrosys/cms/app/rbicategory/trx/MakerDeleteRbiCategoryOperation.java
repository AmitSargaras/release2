package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryReplicationUtils;

/**
 * @author Govind.Sahu
 * Maker Update operation to  update system 
 */
public class MakerDeleteRbiCategoryOperation extends AbstractRbiCategoryTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerDeleteRbiCategoryOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_RBI_CATEGORY;
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
        IRbiCategoryTrxValue trxValue = getRbiCategoryTrxValue(anITrxValue);
        IRbiCategory staging = trxValue.getStagingRbiCategory();
        try {
//            if (staging != null) {
//                
//
//                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
//                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_RBI_CATEGORY);
//                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
//                }
//                
//            }else{
//            	throw new TrxOperationException("Staging Value is null");
//            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
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
        IRbiCategoryTrxValue idxTrxValue = getRbiCategoryTrxValue(anITrxValue);
        IRbiCategory stage = idxTrxValue.getStagingRbiCategory();
        IRbiCategory replicatedRbiCateggory = RbiCategoryReplicationUtils.replicateRbiCategoryForCreateStagingCopy(stage);
        idxTrxValue.setStagingRbiCategory(replicatedRbiCateggory);

        IRbiCategoryTrxValue trxValue = createStagingRbiCategory(idxTrxValue);
        trxValue = updateRbiCategoryTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
