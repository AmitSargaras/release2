/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/debtors/PrepareDebtorsCommand.java,v 1.3 2005/08/12 11:16:03 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.debtors;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.collateral.TimeFreqList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/12 11:16:03 $ Tag: $Name: $
 */

public class PrepareDebtorsCommand extends AbstractCommand {
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
		return (new String[][] { { "debtorPeriodID", "java.util.Collection", REQUEST_SCOPE },
				{ "debtorPeriodValue", "java.util.Collection", REQUEST_SCOPE },
				{ "timeFreqID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "timeFreqValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "currencyCode", "java.util.Collection", REQUEST_SCOPE }, });
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

		DebtorPeriodList periodList = DebtorPeriodList.getInstance();
		result.put("debtorPeriodID", periodList.getDebtorPeriodID());
		result.put("debtorPeriodValue", periodList.getDebtorPeriodValue());

		CurrencyList currencyList = CurrencyList.getInstance();
		result.put("currencyCode", currencyList.getCountryValues());

		TimeFreqList timeFreqList = TimeFreqList.getInstance();
		result.put("timeFreqValue", timeFreqList.getTimeFreqValue());
		result.put("timeFreqID", timeFreqList.getTimeFreqID());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
