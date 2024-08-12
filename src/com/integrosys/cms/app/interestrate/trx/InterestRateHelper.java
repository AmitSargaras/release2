/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.trx;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.OBInterestRate;

/**
 * A helper class for interest rate, contains common methods shared among
 * interest rate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateHelper {

	/**
	 * Initial the interest rate value based on the interest rate type and
	 * month.
	 * @param intRateType the type of interest rate
	 * @param monthYear the date for the interest rate
	 * 
	 * @return a list of interestrate objects
	 */
	public static IInterestRate[] initialIntRate(String intRateType, Date monthYear) {
		ArrayList arrList = new ArrayList();

		GregorianCalendar cal1 = new GregorianCalendar();
		cal1.setTime(monthYear);
		int max = cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
		int year = cal1.get(Calendar.YEAR);
		int mth = cal1.get(Calendar.MONTH);
		int i = 1;
		while (i <= max) {
			OBInterestRate intRate = new OBInterestRate();
			intRate.setIntRateType(intRateType);

			Calendar cal = new GregorianCalendar(year, mth, i);
			intRate.setIntRateDate(cal.getTime());
			arrList.add(intRate);
			i++;
		}
		return (OBInterestRate[]) arrList.toArray(new OBInterestRate[0]);
	}
}
