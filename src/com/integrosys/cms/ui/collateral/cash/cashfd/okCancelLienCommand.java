package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod;
import com.integrosys.cms.app.collateral.bus.type.cash.OBLien;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class okCancelLienCommand extends AbstractCommand{

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				  
               
				//{ "indexID", "java.lang.String", REQUEST_SCOPE },
				//{ "index", "java.lang.String", REQUEST_SCOPE },
				//{ "trxID", "java.lang.String", REQUEST_SCOPE },		
				//{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lcnNo", "java.lang.String", REQUEST_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				
				{ "collateralList", "java.util.List", SERVICE_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
	            { "countryNme", "java.lang.String", SERVICE_SCOPE },
	            {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },				
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
				//{ "systemCustomerId", "java.lang.String", REQUEST_SCOPE },
				//{ "system", "java.lang.String", REQUEST_SCOPE },
				     
				//{ "trxID", "java.lang.String", REQUEST_SCOPE },
				//{ "indexID", "java.lang.String", REQUEST_SCOPE },
				//{ "index", "java.lang.String", REQUEST_SCOPE },
				//{ "OBLien", "com.integrosys.cms.app.collateral.bus.type.cash.ILienMethod", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				//{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "lienNo", "java.lang.String", REQUEST_SCOPE },
				{ "lcnNo", "java.lang.String", REQUEST_SCOPE },
				{ "lienAmount", "java.lang.String", REQUEST_SCOPE },
				{ "serialNo", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },
	            {"serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE},
	            {"form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE},
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
	//	String indexID = (String) map.get("indexID");
		String event = (String) map.get("event");
		String lienNumber = (String) map.get("lienNo");
		String lcnNo = (String) map.get("lcnNo");
		String lienAmount = (String) map.get("lienAmount");
		String serialNo = (String) map.get("serialNo");
		String remark = (String) map.get("remark");
	//	String index = (String) map.get("index");
		
	//	int ind = Integer.parseInt(index)-1;
		//String systemCustomerId = (String) map.get("systemCustomerId");
		DefaultLogger.debug(this, "Inside doExecute() okCancelLienCommand "+event);

		//String lienNumber = (String) map.get("lienNo");
		//String lienAmount = (String) map.get("lienAmount");
		
		
	//	List list = (List)map.get("lienList");		
		
		/*//list.remove(ind);
		OBLien lien = new OBLien();
		if( lienNumber != null && ! lienNumber.equals("") )
		lien.setLienNumber(Long.parseLong(lienNumber));
		if( lienAmount != null && ! lienAmount.equals("") )
			lien.setLienAmount(Long.parseLong(lienAmount));
		
		
		//list.add(lien);
		
		
		list.add(ind,lien);*/
		
		//ILienMethod obLien = (OBLien)map.get("OBLien");
		//resultMap.put("OBLien", obLien);
		//resultMap.put("index", index);
		//resultMap.put("indexID", indexID);
		   ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");

	        if (itrxValue != null) {
	        	
	            String from_event = (String) map.get("from_event");
	            if ((from_event != null) && from_event.equals("read")) {
	            	resultMap.put("form.collateralObject", itrxValue.getCollateral());
	            } else {            	
	            	ICollateral col = itrxValue.getStagingCollateral();
	            	//col.setCurrencyCode(itrxValue.getCollateral().getCurrencyCode());
	            	col.setCollateralLocation(itrxValue.getStagingCollateral().getCollateralLocation());
	            	//col.setSecurityOrganization(itrxValue.getStagingCollateral().getSecurityOrganization());
	            	//col.setSecPriority(itrxValue.getStagingCollateral().getSecPriority());
	            	itrxValue.setStagingCollateral(col); 
	            	resultMap.put("form.collateralObject", itrxValue.getStagingCollateral());
	            }
	            resultMap.put("serviceColObj", itrxValue);
	        }
		
		List list = (List)map.get("lienList");
		list= null;
		resultMap.put("lienList", list);
		resultMap.put("lienNo", lienNumber);
		resultMap.put("lcnNo", lcnNo);
		resultMap.put("lienAmount", lienAmount);
		resultMap.put("serialNo", serialNo);
		resultMap.put("remark", remark);
		resultMap.put("event",event);
		
		resultMap.put("collateralList", map.get("collateralList"));
		resultMap.put("systemBankBranch", map.get("systemBankBranch"));
		resultMap.put("countryNme", map.get("countryNme"));
		//resultMap.put("lienList", list);
		
		DefaultLogger.debug(this, " -------- Create Successfull -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;			
	}
}
