package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;


public class DeleteBeneListCommand extends AbstractCommand implements ILmtCovenantConstants, ILineCovenantConstants {
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			    { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },	
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "beneName", "java.lang.String", REQUEST_SCOPE },
				{ "beneAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustName", "java.lang.String", REQUEST_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
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
			    { "trxID", "java.lang.String", REQUEST_SCOPE },	
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "beneName", "java.lang.String", REQUEST_SCOPE },
				{ "beneAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneCustName", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");
		if(AbstractCommonMapper.isEmptyOrNull(event)) {
			event="remove_bene_rest";
		}
		String index = (String) map.get("index");
		DefaultLogger.debug(this, "Inside doExecute() confirm delete Currency Command event "+event);
		DefaultLogger.debug(this, "Inside doExecute() confirm delete Currency Command  index "+index);
		List list = (List)map.get(SESSION_BENE_LIST_LINE);
		String beneName = (String)map.get("beneName");
		String beneAmount = (String)map.get("beneAmount");
		String beneCustName = (String)map.get("beneCustName");
		String beneCustId = (String)map.get("beneCustId");
		ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
		list.remove((Integer.parseInt(index))-1);
		
		resultMap.put(SESSION_BENE_LIST_LINE, list);
		resultMap.put("index", index);
		resultMap.put("event", event);
		resultMap.put("lmtTrxObj", lmtTrxObj);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}
