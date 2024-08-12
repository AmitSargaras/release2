/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/bond/AbstractBondTrxOperation.java,v 1.1 2003/08/06 08:10:08 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.bond;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.bond.BondFeedGroupException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedBusManager;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
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
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/06 08:10:08 $ Tag: $Name: $
 */
public abstract class AbstractBondTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	
	
	public IBondFeedBusManager bondFeedBusManager;
	
	public IBondFeedBusManager bondFeedBusManagerStaging;
	
	//Add By Govind S:File Upload
	private IBondFeedBusManager bondFeedFileMapperIdBusManager;
	
	private IBondFeedBusManager stagingBondFeedFileMapperIdBusManager;
	//****end Govind S



	public IBondFeedBusManager getBondFeedBusManager() {
		return bondFeedBusManager;
	}

	public void setBondFeedBusManager(IBondFeedBusManager bondFeedBusManager) {
		this.bondFeedBusManager = bondFeedBusManager;
	}

	public IBondFeedBusManager getBondFeedBusManagerStaging() {
		return bondFeedBusManagerStaging;
	}

	public void setBondFeedBusManagerStaging(IBondFeedBusManager bondFeedBusManagerStaging) {
		this.bondFeedBusManagerStaging = bondFeedBusManagerStaging;
	}

	/**
	 * Create the staging document item doc
	 * @param anIBondFeedGroupTrxValue - IBondFeedGroupTrxValue
	 * @return IBondFeedGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IBondFeedGroupTrxValue createStagingBondFeedGroup(IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IBondFeedGroup bondFeedGroup = getBondFeedBusManagerStaging().createBondFeedGroup(
					anIBondFeedGroupTrxValue.getStagingBondFeedGroup());
			anIBondFeedGroupTrxValue.setStagingBondFeedGroup(bondFeedGroup);
			anIBondFeedGroupTrxValue.setStagingReferenceID(String.valueOf(bondFeedGroup.getBondFeedGroupID()));
			return anIBondFeedGroupTrxValue;
		}
		catch (BondFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a bondFeedGroup transaction
	 * @param anIBondFeedGroupTrxValue - ITrxValue
	 * @return IBondFeedGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IBondFeedGroupTrxValue updateBondFeedGroupTransaction(IBondFeedGroupTrxValue anIBondFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			anIBondFeedGroupTrxValue = prepareTrxValue(anIBondFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIBondFeedGroupTrxValue's version time = "
					+ anIBondFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIBondFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBBondFeedGroupTrxValue newValue = new OBBondFeedGroupTrxValue(tempValue);
			newValue.setBondFeedGroup(anIBondFeedGroupTrxValue.getBondFeedGroup());
			newValue.setStagingBondFeedGroup(anIBondFeedGroupTrxValue.getStagingBondFeedGroup());

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
	 * @return IBondFeedGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IBondFeedGroupTrxValue getBondFeedGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IBondFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBBondFeedGroupTrxValue: " + cex.toString());
		}
	}

	protected IBondFeedBusManager getStagingBondFeedBusManager() {
		return getBondFeedBusManagerStaging();
	}


	/**
	 * Prepares a trx object
	 */
	protected IBondFeedGroupTrxValue prepareTrxValue(IBondFeedGroupTrxValue anIBondFeedGroupTrxValue) {
		if (anIBondFeedGroupTrxValue != null) {
			IBondFeedGroup actual = anIBondFeedGroupTrxValue.getBondFeedGroup();
			IBondFeedGroup staging = anIBondFeedGroupTrxValue.getStagingBondFeedGroup();
			if (actual != null) {
				anIBondFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getBondFeedGroupID()));
			}
			else {
				anIBondFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIBondFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getBondFeedGroupID()));
			}
			else {
				anIBondFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIBondFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IBondFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IBondFeedGroupTrxValue value) {
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
	protected IBondFeedGroup mergeBondFeedGroup(IBondFeedGroup anOriginal, IBondFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setBondFeedGroupID(anOriginal.getBondFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}
	
	
/*******File Upload**************/
	
	/**
     * 
     * @param bondFeedGroupTrxValue
     * @return IBondFeedTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IBondFeedGroupTrxValue prepareInsertTrxValue(IBondFeedGroupTrxValue bondFeedGroupTrxValue)throws TrxOperationException {
        if (bondFeedGroupTrxValue != null) {
            IFileMapperId actual = bondFeedGroupTrxValue.getFileMapperID();
            IFileMapperId staging = bondFeedGroupTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	bondFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	bondFeedGroupTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	bondFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	bondFeedGroupTrxValue.setStagingReferenceID(null);
            }
            return bondFeedGroupTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- BondFeed is null");
        }
    }
	
    
    
    protected IBondFeedGroupTrxValue updateMasterInsertTrx(IBondFeedGroupTrxValue bondFeedGroupTrxValue) throws TrxOperationException {
        try {
        	//due to staging ref id set null
        	//creditApprovalTrxValue = prepareInsertTrxValue(creditApprovalTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(bondFeedGroupTrxValue);
            OBBondFeedGroupTrxValue newValue = new OBBondFeedGroupTrxValue(tempValue);
            newValue.setFileMapperID(bondFeedGroupTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(bondFeedGroupTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IBondFeedGroupTrxValue createStagingFileId(IBondFeedGroupTrxValue bondFeedGroupTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingBondFeedFileMapperIdBusManager().createFileId(bondFeedGroupTrxValue.getStagingFileMapperID());
        	bondFeedGroupTrxValue.setStagingFileMapperID(fileMapperID);
        	bondFeedGroupTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return bondFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IBondFeedGroupTrxValue insertActualBondFeedGroup(IBondFeedGroupTrxValue bondFeedGroupTrxValue) throws TrxOperationException {
        try {
            IBondFeedGroup bondFeedGroup = getStagingBondFeedFileMapperIdBusManager().insertBondFeedEntry(bondFeedGroupTrxValue.getStagingBondFeedGroup());
            bondFeedGroupTrxValue.setBondFeedGroup(bondFeedGroup);
            bondFeedGroupTrxValue.setReferenceID(String.valueOf(bondFeedGroup.getBondFeedGroupID()));
            return bondFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	/***********End File Uploading**********************/

	/**
	 * @return the bondFeedFileMapperIdBusManager
	 */
	public IBondFeedBusManager getBondFeedFileMapperIdBusManager() {
		return bondFeedFileMapperIdBusManager;
	}

	/**
	 * @param bondFeedFileMapperIdBusManager the bondFeedFileMapperIdBusManager to set
	 */
	public void setBondFeedFileMapperIdBusManager(
			IBondFeedBusManager bondFeedFileMapperIdBusManager) {
		this.bondFeedFileMapperIdBusManager = bondFeedFileMapperIdBusManager;
	}

	/**
	 * @return the stagingBondFeedFileMapperIdBusManager
	 */
	public IBondFeedBusManager getStagingBondFeedFileMapperIdBusManager() {
		return stagingBondFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingBondFeedFileMapperIdBusManager the stagingBondFeedFileMapperIdBusManager to set
	 */
	public void setStagingBondFeedFileMapperIdBusManager(
			IBondFeedBusManager stagingBondFeedFileMapperIdBusManager) {
		this.stagingBondFeedFileMapperIdBusManager = stagingBondFeedFileMapperIdBusManager;
	}


}