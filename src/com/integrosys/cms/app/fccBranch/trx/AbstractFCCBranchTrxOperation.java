package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchBusManager;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract FCCBranch Operation 
 */

public abstract class AbstractFCCBranchTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IFCCBranchBusManager fccBranchBusManager;

    private IFCCBranchBusManager stagingFCCBranchBusManager;

    private IFCCBranchBusManager stagingFCCBranchFileMapperIdBusManager;
    
    private IFCCBranchBusManager fccBranchFileMapperIdBusManager;

   

	/**
	 * @return the fccBranchBusManager
	 */
	public IFCCBranchBusManager getFccBranchBusManager() {
		return fccBranchBusManager;
	}
	/**
	 * @param fccBranchBusManager the fccBranchBusManager to set
	 */
	public void setFccBranchBusManager(IFCCBranchBusManager fccBranchBusManager) {
		this.fccBranchBusManager = fccBranchBusManager;
	}
	/**
	 * @return the stagingFCCBranchBusManager
	 */
	public IFCCBranchBusManager getStagingFCCBranchBusManager() {
		return stagingFCCBranchBusManager;
	}
	/**
	 * @param stagingFCCBranchBusManager the stagingFCCBranchBusManager to set
	 */
	public void setStagingFCCBranchBusManager(
			IFCCBranchBusManager stagingFCCBranchBusManager) {
		this.stagingFCCBranchBusManager = stagingFCCBranchBusManager;
	}
	/**
	 * @return the stagingFCCBranchFileMapperIdBusManager
	 */
	public IFCCBranchBusManager getStagingFCCBranchFileMapperIdBusManager() {
		return stagingFCCBranchFileMapperIdBusManager;
	}
	/**
	 * @param stagingFCCBranchFileMapperIdBusManager the stagingFCCBranchFileMapperIdBusManager to set
	 */
	public void setStagingFCCBranchFileMapperIdBusManager(
			IFCCBranchBusManager stagingFCCBranchFileMapperIdBusManager) {
		this.stagingFCCBranchFileMapperIdBusManager = stagingFCCBranchFileMapperIdBusManager;
	}
	/**
	 * @return the fccBranchFileMapperIdBusManager
	 */
	public IFCCBranchBusManager getFccBranchFileMapperIdBusManager() {
		return fccBranchFileMapperIdBusManager;
	}
	/**
	 * @param fccBranchFileMapperIdBusManager the fccBranchFileMapperIdBusManager to set
	 */
	public void setFccBranchFileMapperIdBusManager(
			IFCCBranchBusManager fccBranchFileMapperIdBusManager) {
		this.fccBranchFileMapperIdBusManager = fccBranchFileMapperIdBusManager;
	}
	protected IFCCBranchTrxValue prepareTrxValue(IFCCBranchTrxValue fccBranchTrxValue)throws TrxOperationException {
        if (fccBranchTrxValue != null) {
            IFCCBranch actual = fccBranchTrxValue.getFCCBranch();
            IFCCBranch staging = fccBranchTrxValue.getStagingFCCBranch();
            if (actual != null) {
            	fccBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	fccBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	fccBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	fccBranchTrxValue.setStagingReferenceID(null);
            }
            return fccBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- FCCBranch is null");
        }
    }
	/**
	 * 
	 * @param fccBranchTrxValue
	 * @return IFCCBranchTrxValue
	 * @throws TrxOperationException
	 */

    protected IFCCBranchTrxValue updateFCCBranchTrx(IFCCBranchTrxValue fccBranchTrxValue) throws TrxOperationException {
    	if(fccBranchTrxValue!=null){
    	try {
        	fccBranchTrxValue = prepareTrxValue(fccBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(fccBranchTrxValue);
            OBFCCBranchTrxValue newValue = new OBFCCBranchTrxValue(tempValue);
            newValue.setFCCBranch(fccBranchTrxValue.getFCCBranch());
            newValue.setStagingFCCBranch(fccBranchTrxValue.getStagingFCCBranch());
            return newValue;
        }
        
        catch (FCCBranchException ex) {
            throw new FCCBranchException("General Exception: in update fccBranch  " );
        }
    	}else{
        	throw new FCCBranchException("Error : Error  while preparing result fccBranch in abstract trx operation");
        }
    }
    /**
     * 
     * @param fccBranchTrxValue
     * @return IFCCBranchTrxValue
     * @throws TrxOperationException
     */

    protected IFCCBranchTrxValue createStagingFCCBranch(IFCCBranchTrxValue fccBranchTrxValue) throws TrxOperationException {
    	if(fccBranchTrxValue!=null){
    	try {
            IFCCBranch fccBranch = getStagingFCCBranchBusManager().createFCCBranch(fccBranchTrxValue.getStagingFCCBranch());
            fccBranchTrxValue.setStagingFCCBranch(fccBranch);
            fccBranchTrxValue.setStagingReferenceID(String.valueOf(fccBranch.getId()));
            return fccBranchTrxValue;
        }
        catch (FCCBranchException e) {
            throw new FCCBranchException("Error : Error  while creating fccBranch in abstract trx operation");
        }catch (Exception ex) {
            throw new FCCBranchException("Error : Error  while creating fccBranch in abstract trx operation");
        }
    	}else{
        	throw new FCCBranchException("Error : Error  while preparing result fccBranch in abstract trx operation");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IFCCBranchTrxValue
     * @throws TrxOperationException
     */

    protected IFCCBranchTrxValue getFCCBranchTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
    	if(anITrxValue!=null){
    	try {
            return (IFCCBranchTrxValue) anITrxValue;
        }
        catch (FCCBranchException e) {
            throw new FCCBranchException("The ITrxValue is not of type OBCFCCBranchTrxValue: ");
        }
        catch (ClassCastException ex) {
            throw new FCCBranchException("The ITrxValue is not of type OBCFCCBranchTrxValue: " + ex.toString());
        }
    	}else{
        	throw new FCCBranchException("Error : Error  while preparing result fccBranch in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(IFCCBranchTrxValue value) {
    	if(value!=null){
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
        }else{
        	throw new FCCBranchException("Error : Error  while preparing result fccBranch in abstract trx operation");
        }
    }
    
    /**
     * 
     * @param fccBranchTrxValue
     * @return IFCCBranchTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IFCCBranchTrxValue prepareInsertTrxValue(IFCCBranchTrxValue fccBranchTrxValue)throws TrxOperationException {
        if (fccBranchTrxValue != null) {
            IFileMapperId actual = fccBranchTrxValue.getFileMapperID();
            IFileMapperId staging = fccBranchTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	fccBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	fccBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	fccBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	fccBranchTrxValue.setStagingReferenceID(null);
            }
            return fccBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- FCCBranch is null");
        }
    }
	
    
    
    protected IFCCBranchTrxValue updateMasterInsertTrx(IFCCBranchTrxValue fccBranchTrxValue) throws TrxOperationException {
        try {
        	fccBranchTrxValue = prepareInsertTrxValue(fccBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(fccBranchTrxValue);
            OBFCCBranchTrxValue newValue = new OBFCCBranchTrxValue(tempValue);
            newValue.setFileMapperID(fccBranchTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(fccBranchTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IFCCBranchTrxValue createStagingFileId(IFCCBranchTrxValue fccBranchTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingFCCBranchFileMapperIdBusManager().createFileId(fccBranchTrxValue.getStagingFileMapperID());
        	fccBranchTrxValue.setStagingFileMapperID(fileMapperID);
        	fccBranchTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return fccBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IFCCBranchTrxValue insertActualFCCBranch(IFCCBranchTrxValue fccBranchTrxValue) throws TrxOperationException {
        try {
            IFCCBranch fccBranch = getStagingFCCBranchFileMapperIdBusManager().insertFCCBranch(fccBranchTrxValue.getStagingFCCBranch());
            fccBranchTrxValue.setFCCBranch(fccBranch);
            fccBranchTrxValue.setReferenceID(String.valueOf(fccBranch.getId()));
            return fccBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	
}
