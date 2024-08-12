package com.integrosys.cms.app.udf.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfBusManager;

public abstract class AbstractUdfTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IUdfBusManager udfBusManager;

	private IUdfBusManager stagingUdfBusManager;
	
	public IUdfBusManager getUdfBusManager() {
		return udfBusManager;
	}

	public void setUdfBusManager(IUdfBusManager udfBusManager) {
		this.udfBusManager = udfBusManager;
	}


	public IUdfBusManager getStagingUdfBusManager() {
		return stagingUdfBusManager;
	}

	public void setStagingUdfBusManager(IUdfBusManager stagingUdfBusManager) {
		this.stagingUdfBusManager = stagingUdfBusManager;
	}

	protected IUdfTrxValue prepareTrxValue(IUdfTrxValue udfTrxValue)throws TrxOperationException {
        if (udfTrxValue != null) {
        	IUdf actual = udfTrxValue.getUdf();
        	IUdf staging = udfTrxValue.getStagingUdf();
            if (actual != null) {
            	udfTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	udfTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	udfTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	udfTrxValue.setStagingReferenceID(null);
            }
            return udfTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- udf is null");
        }
    }
	
	/**
	 * 
	 * @param udfTrxValue
	 * @return IUdfTrxValue
	 * @throws TrxOperationException
	 */

    protected IUdfTrxValue updateUdfTrx(IUdfTrxValue udfTrxValue) throws TrxOperationException {
        try {
        	udfTrxValue = prepareTrxValue(udfTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(udfTrxValue);
            OBUdfTrxValue newValue = new OBUdfTrxValue(tempValue);
            newValue.setUdf(udfTrxValue.getUdf());
            newValue.setStagingUdf(udfTrxValue.getStagingUdf());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param udfTrxValue
     * @return IUdfTrxValue
     * @throws TrxOperationException
     */

    protected IUdfTrxValue createStagingUdf(IUdfTrxValue udfTrxValue) throws TrxOperationException {
        try {
        	IUdf udf = getStagingUdfBusManager().createUdf(udfTrxValue.getStagingUdf());
        	udfTrxValue.setStagingUdf(udf);
        	udfTrxValue.setStagingReferenceID(String.valueOf(udf.getId()));
            return udfTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IUdfTrxValue
     * @throws TrxOperationException
     */

    protected IUdfTrxValue getUdfTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IUdfTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBUdfTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IUdfTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }

	
}
