package com.integrosys.cms.app.bankingArrangementFacExclusion.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusion;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBusManager;
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

	protected IBankingArrangementFacExclusionTrxValue prepareTrxValue(IBankingArrangementFacExclusionTrxValue trx)throws TrxOperationException {
        if (trx != null) {
        	IBankingArrangementFacExclusion actual = trx.getActual();
        	IBankingArrangementFacExclusion staging = trx.getStaging();
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
        	throw new  TrxOperationException("ERROR-- BankingArrangementFacExclusion is null");
        }
    }
	
    protected IBankingArrangementFacExclusionTrxValue updateTrx(IBankingArrangementFacExclusionTrxValue trx) throws TrxOperationException {
        try {
        	trx = prepareTrxValue(trx);
            ICMSTrxValue tempValue = super.updateTransaction(trx);
            OBBankingArrangementFacExclusionTrxValue newValue = new OBBankingArrangementFacExclusionTrxValue(tempValue);
            newValue.setActual(trx.getActual());
            newValue.setStaging(trx.getStaging());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    protected IBankingArrangementFacExclusionTrxValue createStaging(IBankingArrangementFacExclusionTrxValue trx) throws TrxOperationException {
        try {
        	IBankingArrangementFacExclusion obj = getStagingBusManager().create(trx.getStaging());
        	trx.setStaging(obj);
        	trx.setStagingReferenceID(String.valueOf(obj.getId()));
            return trx;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IBankingArrangementFacExclusionTrxValue getTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IBankingArrangementFacExclusionTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBBankingArrangementFacExclusionTrxValue: " + ex.toString());
        }
    }
    
    protected ITrxResult prepareResult(IBankingArrangementFacExclusionTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}