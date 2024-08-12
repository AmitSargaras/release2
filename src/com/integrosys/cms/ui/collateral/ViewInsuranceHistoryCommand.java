package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

public class ViewInsuranceHistoryCommand extends AbstractCommand implements CollateralConstant {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{ INSURANCE_HISTORY_KEY, InsuranceHistorySearchCriteria.class.getName(), FORM_SCOPE },
			{ SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE },
			{ PARENT_PAGE, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{ SESSION_INSURANCE_HISTORY, InsuranceHistorySearchResult.class.getName(), REQUEST_SCOPE },
			{ INSURANCE_HISTORY_REPORT_CRITERIA, InsuranceHistorySearchCriteria.class.getName(), REQUEST_SCOPE },
			{ PARENT_PAGE, String.class.getName(), SERVICE_SCOPE },
			{ SESSION_COLLATERAL_ID, String.class.getName(), SERVICE_SCOPE}
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		InsuranceHistorySearchCriteria criteria = (InsuranceHistorySearchCriteria) inputMap.get(INSURANCE_HISTORY_KEY);
		
		InsuranceGCJdbcImpl insuranceGcJdbc = (InsuranceGCJdbcImpl) BeanHouse.get("insuranceGcJdbc");
		System.out.println("ViewInsuranceHistoryCommand=>Before insuranceGcJdbc.getInsuranceHistory(criteria)...");
		InsuranceHistorySearchResult sr = insuranceGcJdbc.getInsuranceHistory(criteria);
		
		ICollateralTrxValue collateralTrx = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		long collateralId = collateralTrx.getCollateral().getCollateralID();
		
		String parentPage = (String)inputMap.get(PARENT_PAGE);
		String sessionParentPage = (String)inputMap.get(SESSION_PARENT_PAGE);
		
		resultMap.put(SESSION_INSURANCE_HISTORY, sr);
		resultMap.put(INSURANCE_HISTORY_REPORT_CRITERIA, criteria);		
		resultMap.put(PARENT_PAGE, StringUtils.isNotBlank(parentPage)?parentPage:sessionParentPage);
		resultMap.put(SESSION_COLLATERAL_ID, collateralId);
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
