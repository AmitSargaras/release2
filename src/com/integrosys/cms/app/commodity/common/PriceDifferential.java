/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/PriceDifferential.java,v 1.10 2005/10/10 06:04:54 czhou Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.io.Serializable;
import java.math.BigDecimal;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: czhou $
 * @version $Revision: 1.10 $
 * @since $Date: 2005/10/10 06:04:54 $ Tag: $Name: $
 */
public class PriceDifferential implements Serializable {
	private Amount myPrice;

	private DifferentialSign mySign;

	public PriceDifferential(Amount aPrice, String aSignCode) {
		myPrice = aPrice;
		mySign = DifferentialSign.valueOf(aSignCode);
	}

	public PriceDifferential(Amount aPrice, DifferentialSign aSign) {
		myPrice = aPrice;
		mySign = aSign;
	}

	public Amount getPrice() {
		return myPrice;
	}

	public void setPrice(Amount aPrice) {
		myPrice = aPrice;
	}

	public DifferentialSign getSign() {
		return mySign;
	}

	public void setSign(DifferentialSign aSign) {
		mySign = aSign;
	}

	/**
	 * Apply differential to the price.
	 * 
	 * @param priceToApplyDifferential - Amount representing the price to apply
	 *        differential
	 * @return Amount representing the price after applying differential
	 * @throws PriceDifferentialException
	 */
	public Amount calculate(Amount priceToApplyDifferential) throws PriceDifferentialException {

		DefaultLogger.debug(this, "########## priceDiff.calculate : price - " + priceToApplyDifferential);

		if (priceToApplyDifferential == null) {
			return null;
		}
		if (priceToApplyDifferential.getAmountAsDouble() == 0) {
			return priceToApplyDifferential;
		}

		BigDecimal price = priceToApplyDifferential.getAmountAsBigDecimal();
		CurrencyCode priceCcy = priceToApplyDifferential.getCurrencyCodeAsObject();
		if (myPrice != null) {
			BigDecimal priceDiff = myPrice.getAmountAsBigDecimal();

			// no need to do CCY conversions for price differentials
			// CurrencyCode priceDiffCcy = myPrice.getCurrencyCodeAsObject();
			// priceDiff = (!priceDiffCcy.equals(priceCcy)) ? convert(priceDiff,
			// priceDiffCcy, priceCcy) : priceDiff;

			if (DifferentialSign.POSITIVE_DIFFERENTIAL.equals(mySign)) {
				price = price.add(priceDiff);
			}
			else if (DifferentialSign.NEGATIVE_DIFFERENTIAL.equals(mySign)) {
				price = price.subtract(priceDiff);
			}
			else {
				// no change to price
			}
			DefaultLogger.debug(this, "########## priceDiff.calculate : new price - " + price);
		}
		return new Amount(price, priceCcy);
	}

	/**
	 * Helper method to convert value from source currency to target currency.
	 * 
	 * @param fromUnit - BigDecimal
	 * @param fromCurrency - CurrencyCode
	 * @param toCurrency - CurrencyCode
	 * @return BigDecimal
	 * @throws PriceDifferentialException
	 */
	private BigDecimal convert(BigDecimal fromUnit, CurrencyCode fromCurrency, CurrencyCode toCurrency)
			throws PriceDifferentialException {

		try {
			SBForexManager mgr = getSBForexManager();
			return mgr.convert(fromUnit, fromCurrency, toCurrency);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PriceDifferentialException("Exception when converting amount : " + e.getMessage());
		}
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws PriceDifferentialException for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws PriceDifferentialException {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new PriceDifferentialException("SBForexManager is null!");
		}
		return mgr;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PriceDifferential)) {
			return false;
		}

		final PriceDifferential priceDifferential = (PriceDifferential) o;

		if (myPrice != null ? !myPrice.equals(priceDifferential.myPrice) : priceDifferential.myPrice != null) {
			return false;
		}
		if (mySign != null ? !mySign.equals(priceDifferential.mySign) : priceDifferential.mySign != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (myPrice != null ? myPrice.hashCode() : 0);
		result = 29 * result + (mySign != null ? mySign.hashCode() : 0);
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
