package com.integrosys.cms.app.securityenvelope.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeBusManager;
import com.integrosys.cms.app.securityenvelope.bus.SecEnvelopeException;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Set;
import java.util.Iterator;

/**
 * Title: CLIMS Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 2, 2010
 */
public class SecEnvelopeProxyManagerImpl implements ISecEnvelopeProxyManager {

    private ISecEnvelopeBusManager secEnvelopeBusManager;

    private ISecEnvelopeBusManager stagingSecEnvelopeBusManager;

    private ITrxControllerFactory trxControllerFactory;

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

    public ITrxControllerFactory getTrxControllerFactory() {
        return trxControllerFactory;
    }

    public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
        this.trxControllerFactory = trxControllerFactory;
    }
  
    public ISecEnvelopeTrxValue getSecEnvelopeByTrxID(String trxID) {
        ISecEnvelopeTrxValue trxValue = new OBSecEnvelopeTrxValue();
        trxValue.setTransactionID(String.valueOf(trxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SECURITY_ENVELOPE);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SECENVELOPE);
        return operate(trxValue, param);
    }

    public ISecEnvelopeTrxValue getSecEnvelopeTrxValue(long aSecEnvelope) throws SecEnvelopeException {
        if (aSecEnvelope == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new SecEnvelopeException("Invalid secEnvelopeId");
        }
        ISecEnvelopeTrxValue trxValue = new OBSecEnvelopeTrxValue();
        trxValue.setReferenceID(String.valueOf(aSecEnvelope));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_SECURITY_ENVELOPE);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_SECENVELOPE_ID);
        return operate(trxValue, param);
    }

    public ISecEnvelopeTrxValue getActualMasterSecEnvelope(long aLimitProfID) throws SecEnvelopeException {
        String transactionId ="";
        if (aLimitProfID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new SecEnvelopeException("Invalid limit prof id");
        }
        List trxIDList = getSecEnvelopeBusManager().getActualSecEnvelope(aLimitProfID);
        Iterator trxIdIter = trxIDList.iterator();
        while(trxIdIter.hasNext()){
            transactionId = (String) trxIdIter.next();    
        }
        ISecEnvelopeTrxValue trxValue = new OBSecEnvelopeTrxValue();
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        if(transactionId != null && !transactionId.equals("")){
//            System.out.println("################Inside the searching for key##############");
            trxValue.setTransactionID(String.valueOf(transactionId));
            trxValue.setTransactionType(ICMSConstant.INSTANCE_SECURITY_ENVELOPE);
            param.setAction(ICMSConstant.ACTION_READ_SECENVELOPE);
            return operate(trxValue, param);
        } else{
//            System.out.println("################Outside the searching for key##############");
            return trxValue;
        }
    }

    public ISecEnvelopeTrxValue makerCreateSecEnvelope(ITrxContext anITrxContext, ISecEnvelope anICCSecEnvelope) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anICCSecEnvelope == null) {
            throw new SecEnvelopeException("The ICCSecEnvelope to be updated is null !!!");
        }

        ISecEnvelopeTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCSecEnvelope);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_SECENV);
        return operate(trxValue, param);
    }

    public ISecEnvelopeTrxValue makerUpdateSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue, ISecEnvelope anICCSecEnvelope) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anICCSecEnvelope == null) {
            throw new SecEnvelopeException("The ICCSecEnvelope to be updated is null !!!");
        }
        ISecEnvelopeTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSecEnvelopeTrxValue, anICCSecEnvelope);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SECENV);
        return operate(trxValue, param);
    }

    public ISecEnvelopeTrxValue makerDeleteSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue, ISecEnvelope anICCSecEnvelope) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anICCSecEnvelope == null) {
            throw new SecEnvelopeException("The ICCPropertyIdx to be updated is null !!!");
        }
        ISecEnvelopeTrxValue trxValue = formulateTrxValue(anITrxContext, anICCSecEnvelopeTrxValue, anICCSecEnvelope);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_SECENV);
        return operate(trxValue, param);
    }

    /**
     * Checker approve the document location
     */
    public ISecEnvelopeTrxValue checkerApproveSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anISecEnvelopeTrxValue == null) {
            throw new SecEnvelopeException
                    ("The IPropertyIdxTrxValue to be updated is null!!!");
        }
        anISecEnvelopeTrxValue = formulateTrxValue(anITrxContext, anISecEnvelopeTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SECENV);
        return operate(anISecEnvelopeTrxValue, param);
    }

    public ISecEnvelopeTrxValue checkerRejectSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anISecEnvelopeTrxValue == null) {
            throw new SecEnvelopeException("The ISecEnvelopeTrxValue to be updated is null!!!");
        }
        anISecEnvelopeTrxValue = formulateTrxValue(anITrxContext, anISecEnvelopeTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SECENV);
        return operate(anISecEnvelopeTrxValue, param);
    }

    public ISecEnvelopeTrxValue makerEditRejectedSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue, ISecEnvelope anISecEnvelope) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anISecEnvelopeTrxValue == null) {
            throw new SecEnvelopeException("The ISecTrxValue to be updated is null!!!");
        }
        if (anISecEnvelope == null) {
            throw new SecEnvelopeException("The ISecEnvelope to be updated is null !!!");
        }
        anISecEnvelopeTrxValue = formulateTrxValue(anITrxContext, anISecEnvelopeTrxValue, anISecEnvelope);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_SECENV);
        return operate(anISecEnvelopeTrxValue, param);
    }

    public ISecEnvelopeTrxValue makerCloseRejectedSecEnvelope(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) throws SecEnvelopeException {
        if (anITrxContext == null) {
            throw new SecEnvelopeException("The ITrxContext is null!!!");
        }
        if (anISecEnvelopeTrxValue == null) {
            throw new SecEnvelopeException("The ISecEnvelopeTrxValue to be updated is null!!!");
        }
        anISecEnvelopeTrxValue = formulateTrxValue(anITrxContext, anISecEnvelopeTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_SECENV);
        return operate(anISecEnvelopeTrxValue, param);
    }

    private ISecEnvelopeTrxValue operate(ISecEnvelopeTrxValue anISecEnvelopeTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws SecEnvelopeException {
        ICMSTrxResult result = operateForResult(anISecEnvelopeTrxValue, anOBCMSTrxParameter);
        return (ISecEnvelopeTrxValue) result.getTrxValue();
    }
    /*
     * @param anOBCMSTrxParameter - OBCMSTrxParameter
     * @return ICMSTrxResult - the trx result interface
     */
    protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
            throws SecEnvelopeException {
        try {
            ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
            Validate.notNull(controller, "'controller' must not be null, check the controller factory");

            ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
            return (ICMSTrxResult) result;
        }
        catch (TransactionException e) {
            throw new SecEnvelopeException(e);
        }
        catch (Exception ex) {
            throw new SecEnvelopeException(ex.toString());
        }
    }

    private ISecEnvelopeTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, ISecEnvelope anISecEnvelope) {
        ISecEnvelopeTrxValue ccSecEnvelopeTrxValue = null;
        if (anICMSTrxValue != null) {
            ccSecEnvelopeTrxValue = new OBSecEnvelopeTrxValue(anICMSTrxValue);
        } else {
            ccSecEnvelopeTrxValue = new OBSecEnvelopeTrxValue();
        }
        ccSecEnvelopeTrxValue = formulateTrxValue(anITrxContext, (ISecEnvelopeTrxValue) ccSecEnvelopeTrxValue);
        ccSecEnvelopeTrxValue.setStagingSecEnvelope(anISecEnvelope);
        return ccSecEnvelopeTrxValue;
    }

    private ISecEnvelopeTrxValue formulateTrxValue(ITrxContext anITrxContext, ISecEnvelopeTrxValue anISecEnvelopeTrxValue) {
        anISecEnvelopeTrxValue.setTrxContext(anITrxContext);
        anISecEnvelopeTrxValue.setTransactionType(ICMSConstant.INSTANCE_SECURITY_ENVELOPE);
        return anISecEnvelopeTrxValue;
    }

    public List getAllActual() {
        return getSecEnvelopeBusManager().getAllSecEnvelope();
    }

    public List getAllEnvItemStaging(){
        return getSecEnvelopeBusManager().getAllSecEnvelopeItemStaging();
    }

    public int getNumDocItemInEnvelope(String envBarcode){
        return getSecEnvelopeBusManager().getNumDocItemInEnvelope(envBarcode);
    }

    public List getActualSecEnvelope(long lspLmtProfileId) {
        return getSecEnvelopeBusManager().getActualSecEnvelope(lspLmtProfileId);
    }
    
    public List getEnvelopeItemByLimitProfileId(long lspLmtProfileId){
    	return getSecEnvelopeBusManager().getEnvelopeItemByLimitProfileId(lspLmtProfileId);
    }
}
