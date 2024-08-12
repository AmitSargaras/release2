package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeReplicationUtils;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Feb 08, 2010
 */
public class MakerEditRejectedCreateSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation{
  /**
    * Defaulc Constructor
    */
    public MakerEditRejectedCreateSecEnvelopeOperation()
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
        return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_SECENV;
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
        ISecEnvelopeTrxValue idxTrxValue = getSecEnvelopeTrxValue(anITrxValue);
        ISecEnvelope stage = idxTrxValue.getStagingSecEnvelope();
        ISecEnvelope replicatedSecEnvelope = SecEnvelopeReplicationUtils.replicateSecEnvelopeForCreateStagingCopy(stage);
        idxTrxValue.setStagingSecEnvelope(replicatedSecEnvelope);

        ISecEnvelopeTrxValue trxValue = createStagingSecEnvelope(idxTrxValue);
        trxValue = updateSecEnvelopeTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
