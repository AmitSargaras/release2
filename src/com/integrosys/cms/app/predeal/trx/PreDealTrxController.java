/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealTrxController
 *
 * Created on 11:53:49 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 11:53:49 AM
 */
public class PreDealTrxController extends CMSTrxController {
	public String getInstanceName() {
		return ICMSConstant.INSTANCE_PRE_DEAL;
	}

	public ITrxOperation getOperation(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}

		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		String action = param.getAction();
		String toState = value.getToState(); // actually the current state !!!!!
		String fromState = value.getFromState();

		DefaultLogger.debug(this, "Action : " + action);
		DefaultLogger.debug(this, "toState : " + toState);
		DefaultLogger.debug(this, "fromState : " + fromState);

		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}

		// checker's operations
		if (action.equals(ICMSConstant.ACTION_CHECKER_APPROVE_EAR_MARK)
				&& (ICMSConstant.STATE_PENDING_CREATE.equals(toState)
						|| ICMSConstant.STATE_PENDING_UPDATE.equals(toState) || ICMSConstant.STATE_PENDING_DELETE
						.equals(toState))) {
			return new CheckerApproveEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_CHECKER_REJECT_EAR_MARK)
				&& (ICMSConstant.STATE_PENDING_CREATE.equals(toState)
						|| ICMSConstant.STATE_PENDING_UPDATE.equals(toState) || ICMSConstant.STATE_PENDING_DELETE
						.equals(toState))) {
			return new CheckerRejectEarMarkOperation();
		}
		// maker's operation
		else if (action.equals(ICMSConstant.ACTION_MAKER_CREATE_EAR_MARK)) {
			return new MakerCreateEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_MAKER_TRANSFER_EAR_MARK)
				&& ICMSConstant.STATE_ACTIVE.equals(toState)) {
			return new MakerTransferEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_MAKER_DELETE_EAR_MARK) && ICMSConstant.STATE_ACTIVE.equals(toState)) {
			return new MakerDeleteEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_MAKER_RELEASE_EAR_MARK) && ICMSConstant.STATE_ACTIVE.equals(toState)) {
			return new MakerReleaseEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_MAKER_CLOSE_EAR_MARK)
				&& (ICMSConstant.STATE_REJECTED_CREATE.equals(toState)
						|| ICMSConstant.STATE_REJECTED_DELETE.equals(toState) || ICMSConstant.STATE_REJECTED_UPDATE
						.equals(toState))) {
			return new MakerCloseEarMarkOperation();
		}
		else if (action.equals(ICMSConstant.ACTION_MAKER_UPDATE_REJECTED_EAR_MARK)
				&& ICMSConstant.STATE_REJECTED_CREATE.equals(toState)) {
			return new MakerUpdateRejectEarMarkOperation();
		}

		throw new TrxParameterException("Unknown operation requirement by arguements passed in !");
	}
}
