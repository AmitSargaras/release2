package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * 
 * @author sandiip.shinde
 * @since 04-06-2011
 */

public abstract class AbstractDiscrepencyTrxOperation  extends CMSTrxOperation implements ITrxRouteOperation {
	
	private com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager discrepencyBusManager;

	private com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager stagingDiscrepencyBusManager;
	
	public com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager getDiscrepencyBusManager() {
		return discrepencyBusManager;
	}
	public void setDiscrepencyBusManager(
			com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager discrepencyBusManager) {
		this.discrepencyBusManager = discrepencyBusManager;
	}
	public com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager getStagingDiscrepencyBusManager() {
		return stagingDiscrepencyBusManager;
	}
	public void setStagingDiscrepencyBusManager(
			com.integrosys.cms.app.discrepency.bus.IDiscrepencyBusManager stagingDiscrepencyBusManager) {
		this.stagingDiscrepencyBusManager = stagingDiscrepencyBusManager;
	}
	protected IDiscrepencyTrxValue prepareTrxValue(IDiscrepencyTrxValue discrepency) {
        if (discrepency != null) {
            IDiscrepency actual = discrepency.getActualDiscrepency();
            IDiscrepency staging = discrepency.getStagingDiscrepency();
            if (actual != null) {
            	discrepency.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	discrepency.setReferenceID(null);
            }
            if (staging != null) {
            	discrepency.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	discrepency.setStagingReferenceID(null);
            }
            return discrepency;
        }else{
        	throw new  NoSuchDiscrepencyException("ERROR-- Discrepency is null");
        }
    }
	    /**
	     * 
	     * @param discrepencyTrxValue
	     * @return IDiscrepencyTrxValue
	     * @throws TrxOperationException
	     */

    protected IDiscrepencyTrxValue updateDiscrepencyTrx(IDiscrepencyTrxValue discrepencyTrxValue) throws TrxOperationException {
        try {
        	discrepencyTrxValue = prepareTrxValue(discrepencyTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(discrepencyTrxValue);
            OBDiscrepencyTrxValue newValue = new OBDiscrepencyTrxValue(tempValue);
            newValue.setActualDiscrepency(discrepencyTrxValue.getActualDiscrepency());
            newValue.setStagingDiscrepency(discrepencyTrxValue.getStagingDiscrepency());
            return newValue;
        }
        catch (Exception ex) {
            throw new NoSuchDiscrepencyException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param IDiscrepencyTrxValue
     * @return IDiscrepencyTrxValue
     * @throws TrxOperationException
     */
    protected IDiscrepencyTrxValue createStagingDiscrepency(IDiscrepencyTrxValue discrepencyTrxValue) throws TrxOperationException {
        try {
            IDiscrepency discrepency = getStagingDiscrepencyBusManager().createDiscrepency(discrepencyTrxValue.getStagingDiscrepency());
            discrepencyTrxValue.setStagingDiscrepency(discrepency);
            discrepencyTrxValue.setStagingReferenceID(String.valueOf(discrepency.getId()));
            return discrepencyTrxValue;
        }
        catch (Exception ex) {
        	ex.printStackTrace();
            throw new NoSuchDiscrepencyException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IDiscrepencyTrxValue
     * @throws TrxOperationException
     */

    protected IDiscrepencyTrxValue getDiscrepencyTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IDiscrepencyTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new NoSuchDiscrepencyException("The ITrxValue is not of type OBCDiscrepencyTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IDiscrepencyTrxValue
     * @throws TrxOperationException
     */

    protected IDiscrepency mergeDiscrepency(IDiscrepency anOriginal, IDiscrepency aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IDiscrepencyTrxValue
     */

    protected ITrxResult prepareResult(IDiscrepencyTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IDiscrepencyTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IDiscrepencyTrxValue createTransaction(IDiscrepencyTrxValue value) throws TrxOperationException {
		OBDiscrepencyTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBDiscrepencyTrxValue(tempValue);
			newValue.setActualDiscrepency(value.getActualDiscrepency());
			newValue.setStagingDiscrepency(value.getStagingDiscrepency());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IDiscrepencyTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IDiscrepencyTrxValue updateTransaction(IDiscrepencyTrxValue value) throws TrxOperationException {
		OBDiscrepencyTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBDiscrepencyTrxValue(tempValue);
			newValue.setActualDiscrepency(value.getActualDiscrepency());
			newValue.setStagingDiscrepency(value.getStagingDiscrepency());
		}	
		return newValue;
	}
}
