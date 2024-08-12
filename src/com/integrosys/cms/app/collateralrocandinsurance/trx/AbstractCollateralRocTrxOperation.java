package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRocBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractCollateralRocTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private ICollateralRocBusManager collateralRocBusManager;

	private ICollateralRocBusManager stagingCollateralRocBusManager;

	public ICollateralRocBusManager getCollateralRocBusManager() {
		return collateralRocBusManager;
	}

	public void setCollateralRocBusManager(ICollateralRocBusManager collateralRocBusManager) {
		this.collateralRocBusManager = collateralRocBusManager;
	}

	public ICollateralRocBusManager getStagingCollateralRocBusManager() {
		return stagingCollateralRocBusManager;
	}

	public void setStagingCollateralRocBusManager(ICollateralRocBusManager stagingCollateralRocBusManager) {
		this.stagingCollateralRocBusManager = stagingCollateralRocBusManager;
	}
	
	protected ICollateralRocTrxValue prepareTrxValue(ICollateralRocTrxValue collateralRocTrxValue)throws TrxOperationException {
        if (collateralRocTrxValue != null) {
        	ICollateralRoc actual = collateralRocTrxValue.getCollateralRoc();
        	ICollateralRoc staging = collateralRocTrxValue.getStagingCollateralRoc();
            if (actual != null) {
            	collateralRocTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	collateralRocTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	collateralRocTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	collateralRocTrxValue.setStagingReferenceID(null);
            }
            return collateralRocTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- Collateral Roc is null");
        }
    }
	/**
	 * 
	 * @param CollateralRocTrxValue
	 * @return ICollateralRocTrxValue
	 * @throws TrxOperationException
	 */

    protected ICollateralRocTrxValue updateCollateralRocTrx(ICollateralRocTrxValue collateralRocTrxValue) throws TrxOperationException {
        try {
        	collateralRocTrxValue = prepareTrxValue(collateralRocTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(collateralRocTrxValue);
            OBCollateralRocTrxValue newValue = new OBCollateralRocTrxValue(tempValue);
            newValue.setCollateralRoc(collateralRocTrxValue.getCollateralRoc());
            newValue.setStagingCollateralRoc(collateralRocTrxValue.getStagingCollateralRoc());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param CollateralRocTrxValue
     * @return ICollateralRocTrxValue
     * @throws TrxOperationException
     */

    protected ICollateralRocTrxValue createStagingCollateralRoc(ICollateralRocTrxValue collateralRocTrxValue) throws TrxOperationException {
        try {
        	ICollateralRoc collateralRoc = getStagingCollateralRocBusManager().createCollateralRoc(collateralRocTrxValue.getStagingCollateralRoc());
            collateralRocTrxValue.setStagingCollateralRoc(collateralRoc);
            collateralRocTrxValue.setStagingReferenceID(String.valueOf(collateralRoc.getId()));
            return collateralRocTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return ICollateralRocTrxValue
     * @throws TrxOperationException
     */

    protected ICollateralRocTrxValue getCollateralRocTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICollateralRocTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCollateralRocTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ICollateralRocTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
