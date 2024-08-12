/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.OBTemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
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
public class CreateFacilityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CreateFacilityMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "secType", "java.lang.String", REQUEST_SCOPE },
				{ "subType", "java.lang.String", REQUEST_SCOPE }, 
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "facDocCode", "java.lang.String", SERVICE_SCOPE },
				{ "facDocDesc", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureCount", "java.lang.String", SERVICE_SCOPE },
				{ "facDocTenureType", "java.lang.String", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE },
				{ "dynamicPropertySetupList", "com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup[]", SERVICE_SCOPE },
				{ "entryCodesArrayList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "idList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "categoryList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "tenureTypeList", "java.util.List", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
				{ "facilityTypeList", "java.util.List", SERVICE_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },});
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
		String secType = (String) map.get("secType");
		String subType = (String) map.get("subType");
		//String secTrxID = (String) map.get("custTypeTrxID");
		try {
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			boolean wip = proxy.pendingCreateCollateralTemplateAlreadyExist(secType, subType);
			//ITemplateTrxValue secTrxVal = proxy.getTemplateByTrxID(secTrxID);
			resultMap.put("secTrxVal", null);
			resultMap.put("isEdit","false");
			ITemplate template = new OBTemplate();
			template.setTemplateType(ICMSConstant.DOC_TYPE_FACILITY);
			template.setCollateralType(secType);
			template.setCollateralSubType(subType);
			resultMap.put("template", template);
			if (wip) {
				resultMap.put("wip", "wip");
			}
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
					resultMap.put("dynamicPropertySetupList", dynamicPropertySetupList);
					resultMap.put("entryCodesArrayList", entryCodesArrayList);
					resultMap.put("idList", idList);
					resultMap.put("categoryList", categoryList);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		resultMap.put("facDocCode", "");
		resultMap.put("facDocDesc","");
		resultMap.put("facDocTenureCount","");
		resultMap.put("facDocTenureType","");
		resultMap.put("tenureTypeList", getTenureType());
		resultMap.put("facilityCategoryList",getFacilityCategoryList() );
		resultMap.put("facilityTypeList",getFacilityTypeList() );
		resultMap.put("systemList",getSystemList() );
		DefaultLogger.debug(this, "Going out of doExecute()");
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
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
}
