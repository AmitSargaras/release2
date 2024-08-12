/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/ForexHelper.java,v 1.8 2005/08/25 07:23:35 wltan Exp $
 */
package com.integrosys.cms.ui.common;

//ofa

import java.math.BigDecimal;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This interface defines the constant specific to the collateral task table and
 * the methods required by the document
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/25 07:23:35 $ Tag: $Name: $
 */
public class ForexHelper {
	private static ForexHelper instance;

	public static ForexHelper getInstance() {
		instance = (instance == null) ? new ForexHelper() : instance;
		return instance;
	}

	public ForexHelper() {
	}

	/**
	 * To convert the amount to the currency code specified based on the forex
	 * rates
	 * @param anAmount
	 * @param aCurrencyCode
	 * @return aCurrencyCode
	 */
	public double convertAmount(Amount anAmount, CurrencyCode aCurrencyCode) throws Exception {
		if (anAmount == null) {
			return 0;
		}

		if (anAmount.getCurrencyCode() == null) {
			throw new Exception("Currency Code for amount to be converted is null.");
		}
		if ((aCurrencyCode == null) || (aCurrencyCode.getCode() == null) || (aCurrencyCode.getCode().length() == 0)) {
			throw new Exception("Currency Code to convert amount to is null.");
		}
		if (anAmount.getCurrencyCode().equals(aCurrencyCode.getCode())) {
			return Math.round(anAmount.getAmount());
		}
		SBForexManager mgr = getSBForexManager();
		DefaultLogger.debug("ForexHelper.convertAmount", "Amount: " + anAmount);
		DefaultLogger.debug("ForexHelper.convertAmount", "Currency: " + aCurrencyCode);

		Amount amount = mgr.convert(anAmount, aCurrencyCode);

		// Coversion error , return 0 .
		if (amount == null) {
			DefaultLogger.error(this, "Convert Amount is Null");
			return 0;
		}

		return (amount.getAmount());
	}

	/**
	 * Convert the amount to the currency specified based on current forex
	 * rates.
	 * 
	 * @param amtToBeConverted - Amount denoting value to be converted in source
	 *        currency.
	 * @param toCurrencyCode - CurrencyCode denoting target currency.
	 * @return Amount - Amount converted to target currency.
	 * @throws Exception thrown when the source currency or target currency
	 *         cannot be determined.
	 */
	public Amount convert(Amount amtToBeConverted, CurrencyCode toCurrencyCode) throws Exception {
		if (amtToBeConverted != null) {
			if ((amtToBeConverted.getCurrencyCode() == null) || (amtToBeConverted.getCurrencyCode().length() == 0)) {
				throw new Exception("Currency Code for amount to be converted is null.");
			}
			if ((toCurrencyCode == null) || (toCurrencyCode.getCode() == null)
					|| (toCurrencyCode.getCode().length() == 0)) {
				throw new Exception("Currency Code to convert amount to is null.");
			}
			if (amtToBeConverted.getCurrencyCode().equals(toCurrencyCode.getCode())) {
				return amtToBeConverted;
			}
			SBForexManager mgr = getSBForexManager();
			DefaultLogger.debug("ForexHelper.convert", "Conversion Amount: " + amtToBeConverted);
			DefaultLogger.debug("ForexHelper.convert", "Currency to convert to: " + toCurrencyCode);
			return mgr.convert(amtToBeConverted, toCurrencyCode);
		}
		return null;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws Exception for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}

	//==========================================================================
	// ==============
	// Method from app\common\CommonUtil
	// Changes to the following methods should be reflected in the CommonUtil
	// class as well
	//==========================================================================
	// ==============
	/**
	 * Check if the amount is a result of a forex error.
	 * @param amt - Amount
	 * @return - boolean
	 */
	public static boolean isForexErrorAmount(Amount amt) {
		boolean isForexErrorAmt = ((amt != null) && (amt.getAmount() == 0) && amt.getCurrencyCode().equals(
				ICMSConstant.CURRENCYCODE_INVALID_VALUE));
		return isForexErrorAmt;
	}

