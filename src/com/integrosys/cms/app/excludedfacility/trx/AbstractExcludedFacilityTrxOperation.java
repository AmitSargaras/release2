package com.integrosys.cms.app.excludedfacility.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacilityBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractExcludedFacilityTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private IExcludedFacilityBusManager excludedFacilityBusManager;

	private IExcludedFacilityBusManager stagingExcludedFacilityBusManager;
	
    public IExcludedFacilityBusManager getExcludedFacilityBusManager() {
		return excludedFacilityBusManager;
	}

	public void setExcludedFacilityBusManager(IExcludedFacilityBusManager excludedFacilityBusManager) {
		this.excludedFacilityBusManager = excludedFacilityBusManager;
	}

	public IExcludedFacilityBusManager getStagingExcludedFacilityBusManager() {
		return stagingExcludedFacilityBusManager;
	}

	public void setStagingExcludedFacilityBusManager(IExcludedFacilityBusManager stagingExcludedFacilityBusManager) {
		this.stagingExcludedFacilityBusManager = stagingExcludedFacilityBusManager;
	}

	protected IExcludedFacilityTrxValue prepareTrxValue(IExcludedFacilityTrxValue excludedFacilityTrxValue)throws TrxOperationException {
        if (excludedFacilityTrxValue != null) {
        	IExcludedFacility actual = excludedFacilityTrxValue.getExcludedFacility();
        	IExcludedFacility staging = excludedFacilityTrxValue.getStagingExcludedFacility();
            if (actual != null) {
            	excludedFacilityTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	excludedFacilityTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	excludedFacilityTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	excludedFacilityTrxValue.setStagingReferenceID(null);
            }
            return excludedFacilityTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Excluded Facility is null");
        }
    }
	/**
	 * 
	 * @param facilityNewMasterTrxValue
	 * @return IFacilityNewMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected IExcludedFacilityTrxValue updateExcludedFacilityTrx(IExcludedFacilityTrxValue excludedFacilityTrxValue) throws TrxOperationException {
        try {
        	excludedFacilityTrxValue = prepareTrxValue(excludedFacilityTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(excludedFacilityTrxValue);
            OBExcludedFacilityTrxValue newValue = new OBExcludedFacilityTrxValue(tempValue);
            newValue.setExcludedFacility(excludedFacilityTrxValue.getExcludedFacility());
            newValue.setStagingExcludedFacility(excludedFacilityTrxValue.getStagingExcludedFacility());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param facilityNewMasterTrxValue
     * @return IFacilityNewMasterTrxValue
     * @throws TrxOperationException
     */

    protected IExcludedFacilityTrxValue createStagingExcludedFacility(IExcludedFacilityTrxValue excludedFacilityTrxValue) throws TrxOperationException {
        try {
        	IExcludedFacility excludedFacility = getStagingExcludedFacilityBusManager().createExcludedFacility(excludedFacilityTrxValue.getStagingExcludedFacility());
            excludedFacilityTrxValue.setStagingExcludedFacility(excludedFacility);
            excludedFacilityTrxValue.setStagingReferenceID(String.valueOf(excludedFacility.getId()));
            return excludedFacilityTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IFacilityNewMasterTrxValue
     * @throws TrxOperationException
     */

    protected IExcludedFacilityTrxValue getExcludedFacilityTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IExcludedFacilityTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBExcludedFacilityTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IExcludedFacilityTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
