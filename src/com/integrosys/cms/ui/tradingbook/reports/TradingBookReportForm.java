/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.reports;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: for trading book report Description: trading
 * book report
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class TradingBookReportForm extends TrxContextForm implements java.io.Serializable {

	private String searchDate = "";

	/**
	 * Description : get method for form to get the search date
	 * 
	 * @return searchDate
	 */

	public String getSearchDate() {
		return searchDate;
	}

	/**
	 * Description : set the search date
	 * 
	 * @param searchDate is the search date
	 */

	public void setSearchDate(String searchDate) {
		this.searchDate = searchDate;
	}

}
