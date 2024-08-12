package com.integrosys.cms.app.feed.trx.gold;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.gold.GoldFeedGroupException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedBusManager;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractGoldTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private IGoldFeedBusManager goldFeedBusManager;

	private IGoldFeedBusManager stagingGoldFeedBusManager;

	/**
	 * @return the goldFeedBusManager
	 */
	public IGoldFeedBusManager getGoldFeedBusManager() {
		return goldFeedBusManager;
	}

	/**
	 * @param goldFeedBusManager the goldFeedBusManager to set
	 */
	public void setGoldFeedBusManager(IGoldFeedBusManager goldFeedBusManager) {
		this.goldFeedBusManager = goldFeedBusManager;
	}

	/**
	 * @return the stagingGoldFeedBusManager
	 */
	public IGoldFeedBusManager getStagingGoldFeedBusManager() {
		return stagingGoldFeedBusManager;
	}

	/**
	 * @param stagingGoldFeedBusManager the stagingGoldFeedBusManager to set
	 */
	public void setStagingGoldFeedBusManager(IGoldFeedBusManager stagingGoldFeedBusManager) {
		this.stagingGoldFeedBusManager = stagingGoldFeedBusManager;
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

	protected IGoldFeedGroupTrxValue createStagingGoldFeedGroup(IGoldFeedGroupTrxValue anIGoldFeedGroupTrxValue)
			throws TrxOperationException {

		try {
			IGoldFeedGroup goldFeedGroup = getStagingGoldFeedBusManager().createGoldFeedGroup(
					anIGoldFeedGroupTrxValue.getStagingGoldFeedGroup());
			anIGoldFeedGroupTrxValue.setStagingGoldFeedGroup(goldFeedGroup);
			anIGoldFeedGroupTrxValue.setStagingReferenceID(String.valueOf(goldFeedGroup.getGoldFeedGroupID()));
			return anIGoldFeedGroupTrxValue;
		}
		catch (GoldFeedGroupException e) {
			throw new TrxOperationException(e);
		}
	}

	protected IGoldFeedGroupTrxValue updateGoldFeedGroupTransaction(IGoldFeedGroupTrxValue anIGoldFeedGroupTrxValue)
			throws TrxOperationException {
		try {
			anIGoldFeedGroupTrxValue = prepareTrxValue(anIGoldFeedGroupTrxValue);

			DefaultLogger.debug(this, "anIGoldFeedGroupTrxValue's version time = "
					+ anIGoldFeedGroupTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anIGoldFeedGroupTrxValue);

			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBGoldFeedGroupTrxValue newValue = new OBGoldFeedGroupTrxValue(tempValue);
			newValue.setGoldFeedGroup(anIGoldFeedGroupTrxValue.getGoldFeedGroup());
			newValue.setStagingGoldFeedGroup(anIGoldFeedGroupTrxValue.getStagingGoldFeedGroup());

			DefaultLogger.debug(this, "newValue's version time = " + newValue.getVersionTime());

			return newValue;
		}
		catch (TransactionException tex) {
			throw new TrxOperationException(tex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("GeneralException : " + ex.toString());
		}
	}

	protected IGoldFeedGroupTrxValue getGoldFeedGroupTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IGoldFeedGroupTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBGoldFeedGroupTrxValue: " + cex.toString());
		}
	}

	protected IGoldFeedGroupTrxValue prepareTrxValue(IGoldFeedGroupTrxValue anIGoldFeedGroupTrxValue)
			throws TrxOperationException {
		if (anIGoldFeedGroupTrxValue != null) {
			IGoldFeedGroup actual = anIGoldFeedGroupTrxValue.getGoldFeedGroup();
			IGoldFeedGroup staging = anIGoldFeedGroupTrxValue.getStagingGoldFeedGroup();

			if (actual != null) {
				anIGoldFeedGroupTrxValue.setReferenceID(String.valueOf(actual.getGoldFeedGroupID()));
			}
			else {
				anIGoldFeedGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anIGoldFeedGroupTrxValue.setStagingReferenceID(String.valueOf(staging.getGoldFeedGroupID()));
			}
			else {
				anIGoldFeedGroupTrxValue.setStagingReferenceID(null);
			}
			return anIGoldFeedGroupTrxValue;
		}
		return null;
	}

	protected ITrxResult prepareResult(IGoldFeedGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected IGoldFeedGroup mergeGoldFeedGroup(IGoldFeedGroup anOriginal, IGoldFeedGroup aCopy)
			throws TrxOperationException {
		aCopy.setGoldFeedGroupID(anOriginal.getGoldFeedGroupID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}
}
