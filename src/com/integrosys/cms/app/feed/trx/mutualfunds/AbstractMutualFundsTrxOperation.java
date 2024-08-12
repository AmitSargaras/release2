/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/AbstractBondTrxOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedBusManager;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsFeedGroupException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: Govind S $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public abstract class AbstractMutualFundsTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	
	
	public IMutualFundsFeedBusManager mutualFundsFeedBusManager;
	
	public IMutualFundsFeedBusManager mutualFundsFeedBusManagerStaging;
	//Add By Govind S:File Upload
	private IMutualFundsFeedBusManager mutualfundsFeedFileMapperIdBusManager;
	
	private IMutualFundsFeedBusManager stagingMutualFundsFeedFileMapperIdBusManager;
	//****end Govind S


	public IMutualFundsFeedBusManager getMutualFundsFeedBusManager() {
		return mutualFundsFeedBusManager;
	}

	public void setMutualFundsFeedBusManager(IMutualFundsFeedBusManager mutualFundsFeedBusManager) {
		this.mutualFundsFeedBusManager = mutualFundsFeedBusManager;
	}

	public IMutualFundsFeedBusManager getMutualFundsFeedBusManagerStaging() {
		return mutualFundsFeedBusManagerStaging;
	}

	public void setMutualFundsFeedBusManagerStaging(IMutualFundsFeedBusManager mutualFundsFeedBusManagerStaging) {
		this.mutualFundsFeedBusManagerStaging = mutualFundsFeedBusManagerStaging;
	}

	/**
	 * @return the mutualfundsFeedFileMapperIdBusManager
	 */
	public IMutualFundsFeedBusManager getMutualfundsFeedFileMapperIdBusManager() {
		return mutualfundsFeedFileMapperIdBusManager;
	}

	/**
	 * @param mutualfundsFeedFileMapperIdBusManager the mutualfundsFeedFileMapperIdBusManager to set
	 */
	public void setMutualfundsFeedFileMapperIdBusManager(
			IMutualFundsFeedBusManager mutualfundsFeedFileMapperIdBusManager) {
		this.mutualfundsFeedFileMapperIdBusManager = mutualfundsFeedFileMapperIdBusManager;
	}

	/**
	 * @return the stagingMutualFundsFeedFileMapperIdBusManager
	 */
	public IMutualFundsFeedBusManager getStagingMutualFundsFeedFileMapperIdBusManager() {
		return stagingMutualFundsFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingMutualFundsFeedFileMapperIdBusManager the stagingMutualFundsFeedFileMapperIdBusManager to set
	 */
	public void setStagingMutualFundsFeedFileMapperIdBusManager(
			IMutualFundsFeedBusManager stagingMutualFundsFeedFileMapperIdBusManager) {
		this.stagingMutualFundsFeedFileMapperIdBusManager = stagingMutualFundsFeedFileMapperIdBusManager;
	}


	/**
	 * Create the staging document item doc
	 * @param anIMutualFundsFeedGroupTrxValue - IMutualFundsFeedGroupTrxValue
	 * @return IMutualFundsFeedGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IMutualFundsFeedGroupTrxValue createStagingMutualFundsFeedGroup(IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IMutualFundsFeedGroup mutualFundsFeedGroup = getMutualFundsFeedBusManagerStaging().createMutualFundsFeedGroup(
					anIMutualFundsFeedGroupTrxValue.getStagingMutualFundsFeedGroup());
			anIMutualFundsFeedGroupTrxValue.setStagingMutualFundsFeedGroup(mutualFundsFeedGroup);
			anIMutualFundsFeedGroupTrxValue.setStagingReferenceID(String.valueOf(mutualFundsFeedGroup.getMutualFundsFeedGroupID()));
			return anIMutualFundsFeedGroupTrxValue;
		}
		catch (MutualFundsFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a mutualFundsFeedGroup transaction
	 * @param anIMutualFundsFeedGroupTrxValue - ITrxValue
	 * @return IMutualFundsFeedGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IMutualFundsFeedGroupTrxValue updateMutualFundsFeedGroupTransaction(IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			anIMutualFundsFeedGroupTrxValue = prepareTrxValue(anIMutualFundsFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIMutualFundsFeedGroupTrxValue's version time = "
					+ anIMutualFundsFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIMutualFundsFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBMutualFundsFeedGroupTrxValue newValue = new OBMutualFundsFeedGroupTrxValue(tempValue);
			newValue.setMutualFundsFeedGroup(anIMutualFundsFeedGroupTrxValue.getMutualFundsFeedGroup());
			newValue.setStagingMutualFundsFeedGroup(anIMutualFundsFeedGroupTrxValue.getStagingMutualFundsFeedGroup());

			DefaultLogger.debug(this, "newValue's version time = " + newValue.getVersionTime());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue - ITrxValue
	 * @return IMutualFundsFeedGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IMutualFundsFeedGroupTrxValue getMutualFundsFeedGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IMutualFundsFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBMutualFundsFeedGroupTrxValue: " + cex.toString());
		}
	}

	protected IMutualFundsFeedBusManager getStagingMutualFundsFeedBusManager() {
		return getMutualFundsFeedBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected IMutualFundsFeedGroupTrxValue prepareTrxValue(IMutualFundsFeedGroupTrxValue anIMutualFundsFeedGroupTrxValue) {
		if (anIMutualFundsFeedGroupTrxValue != null) {
			IMutualFundsFeedGroup actual = anIMutualFundsFeedGroupTrxValue.getMutualFundsFeedGroup();
			IMutualFundsFeedGroup staging = anIMutualFundsFeedGroupTrxValue.getStagingMutualFundsFeedGroup();
			if (actual != null) {
				anIMutualFundsFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getMutualFundsFeedGroupID()));
			}
			else {
				anIMutualFundsFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIMutualFundsFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getMutualFundsFeedGroupID()));
			}
			else {
				anIMutualFundsFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIMutualFundsFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IMutualFundsFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IMutualFundsFeedGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * This method set the primary key from the original to the copied checklist
	 * objects. It is required for the case of updating staging from actual and
	 * vice versa as there is a need to perform a deep clone of the object and
	 * set the required attribute in the object to the original one so that a
	 * proper update can be done.
	 * @param anOriginal
	 * @param aCopy - ICheckList
	 * @return ICheckList - the copied object with required attributes from the
	 *         original checklist
	 * @throws TrxOperationException on errors
	 */
	protected IMutualFundsFeedGroup mergeMutualFundsFeedGroup(IMutualFundsFeedGroup anOriginal, IMutualFundsFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setMutualFundsFeedGroupID(anOriginal.getMutualFundsFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}
	
	/**
     * 
     * @param mutualfundsFeedGroupTrxValue
     * @return IMutualFundsFeedTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IMutualFundsFeedGroupTrxValue prepareInsertTrxValue(IMutualFundsFeedGroupTrxValue mutualfundsFeedGroupTrxValue)throws TrxOperationException {
        if (mutualfundsFeedGroupTrxValue != null) {
            IFileMapperId actual = mutualfundsFeedGroupTrxValue.getFileMapperID();
            IFileMapperId staging = mutualfundsFeedGroupTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	mutualfundsFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	mutualfundsFeedGroupTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	mutualfundsFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	mutualfundsFeedGroupTrxValue.setStagingReferenceID(null);
            }
            return mutualfundsFeedGroupTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- MutualFundsFeed is null");
        }
    }
	
    
    
    protected IMutualFundsFeedGroupTrxValue updateMasterInsertTrx(IMutualFundsFeedGroupTrxValue mutualfundsFeedGroupTrxValue) throws TrxOperationException {
        try {
        	//due to staging ref id set null
        	//creditApprovalTrxValue = prepareInsertTrxValue(creditApprovalTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(mutualfundsFeedGroupTrxValue);
            OBMutualFundsFeedGroupTrxValue newValue = new OBMutualFundsFeedGroupTrxValue(tempValue);
            newValue.setFileMapperID(mutualfundsFeedGroupTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(mutualfundsFeedGroupTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IMutualFundsFeedGroupTrxValue createStagingFileId(IMutualFundsFeedGroupTrxValue mutualfundsFeedGroupTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingMutualFundsFeedFileMapperIdBusManager().createFileId(mutualfundsFeedGroupTrxValue.getStagingFileMapperID());
        	mutualfundsFeedGroupTrxValue.setStagingFileMapperID(fileMapperID);
        	mutualfundsFeedGroupTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return mutualfundsFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IMutualFundsFeedGroupTrxValue insertActualMutualFundsFeedGroup(IMutualFundsFeedGroupTrxValue mutualfundsFeedGroupTrxValue) throws TrxOperationException {
        try {
            IMutualFundsFeedGroup mutualfundsFeedGroup = getStagingMutualFundsFeedFileMapperIdBusManager().insertMutualFundsFeedEntry(mutualfundsFeedGroupTrxValue.getStagingMutualFundsFeedGroup());
            mutualfundsFeedGroupTrxValue.setMutualFundsFeedGroup(mutualfundsFeedGroup);
            mutualfundsFeedGroupTrxValue.setReferenceID(String.valueOf(mutualfundsFeedGroup.getMutualFundsFeedGroupID()));
            return mutualfundsFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	/***********End File Uploading**********************/
	

}