/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/stockindex/AbstractStockIndexTrxOperation.java,v 1.1 2003/08/18 10:13:16 btchng Exp $
 */
package com.integrosys.cms.app.feed.trx.stockindex;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedBusManager;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.bus.stockindex.StockIndexFeedGroupException;
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
 * @since $Date: 2003/08/18 10:13:16 $ Tag: $Name: $
 */
public abstract class AbstractStockIndexTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private IStockIndexFeedBusManager stockIndexFeedBusManager;

	private IStockIndexFeedBusManager stagingStockIndexFeedBusManager;

	/**
	 * @return stock index feed bus manager interfacing with actual table
	 */
	public IStockIndexFeedBusManager getStockIndexFeedBusManager() {
		return stockIndexFeedBusManager;
	}

	public void setStockIndexFeedBusManager(IStockIndexFeedBusManager stockIndexFeedBusManager) {
		this.stockIndexFeedBusManager = stockIndexFeedBusManager;
	}

	/**
	 * @return stock index feed bus manager interfacing with staging table
	 */
	public IStockIndexFeedBusManager getStagingStockIndexFeedBusManager() {
		return stagingStockIndexFeedBusManager;
	}

	public void setStagingStockIndexFeedBusManager(IStockIndexFeedBusManager stagingStockIndexFeedBusManager) {
		this.stagingStockIndexFeedBusManager = stagingStockIndexFeedBusManager;
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
	 * @param anIStockIndexFeedGroupTrxValue - IStockIndexFeedGroupTrxValue
	 * @return IStockIndexFeedGroupTrxValue - the trx object containing the
	 *         created staging document item
	 * @throws TrxOperationException if errors
	 */
	protected IStockIndexFeedGroupTrxValue createStagingStockIndexFeedGroup(
			IStockIndexFeedGroupTrxValue anIStockIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			IStockIndexFeedGroup stockIndexFeedGroup = getStagingStockIndexFeedBusManager().createStockIndexFeedGroup(
					anIStockIndexFeedGroupTrxValue.getStagingStockIndexFeedGroup());

			anIStockIndexFeedGroupTrxValue.setStagingStockIndexFeedGroup(stockIndexFeedGroup);
			anIStockIndexFeedGroupTrxValue.setStagingReferenceID(String.valueOf(stockIndexFeedGroup
					.getStockIndexFeedGroupID()));

			return anIStockIndexFeedGroupTrxValue;
		}
		catch (StockIndexFeedGroupException e) {
			throw new TrxOperationException(e.toString());
		}
	}

	/**
	 * Update a stockIndexFeedGroup transaction
	 * @param anIStockIndexFeedGroupTrxValue - ITrxValue
	 * @return IStockIndexFeedGroupTrxValue - the document item specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IStockIndexFeedGroupTrxValue updateStockIndexFeedGroupTransaction(
			IStockIndexFeedGroupTrxValue anIStockIndexFeedGroupTrxValue) throws TrxOperationException {
		try {
			anIStockIndexFeedGroupTrxValue = prepareTrxValue(anIStockIndexFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIStockIndexFeedGroupTrxValue's version time = "
					+ anIStockIndexFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIStockIndexFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBStockIndexFeedGroupTrxValue newValue = new OBStockIndexFeedGroupTrxValue(tempValue);
			newValue.setStockIndexFeedGroup(anIStockIndexFeedGroupTrxValue.getStockIndexFeedGroup());
			newValue.setStagingStockIndexFeedGroup(anIStockIndexFeedGroupTrxValue.getStagingStockIndexFeedGroup());

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
	 * @return IStockIndexFeedGroupTrxValue - the document item specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IStockIndexFeedGroupTrxValue getStockIndexFeedGroupTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IStockIndexFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBStockIndexFeedGroupTrxValue: "
					+ cex.toString());
		}
	}

	/**
	 * Prepares a trx object
	 */
	protected IStockIndexFeedGroupTrxValue prepareTrxValue(IStockIndexFeedGroupTrxValue anIStockIndexFeedGroupTrxValue) {
		if (anIStockIndexFeedGroupTrxValue != null) {
			IStockIndexFeedGroup actual = anIStockIndexFeedGroupTrxValue.getStockIndexFeedGroup();
			IStockIndexFeedGroup staging = anIStockIndexFeedGroupTrxValue.getStagingStockIndexFeedGroup();
			if (actual != null) {
				anIStockIndexFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getStockIndexFeedGroupID()));
			}
			else {
				anIStockIndexFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIStockIndexFeedGroupTrxValue
						.setStagingReferenceID(String.valueOf(staging.getStockIndexFeedGroupID()));
			}
			else {
				anIStockIndexFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIStockIndexFeedGroupTrxValue;
		}
		return null;
	}

	/**
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type IStockIndexFeedGroupTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IStockIndexFeedGroupTrxValue value) {
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
	protected IStockIndexFeedGroup mergeStockIndexFeedGroup(IStockIndexFeedGroup anOriginal, IStockIndexFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setStockIndexFeedGroupID(anOriginal.getStockIndexFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}

}