package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBIfscMethod;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class ConfirmDeleteBankingMethodCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */


	public String[][] getParameterDescriptor() {
		return (new String[][] {

				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
						{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },

				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "amount", "java.lang.String", REQUEST_SCOPE },
				{ "partyId", "java.lang.String", REQUEST_SCOPE },
				{ "banktype", "java.lang.String", REQUEST_SCOPE },
				{ "ifscFlag", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE } });
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
				/*
				 * { "customerOb",
				 * "com.integrosys.cms.app.customer.bus.OBCMSCustomer",
				 * REQUEST_SCOPE },
				 */

				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer",
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						FORM_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		List system = null;
		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		
		String index = (String) map.get("index");
		
		//long BankId = 0L;
		String bankId="";
		if((String) map.get("partyId") != null || (String) map.get("partyId") != "") {
			//BankId = Long.parseLong((String) map.get("partyId"));	
			bankId = (String) map.get("partyId");	
		}
		
		String bankType = (String) map.get("banktype");
		
		List branchList = (List) map.get("branchList");
		TreeMap soretedMap= new TreeMap();
		if("delete_banking_method_in_edit".equals(event) || "delete_banking_method_in_resubmit".equals(event)) {
			for(int i=0;i<branchList.size();i++)
			{
				OBBankingMethod ob=(OBBankingMethod)branchList.get(i);
				if("S".equals(ob.getBankType())){
					if(Long.toString(ob.getBankId()).equals(bankId))
						ob.setStatus("DISABLE");
						ob.setNodal("");
						ob.setLead("");
					soretedMap.put(ob.getSystembank().getSystemBankName().toUpperCase(),ob);
				}
				else if("O".equals(ob.getBankType())){
					if(Long.toString(ob.getBankId()).equals(bankId))
						ob.setStatus("DISABLE");
					    ob.setNodal("");
					    ob.setLead("");
					soretedMap.put(ob.getOtherbranch().getOtherBranchName().toUpperCase()+"+"+ob.getOtherbranch().getOtherBankCode(),ob);
				}
			}
		}else {
			for(int i=0;i<branchList.size();i++)
			{
				OBBankingMethod ob=(OBBankingMethod)branchList.get(i);
				if("S".equals(ob.getBankType())){
					if(!Long.toString(ob.getBankId()).equals(bankId))
					{
						soretedMap.put(ob.getSystembank().getSystemBankName().toUpperCase(),ob);
					}
				}
				else if("O".equals(ob.getBankType())){
					if(!Long.toString(ob.getBankId()).equals(bankId))
					{
						soretedMap.put(ob.getOtherbranch().getOtherBranchName().toUpperCase()+"+"+ob.getOtherbranch().getOtherBankCode(),ob);
					}
				}
			}
		}

		branchList = new ArrayList(soretedMap.values());
	//	branchList.remove((Integer.parseInt(index))-1); 
		String ifscFlag = (String) map.get("ifscFlag"); 
		List ifscBranchList = (List) map.get("ifscBranchList");
		//disable branch when event is edit
		if("true".equals(ifscFlag)&&null!=ifscBranchList) {
			int size = ifscBranchList.size();
			String ifscCode=(String) map.get("partyId");
			for(int j=0;j<ifscBranchList.size();j++){
				OBIfscMethod ob=(OBIfscMethod)ifscBranchList.get(j);
				if(ifscCode.equals(ob.getIfscCode())) {
					ob.setStatus("DISABLE");
					ob.setNodal("");
					ob.setLead("");
					ifscBranchList.set(j, ob);
				}
			}
		}
		//remove branch when event is create
		if("false".equals(ifscFlag)&&null!=ifscBranchList) {
			int size = ifscBranchList.size();
			String ifscCode=(String) map.get("partyId");
			for(int j=0;j<ifscBranchList.size();j++){
				OBIfscMethod ob=(OBIfscMethod)ifscBranchList.get(j);
				if(ifscCode.equals(ob.getIfscCode())) {
					ifscBranchList.remove(j);
				}
			}
		}
		resultMap.put("ifscBranchList", ifscBranchList);
		
		resultMap.put("OBCMSCustomer", obCustomer);
		resultMap.put("branchList", branchList);

		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
