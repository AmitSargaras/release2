package com.integrosys.cms.app.cersaiMapping.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMappingBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractCersaiMappingTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private ICersaiMappingBusManager cersaiMappingBusManager;

	private ICersaiMappingBusManager stagingCersaiMappingBusManager;
	
	

	public ICersaiMappingBusManager getCersaiMappingBusManager() {
		return cersaiMappingBusManager;
	}

	public void setCersaiMappingBusManager(ICersaiMappingBusManager cersaiMappingBusManager) {
		this.cersaiMappingBusManager = cersaiMappingBusManager;
	}

	
	public ICersaiMappingBusManager getStagingCersaiMappingBusManager() {
		return stagingCersaiMappingBusManager;
	}

	public void setStagingCersaiMappingBusManager(ICersaiMappingBusManager stagingCersaiMappingBusManager) {
		this.stagingCersaiMappingBusManager = stagingCersaiMappingBusManager;
	}

	
	protected ICersaiMappingTrxValue prepareTrxValue(ICersaiMappingTrxValue cersaiMappingTrxValue)throws TrxOperationException {
        if (cersaiMappingTrxValue != null) {
        	ICersaiMapping actual = cersaiMappingTrxValue.getCersaiMapping();
        	ICersaiMapping staging = cersaiMappingTrxValue.getStagingCersaiMapping();
            if (actual != null) {
            	cersaiMappingTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	cersaiMappingTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	cersaiMappingTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	cersaiMappingTrxValue.setStagingReferenceID(null);
            }
            return cersaiMappingTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CersaiMapping is null");
        }
    }
	
	/**
	 * 
	 * @param CersaiMappingTrxValue
	 * @return ICersaiMappingTrxValue
	 * @throws TrxOperationException
	 */

    protected ICersaiMappingTrxValue updateCersaiMappingTrx(ICersaiMappingTrxValue cersaiMappingTrxValue) throws TrxOperationException {
        try {
        	cersaiMappingTrxValue = prepareTrxValue(cersaiMappingTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(cersaiMappingTrxValue);
            OBCersaiMappingTrxValue newValue = new OBCersaiMappingTrxValue(tempValue);
            newValue.setCersaiMapping(cersaiMappingTrxValue.getCersaiMapping());
            newValue.setStagingCersaiMapping(cersaiMappingTrxValue.getStagingCersaiMapping());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param CersaiMappingTrxValue
     * @return ICersaiMappingTrxValue
     * @throws TrxOperationException
     */

    protected ICersaiMappingTrxValue createStagingCersaiMapping(ICersaiMappingTrxValue cersaiMappingTrxValue) throws TrxOperationException {
        try {
        	ICersaiMapping cersaiMapping = getStagingCersaiMappingBusManager().createCersaiMapping(cersaiMappingTrxValue.getStagingCersaiMapping());
        	cersaiMappingTrxValue.setStagingCersaiMapping(cersaiMapping);
        	cersaiMappingTrxValue.setStagingReferenceID(String.valueOf(cersaiMapping.getId()));
            return cersaiMappingTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return ICersaiMappingTrxValue
     * @throws TrxOperationException
     */

    protected ICersaiMappingTrxValue getCersaiMappingTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICersaiMappingTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCersaiMappingTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ICersaiMappingTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
