package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranchBusManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract CaseBranch Operation 
 */

public abstract class AbstractCaseBranchTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ICaseBranchBusManager caseBranchBusManager;

    private ICaseBranchBusManager stagingCaseBranchBusManager;

    private ICaseBranchBusManager stagingCaseBranchFileMapperIdBusManager;
    
    private ICaseBranchBusManager caseBranchFileMapperIdBusManager;

    public ICaseBranchBusManager getCaseBranchBusManager() {
		return caseBranchBusManager;
	}

	public void setCaseBranchBusManager(
			ICaseBranchBusManager caseBranchBusManager) {
		this.caseBranchBusManager = caseBranchBusManager;
	}

	public ICaseBranchBusManager getStagingCaseBranchBusManager() {
		return stagingCaseBranchBusManager;
	}

	public void setStagingCaseBranchBusManager(
			ICaseBranchBusManager stagingCaseBranchBusManager) {
		this.stagingCaseBranchBusManager = stagingCaseBranchBusManager;
	}
	
	public ICaseBranchBusManager getStagingCaseBranchFileMapperIdBusManager() {
		return stagingCaseBranchFileMapperIdBusManager;
	}

	public void setStagingCaseBranchFileMapperIdBusManager(
			ICaseBranchBusManager stagingCaseBranchFileMapperIdBusManager) {
		this.stagingCaseBranchFileMapperIdBusManager = stagingCaseBranchFileMapperIdBusManager;
	}

	public ICaseBranchBusManager getCaseBranchFileMapperIdBusManager() {
		return caseBranchFileMapperIdBusManager;
	}

	public void setCaseBranchFileMapperIdBusManager(
			ICaseBranchBusManager caseBranchFileMapperIdBusManager) {
		this.caseBranchFileMapperIdBusManager = caseBranchFileMapperIdBusManager;
	}

	protected ICaseBranchTrxValue prepareTrxValue(ICaseBranchTrxValue caseBranchTrxValue)throws TrxOperationException {
        if (caseBranchTrxValue != null) {
            ICaseBranch actual = caseBranchTrxValue.getCaseBranch();
            ICaseBranch staging = caseBranchTrxValue.getStagingCaseBranch();
            if (actual != null) {
            	caseBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	caseBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	caseBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	caseBranchTrxValue.setStagingReferenceID(null);
            }
            return caseBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CaseBranch is null");
        }
    }
	/**
	 * 
	 * @param caseBranchTrxValue
	 * @return ICaseBranchTrxValue
	 * @throws TrxOperationException
	 */

    protected ICaseBranchTrxValue updateCaseBranchTrx(ICaseBranchTrxValue caseBranchTrxValue) throws TrxOperationException {
    	if(caseBranchTrxValue!=null){
    	try {
        	caseBranchTrxValue = prepareTrxValue(caseBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(caseBranchTrxValue);
            OBCaseBranchTrxValue newValue = new OBCaseBranchTrxValue(tempValue);
            newValue.setCaseBranch(caseBranchTrxValue.getCaseBranch());
            newValue.setStagingCaseBranch(caseBranchTrxValue.getStagingCaseBranch());
            return newValue;
        }
        
        catch (CaseBranchException ex) {
            throw new CaseBranchException("General Exception: in update caseBranch  " );
        }
    	}else{
        	throw new CaseBranchException("Error : Error  while preparing result caseBranch in abstract trx operation");
        }
    }
    /**
     * 
     * @param caseBranchTrxValue
     * @return ICaseBranchTrxValue
     * @throws TrxOperationException
     */

    protected ICaseBranchTrxValue createStagingCaseBranch(ICaseBranchTrxValue caseBranchTrxValue) throws TrxOperationException {
    	if(caseBranchTrxValue!=null){
    	try {
            ICaseBranch caseBranch = getStagingCaseBranchBusManager().createCaseBranch(caseBranchTrxValue.getStagingCaseBranch());
            caseBranchTrxValue.setStagingCaseBranch(caseBranch);
            caseBranchTrxValue.setStagingReferenceID(String.valueOf(caseBranch.getId()));
            return caseBranchTrxValue;
        }
        catch (CaseBranchException e) {
            throw new CaseBranchException("Error : Error  while creating caseBranch in abstract trx operation");
        }catch (Exception ex) {
            throw new CaseBranchException("Error : Error  while creating caseBranch in abstract trx operation");
        }
    	}else{
        	throw new CaseBranchException("Error : Error  while preparing result caseBranch in abstract trx operation");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ICaseBranchTrxValue
     * @throws TrxOperationException
     */

    protected ICaseBranchTrxValue getCaseBranchTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (ICaseBranchTrxValue) anITrxValue;
        }
        catch (CaseBranchException e) {
            throw new CaseBranchException("The ITrxValue is not of type OBCCaseBranchTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new CaseBranchException("The ITrxValue is not of type OBCCaseBranchTrxValue: " + ex.toString());
        }
    	}else{
        	throw new CaseBranchException("Error : Error  while preparing result caseBranch in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ICaseBranchTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new CaseBranchException("Error : Error  while preparing result caseBranch in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param caseBranchTrxValue
     * @return ICaseBranchTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected ICaseBranchTrxValue prepareInsertTrxValue(ICaseBranchTrxValue caseBranchTrxValue)throws TrxOperationException {
        if (caseBranchTrxValue != null) {
            IFileMapperId actual = caseBranchTrxValue.getFileMapperID();
            IFileMapperId staging = caseBranchTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	caseBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	caseBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	caseBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	caseBranchTrxValue.setStagingReferenceID(null);
            }
            return caseBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CaseBranch is null");
        }
    }
	
    
    
    protected ICaseBranchTrxValue updateMasterInsertTrx(ICaseBranchTrxValue caseBranchTrxValue) throws TrxOperationException {
        try {
        	caseBranchTrxValue = prepareInsertTrxValue(caseBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(caseBranchTrxValue);
            OBCaseBranchTrxValue newValue = new OBCaseBranchTrxValue(tempValue);
            newValue.setFileMapperID(caseBranchTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(caseBranchTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected ICaseBranchTrxValue createStagingFileId(ICaseBranchTrxValue caseBranchTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingCaseBranchFileMapperIdBusManager().createFileId(caseBranchTrxValue.getStagingFileMapperID());
        	caseBranchTrxValue.setStagingFileMapperID(fileMapperID);
        	caseBranchTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return caseBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected ICaseBranchTrxValue insertActualCaseBranch(ICaseBranchTrxValue caseBranchTrxValue) throws TrxOperationException {
        try {
            ICaseBranch caseBranch = getStagingCaseBranchFileMapperIdBusManager().insertCaseBranch(caseBranchTrxValue.getStagingCaseBranch());
            caseBranchTrxValue.setCaseBranch(caseBranch);
            caseBranchTrxValue.setReferenceID(String.valueOf(caseBranch.getId()));
            return caseBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	
}
