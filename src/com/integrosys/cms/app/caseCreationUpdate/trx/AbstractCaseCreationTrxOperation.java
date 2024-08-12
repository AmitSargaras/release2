package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreationBusManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract CaseCreation Operation 
 */

public abstract class AbstractCaseCreationTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ICaseCreationBusManager caseCreationBusManager;

    private ICaseCreationBusManager stagingCaseCreationBusManager;

    private ICaseCreationBusManager stagingCaseCreationFileMapperIdBusManager;
    
    private ICaseCreationBusManager caseCreationUpdateFileMapperIdBusManager;

    public ICaseCreationBusManager getCaseCreationBusManager() {
		return caseCreationBusManager;
	}

	public void setCaseCreationBusManager(
			ICaseCreationBusManager caseCreationBusManager) {
		this.caseCreationBusManager = caseCreationBusManager;
	}

	public ICaseCreationBusManager getStagingCaseCreationBusManager() {
		return stagingCaseCreationBusManager;
	}

	public void setStagingCaseCreationBusManager(
			ICaseCreationBusManager stagingCaseCreationBusManager) {
		this.stagingCaseCreationBusManager = stagingCaseCreationBusManager;
	}
	
	public ICaseCreationBusManager getStagingCaseCreationFileMapperIdBusManager() {
		return stagingCaseCreationFileMapperIdBusManager;
	}

	public void setStagingCaseCreationFileMapperIdBusManager(
			ICaseCreationBusManager stagingCaseCreationFileMapperIdBusManager) {
		this.stagingCaseCreationFileMapperIdBusManager = stagingCaseCreationFileMapperIdBusManager;
	}

	public ICaseCreationBusManager getCaseCreationFileMapperIdBusManager() {
		return caseCreationUpdateFileMapperIdBusManager;
	}

	public void setCaseCreationFileMapperIdBusManager(
			ICaseCreationBusManager caseCreationUpdateFileMapperIdBusManager) {
		this.caseCreationUpdateFileMapperIdBusManager = caseCreationUpdateFileMapperIdBusManager;
	}

	protected ICaseCreationTrxValue prepareTrxValue(ICaseCreationTrxValue caseCreationUpdateTrxValue)throws TrxOperationException {
        if (caseCreationUpdateTrxValue != null) {
            ICaseCreation actual = caseCreationUpdateTrxValue.getCaseCreation();
            ICaseCreation staging = caseCreationUpdateTrxValue.getStagingCaseCreation();
            if (actual != null) {
            	caseCreationUpdateTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	caseCreationUpdateTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	caseCreationUpdateTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	caseCreationUpdateTrxValue.setStagingReferenceID(null);
            }
            return caseCreationUpdateTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CaseCreation is null");
        }
    }
	/**
	 * 
	 * @param caseCreationUpdateTrxValue
	 * @return ICaseCreationTrxValue
	 * @throws TrxOperationException
	 */

    protected ICaseCreationTrxValue updateCaseCreationTrx(ICaseCreationTrxValue caseCreationUpdateTrxValue) throws TrxOperationException {
    	if(caseCreationUpdateTrxValue!=null){
    	try {
        	caseCreationUpdateTrxValue = prepareTrxValue(caseCreationUpdateTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(caseCreationUpdateTrxValue);
            OBCaseCreationTrxValue newValue = new OBCaseCreationTrxValue(tempValue);
            newValue.setCaseCreation(caseCreationUpdateTrxValue.getCaseCreation());
            newValue.setStagingCaseCreation(caseCreationUpdateTrxValue.getStagingCaseCreation());
            return newValue;
        }
        
        catch (CaseCreationException ex) {
            throw new CaseCreationException("General Exception: in update caseCreationUpdate  " );
        }
    	}else{
        	throw new CaseCreationException("Error : Error  while preparing result caseCreationUpdate in abstract trx operation");
        }
    }
    /**
     * 
     * @param caseCreationUpdateTrxValue
     * @return ICaseCreationTrxValue
     * @throws TrxOperationException
     */

    protected ICaseCreationTrxValue createStagingCaseCreation(ICaseCreationTrxValue caseCreationUpdateTrxValue) throws TrxOperationException {
    	if(caseCreationUpdateTrxValue!=null){
    	try {
            ICaseCreation caseCreationUpdate = getStagingCaseCreationBusManager().createCaseCreation(caseCreationUpdateTrxValue.getStagingCaseCreation());
            caseCreationUpdateTrxValue.setStagingCaseCreation(caseCreationUpdate);
            caseCreationUpdateTrxValue.setStagingReferenceID(String.valueOf(caseCreationUpdate.getId()));
            return caseCreationUpdateTrxValue;
        }
        catch (CaseCreationException e) {
            throw new CaseCreationException("Error : Error  while creating caseCreationUpdate in abstract trx operation");
        }catch (Exception ex) {
            throw new CaseCreationException("Error : Error  while creating caseCreationUpdate in abstract trx operation");
        }
    	}else{
        	throw new CaseCreationException("Error : Error  while preparing result caseCreationUpdate in abstract trx operation");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ICaseCreationTrxValue
     * @throws TrxOperationException
     */

    protected ICaseCreationTrxValue getCaseCreationTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (ICaseCreationTrxValue) anITrxValue;
        }
        catch (CaseCreationException e) {
            throw new CaseCreationException("The ITrxValue is not of type OBCCaseCreationTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new CaseCreationException("The ITrxValue is not of type OBCCaseCreationTrxValue: " + ex.toString());
        }
    	}else{
        	throw new CaseCreationException("Error : Error  while preparing result caseCreationUpdate in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ICaseCreationTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new CaseCreationException("Error : Error  while preparing result caseCreationUpdate in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param caseCreationUpdateTrxValue
     * @return ICaseCreationTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected ICaseCreationTrxValue prepareInsertTrxValue(ICaseCreationTrxValue caseCreationUpdateTrxValue)throws TrxOperationException {
        if (caseCreationUpdateTrxValue != null) {
            IFileMapperId actual = caseCreationUpdateTrxValue.getFileMapperID();
            IFileMapperId staging = caseCreationUpdateTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	caseCreationUpdateTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	caseCreationUpdateTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	caseCreationUpdateTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	caseCreationUpdateTrxValue.setStagingReferenceID(null);
            }
            return caseCreationUpdateTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CaseCreation is null");
        }
    }
	
    
    
    protected ICaseCreationTrxValue updateMasterInsertTrx(ICaseCreationTrxValue caseCreationUpdateTrxValue) throws TrxOperationException {
        try {
        	caseCreationUpdateTrxValue = prepareInsertTrxValue(caseCreationUpdateTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(caseCreationUpdateTrxValue);
            OBCaseCreationTrxValue newValue = new OBCaseCreationTrxValue(tempValue);
            newValue.setFileMapperID(caseCreationUpdateTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(caseCreationUpdateTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected ICaseCreationTrxValue createStagingFileId(ICaseCreationTrxValue caseCreationUpdateTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingCaseCreationFileMapperIdBusManager().createFileId(caseCreationUpdateTrxValue.getStagingFileMapperID());
        	caseCreationUpdateTrxValue.setStagingFileMapperID(fileMapperID);
        	caseCreationUpdateTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return caseCreationUpdateTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected ICaseCreationTrxValue insertActualCaseCreation(ICaseCreationTrxValue caseCreationUpdateTrxValue) throws TrxOperationException {
        try {
            ICaseCreation caseCreationUpdate = getStagingCaseCreationFileMapperIdBusManager().insertCaseCreation(caseCreationUpdateTrxValue.getStagingCaseCreation());
            caseCreationUpdateTrxValue.setCaseCreation(caseCreationUpdate);
            caseCreationUpdateTrxValue.setReferenceID(String.valueOf(caseCreationUpdate.getId()));
            return caseCreationUpdateTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	
}
