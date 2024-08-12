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
 * Operation to read MF Checklist.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadMFChecklistOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	public ReadMFChecklistOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_MF_CHECKLIST;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param trxValue transaction value required for retrieving transaction
	 *        record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue trxValue) throws TransactionException {

		ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(trxValue);

		IMFChecklist stageMFChecklist = null;
		IMFChecklist actualMFChecklist = null;

		ICMSTrxValue[] cmsTrxValueArray = new ICMSTrxValue[0];
		try {
			cmsTrxValueArray = getTrxManager().getTrxByParentTrxID(cmsTrxValue.getTrxReferenceID(),
					ICMSConstant.INSTANCE_MF_CHECKLIST);
		}
		catch (RemoteException e) {
			throw new TransactionException(
					"fail to retrieve transaction records when executing on trx manager remote.", e.getCause());
		}

		if ((cmsTrxValueArray != null) && (cmsTrxValueArray.length > 0)) {
			boolean found = false;
			for (int i = 0; i < cmsTrxValueArray.length; i++) {
				cmsTrxValue = cmsTrxValueArray[i];

				if ((cmsTrxValue.getStatus() != null) && !cmsTrxValue.getStatus().equals(ICMSConstant.STATE_CLOSED)) {
					found = true;
					break;
				}
			}
			if (!found) {
				return null;
			}
		}
		else {
			return null;
		}

		IMFChecklistTrxValue trxVal = new OBMFChecklistTrxValue(cmsTrxValue);

		String stagingRef = trxVal.getStagingReferenceID();
		String actualRef = trxVal.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (actualRef != null) {
			try {
				actualMFChecklist = getActualCollateralBusManager().getMFChecklist(Long.parseLong(actualRef));
				trxVal.setMFChecklist(actualMFChecklist);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve market factor checklist, using reference id ["
						+ actualRef + "]", ex);
			}
		}

		if (stagingRef != null) {
			try {
				stageMFChecklist = getStagingCollateralBusManager().getMFChecklist(Long.parseLong(stagingRef));
				trxVal.setStagingMFChecklist(stageMFChecklist);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException(
						"failed to retrieve market factor checklist, using staging reference id [" + stagingRef + "]",
						ex);
			}
		}

		return trxVal;

	}

}
