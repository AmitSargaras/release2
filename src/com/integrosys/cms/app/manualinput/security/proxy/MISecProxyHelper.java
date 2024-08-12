/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.manualinput.security.proxy;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MISecProxyHelper {
	/**
	 * Retrieve Collateral information to complete the OBs
	 */
	public ICollateralTrxValue prepareTrxSec(ICollateralTrxValue trxValue) {
		return trxValue;
	}

	public ITrxValue constructTrxValue(ITrxContext ctx, ITrxValue trxValue) {
		((ICollateralTrxValue) trxValue).setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
		((ICollateralTrxValue) trxValue).setTrxReferenceID(String.valueOf(ICMSConstant.LONG_INVALID_VALUE));
		((ICollateralTrxValue) trxValue).setTrxContext(ctx);
		return trxValue;
	}
}
