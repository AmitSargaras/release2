/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.ContractFinancingAwarderTypeList;
import com.integrosys.cms.ui.common.ContractFinancingContractTypeList;
import com.integrosys.cms.ui.common.ContractFinancingSinkingFundList;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Prepares for editing
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PrepareContractFinancingCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "initEvent", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "initEvent", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE }, { "awarderTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "awarderTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "contractTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "contractTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "sinkingFundLabels", "java.util.List", REQUEST_SCOPE },
				{ "sinkingFundValues", "java.util.List", REQUEST_SCOPE }, };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			String initEvent = (String) hashMap.get("initEvent");
			if ((initEvent != null) && !initEvent.equals("")) {
				DefaultLogger.debug(this, "set initEvent now to:" + initEvent);
				resultMap.put("initEvent", initEvent);
				resultMap.put("tab", null); // clear service scope initial
			}

			ContractFinancingAwarderTypeList contractFinancingAwarderTypeList = ContractFinancingAwarderTypeList
					.getInstance();
			resultMap.put("awarderTypeLabels", contractFinancingAwarderTypeList.getContractFinancingAwarderTypeLabel());
			resultMap.put("awarderTypeValues", contractFinancingAwarderTypeList
					.getContractFinancingAwarderTypeProperty());

			ContractFinancingContractTypeList contractFinancingContractTypeList = ContractFinancingContractTypeList
					.getInstance();
			resultMap.put("contractTypeLabels", contractFinancingContractTypeList
					.getContractFinancingContractTypeLabel());
			resultMap.put("contractTypeValues", contractFinancingContractTypeList
					.getContractFinancingContractTypeProperty());

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());

			ContractFinancingSinkingFundList contractFinancingSinkingFundList = ContractFinancingSinkingFundList
					.getInstance();
			resultMap.put("sinkingFundLabels", contractFinancingSinkingFundList.getContractFinancingSinkingFundLabel());
			resultMap.put("sinkingFundValues", contractFinancingSinkingFundList
					.getContractFinancingSinkingFundProperty());

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;

		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}
}