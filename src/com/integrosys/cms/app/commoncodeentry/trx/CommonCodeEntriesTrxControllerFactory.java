/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntriesTrxControllerFactory.java
 *
 * Created on February 6, 2007, 5:11:24 PM
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

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 6, 2007 Time: 5:11:24 PM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntriesTrxControllerFactory implements ITrxControllerFactory {

	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		DefaultLogger.debug(this, "param " + param);
		DefaultLogger.debug(this, "param action" + param.getAction());

		if (isReadOperation(param.getAction())) {
			DefaultLogger.debug(this, "read operation");
			return new CommonCodeEntriesReadController();
		}

		DefaultLogger.debug(this, "trx operation");

		return new CommonCodeEntriesTrxController();
	}

	/**
	 * To check if the action requires a read operation or not
	 * @param anAction - String
	 * @return boolean - true if requires a read operation and false otherwise
	 */
	private boolean isReadOperation(String action) {
		return (ICMSConstant.COMMON_CODE_ENTRY_READ.equals(action)
				|| ICMSConstant.COMMON_CODE_ENTRY_READ_BY_REF.equals(action));
	}
}
