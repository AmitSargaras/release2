package com.integrosys.cms.ui.feed.propertyindex.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * This class implements command
 */
public class PreparePropertyIndexItemCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	//
	//
	// public String[][] getResultDescriptor() {
	// return new String[][]{
	// {"currencyLabels", "java.util.Collection", REQUEST_SCOPE},
	// {"currencyValues", "java.util.Collection", REQUEST_SCOPE},
	// {"currency.description", "java.lang.String", REQUEST_SCOPE}};
	// }
	//

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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			// String currencyCode =
			// (String)map.get(PropertyIndexItemForm.MAPPER);
			//
			// CurrencyList currencyList = CurrencyList.getInstance();
			// Collection currencyLabels = currencyList.getCurrencyLabels();
			// Collection currencyValues = currencyList.getCountryValues();
			// String currencyDescription = null;
			//
			// if (currencyCode == null) {
			// // First time this command is executed.
			// currencyCode = (String)currencyValues.iterator().next();
			// }
			//
			// currencyDescription = currencyList.getCountryName(currencyCode);
			//
			// resultMap.put("currencyLabels", currencyLabels);
			// resultMap.put("currencyValues", currencyValues);
			// resultMap.put("currency.description", currencyDescription);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
