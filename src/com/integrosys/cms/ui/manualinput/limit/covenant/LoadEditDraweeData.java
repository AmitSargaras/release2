package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

public class LoadEditDraweeData extends AbstractCommand{

	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "draweeName", "java.lang.String", REQUEST_SCOPE },
				{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
				{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "index", "java.lang.String", REQUEST_SCOPE },
			});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
			{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
			{ "restCountryList", "java.util.List", SERVICE_SCOPE },
			{ "restBankList", "java.util.List", SERVICE_SCOPE },
			{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
			{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
			{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
			{ "restBeneList", "java.util.List", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "draweeName", "java.lang.String", REQUEST_SCOPE },
			{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
			{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
			{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
			{"eventForEdit", "java.lang.String", REQUEST_SCOPE },
			});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		String index = (String) map.get("index");
		int ind = Integer.parseInt(index) - 1;
		List list = new ArrayList();
	    list = (List)map.get("restDraweeList");
		HashMap exceptionMap = new HashMap();
		ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
		String draweeName = (String)map.get("draweeName");
		String draweeAmount = (String)map.get("draweeAmount");
		String draweeCustId = (String)map.get("draweeCustId");
		String draweeCustName = (String)map.get("draweeCustName");
		DefaultLogger.debug(this, "Inside doExecute() LoadEditdraweeCommand "+event);
		DefaultLogger.debug(this, "Inside doExecute() LoadEditdraweeCommand "+index);
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("eventForEdit",(String)map.get("event"));
		resultMap.put("restBankList", map.get("restBankList"));
		resultMap.put("restCurrencyList", map.get("restCurrencyList"));
		resultMap.put("restBankList", map.get("restBankList"));
		resultMap.put("restDrawerList", map.get("restDrawerList"));
		resultMap.put("restBeneList", map.get("restBeneList"));
		resultMap.put("restDraweeList", list);
		resultMap.put("draweeName",draweeName);
		resultMap.put("draweeAmount",draweeAmount);
		resultMap.put("draweeCustId",draweeCustId);
		resultMap.put("draweeCustName",draweeCustName);
		

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}

