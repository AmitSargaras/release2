package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationBusManager;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;
import com.integrosys.cms.app.leiDateValidation.trx.OBLeiDateValidationTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractLeiDateValidationTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private ILeiDateValidationBusManager leiDateValidationBusManager;

	private ILeiDateValidationBusManager stagingLeiDateValidationBusManager;
	
	public ILeiDateValidationBusManager getLeiDateValidationBusManager() {
		return leiDateValidationBusManager;
	}

	public void setLeiDateValidationBusManager(ILeiDateValidationBusManager leiDateValidationBusManager) {
		this.leiDateValidationBusManager = leiDateValidationBusManager;
	}

	public ILeiDateValidationBusManager getStagingLeiDateValidationBusManager() {
		return stagingLeiDateValidationBusManager;
	}

	public void setStagingLeiDateValidationBusManager(ILeiDateValidationBusManager stagingLeiDateValidationBusManager) {
		this.stagingLeiDateValidationBusManager = stagingLeiDateValidationBusManager;
	}

	protected ILeiDateValidationTrxValue prepareTrxValue(ILeiDateValidationTrxValue leiDateValidationTrxValue)throws TrxOperationException {
        if (leiDateValidationTrxValue != null) {
        	ILeiDateValidation actual = leiDateValidationTrxValue.getLeiDateValidation();
        	ILeiDateValidation staging = leiDateValidationTrxValue.getStagingLeiDateValidation();
            if (actual != null) {
            	leiDateValidationTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	leiDateValidationTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	leiDateValidationTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	leiDateValidationTrxValue.setStagingReferenceID(null);
            }
            return leiDateValidationTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- LeiDateValidation is null");
        }
    }
	
	/**
	 * 
	 * @param LeiDateValidationTrxValue
	 * @return ILeiDateValidationTrxValue
	 * @throws TrxOperationException
	 */

    protected ILeiDateValidationTrxValue updateLeiDateValidationTrx(ILeiDateValidationTrxValue leiDateValidationTrxValue) throws TrxOperationException {
        try {
        	leiDateValidationTrxValue = prepareTrxValue(leiDateValidationTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(leiDateValidationTrxValue);
            OBLeiDateValidationTrxValue newValue = new OBLeiDateValidationTrxValue(tempValue);
            newValue.setLeiDateValidation(leiDateValidationTrxValue.getLeiDateValidation());
            newValue.setStagingLeiDateValidation(leiDateValidationTrxValue.getStagingLeiDateValidation());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param LeiDateValidationTrxValue
     * @return ILeiDateValidationTrxValue
     * @throws TrxOperationException
     */

    protected ILeiDateValidationTrxValue createStagingLeiDateValidation(ILeiDateValidationTrxValue leiDateValidationTrxValue) throws TrxOperationException {
        try {
        	ILeiDateValidation leiDateValidation = getStagingLeiDateValidationBusManager().createLeiDateValidation(leiDateValidationTrxValue.getStagingLeiDateValidation());
        	leiDateValidationTrxValue.setStagingLeiDateValidation(leiDateValidation);
        	leiDateValidationTrxValue.setStagingReferenceID(String.valueOf(leiDateValidation.getId()));
            return leiDateValidationTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return ILeiDateValidationTrxValue
     * @throws TrxOperationException
     */

    protected ILeiDateValidationTrxValue getLeiDateValidationTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ILeiDateValidationTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBLeiDateValidationTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ILeiDateValidationTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
