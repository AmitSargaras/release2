package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeException;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeReplicationUtils;

import java.util.Set;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 4, 2010
 */

public class CheckerApproveCreateSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_SECENV;
    }

    /**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ISecEnvelopeTrxValue trxValue = getSecEnvelopeTrxValue(anITrxValue);
        trxValue = createActualSecEnvelope(trxValue);
        trxValue = updateSecEnvelopeTrx(trxValue);
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws TrxOperationException on errors
     */
    private ISecEnvelopeTrxValue createActualSecEnvelope(ISecEnvelopeTrxValue envTrxValue) throws TrxOperationException {
        try {
            ISecEnvelope staging = envTrxValue.getStagingSecEnvelope();
            ISecEnvelope replicatedSecEnvelope = SecEnvelopeReplicationUtils.replicateSecEnvelopeForCreateStagingCopy(staging);
            ISecEnvelope actual = getSecEnvelopeBusManager().createSecEnvelope(replicatedSecEnvelope);
            envTrxValue.setSecEnvelope(actual);
            envTrxValue.setReferenceID(String.valueOf(actual.getSecEnvelopeId()));

            Set updateSecEnvelopeEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(actual
				.getSecEnvelopeItemList(), "secEnvelopeRefId", Long.class, "secEnvelopeItemId");
		    actual.getSecEnvelopeItemList().clear();
		    if (updateSecEnvelopeEntryRefSet != null) {
			    actual.getSecEnvelopeItemList().addAll(updateSecEnvelopeEntryRefSet);
		    }
		    getSecEnvelopeBusManager().updateSecEnvelope(actual);
            return envTrxValue;
        }
        catch (SecEnvelopeException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
