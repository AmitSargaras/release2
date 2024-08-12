package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * @author dattatray.thorat
 * Maker delete operation to  delete Insurance Coverage Details 
 */
public class MakerDeleteInsuranceCoverageDtlsOperation extends AbstractInsuranceCoverageDtlsTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerDeleteInsuranceCoverageDtlsOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_INSURANCE_COVERAGE_DTLS;
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
        IInsuranceCoverageDtlsTrxValue trxValue = getInsuranceCoverageDtlsTrxValue(anITrxValue);
        IInsuranceCoverageDtls staging = trxValue.getStagingInsuranceCoverageDtls();
        try {
            if (staging != null) {
                

                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getId()), ICMSConstant.INSTANCE_INSURANCE_COVERAGE_DTLS);
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
        IInsuranceCoverageDtlsTrxValue idxTrxValue = getInsuranceCoverageDtlsTrxValue(anITrxValue);
        IInsuranceCoverageDtls stage = idxTrxValue.getStagingInsuranceCoverageDtls();
        IInsuranceCoverageDtls replicatedInsuranceCoverage = InsuranceCoverageDtlsReplicationUtils.replicateInsuranceCoverageDtlsForCreateStagingCopy(stage);
   
        idxTrxValue.setStagingInsuranceCoverageDtls(replicatedInsuranceCoverage);

        IInsuranceCoverageDtlsTrxValue trxValue = createStagingInsuranceCoverageDtls(idxTrxValue);
        trxValue = updateInsuranceCoverageDtlsTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
