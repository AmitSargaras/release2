package com.integrosys.cms.app.propertyindex.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdxBusManager;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 17, 2008
 */

public abstract class AbstractPropertyIdxTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IPropertyIdxBusManager propertyIdxBusManager;

    private IPropertyIdxBusManager stagingPropertyIdxBusManager;

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

    protected IPropertyIdxTrxValue prepareTrxValue(IPropertyIdxTrxValue propertyIdxTrxValue) {
        if (propertyIdxTrxValue != null) {
            IPropertyIdx actual = propertyIdxTrxValue.getPrIdx();
            IPropertyIdx staging = propertyIdxTrxValue.getStagingPrIdx();
            if (actual != null) {
                propertyIdxTrxValue.setReferenceID(String.valueOf(actual.getPropertyIdxId()));
            } else {
                propertyIdxTrxValue.setReferenceID(null);
            }
            if (staging != null) {
                propertyIdxTrxValue.setStagingReferenceID(String.valueOf(staging.getPropertyIdxId()));
            } else {
                propertyIdxTrxValue.setStagingReferenceID(null);
            }
            return propertyIdxTrxValue;
        }
        return null;
    }

    protected IPropertyIdxTrxValue updatePropertyIdxTrx(IPropertyIdxTrxValue propertyIdxTrxValue) throws TrxOperationException {
        try {
            propertyIdxTrxValue = prepareTrxValue(propertyIdxTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(propertyIdxTrxValue);
            OBPropertyIdxTrxValue newValue = new OBPropertyIdxTrxValue(tempValue);
            newValue.setPrIdx(propertyIdxTrxValue.getPrIdx());
            newValue.setStagingPrIdx(propertyIdxTrxValue.getStagingPrIdx());
            return newValue;
        }
        catch (TransactionException ex) {
            throw new TrxOperationException(ex);
        }
        catch (Exception ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }

    protected IPropertyIdxTrxValue createStagingPropertyIdx(IPropertyIdxTrxValue propertyIdxTrxValue) throws TrxOperationException {
        try {
            IPropertyIdx propertyIdx = getStagingPropertyIdxBusManager().createPropertyIdx(propertyIdxTrxValue.getStagingPrIdx());
            propertyIdxTrxValue.setStagingPrIdx(propertyIdx);
            propertyIdxTrxValue.setStagingReferenceID(String.valueOf(propertyIdx.getPropertyIdxId()));
            return propertyIdxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }

    protected IPropertyIdxTrxValue getPropertyIdxTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IPropertyIdxTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCPropertyIdxTrxValue: " + ex.toString());
        }
    }

    protected IPropertyIdx mergePropertyIdx(IPropertyIdx anOriginal, IPropertyIdx aCopy) throws TrxOperationException {
        aCopy.setPropertyIdxId(anOriginal.getPropertyIdxId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }

    protected ITrxResult prepareResult(IPropertyIdxTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
