/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadValuationByTrxRefIDOperation.java,v 1.6 2003/08/19 10:38:17 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read valuation by its transaction reference id.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/19 10:38:17 $ Tag: $Name: $
 */
public class ReadValuationByTrxRefIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	private ICollateralTrxDAO collateralTrxJdbcDao;

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

	public void setCollateralTrxJdbcDao(ICollateralTrxDAO collateralTrxJdbcDao) {
		this.collateralTrxJdbcDao = collateralTrxJdbcDao;
	}

	public ICollateralTrxDAO getCollateralTrxJdbcDao() {
		return collateralTrxJdbcDao;
	}

	/**
	 * Default Constructor
	 */
	public ReadValuationByTrxRefIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_VAL_BY_TRXREFID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param trxVal transaction value required for retrieving transaction
	 *        record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue trxVal) throws TransactionException {

		ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(trxVal);

		try {
			String trxID = getCollateralTrxJdbcDao().getTrxIDByTrxRefID(cmsTrxValue.getTrxReferenceID(),
					cmsTrxValue.getTransactionType());
			cmsTrxValue = getTrxManager().getTransaction(trxID);
		}
		catch (RemoteException e) {
			throw new TransactionException("encounter remote exception when retrieving transaction object.", e
					.getCause());
		}

		OBValuationTrxValue valTrxValue = new OBValuationTrxValue(cmsTrxValue);

		String stagingRef = valTrxValue.getStagingReferenceID();
		String actualRef = valTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (stagingRef != null) {
			try {
				IValuation val = getStagingCollateralBusManager().getValuation(Long.parseLong(stagingRef));
				valTrxValue.setStagingValuation(val);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral valuation, using staging reference id ["
						+ stagingRef + "]", ex);
			}
		}

		if (actualRef != null) {
			try {
				IValuation val = getActualCollateralBusManager().getValuation(Long.parseLong(actualRef));
				valTrxValue.setValuation(val);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral valuation, using staging reference id ["
						+ stagingRef + "]", ex);
			}
		}
		return valTrxValue;

	}
}