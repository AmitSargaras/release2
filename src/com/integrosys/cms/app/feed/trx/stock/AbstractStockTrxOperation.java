/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stock/AbstractStockTrxOperation.java,v 1.1 2003/08/07 08:36:38 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stock;

// java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedBusManager;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedBusManager;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockFeedGroupException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of document
 * item trx operations
 * 
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/07
 */
public abstract class AbstractStockTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private IStockFeedBusManager stockFeedBusManager;

	private IStockFeedBusManager stagingStockFeedBusManager;
	
	//Add By Govind S:File Upload
	private IStockFeedBusManager stockFeedFileMapperIdBusManager;
	
	private IStockFeedBusManager stagingStockFeedFileMapperIdBusManager;
	//****end Govind S

	public IStockFeedBusManager getStockFeedBusManager() {
		return stockFeedBusManager;
	}

	public void setStockFeedBusManager(IStockFeedBusManager stockFeedBusManager) {
		this.stockFeedBusManager = stockFeedBusManager;
	}

	public IStockFeedBusManager getStagingStockFeedBusManager() {
		return stagingStockFeedBusManager;
	}

	public void setStagingStockFeedBusManager(IStockFeedBusManager stagingStockFeedBusManager) {
		this.stagingStockFeedBusManager = stagingStockFeedBusManager;
	}
	/**
	 * @param stockFeedFileMapperIdBusManager the stockFeedFileMapperIdBusManager to set
	 */
	public void setStockFeedFileMapperIdBusManager(
			IStockFeedBusManager stockFeedFileMapperIdBusManager) {
		this.stockFeedFileMapperIdBusManager = stockFeedFileMapperIdBusManager;
	}

	/**
	 * @param stagingStockFeedFileMapperIdBusManager the stagingStockFeedFileMapperIdBusManager to set
	 */
	public void setStagingStockFeedFileMapperIdBusManager(
			IStockFeedBusManager stagingStockFeedFileMapperIdBusManager) {
		this.stagingStockFeedFileMapperIdBusManager = stagingStockFeedFileMapperIdBusManager;
	}

	/**
	 * @return the stockFeedFileMapperIdBusManager
	 */
	public IStockFeedBusManager getStockFeedFileMapperIdBusManager() {
		return stockFeedFileMapperIdBusManager;
	}

	/**
	 * @return the stagingStockFeedFileMapperIdBusManager
	 */
	public IStockFeedBusManager getStagingStockFeedFileMapperIdBusManager() {
		return stagingStockFeedFileMapperIdBusManager;
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
	 * @param anIStockFeedGroupTrxValue - IStockFeedGroupTrxValue
	 * @return IStockFeedGroupTrxValue - the trx object containing the created
	 *         staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IStockFeedGroupTrxValue createStagingStockFeedGroup(IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			IStockFeedGroup stockFeedGroup = getStagingStockFeedBusManager().createStockFeedGroup(
					anIStockFeedGroupTrxValue.getStagingStockFeedGroup());
			anIStockFeedGroupTrxValue.setStagingStockFeedGroup(stockFeedGroup);
			anIStockFeedGroupTrxValue.setStagingReferenceID(String.valueOf(stockFeedGroup.getStockFeedGroupID()));
			return anIStockFeedGroupTrxValue;
		}
		catch (StockFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a stockFeedGroup transaction
	 * @param anIStockFeedGroupTrxValue - ITrxValue
	 * @return IStockFeedGroupTrxValue - the document item specific transaction
	 *         object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IStockFeedGroupTrxValue updateStockFeedGroupTransaction(IStockFeedGroupTrxValue anIStockFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			anIStockFeedGroupTrxValue = prepareTrxValue(anIStockFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIStockFeedGroupTrxValue's version time = "
					+ anIStockFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIStockFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBStockFeedGroupTrxValue newValue = new OBStockFeedGroupTrxValue(tempValue);
			newValue.setStockFeedGroup(anIStockFeedGroupTrxValue.getStockFeedGroup());
			newValue.setStagingStockFeedGroup(anIStockFeedGroupTrxValue.getStagingStockFeedGroup());

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
	 * @return IStockFeedGroupTrxValue - the document item specific trx value
	 *         object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IStockFeedGroupTrxValue getStockFeedGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IStockFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBStockFeedGroupTrxValue: " + cex.toString());
		}
	}

	/**
	 * Prepares a trx object
	 */
	protected IStockFeedGroupTrxValue prepareTrxValue(IStockFeedGroupTrxValue anIStockFeedGroupTrxValue) {
		if (anIStockFeedGroupTrxValue != null) {
			IStockFeedGroup actual = anIStockFeedGroupTrxValue.getStockFeedGroup();
			IStockFeedGroup staging = anIStockFeedGroupTrxValue.getStagingStockFeedGroup();
			if (actual != null) {
				anIStockFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getStockFeedGroupID()));
			}
			else {
				anIStockFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIStockFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getStockFeedGroupID()));
			}
			else {
				anIStockFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIStockFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IStockFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IStockFeedGroupTrxValue value) {
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
	protected IStockFeedGroup mergeStockFeedGroup(IStockFeedGroup anOriginal, IStockFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setStockFeedGroupID(anOriginal.getStockFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

/*******File Upload**************/
	
	/**
     * 
     * @param stockFeedGroupTrxValue
     * @return IStockFeedTrxValue
     * @throws TrxOperationException
     */
    //------------------------------------File Insert---------------------------------------------
    
    protected IStockFeedGroupTrxValue prepareInsertTrxValue(IStockFeedGroupTrxValue stockFeedGroupTrxValue)throws TrxOperationException {
        if (stockFeedGroupTrxValue != null) {
            IFileMapperId actual = stockFeedGroupTrxValue.getFileMapperID();
            IFileMapperId staging = stockFeedGroupTrxValue.getStagingFileMapperID();
            if (actual != null) {
            	stockFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getId()));
            } else {
            	stockFeedGroupTrxValue.setReferenceID(null);
            }
            if (staging != null) {
            	stockFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getId()));
            } else {
            	stockFeedGroupTrxValue.setStagingReferenceID(null);
            }
            return stockFeedGroupTrxValue;
        }
        else{
        	throw new  TrxOperationException("ERROR-- StockFeed is null");
        }
    }
	
    
    
    protected IStockFeedGroupTrxValue updateMasterInsertTrx(IStockFeedGroupTrxValue stockFeedGroupTrxValue) throws TrxOperationException {
        try {
        	//due to staging ref id set null
        	//creditApprovalTrxValue = prepareInsertTrxValue(creditApprovalTrxValue);
            ICMSTrxValue tempValue = super.updateTransaction(stockFeedGroupTrxValue);
            OBStockFeedGroupTrxValue newValue = new OBStockFeedGroupTrxValue(tempValue);
            newValue.setFileMapperID(stockFeedGroupTrxValue.getFileMapperID());
            newValue.setStagingFileMapperID(stockFeedGroupTrxValue.getStagingFileMapperID());
            return newValue;
        }
        
        catch (TrxOperationException ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }
    
    
    
    
    protected IStockFeedGroupTrxValue createStagingFileId(IStockFeedGroupTrxValue stockFeedGroupTrxValue) throws TrxOperationException {
        try {
        	IFileMapperId fileMapperID = getStagingStockFeedFileMapperIdBusManager().createFileId(stockFeedGroupTrxValue.getStagingFileMapperID());
        	stockFeedGroupTrxValue.setStagingFileMapperID(fileMapperID);
        	stockFeedGroupTrxValue.setStagingReferenceID(String.valueOf(fileMapperID.getId()));
            return stockFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
    

    protected IStockFeedGroupTrxValue insertActualStockFeedGroup(IStockFeedGroupTrxValue stockFeedGroupTrxValue) throws TrxOperationException {
        try {
            IStockFeedGroup stockFeedGroup = getStagingStockFeedFileMapperIdBusManager().insertStockFeedEntry(stockFeedGroupTrxValue.getStagingStockFeedGroup());
            stockFeedGroupTrxValue.setStockFeedGroup(stockFeedGroup);
            stockFeedGroupTrxValue.setReferenceID(String.valueOf(stockFeedGroup.getStockFeedGroupID()));
            return stockFeedGroupTrxValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
	/***********End File Uploading**********************/
	



}