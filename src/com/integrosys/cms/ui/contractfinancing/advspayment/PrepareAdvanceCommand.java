/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.ContractFinancingFacTypeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.FrequencyList;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PrepareAdvanceCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "initAdvEvent", "java.lang.String", REQUEST_SCOPE },
				{ "advanceIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "obActualAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "initAdvEvent", "java.lang.String", SERVICE_SCOPE },
				{ "advanceIndex", "java.lang.String", SERVICE_SCOPE },
				{ "facTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "facTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "frequencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "frequencyValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			String initAdvEvent = (String) hashMap.get("initAdvEvent");
			if ((initAdvEvent != null) && !initAdvEvent.equals("")) {
				DefaultLogger.debug(this, "set initAdvEvent now to:" + initAdvEvent);
				resultMap.put("initAdvEvent", initAdvEvent);
				resultMap.put("obAdvance", null); // clear service scope initial
				resultMap.put("obActualAdvance", null); // clear service scope
														// initial
			}

			String advanceIndex = (String) hashMap.get("advanceIndex");
			if (advanceIndex != null) {
				resultMap.put("advanceIndex", advanceIndex);
			}

			ContractFinancingFacTypeList contractFinancingFacTypeList = ContractFinancingFacTypeList.getInstance();
			resultMap.put("facTypeLabels", contractFinancingFacTypeList.getContractFinancingFacTypeLabel());
			resultMap.put("facTypeValues", contractFinancingFacTypeList.getContractFinancingFacTypeProperty());

			FrequencyList frequencyList = FrequencyList.getInstance();
			resultMap.put("frequencyLabels", frequencyList.getFrequencyLabel());
			resultMap.put("frequencyValues", frequencyList.getFrequencyProperty());

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}
