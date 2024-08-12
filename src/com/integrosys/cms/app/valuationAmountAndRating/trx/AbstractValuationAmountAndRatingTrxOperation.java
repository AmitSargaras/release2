package com.integrosys.cms.app.valuationAmountAndRating.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRating;
import com.integrosys.cms.app.valuationAmountAndRating.bus.IValuationAmountAndRatingBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractValuationAmountAndRatingTrxOperation extends CMSTrxOperation implements ITrxRouteOperation{

	private IValuationAmountAndRatingBusManager valuationAmountAndRatingBusManager;

	private IValuationAmountAndRatingBusManager stagingValuationAmountAndRatingBusManager;
	
	public IValuationAmountAndRatingBusManager getValuationAmountAndRatingBusManager() {
		return valuationAmountAndRatingBusManager;
	}

	public void setValuationAmountAndRatingBusManager(IValuationAmountAndRatingBusManager valuationAmountAndRatingBusManager) {
		this.valuationAmountAndRatingBusManager = valuationAmountAndRatingBusManager;
	}

	public IValuationAmountAndRatingBusManager getStagingValuationAmountAndRatingBusManager() {
		return stagingValuationAmountAndRatingBusManager;
	}

	public void setStagingValuationAmountAndRatingBusManager(IValuationAmountAndRatingBusManager stagingValuationAmountAndRatingBusManager) {
		this.stagingValuationAmountAndRatingBusManager = stagingValuationAmountAndRatingBusManager;
	}

	protected IValuationAmountAndRatingTrxValue prepareTrxValue(IValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue)throws TrxOperationException {
        if (valuationAmountAndRatingTrxValue != null) {
        	IValuationAmountAndRating actual = valuationAmountAndRatingTrxValue.getValuationAmountAndRating();
        	IValuationAmountAndRating staging = valuationAmountAndRatingTrxValue.getStagingValuationAmountAndRating();
            if (actual != null) {
            	valuationAmountAndRatingTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	valuationAmountAndRatingTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	valuationAmountAndRatingTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	valuationAmountAndRatingTrxValue.setStagingReferenceID(null);
            }
            return valuationAmountAndRatingTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- ValuationAmountAndRating is null");
        }
    }
	
	/**
	 * 
	 * @param ValuationAmountAndRatingTrxValue
	 * @return IValuationAmountAndRatingTrxValue
	 * @throws TrxOperationException
	 */

    protected IValuationAmountAndRatingTrxValue updateValuationAmountAndRatingTrx(IValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue) throws TrxOperationException {
        try {
        	valuationAmountAndRatingTrxValue = prepareTrxValue(valuationAmountAndRatingTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(valuationAmountAndRatingTrxValue);
            OBValuationAmountAndRatingTrxValue newValue = new OBValuationAmountAndRatingTrxValue(tempValue);
            newValue.setValuationAmountAndRating(valuationAmountAndRatingTrxValue.getValuationAmountAndRating());
            newValue.setStagingValuationAmountAndRating(valuationAmountAndRatingTrxValue.getStagingValuationAmountAndRating());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param ValuationAmountAndRatingTrxValue
     * @return IValuationAmountAndRatingTrxValue
     * @throws TrxOperationException
     */

    protected IValuationAmountAndRatingTrxValue createStagingValuationAmountAndRating(IValuationAmountAndRatingTrxValue valuationAmountAndRatingTrxValue) throws TrxOperationException {
        try {
        	IValuationAmountAndRating valuationAmountAndRating = getStagingValuationAmountAndRatingBusManager().createValuationAmountAndRating(valuationAmountAndRatingTrxValue.getStagingValuationAmountAndRating());
        	valuationAmountAndRatingTrxValue.setStagingValuationAmountAndRating(valuationAmountAndRating);
        	valuationAmountAndRatingTrxValue.setStagingReferenceID(String.valueOf(valuationAmountAndRating.getId()));
            return valuationAmountAndRatingTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param anITrxValue
     * @return IValuationAmountAndRatingTrxValue
     * @throws TrxOperationException
     */

    protected IValuationAmountAndRatingTrxValue getValuationAmountAndRatingTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IValuationAmountAndRatingTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBValuationAmountAndRatingTrxValue: " + ex.toString());
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IValuationAmountAndRatingTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	
}
