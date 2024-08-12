/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 04:12:38 $ Tag: $Name: $
 */
public class EditFacilityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public EditFacilityMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "secTrxID", "java.lang.String", REQUEST_SCOPE },
				 { "go", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "facDocDesc", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureCount", "java.lang.String", REQUEST_SCOPE },
				{ "facDocTenureType", "java.lang.String", REQUEST_SCOPE },
				{ "facDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "secTrxIDSession", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE }, { "subType", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "facDocCode", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDesc", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCount", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureType", "java.lang.String", SERVICE_SCOPE },
				{ "secTrxID", "java.lang.String", REQUEST_SCOPE },
				{ "facDocCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDescSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCountSession", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "secTrxIDSession", "java.lang.String", SERVICE_SCOPE },
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "dynamicPropertySetupList", "com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup[]", SERVICE_SCOPE },
				{ "entryCodesArrayList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "idList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "categoryList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "tenureTypeList", "java.util.List", SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
				{ "facilityTypeList", "java.util.List", SERVICE_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "facCategory", "java.lang.String", SERVICE_SCOPE },
				{ "facType", "java.lang.String", SERVICE_SCOPE },
				{ "facSystem", "java.lang.String", SERVICE_SCOPE },});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
	//	String secType = (String) map.get("secType");
		String subType = (String) map.get("subType");
		DefaultLogger.debug(this, "subType in edit================" + subType);
		try {
			DefaultLogger.debug(this, "Map input keyset " + map.keySet());
			String secTrxID = (String) map.get("secTrxID");
			DefaultLogger.debug(this, "TrxiD before backend call" + secTrxID);
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			
			// get result list from search criteria.
			String facDocCode = (String)map.get("facDocCode");
			String facDocDesc = (String)map.get("facDocDesc");
			String facDocTenureCount=(String) map.get("facDocTenureCount");
			String facDocTenureType=(String) map.get("facDocTenureType");
			String facDocCodeSession = (String)map.get("facDocCodeSession");
			String facDocDescSession = (String)map.get("facDocDescSession");
			String facDocTenureCountSession=(String) map.get("facDocTenureCountSession");
			String facDocTenureTypeSession=(String) map.get("facDocTenureTypeSession");
			String secTrxIDSession = (String) map.get("secTrxIDSession");
			String go = (String)map.get("go");
			String event = (String)map.get("event");
			List items=new ArrayList();
		 	// if go button is clicked then put values in session
		 	if(go!=null){
		 	if(go.equalsIgnoreCase("y")){
		 		facDocCodeSession=facDocCode;
		 		facDocDescSession=facDocDesc;
		 		facDocTenureCountSession=facDocTenureCount;
		 		facDocTenureTypeSession=facDocTenureType;
		 	}
		 	if(go.equalsIgnoreCase("s")){
		 	secTrxIDSession=secTrxID;
		 	facDocCodeSession=facDocDescSession=facDocTenureCountSession=facDocTenureTypeSession=""; // remove values from session
		 	}
		 	}
		 	// get values from session.
		 	facDocCode=facDocCodeSession;
	 		facDocDesc=facDocDescSession;
	 		facDocTenureCount=facDocTenureCountSession;
	 		facDocTenureType=facDocTenureTypeSession;
	 		secTrxID=secTrxIDSession;
	 		
	 		if(null!=facDocCode)
	 			facDocCode=facDocCode.trim();
	 		if(null!=facDocDesc)
	 			facDocDesc=facDocDesc.trim();
			if(null!=facDocTenureCount)
				facDocTenureCount=facDocTenureCount.trim();
			
	 		resultMap.put("facDocCode", facDocCode);
	 		resultMap.put("facDocDesc", facDocDesc);
	 		resultMap.put("facDocTenureCount", facDocTenureCount);
	 		resultMap.put("facDocTenureType", facDocTenureType);
	 		resultMap.put("facDocCodeSession", facDocCodeSession);
	 		resultMap.put("facDocDescSession", facDocDescSession);	
	 		resultMap.put("facDocTenureCountSession", facDocTenureCountSession);
	 		resultMap.put("facDocTenureTypeSession", facDocTenureTypeSession);
	 		resultMap.put("secTrxID", secTrxID);
	 		resultMap.put("secTrxIDSession", secTrxIDSession);
	 		
	 		ITemplate iTemp=null;
	 		ITemplateTrxValue secTrxVal = null;
	 		String facCategory ="";
			String facType = "";
			String facSystem = "";
			String facCode = "";
	 		if(null!=secTrxID)
	 		 secTrxVal = proxy.getTemplateByTrxID(secTrxID);
	 		facCode = secTrxVal.getStagingTemplate().getCollateralType();
	 		if(null!=secTrxVal){
			if(null!=secTrxVal.getTemplate()){
				if(null!=secTrxVal.getTemplate().getTemplateItemList()){
					ITemplateItem[] iTempItem=secTrxVal.getTemplate().getTemplateItemList();
					if(iTempItem.length!=0){
					for(int i=0;i<iTempItem.length;i++){
						IItem obItem=iTempItem[i].getItem();
						if(facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase(obItem.getItemCode())) //when only doc code is entered 
							items.add(iTempItem[i]);
						if(facDocCode.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc) ) //when only doc desc is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocDesc.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())) //when only tenure count is entered 
							items.add(iTempItem[i]);
						if(facDocDesc.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when only tenure Type is entered 
							items.add(iTempItem[i]);
						//----------------------------------
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")) //when doc code and desc is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when doc desc and tenure count is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase("")&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when tenure count and type is entered 
							items.add(iTempItem[i]);
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when code and tenure type is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when code and tenure count is entered 
							items.add(iTempItem[i]);
						if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc desc and tenure type is entered 
							items.add(iTempItem[i]);
						//----------------------------------
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when doc code,desc and tenure count is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc desc and tenure count,type is entered 
							items.add(iTempItem[i]);
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc code,desc and tenure type is entered 
							items.add(iTempItem[i]);
						if(!facDocTenureCount.equalsIgnoreCase(""))
						if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc code,tenure count,type is entered 
							items.add(iTempItem[i]);
						//-----------------------------------
						if(!facDocCode.equalsIgnoreCase("")&&!facDocDesc.equalsIgnoreCase("")&&!facDocTenureCount.equalsIgnoreCase("")&&!facDocTenureType.equalsIgnoreCase(""))
							if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType()))// all fields are entered
								items.add(iTempItem[i]);
						//-----------------------------------
						if(facDocCode.equalsIgnoreCase("")&&facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(""))
								items.add(iTempItem[i]);
						
						 facCategory = iTempItem[i].getFacilityCategory();
						 facType =  iTempItem[i].getFacilityType();
						 facSystem = iTempItem[i].getSystem();
						
								
					}
					                       }
				}
				resultMap.put("template", secTrxVal.getTemplate());
				if(!items.isEmpty()){
					ITemplateItem[] t=(ITemplateItem[]) items.toArray(new ITemplateItem[items.size()]);
					iTemp=secTrxVal.getTemplate();
					iTemp.setTemplateItemList(t);
					resultMap.put("template",iTemp);
				}
				if(go!=null){
				if(items.isEmpty()&&go.equalsIgnoreCase("y")){
					iTemp=secTrxVal.getTemplate();
					iTemp.setTemplateItemList(null);
					resultMap.put("template",iTemp);
				}
				}
			}
		}
	 		/*
	 		 * to get search result from newly added facility documents - get staging template from service scope
	 		 */
	 		ITemplateItem arr1[]=null;
	 		if(null!=iTemp){
	 			arr1=iTemp.getTemplateItemList();
	 		}
			if(null==iTemp||null==arr1){
		 			ITemplate iT=(ITemplate) map.get("template");
		 			if(null!=iT){
		 				if(null!=iT.getTemplateItemList()){
							ITemplateItem[] iTempItem=iT.getTemplateItemList();
							if(iTempItem.length!=0){
							for(int i=0;i<iTempItem.length;i++){
								IItem obItem=iTempItem[i].getItem();
								if(facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase(obItem.getItemCode())) //when only doc code is entered 
									items.add(iTempItem[i]);
								if(facDocCode.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc) ) //when only doc desc is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocDesc.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())) //when only tenure count is entered 
									items.add(iTempItem[i]);
								if(facDocDesc.equalsIgnoreCase("")&&facDocCode.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when only tenure Type is entered 
									items.add(iTempItem[i]);
								//----------------------------------
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase("")) //when doc code and desc is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when doc desc and tenure count is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase("")&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when tenure count and type is entered 
									items.add(iTempItem[i]);
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when code and tenure type is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when code and tenure count is entered 
									items.add(iTempItem[i]);
								if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc desc and tenure type is entered 
									items.add(iTempItem[i]);
								//----------------------------------
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase("")) //when doc code,desc and tenure count is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase("")&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc desc and tenure count,type is entered 
									items.add(iTempItem[i]);
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc code,desc and tenure type is entered 
									items.add(iTempItem[i]);
								if(!facDocTenureCount.equalsIgnoreCase(""))
								if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&facDocDesc.equalsIgnoreCase("")&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType())) //when doc code,tenure count,type is entered 
									items.add(iTempItem[i]);
								//-----------------------------------
								if(!facDocCode.equalsIgnoreCase("")&&!facDocDesc.equalsIgnoreCase("")&&!facDocTenureCount.equalsIgnoreCase("")&&!facDocTenureType.equalsIgnoreCase(""))
									if(facDocCode.equalsIgnoreCase(obItem.getItemCode())&&isSubString(obItem.getItemDesc(),facDocDesc)&&Integer.parseInt(facDocTenureCount)==(obItem.getTenureCount())&&facDocTenureType.equalsIgnoreCase(obItem.getTenureType()))// all fields are entered
										items.add(iTempItem[i]);
								//-----------------------------------
								if(facDocCode.equalsIgnoreCase("")&&facDocDesc.equalsIgnoreCase("")&&facDocTenureCount.equalsIgnoreCase("")&&facDocTenureType.equalsIgnoreCase(""))
										items.add(iTempItem[i]);
								
								facCategory = iTempItem[i].getFacilityCategory();
								 facType =  iTempItem[i].getFacilityType();
								 facSystem = iTempItem[i].getSystem();
							}
							                       }
						}
						resultMap.put("template", iT);
						if(!items.isEmpty()){
							ITemplateItem[] t=(ITemplateItem[]) items.toArray(new ITemplateItem[items.size()]);
							 iTemp=iT;
							iTemp.setTemplateItemList(t);
							resultMap.put("template",iTemp);
						}
						if(go!=null){
						if(items.isEmpty()&&go.equalsIgnoreCase("y")){
							 iTemp=iT;
							iTemp.setTemplateItemList(null);
							resultMap.put("template",iTemp);
						}
						}
		 			}
			}
			
			facCategory = getFacilityCategory(facCode);
			facType = getFacilityType(facCode);
			facSystem = getFacilitySystem(facCode);
			
			// --> end getting result from search criteria
			if(subType.equalsIgnoreCase(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)){
				IDynamicPropertySetup[] dynamicPropertySetupList = proxy.getDynamicPropertySetup(subType);
				ArrayList entryCodesArrayList = new ArrayList();
				ArrayList idList = new ArrayList();
				ArrayList categoryList = new ArrayList();
				for(int a1=0;a1<dynamicPropertySetupList.length;a1++){
					for(int a2=0;a2<dynamicPropertySetupList[a1].getEntryCodes().length;a2++){
						entryCodesArrayList.add(dynamicPropertySetupList[a1].getEntryCodes()[a2]);
						idList.add(String.valueOf(a1+1));
						if(a1==0){
							categoryList.add(ICMSConstant.CATEGORY_OF_PBR_PBT);
						}else{
							categoryList.add(ICMSConstant.CATEGORY_OF_GOODS_STATUS);
						}	
					}	
				}
				if(dynamicPropertySetupList.length!=0){
					resultMap.put("dynamicPropertySetupList", dynamicPropertySetupList);
					resultMap.put("entryCodesArrayList", entryCodesArrayList);
					resultMap.put("idList", idList);
					resultMap.put("categoryList", categoryList);
				
				}
			}
			resultMap.put("tenureTypeList", getTenureType());
			resultMap.put("secTrxVal", secTrxVal);
			resultMap.put("facilityCategoryList",getFacilityCategoryList() );
			resultMap.put("facilityTypeList",getFacilityTypeList() );
			resultMap.put("systemList",getSystemList() );
			resultMap.put("facCategory",facCategory );
			resultMap.put("facType",facType );
			resultMap.put("facSystem",facSystem );
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	/*
	 * getSubString - used for document description field to get substring
	 */
	public static boolean isSubString(String fullString,String subString){
		 try{
		 fullString=fullString.toLowerCase();
		 subString=subString.toLowerCase();
		 int start = fullString.indexOf(subString);
		 String mySubstring = fullString.substring(start, subString.length() + start);
		 }catch(Exception e){
		  return false;
		 }
		 if(subString.equals(""))
			 return false;
		 return true;
		 }
	/*
     * getTenureType - get dropdown for tenure type field from common code.
     */
	private List getTenureType() {
		List lbValList = new ArrayList();
		HashMap tenureTypeMap;
		 ArrayList tenureTypeLabel = new ArrayList();

			ArrayList tenureTypeValue = new ArrayList();

			tenureTypeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.TIME_FREQ);
			tenureTypeValue.addAll(tenureTypeMap.keySet());
			tenureTypeLabel.addAll(tenureTypeMap.values());
		try {
		
			for (int i = 0; i < tenureTypeLabel.size(); i++) {
				String id = tenureTypeLabel.get(i).toString();
				String val = tenureTypeValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id,val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getFacilityCategoryList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_CATEGORY);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id, val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getFacilityTypeList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_TYPE);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id, val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getSystemList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.SYSTEM);
			facilityCategoryValue.addAll(facilityCategoryMap.keySet());
			facilityCategoryLabel.addAll(facilityCategoryMap.values());
		try {
		
			for (int i = 0; i < facilityCategoryLabel.size(); i++) {
				String id = facilityCategoryLabel.get(i).toString();
				String val = facilityCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(id, val);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	
private String getFacilityCategory(String facCode) {
		String data="";
		String sql="";
		 sql="SELECT ENTRY_NAME " + 
		 		"FROM COMMON_CODE_CATEGORY_ENTRY " + 
		 		"WHERE ENTRY_CODE = " + 
		 		"  (SELECT NEW_FACILITY_CATEGORY " + 
		 		"  FROM CMS_FACILITY_NEW_MASTER " + 
		 		"  WHERE NEW_FACILITY_CODE = '"+facCode+"' " + 
		 		"  )";
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					data=rs.getString("ENTRY_NAME");
					}
				}
			rs.close();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return data;
	}

