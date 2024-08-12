/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by system to update a collateral. Enhancement
 * for Stp module, with dependency injection
 * 
 * @author Ng Lie Yung
 * @author Andy Wong
 * @author Chong Jun Yong
 * @since 2004/07/07
 */
public class SystemUpdateCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -1343795403876263342L;

	private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_SYSTEM_UPDATE_COL;

	private String operationName;

	/**
	 * Default constructor.
	 */
	public SystemUpdateCollateralOperation() {
		operationName = DEFAULT_OPERATION_NAME;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		// this preProcess is to maintain fromState so that it will not be
		// overwritten by the current status which is rejected. i.e. we don't
		// want to have a scenario where we have from statte: rejected, to
		// state: rejected too.
		String status = value.getStatus();
		if (status.equals(ICMSConstant.STATE_REJECTED)) {
			String fromState = value.getFromState();
			value = super.preProcess(value);
			// now set back the from state to overwrite what was created in
			// preProcess
			ICMSTrxValue trxValue = getCMSTrxValue(value);
			trxValue.setFromState(fromState);
			return trxValue;
		}
		else {
			return super.preProcess(value);
		}
	}

	/**
	 * The following tasks are performed:
	 * <p/>
	 * 1. update staging collateral record 2. update actual collateral record 2.
	 * update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

		if (DEFAULT_OPERATION_NAME.equals(this.operationName) && trxValue.getStagingCollateral() != null) {
			trxValue = super.updateStagingCommonCollateral(trxValue);
		}

		trxValue = super.updateActualfromActualCommonCollateral(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}
