package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.insurancecoverage.bus.IInsuranceCoverageBusManager;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;

/**
 * @author dattatray.thorat
 * Abstract Insurance Coverage Operation 
 */

public abstract class AbstractInsuranceCoverageTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IInsuranceCoverageBusManager insuranceCoverageBusManager;

    private IInsuranceCoverageBusManager stagingInsuranceCoverageBusManager;
    
    private IInsuranceCoverageBusManager stagingInsuranceCoverageFileMapperIdBusManager;
    
    private IInsuranceCoverageBusManager insuranceCoverageFileMapperIdBusManager;

	/**
	 * @return the stagingInsuranceCoverageFileMapperIdBusManager
	 */
	public IInsuranceCoverageBusManager getStagingInsuranceCoverageFileMapperIdBusManager() {
		return stagingInsuranceCoverageFileMapperIdBusManager;
	}
	/**
	 * @param stagingInsuranceCoverageFileMapperIdBusManager the stagingInsuranceCoverageFileMapperIdBusManager to set
	 */
	public void setStagingInsuranceCoverageFileMapperIdBusManager(
			IInsuranceCoverageBusManager stagingInsuranceCoverageFileMapperIdBusManager) {
		this.stagingInsuranceCoverageFileMapperIdBusManager = stagingInsuranceCoverageFileMapperIdBusManager;
	}
	/**
	 * @return the insuranceCoverageFileMapperIdBusManager
	 */
	public IInsuranceCoverageBusManager getInsuranceCoverageFileMapperIdBusManager() {
		return insuranceCoverageFileMapperIdBusManager;
	}
	/**
	 * @param insuranceCoverageFileMapperIdBusManager the insuranceCoverageFileMapperIdBusManager to set
	 */
	public void setInsuranceCoverageFileMapperIdBusManager(
			IInsuranceCoverageBusManager insuranceCoverageFileMapperIdBusManager) {
		this.insuranceCoverageFileMapperIdBusManager = insuranceCoverageFileMapperIdBusManager;
	}
	/**
	 * @return the insuranceCoverageBusManager
	 */
	public IInsuranceCoverageBusManager getInsuranceCoverageBusManager() {
		return insuranceCoverageBusManager;
	}
	/**
	 * @param insuranceCoverageBusManager the insuranceCoverageBusManager to set
	 */
	public void setInsuranceCoverageBusManager(
			IInsuranceCoverageBusManager insuranceCoverageBusManager) {
		this.insuranceCoverageBusManager = insuranceCoverageBusManager;
	}
	/**
	 * @return the stagingInsuranceCoverageBusManager
	 */
	public IInsuranceCoverageBusManager getStagingInsuranceCoverageBusManager() {
		return stagingInsuranceCoverageBusManager;
	}
	/**
	 * @param stagingInsuranceCoverageBusManager the stagingInsuranceCoverageBusManager to set
	 */
	public void setStagingInsuranceCoverageBusManager(
			IInsuranceCoverageBusManager stagingInsuranceCoverageBusManager) {
		this.stagingInsuranceCoverageBusManager = stagingInsuranceCoverageBusManager;
	}
	/**
     * 
     * @param IInsuranceCoverageDtlsTrxValue
     * @return IIInsuranceCoverageTrxValue
     */

    protected IInsuranceCoverageTrxValue prepareTrxValue(IInsuranceCoverageTrxValue insuranceCoverageTrxValue) {
        if (insuranceCoverageTrxValue != null) {
            IInsuranceCoverage actual = insuranceCoverageTrxValue.getInsuranceCoverage();
            IInsuranceCoverage staging = insuranceCoverageTrxValue.getStagingInsuranceCoverage();
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
        	throw new  InsuranceCoverageException("ERROR-- Insurance Coverage is null");
        }
    }
    /**
     * 
     * @param insuranceCoverageTrxValue
     * @return IInsuranceCoverageTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverageTrxValue updateInsuranceCoverageTrx(IInsuranceCoverageTrxValue insuranceCoverageTrxValue) throws TrxOperationException {
        try {
        	insuranceCoverageTrxValue = prepareTrxValue(insuranceCoverageTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(insuranceCoverageTrxValue);
            OBInsuranceCoverageTrxValue newValue = new OBInsuranceCoverageTrxValue(tempValue);
            newValue.setInsuranceCoverage(insuranceCoverageTrxValue.getInsuranceCoverage());
            newValue.setStagingInsuranceCoverage(insuranceCoverageTrxValue.getStagingInsuranceCoverage());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new InsuranceCoverageException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param RelationshipTrxTrxValue
     * @return IInsuranceCoverageTrxValue
     * @throws TrxOperationException
     */
    protected IInsuranceCoverageTrxValue createStagingInsuranceCoverage(IInsuranceCoverageTrxValue insuranceCoverageTrxValue) throws TrxOperationException {
        try {
            IInsuranceCoverage insuranceCoverage = getStagingInsuranceCoverageBusManager().createInsuranceCoverage(insuranceCoverageTrxValue.getStagingInsuranceCoverage());
            insuranceCoverageTrxValue.setStagingInsuranceCoverage(insuranceCoverage);
            insuranceCoverageTrxValue.setStagingReferenceID(String.valueOf(insuranceCoverage.getId()));
            return insuranceCoverageTrxValue;
        }
        catch (Exception ex) {
            throw new InsuranceCoverageException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IInsuranceCoverageTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverageTrxValue getInsuranceCoverageTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IInsuranceCoverageTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new InsuranceCoverageException("The ITrxValue is not of type OBCinsuranceCoverageTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IInsuranceCoverageTrxValue
     * @throws TrxOperationException
     */

    protected IInsuranceCoverage mergeInsuranceCoverage(IInsuranceCoverage anOriginal, IInsuranceCoverage aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IInsuranceCoverageTrxValue
     */

    protected ITrxResult prepareResult(IInsuranceCoverageTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IInsuranceCoverageTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IInsuranceCoverageTrxValue createTransaction(IInsuranceCoverageTrxValue value) throws TrxOperationException {
		OBInsuranceCoverageTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBInsuranceCoverageTrxValue(tempValue);
			newValue.setInsuranceCoverage(value.getInsuranceCoverage());
			newValue.setStagingInsuranceCoverage(value.getStagingInsuranceCoverage());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IInsuranceCoverageTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IInsuranceCoverageTrxValue updateTransaction(IInsuranceCoverageTrxValue value) throws TrxOperationException {
		OBInsuranceCoverageTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBInsuranceCoverageTrxValue(tempValue);
			newValue.setInsuranceCoverage(value.getInsuranceCoverage());
			newValue.setStagingInsuranceCoverage(value.getStagingInsuranceCoverage());
		}	
		return newValue;

	}
	
//------------------------------------File Insert---------------------------------------------
    
    protected IInsuranceCoverageTrxValue prepareInsertTrxValue(IInsuranceCoverageTrxValue relationshipMgrTrxValue)throws TrxOperationException {
        if (relationshipMgrTrxValue != null) {
            IFileMapperId actual = relationshipMgrTrxValue.getFileMapperID();
            IFileMapperId staging = relationshipMgrTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	relationshipMgrTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	relationshipMgrTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	relationshipMgrTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	relationshipMgrTrxValue.setStagingReferenceID(null);
            }
            return relationshipMgrTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- InsuranceCoverage is null");
        }
    }
	
    
    
    protected IInsuranceCoverageTrxValue updateMasterInsertTrx(IInsuranceCoverageTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
        	relationshipMgrTrxValue = prepareInsertTrxValue(relationshipMgrTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(relationshipMgrTrxValue);
            OBInsuranceCoverageTrxValue newValue = new OBInsuranceCoverageTrxValue(tempValue);
            newValue.setFileMapperID(relationshipMgrTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(relationshipMgrTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IInsuranceCoverageTrxValue createStagingFileId(IInsuranceCoverageTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingInsuranceCoverageFileMapperIdBusManager().createFileId(relationshipMgrTrxValue.getStagingFileMapperID());
        	relationshipMgrTrxValue.setStagingFileMapperID(fileMapperID);
        	relationshipMgrTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return relationshipMgrTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IInsuranceCoverageTrxValue insertActualInsuranceCoverage(IInsuranceCoverageTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
            IInsuranceCoverage relationshipMgr = getStagingInsuranceCoverageFileMapperIdBusManager().insertInsuranceCoverage(relationshipMgrTrxValue.getStagingInsuranceCoverage());
            relationshipMgrTrxValue.setInsuranceCoverage(relationshipMgr);
            relationshipMgrTrxValue.setReferenceID(String.valueOf(relationshipMgr.getId()));
            return relationshipMgrTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