private String getFacilityType(String facCode) {
	String data="";
	String sql="";
	 sql="SELECT ENTRY_NAME " + 
	 		"FROM COMMON_CODE_CATEGORY_ENTRY " + 
	 		"WHERE ENTRY_CODE = " + 
	 		"  (SELECT NEW_FACILITY_TYPE " + 
	 		"  FROM CMS_FACILITY_NEW_MASTER " + 
	 		"  WHERE NEW_FACILITY_CODE = '"+facCode+"' " + 
	 		"  )";
	DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				data=rs.getString("ENTRY_NAME");
				}
			}
		rs.close();
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{ 
		try {
			dbUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return data;
}

private String getFacilitySystem(String facCode) {
	String data="";
	String sql="";
	 sql="SELECT ENTRY_NAME " + 
	 		"FROM COMMON_CODE_CATEGORY_ENTRY " + 
	 		"WHERE ENTRY_CODE = " + 
	 		"  (SELECT NEW_FACILITY_SYSTEM " + 
	 		"  FROM CMS_FACILITY_NEW_MASTER " + 
	 		"  WHERE NEW_FACILITY_CODE = '"+facCode+"' " + 
	 		"  )";
	DBUtil dbUtil=null;
	try {
		 dbUtil=new DBUtil();
		dbUtil.setSQL(sql);
		ResultSet rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				data=rs.getString("ENTRY_NAME");
				}
			}
		rs.close();
	} catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{ 
		try {
			dbUtil.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return data;
}
	
}
