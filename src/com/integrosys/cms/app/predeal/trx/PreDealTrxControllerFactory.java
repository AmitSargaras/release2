/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealTrxControllerFactory
 *
 * Created on 11:25:25 AM
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

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 11:25:25 AM
 */
public class PreDealTrxControllerFactory implements ITrxControllerFactory {

	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		DefaultLogger.debug(this, "param : " + param);
		DefaultLogger.debug(this, "param action : " + param.getAction());

		if (isReadOperation(param.getAction())) {
			DefaultLogger.debug(this, "read operation");

			return new PreDealReadController();
		}

		DefaultLogger.debug(this, "trx operation");

		return new PreDealTrxController();
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String action) {
		return ICMSConstant.ACTION_READ_PRE_DEAL_BY_FEED_ID.equals(action)
				|| ICMSConstant.ACTION_READ_PRE_DEAL_BY_TRX_ID.equals(action)
				|| ICMSConstant.ACTION_READ_PRE_DEAL.equals(action);
	}

}
