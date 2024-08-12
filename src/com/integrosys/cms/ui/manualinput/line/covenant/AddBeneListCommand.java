package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

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

public class AddBeneListCommand extends AbstractCommand implements ILineCovenantConstants {
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				
				{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
				{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
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
			{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
			{ "beneficiaryName", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryAmount", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryCustId", "java.lang.String", REQUEST_SCOPE },
			{ "beneficiaryCustName", "java.lang.String", REQUEST_SCOPE },
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
		event="add_bene_rest";
	}
	List vendor = null;
	List list = (List)map.get(SESSION_BENE_LIST_LINE);
	 HashMap exceptionMap = new HashMap();
	 String beneficiaryName = (String)map.get("beneficiaryName");
		String beneficiaryAmount = (String)map.get("beneficiaryAmount");
		String beneficiaryCustId = (String)map.get("beneficiaryCustId");
		String beneficiaryCustName = (String)map.get("beneficiaryCustName");

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
			if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
					&&ven.getBeneAmount()!=null) {
				if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
						&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount))	
				{
					flag = true;
				}
			}
		}
	}
	
	
	/*if(beneficiaryName==null || "".equals(beneficiaryName)){
		exceptionMap.put("beneficiaryName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	
	if(beneficiaryAmount==null || "".equals(beneficiaryAmount)){
		exceptionMap.put("beneficiaryAmount", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	if(beneficiaryCustId==null ||  "".equals(beneficiaryCustId)){
		exceptionMap.put("beneficiaryCustId", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}
	
	/*if(beneficiaryCustName==null || "".equals(beneficiaryCustName)){
		exceptionMap.put("beneficiaryCustName", new ActionMessage("error.string.mandatory"));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			}*/
	
String errorCode = null;

if (!("".equals(beneficiaryCustId)) && !(isNumeric(beneficiaryCustId))) {
	exceptionMap.put("beneficiaryCustId", new ActionMessage("error.string.invalidNumbers"));
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
}
	
	if(!beneficiaryAmount.equals("") && !"".equals(beneficiaryAmount)) {
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkNumber(beneficiaryAmount, false, 0.0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_24_2))) {
			exceptionMap.put("beneficiaryAmount", new ActionMessage("error.string.serial.no24"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		}
	}	
	if(list.size() >= 20)
	{
		exceptionMap.put("beneficiaryName", new ActionMessage("error.beneficiary.twenty.allowed"));
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
				if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
						&&ven.getBeneAmount()!=null) {
					if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
							&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount))	
				{
						exceptionMap.put("beneListError", new ActionMessage("error.string.duplicate.bene.details"));
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
			if(ven.getBeneName()!=null && ven.getBeneCustId()!=null && ven.getBeneCustName()!=null
					&&ven.getBeneAmount()!=null) {
				if(ven.getBeneName().equals(beneficiaryName)&&ven.getBeneCustId().equals(beneficiaryCustId)
						&&ven.getBeneCustName().equals(beneficiaryCustName)&&ven.getBeneAmount().equals(beneficiaryAmount))	
				{
					exceptionMap.put("beneListError", new ActionMessage("error.string.duplicate.bene.details"));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
		}
		
	}
	
	OBLineCovenant value = new OBLineCovenant();
	value.setBeneName(beneficiaryName);
	value.setBeneAmount(beneficiaryAmount);
	value.setBeneCustId(beneficiaryCustId);
	value.setBeneCustName(beneficiaryCustName);
	value.setBeneInd(ICMSConstant.YES);
	
	list.add(value);
	resultMap.put("index",index);
	resultMap.put("event",event);
	
	resultMap.put(SESSION_BENE_LIST_LINE, list);


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
