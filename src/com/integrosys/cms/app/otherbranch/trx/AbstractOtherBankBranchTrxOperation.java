package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.otherbank.bus.IOtherBankBusManager;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbank.trx.OBOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.IOtherBranchBusManager;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 * Abstract Other Bank Branch Operation 
 */

public abstract class AbstractOtherBankBranchTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

  












	private IOtherBranchBusManager otherBranchBusManager;

    private IOtherBranchBusManager stagingOtherBranchBusManager;
    
    
    //**********************FOR UPLOAD********************************
	   private IOtherBranchBusManager stagingOtherBankBranchFileMapperIdBusManager;
	    
	    private IOtherBranchBusManager otherBankBranchFileMapperIdBusManager;

	  //**********************UPLOAD********************************
	  



		public IOtherBranchBusManager getStagingOtherBankBranchFileMapperIdBusManager() {
			return stagingOtherBankBranchFileMapperIdBusManager;
		}
		public void setStagingOtherBankBranchFileMapperIdBusManager(
				IOtherBranchBusManager stagingOtherBankBranchFileMapperIdBusManager) {
			this.stagingOtherBankBranchFileMapperIdBusManager = stagingOtherBankBranchFileMapperIdBusManager;
		}
		public IOtherBranchBusManager getOtherBankBranchFileMapperIdBusManager() {
			return otherBankBranchFileMapperIdBusManager;
		}
		public void setOtherBankBranchFileMapperIdBusManager(
				IOtherBranchBusManager otherBankBranchFileMapperIdBusManager) {
			this.otherBankBranchFileMapperIdBusManager = otherBankBranchFileMapperIdBusManager;
		}


	/**
	 * @return the otherBranchBusManager
	 */
	public IOtherBranchBusManager getOtherBranchBusManager() {
		return otherBranchBusManager;
	}
	/**
	 * @param otherBranchBusManager the otherBranchBusManager to set
	 */
	public void setOtherBranchBusManager(
			IOtherBranchBusManager otherBranchBusManager) {
		this.otherBranchBusManager = otherBranchBusManager;
	}

	/**
	 * @return the stagingOtherBranchBusManager
	 */
	public IOtherBranchBusManager getStagingOtherBranchBusManager() {
		return stagingOtherBranchBusManager;
	}
	/**
	 * @param stagingOtherBranchBusManager the stagingOtherBranchBusManager to set
	 */
	public void setStagingOtherBranchBusManager(
			IOtherBranchBusManager stagingOtherBranchBusManager) {
		this.stagingOtherBranchBusManager = stagingOtherBranchBusManager;
	}
	/**
     * 
     * @param OtherBankTrxValue
     * @return IOtherBankBranchTrxValue
     */

    protected IOtherBankBranchTrxValue prepareTrxValue(IOtherBankBranchTrxValue otherBankTrxValue) {
        if (otherBankTrxValue != null) {
            IOtherBranch actual = otherBankTrxValue.getOtherBranch();
            IOtherBranch staging = otherBankTrxValue.getStagingOtherBranch();
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
     * @return IOtherBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBankBranchTrxValue updateOtherBankBranchTrx(IOtherBankBranchTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
        	otherBankTrxValue = prepareTrxValue(otherBankTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(otherBankTrxValue);
            OBOtherBankBranchTrxValue newValue = new OBOtherBankBranchTrxValue(tempValue);
            newValue.setOtherBranch(otherBankTrxValue.getOtherBranch());
            newValue.setStagingOtherBranch(otherBankTrxValue.getStagingOtherBranch());
            return newValue;
        }
        
        catch (Exception ex) {
            throw new OtherBankException("General Exception: " + ex.toString());
        }
    }

    /**
     * 
     * @param otherBankTrxValue
     * @return IOtherBankBranchTrxValue
     * @throws TrxOperationException
     */
    protected IOtherBankBranchTrxValue createStagingOtherBankBranch(IOtherBankBranchTrxValue otherBankTrxValue) throws TrxOperationException {
        try {
            IOtherBranch otherBank = getStagingOtherBranchBusManager().createOtherBranch(otherBankTrxValue.getStagingOtherBranch());
            otherBankTrxValue.setStagingOtherBranch(otherBank);
            otherBankTrxValue.setStagingReferenceID(String.valueOf(otherBank.getId()));
            return otherBankTrxValue;
        }
        catch (Exception ex) {
            throw new OtherBranchException("ERROR-- While creating Staging value");
        }
    }
    /**
     * 
     * @param anITrxValue
     * @return IOtherBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBankBranchTrxValue getOtherBankBranchTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (IOtherBankBranchTrxValue) anITrxValue;
        }
        catch (ClassCastException ex) {
            throw new OtherBankException("The ITrxValue is not of type OBCOtherBankTrxValue: " + ex.toString());
        }
    }
    /**
     * 
     * @param anOriginal
     * @param aCopy
     * @return IOtherBankBranchTrxValue
     * @throws TrxOperationException
     */

    protected IOtherBranch mergeOtherBankBranch(IOtherBranch anOriginal, IOtherBranch aCopy) throws TrxOperationException {
        aCopy.setId(anOriginal.getId());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }
    /**
     * 
     * @param value
     * @return IOtherBankBranchTrxValue
     */

    protected ITrxResult prepareResult(IOtherBankBranchTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }
    
    /**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IOtherBankBranchTrxValue
	 * @return other bank transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IOtherBankBranchTrxValue createTransaction(IOtherBankBranchTrxValue value) throws TrxOperationException {
	//	value = prepareTrxValue(value);
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBOtherBankBranchTrxValue newValue = new OBOtherBankBranchTrxValue(tempValue);
		newValue.setOtherBranch(value.getOtherBranch());
		newValue.setStagingOtherBranch(value.getStagingOtherBranch());
		return newValue;
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type IOtherBankBranchTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IOtherBankBranchTrxValue updateTransaction(IOtherBankBranchTrxValue value) throws TrxOperationException {
	//	value = prepareTrxValue(value);
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBOtherBankBranchTrxValue newValue = new OBOtherBankBranchTrxValue(tempValue);
		newValue.setOtherBranch(value.getOtherBranch());
		newValue.setStagingOtherBranch(value.getStagingOtherBranch());  
		return newValue;

	}
	
	
	  //------------------------------------File Insert---------------------------------------------
	    
	    protected IOtherBankBranchTrxValue prepareInsertTrxValue(IOtherBankBranchTrxValue otherBankBranchTrxValue)throws TrxOperationException {
	        if (otherBankBranchTrxValue != null) {
	            IFileMapperId actual = otherBankBranchTrxValue.getFileMapperID();
	            IFileMapperId staging = otherBankBranchTrxValue.getStagingFileMapperID();
	            if (actual != null) {
	            	otherBankBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            } else {
	            	otherBankBranchTrxValue.setReferenceID(null);
	            }
	            if (staging != null) {
	            	otherBankBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
	            } else {
	            	otherBankBranchTrxValue.setStagingReferenceID(null);
	            }
	            return otherBankBranchTrxValue;
	        }
	        else{
	        	throw new  TrxOperationException("ERROR-- OtherBank is null");
	        }
	    }
	    
	
	
	
	 protected IOtherBankBranchTrxValue updateOtherBankBranchMasterInsertTrx(IOtherBankBranchTrxValue otherBankBranchTrxValue) throws TrxOperationException {
	        try {
	        	otherBankBranchTrxValue = prepareInsertTrxValue(otherBankBranchTrxValue);  
	            ICMSTrxValue tempValue = super.updateTransaction(otherBankBranchTrxValue);
	            OBOtherBankBranchTrxValue newValue = new OBOtherBankBranchTrxValue(tempValue);
	            newValue.setFileMapperID(otherBankBranchTrxValue.getFileMapperID());
	            newValue.setStagingFileMapperID(otherBankBranchTrxValue.getStagingFileMapperID());
	            return newValue;
	        }
	        
	        catch (TrxOperationException ex) {
	            throw new TrxOperationException("General Exception: " + ex.toString());
	        }
	    }
	 
	   protected IOtherBankBranchTrxValue insertActualOtherBankBranch(IOtherBankBranchTrxValue otherBankTrxValue) throws TrxOperationException {
	        try {
	            IOtherBranch otherBank = getStagingOtherBankBranchFileMapperIdBusManager().insertOtherBankBranch(otherBankTrxValue.getStagingOtherBranch());
	            otherBankTrxValue.setOtherBranch(otherBank);
	            otherBankTrxValue.setReferenceID(String.valueOf(otherBank.getId()));
	            return otherBankTrxValue;
	        }
	        catch (Exception ex) {
	            throw new TrxOperationException(ex);
	        }
	    }
	    
	   
	   protected IOtherBankBranchTrxValue prepareTrxValueBankBranch(IOtherBankBranchTrxValue otherBankBranchTrxValue) {
	        if (otherBankBranchTrxValue != null) {
	            IOtherBranch actual = otherBankBranchTrxValue.getOtherBranch();
	            IOtherBranch staging = otherBankBranchTrxValue.getStagingOtherBranch();
	            if (actual != null) {
	            	otherBankBranchTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            } else {
	            	otherBankBranchTrxValue.setReferenceID(null);
	            }
	            if (staging != null) {
	            	otherBankBranchTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
	            } else {
	            	otherBankBranchTrxValue.setStagingReferenceID(null);
	            }
	            return otherBankBranchTrxValue;
	        }else{
	        	throw new  OtherBranchException("ERROR-- Other Bank is null");
	        }
	    }
  
	   
	   protected IOtherBankBranchTrxValue createStagingFileId(IOtherBankBranchTrxValue otherBankTrxValue) throws TrxOperationException {
	        try {
	        	IFileMapperId fileMapperID = getStagingOtherBankBranchFileMapperIdBusManager().createFileIdBankBranch(otherBankTrxValue.getStagingFileMapperID());
	        	otherBankTrxValue.setStagingFileMapperID(fileMapperID);
	        	otherBankTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
	            return otherBankTrxValue;
	        }
	        catch (Exception ex) {
	            throw new TrxOperationException(ex);
	        }
	    }
	
	
}
