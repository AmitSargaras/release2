package com.integrosys.cms.app.goodsMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractGoodsMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IGoodsMasterBusManager goodsMasterBusManager;

	private IGoodsMasterBusManager stagingGoodsMasterBusManager;
	
	public IGoodsMasterBusManager getGoodsMasterBusManager() {
		return goodsMasterBusManager;
	}

	public void setGoodsMasterBusManager(IGoodsMasterBusManager goodsMasterBusManager) {
		this.goodsMasterBusManager = goodsMasterBusManager;
	}

	public IGoodsMasterBusManager getStagingGoodsMasterBusManager() {
		return stagingGoodsMasterBusManager;
	}

	public void setStagingGoodsMasterBusManager(IGoodsMasterBusManager stagingGoodsMasterBusManager) {
		this.stagingGoodsMasterBusManager = stagingGoodsMasterBusManager;
	}

	protected IGoodsMasterTrxValue prepareTrxValue(IGoodsMasterTrxValue goodsMasterTrxValue)throws TrxOperationException {
        if (goodsMasterTrxValue != null) {
        	IGoodsMaster actual = goodsMasterTrxValue.getGoodsMaster();
        	IGoodsMaster staging = goodsMasterTrxValue.getStagingGoodsMaster();
            if (actual != null) {
            	goodsMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	goodsMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	goodsMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	goodsMasterTrxValue.setStagingReferenceID(null);
            }
            return goodsMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- GoodsMaster is null");
        }
    }
	
	/**
	 * 
	 * @param GoodsMasterTrxValue
	 * @return IGoodsMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected IGoodsMasterTrxValue updateGoodsMasterTrx(IGoodsMasterTrxValue goodsMasterTrxValue) throws TrxOperationException {
        try {
        	goodsMasterTrxValue = prepareTrxValue(goodsMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(goodsMasterTrxValue);
            OBGoodsMasterTrxValue newValue = new OBGoodsMasterTrxValue(tempValue);
            newValue.setGoodsMaster(goodsMasterTrxValue.getGoodsMaster());
            newValue.setStagingGoodsMaster(goodsMasterTrxValue.getStagingGoodsMaster());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param GoodsMasterTrxValue
     * @return IGoodsMasterTrxValue
     * @throws TrxOperationException
     */

    protected IGoodsMasterTrxValue createStagingGoodsMaster(IGoodsMasterTrxValue goodsMasterTrxValue) throws TrxOperationException {
        try {
        	IGoodsMaster goodsMaster = getStagingGoodsMasterBusManager().createGoodsMaster(goodsMasterTrxValue.getStagingGoodsMaster());
        	goodsMasterTrxValue.setStagingGoodsMaster(goodsMaster);
        	goodsMasterTrxValue.setStagingReferenceID(String.valueOf(goodsMaster.getId()));
            return goodsMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IGoodsMasterTrxValue
     * @throws TrxOperationException
     */

    protected IGoodsMasterTrxValue getGoodsMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IGoodsMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBGoodsMasterTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IGoodsMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
