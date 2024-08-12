/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealReadController
 *
 * Created on 11:42:40 AM
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

import com.integrosys.base.businfra.transaction.AbstractTrxController;
import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxOperationFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSReadTrxManager;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 11:42:40 AM
 */
public class PreDealReadController extends AbstractTrxController implements ITrxOperationFactory {

	public String getInstanceName() {
		return ICMSConstant.INSTANCE_PRE_DEAL;
	}

	public ITrxResult operate(ITrxValue value, ITrxParameter param) throws TrxParameterException,
			TrxControllerException, TransactionException {
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}

		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		value = setInstanceName(value);

		ITrxOperation op = getOperation(value, param);
		CMSReadTrxManager mgr = new CMSReadTrxManager();
		ITrxResult result = null;

		try {
			result = mgr.operateTransaction(op, value);

			return result;
		}
		catch (TransactionException te) {
			throw te;
		}
		catch (Exception re) {
			throw new TrxControllerException("Caught Unknown Exception: " + re.toString());
		}
	}

	public ITrxOperation getOperation(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
		if (null == param) {
			throw new TrxParameterException("ITrxParameter is null!");
		}

		String action = param.getAction();

		if (action != null) {
			if (action.equals(ICMSConstant.ACTION_READ_PRE_DEAL_BY_TRX_ID)) {
				return new PreDealTrxReadOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_PRE_DEAL)) {
				return new PreDealReadOperation();
			}
			else if (action.equals(ICMSConstant.ACTION_READ_PRE_DEAL_BY_FEED_ID)) {
				return new PreDealReadFeedIdOperation();
			}

			throw new TrxParameterException("Unknow Action: " + action + ".");
		}

		throw new TrxParameterException("Action is null!");
	}

}
