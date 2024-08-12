
package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalReplicationUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.creditApproval.bus.ICreditApprovalBusManager;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 1.1 $
 * @since $Date: 2011/04/06 09:45:08 $ Tag: $Name: $
 */
public abstract class AbstractCreditApprovalTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}
	
	
	public ICreditApprovalBusManager creditApprovalBusManager;
	
	public ICreditApprovalBusManager creditApprovalBusManagerStaging;
	
    private ICreditApprovalBusManager creditApprovalFileMapperIdBusManager;
	
    private ICreditApprovalBusManager stagingCreditApprovalFileMapperIdBusManager;
    





	/**
	 * @return the creditApprovalBusManager
	 */
	public ICreditApprovalBusManager getCreditApprovalBusManager() {
		return creditApprovalBusManager;
	}
	/**
	 * @param creditApprovalBusManager the creditApprovalBusManager to set
	 */
	public void setCreditApprovalBusManager(
			ICreditApprovalBusManager creditApprovalBusManager) {
		this.creditApprovalBusManager = creditApprovalBusManager;
	}
	/**
	 * @return the creditApprovalBusManagerStaging
	 */
	public ICreditApprovalBusManager getCreditApprovalBusManagerStaging() {
		return creditApprovalBusManagerStaging;
	}
	/**
	 * @param creditApprovalBusManagerStaging the creditApprovalBusManagerStaging to set
	 */
	public void setCreditApprovalBusManagerStaging(
			ICreditApprovalBusManager creditApprovalBusManagerStaging) {
		this.creditApprovalBusManagerStaging = creditApprovalBusManagerStaging;
	}
	/**
	 * Create the staging document item doc
	 * @param anICreditApprovalTrxValue - ICreditApprovalTrxValue
	 * @return ICreditApprovalTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected ICreditApprovalTrxValue createStagingCreditApproval(ICreditApprovalTrxValue anICreditApprovalTrxValue)
	throws TrxOperationException {
		try {
			ICreditApproval creditApproval = getCreditApprovalBusManagerStaging().createCreditApproval(
			anICreditApprovalTrxValue.getStagingCreditApproval());
			anICreditApprovalTrxValue.setStagingCreditApproval(creditApproval);
			anICreditApprovalTrxValue.setStagingReferenceID(String.valueOf(creditApproval.getId()));
			return anICreditApprovalTrxValue;
		}
		catch (CreditApprovalException e) {
	throw new TrxOperationException(e.toString());
		}
	}
	/**
	 * Update a CreditApprovalFeedGroup transaction
	 * @param anICreditApprovalTrxValue - ITrxValue
	 * @return ICreditApprovalTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected ICreditApprovalTrxValue updateCreditApprovalTransaction(ICreditApprovalTrxValue anICreditApprovalTrxValue)
			throws TrxOperationException {
		try {
			anICreditApprovalTrxValue = prepareTrxValue(anICreditApprovalTrxValue);

			DefaultLogger.debug(this, "anICreditApprovalTrxValue's version time = "
					+ anICreditApprovalTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anICreditApprovalTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBCreditApprovalTrxValue newValue = new OBCreditApprovalTrxValue(tempValue);
			newValue.setCreditApproval(anICreditApprovalTrxValue.getCreditApproval());
			newValue.setStagingCreditApproval(anICreditApprovalTrxValue.getStagingCreditApproval());

			DefaultLogger.debug(this, "newValue's version time = " + newValue.getVersionTime());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex.getMessage());
		}
		catch (Exception ex) {
			ex.printStackTrace();
			 DefaultLogger.debug(this, "General Exception:  " + ex.getMessage());
			throw new TrxOperationException("General Exception: ");
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return ICreditApprovalTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected ICreditApprovalTrxValue getCreditApprovalTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ICreditApprovalTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			 cex.printStackTrace();
			 DefaultLogger.debug(this, "The ITrxValue is not of type OBCreditApprovalTrxValue: " + cex.getMessage());
			throw new TrxOperationException("The ITrxValue is not of type OBCreditApprovalTrxValue: ");
		}
	}

	protected ICreditApprovalBusManager getStagingCreditApprovalFeedBusManager() {
		return getCreditApprovalBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected ICreditApprovalTrxValue prepareTrxValue(ICreditApprovalTrxValue anICreditApprovalTrxValue) {
		if (anICreditApprovalTrxValue != null) {
			ICreditApproval actual = anICreditApprovalTrxValue.getCreditApproval();
			ICreditApproval staging = anICreditApprovalTrxValue.getStagingCreditApproval();
			if (actual != null) {
				anICreditApprovalTrxValue.setReferenceID(String.valueOf(actual.getId()));
			}
			else {
				anICreditApprovalTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anICreditApprovalTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
			}
			else {
				anICreditApprovalTrxValue.setStagingReferenceID(null);
			}
			return anICreditApprovalTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type anICreditApprovalTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(ICreditApprovalTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IImageUploadTrxValue
	 * @return collateral transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICreditApprovalTrxValue createTransaction(ICreditApprovalTrxValue value) throws TrxOperationException {
		//due to staging ref id set null
		//value = prepareTrxValue(value);
		ICMSTrxValue tempValue = super.createTransaction(value);
		OBCreditApprovalTrxValue newValue = new OBCreditApprovalTrxValue(tempValue);
		newValue.setCreditApproval(value.getCreditApproval());
		newValue.setStagingCreditApproval(value.getStagingCreditApproval());
		return newValue;
	}
	
	/**
	 * Create actual Image Upload record.
	 * 
	 * @param value is of type ICreditApprovalTrxValue
	 * @return CreditApproval transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICreditApprovalTrxValue createActualCreditApproval(ICreditApprovalTrxValue trxValue) throws TrxOperationException {
		try {
			ICreditApproval staging = trxValue.getStagingCreditApproval();
			ICreditApproval replicated = trxValue.getStagingCreditApproval();
			replicated = CreditApprovalReplicationUtils.replicateCreditApprovalForCreateStagingCopy(staging);
			replicated = (ICreditApproval) getCreditApprovalBusManager().createCreditApproval(replicated);
	         trxValue.setStagingCreditApproval(staging);	
	         trxValue.setCreditApproval(replicated);			
		}
		catch (CreditApprovalException e) {
			  e.printStackTrace();
			  DefaultLogger.debug(this, "Failed to create Actual Credit Approval using staging Credit Approval" + e);
			  throw new CreditApprovalException(
					"Failed to create Actual Credit Approval using staging Credit Approval");
		}
		return trxValue;
	}
	
	
	/**
	 * Create actual CreditApproval.
	 * Update actual table 
	 * @param value is of type ICreditApprovalTrxValue
	 * @return CreditApproval transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICreditApprovalTrxValue updateStatusCreditApprovalFeedEntry(ICreditApprovalTrxValue trxValue) throws TrxOperationException {
		try {
			ICreditApproval actual = trxValue.getCreditApproval();
			actual = (ICreditApproval) getCreditApprovalBusManager().updateStatusCreditApproval(actual);
	         trxValue.setCreditApproval(actual);
	         return trxValue;
		}
		catch (CreditApprovalException e) {
			  e.printStackTrace();
			  DefaultLogger.debug(this, "Failed to create Actual Credit Approval using staging Credit Approval" + e);
			  throw new CreditApprovalException(
					"Failed to create Actual Credit Approval using staging Credit Approval");
		}
		
	}
	
	/*******File Upload**************/
	
	/**
     * 
     * @param creditApprovalTrxValue
     * @return ICreditApprovalTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected ICreditApprovalTrxValue prepareInsertTrxValue(ICreditApprovalTrxValue creditApprovalTrxValue)throws TrxOperationException {
        if (creditApprovalTrxValue != null) {
            IFileMapperId actual = creditApprovalTrxValue.getFileMapperID();
            IFileMapperId staging = creditApprovalTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	creditApprovalTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	creditApprovalTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	creditApprovalTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	creditApprovalTrxValue.setStagingReferenceID(null);
            }
            return creditApprovalTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- CreditApproval is null");
        }
    }
	
    
    
    protected ICreditApprovalTrxValue updateMasterInsertTrx(ICreditApprovalTrxValue creditApprovalTrxValue) throws TrxOperationException {
        try {
        	//due to staging ref id set null
        	//creditApprovalTrxValue = prepareInsertTrxValue(creditApprovalTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(creditApprovalTrxValue);
            OBCreditApprovalTrxValue newValue = new OBCreditApprovalTrxValue(tempValue);
            newValue.setFileMapperID(creditApprovalTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(creditApprovalTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected ICreditApprovalTrxValue createStagingFileId(ICreditApprovalTrxValue creditApprovalTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingCreditApprovalFileMapperIdBusManager().createFileId(creditApprovalTrxValue.getStagingFileMapperID());
        	creditApprovalTrxValue.setStagingFileMapperID(fileMapperID);
        	creditApprovalTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return creditApprovalTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected ICreditApprovalTrxValue insertActualCreditApproval(ICreditApprovalTrxValue creditApprovalTrxValue) throws TrxOperationException {
        try {
            ICreditApproval creditApproval = getStagingCreditApprovalFileMapperIdBusManager().insertCreditApproval(creditApprovalTrxValue.getStagingCreditApproval());
            creditApprovalTrxValue.setCreditApproval(creditApproval);
            creditApprovalTrxValue.setReferenceID(String.valueOf(creditApproval.getId()));
            return creditApprovalTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	/***********End File Uploading**********************/
	/**
	 * @return the creditApprovalFileMapperIdBusManager
	 */
	public ICreditApprovalBusManager getCreditApprovalFileMapperIdBusManager() {
		return creditApprovalFileMapperIdBusManager;
	}
	/**
	 * @param creditApprovalFileMapperIdBusManager the creditApprovalFileMapperIdBusManager to set
	 */
	public void setCreditApprovalFileMapperIdBusManager(
			ICreditApprovalBusManager creditApprovalFileMapperIdBusManager) {
		this.creditApprovalFileMapperIdBusManager = creditApprovalFileMapperIdBusManager;
	}
	/**
	 * @return the stagingCreditApprovalFileMapperIdBusManager
	 */
	public ICreditApprovalBusManager getStagingCreditApprovalFileMapperIdBusManager() {
		return stagingCreditApprovalFileMapperIdBusManager;
	}
	/**
	 * @param stagingCreditApprovalFileMapperIdBusManager the stagingCreditApprovalFileMapperIdBusManager to set
	 */
	public void setStagingCreditApprovalFileMapperIdBusManager(
			ICreditApprovalBusManager stagingCreditApprovalFileMapperIdBusManager) {
		this.stagingCreditApprovalFileMapperIdBusManager = stagingCreditApprovalFileMapperIdBusManager;
	}
	
	
	
	
	
	


}