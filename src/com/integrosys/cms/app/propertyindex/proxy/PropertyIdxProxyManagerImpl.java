package com.integrosys.cms.app.propertyindex.proxy;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdxBusManager;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyindex.trx.OBPropertyIdxTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import org.apache.commons.lang.Validate;

import java.util.List;
import java.util.Set;

/**
 * Title: CLIMS Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */
public class PropertyIdxProxyManagerImpl implements IPropertyIdxProxyManager {

    private IPropertyIdxBusManager propertyIdxBusManager;

    private IPropertyIdxBusManager stagingPropertyIdxBusManager;

    private ITrxControllerFactory trxControllerFactory;

    public IPropertyIdxBusManager getPropertyIdxBusManager() {
        return propertyIdxBusManager;
    }

    public void setPropertyIdxBusManager(IPropertyIdxBusManager propertyIdxBusManager) {
        this.propertyIdxBusManager = propertyIdxBusManager;
    }

    public IPropertyIdxBusManager getStagingPropertyIdxBusManager() {
        return stagingPropertyIdxBusManager;
    }

    public void setStagingPropertyIdxBusManager(IPropertyIdxBusManager stagingPropertyIdxBusManager) {
        this.stagingPropertyIdxBusManager = stagingPropertyIdxBusManager;
    }

    public ITrxControllerFactory getTrxControllerFactory() {
        return trxControllerFactory;
    }

    public void setTrxControllerFactory(ITrxControllerFactory trxControllerFactory) {
        this.trxControllerFactory = trxControllerFactory;
    }

