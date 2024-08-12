/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/sublimittype/SubLimitTypeTrxControllerFactory.java,v 1.1 2005/10/06 05:08:56 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.sublimittype;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.trx.sublimittype.
 *      SubLimitTypeTrxControllerFactory.java
 */
public class SubLimitTypeTrxControllerFactory implements ITrxControllerFactory {
	public SubLimitTypeTrxControllerFactory() {
		super();
	}

	/*
	 * @see
	 * com.integrosys.base.businfra.transaction.ITrxControllerFactory#getController
	 * (com.integrosys.base.businfra.transaction.ITrxValue,
	 * com.integrosys.base.businfra.transaction.ITrxParameter)
	 */
	public ITrxController getController(ITrxValue itrxvalue, ITrxParameter itrxparameter) throws TrxParameterException {
		String action = itrxparameter.getAction();
		DefaultLogger.debug(this, "Action : " + action);
		if (isReadOperation(action)) {
			return new SubLimitTypeReadController();
		}
		else {
			return new SubLimitTypeTrxController();
		}
	}

	private boolean isReadOperation(String action) {
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID.equals(action)) {
			return true;
		}
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID.equals(action)) {
			return true;
		}
		if (ICMSConstant.ACTION_READ_COMMODITY_MAIN_SLT_GROUP.equals(action)) {
			return true;
		}
		return false;
	}

}
