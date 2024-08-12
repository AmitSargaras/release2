package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract CollateralNewMaster Operation 
 */

public abstract class AbstractCollateralNewMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ICollateralNewMasterBusManager collateralNewMasterBusManager;

    private ICollateralNewMasterBusManager stagingCollateralNewMasterBusManager;

 

    public ICollateralNewMasterBusManager getCollateralNewMasterBusManager() {
		return collateralNewMasterBusManager;
	}

	public void setCollateralNewMasterBusManager(
			ICollateralNewMasterBusManager collateralNewMasterBusManager) {
		this.collateralNewMasterBusManager = collateralNewMasterBusManager;
	}

	public ICollateralNewMasterBusManager getStagingCollateralNewMasterBusManager() {
		return stagingCollateralNewMasterBusManager;
	}

	public void setStagingCollateralNewMasterBusManager(
			ICollateralNewMasterBusManager stagingCollateralNewMasterBusManager) {
		this.stagingCollateralNewMasterBusManager = stagingCollateralNewMasterBusManager;
	}

	protected ICollateralNewMasterTrxValue prepareTrxValue(ICollateralNewMasterTrxValue collateralNewMasterTrxValue)throws TrxOperationException {
        if (collateralNewMasterTrxValue != null) {
            ICollateralNewMaster actual = collateralNewMasterTrxValue.getCollateralNewMaster();
            ICollateralNewMaster staging = collateralNewMasterTrxValue.getStagingCollateralNewMaster();
            if (actual != null) {
            	collateralNewMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	collateralNewMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	collateralNewMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	collateralNewMasterTrxValue.setStagingReferenceID(null);
            }
            return collateralNewMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CollateralNewMaster is null");
        }
    }
	/**
	 * 
	 * @param collateralNewMasterTrxValue
	 * @return ICollateralNewMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected ICollateralNewMasterTrxValue updateCollateralNewMasterTrx(ICollateralNewMasterTrxValue collateralNewMasterTrxValue) throws TrxOperationException {
        try {
        	collateralNewMasterTrxValue = prepareTrxValue(collateralNewMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(collateralNewMasterTrxValue);
            OBCollateralNewMasterTrxValue newValue = new OBCollateralNewMasterTrxValue(tempValue);
            newValue.setCollateralNewMaster(collateralNewMasterTrxValue.getCollateralNewMaster());
            newValue.setStagingCollateralNewMaster(collateralNewMasterTrxValue.getStagingCollateralNewMaster());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param collateralNewMasterTrxValue
     * @return ICollateralNewMasterTrxValue
     * @throws TrxOperationException
     */

    protected ICollateralNewMasterTrxValue createStagingCollateralNewMaster(ICollateralNewMasterTrxValue collateralNewMasterTrxValue) throws TrxOperationException {
        try {
            ICollateralNewMaster collateralNewMaster = getStagingCollateralNewMasterBusManager().createCollateralNewMaster(collateralNewMasterTrxValue.getStagingCollateralNewMaster());
            collateralNewMasterTrxValue.setStagingCollateralNewMaster(collateralNewMaster);
            collateralNewMasterTrxValue.setStagingReferenceID(String.valueOf(collateralNewMaster.getId()));
            return collateralNewMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ICollateralNewMasterTrxValue
     * @throws TrxOperationException
     */

    protected ICollateralNewMasterTrxValue getCollateralNewMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICollateralNewMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCCollateralNewMasterTrxValue: " + ex.toString());
        }
    }
   
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ICollateralNewMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
}
