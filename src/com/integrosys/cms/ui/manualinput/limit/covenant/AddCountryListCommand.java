package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class AddCountryListCommand extends AbstractCommand implements ILmtCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				/*{ "countryRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "draweeRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "drawerRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "beneRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "combinedTenorRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "runningAccountRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "sellDownRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "availDateRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "moratoriumRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "goodsRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "currencyRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "bankRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "buyersRatingRestriction", "java.lang.String", REQUEST_SCOPE },
				{ "ecgcCoverRestriction", "java.lang.String", REQUEST_SCOPE },*/
				{ COVENANT_DETAIL_FORM, ILimitCovenant.class.getName(), FORM_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "countryRestrictionName", "java.lang.String", REQUEST_SCOPE },
				{ "countryRestrictionAmt", "java.lang.String", REQUEST_SCOPE },
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
			{ COVENANT_DETAIL_FORM,ILimitCovenant.class.getName(), FORM_SCOPE },
			{ "restBankList", "java.util.List", SERVICE_SCOPE },
			{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
			{ "restCountryList", "java.util.List", SERVICE_SCOPE },
			{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
			{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
			{ "restBeneList", "java.util.List", SERVICE_SCOPE },
			{ "countryRestrictionName", "java.lang.String", REQUEST_SCOPE },
			{ "countryRestrictionAmt", "java.lang.String", REQUEST_SCOPE },
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
	String index = (String) map.get("index");
	List list = (List)map.get("restCountryList");
	 HashMap exceptionMap = new HashMap();
	/*
	String draweeRestriction = (String)map.get("draweeRestriction");    
	String drawerRestriction = (String)map.get("drawerRestriction");
	String bankRestriction = (String)map.get("beneRestriction");
	String combinedTenorRestriction = (String)map.get("combinedTenorRestriction");
	String runningAccountRestriction = (String)map.get("runningAccountRestriction");
	String sellDownRestriction = (String)map.get("sellDownRestriction");
	String availDateRestriction = (String)map.get("availDateRestriction");
	String moratoriumRestriction = (String)map.get("moratoriumRestriction");
	String goodsRestriction = (String)map.get("goodsRestriction");
	String currencyRestriction = (String)map.get("currencyRestriction");	
	String ecgcCoverRestriction = (String)map.get("ecgcCoverRestriction");
	String buyersRatingRestriction = (String)map.get("buyersRatingRestriction");
	*/
	String countryRestrictionName = (String)map.get("countryRestrictionName");
	String amount = (String)map.get("countryRestrictionAmt");
	String lmtID = (String)map.get("lmtID");
	//String countryRestriction = (String)map.get("countryRestriction");
	ILimitTrxValue lmtTrxObj = (ILimitTrxValue) map.get("lmtTrxObj");
	//DefaultLogger.debug(this, "Size of list for event edit "+list.size());
	DefaultLogger.debug(this, "in AddCountry.java ==82==>> lmtTrxObj.getReferenceID();"+ lmtTrxObj.getReferenceID()); 
	DefaultLogger.debug(this, "in AddCountry.java ==90==>> event "+ event); 

	if(list==null)
	{
	list = new ArrayList();
	}
	boolean flag = false;
	ILimitDAO lmtDao = LimitDAOFactory.getDAO();
	List vNames = new ArrayList();
	if(lmtTrxObj.getReferenceID()!=null) {
     vNames = lmtDao.getRestrictedCountryList(lmtTrxObj.getReferenceID());
	}
	ILimitCovenant covenantDetail = (ILimitCovenant) map.get(COVENANT_DETAIL_FORM);

	if(list!=null && list.size()!=0)
	{	
		for(int i = 0;i<list.size();i++)
		{
		OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
			if((ven.getRestrictedCountryname()!=null && ven.getRestrictedCountryname().equals(countryRestrictionName))){
			flag = false;
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
				OBLimitCovenant ven = (OBLimitCovenant)vNames.get(i);
					if(ven.getRestrictedCountryname()!=null && ven.getRestrictedCountryname().equals(countryRestrictionName))
					{
						exceptionMap.put("countryListError", new ActionMessage("error.string.duplicate.country.details"));
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
			OBLimitCovenant ven = (OBLimitCovenant)list.get(i);
			if(ven.getRestrictedCountryname().equals(countryRestrictionName))
					{
				exceptionMap.put("countryListError", new ActionMessage("error.string.duplicate.country.details"));
				ICMSCustomerTrxValue partyGroupTrxValue = null;
				resultMap.put("request.ITrxValue", partyGroupTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
					}
		}
		
	}
	
	OBLimitCovenant value = new OBLimitCovenant();
	value.setRestrictedCountryname(countryRestrictionName);
	value.setRestrictedAmount(amount);
	value.setSingleCovenantInd(ICMSConstant.NO);
	value.setRestrictedCountryInd(ICMSConstant.YES);
	value.setIsNewEntry("Y");
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	resultMap.put("restCountryList", list);
	resultMap.put("restBankList", map.get("restBankList"));
	resultMap.put("restCurrencyList", map.get("restCurrencyList"));
	resultMap.put("restDrawerList", map.get("restDrawerList"));
	resultMap.put("restDraweeList", map.get("restDraweeList"));
	resultMap.put("restBeneList",  map.get("restBeneList"));
	resultMap.put("lmtTrxObj", lmtTrxObj);
	resultMap.put(COVENANT_DETAIL_FORM, covenantDetail);
	
	DefaultLogger.debug(this, " -------- Create Successfull -----------");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	return returnMap;		
}

}
