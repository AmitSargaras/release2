package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.ui.collateral.CollateralConstant.INSURANCE_HISTORY_REPORT_CRITERIA;
import static com.integrosys.cms.ui.collateral.CollateralConstant.INSURANCE_HISTORY_REPORT_FILE_NAME;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SERVICE_COLLATERAL_OBJ;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_INSURANCE_HISTORY;
import static com.integrosys.cms.app.common.constant.ICMSConstant.FILE_NAME;
import static com.integrosys.cms.app.common.constant.ICMSConstant.REPORT_FILE;
import static com.integrosys.cms.ui.collateral.CollateralConstant.INSURANCE_HISTORY_KEY;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_INSURANCE_HISTORY_KEY;

import java.util.HashMap;
import java.util.Map;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class GenerateInsuranceHistoryReportCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
			{IGlobalConstant.GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName(), GLOBAL_SCOPE},
			{SERVICE_COLLATERAL_OBJ, ICollateralTrxValue.class.getName(), SERVICE_SCOPE},
			{SESSION_INSURANCE_HISTORY, InsuranceHistorySearchResult.class.getName(), REQUEST_SCOPE},
			{INSURANCE_HISTORY_REPORT_CRITERIA, InsuranceHistorySearchCriteria.class.getName(), REQUEST_SCOPE},
			{ "insuranceCompanyNameMap",Map.class.getName() ,SERVICE_SCOPE},
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] { 
			{INSURANCE_HISTORY_KEY, InsuranceHistorySearchResult.class.getName(), FORM_SCOPE },
			{SESSION_INSURANCE_HISTORY_KEY, InsuranceHistorySearchResult.class.getName(), SERVICE_SCOPE },
			{INSURANCE_HISTORY_REPORT_FILE_NAME, String.class.getName(), REQUEST_SCOPE},
			{ FILE_NAME, String.class.getName(), SERVICE_SCOPE },
			{ REPORT_FILE, String.class.getName(), SERVICE_SCOPE }, 
		};
	}

	public HashMap<String, Map<String, ?>> doExecute(@SuppressWarnings("rawtypes") HashMap inputMap)
			throws CommandProcessingException, CommandValidationException {

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		InsuranceHistorySearchCriteria criteria = (InsuranceHistorySearchCriteria) inputMap
				.get(INSURANCE_HISTORY_REPORT_CRITERIA);
		Map insuranceCompanyNameMap = (Map) inputMap.get("insuranceCompanyNameMap");
		
		Map<String, String> basicDataMap = new HashMap<String, String>();
		ICMSCustomer customer = (ICMSCustomer) inputMap.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		basicDataMap.put("customerId", customer.getCifId());
		basicDataMap.put("customerName", customer.getCustomerName());
		ICollateralTrxValue trxCollateral = (ICollateralTrxValue) inputMap.get(SERVICE_COLLATERAL_OBJ);
		ICollateral collateral = trxCollateral.getCollateral();
		basicDataMap.put("collaetralId", String.valueOf(collateral.getCollateralID()));
		basicDataMap.put("collateralType", collateral.getCollateralType().getTypeName());
		basicDataMap.put("collateralSubType", collateral.getCollateralSubType().getSubTypeDesc());
		IInsuranceBO insuranceBO = (IInsuranceBO) BeanHouse.get("insuranceBO");
		String fileName = "";
		try {
			fileName = insuranceBO.createInsuranceHistoryReport(criteria, basicDataMap,insuranceCompanyNameMap);
		}catch(Exception e) {
			fileName = "InsuranceHistory_Template.xlsx";
			System.out.println("Exception in insuranceBO.createInsuranceHistoryReport(criteria, basicDataMap,insuranceCompanyNameMap)..=>"+e.getMessage());
			e.printStackTrace();
		}
//		String fileName = insuranceBO.createInsuranceHistoryReport(criteria, basicDataMap,insuranceCompanyNameMap);
//		String fileName = "InsuranceHistory_Template.xlsx";
		InsuranceHistorySearchResult sr = (InsuranceHistorySearchResult) inputMap.get(SESSION_INSURANCE_HISTORY);
		
		resultMap.put(INSURANCE_HISTORY_KEY, sr);
		resultMap.put(SESSION_INSURANCE_HISTORY_KEY, sr);
		resultMap.put(FILE_NAME, fileName);
		resultMap.put(REPORT_FILE, PropertyManager.getValue("insurance.history.report.path")+fileName); 
		
		HashMap<String, Map<String, ?>> returnMap = new HashMap<String, Map<String, ?>>();
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
		
}
