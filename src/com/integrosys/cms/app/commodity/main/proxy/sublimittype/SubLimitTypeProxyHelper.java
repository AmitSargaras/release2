/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/sublimittype/SubLimitTypeProxyHelper.java,v 1.1 2005/10/06 05:20:08 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy.sublimittype;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue;
import com.integrosys.cms.app.commodity.main.trx.sublimittype.SubLimitTypeTrxControllerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag com.integrosys.cms.app.commodity.main.proxy.sublimittype.
 *      SubLimitTypeProxyHelper.java
 */
public class SubLimitTypeProxyHelper {

	public ISubLimitTypeTrxValue operate(ITrxContext trxContext, ISubLimitTypeTrxValue inTrxValue, String action)
			throws CommodityException, TransactionException {
		DefaultLogger.debug(this, "operate()");
		if (trxContext == null) {
			throw new CommodityException("The anITrxContext is null!!!");
		}
		if (inTrxValue == null) {
			throw new CommodityException("The ISubLimitType to be created is null!!!");
		}

		ISubLimitTypeTrxValue trxValue = formulateTrxValue(trxContext, inTrxValue);
		if ((action == null) || (action.trim().length() == 0)) {
			throw new CommodityException(" FromProxy : ACTION is NULL. can't proceed without knowing the "
					+ "action. given action is '" + action + "'  , The value is " + trxValue);
		}
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(action);
		return operate(trxValue, param);
	}

	private ISubLimitTypeTrxValue formulateTrxValue(ITrxContext trxContext, ISubLimitTypeTrxValue trxValue) {
		trxValue.setTrxContext(trxContext);
		// trxValue
		//.setTransactionType(ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE)
		// ;
		return trxValue;
	}

	private ISubLimitTypeTrxValue operate(ISubLimitTypeTrxValue trxValue, OBCMSTrxParameter anOBCMSTrxParameter)
			throws TransactionException {
		ICMSTrxResult result = operateAnyAbstractSubLimitType(trxValue, anOBCMSTrxParameter);
		return (ISubLimitTypeTrxValue) result.getTrxValue();
	}

	private ICMSTrxResult operateAnyAbstractSubLimitType(ICMSTrxValue trxValue, OBCMSTrxParameter trxParam)
			throws TransactionException {
		try {
			ITrxController controller = null;
			if (ICMSConstant.INSTANCE_COMMODITY_MAIN_SUBLIMITTYPE.equals(trxValue.getTransactionType())) {
				controller = (new SubLimitTypeTrxControllerFactory()).getController(trxValue, trxParam);
			}
			else {
				throw new CommodityException(" From Proxy : TransactionType '" + trxValue.getTransactionType()
						+ "' is invalid. Unable to proceed. Please pass a valid transaction type.");
			}
			if (controller == null) {
				throw new CommodityException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxValue, trxParam);
			return (ICMSTrxResult) result;
		}
		catch (Exception ex) {
			throw new TransactionException(ex);
		}
	}
}
