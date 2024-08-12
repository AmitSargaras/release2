package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class AddLienCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
//				{ "trxID", "java.lang.String", REQUEST_SCOPE },	
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				{ "radioSelect", "java.lang.String", REQUEST_SCOPE },
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
	            { "facilityName", "java.lang.String", REQUEST_SCOPE},
	            { "facilityId", "java.lang.String", REQUEST_SCOPE},
	            { "lcnNo", "java.lang.String", REQUEST_SCOPE},	            
//			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				/*{ "event", "java.lang.String", REQUEST_SCOPE }, 
				{ "legalId", "java.lang.String", REQUEST_SCOPE },
				{ "legalSource", "java.lang.String", REQUEST_SCOPE },*/
				//{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", GLOBAL_SCOPE }
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
				/*{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },*/
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "radioSelect", "java.lang.String", REQUEST_SCOPE },
	            { "facilityName", "java.lang.String", REQUEST_SCOPE},
	            { "facilityId", "java.lang.String", REQUEST_SCOPE},
	            { "lcnNo", "java.lang.String", REQUEST_SCOPE},	
/*	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
	            { "facilityName", "java.lang.String", REQUEST_SCOPE},
	            { "facilityId", "java.lang.String", REQUEST_SCOPE},*/
				//{ "index", "java.lang.String", REQUEST_SCOPE },
				//{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
				//{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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
		String lienNumber = (String) map.get("lienNo");
		String lienAmount = (String) map.get("lienAmount");
		
		//Phase 3 CR:comma separated
		lienAmount=UIUtil.removeComma(lienAmount);
		
		String serialNo = (String) map.get("serialNo");
		String remark = (String) map.get("remark");
		String indexID = (String) map.get("indexID");
    	String systemName =(String) map.get("systemName");
    	List system =(List) map.get("system");
		//String index = (String) map.get("index");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this, "Inside doExecute() AddLienCommand "+event);
		
		String facilityName = (String) map.get("facilityName");
		String facilityId = (String) map.get("facilityId");
		String lcnNo = (String) map.get("lcnNo");		
		
		HashMap exceptionMap = new HashMap();
		
		boolean validatelienAmount = false;
		boolean validatelienNumber = false;
		
		validatelienNumber = ((lienNumber.equals(""))|| (lienNumber== null));
		if( validatelienNumber ){
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
			resultMap.put("lienList", (List)map.get("lienList"));
			resultMap.put("indexID", indexID);
	        exceptionMap.put("lienNo",  new ActionMessage("error.string.mandatory","Lien Number"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}else if(lienNumber.equals("0"))
		{
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
			resultMap.put("lienList", (List)map.get("lienList"));
			resultMap.put("indexID", indexID);
	        exceptionMap.put("lienNo",  new ActionMessage("error.string.not.zero","Lien Number"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		else
		{
			String errorCode = null;
			ActionErrors errors = new ActionErrors();
			//if (!(errorCode = Validator.checkNumber(lienNumber.toString(), true,0.0,IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, 3, locale)).equals(Validator.ERROR_NONE)) {
			
			
			if (!(errorCode = Validator.checkString(lienNumber, false, 1, 30)).equals(Validator.ERROR_NONE)) {
				resultMap.put("event",event);
				resultMap.put("radioSelect", (String) map.get("radioSelect"));
				resultMap.put("lienList", (List)map.get("lienList"));
				resultMap.put("indexID", indexID);
		        exceptionMap.put("lienNo",  new ActionMessage("error.string.lien.no","Lien Number"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}
		
		validatelienAmount = ((lienAmount.equals(""))|| (lienAmount== null));
		
		//Uma:changes for production issue: NumberFormatException while entering comma in Lien Amount
		if(!validatelienAmount){
			DecimalFormat formatter1 = new DecimalFormat();
			try {
				lienAmount=formatter1.parse(lienAmount).toString();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if( validatelienAmount ){
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
			resultMap.put("lienList", (List)map.get("lienList"));
			resultMap.put("indexID", indexID);
	        exceptionMap.put("lienAmount",  new ActionMessage("error.string.mandatory","Lien Amount"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}else if(Double.parseDouble(lienAmount)<=0)
		{
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
			resultMap.put("lienList", (List)map.get("lienList"));
			resultMap.put("indexID", indexID);
	        exceptionMap.put("lienAmount",  new ActionMessage("error.string.not.zero","Lien Amount"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		else
		{	
			String errorCode = null;
			ActionErrors errors = new ActionErrors();
			if (!(errorCode = Validator.checkNumber(lienAmount.toString(), true,0.0,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
				resultMap.put("lienList", (List)map.get("lienList"));
				resultMap.put("indexID", indexID);
		        exceptionMap.put("lienAmount",  new ActionMessage("error.string.amount.lienAmount","Lien Amount"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
	        }
			
		}
		/* validation for Serial NO 1. field is mandatory and numeric with 10 digit - By sachin patil*/
		boolean validateSerialNo = ((serialNo.equals(""))|| (serialNo== null));
		if( validateSerialNo ){
			resultMap.put("event",event);
			resultMap.put("radioSelect", (String) map.get("radioSelect"));
			resultMap.put("lienList", (List)map.get("lienList"));
			resultMap.put("indexID", indexID);
	        exceptionMap.put("serialNo",  new ActionMessage("error.string.mandatory","serial No"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		else
		{
			String errorCode = null;
			ActionErrors errors = new ActionErrors();
			//if (!(errorCode = Validator.checkNumber(lienNumber.toString(), true,0.0,IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, 3, locale)).equals(Validator.ERROR_NONE)) {
			
		
			if (!(errorCode = Validator.checkNumber(serialNo, false, 0, 9999999999L)).equals(Validator.ERROR_NONE)) {
				resultMap.put("event",event);
				resultMap.put("radioSelect", (String) map.get("radioSelect"));
				resultMap.put("lienList", (List)map.get("lienList"));
				resultMap.put("indexID", indexID);
		        exceptionMap.put("serialNo",  new ActionMessage("error.string.serial.no","serial No"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}
		if(!(lcnNo.equals("") || lcnNo.equals(null))) {
			String errorCode = null;
			ActionErrors errors = new ActionErrors();
			if (!(errorCode = Validator.checkNumber(lcnNo, false, 0, 99L)).equals(Validator.ERROR_NONE)) {
				resultMap.put("event",event);
				resultMap.put("radioSelect", (String) map.get("radioSelect"));
				resultMap.put("lienList", (List)map.get("lienList"));
				resultMap.put("indexID", indexID);
		        exceptionMap.put("lcnNo",  new ActionMessage("error.amount.format","LCN No"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}
		if(systemName.equals("BAHRAIN") || systemName.equals("HONGKONG") || systemName.equals("GIFTCITY")) {
			if(facilityName.equals("") || facilityName.equals(null) ){
				resultMap.put("event",event);
				resultMap.put("indexID", indexID);
		        exceptionMap.put("facilityName",  new ActionMessage("error.string.mandatory","Facility Name"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			if(facilityId.equals("") || facilityId.equals(null) ){
				resultMap.put("event",event);
				resultMap.put("indexID", indexID);
		        exceptionMap.put("facilityId",  new ActionMessage("error.string.mandatory","Facility Id"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
		}
		/*--- end validation of Serial No field*/
		List list = (List)map.get("lienList");
		if(list==null)
		{
		list = new ArrayList();
		}
		OBLien lien = new OBLien();
		lien.setActualLienId(1);
		if( (lienNumber != null && ! lienNumber.equals("")) && (serialNo != null && ! serialNo.equals("")) )
		{
			if(list.size()!=0)
			{
				for(int k = 0;k<list.size();k++)
				{
					OBLien lien1 = new OBLien();
					lien1 = (OBLien) list.get(k);
					if((lienNumber.equals(lien1.getLienNumber()))&& (serialNo.equals(lien1.getSerialNo())))
					{
						resultMap.put("event",event);
						resultMap.put("radioSelect", (String) map.get("radioSelect"));
						resultMap.put("lienList", (List)map.get("lienList"));
						resultMap.put("indexID", indexID);
				        exceptionMap.put("lienNo",  new ActionMessage("error.string.duplicate.lienNumber","Lien Number"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					
				}
				
			}
		lien.setLienNumber(lienNumber);
		lien.setSerialNo(serialNo);
		}
//		if( lienAmount != null && ! lienAmount.equals("") )
//		{
//			DecimalFormat formatter = new DecimalFormat();
//			 //int parsedFromString = formatter.parse(value);
//			 try {
//				 lienAmount = formatter.parse(lienAmount).toString();
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			lien.setLienAmount(Double.parseDouble(lienAmount));
//		}
		
			if( facilityName != null && ! facilityName.equals("") )
			{
			lien.setFacilityName(facilityName);
			}
			else{
				lien.setFacilityName("");
			}
			
			if( facilityId != null && ! facilityId.equals("") )
			{
			lien.setFacilityId(facilityId);
			}
			else{
				lien.setFacilityId("");
			}
		
		if( serialNo != null && ! serialNo.equals("") )
		{
		lien.setSerialNo(serialNo);
		}
		else{
			lien.setSerialNo("");
		}
		
		
		if( remark != null && ! remark.equals("") )
		{
		lien.setRemark(remark);
		}
		else{
			lien.setRemark("");
		}
		
		if( lcnNo != null && ! lcnNo.equals("") )
		{
		lien.setLcnNo(lcnNo);;
		}
		else{
			lien.setLcnNo("");
		}
		
		list.add(lien);
		
		//ILienMethod obLien = (OBLien)map.get("OBLien");
		//resultMap.put("OBLien", obLien);
		resultMap.put("event",event);
		resultMap.put("radioSelect", (String) map.get("radioSelect"));
		resultMap.put("lienList", list);
		resultMap.put("indexID", indexID);
		resultMap.put("facilityName",facilityName);
		resultMap.put("facilityId", facilityId);
		resultMap.put("lcnNo",lcnNo);
		//resultMap.put("index", index);
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;		
	}
}
