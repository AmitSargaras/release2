/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadCollateralSubTypeByTypeCodeOperation.java,v 1.5 2003/08/14 13:40:02 lyng Exp $
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
import com.integrosys.cms.app.collateral.bus.OBCollateralType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Operation to read security subtype by security type code.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/14 13:40:02 $ Tag: $Name: $
 */
public class ReadCollateralSubTypeByTypeCodeOperation extends CMSTrxOperation implements ITrxReadOperation {

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
	public ReadCollateralSubTypeByTypeCodeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_SUBTYPE_BY_TYPECODE;
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

		ICollateralSubTypeTrxValue trxVal = (ICollateralSubTypeTrxValue) val;

		String typeCode = trxVal.getCollateralTypeCode();

		DefaultLogger.debug(this, " Type Code: " + typeCode);

		OBCollateralType colType = new OBCollateralType();
		colType.setTypeCode(typeCode);

		ICollateralSubType[] stageSubTypes;
		try {
			stageSubTypes = getStagingCollateralBusManager().getCollateralSubTypeByType(colType);
		}
		catch (CollateralException ex) {
			throw new TrxOperationException(
					"failed to retrieve collateral sub types from staging, using collateral type [" + colType + "]", ex);
		}

		ICollateralSubType[] actualSubTypes;
		try {
			actualSubTypes = getActualCollateralBusManager().getCollateralSubTypeByType(colType);
		}
		catch (CollateralException ex) {
			throw new TrxOperationException("failed to retrieve collateral sub types, using collateral type ["
					+ colType + "]", ex);
		}

		String actualRefID = null;
		if ((actualSubTypes != null) && (actualSubTypes.length != 0)) {
			actualRefID = String.valueOf(actualSubTypes[0].getGroupID());
		}

		if (actualRefID != null) {
			try {
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_COL_SUBTYPE);
			}
			catch (RemoteException e) {
				throw new TrxOperationException("fail to retrieve trx value, for ref id [" + actualRefID
						+ "], trx type [" + ICMSConstant.INSTANCE_COL_SUBTYPE + "], throwing root cause ["
						+ e.getClass() + "]", e.getCause());
			}
		}

		OBCollateralSubTypeTrxValue subTypeTrx = new OBCollateralSubTypeTrxValue(cmsTrxValue);
		subTypeTrx.setCollateralSubTypes(actualSubTypes);

		if ((stageSubTypes == null) || (stageSubTypes.length == 0)) {
			stageSubTypes = actualSubTypes;
		}

		subTypeTrx.setStagingCollateralSubTypes(stageSubTypes);
		return subTypeTrx;

	}
}
