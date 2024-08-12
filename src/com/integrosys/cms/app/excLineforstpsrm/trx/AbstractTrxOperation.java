package com.integrosys.cms.app.excLineforstpsrm.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRM;
import com.integrosys.cms.app.excLineforstpsrm.bus.IBusManager;
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

	protected IExcLineForSTPSRMTrxValue prepareTrxValue(IExcLineForSTPSRMTrxValue trx)throws TrxOperationException {
        if (trx != null) {
        	IExcLineForSTPSRM actual = trx.getActual();
        	IExcLineForSTPSRM staging = trx.getStaging();
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
        	throw new  TrxOperationException("ERROR-- Exclusion Line for STP for SRM is null");
        }
    }
	
    protected IExcLineForSTPSRMTrxValue updateTrx(IExcLineForSTPSRMTrxValue trx) throws TrxOperationException {
        try {
        	trx = prepareTrxValue(trx);
            ICMSTrxValue tempValue = super.updateTransaction(trx);
            OBExcLineForSTPSRMTrxValue newValue = new OBExcLineForSTPSRMTrxValue(tempValue);
            newValue.setActual(trx.getActual());
            newValue.setStaging(trx.getStaging());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    protected IExcLineForSTPSRMTrxValue createStaging(IExcLineForSTPSRMTrxValue trx) throws TrxOperationException {
        try {
        	IExcLineForSTPSRM obj = getStagingBusManager().create(trx.getStaging());
        	trx.setStaging(obj);
        	trx.setStagingReferenceID(String.valueOf(obj.getId()));
            return trx;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IExcLineForSTPSRMTrxValue getTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IExcLineForSTPSRMTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type IExcLineForSTPSRMTrxValue: " + ex.toString());
        }
    }
    
    protected ITrxResult prepareResult(IExcLineForSTPSRMTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}