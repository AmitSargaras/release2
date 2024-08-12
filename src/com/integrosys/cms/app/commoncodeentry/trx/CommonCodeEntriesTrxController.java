/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesTrxController.java
 *
 * Created on February 6, 2007, 5:57:46 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry.trx;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxController;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 6, 2007 Time: 5:57:46 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntriesTrxController extends CMSTrxController {
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

		if (null == action) {
			throw new TrxParameterException("Action is null in ITrxParameter!");
		}
		DefaultLogger.debug(this, "Action: " + action);
		DefaultLogger.debug(this, "toState: " + toState);
		DefaultLogger.debug(this, "fromState: " + fromState);

		if (action.equals(ICMSConstant.COMMON_CODE_ENTRY_APPROVE) && ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CommonCodeEntryApproveOperation();
		}
		else if (action.equals(ICMSConstant.COMMON_CODE_ENTRY_REJECT)
				&& ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CommonCodeEntryRejectOperation();
		}
		else if (action.equals(ICMSConstant.COMMON_CODE_ENTRY_CLOSE) && ICMSConstant.STATE_REJECTED.equals(toState)) {
			return new CommonCodeEntryCloseOperation();
		}
		else if (action.equals(ICMSConstant.COMMON_CODE_ENTRY_UPDATE)
				&& !ICMSConstant.STATE_PENDING_UPDATE.equals(toState)) {
			return new CommonCodeEntryUpdateOperation();
		}

		DefaultLogger.debug(this, "No operations found !");

		throw new TrxParameterException("No operations found!");
	}

	public String getInstanceName() {
		return ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME;
	}

}
