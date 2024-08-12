/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadColParamByCountryAndColTypeOperation.java,v 1.4 2003/08/15 10:11:25 lyng Exp $
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
 * Operation to read security parameters by country and security type code.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/15 10:11:25 $ Tag: $Name: $
 */
public class ReadColParamByCountryAndColTypeOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	public ReadColParamByCountryAndColTypeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COLPARAM_BY_COUNTRY_COLTYPE;
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

		ICollateralParameterTrxValue trxVal = (ICollateralParameterTrxValue) val;

		String countryCode = trxVal.getCountryCode();
		String colType = trxVal.getCollateralTypeCode();

		DefaultLogger.debug(this, "Country Code: " + countryCode + ", Type Code: " + colType);

		ICollateralParameter[] stageColParams = null;
		try {
			stageColParams = getStagingCollateralBusManager().getCollateralParameter(countryCode, colType);
		}
		catch (CollateralException ex) {
			throw new TrxOperationException(
					"failed to retrieve collateral parameter from staging, using country code [" + countryCode
							+ "] collateral type [" + colType + "]", ex);
		}

		ICollateralParameter[] actualColParams = null;
		try {
			actualColParams = getActualCollateralBusManager().getCollateralParameter(countryCode, colType);
		}
		catch (CollateralException ex) {
			throw new TrxOperationException("failed to retrieve collateral parameter, using country code ["
					+ countryCode + "] collateral type [" + colType + "]", ex);
		}

		String actualRefID = null;
		if ((actualColParams != null) && (actualColParams.length != 0)) {
			actualRefID = String.valueOf(actualColParams[0].getGroupId());
		}

		try {
			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_COL_PARAMETER);
		}
		catch (RemoteException e) {
			throw new TrxOperationException("fail to retrieve trx value, for ref id [" + actualRefID + "], trx type ["
					+ ICMSConstant.INSTANCE_COL_PARAMETER + "], throwing root cause [" + e.getClass() + "]", e
					.getCause());
		}

		OBCollateralParameterTrxValue colParamTrx = new OBCollateralParameterTrxValue(cmsTrxValue);
		colParamTrx.setCollateralParameters(actualColParams);
		if ((stageColParams == null) || (stageColParams.length == 0)) {
			stageColParams = actualColParams;
		}
		colParamTrx.setStagingCollateralParameters(stageColParams);
		return colParamTrx;

	}
}
