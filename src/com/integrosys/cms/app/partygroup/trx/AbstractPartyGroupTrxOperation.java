package com.integrosys.cms.app.partygroup.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupBusManager;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * @author Bharat Waghela Abstract Party Group Operation
 */

public abstract class AbstractPartyGroupTrxOperation extends CMSTrxOperation
		implements ITrxRouteOperation {

	private IPartyGroupBusManager partyGroupBusManager;

	private IPartyGroupBusManager stagingPartyGroupBusManager;

	public IPartyGroupBusManager getPartyGroupBusManager() {
		return partyGroupBusManager;
	}

	public void setPartyGroupBusManager(
			IPartyGroupBusManager partyGroupBusManager) {
		this.partyGroupBusManager = partyGroupBusManager;
	}

	public IPartyGroupBusManager getStagingPartyGroupBusManager() {
		return stagingPartyGroupBusManager;
	}

	public void setStagingPartyGroupBusManager(
			IPartyGroupBusManager stagingPartyGroupBusManager) {
		this.stagingPartyGroupBusManager = stagingPartyGroupBusManager;
	}

	/**
	 * 
	 * @param partyGroupTrxValue
	 * @return IPartyGroupTrxValue
	 */

	protected IPartyGroupTrxValue prepareTrxValue(
			IPartyGroupTrxValue partyGroupTrxValue) {
		if (partyGroupTrxValue != null) {
			IPartyGroup actual = partyGroupTrxValue.getPartyGroup();
			IPartyGroup staging = partyGroupTrxValue.getStagingPartyGroup();
			if (actual != null) {
				partyGroupTrxValue.setReferenceID(String
						.valueOf(actual.getId()));
			} else {
				partyGroupTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				partyGroupTrxValue.setStagingReferenceID(String.valueOf(staging
						.getId()));
			} else {
				partyGroupTrxValue.setStagingReferenceID(null);
			}
			return partyGroupTrxValue;
		} else {
			throw new PartyGroupException("ERROR-- Party Group is null");
		}
	}

	/**
	 * 
	 * @param partyGroupTrxValue
	 * @return IPartyGroupTrxValue
	 * @throws TrxOperationException
	 */

	protected IPartyGroupTrxValue updatePartyGroupTrx(
			IPartyGroupTrxValue partyGroupTrxValue)
			throws TrxOperationException {
		try {
			partyGroupTrxValue = prepareTrxValue(partyGroupTrxValue);
			ICMSTrxValue tempValue = super
					.updateTransaction(partyGroupTrxValue);
			OBPartyGroupTrxValue newValue = new OBPartyGroupTrxValue(tempValue);
			newValue.setPartyGroup(partyGroupTrxValue.getPartyGroup());
			newValue.setStagingPartyGroup(partyGroupTrxValue
					.getStagingPartyGroup());
			return newValue;
		}

		catch (Exception ex) {
			throw new PartyGroupException("General Exception: " + ex.toString());
		}
	}

	/**
	 * 
	 * @param partyGroupTrxValue
	 * @return IPartyGroupTrxValue
	 * @throws TrxOperationException
	 */
	protected IPartyGroupTrxValue createStagingPartyGroup(
			IPartyGroupTrxValue partyGroupTrxValue)
			throws TrxOperationException {
		try {
			IPartyGroup partyGroup = getStagingPartyGroupBusManager()
					.createPartyGroup(partyGroupTrxValue.getStagingPartyGroup());
			partyGroupTrxValue.setStagingPartyGroup(partyGroup);
			partyGroupTrxValue.setStagingReferenceID(String.valueOf(partyGroup
					.getId()));
			return partyGroupTrxValue;
		} catch (Exception ex) {
			throw new PartyGroupException(
					"ERROR-- While creating Staging value");
		}
	}

	/**
	 * 
	 * @param anITrxValue
	 * @return IPartyGroupTrxValue
	 * @throws TrxOperationException
	 */

	protected IPartyGroupTrxValue getPartyGroupTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IPartyGroupTrxValue) anITrxValue;
		} catch (ClassCastException ex) {
			throw new PartyGroupException(
					"The ITrxValue is not of type OBCPartyGroupTrxValue: "
							+ ex.toString());
		}
	}

	/**
	 * 
	 * @param anOriginal
	 * @param aCopy
	 * @return IPartyGroupTrxValue
	 * @throws TrxOperationException
	 */

	protected IPartyGroup mergePartyGroup(IPartyGroup anOriginal,
			IPartyGroup aCopy) throws TrxOperationException {
		aCopy.setId(anOriginal.getId());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	/**
	 * 
	 * @param value
	 * @return IPartyGroupTrxValue
	 */

	protected ITrxResult prepareResult(IPartyGroupTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}
}
