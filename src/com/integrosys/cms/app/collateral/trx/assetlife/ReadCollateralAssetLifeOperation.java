/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

/**
 * Operation to read security assetlife.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadCollateralAssetLifeOperation extends AbstractReadCollateralAssetLifeOperation {
	/**
	 * Default Constructor
	 */
	public ReadCollateralAssetLifeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ASSETLIFE;
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

		ICollateralAssetLife[] stageAssetLifes = null;
		try {
			stageAssetLifes = getStagingCollateralBusManager().getCollateralAssetLife();
		}
		catch (CollateralException e) {
			throw new TrxOperationException("failed to retrieve collateral asset life from actual table", e);
		}
		ICollateralAssetLife[] actualAssetLifes = null;
		try {
			actualAssetLifes = getActualCollateralBusManager().getCollateralAssetLife();
		}
		catch (CollateralException e) {
			throw new TrxOperationException("failed to retrieve collateral asset life from staging table", e);
		}

		String actualRefID = (ArrayUtils.isEmpty(actualAssetLifes)) ? null : String.valueOf(actualAssetLifes[0]
				.getGroupID());

		if (actualRefID != null) {

			ICMSTrxValue trxValue = new OBCMSTrxValue();
			trxValue.setReferenceID(actualRefID);

			cmsTrxValue = doInTrxManager(trxValue);
		}

		OBCollateralAssetLifeTrxValue assetLifeTrx = new OBCollateralAssetLifeTrxValue(cmsTrxValue);

		assetLifeTrx.setCollateralAssetLifes(actualAssetLifes);

		if (ArrayUtils.isEmpty(stageAssetLifes)) {
			stageAssetLifes = actualAssetLifes;
		}

		assetLifeTrx.setStagingCollateralAssetLifes(stageAssetLifes);

		return assetLifeTrx;
	}

	public ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		Validate.notNull(trxValue, "'trxValue' must not be null");

		try {
			return getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					ICMSConstant.INSTANCE_COL_ASSETLIFE);
		}
		catch (Throwable t) {
			throw new TransactionException("encounter error when reading trx value using reference id ["
					+ trxValue.getReferenceID() + "] trx tye [" + ICMSConstant.INSTANCE_COL_ASSETLIFE + "]", t);
		}
	}
}
