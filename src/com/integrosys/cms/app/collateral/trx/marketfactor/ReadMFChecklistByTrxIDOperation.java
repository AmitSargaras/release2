/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read MF Checklist by transaction ID.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadMFChecklistByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	/**
	 * @return the actualCollateralBusManager
	 */
	public ICollateralBusManager getActualCollateralBusManager() {
		return actualCollateralBusManager;
	}

	/**
	 * @return the stagingCollateralBusManager
	 */
	public ICollateralBusManager getStagingCollateralBusManager() {
		return stagingCollateralBusManager;
	}

	/**
	 * @param actualCollateralBusManager the actualCollateralBusManager to set
	 */
	public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
		this.actualCollateralBusManager = actualCollateralBusManager;
	}

	/**
	 * @param stagingCollateralBusManager the stagingCollateralBusManager to set
	 */
	public void setStagingCollateralBusManager(ICollateralBusManager stagingCollateralBusManager) {
		this.stagingCollateralBusManager = stagingCollateralBusManager;
	}

	/**
	 * Default Constructor
	 */
	public ReadMFChecklistByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_MF_CHECKLIST_BY_TRXID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

		ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);
		try {
			cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
		}
		catch (RemoteException e) {
			throw new TransactionException(
					"fail to retrieve transaction records when executing on trx manager remote.", e.getCause());
		}

		OBMFChecklistTrxValue trxVal = new OBMFChecklistTrxValue(cmsTrxValue);

		String stagingRef = cmsTrxValue.getStagingReferenceID();
		String actualRef = cmsTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (actualRef != null) {
			try {
				IMFChecklist checklist = getActualCollateralBusManager().getMFChecklist(Long.parseLong(actualRef));
				trxVal.setMFChecklist(checklist);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral asset life, using reference id ["
						+ actualRef + "]", ex);
			}

		}

		if (stagingRef != null) {
			try {
				IMFChecklist checklist = getStagingCollateralBusManager().getMFChecklist(Long.parseLong(stagingRef));
				trxVal.setStagingMFChecklist(checklist);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException(
						"failed to retrieve market factor checking, using staging reference id [" + stagingRef + "]",
						ex);
			}

		}

		return trxVal;

	}

}