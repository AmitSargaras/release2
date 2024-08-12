/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/HostUpdateCollateralOperation.java,v 1.1 2003/09/30 03:14:21 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation class is invoked by host to update a collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/30 03:14:21 $ Tag: $Name: $
 */
public class HostUpdateCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -8252810766497213310L;

	private String[] retainedFromStateStatus;

	/**
	 * <p>
	 * List of status that required to retained the from state.
	 * <p>
	 * IE, if from state = 'PENDING_UPDATE', to state = 'REJECTED', after gone
	 * through round of state matrix, from state will still remain as
	 * 'PENDING_UPDATE' instead of 'REJECTED' (normal flow)
	 * @param retainedFromStateStatus list of status required to retained the
	 *        from state.
	 */
	public void setRetainedFromStateStatus(String[] retainedFromStateStatus) {
		this.retainedFromStateStatus = retainedFromStateStatus;
	}

	/**
	 * Default constructor.
	 */
	public HostUpdateCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_HOST_UPDATE_COL;
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
		// overwritten by the
		// current status which is rejected. i.e. we don't want to have a
		// scenario where we have from statte: rejected, to state: rejected too.
		String status = value.getStatus();
		if (ArrayUtils.contains(this.retainedFromStateStatus, status)) {
			String fromState = value.getFromState();
			value = super.preProcess(value);

			ICMSTrxValue trxValue = getCMSTrxValue(value);

			// now set back the from state to overwrite what was created in
			// preProcess
			trxValue.setFromState(fromState);
			return trxValue;
		}
		else {
			return super.preProcess(value);
		}
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}
