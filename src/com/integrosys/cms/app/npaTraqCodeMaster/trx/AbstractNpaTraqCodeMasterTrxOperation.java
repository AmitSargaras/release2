package com.integrosys.cms.app.npaTraqCodeMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMaster;
import com.integrosys.cms.app.npaTraqCodeMaster.bus.INpaTraqCodeMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractNpaTraqCodeMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private INpaTraqCodeMasterBusManager npaTraqCodeMasterBusManager;
	private INpaTraqCodeMasterBusManager stagingNpaTraqCodeMasterBusManager;
	
	public INpaTraqCodeMasterBusManager getNpaTraqCodeMasterBusManager() {
		return npaTraqCodeMasterBusManager;
	}

	public void setNpaTraqCodeMasterBusManager(INpaTraqCodeMasterBusManager npaTraqCodeMasterBusManager) {
		this.npaTraqCodeMasterBusManager = npaTraqCodeMasterBusManager;
	}

	public INpaTraqCodeMasterBusManager getStagingNpaTraqCodeMasterBusManager() {
		return stagingNpaTraqCodeMasterBusManager;
	}

	public void setStagingNpaTraqCodeMasterBusManager(INpaTraqCodeMasterBusManager stagingNpaTraqCodeMasterBusManager) {
		this.stagingNpaTraqCodeMasterBusManager = stagingNpaTraqCodeMasterBusManager;
	}
	

	protected INpaTraqCodeMasterTrxValue prepareTrxValue(INpaTraqCodeMasterTrxValue npaTraqCodeMasterTrxValue)throws TrxOperationException {
        if (npaTraqCodeMasterTrxValue != null) {
        	INpaTraqCodeMaster actual = npaTraqCodeMasterTrxValue.getNpaTraqCodeMaster();
        	INpaTraqCodeMaster staging = npaTraqCodeMasterTrxValue.getStagingNpaTraqCodeMaster();
            if (actual != null) {
            	npaTraqCodeMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	npaTraqCodeMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	npaTraqCodeMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	npaTraqCodeMasterTrxValue.setStagingReferenceID(null);
            }
            return npaTraqCodeMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- npaTraqCodeMaster is null");
        }
    }
	
	/**
	 * 
	 * @param NpaTraqCodeMasterTrxValue
	 * @return INpaTraqCodeMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected INpaTraqCodeMasterTrxValue updateNpaTraqCodeMasterTrx(INpaTraqCodeMasterTrxValue npaTraqCodeMasterTrxValue) throws TrxOperationException {
        try {
        	npaTraqCodeMasterTrxValue = prepareTrxValue(npaTraqCodeMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(npaTraqCodeMasterTrxValue);
            OBNpaTraqCodeMasterTrxValue newValue = new OBNpaTraqCodeMasterTrxValue(tempValue);
            newValue.setNpaTraqCodeMaster(npaTraqCodeMasterTrxValue.getNpaTraqCodeMaster());
            newValue.setStagingNpaTraqCodeMaster(npaTraqCodeMasterTrxValue.getStagingNpaTraqCodeMaster());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param NpaTraqCodeMasterTrxValue
     * @return INpaTraqCodeMasterTrxValue
     * @throws TrxOperationException
     */

    protected INpaTraqCodeMasterTrxValue createStagingNpaTraqCodeMaster(INpaTraqCodeMasterTrxValue npaTraqCodeMasterTrxValue) throws TrxOperationException {
        try {
        	INpaTraqCodeMaster npaTraqCodeMaster = getStagingNpaTraqCodeMasterBusManager().createNpaTraqCodeMaster(npaTraqCodeMasterTrxValue.getStagingNpaTraqCodeMaster());
        	npaTraqCodeMasterTrxValue.setStagingNpaTraqCodeMaster(npaTraqCodeMaster);
        	npaTraqCodeMasterTrxValue.setStagingReferenceID(String.valueOf(npaTraqCodeMaster.getId()));
            return npaTraqCodeMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return INpaTraqCodeMasterTrxValue
     * @throws TrxOperationException
     */

    protected INpaTraqCodeMasterTrxValue getNpaTraqCodeMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (INpaTraqCodeMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBNpaTraqCodeMasterTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(INpaTraqCodeMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
