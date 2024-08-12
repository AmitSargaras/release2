package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

public class LoadEditBeneData extends AbstractCommand{

	
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
				{ "beneName", "java.lang.String", REQUEST_SCOPE },
				{ "beneAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", SERVICE_SCOPE },
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
			{ "amount", "java.lang.String", REQUEST_SCOPE },
			{ "amount", "java.lang.String", SERVICE_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
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
	    list = (List)map.get("restBeneList");
		HashMap exceptionMap = new HashMap();
		ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
		String beneficiaryName = (String)map.get("beneName");
		String beneficiaryAmount = (String)map.get("beneAmount");
		String beneficiaryCustId = (String)map.get("beneCustId");
		String beneficiaryCustName = (String)map.get("beneCustName");
		DefaultLogger.debug(this, "Inside doExecute() LoadEditBeneCommand "+event);
		DefaultLogger.debug(this, "Inside doExecute() LoadEditBeneCommand "+index);
		resultMap.put("index",index);
		resultMap.put("event",event);
		resultMap.put("eventForEdit",(String)map.get("event"));
		resultMap.put("restBankList", map.get("restBankList"));
		resultMap.put("restCurrencyList", map.get("restCurrencyList"));
		resultMap.put("restBankList", map.get("restBankList"));
		resultMap.put("restDrawerList", map.get("restDrawerList"));
		resultMap.put("restDraweeList", map.get("restDraweeList"));
		resultMap.put("restBeneList", list);
		resultMap.put("beneficiaryName",beneficiaryName);
		resultMap.put("beneficiaryAmount",beneficiaryAmount);
		resultMap.put("beneficiaryCustId",beneficiaryCustId);
		resultMap.put("beneficiaryCustName",beneficiaryCustName);
		

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}

