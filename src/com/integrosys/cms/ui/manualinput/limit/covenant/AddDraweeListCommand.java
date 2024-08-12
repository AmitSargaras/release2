package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AddDraweeListCommand extends AbstractCommand implements ILmtCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
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
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{"exceptionMap","java.util.HashMap",REQUEST_SCOPE},
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
			{ "draweeName", "java.lang.String", REQUEST_SCOPE },
			{ "draweeAmount", "java.lang.String", REQUEST_SCOPE },
			{ "draweeCustId", "java.lang.String", REQUEST_SCOPE },
			{ "draweeCustName", "java.lang.String", REQUEST_SCOPE },
			{"exceptionMap","java.util.HashMap",REQUEST_SCOPE},
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
	String index = (String) map.get("index");

	List vendor = null;
	List list = (List)map.get("restDraweeList");
	 HashMap exceptionMap = new HashMap();
	String draweeName = (String)map.get("draweeName");
	String draweeAmount = (String)map.get("draweeAmount");
	String draweeCustId = (String)map.get("draweeCustId");
	String draweeCustName = (String)map.get("draweeCustName");

	String lmtID = (String)map.get("lmtID");
	ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
	//DefaultLogger.debug(this, "Size of list for event edit "+list.size());
	DefaultLogger.debug(this, "in AddCurrency.java ==82==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 
	if(list==null)
	{
	list = new ArrayList();
	}
	boolean flag = false;
	ILimitDAO lmtDao = LimitDAOFactory.getDAO();
	List vNames = new ArrayList();
	if(lmtTrxObj.getReferenceID()!=null) {
    vNames = lmtDao.getRestrictedCustomerList(lmtTrxObj.getReferenceID());
	}
	if(list!=null && list.size()!=0)
	{	
		for(int i = 0;i<list.size();i++)
		{
		OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
		if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
				&&ven.getDraweeAmount()!=null) {
		if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
				&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount)){
			flag = false;
			}
		}
		}
	}
	
	/*if(draweeName==null || "".equals(draweeName)){
		exceptionMap.put("draweeName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	
	if(draweeAmount==null || "".equals(draweeAmount)){
		exceptionMap.put("draweeAmount", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	if(draweeCustId==null || "".equals(draweeCustId)){
		exceptionMap.put("draweeCustId", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	/*if(draweeCustName==null || "".equals(draweeCustName)){
		exceptionMap.put("draweeCustName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	
String errorCode = null;
	

if (!("".equals(draweeCustId)) && !(isNumeric(draweeCustId))) {
	exceptionMap.put("draweeCustId", new ActionMessage("error.string.invalidNumbers"));
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
}
	if(!draweeAmount.equals("") && !"".equals(draweeAmount)) {
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(draweeAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
			exceptionMap.put("draweeAmount", new ActionMessage("error.string.serial.no24"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
	}
	if(list.size() >= 20)
	{
		exceptionMap.put("draweeName", new ActionMessage("error.drawee.twenty.allowed"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	}
	resultMap.put("exceptionMap",exceptionMap);
	if(exceptionMap.size()>0) {
		return returnMap;
	}
	
	if(!flag)
	{
		if(vNames!=null && vNames.size()!=0)
		{	
			for(int i = 0;i<vNames.size();i++)
			{
				OBLimitCovenant ven = (OBLimitCovenant)vNames.get(i);
				if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
						&&ven.getDraweeAmount()!=null) {
					if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
							&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount))	
					{
						{
							exceptionMap.put("draweeListError", new ActionMessage("error.string.duplicate.drawee.details"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
					}
				}
			}
		}
		for(int i = 0;i<list.size();i++)
		{
			OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
			if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
					&&ven.getDraweeAmount()!=null) {
				if(ven.getDraweeName().equals(draweeName)&&ven.getDraweeCustId().equals(draweeCustId)
						&&ven.getDraweeCustName().equals(draweeCustName)&&ven.getDraweeAmount().equals(draweeAmount))
				{
					exceptionMap.put("draweeListError", new ActionMessage("error.string.duplicate.drawee.details"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
		}


	}
	
	
	
	OBLimitCovenant value = new OBLimitCovenant();
	value.setDraweeName(draweeName);
	value.setDraweeAmount(draweeAmount);
	value.setDraweeCustId(draweeCustId);
	value.setDraweeCustName(draweeCustName);
	value.setDraweeInd(ICMSConstant.YES);
	value.setIsNewEntry("Y");
	
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	resultMap.put("restCurrencyList", map.get("restCurrencyList"));
	resultMap.put("restCountryList", map.get("restCountryList"));
	resultMap.put("restBankList", map.get("restBankList"));
	resultMap.put("restDraweeList",list);
	resultMap.put("restDrawerList", map.get("restDrawerList"));
	resultMap.put("restBeneList", map.get("restBeneList"));
	resultMap.put("draweeName",draweeName);
	resultMap.put("draweeAmount",draweeAmount);
	resultMap.put("draweeCustId",draweeCustId);
	resultMap.put("draweeCustName",draweeCustName);

	resultMap.put("lmtTrxObj", lmtTrxObj);
	
	DefaultLogger.debug(this, " -------- Create Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}
	
	public static boolean isNumeric(String s){
		if(null!=s && !s.isEmpty()){
	    String pattern= "^[0-9]*$";
	        if(s.matches(pattern)){
	            return true;
	        }
	        
		}
		
	    return false;   
	}

}
