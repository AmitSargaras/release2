package com.integrosys.cms.app.discrepency.trx;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.DiscrepencyReplicationUtils;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class MakerDeleteDiscrepencyOperation  extends AbstractDiscrepencyTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerDeleteDiscrepencyOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_DISCREPENCY;
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
        IDiscrepencyTrxValue trxValue = getDiscrepencyTrxValue(anITrxValue);
        IDiscrepency staging = trxValue.getStagingDiscrepency();
        try {
            if (staging != null) {
                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_DISCREPENCY);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                
            }else{
            	throw new TrxOperationException("Staging Value is null");
            }
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
        IDiscrepencyTrxValue idxTrxValue = getDiscrepencyTrxValue(anITrxValue);
        IDiscrepency stage = idxTrxValue.getStagingDiscrepency();
        IDiscrepency replicatedDiscrepency = DiscrepencyReplicationUtils.replicateDiscrepencyForCreateStagingCopy(stage);
   
        idxTrxValue.setStagingDiscrepency(replicatedDiscrepency);

        IDiscrepencyTrxValue trxValue = createStagingDiscrepency(idxTrxValue);
        trxValue = updateDiscrepencyTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
