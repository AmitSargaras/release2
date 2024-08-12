package com.integrosys.cms.app.insurancecoveragedtls.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.IInsuranceCoverageDtlsBusManager;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.insurancecoveragedtls.IInsuranceCoverageDtls;

/**
 * @author dattatray.thorat
 * Abstract Insurance Coverage Details Operation 
 */

public abstract class AbstractInsuranceCoverageDtlsTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IInsuranceCoverageDtlsBusManager insuranceCoverageDtlsBusManager;

    private IInsuranceCoverageDtlsBusManager stagingInsuranceCoverageDtlsBusManager;

	/**
	 * @return the insuranceCoverageDtlsBusManager
	 */
	public IInsuranceCoverageDtlsBusManager getInsuranceCoverageDtlsBusManager() {
		return insuranceCoverageDtlsBusManager;
	}
	/**
	 * @param insuranceCoverageDtlsBusManager the insuranceCoverageDtlsBusManager to set
	 */
	public void setInsuranceCoverageDtlsBusManager(
			IInsuranceCoverageDtlsBusManager insuranceCoverageDtlsBusManager) {
		this.insuranceCoverageDtlsBusManager = insuranceCoverageDtlsBusManager;
	}
	/**
	 * @return the stagingInsuranceCoverageDtlsBusManager
	 */
	public IInsuranceCoverageDtlsBusManager getStagingInsuranceCoverageDtlsBusManager() {
		return stagingInsuranceCoverageDtlsBusManager;
	}
	/**
	 * @param stagingInsuranceCoverageDtlsBusManager the stagingInsuranceCoverageDtlsBusManager to set
	 */
	public void setStagingInsuranceCoverageDtlsBusManager(
			IInsuranceCoverageDtlsBusManager stagingInsuranceCoverageDtlsBusManager) {
		this.stagingInsuranceCoverageDtlsBusManager = stagingInsuranceCoverageDtlsBusManager;
	}
	/**
     * 
     * @param IInsuranceCoverageDtlsTrxValue
     * @return IIInsuranceCoverageDtlsTrxValue
     */

    protected IInsuranceCoverageDtlsTrxValue prepareTrxValue(IInsuranceCoverageDtlsTrxValue insuranceCoverageTrxValue) {
        if (insuranceCoverageTrxValue != null) {
            IInsuranceCoverageDtls actual = insuranceCoverageTrxValue.getInsuranceCoverageDtls();
            IInsuranceCoverageDtls staging = insuranceCoverageTrxValue.getStagingInsuranceCoverageDtls();
            if (actual != null) {
            	insuranceCoverageTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	insuranceCoverageTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	insuranceCoverageTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	insuranceCoverageTrxValue.setStagingReferenceID(null);
            }
            return insuranceCoverageTrxValue;
        }else{
        	throw new  InsuranceCoverageDtlsException("ERROR-- Insurance Coverage is null");
        }
    }
    /**
     * 
     * @param insuranceCoverageTrxValue
     * @return IInsuranceCoverageDtlsTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverageDtlsTrxValue updateInsuranceCoverageDtlsTrx(IInsuranceCoverageDtlsTrxValue insuranceCoverageTrxValue) throws TrxOperationException {
        try {
        	insuranceCoverageTrxValue = prepareTrxValue(insuranceCoverageTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(insuranceCoverageTrxValue);
            OBInsuranceCoverageDtlsTrxValue newValue = new OBInsuranceCoverageDtlsTrxValue(tempValue);
            newValue.setInsuranceCoverageDtls(insuranceCoverageTrxValue.getInsuranceCoverageDtls());
            newValue.setStagingInsuranceCoverageDtls(insuranceCoverageTrxValue.getStagingInsuranceCoverageDtls());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new InsuranceCoverageDtlsException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param RelationshipTrxTrxValue
     * @return IInsuranceCoverageDtlsTrxValue
     * @throws TrxOperationException
     */
    protected IInsuranceCoverageDtlsTrxValue createStagingInsuranceCoverageDtls(IInsuranceCoverageDtlsTrxValue insuranceCoverageTrxValue) throws TrxOperationException {
        try {
            IInsuranceCoverageDtls insuranceCoverage = getStagingInsuranceCoverageDtlsBusManager().createInsuranceCoverageDtls(insuranceCoverageTrxValue.getStagingInsuranceCoverageDtls());
            insuranceCoverageTrxValue.setStagingInsuranceCoverageDtls(insuranceCoverage);
            insuranceCoverageTrxValue.setStagingReferenceID(String.valueOf(insuranceCoverage.getId()));
            return insuranceCoverageTrxValue;
        }
        catch (Exception ex) {
            throw new InsuranceCoverageDtlsException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IInsuranceCoverageDtlsTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverageDtlsTrxValue getInsuranceCoverageDtlsTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IInsuranceCoverageDtlsTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new InsuranceCoverageDtlsException("The ITrxValue is not of type OBCinsuranceCoverageTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IInsuranceCoverageDtlsTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverageDtls mergeInsuranceCoverageDtls(IInsuranceCoverageDtls anOriginal, IInsuranceCoverageDtls aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IInsuranceCoverageDtlsTrxValue
     */

    protected ITrxResult prepareResult(IInsuranceCoverageDtlsTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IInsuranceCoverageDtlsTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IInsuranceCoverageDtlsTrxValue createTransaction(IInsuranceCoverageDtlsTrxValue value) throws TrxOperationException {
		OBInsuranceCoverageDtlsTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBInsuranceCoverageDtlsTrxValue(tempValue);
			newValue.setInsuranceCoverageDtls(value.getInsuranceCoverageDtls());
			newValue.setStagingInsuranceCoverageDtls(value.getStagingInsuranceCoverageDtls());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IInsuranceCoverageDtlsTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IInsuranceCoverageDtlsTrxValue updateTransaction(IInsuranceCoverageDtlsTrxValue value) throws TrxOperationException {
		OBInsuranceCoverageDtlsTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBInsuranceCoverageDtlsTrxValue(tempValue);
			newValue.setInsuranceCoverageDtls(value.getInsuranceCoverageDtls());
			newValue.setStagingInsuranceCoverageDtls(value.getStagingInsuranceCoverageDtls());
		}	
		return newValue;

	}
}
