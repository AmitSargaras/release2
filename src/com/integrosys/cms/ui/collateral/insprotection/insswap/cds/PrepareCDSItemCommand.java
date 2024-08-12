/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/insswap/cds/PrepareCDSItemCommand.java,v 1.1 2005/09/29 09:41:57 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.BookingLocationList;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:41:57 $ Tag: $Name: $
 */

public class PrepareCDSItemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {});

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "currencyCode", "java.util.Collection", REQUEST_SCOPE },
				{ "countryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "bookingLocationValue", "java.util.Collection", REQUEST_SCOPE },
				{ "freqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "freqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		Collection list2 = CurrencyList.getInstance().getCountryValues();
		result.put("currencyCode", list2);

		CountryList list = CountryList.getInstance();
		result.put("countryLabels", list.getCountryLabels());
		result.put("countryValues", list.getCountryValues());

		BookingLocationList bookingLoc = BookingLocationList.getInstance();

		result.put("bookingLocationValue", bookingLoc.getBookingLocationValue());

		TimeFreqList freqList = TimeFreqList.getInstance();
		result.put("freqValue", freqList.getTimeFreqValue());
		result.put("freqID", freqList.getTimeFreqID());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
