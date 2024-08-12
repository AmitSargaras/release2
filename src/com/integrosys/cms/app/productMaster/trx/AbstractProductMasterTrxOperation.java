package com.integrosys.cms.app.productMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractProductMasterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IProductMasterBusManager productMasterBusManager;

	private IProductMasterBusManager stagingProductMasterBusManager;
	
	public IProductMasterBusManager getProductMasterBusManager() {
		return productMasterBusManager;
	}

	public void setProductMasterBusManager(IProductMasterBusManager productMasterBusManager) {
		this.productMasterBusManager = productMasterBusManager;
	}

	public IProductMasterBusManager getStagingProductMasterBusManager() {
		return stagingProductMasterBusManager;
	}

	public void setStagingProductMasterBusManager(IProductMasterBusManager stagingProductMasterBusManager) {
		this.stagingProductMasterBusManager = stagingProductMasterBusManager;
	}

	protected IProductMasterTrxValue prepareTrxValue(IProductMasterTrxValue productMasterTrxValue)throws TrxOperationException {
        if (productMasterTrxValue != null) {
        	IProductMaster actual = productMasterTrxValue.getProductMaster();
        	IProductMaster staging = productMasterTrxValue.getStagingProductMaster();
            if (actual != null) {
            	productMasterTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	productMasterTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	productMasterTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	productMasterTrxValue.setStagingReferenceID(null);
            }
            return productMasterTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- ProductMaster is null");
        }
    }
	
	/**
	 * 
	 * @param ProductMasterTrxValue
	 * @return IProductMasterTrxValue
	 * @throws TrxOperationException
	 */

    protected IProductMasterTrxValue updateProductMasterTrx(IProductMasterTrxValue productMasterTrxValue) throws TrxOperationException {
        try {
        	productMasterTrxValue = prepareTrxValue(productMasterTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(productMasterTrxValue);
            OBProductMasterTrxValue newValue = new OBProductMasterTrxValue(tempValue);
            newValue.setProductMaster(productMasterTrxValue.getProductMaster());
            newValue.setStagingProductMaster(productMasterTrxValue.getStagingProductMaster());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param ProductMasterTrxValue
     * @return IProductMasterTrxValue
     * @throws TrxOperationException
     */

    protected IProductMasterTrxValue createStagingProductMaster(IProductMasterTrxValue productMasterTrxValue) throws TrxOperationException {
        try {
        	IProductMaster productMaster = getStagingProductMasterBusManager().createProductMaster(productMasterTrxValue.getStagingProductMaster());
        	productMasterTrxValue.setStagingProductMaster(productMaster);
        	productMasterTrxValue.setStagingReferenceID(String.valueOf(productMaster.getId()));
            return productMasterTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IProductMasterTrxValue
     * @throws TrxOperationException
     */

    protected IProductMasterTrxValue getProductMasterTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IProductMasterTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBProductMasterTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IProductMasterTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
