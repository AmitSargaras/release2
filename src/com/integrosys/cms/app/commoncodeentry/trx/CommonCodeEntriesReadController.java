/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesReadController.java
 *
 * Created on February 6, 2007, 5:14:17 PM
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

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 6, 2007 Time: 5:14:17 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntriesReadController extends AbstractTrxController implements ITrxOperationFactory {

	public String getInstanceName() {
		return ICMSConstant.COMMON_CODE_ENTRY_INSTANCE_NAME;
	}

	public ITrxResult operate(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == iTrxValue) {
			throw new TrxParameterException("ITrxValue is null!");
		}
		if (null == iTrxParameter) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		iTrxValue = setInstanceName(iTrxValue);

		DefaultLogger.debug(this, "Instance Name: " + iTrxValue.getInstanceName());

		ITrxOperation op = getOperation(iTrxValue, iTrxParameter);

		DefaultLogger.debug(this, "From state " + iTrxValue.getFromState());

		CMSReadTrxManager mgr = new CMSReadTrxManager();

		ITrxResult result = null;

		try {
			result = mgr.operateTransaction(op, iTrxValue);
			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	public ITrxOperation getOperation(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
		if (null == iTrxParameter) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		String action = iTrxParameter.getAction();

		if (action != null) {
			if (action.equals(ICMSConstant.COMMON_CODE_ENTRY_READ)) {
				return new ReadCommonCodeEntriesOperation();
			}
			if (ICMSConstant.COMMON_CODE_ENTRY_READ_BY_REF.equals(action)) {
				return new ReadCommonCodeEntriesByRefOperation();
			}

			throw new TrxParameterException("Unknow Action: " + action + ".");
		}

		throw new TrxParameterException("Action is null!");
	}
}
