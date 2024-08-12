package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerDeleteSegmentWiseEmailOperation extends AbstractSegmentWiseEmailTrxOperation {

	/**
     * Defaulc Constructor
     */
	public MakerDeleteSegmentWiseEmailOperation() {
		super();
	}
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_SEGMENT_WISE_EMAIL;
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
        ISegmentWiseEmailTrxValue trxValue = getSegmentWiseEmailTrxValue(anITrxValue);
        ISegmentWiseEmail staging = trxValue.getStagingSegmentWiseEmail();
        try {
            if (staging != null) {
                

                if (staging.getID() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getID()), ICMSConstant.INSTANCE_SEGMENT_WISE_EMAIL);
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
        ISegmentWiseEmailTrxValue idxTrxValue = getSegmentWiseEmailTrxValue(anITrxValue);
        ISegmentWiseEmail stage = idxTrxValue.getStagingSegmentWiseEmail();
        ISegmentWiseEmail replicatedSegmentWiseEmail = SegmentWiseEmailReplicationUtils.replicateSegmentWiseEmailForCreateStagingCopy(stage);
     //   replicatedFacilityNewMaster.getSystemBankCode().setId(stage.getSystemBankCode().getId());
        idxTrxValue.setStagingSegmentWiseEmail(replicatedSegmentWiseEmail);

        ISegmentWiseEmailTrxValue trxValue = createStagingSegmentWiseEmail(idxTrxValue);
        trxValue = updateSegmentWiseEmailTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