    public IPropertyIdxTrxValue getPropertyIdxByTrxID(String trxID) {
        IPropertyIdxTrxValue trxValue = new OBPropertyIdxTrxValue();
        trxValue.setTransactionID(String.valueOf(trxID));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_PROPERTY_IDX);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_PRIDX);
        return operate(trxValue, param);
    }

    public IPropertyIdxTrxValue getPropertyIdxTrxValue(long aPropertyIdx) throws PropertyIdxException {
        if (aPropertyIdx == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            throw new PropertyIdxException("Invalid propertyIdxId");
        }
        IPropertyIdxTrxValue trxValue = new OBPropertyIdxTrxValue();
        trxValue.setReferenceID(String.valueOf(aPropertyIdx));
        trxValue.setTransactionType(ICMSConstant.INSTANCE_PROPERTY_IDX);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_PRIDX_ID);
        return operate(trxValue, param);
    }

    public IPropertyIdxTrxValue makerCreatePropertyIdx(ITrxContext anITrxContext, IPropertyIdx anICCPropertyIdx) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anICCPropertyIdx == null) {
            throw new PropertyIdxException("The ICCPropertyIdx to be updated is null !!!");
        }

        IPropertyIdxTrxValue trxValue = formulateTrxValue(anITrxContext, null, anICCPropertyIdx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CREATE_PRIDX);
        return operate(trxValue, param);
    }

    public IPropertyIdxTrxValue makerUpdatePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anICCPropertyIdxTrxValue, IPropertyIdx anICCPropertyIdx) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anICCPropertyIdx == null) {
            throw new PropertyIdxException("The ICCPropertyIdx to be updated is null !!!");
        }
        IPropertyIdxTrxValue trxValue = formulateTrxValue(anITrxContext, anICCPropertyIdxTrxValue, anICCPropertyIdx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_PRIDX);
        return operate(trxValue, param);
    }

    public IPropertyIdxTrxValue makerDeletePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anICCPropertyIdxTrxValue, IPropertyIdx anICCPropertyIdx) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anICCPropertyIdx == null) {
            throw new PropertyIdxException("The ICCPropertyIdx to be updated is null !!!");
        }
        IPropertyIdxTrxValue trxValue = formulateTrxValue(anITrxContext, anICCPropertyIdxTrxValue, anICCPropertyIdx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_PRIDX);
        return operate(trxValue, param);
    }

    /**
     * Checker approve the document location
     */
    public IPropertyIdxTrxValue checkerApprovePropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anIPropertyIdxTrxValue == null) {
            throw new PropertyIdxException
                    ("The IPropertyIdxTrxValue to be updated is null!!!");
        }
        anIPropertyIdxTrxValue = formulateTrxValue(anITrxContext, anIPropertyIdxTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_PRIDX);
        return operate(anIPropertyIdxTrxValue, param);
    }

    public IPropertyIdxTrxValue checkerRejectPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anIPropertyIdxTrxValue == null) {
            throw new PropertyIdxException("The IPropertyIdxTrxValue to be updated is null!!!");
        }
        anIPropertyIdxTrxValue = formulateTrxValue(anITrxContext, anIPropertyIdxTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_PRIDX);
        return operate(anIPropertyIdxTrxValue, param);
    }

    public IPropertyIdxTrxValue makerEditRejectedPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue, IPropertyIdx anIPropertyIdx) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anIPropertyIdxTrxValue == null) {
            throw new PropertyIdxException("The IPropertyIdxTrxValue to be updated is null!!!");
        }
        if (anIPropertyIdx == null) {
            throw new PropertyIdxException("The IPropertyIdx to be updated is null !!!");
        }
        anIPropertyIdxTrxValue = formulateTrxValue(anITrxContext, anIPropertyIdxTrxValue, anIPropertyIdx);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_EDIT_REJECTED_PRIDX);
        return operate(anIPropertyIdxTrxValue, param);
    }

    public IPropertyIdxTrxValue makerCloseRejectedPropertyIdx(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) throws PropertyIdxException {
        if (anITrxContext == null) {
            throw new PropertyIdxException("The ITrxContext is null!!!");
        }
        if (anIPropertyIdxTrxValue == null) {
            throw new PropertyIdxException("The IPropertyIdxTrxValue to be updated is null!!!");
        }
        anIPropertyIdxTrxValue = formulateTrxValue(anITrxContext, anIPropertyIdxTrxValue);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_PRIDX);
        return operate(anIPropertyIdxTrxValue, param);
    }

    private IPropertyIdxTrxValue operate(IPropertyIdxTrxValue anIPrIdxTrxValue, OBCMSTrxParameter anOBCMSTrxParameter) throws PropertyIdxException {
        ICMSTrxResult result = operateForResult(anIPrIdxTrxValue, anOBCMSTrxParameter);
        return (IPropertyIdxTrxValue) result.getTrxValue();
    }

    /**
     * @param anICMSTrxValue
     * @param anOBCMSTrxParameter - OBCMSTrxParameter
     * @return ICMSTrxResult - the trx result interface
     */
    protected ICMSTrxResult operateForResult(ICMSTrxValue anICMSTrxValue, OBCMSTrxParameter anOBCMSTrxParameter)
            throws PropertyIdxException {
        try {
            ITrxController controller = getTrxControllerFactory().getController(anICMSTrxValue, anOBCMSTrxParameter);
            Validate.notNull(controller, "'controller' must not be null, check the controller factory");

            ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);
            return (ICMSTrxResult) result;
        }
        catch (TransactionException e) {
            throw new PropertyIdxException(e);
        }
        catch (Exception ex) {
            throw new PropertyIdxException(ex.toString());
        }
    }

    private IPropertyIdxTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue, IPropertyIdx anIPropertyIdx) {
        IPropertyIdxTrxValue ccPropertyIdxTrxValue = null;
        if (anICMSTrxValue != null) {
            ccPropertyIdxTrxValue = new OBPropertyIdxTrxValue(anICMSTrxValue);
        } else {
            ccPropertyIdxTrxValue = new OBPropertyIdxTrxValue();
        }
        ccPropertyIdxTrxValue = formulateTrxValue(anITrxContext, (IPropertyIdxTrxValue) ccPropertyIdxTrxValue);
        ccPropertyIdxTrxValue.setStagingPrIdx(anIPropertyIdx);
        return ccPropertyIdxTrxValue;
    }

    private IPropertyIdxTrxValue formulateTrxValue(ITrxContext anITrxContext, IPropertyIdxTrxValue anIPropertyIdxTrxValue) {
        anIPropertyIdxTrxValue.setTrxContext(anITrxContext);
        anIPropertyIdxTrxValue.setTransactionType(ICMSConstant.INSTANCE_PROPERTY_IDX);
        return anIPropertyIdxTrxValue;
    }

    public List getAllActual() {
        return getPropertyIdxBusManager().getAllPropertyIdx();
    }

    public boolean isSecSubTypeValTypeExist (long propertyIndexID, Set secSubTypeList, String valDesc){
        return getPropertyIdxBusManager().isSecSubTypeValTypeExist(propertyIndexID, secSubTypeList, valDesc);
    }
}
