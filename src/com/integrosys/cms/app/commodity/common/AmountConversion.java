/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/AmountConversion.java,v 1.8 2004/07/23 13:08:10 lyng Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/07/23 13:08:10 $ Tag: $Name: $
 */
public class AmountConversion implements Serializable {
	private static SBForexManager forexMgr = null;

	public static Amount getConversionAmount(Amount amt, String newCcyCode) throws AmountConversionException {
		try {
			if (newCcyCode == null) {
				throw new AmountConversionException("New currency code is null!!!");
			}

			if (amt == null) {
				return null;
			}

			if (amt.getCurrencyCode() == null) {
				throw new AmountConversionException("The Amount doesn't have currency code!");
			}

			if (!amt.getCurrencyCode().equals(newCcyCode)) {
				if (forexMgr == null) {
					forexMgr = getSBForexManager();
				}
				CurrencyCode newCurrency = new CurrencyCode(newCcyCode);

				Amount newAmt = forexMgr.convert(amt, newCurrency);
				if (newAmt != null) {
					return new Amount(newAmt.getAmountAsBigDecimal(), newAmt.getCurrencyCodeAsObject());
				}
				else {
					throw new AmountConversionException("No rate for FROM Currency: " + amt.getCurrencyCode()
							+ " and TO Currency: " + newCcyCode);
					// return null;
				}
			}
			else {
				return new Amount(amt.getAmountAsBigDecimal(), amt.getCurrencyCodeAsObject());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new AmountConversionException(e.getMessage(), e);
		}
	}

	private static SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());

		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}
}
