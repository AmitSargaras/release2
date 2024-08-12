package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.ui.collateral.CollateralConstant.INSURANCE_HISTORY_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_INSURANCE_HISTORY_KEY;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

public class PrepareViewInsuranceSessionCommand extends AbstractCommand {
	
	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{SESSION_INSURANCE_HISTORY_KEY, InsuranceHistorySearchResult.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{INSURANCE_HISTORY_KEY, InsuranceHistorySearchResult.class.getName(), FORM_SCOPE },
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(@SuppressWarnings("rawtypes") HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		
		InsuranceHistorySearchResult sr = (InsuranceHistorySearchResult) inputMap.get(SESSION_INSURANCE_HISTORY_KEY);
		resultMap.put(INSURANCE_HISTORY_KEY, sr);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
