/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/AbstractForexTrxOperation.java,v 1.8 2003/08/11 06:36:51 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.forex;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedBusManager;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.8 $
 * @since $Date: 2003/08/11 06:36:51 $ Tag: $Name: $
 */
public abstract class AbstractForexTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private IForexFeedBusManager forexFeedBusManager;
	
	private IForexFeedBusManager stagingForexFeedBusManager;
	
	private IForexFeedBusManager forexFeedFileMapperIdBusManager;
		
	private IForexFeedBusManager stagingForexFeedFileMapperIdBusManager;
	    
	
	/**
	 * @return the forexFeedBusManager
	 */
	public IForexFeedBusManager getForexFeedBusManager() {
		return forexFeedBusManager;
	}

	/**
	 * @param forexFeedBusManager the forexFeedBusManager to set
	 */
	public void setForexFeedBusManager(IForexFeedBusManager forexFeedBusManager) {
		this.forexFeedBusManager = forexFeedBusManager;
	}

	/**
	 * @return the stagingForexFeedBusManager
	 */
	public IForexFeedBusManager getStagingForexFeedBusManager() {
		return stagingForexFeedBusManager;
	}

	/**
	 * @param stagingForexFeedBusManager the stagingForexFeedBusManager to set
	 */
	public void setStagingForexFeedBusManager(
			IForexFeedBusManager stagingForexFeedBusManager) {
		this.stagingForexFeedBusManager = stagingForexFeedBusManager;
	}

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

	/**
	 * Create the staging document item doc
	 * @param anIForexFeedGroupTrxValue - IForexFeedGroupTrxValue
	 * @return IForexFeedGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IForexFeedGroupTrxValue createStagingForexFeedGroup(IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IForexFeedGroup forexFeedGroup = getStagingForexFeedBusManager().createForexFeedGroup(
					anIForexFeedGroupTrxValue.getStagingForexFeedGroup());
			anIForexFeedGroupTrxValue.setStagingForexFeedGroup(forexFeedGroup);
			anIForexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(forexFeedGroup.getForexFeedGroupID()));
			return anIForexFeedGroupTrxValue;
		}
		catch (ForexFeedGroupException e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Update a forexFeedGroup transaction
	 * @param anIForexFeedGroupTrxValue - ITrxValue
	 * @return IForexFeedGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IForexFeedGroupTrxValue updateForexFeedGroupTransaction(IForexFeedGroupTrxValue anIForexFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			anIForexFeedGroupTrxValue = prepareTrxValue(anIForexFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIForexFeedGroupTrxValue's version time = "
					+ anIForexFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIForexFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBForexFeedGroupTrxValue newValue = new OBForexFeedGroupTrxValue(tempValue);
			newValue.setForexFeedGroup(anIForexFeedGroupTrxValue.getForexFeedGroup());
			newValue.setStagingForexFeedGroup(anIForexFeedGroupTrxValue.getStagingForexFeedGroup());

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
	 * @return IForexFeedGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IForexFeedGroupTrxValue getForexFeedGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IForexFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBForexFeedGroupTrxValue: " + cex.toString());
		}
	}

	/**
	 * Prepares a trx object
	 */
	protected IForexFeedGroupTrxValue prepareTrxValue(IForexFeedGroupTrxValue anIForexFeedGroupTrxValue) {
		if (anIForexFeedGroupTrxValue != null) {
			IForexFeedGroup actual = anIForexFeedGroupTrxValue.getForexFeedGroup();
			IForexFeedGroup staging = anIForexFeedGroupTrxValue.getStagingForexFeedGroup();
			if (actual != null) {
				anIForexFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getForexFeedGroupID()));
			}
			else {
				anIForexFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIForexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getForexFeedGroupID()));
			}
			else {
				anIForexFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIForexFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IForexFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IForexFeedGroupTrxValue value) {
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
	protected IForexFeedGroup mergeForexFeedGroup(IForexFeedGroup anOriginal, IForexFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setForexFeedGroupID(anOriginal.getForexFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}
	
/*******File Upload**************/
	
	/**
     * 
     * @param forexFeedGroupTrxValue
     * @return IForexFeedTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IForexFeedGroupTrxValue prepareInsertTrxValue(IForexFeedGroupTrxValue forexFeedGroupTrxValue)throws TrxOperationException {
        if (forexFeedGroupTrxValue != null) {
            IFileMapperId actual = forexFeedGroupTrxValue.getFileMapperID();
            IFileMapperId staging = forexFeedGroupTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	forexFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	forexFeedGroupTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	forexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	forexFeedGroupTrxValue.setStagingReferenceID(null);
            }
            return forexFeedGroupTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- ForexFeed is null");
        }
    }
	
    
    
    protected IForexFeedGroupTrxValue updateMasterInsertTrx(IForexFeedGroupTrxValue forexFeedGroupTrxValue) throws TrxOperationException {
        try {
        	//due to staging ref id set null
        	//creditApprovalTrxValue = prepareInsertTrxValue(creditApprovalTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(forexFeedGroupTrxValue);
            OBForexFeedGroupTrxValue newValue = new OBForexFeedGroupTrxValue(tempValue);
            newValue.setFileMapperID(forexFeedGroupTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(forexFeedGroupTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IForexFeedGroupTrxValue createStagingFileId(IForexFeedGroupTrxValue forexFeedGroupTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingForexFeedFileMapperIdBusManager().createFileId(forexFeedGroupTrxValue.getStagingFileMapperID());
        	forexFeedGroupTrxValue.setStagingFileMapperID(fileMapperID);
        	forexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return forexFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IForexFeedGroupTrxValue insertActualForexFeedGroup(IForexFeedGroupTrxValue forexFeedGroupTrxValue) throws TrxOperationException {
        try {
            IForexFeedGroup forexFeedGroup = getStagingForexFeedFileMapperIdBusManager().insertForexFeedEntry(forexFeedGroupTrxValue.getStagingForexFeedGroup());
            forexFeedGroupTrxValue.setForexFeedGroup(forexFeedGroup);
            forexFeedGroupTrxValue.setReferenceID(String.valueOf(forexFeedGroup.getForexFeedGroupID()));
            return forexFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	/***********End File Uploading**********************/

	/**
	 * @return the forexFeedFileMapperIdBusManager
	 */
	public IForexFeedBusManager getForexFeedFileMapperIdBusManager() {
		return forexFeedFileMapperIdBusManager;
	}

	/**
	 * @param forexFeedFileMapperIdBusManager the forexFeedFileMapperIdBusManager to set
	 */
	public void setForexFeedFileMapperIdBusManager(
			IForexFeedBusManager forexFeedFileMapperIdBusManager) {
		this.forexFeedFileMapperIdBusManager = forexFeedFileMapperIdBusManager;
	}

	/**
	 * @return the stagingForexFeedFileMapperIdBusManager
	 */
	public IForexFeedBusManager getStagingForexFeedFileMapperIdBusManager() {
		return stagingForexFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingForexFeedFileMapperIdBusManager the stagingForexFeedFileMapperIdBusManager to set
	 */
	public void setStagingForexFeedFileMapperIdBusManager(
			IForexFeedBusManager stagingForexFeedFileMapperIdBusManager) {
		this.stagingForexFeedFileMapperIdBusManager = stagingForexFeedFileMapperIdBusManager;
	}

}