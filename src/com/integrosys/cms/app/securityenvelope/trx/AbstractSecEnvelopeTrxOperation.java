package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeBusManager;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 2, 2010
 */

public abstract class AbstractSecEnvelopeTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ISecEnvelopeBusManager secEnvelopeBusManager;

    private ISecEnvelopeBusManager stagingSecEnvelopeBusManager;

    public ISecEnvelopeBusManager getSecEnvelopeBusManager() {
        return secEnvelopeBusManager;
    }

    public void setSecEnvelopeBusManager(ISecEnvelopeBusManager secEnvelopeBusManager) {
        this.secEnvelopeBusManager = secEnvelopeBusManager;
    }

    public ISecEnvelopeBusManager getStagingSecEnvelopeBusManager() {
        return stagingSecEnvelopeBusManager;
    }

    public void setStagingSecEnvelopeBusManager(ISecEnvelopeBusManager stagingSecEnvelopeBusManager) {
        this.stagingSecEnvelopeBusManager = stagingSecEnvelopeBusManager;
    }

    protected ISecEnvelopeTrxValue prepareTrxValue(ISecEnvelopeTrxValue secEnvelopeTrxValue) {
        if (secEnvelopeTrxValue != null) {
            ISecEnvelope actual = secEnvelopeTrxValue.getSecEnvelope();
            ISecEnvelope staging = secEnvelopeTrxValue.getStagingSecEnvelope();
            if (actual != null) {
                secEnvelopeTrxValue.setReferenceID(String.valueOf(actual.getSecEnvelopeId()));
            } else {
                secEnvelopeTrxValue.setReferenceID(null);
            }                                                       
            if (staging != null) {
                secEnvelopeTrxValue.setStagingReferenceID(String.valueOf(staging.getSecEnvelopeId()));
            } else {
                secEnvelopeTrxValue.setStagingReferenceID(null);
            }
            return secEnvelopeTrxValue;
        }
        return null;
    }

    protected ISecEnvelopeTrxValue updateSecEnvelopeTrx(ISecEnvelopeTrxValue secEnvelopeTrxValue) throws TrxOperationException {
        try {
            secEnvelopeTrxValue = prepareTrxValue(secEnvelopeTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(secEnvelopeTrxValue);
            OBSecEnvelopeTrxValue newValue = new OBSecEnvelopeTrxValue(tempValue);
            newValue.setSecEnvelope(secEnvelopeTrxValue.getSecEnvelope());
            newValue.setStagingSecEnvelope(secEnvelopeTrxValue.getStagingSecEnvelope());
            return newValue;
        }
        catch (TransactionException ex) {
            throw new TrxOperationException(ex);
        }
        catch (Exception ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }

    protected ISecEnvelopeTrxValue createStagingSecEnvelope(ISecEnvelopeTrxValue secEnvelopeTrxValue) throws TrxOperationException {
        try {
            ISecEnvelope secEnvelope = getStagingSecEnvelopeBusManager().createSecEnvelope(secEnvelopeTrxValue.getStagingSecEnvelope());
            secEnvelopeTrxValue.setStagingSecEnvelope(secEnvelope);
            secEnvelopeTrxValue.setStagingReferenceID(String.valueOf(secEnvelope.getSecEnvelopeId()));
            return secEnvelopeTrxValue;
        }
        catch (SecEnvelopeException ex) {
            throw new TrxOperationException(ex);
        }
    }

    protected ISecEnvelopeTrxValue getSecEnvelopeTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ISecEnvelopeTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCSecEnvelopeTrxValue: " + ex.toString());
        }
    }

    protected ISecEnvelope mergeSecEnvelope(ISecEnvelope anOriginal, ISecEnvelope aCopy) throws TrxOperationException {
        aCopy.setSecEnvelopeId(anOriginal.getSecEnvelopeId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }

    protected ITrxResult prepareResult(ISecEnvelopeTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
