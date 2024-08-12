/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/CommodityPriceTrxControllerFactory.java,v 1.3 2006/03/03 05:21:41 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxControllerFactory;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This factory creates transaction controller for Commodity Price given its
 * transaction parameters.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/03/03 05:21:41 $ Tag: $Name: $
 */
public class CommodityPriceTrxControllerFactory implements ITrxControllerFactory {
	/**
	 * Default Constructor
	 */
	public CommodityPriceTrxControllerFactory() {
		super();
	}

	/**
	 * Get the ITrxController given the ITrxValue and ITrxParameter objects.
	 * 
	 * @param value is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @return commodity price transaction controller
	 * @throws TrxParameterException if any error occurs
	 */
	public ITrxController getController(ITrxValue value, ITrxParameter param) throws TrxParameterException {
		DefaultLogger.debug(this, "- TrxType : " + value.getTransactionType());
		DefaultLogger.debug(this, "- Action : " + param.getAction());

		boolean isRIC = true;
		if (ICMSConstant.INSTANCE_COMMODITY_MAIN_PRICE_NON_RIC.equals(value.getTransactionType())) {
			isRIC = false;
		}
		boolean isRead = isReadOperation(param.getAction());

		DefaultLogger.debug(this, "- IsRIC : " + isRIC);
		DefaultLogger.debug(this, "- IsRead : " + isRead);

		if (isRead && isRIC) {
			return new CommodityPriceReadController();
		}
		else if (!isRead && isRIC) {
			return new CommodityPriceTrxController();
		}
		else if (isRead && !isRIC) {
			return new CmdtNonRICPriceReadController();
		}
		else {
			return new CmdtNonRICPriceTrxController();
		}
	}

	/**
	 * Helper method to check if the action requires a read operation or not.
	 * 
	 * @param anAction of type String
	 * @return boolean true if requires a read operation, otherwise false
	 */
	private boolean isReadOperation(String anAction) {
		if (anAction.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD)
				|| anAction.equals(ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID)) {
			return true;
		}
		else {
			return false;
		}
	}
}