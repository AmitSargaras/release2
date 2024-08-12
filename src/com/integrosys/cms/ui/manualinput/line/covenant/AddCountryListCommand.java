package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class AddCountryListCommand extends AbstractCommand implements ILmtCovenantConstants,ILineCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ "countryRestrictionName", "java.lang.String", REQUEST_SCOPE },
				{ "countryRestrictionAmt", "java.lang.String", REQUEST_SCOPE },
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
			{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
			{ "countryRestrictionName", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", SERVICE_SCOPE }, 
			{ "event", "java.lang.String", REQUEST_SCOPE }, 			
			{ "countryRestrictionAmt", "java.lang.String", REQUEST_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
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
		event="add_country_rest";
	}
	List list = (List)map.get(SESSION_COUNTRY_LIST_LINE);
	 HashMap exceptionMap = new HashMap();
	String countryRestrictionName = (String)map.get("countryRestrictionName");
	String amount = (String)map.get("countryRestrictionAmt");
	String lmtID = (String)map.get("lmtID");
	ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
	//DefaultLogger.debug(this, "Size of list for event edit "+list.size());
	DefaultLogger.debug(this, "in AddCountry.java ==82==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 
	if(list==null)
	{
	list = new ArrayList();
	}
	boolean flag = false;
	ILimitDAO lmtDao = LimitDAOFactory.getDAO();
	List vNames = new ArrayList();
	if(lmtTrxObj.getReferenceID()!=null) {
    vNames = lmtDao.getRestrictedCountryListForLine(lmtTrxObj.getReferenceID());
	}
	if(list!=null && list.size()!=0)
	{	
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if((ven.getRestrictedCountryname().equals(countryRestrictionName))){
			flag = true;
			}
		}
	}
	
	if(countryRestrictionName==null || "".equals(countryRestrictionName)){
		exceptionMap.put("countryRestrictionName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	if(amount==null || "".equals(amount)){
		exceptionMap.put("countryRestrictionAmt", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	
String errorCode = null;
	
	if(!"".equals(amount)) {
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkAmount(amount, true, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2,IGlobalConstant.DEFAULT_CURRENCY, Locale.getDefault()))) {
			exceptionMap.put("countryRestrictionAmt", new ActionMessage("error.value.amount.limit"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
	}	
	
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
					if(ven.getRestrictedCountryname()!=null && ven.getRestrictedCountryname().equals(countryRestrictionName))
					{
						exceptionMap.put("countryListError", new ActionMessage("error.string.duplicate.country.name"));
						ICMSCustomerTrxValue partyGroupTrxValue = null;
						resultMap.put("request.ITrxValue", partyGroupTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
			}
		}
		
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if(ven.getRestrictedCountryname().equals(countryRestrictionName))
					{
				exceptionMap.put("countryListError", new ActionMessage("error.string.duplicate.country.name"));
				ICMSCustomerTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
					}
		}
		
	}
	
	OBLineCovenant value = new OBLineCovenant();
	value.setRestrictedCountryname(countryRestrictionName);
	value.setRestrictedAmount(amount);
	value.setRestrictedCountryInd(ICMSConstant.YES);
	
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	resultMap.put(SESSION_COUNTRY_LIST_LINE, list);
	resultMap.put("lmtTrxObj", lmtTrxObj);
	
	DefaultLogger.debug(this, " -------- Create Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}

}
