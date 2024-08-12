/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/ReadCollateralByColIDOperation.java,v 1.4 2003/08/19 09:06:12 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralPledgor;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/19 09:06:12 $ Tag: $Name: $
 */
public class ReadCollateralByColIDOperation extends CMSTrxOperation implements ITrxReadOperation {

	private static final long serialVersionUID = 5298252536682161684L;

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
	public ReadCollateralByColIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COL_BY_COLID;
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
			cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(cmsTrxValue.getReferenceID(),
					ICMSConstant.INSTANCE_COLLATERAL);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("failed to work on workflow manager remote interface; reference id ["
					+ cmsTrxValue.getReferenceID() + "], 'COL'; throwing root cause", ex.getCause());
		}

		OBCollateralTrxValue colTrxValue = new OBCollateralTrxValue(cmsTrxValue);

		String stagingRef = cmsTrxValue.getStagingReferenceID();
		String actualRef = colTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		ICollateralPledgor[] stagePledgor = null;

		if (stagingRef != null) {
			try {
				ICollateral col = getStagingCollateralBusManager().getCollateral(Long.parseLong(stagingRef));
				colTrxValue.setStagingCollateral(col);
				stagePledgor = col.getPledgors();
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve staging collateral, collateral id [" + stagingRef
						+ "]", ex);
			}
		}

		if (actualRef != null) {
			ICollateral col = null;
			try {
				col = getActualCollateralBusManager().getCollateral(Long.parseLong(actualRef));
				colTrxValue.setCollateral(col);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve actual collateral, collateral id [" + actualRef
						+ "]", ex);
			}

			if ((colTrxValue.getStagingCollateral() != null) && ((stagePledgor == null) || (stagePledgor.length == 0))) {
				colTrxValue.getStagingCollateral().setPledgors(col.getPledgors());
			}

			// Unlike ReadCollateralByTrxIDOperation, do not need to check for
			// staging valuation record
			// since reading by collateral id meant the actual one is always the
			// most updated
			if (colTrxValue.getStagingCollateral() != null) {
				colTrxValue.getStagingCollateral().setSourceValuation(col.getSourceValuation());
				colTrxValue.getStagingCollateral().setValuationFromLOS(col.getValuationFromLOS());

				if (colTrxValue.getStagingCollateral().getSecSystemName() == null
						|| colTrxValue.getStagingCollateral().getSecSystemName().isEmpty()) {
					OBCollateral staging = (OBCollateral) colTrxValue.getStagingCollateral();
					staging.setSecSystemName(col.getSecSystemName());
					colTrxValue.setStagingCollateral(staging);
				}
			}
		}

		return colTrxValue;
	}
}