	/**
	 * Get amount representing forex error has occurred.
	 * @return - boolean
	 */
	public static Amount getForexErrorAmount() {
		return new Amount(new BigDecimal(0d), new CurrencyCode(ICMSConstant.CURRENCYCODE_INVALID_VALUE));
	}

	/**
	 * Add 2 amount object. If the amount is in different currency, convert the
	 * 2nd amount (amtToAdd) to the currency of the 1st amount (total) and
	 * perform the addition.
	 * @param total - total amount (When total is null, amtToAdd is returned)
	 * @param amtToAdd - amount to add to the total amount
	 * @return - Amount
	 * @throws Exception - thrown when there is no exchange rate is available to
	 *         does the currency conversion
	 */
	public static Amount addAmount(Amount total, Amount amtToAdd) throws Exception {

		if (isForexErrorAmount(total) || isForexErrorAmount(amtToAdd)) {
			return getForexErrorAmount();
		}
		if (total == null) {
			return amtToAdd;
		}

		Amount convertedAmt = getInstance().convert(amtToAdd, total.getCurrencyCodeAsObject());
		return (amtToAdd == null) ? total : total.add(convertedAmt);

	}

	/**
	 * Subtract 2 amount object. If the amount is in different currency, convert
	 * the 2nd amount (amtToSubtract) to the currency of the 1st amount (total)
	 * and perform the addition. If total is null, it is treated as [0 -
	 * amtToSubtract]
	 * @param total - total amount (When total is null, negative of
	 *        amtToSubtract is returned)
	 * @param amtToSubtract - amount to subtract from the total amount
	 * @return - Amount
	 * @throws Exception - thrown when there is no exchange rate is available to
	 *         does the currency conversion
	 */
	public static Amount subtractAmount(Amount total, Amount amtToSubtract) throws Exception {

		if (isForexErrorAmount(total) || isForexErrorAmount(amtToSubtract)) {
			return getForexErrorAmount();
		}

		if (total == null) {
			return new Amount(-amtToSubtract.getAmount(), amtToSubtract.getCurrencyCode());
		}

		Amount convertedAmt = getInstance().convert(amtToSubtract, total.getCurrencyCodeAsObject());
		return (amtToSubtract == null) ? total : total.subtract(convertedAmt);
	}

	/**
	 * Compares 2 amount object to determine if they are equal in value or which
	 * is smaller or greater. If the amounts are in different currencies,
	 * conversion of the 2nd amount object (amt2) will be converted to the
	 * currency of the first object before carrying out the comparison. Both
	 * objects are considered equal when 1. they are both null, 2. the "amount"
	 * value is equal (in the same currency)
	 * 
	 * @param amt1 - Amount 1 to be compared against
	 * @param amt2 - Amount 2 to be compared to Amount 1
	 * @return CommonUtil.EQUAL if amt1 = amt2, CommonUtil.LESS if amt1 < amt2,
	 *         CommonUtil.GREATER if amt1 > amt2
	 * @throws Exception - thrown when there is no exchange rate is available to
	 *         does the currency conversion
	 */
	public static int compareAmount(Amount amt1, Amount amt2) throws Exception {
		if ((amt1 == null) && (amt2 == null)) {
			return CommonUtil.EQUAL;
		}

		if ((amt1 == null) && (amt2 != null)) {
			return CommonUtil.EQUAL;
		}

		if ((amt1 != null) && (amt2 == null)) {
			return CommonUtil.EQUAL;
		}

		if (!(amt1.getCurrencyCode().equals(amt2.getCurrencyCode()))) {
			amt2 = getInstance().convert(amt2, amt1.getCurrencyCodeAsObject());
		}

		if ((amt1.getAmount() == amt2.getAmount())) {
			return CommonUtil.EQUAL;
		}

		return (amt1.getAmount() < amt2.getAmount()) ? CommonUtil.LESS : CommonUtil.GREATER;
	}
}
