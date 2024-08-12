package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;

public class AddDrawerListCommand extends AbstractCommand implements ILmtCovenantConstants, ILineCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "drawerName", "java.lang.String", REQUEST_SCOPE },
				{ "drawerAmount", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustId", "java.lang.String", REQUEST_SCOPE },
				{ "drawerCustName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE }, 
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
			{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "drawerName", "java.lang.String", REQUEST_SCOPE },
			{ "drawerAmount", "java.lang.String", REQUEST_SCOPE },
			{ "drawerCustId", "java.lang.String", REQUEST_SCOPE },
			{ "drawerCustName", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", SERVICE_SCOPE }, 
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
			{ "amount", "java.lang.String", REQUEST_SCOPE },
			{ "amount", "java.lang.String", SERVICE_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
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
	if(AbstractCommonMapper.isEmptyOrNull(event)) {
		event="add_drawer_rest";
	}
	List vendor = null;
	List list = (List)map.get(SESSION_DRAWER_LIST_LINE);
	 HashMap exceptionMap = new HashMap();
	String drawerName = (String)map.get("drawerName");
	String drawerAmount = (String)map.get("drawerAmount");
	String drawerCustId = (String)map.get("drawerCustId");
	String drawerCustName = (String)map.get("drawerCustName");

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
    vNames = lmtDao.getRestrictedCustomerListForLine(lmtTrxObj.getReferenceID());
	}
	if(list!=null && list.size()!=0)
	{	
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
					&&ven.getDraweeAmount()!=null) {
				if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerCustId)
						&&ven.getDrawerCustName().equals(drawerCustId)&&ven.getDrawerAmount().equals(drawerAmount))	
				{
					flag = true;
				}
			}
		}
	}
	
	/*if(drawerName==null || "".equals(drawerName)){
		exceptionMap.put("drawerName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	
	if(drawerAmount==null || "".equals(drawerAmount)){
		exceptionMap.put("drawerAmount", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	if(drawerCustId==null || "".equals(drawerCustId)){
		exceptionMap.put("drawerCustId", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	/*if(drawerCustName==null || "".equals(drawerCustName)){
		exceptionMap.put("drawerCustName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	String errorCode = null;
	if (!("".equals(drawerCustId)) && !(isNumeric(drawerCustId))) {
		exceptionMap.put("drawerCustId", new ActionMessage("error.string.invalidNumbers"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	}
	
	if(!drawerAmount.equals("") && !"".equals(drawerAmount)) {
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(drawerAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
			exceptionMap.put("drawerAmount", new ActionMessage("error.string.serial.no24"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
	}
	if(list.size() >= 20)
	{
		exceptionMap.put("drawerName", new ActionMessage("error.drawer.twenty.allowed"));
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
				OBLineCovenant ven = (OBLineCovenant)vNames.get(i);
				if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
						&&ven.getDraweeAmount()!=null) {
				if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerName)
						&&ven.getDrawerCustName().equals(drawerName)&&ven.getDrawerAmount().equals(drawerName))	
						{
					
						exceptionMap.put("drawerListError", new ActionMessage("error.string.duplicate.drawer.details"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
			}
		}
		
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if(ven.getDraweeName()!=null && ven.getDraweeCustId()!=null && ven.getDraweeCustName()!=null
					&&ven.getDraweeAmount()!=null) {
				if(ven.getDrawerName().equals(drawerName)&&ven.getDrawerCustId().equals(drawerName)
						&&ven.getDrawerCustName().equals(drawerName)&&ven.getDrawerAmount().equals(drawerName))	
				{
					exceptionMap.put("drawerListError", new ActionMessage("error.string.duplicate.drawer.details"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
		}
		
	}
	
	OBLineCovenant value = new OBLineCovenant();
	value.setDrawerName(drawerName);
	value.setDrawerAmount(drawerAmount);
	value.setDrawerCustId(drawerCustId);
	value.setDrawerCustName(drawerCustName);
	value.setDrawerInd(ICMSConstant.YES);
	
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	
	resultMap.put("restCurrencyListForLine", map.get("restCurrencyListForLine"));
	resultMap.put("restCountryListForLine", map.get("restCountryListForLine"));
	resultMap.put("restBankListForLine", map.get("restBankListForLine"));
	resultMap.put(SESSION_DRAWER_LIST_LINE,list);
	resultMap.put("restDraweeListForLine", map.get("restDraweeListForLine"));
	resultMap.put("restBeneListForLine", map.get("restBeneListForLine"));

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
