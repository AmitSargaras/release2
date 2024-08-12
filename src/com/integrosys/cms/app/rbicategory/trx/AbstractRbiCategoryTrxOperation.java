package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategoryBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author Govind.Sahu
 * Abstract Rbi Category 
 */

public abstract class AbstractRbiCategoryTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IRbiCategoryBusManager rbiCategoryBusManager;

    private IRbiCategoryBusManager stagingRbiCategoryBusManager;


	protected IRbiCategoryTrxValue prepareTrxValue(IRbiCategoryTrxValue rbiCategoryTrxValue)throws TrxOperationException {
        if (rbiCategoryTrxValue != null) {
        	IRbiCategory actual = rbiCategoryTrxValue.getRbiCategory();
        	IRbiCategory staging = rbiCategoryTrxValue.getStagingRbiCategory();
            if (actual != null) {
            	rbiCategoryTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	rbiCategoryTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	rbiCategoryTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	rbiCategoryTrxValue.setStagingReferenceID(null);
            }
            return rbiCategoryTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- rbi Category TrxValue is null");
        }
    }
	/**
	 * 
	 * @param rbiCategoryTrxValue
	 * @return IRbiCategoryTrxValue
	 * @throws TrxOperationException
	 */

    protected IRbiCategoryTrxValue updateRbiCategoryTrx(IRbiCategoryTrxValue rbiCategoryTrxValue) throws TrxOperationException {
        try {
        	rbiCategoryTrxValue = prepareTrxValue(rbiCategoryTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(rbiCategoryTrxValue);
            OBRbiCategoryTrxValue newValue = new OBRbiCategoryTrxValue(tempValue);
            newValue.setRbiCategory(rbiCategoryTrxValue.getRbiCategory());
            newValue.setStagingRbiCategory(rbiCategoryTrxValue.getStagingRbiCategory());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param rbiCategoryTrxValue
     * @return IRbiCategoryTrxValue
     * @throws TrxOperationException
     */

    protected IRbiCategoryTrxValue createStagingRbiCategory(IRbiCategoryTrxValue rbiCategoryTrxValue) throws TrxOperationException {
        try {
        	IRbiCategory rbiCategory = getStagingRbiCategoryBusManager().createRbiCategory(rbiCategoryTrxValue.getStagingRbiCategory());
        	rbiCategoryTrxValue.setStagingRbiCategory(rbiCategory);
        	rbiCategoryTrxValue.setStagingReferenceID(String.valueOf(rbiCategory.getId()));
            return rbiCategoryTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IRbiCategoryTrxValue
     * @throws TrxOperationException
     */

    protected IRbiCategoryTrxValue getRbiCategoryTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IRbiCategoryTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCRbiCategoryTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IRbiCategoryTrxValue
     * @throws TrxOperationException
     */

    protected IRbiCategory mergeRbiCategory(IRbiCategory anOriginal, IRbiCategory aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IRbiCategoryTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
	/**
	 * @return the rbiCategoryBusManager
	 */
	public IRbiCategoryBusManager getRbiCategoryBusManager() {
		return rbiCategoryBusManager;
	}
	/**
	 * @param rbiCategoryBusManager the rbiCategoryBusManager to set
	 */
	public void setRbiCategoryBusManager(
			IRbiCategoryBusManager rbiCategoryBusManager) {
		this.rbiCategoryBusManager = rbiCategoryBusManager;
	}
	/**
	 * @return the stagingRbiCategoryBusManager
	 */
	public IRbiCategoryBusManager getStagingRbiCategoryBusManager() {
		return stagingRbiCategoryBusManager;
	}
	/**
	 * @param stagingRbiCategoryBusManager the stagingRbiCategoryBusManager to set
	 */
	public void setStagingRbiCategoryBusManager(
			IRbiCategoryBusManager stagingRbiCategoryBusManager) {
		this.stagingRbiCategoryBusManager = stagingRbiCategoryBusManager;
	}
}
