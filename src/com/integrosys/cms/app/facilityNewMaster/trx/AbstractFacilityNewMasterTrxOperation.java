package com.integrosys.cms.app.facilityNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract FacilityNewMaster Operation 
 */

public abstract class AbstractFacilityNewMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IFacilityNewMasterBusManager facilityNewMasterBusManager;

    private IFacilityNewMasterBusManager stagingFacilityNewMasterBusManager;

 

    public IFacilityNewMasterBusManager getFacilityNewMasterBusManager() {
		return facilityNewMasterBusManager;
	}

	public void setFacilityNewMasterBusManager(
			IFacilityNewMasterBusManager facilityNewMasterBusManager) {
		this.facilityNewMasterBusManager = facilityNewMasterBusManager;
	}

	public IFacilityNewMasterBusManager getStagingFacilityNewMasterBusManager() {
		return stagingFacilityNewMasterBusManager;
	}

	public void setStagingFacilityNewMasterBusManager(
			IFacilityNewMasterBusManager stagingFacilityNewMasterBusManager) {
		this.stagingFacilityNewMasterBusManager = stagingFacilityNewMasterBusManager;
	}

	protected IFacilityNewMasterTrxValue prepareTrxValue(IFacilityNewMasterTrxValue facilityNewMasterTrxValue)throws TrxOperationException {
        if (facilityNewMasterTrxValue != null) {
            IFacilityNewMaster actual = facilityNewMasterTrxValue.getFacilityNewMaster();
            IFacilityNewMaster staging = facilityNewMasterTrxValue.getStagingFacilityNewMaster();
            if (actual != null) {
            	facilityNewMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	facilityNewMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	facilityNewMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	facilityNewMasterTrxValue.setStagingReferenceID(null);
            }
            return facilityNewMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- FacilityNewMaster is null");
        }
    }
	/**
	 * 
	 * @param facilityNewMasterTrxValue
	 * @return IFacilityNewMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected IFacilityNewMasterTrxValue updateFacilityNewMasterTrx(IFacilityNewMasterTrxValue facilityNewMasterTrxValue) throws TrxOperationException {
        try {
        	facilityNewMasterTrxValue = prepareTrxValue(facilityNewMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(facilityNewMasterTrxValue);
            OBFacilityNewMasterTrxValue newValue = new OBFacilityNewMasterTrxValue(tempValue);
            newValue.setFacilityNewMaster(facilityNewMasterTrxValue.getFacilityNewMaster());
            newValue.setStagingFacilityNewMaster(facilityNewMasterTrxValue.getStagingFacilityNewMaster());
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

    protected IFacilityNewMasterTrxValue createStagingFacilityNewMaster(IFacilityNewMasterTrxValue facilityNewMasterTrxValue) throws TrxOperationException {
        try {
            IFacilityNewMaster facilityNewMaster = getStagingFacilityNewMasterBusManager().createFacilityNewMaster(facilityNewMasterTrxValue.getStagingFacilityNewMaster());
            facilityNewMasterTrxValue.setStagingFacilityNewMaster(facilityNewMaster);
            facilityNewMasterTrxValue.setStagingReferenceID(String.valueOf(facilityNewMaster.getId()));
            return facilityNewMasterTrxValue;
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

    protected IFacilityNewMasterTrxValue getFacilityNewMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IFacilityNewMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCFacilityNewMasterTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IFacilityNewMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
