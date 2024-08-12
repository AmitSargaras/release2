package com.integrosys.cms.app.propertyparameters.trx;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:01:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrPaTrxControllerFactory implements ITrxControllerFactory {

	public PrPaTrxControllerFactory() {
		super();
	}

	public ITrxController getController(ITrxValue iTrxValue, ITrxParameter param) throws TrxParameterException {
		if (isReadOperation(param.getAction())) {
			return new PrPaReadController();
		}
		return new PrPaTrxController();
	}

	private boolean isReadOperation(String anAction) {
		if ((anAction.equals(ICMSConstant.ACTION_READ_PRPA)) || (anAction.equals(ICMSConstant.ACTION_READ_PRPA_ID))) {
			return true;
		}
		return false;
	}
}
