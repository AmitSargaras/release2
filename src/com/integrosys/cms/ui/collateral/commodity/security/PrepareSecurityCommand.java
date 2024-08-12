/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/PrepareSecurityCommand.java,v 1.4 2004/06/28 14:11:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.ChargeTypeList;
import com.integrosys.cms.ui.collateral.ExchangeControlList;
import com.integrosys.cms.ui.collateral.LEList;
import com.integrosys.cms.ui.collateral.SecEnvRiskyList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/06/28 14:11:56 $ Tag: $Name: $
 */

public class PrepareSecurityCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[0][];
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "LEValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "chargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "secRiskyValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "ExchangeControlValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
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

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		LEList leList = LEList.getInstance();
		result.put("LEID", leList.getLEID());
		result.put("LEValue", leList.getLEValue());

		SecEnvRiskyList secRiskyList = SecEnvRiskyList.getInstance();
		result.put("secRiskyID", secRiskyList.getSecEnvRiskyID());
		result.put("secRiskyValue", secRiskyList.getSecEnvRiskyValue());

		CountryList countryList = CountryList.getInstance();
		result.put("countryLabels", countryList.getCountryLabels());
		result.put("countryValues", countryList.getCountryValues());

		ChargeTypeList chargeTypeList = ChargeTypeList.getInstance();
		result.put("chargeID", chargeTypeList.getChargeTypeID());
		result.put("chargeValue", chargeTypeList.getChargeTypeValue());

		ExchangeControlList list2 = ExchangeControlList.getInstance();
		result.put("ExchangeControlID", list2.getExchangeControlID());
		result.put("ExchangeControlValue", list2.getExchangecontrolValue());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
