package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

public class AddCurrencyList extends AbstractCommand implements ILmtCovenantConstants,ILineCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ "currencyRestrictionCode", "java.lang.String", REQUEST_SCOPE },
				{ "currencyRestrictionAmount", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", SERVICE_SCOPE }, 
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
			{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "currencyRestrictionCode", "java.lang.String", REQUEST_SCOPE },
			{ "currencyRestrictionAmount", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, 
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
	
	if(AbstractCommonMapper.isEmptyOrNull(event)) {
		event="add_currency_rest";
	}
	String index = (String) map.get("index");

	List vendor = null;
	List list = (List)map.get(SESSION_CURRENCY_LIST_LINE);
	 HashMap exceptionMap = new HashMap();
	String currencyRestrictionCode = (String)map.get("currencyRestrictionCode");
	String amount = (String)map.get("currencyRestrictionAmount");
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
     vNames = lmtDao.getRestrictedCurrencyListForLine(lmtTrxObj.getReferenceID());
	}
	if(list!=null && list.size()!=0)
	{	
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if((ven.getRestrictedCurrency().equals(currencyRestrictionCode))){
			flag = true;
			}
		}
	}
	
	if(currencyRestrictionCode==null || "".equals(currencyRestrictionCode)){
		exceptionMap.put("currencyRestrictionCode", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	if(amount==null || "".equals(amount)){
		exceptionMap.put("currencyRestrictionAmount", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	
String errorCode = null;
	
	if(!"".equals(amount)) {
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(amount, false, 1, IGlobalConstant.MAXIMUM_ALLOWED_VALUE_10))) {
			exceptionMap.put("currencyRestrictionAmount", new ActionMessage("error.amount.number.format"));
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
					if(ven.getRestrictedCurrency()!=null && ven.getRestrictedCurrency().equals(currencyRestrictionCode))
					{
						exceptionMap.put("currencyListError", new ActionMessage("error.string.duplicate.currency.name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
			}
		}
		
		for(int i = 0;i<list.size();i++)
		{
			OBLineCovenant ven = (OBLineCovenant)list.get(i);
			if(ven.getRestrictedCurrency().equals(currencyRestrictionCode))
					{
				exceptionMap.put("currencyListError", new ActionMessage("error.string.duplicate.currency.name"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
					}
		}
		
	}
	
	OBLineCovenant value = new OBLineCovenant();
	value.setRestrictedCurrency(currencyRestrictionCode);
	value.setRestrictedCurrencyAmount(amount);
	value.setRestrictedCurrencyInd(ICMSConstant.YES);
	
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	resultMap.put(SESSION_CURRENCY_LIST_LINE, list);
	
	resultMap.put("lmtTrxObj", lmtTrxObj);
	
	DefaultLogger.debug(this, " -------- Create Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}

}