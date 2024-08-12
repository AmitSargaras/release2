/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/interestrate/MaintainInterestRateForm.java,v 1 2007/02/08 Jerlin Exp $
 */
package com.integrosys.cms.ui.interestrate;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Interest Rate Description: Have set and get method to store the screen
 * value and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class MaintainInterestRateForm extends TrxContextForm implements java.io.Serializable {

	private String monthYear = "";

	private String typeInterestRates = "";

	private String typeInterestRatesDesc = "";

	private String[] intRateDate;

	private String[] intRatePercent;

	/**
	 * Description : get method for form to get the type of interest rate
	 * 
	 * @return typeInterestRates
	 */

	public String getTypeInterestRates() {
		return typeInterestRates;
	}

	/**
	 * Description : set the type of interest rate
	 * 
	 * @param typeInterestRates is the type of interest rate value
	 */

	public void setTypeInterestRates(String typeInterestRates) {
		this.typeInterestRates = typeInterestRates;
	}

	/**
	 * Description : get method for form to get the type of interest rate
	 * description
	 * 
	 * @return typeInterestRatesDesc
	 */

	public String getTypeInterestRatesDesc() {
		if (typeInterestRates.equals("F")) {
			typeInterestRatesDesc = "Fed Rates";
		}
		else {
			typeInterestRatesDesc = "KLIBO";
		}
		return typeInterestRatesDesc;
	}

	/**
	 * Description : get method for form to get the month and year value
	 * 
	 * @return monthYear
	 */

	public Date getMonthYearDt() {

		String[] mthYr = new String[2];
		StringTokenizer token = new StringTokenizer(monthYear, "|");
		mthYr[0] = token.nextToken();
		mthYr[1] = token.nextToken();
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>mthYr[0] : " +
		// mthYr[0]);
		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>mthYr[1] : " +
		// mthYr[1]);

		Calendar cal = new GregorianCalendar(Integer.parseInt(mthYr[1]), Integer.parseInt(mthYr[0]) - 1, 1);

		// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>cal.getTime() : " +
		// cal.getTime());

		return cal.getTime();

	}

	public String getMonthYear() {

		return monthYear;

	}

	/**
	 * Description : set the month and year value
	 * 
	 * @param monthYearStr is the month and year value
	 */

	public void setMonthYear(String monthYearStr) {

		this.monthYear = monthYearStr;

	}

	/**
	 * Description : get method for form to get the interest rate percentage
	 * 
	 * @return intRatePercent
	 */

	public String[] getIntRatePercent() {
		return intRatePercent;
	}

	public String[] getIntRateDate() {
		return intRateDate;
	}

	/**
	 * Description : set the interest rate percentage value
	 * 
	 * @param intRatePercent is the interest rate percentage value
	 */

	public void setIntRatePercent(String[] intRatePercent) {
		this.intRatePercent = intRatePercent;
	}

	public void setIntRateDate(String[] intRateDate) {
		this.intRateDate = intRateDate;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = {

		{ "InitialInterestRate", "com.integrosys.cms.ui.interestrate.MaintainInterestRateMapper" },

		{ "InterestRates", "com.integrosys.cms.ui.interestrate.MaintainInterestRateMapper" },

		{ "InterestRateTrxValue", "com.integrosys.cms.ui.interestrate.MaintainInterestRateMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		{ "mapper", "com.integrosys.cms.ui.interestrate.MaintainInterestRateMapper" }

		};

		return input;

	}

}
