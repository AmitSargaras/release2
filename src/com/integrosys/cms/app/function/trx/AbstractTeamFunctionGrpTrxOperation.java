package com.integrosys.cms.app.function.trx;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrp;
import com.integrosys.cms.app.function.bus.ITeamFunctionGrpBusManager;
import com.integrosys.cms.app.function.bus.OBTeamFunctionGrp;
import com.integrosys.cms.app.function.bus.TeamFunctionGrpException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

public abstract class AbstractTeamFunctionGrpTrxOperation extends CMSTrxOperation {

	private static final long serialVersionUID = 6194891575949918976L;

	private ITeamFunctionGrpBusManager teamFunctionGrpBusManager;

	private ITeamFunctionGrpBusManager stagingTeamFunctionGrpBusManager;

	public ITeamFunctionGrpBusManager getTeamFunctionGrpBusManager() {
		return teamFunctionGrpBusManager;
	}

	public void setTeamFunctionGrpBusManager(ITeamFunctionGrpBusManager teamFunctionGrpBusManager) {
		this.teamFunctionGrpBusManager = teamFunctionGrpBusManager;
	}

	public ITeamFunctionGrpBusManager getStagingTeamFunctionGrpBusManager() {
		return stagingTeamFunctionGrpBusManager;
	}

	public void setStagingTeamFunctionGrpBusManager(ITeamFunctionGrpBusManager stagingTeamFunctionGrpBusManager) {
		this.stagingTeamFunctionGrpBusManager = stagingTeamFunctionGrpBusManager;
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

	protected ITeamFunctionGrpTrxValue createStagingTeamFunctionGrp(ITeamFunctionGrpTrxValue anITeamFunctionGrpTrxValue)
			throws TrxOperationException {

		try {
			List listTeamFunctionGrpTrxValue = new ArrayList();
			ITeamFunctionGrp[] teamFunctionGrp = new OBTeamFunctionGrp[anITeamFunctionGrpTrxValue
					.getStagingTeamFunctionGrps().size()];

			// set the old staging data status to D = deleted
			if (anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps().get(0) != null) {
				List oldStagingTeamFunctionGrp = getStagingTeamFunctionGrpBusManager()
						.getTeamFunctionGrpByTeamId(
								((ITeamFunctionGrp) anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps().get(0))
										.getTeamId());
				if (oldStagingTeamFunctionGrp != null) {
					for (int i = 0; i < oldStagingTeamFunctionGrp.size(); i++) {
						ITeamFunctionGrp oldTeamFunctionGrp = (ITeamFunctionGrp) oldStagingTeamFunctionGrp.get(i);
						oldTeamFunctionGrp.setStatus("D");
						getStagingTeamFunctionGrpBusManager().updateTeamFunctionGrp(oldTeamFunctionGrp);
					}
				}
			}

			// create new staging data
			for (int i = 0; i < anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps().size(); i++) {
				teamFunctionGrp[i] = getStagingTeamFunctionGrpBusManager().createTeamFunctionGrp(
						(ITeamFunctionGrp) anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps().get(i));
				teamFunctionGrp[i].setGroupId(teamFunctionGrp[0].getTeamFunctionGrpID());
				teamFunctionGrp[i] = getStagingTeamFunctionGrpBusManager().updateTeamFunctionGrp(teamFunctionGrp[i]);
				listTeamFunctionGrpTrxValue.add(teamFunctionGrp[i]);
			}

			anITeamFunctionGrpTrxValue.setStagingTeamFunctionGrps(listTeamFunctionGrpTrxValue);

			anITeamFunctionGrpTrxValue.setStagingReferenceID(String
					.valueOf(((ITeamFunctionGrp) listTeamFunctionGrpTrxValue.get(0)).getGroupId()));

			return anITeamFunctionGrpTrxValue;
		}
		catch (TeamFunctionGrpException e) {
			throw new TrxOperationException(e);
		}
	}

	protected ITeamFunctionGrpTrxValue updateTeamFunctionGrpTransaction(
			ITeamFunctionGrpTrxValue anITeamFunctionGrpTrxValue) throws TrxOperationException {
		try {
			anITeamFunctionGrpTrxValue = prepareTrxValue(anITeamFunctionGrpTrxValue);

			DefaultLogger.debug(this, "anITeamFunctionGrpTrxValue's version time = "
					+ anITeamFunctionGrpTrxValue.getVersionTime());

			ICMSTrxValue tempValue = super.updateTransaction(anITeamFunctionGrpTrxValue);
			DefaultLogger.debug(this, "tempValue's version time = " + tempValue.getVersionTime());

			OBTeamFunctionGrpTrxValue newValue = new OBTeamFunctionGrpTrxValue(tempValue);
			newValue.setTeamFunctionGrps(anITeamFunctionGrpTrxValue.getTeamFunctionGrps());
			newValue.setStagingTeamFunctionGrps(anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps());

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

	protected ITeamFunctionGrpTrxValue createTeamFunctionGrpTransaction(
			ITeamFunctionGrpTrxValue anITeamFunctionGrpTrxValue) throws TrxOperationException {
		try {
			anITeamFunctionGrpTrxValue = prepareTrxValue(anITeamFunctionGrpTrxValue);
			ICMSTrxValue trxValue = createTransaction(anITeamFunctionGrpTrxValue);
			OBTeamFunctionGrpTrxValue obPrepareTrxValue = new OBTeamFunctionGrpTrxValue(trxValue);
			// obPrepareTrxValue.setStagingBridgingLoan(anIBridgingLoanTrxValue.getStagingBridgingLoan());
			// obPrepareTrxValue.setBridgingLoan(anIBridgingLoanTrxValue.getBridgingLoan());
			return obPrepareTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	protected ITeamFunctionGrpTrxValue getTeamFunctionGrpTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (ITeamFunctionGrpTrxValue) anITrxValue;
		}
		catch (ClassCastException cex) {
			throw new TrxOperationException("The ITrxValue is not of type OBTeamFunctionGrpTrxValue: " + cex.toString());
		}
	}

	protected ITeamFunctionGrpTrxValue prepareTrxValue(ITeamFunctionGrpTrxValue anITeamFunctionGrpTrxValue)
			throws TrxOperationException {
		if (anITeamFunctionGrpTrxValue != null) {
			List actual = anITeamFunctionGrpTrxValue.getTeamFunctionGrps();
			List staging = anITeamFunctionGrpTrxValue.getStagingTeamFunctionGrps();

			if (actual != null) {
				anITeamFunctionGrpTrxValue.setReferenceID(String.valueOf(((ITeamFunctionGrp) actual.get(0))
						.getGroupId()));
			}
			else {
				anITeamFunctionGrpTrxValue.setReferenceID(null);
			}
			if (staging != null) {
				anITeamFunctionGrpTrxValue.setStagingReferenceID(String.valueOf(((ITeamFunctionGrp) staging.get(0))
						.getGroupId()));
			}
			else {
				anITeamFunctionGrpTrxValue.setStagingReferenceID(null);
			}
			return anITeamFunctionGrpTrxValue;
		}
		return null;
	}

	protected ITrxResult prepareResult(ITeamFunctionGrpTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	protected ITeamFunctionGrp mergeTeamFunctionGrp(ITeamFunctionGrp anOriginal, ITeamFunctionGrp aCopy)
			throws TrxOperationException {
		aCopy.setTeamFunctionGrpID(anOriginal.getTeamFunctionGrpID());
		aCopy.setVersionTime(anOriginal.getVersionTime());

		return aCopy;
	}
}
