/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/uom/UnitofMeasureTrxControllerFactory.java,v 1.2 2004/06/04 04:54:22 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.uom;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:22 $ Tag: $Name: $
 */
public class UnitofMeasureTrxControllerFactory {
	public ITrxController getController(ITrxValue trxVal, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new UnitofMeasureReadController();
		}
		return new UnitofMeasureTrxController();
	}

	/**
	 * Helper method to check if the action requires a read operation or not.
	 * 
	 * @param anAction of type String
	 * @return boolean true if requires a read operation, otherwise false
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD)
				|| anAction.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID)
				|| anAction.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_ID)) {
			return true;
		}
		else {
			return false;
		}
	}
}
