package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.otherbank.bus.IOtherBankBusManager;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 * Abstract Other Bank Operation 
 */

public abstract class AbstractOtherBankTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

  

	

	private IOtherBankBusManager otherBankBusManager;

    private IOtherBankBusManager stagingOtherBankBusManager;
    
  //**********************FOR UPLOAD********************************
	   private IOtherBankBusManager stagingOtherBankFileMapperIdBusManager;
	    
	    private IOtherBankBusManager otherBankFileMapperIdBusManager;

	  //**********************UPLOAD********************************
	 
	    public IOtherBankBusManager getStagingOtherBankFileMapperIdBusManager() {
			return stagingOtherBankFileMapperIdBusManager;
		}
		public void setStagingOtherBankFileMapperIdBusManager(
				IOtherBankBusManager stagingOtherBankFileMapperIdBusManager) {
			this.stagingOtherBankFileMapperIdBusManager = stagingOtherBankFileMapperIdBusManager;
		}
		public IOtherBankBusManager getOtherBankFileMapperIdBusManager() {
			return otherBankFileMapperIdBusManager;
		}
		public void setOtherBankFileMapperIdBusManager(
				IOtherBankBusManager otherBankFileMapperIdBusManager) {
			this.otherBankFileMapperIdBusManager = otherBankFileMapperIdBusManager;
		}
	    
	  
    /**
	 * @return the otherBankBusManager
	 */
	/**
	 * @return
	 */
	public IOtherBankBusManager getOtherBankBusManager() {
		return otherBankBusManager;
	}
	/**
	 * @param otherBankBusManager the otherBankBusManager to set
	 */
	public void setOtherBankBusManager(IOtherBankBusManager otherBankBusManager) {
		this.otherBankBusManager = otherBankBusManager;
	}
	/**
	 * @return the stagingOtherBankBusManager
	 */
	public IOtherBankBusManager getStagingOtherBankBusManager() {
		return stagingOtherBankBusManager;
	}
	/**
	 * @param stagingOtherBankBusManager the stagingOtherBankBusManager to set
	 */
	public void setStagingOtherBankBusManager(
			IOtherBankBusManager stagingOtherBankBusManager) {
		this.stagingOtherBankBusManager = stagingOtherBankBusManager;
	}
	/**
     * 
     * @param OtherBankTrxValue
     * @return IOtherBankTrxValue
     */

    protected IOtherBankTrxValue prepareTrxValue(IOtherBankTrxValue otherBankTrxValue) {
        if (otherBankTrxValue != null) {
            IOtherBank actual = otherBankTrxValue.getOtherBank();
            IOtherBank staging = otherBankTrxValue.getStagingOtherBank();
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
     * @return IOtherBankTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBankTrxValue updateOtherBankTrx(IOtherBankTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
        	otherBankTrxValue = prepareTrxValue(otherBankTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(otherBankTrxValue);
            OBOtherBankTrxValue newValue = new OBOtherBankTrxValue(tempValue);
            newValue.setOtherBank(otherBankTrxValue.getOtherBank());
            newValue.setStagingOtherBank(otherBankTrxValue.getStagingOtherBank());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new OtherBankException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param otherBankTrxValue
     * @return IOtherBankTrxValue
     * @throws TrxOperationException
     */
    protected IOtherBankTrxValue createStagingOtherBank(IOtherBankTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
            IOtherBank otherBank = getStagingOtherBankBusManager().createOtherBank(otherBankTrxValue.getStagingOtherBank());
            otherBankTrxValue.setStagingOtherBank(otherBank);
            otherBankTrxValue.setStagingReferenceID(String.valueOf(otherBank.getId()));
            return otherBankTrxValue;
        }
        catch (Exception ex) {
            throw new OtherBankException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IOtherBankTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBankTrxValue getOtherBankTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IOtherBankTrxValue) anITrxValue;
        }
        catch (Exception ex) {
            throw new OtherBankException("The ITrxValue is not of type OBCOtherBankTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IOtherBankTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBank mergeOtherBank(IOtherBank anOriginal, IOtherBank aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IOtherBankTrxValue
     */

    protected ITrxResult prepareResult(IOtherBankTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IOtherBankTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IOtherBankTrxValue createTransaction(IOtherBankTrxValue value) throws TrxOperationException {
		OBOtherBankTrxValue newValue = null;
		if(value != null){
		//	value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBOtherBankTrxValue(tempValue);
			newValue.setOtherBank(value.getOtherBank());
			newValue.setStagingOtherBank(value.getStagingOtherBank());
		}	
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IOtherBankTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IOtherBankTrxValue updateTransaction(IOtherBankTrxValue value) throws TrxOperationException {
		OBOtherBankTrxValue newValue = null;
		if(value != null){
		//	value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			newValue = new OBOtherBankTrxValue(tempValue);
			newValue.setOtherBank(value.getOtherBank());
			newValue.setStagingOtherBank(value.getStagingOtherBank());
		}	
		return newValue;

	}
	
  //------------------------------------File Insert---------------------------------------------
    
    protected IOtherBankTrxValue prepareInsertTrxValue(IOtherBankTrxValue otherBankTrxValue)throws TrxOperationException {
        if (otherBankTrxValue != null) {
            IFileMapperId actual = otherBankTrxValue.getFileMapperID();
            IFileMapperId staging = otherBankTrxValue.getStagingFileMapperID();
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
        }
        else{
        	throw new  TrxOperationException("ERROR-- OtherBank is null");
        }
    }
    
    
    
    protected IOtherBankTrxValue createStagingFileId(IOtherBankTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingOtherBankFileMapperIdBusManager().createFileId(otherBankTrxValue.getStagingFileMapperID());
        	otherBankTrxValue.setStagingFileMapperID(fileMapperID);
        	otherBankTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return otherBankTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IOtherBankTrxValue insertActualOtherBank(IOtherBankTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
            IOtherBank otherBank = getStagingOtherBankFileMapperIdBusManager().insertOtherBank(otherBankTrxValue.getStagingOtherBank());
            otherBankTrxValue.setOtherBank(otherBank);
            otherBankTrxValue.setReferenceID(String.valueOf(otherBank.getId()));
            return otherBankTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    
    protected IOtherBankTrxValue updateMasterInsertTrx(IOtherBankTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
        	otherBankTrxValue = prepareInsertTrxValue(otherBankTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(otherBankTrxValue);
            OBOtherBankTrxValue newValue = new OBOtherBankTrxValue(tempValue);
            newValue.setFileMapperID(otherBankTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(otherBankTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }

	

	

	
}
