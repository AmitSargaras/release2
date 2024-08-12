package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranchBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author abhijit.rudrakshawar
 * Abstract System Bank Operation 
 */

public abstract class AbstractSystemBankBranchTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private ISystemBankBranchBusManager systemBankBranchBusManager;

    private ISystemBankBranchBusManager stagingSystemBankBranchBusManager;
    
    private ISystemBankBranchBusManager stagingFileMapperIdSysBusManager;
    
    private ISystemBankBranchBusManager fileMapperIdSysBusManager;

 

    public ISystemBankBranchBusManager getSystemBankBranchBusManager() {
		return systemBankBranchBusManager;
	}

	public void setSystemBankBranchBusManager(
			ISystemBankBranchBusManager systemBankBranchBusManager) {
		this.systemBankBranchBusManager = systemBankBranchBusManager;
	}

	public ISystemBankBranchBusManager getStagingSystemBankBranchBusManager() {
		return stagingSystemBankBranchBusManager;
	}

	public void setStagingSystemBankBranchBusManager(
			ISystemBankBranchBusManager stagingSystemBankBranchBusManager) {
		this.stagingSystemBankBranchBusManager = stagingSystemBankBranchBusManager;
	}
	
	public ISystemBankBranchBusManager getStagingFileMapperIdSysBusManager() {
		return stagingFileMapperIdSysBusManager;
	}

	public void setStagingFileMapperIdSysBusManager(
			ISystemBankBranchBusManager stagingFileMapperIdSysBusManager) {
		this.stagingFileMapperIdSysBusManager = stagingFileMapperIdSysBusManager;
	}
	
	public ISystemBankBranchBusManager getFileMapperIdSysBusManager() {
		return fileMapperIdSysBusManager;
	}

	public void setFileMapperIdSysBusManager(
			ISystemBankBranchBusManager fileMapperIdSysBusManager) {
		this.fileMapperIdSysBusManager = fileMapperIdSysBusManager;
	}


	protected ISystemBankBranchTrxValue prepareTrxValue(ISystemBankBranchTrxValue systemBankBranchTrxValue)throws TrxOperationException {
        if (systemBankBranchTrxValue != null) {
            ISystemBankBranch actual = systemBankBranchTrxValue.getSystemBankBranch();
            ISystemBankBranch staging = systemBankBranchTrxValue.getStagingSystemBankBranch();
            if (actual != null) {
            	systemBankBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	systemBankBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	systemBankBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	systemBankBranchTrxValue.setStagingReferenceID(null);
            }
            return systemBankBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- System Bank is null");
        }
    }
	/**
	 * 
	 * @param systemBankBranchTrxValue
	 * @return ISystemBankBranchTrxValue
	 * @throws TrxOperationException
	 */

    protected ISystemBankBranchTrxValue updateSystemBankBranchTrx(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
        	systemBankBranchTrxValue = prepareTrxValue(systemBankBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(systemBankBranchTrxValue);
            OBSystemBankBranchTrxValue newValue = new OBSystemBankBranchTrxValue(tempValue);
            newValue.setSystemBankBranch(systemBankBranchTrxValue.getSystemBankBranch());
            newValue.setStagingSystemBankBranch(systemBankBranchTrxValue.getStagingSystemBankBranch());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    /**
     * 
     * @param systemBankBranchTrxValue
     * @return ISystemBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankBranchTrxValue createStagingSystemBankBranch(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
            ISystemBankBranch systemBankBranch = getStagingSystemBankBranchBusManager().createSystemBankBranch(systemBankBranchTrxValue.getStagingSystemBankBranch());
            systemBankBranchTrxValue.setStagingSystemBankBranch(systemBankBranch);
            systemBankBranchTrxValue.setStagingReferenceID(String.valueOf(systemBankBranch.getId()));
            return systemBankBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param systemBankBranchTrxValue
     * @return ISystemBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankBranchTrxValue createActualSystemBankBranch(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
            ISystemBankBranch systemBankBranch = getStagingSystemBankBranchBusManager().createSystemBankBranch(systemBankBranchTrxValue.getStagingSystemBankBranch());
            systemBankBranchTrxValue.setStagingSystemBankBranch(systemBankBranch);
            systemBankBranchTrxValue.setStagingReferenceID(String.valueOf(systemBankBranch.getId()));
            return systemBankBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return ISystemBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankBranchTrxValue getSystemBankBranchTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ISystemBankBranchTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCSystemBankBranchTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return ISystemBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankBranch mergeSystemBankBranch(ISystemBankBranch anOriginal, ISystemBankBranch aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return ITrxResult
     */

    protected ITrxResult prepareResult(ISystemBankBranchTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    //------------------------------------File Insert---------------------------------------------
    
    
    protected ISystemBankBranchTrxValue prepareInsertTrxValue(ISystemBankBranchTrxValue systemBankBranchTrxValue)throws TrxOperationException {
        if (systemBankBranchTrxValue != null) {
            IFileMapperId actual = systemBankBranchTrxValue.getFileMapperID();
            IFileMapperId staging = systemBankBranchTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	systemBankBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	systemBankBranchTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	systemBankBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	systemBankBranchTrxValue.setStagingReferenceID(null);
            }
            return systemBankBranchTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- System Bank is null");
        }
    }
    
    
    protected ISystemBankBranchTrxValue updateSystemBankBranchInsertTrx(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
        	systemBankBranchTrxValue = prepareInsertTrxValue(systemBankBranchTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(systemBankBranchTrxValue);
            OBSystemBankBranchTrxValue newValue = new OBSystemBankBranchTrxValue(tempValue);
            newValue.setFileMapperID(systemBankBranchTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(systemBankBranchTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
	 ISystemBankBranchTrxValue createStagingFileId(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingFileMapperIdSysBusManager().createFileId(systemBankBranchTrxValue.getStagingFileMapperID());
            systemBankBranchTrxValue.setStagingFileMapperID(fileMapperID);
            systemBankBranchTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return systemBankBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    /**
     * 
     * @param systemBankBranchTrxValue
     * @return ISystemBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected ISystemBankBranchTrxValue insertActualSystemBankBranch(ISystemBankBranchTrxValue systemBankBranchTrxValue) throws TrxOperationException {
        try {
            ISystemBankBranch systemBankBranch = getSystemBankBranchBusManager().insertSystemBankBranch(systemBankBranchTrxValue.getStagingSystemBankBranch());
            systemBankBranchTrxValue.setSystemBankBranch(systemBankBranch);
            systemBankBranchTrxValue.setReferenceID(String.valueOf(systemBankBranch.getId()));
            return systemBankBranchTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	    
}
