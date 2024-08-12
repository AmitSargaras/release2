/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadCollateralSubTypeByTrxIDOperation.java,v 1.1 2003/08/15 07:14:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read collateral subtype.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 07:14:24 $ Tag: $Name: $
 */
public class ReadCollateralSubTypeByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	public ReadCollateralSubTypeByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_SUBTYPE_BY_TRXID;
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
			throw new TrxOperationException("fail to retrieve trx value, for transaction id ["
					+ cmsTrxValue.getTransactionID() + "], throwing root cause [" + e.getClass() + "]", e.getCause());
		}

		OBCollateralSubTypeTrxValue subTypeTrx = new OBCollateralSubTypeTrxValue(cmsTrxValue);

		String stagingRef = cmsTrxValue.getStagingReferenceID();
		String actualRef = cmsTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (stagingRef != null) {
			try {
				ICollateralSubType[] subtypes = getStagingCollateralBusManager().getCollateralSubTypeByGroupID(
						Long.parseLong(stagingRef));
				subTypeTrx.setStagingCollateralSubTypes(subtypes);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral sub types, using staging reference id ["
						+ stagingRef + "]", ex);
			}
		}

		if (actualRef != null) {
			try {
				ICollateralSubType[] subtypes = getActualCollateralBusManager().getCollateralSubTypeByGroupID(
						Long.parseLong(actualRef));
				subTypeTrx.setCollateralSubTypes(subtypes);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral sub types, using reference id ["
						+ actualRef + "]", ex);
			}
		}
		return subTypeTrx;

	}
}