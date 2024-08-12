package com.integrosys.cms.app.relationshipmgr.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.holiday.bus.IHoliday;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrBusManager;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;

/**
 * @author dattatray.thorat
 * Abstract Relationship Manager Operation 
 */

public abstract class AbstractRelationshipMgrTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    private IRelationshipMgrBusManager relationshipMgrBusManager;

    private IRelationshipMgrBusManager stagingRelationshipMgrBusManager;
    
    private IRelationshipMgrBusManager stagingRelationshipMgrFileMapperIdBusManager;
    
    private IRelationshipMgrBusManager relationshipMgrFileMapperIdBusManager;
    
	/**
	 * @return the stagingRelationshipMgrFileMapperIdBusManager
	 */
	public IRelationshipMgrBusManager getStagingRelationshipMgrFileMapperIdBusManager() {
		return stagingRelationshipMgrFileMapperIdBusManager;
	}
	/**
	 * @param stagingRelationshipMgrFileMapperIdBusManager the stagingRelationshipMgrFileMapperIdBusManager to set
	 */
	public void setStagingRelationshipMgrFileMapperIdBusManager(
			IRelationshipMgrBusManager stagingRelationshipMgrFileMapperIdBusManager) {
		this.stagingRelationshipMgrFileMapperIdBusManager = stagingRelationshipMgrFileMapperIdBusManager;
	}
	/**
	 * @return the relationshipMgrFileMapperIdBusManager
	 */
	public IRelationshipMgrBusManager getRelationshipMgrFileMapperIdBusManager() {
		return relationshipMgrFileMapperIdBusManager;
	}
	/**
	 * @param relationshipMgrFileMapperIdBusManager the relationshipMgrFileMapperIdBusManager to set
	 */
	public void setRelationshipMgrFileMapperIdBusManager(
			IRelationshipMgrBusManager relationshipMgrFileMapperIdBusManager) {
		this.relationshipMgrFileMapperIdBusManager = relationshipMgrFileMapperIdBusManager;
	}
	/**
	 * @return the relationshipMgrBusManager
	 */
	public IRelationshipMgrBusManager getRelationshipMgrBusManager() {
		return relationshipMgrBusManager;
	}
	/**
	 * @param relationshipMgrBusManager the relationshipMgrBusManager to set
	 */
	public void setRelationshipMgrBusManager(
			IRelationshipMgrBusManager relationshipMgrBusManager) {
		this.relationshipMgrBusManager = relationshipMgrBusManager;
	}
	/**
	 * @return the stagingRelationshipMgrBusManager
	 */
	public IRelationshipMgrBusManager getStagingRelationshipMgrBusManager() {
		return stagingRelationshipMgrBusManager;
	}
	/**
	 * @param stagingRelationshipMgrBusManager the stagingRelationshipMgrBusManager to set
	 */
	public void setStagingRelationshipMgrBusManager(
			IRelationshipMgrBusManager stagingRelationshipMgrBusManager) {
		this.stagingRelationshipMgrBusManager = stagingRelationshipMgrBusManager;
	}
	/**
     * 
     * @param OtherBankTrxValue
     * @return IOtherBankTrxValue
     */

    protected IRelationshipMgrTrxValue prepareTrxValue(IRelationshipMgrTrxValue otherBankTrxValue) {
        if (otherBankTrxValue != null) {
            IRelationshipMgr actual = otherBankTrxValue.getRelationshipMgr();
            IRelationshipMgr staging = otherBankTrxValue.getStagingRelationshipMgr();
            if (actual != null) {
            	otherBankTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	otherBankTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	otherBankTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	otherBankTrxValue.setStagingReferenceID(null);
            }
            return otherBankTrxValue;
        }else{
        	throw new  OtherBankException("ERROR-- Other Bank is null");
        }
    }
    /**
     * 
     * @param otherBankTrxValue
     * @return IRelationshipMgrTrxValue
     * @throws TrxOperationException
     */

    protected IRelationshipMgrTrxValue updateRelationshipMgrTrx(IRelationshipMgrTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
        	otherBankTrxValue = prepareTrxValue(otherBankTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(otherBankTrxValue);
            OBRelationshipMgrTrxValue newValue = new OBRelationshipMgrTrxValue(tempValue);
            newValue.setRelationshipMgr(otherBankTrxValue.getRelationshipMgr());
            newValue.setStagingRelationshipMgr(otherBankTrxValue.getStagingRelationshipMgr());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new RelationshipMgrException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param RelationshipTrxTrxValue
     * @return IRelationshipMgrTrxValue
     * @throws TrxOperationException
     */
    protected IRelationshipMgrTrxValue createStagingRelationshipMgr(IRelationshipMgrTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
            IRelationshipMgr otherBank = getStagingRelationshipMgrBusManager().createRelationshipMgr(relationshipMgrTrxValue.getStagingRelationshipMgr());
            relationshipMgrTrxValue.setStagingRelationshipMgr(otherBank);
            relationshipMgrTrxValue.setStagingReferenceID(String.valueOf(otherBank.getId()));
            return relationshipMgrTrxValue;
        }
        catch (Exception ex) {
            throw new RelationshipMgrException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IRelationshipMgrTrxValue
     * @throws TrxOperationException
     */

    protected IRelationshipMgrTrxValue getRelationshipMgrTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IRelationshipMgrTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new RelationshipMgrException("The ITrxValue is not of type OBCRelationshipMgrTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IRelationshipMgrTrxValue
     * @throws TrxOperationException
     */

    protected IRelationshipMgr mergeRelationshipMgr(IRelationshipMgr anOriginal, IRelationshipMgr aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IRelationshipMgrTrxValue
     */

    protected ITrxResult prepareResult(IRelationshipMgrTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IRelationshipMgrTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IRelationshipMgrTrxValue createTransaction(IRelationshipMgrTrxValue value) throws TrxOperationException {
		OBRelationshipMgrTrxValue newValue = null;
		if(value != null){
			//value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBRelationshipMgrTrxValue(tempValue);
			newValue.setRelationshipMgr(value.getRelationshipMgr());
			newValue.setStagingRelationshipMgr(value.getStagingRelationshipMgr());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IRelationshipMgrTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IRelationshipMgrTrxValue updateTransaction(IRelationshipMgrTrxValue value) throws TrxOperationException {
		OBRelationshipMgrTrxValue newValue = null;
		if(value != null){
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBRelationshipMgrTrxValue(tempValue);
			newValue.setRelationshipMgr(value.getRelationshipMgr());
			newValue.setStagingRelationshipMgr(value.getStagingRelationshipMgr());
		}	
		return newValue;

	}
	
//------------------------------------File Insert---------------------------------------------
    
    protected IRelationshipMgrTrxValue prepareInsertTrxValue(IRelationshipMgrTrxValue relationshipMgrTrxValue)throws TrxOperationException {
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
        	throw new  TrxOperationException("ERROR-- RelationshipMgr is null");
        }
    }
	
    
    
    protected IRelationshipMgrTrxValue updateMasterInsertTrx(IRelationshipMgrTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
        	relationshipMgrTrxValue = prepareInsertTrxValue(relationshipMgrTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(relationshipMgrTrxValue);
            OBRelationshipMgrTrxValue newValue = new OBRelationshipMgrTrxValue(tempValue);
            newValue.setFileMapperID(relationshipMgrTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(relationshipMgrTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IRelationshipMgrTrxValue createStagingFileId(IRelationshipMgrTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingRelationshipMgrFileMapperIdBusManager().createFileId(relationshipMgrTrxValue.getStagingFileMapperID());
        	relationshipMgrTrxValue.setStagingFileMapperID(fileMapperID);
        	relationshipMgrTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return relationshipMgrTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IRelationshipMgrTrxValue insertActualRelationshipMgr(IRelationshipMgrTrxValue relationshipMgrTrxValue) throws TrxOperationException {
        try {
            IRelationshipMgr relationshipMgr = getStagingRelationshipMgrFileMapperIdBusManager().insertRelationshipMgr(relationshipMgrTrxValue.getStagingRelationshipMgr());
            relationshipMgrTrxValue.setRelationshipMgr(relationshipMgr);
            relationshipMgrTrxValue.setReferenceID(String.valueOf(relationshipMgr.getId()));
            return relationshipMgrTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
