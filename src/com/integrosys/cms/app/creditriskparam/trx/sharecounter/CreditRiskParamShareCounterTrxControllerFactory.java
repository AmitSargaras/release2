/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamShareCounterTrxControllerFactory
 *
 * Created on 11:59:25 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.trx.sharecounter;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 11:59:25 AM
 */
public class CreditRiskParamShareCounterTrxControllerFactory implements ITrxControllerFactory {
	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter iTrxParameter) throws TrxParameterException {
		if (iTrxParameter == null) {
			throw new TrxParameterException("ITrxParameter argument is null");
		}

		DefaultLogger.debug(this, "Retrieving transaction controller for action " + iTrxParameter.getAction());

		if (ICMSConstant.ACTION_CREDIT_RISK_PARAM_READ.equals(iTrxParameter.getAction())
				|| ICMSConstant.ACTION_CREDIT_RISK_PARAM_READBY_TRXID.equals(iTrxParameter.getAction())) {
			DefaultLogger.debug(this, "Retrieving read controller");

			return new CreditRiskParamShareCounterReadController();
		}
		else {
			DefaultLogger.debug(this, "Retrieving transaction controller");

			return new CreditRiskParamShareCounterTrxController();
		}
	}
}
