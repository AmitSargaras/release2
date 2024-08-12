package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

import java.rmi.RemoteException;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 06, 2010
 */
public class MakerUpdateSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerUpdateSecEnvelopeOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_SECENV;
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
        ISecEnvelopeTrxValue trxValue = getSecEnvelopeTrxValue(anITrxValue);
        ISecEnvelope staging = trxValue.getStagingSecEnvelope();
        staging.setSecEnvelopeId(trxValue.getSecEnvelope().getSecEnvelopeId());
        try {
            if (staging != null) {
                ICMSTrxValue parentTrx = null;

                if (staging.getSecEnvelopeId() != ICMSConstant.LONG_INVALID_VALUE) {
                    parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getSecEnvelopeId()), ICMSConstant.INSTANCE_SECURITY_ENVELOPE);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                return trxValue;
            }
            return trxValue;
        }
        catch (TransactionException ex) {
            throw new TrxOperationException(ex);
        }
        catch (RemoteException ex) {
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
        ISecEnvelopeTrxValue idxTrxValue = getSecEnvelopeTrxValue(anITrxValue);
        ISecEnvelope stage = idxTrxValue.getStagingSecEnvelope();
        ISecEnvelope replicatedSecEnvelope = SecEnvelopeReplicationUtils.replicateSecEnvelopeForCreateStagingCopy(stage);
        idxTrxValue.setStagingSecEnvelope(replicatedSecEnvelope);

        ISecEnvelopeTrxValue trxValue = createStagingSecEnvelope(idxTrxValue);
        trxValue = updateSecEnvelopeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
