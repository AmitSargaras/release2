/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadColParamByTrxIDOperation.java,v 1.3 2003/08/15 06:40:19 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read collateral parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/15 06:40:19 $ Tag: $Name: $
 */
public class ReadColParamByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	public ReadColParamByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COLPARAM_BY_TRXID;
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

		OBCollateralParameterTrxValue paramTrxValue = new OBCollateralParameterTrxValue(cmsTrxValue);

		String stagingRef = cmsTrxValue.getStagingReferenceID();
		String actualRef = cmsTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (stagingRef != null) {
			try {
				ICollateralParameter[] colParams = getStagingCollateralBusManager().getCollateralParameter(
						Long.parseLong(stagingRef));
				paramTrxValue.setStagingCollateralParameters(colParams);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral parameter, using staging reference id ["
						+ stagingRef + "]", ex);
			}
		}

		if (actualRef != null) {
			try {
				ICollateralParameter[] colParams = getActualCollateralBusManager().getCollateralParameter(
						Long.parseLong(actualRef));
				paramTrxValue.setCollateralParameters(colParams);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral parameter, using reference id ["
						+ actualRef + "]", ex);
			}

		}
		return paramTrxValue;

	}
}