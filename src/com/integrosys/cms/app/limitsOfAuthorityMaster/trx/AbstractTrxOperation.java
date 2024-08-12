package com.integrosys.cms.app.limitsOfAuthorityMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.IBusManager;
import com.integrosys.cms.app.limitsOfAuthorityMaster.bus.ILimitsOfAuthorityMaster;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IBusManager busManager;

	private IBusManager stagingBusManager;
	
	public IBusManager getBusManager() {
		return busManager;
	}

	public void setBusManager(IBusManager busManager) {
		this.busManager = busManager;
	}

	public IBusManager getStagingBusManager() {
		return stagingBusManager;
	}

	public void setStagingBusManager(IBusManager stagingBusManager) {
		this.stagingBusManager = stagingBusManager;
	}

	protected ILimitsOfAuthorityMasterTrxValue prepareTrxValue(ILimitsOfAuthorityMasterTrxValue trx)throws TrxOperationException {
        if (trx != null) {
        	ILimitsOfAuthorityMaster actual = trx.getActual();
        	ILimitsOfAuthorityMaster staging = trx.getStaging();
            if (actual != null) {
            	trx.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	trx.setReferenceID(null);
            }
            if (staging != null) {
            	trx.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	trx.setStagingReferenceID(null);
            }
            return trx;
        }
        else{
        	throw new  TrxOperationException("ERROR-- ILimitsOfAuthorityMaster is null");
        }
    }
	
    protected ILimitsOfAuthorityMasterTrxValue updateTrx(ILimitsOfAuthorityMasterTrxValue trx) throws TrxOperationException {
        try {
        	trx = prepareTrxValue(trx);
            ICMSTrxValue tempValue = super.updateTransaction(trx);
            OBLimitsOfAuthorityMasterTrxValue newValue = new OBLimitsOfAuthorityMasterTrxValue(tempValue);
            newValue.setActual(trx.getActual());
            newValue.setStaging(trx.getStaging());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    protected ILimitsOfAuthorityMasterTrxValue createStaging(ILimitsOfAuthorityMasterTrxValue trx) throws TrxOperationException {
        try {
        	ILimitsOfAuthorityMaster obj = getStagingBusManager().create(trx.getStaging());
        	trx.setStaging(obj);
        	trx.setStagingReferenceID(String.valueOf(obj.getId()));
            return trx;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected ILimitsOfAuthorityMasterTrxValue getTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ILimitsOfAuthorityMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBILimitsOfAuthorityMasterTrxValue: " + ex.toString());
        }
    }
    
    protected ITrxResult prepareResult(ILimitsOfAuthorityMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}