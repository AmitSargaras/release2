/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.IDealValuation;
import com.integrosys.cms.app.tradingbook.bus.IGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.IISDACSADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.bus.OBDealValuation;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADealVal;
import com.integrosys.cms.app.tradingbook.bus.OBISDACSADealVal;

/**
 * A helper class for trading book, contains common methods shared among trading
 * book.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class TradingBookHelper {

	public static final BigDecimal DAYS_IN_YEAR = new BigDecimal(360);

	public static final int SCALE = 4;

	public static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

	public static IGMRADealVal[] toGMRAValArray(ArrayList dealValList) {
		return (IGMRADealVal[]) dealValList.toArray(new OBGMRADealVal[0]);
	}

	public static IISDACSADealVal[] toCSAValArray(ArrayList dealValList) {
		return (IISDACSADealVal[]) dealValList.toArray(new OBISDACSADealVal[0]);
	}

	public static IDealValuation[] toValArray(ArrayList dealValList) {
		return (IDealValuation[]) dealValList.toArray(new OBDealValuation[0]);
	}

	public static ICashMargin[] toCashMarginArray(ArrayList dealValList) {
		return (ICashMargin[]) dealValList.toArray(new OBCashMargin[0]);
	}

	public static ICashMargin sumCashMarginValue(ICashMargin cm1, ICashMargin cm2) {
		if (!DB2DateConverter.formatDate(cm1.getTrxDate()).equals(DB2DateConverter.formatDate(cm2.getTrxDate()))) {
			return cm1;
		}
		if (cm1 == null) {
			return cm2;
		}
		if (cm2 == null) {
			return cm1;
		}

		BigDecimal b1 = new BigDecimal(cm1.getNAPAmount().getAmount());
		if (!cm1.getNAPSignAddInd()) {
			b1 = b1.negate();
		}
		BigDecimal b2 = new BigDecimal(cm2.getNAPAmount().getAmount());
		if (!cm2.getNAPSignAddInd()) {
			b2 = b2.negate();
		}
		b1 = b1.add(b2);

		if (b1.signum() == -1) {
			cm1.setNAPSignAddInd(false);
			b1 = b1.negate();
		}
		else {
			cm1.setNAPSignAddInd(true);
		}
		cm1.getNAPAmount().setAmount(b1.doubleValue());
		return cm1;
	}

	public static BigDecimal calcCashMarginInterest(ICashMargin cm1, IInterestRate int2) {
		if ((cm1 != null) && (int2 != null) && (cm1.getNAPAmount() != null) && (int2.getIntRatePercent() != null)) {
			BigDecimal principalAmt = new BigDecimal(cm1.getNAPAmount().getAmount());
			BigDecimal intRate = new BigDecimal(int2.getIntRatePercent().doubleValue()).divide(new BigDecimal(100),
					SCALE, ROUNDING_MODE);

			BigDecimal result = principalAmt.multiply(intRate).divide(DAYS_IN_YEAR, SCALE, ROUNDING_MODE);

			return result;
		}

		return null;
	}
}
