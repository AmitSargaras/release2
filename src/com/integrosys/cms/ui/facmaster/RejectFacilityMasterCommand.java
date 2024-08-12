/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.facmaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.CollateralSubTypeSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/02 04:26:53 $ Tag: $Name: $
 */
public class RejectFacilityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RejectFacilityMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },
				{ "facCategory", "java.lang.String", SERVICE_SCOPE },
				{ "facType", "java.lang.String", SERVICE_SCOPE },
				{ "facSystem", "java.lang.String", SERVICE_SCOPE },});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE },
				{ "facilityMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },
				{ "facCategory", "java.lang.String", SERVICE_SCOPE },
				{ "facType", "java.lang.String", SERVICE_SCOPE },
				{ "facSystem", "java.lang.String", SERVICE_SCOPE },
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
		HashMap exceptionMap = new HashMap();
		HashMap facilityMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		ITemplateTrxValue secTrxVal = (ITemplateTrxValue) map.get("secTrxVal");
		DefaultLogger.debug(this, "itemTrxVal before approve " + secTrxVal);
		String facCategory =(String) map.get("facCategory");
		String facType =(String) map.get("facType");
		String facSystem =(String) map.get("facSystem");
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		TemplateSearchCriteria aCriteria = new TemplateSearchCriteria();
		aCriteria.setCountry("IN");
	CollateralSubTypeSearchResultItem sr[];
	try {
		sr = proxy.getFacilitySubType(aCriteria);
	
	List facSubTypeList = Arrays.asList(sr);
		for(int k=0;k<facSubTypeList.size();k++){
			CollateralSubTypeSearchResultItem item=(CollateralSubTypeSearchResultItem)facSubTypeList.get(k);
			facilityMap.put(item.getCollateralSubTypeCode(), item.getCollateralSubTypeDesc());
		}
		resultMap.put("facilityMap", facilityMap);
		
	} catch (SearchDAOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (CheckListTemplateException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		if(ctx.getRemarks()==null || ctx.getRemarks().trim().equals("")){
			exceptionMap.put("templateRemarksError", new ActionMessage("error.string.mandatory"));
			resultMap.put("custTypeTrxID", secTrxVal.getTransactionID());
			resultMap.put("request.ITrxValue", secTrxVal);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			resultMap.put("facilityCategoryList",getFacilityCategoryList() );
			resultMap.put("facilityTypeList",getFacilityTypeList() );
			resultMap.put("systemList",getSystemList() );
			resultMap.put("facCategory",facCategory );
			resultMap.put("facType",facType );
			resultMap.put("facSystem",facSystem );
			return returnMap;
		}else{
		try {
			
			secTrxVal = proxy.checkerRejectTemplate(ctx, secTrxVal);
			DefaultLogger.debug(this, "docTrxObj after approve " + secTrxVal);
			resultMap.put("request.ITrxValue", secTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		resultMap.put("facilityCategoryList",getFacilityCategoryList() );
		resultMap.put("facilityTypeList",getFacilityTypeList() );
		resultMap.put("systemList",getSystemList() );
		resultMap.put("facCategory",facCategory );
		resultMap.put("facType",facType );
		resultMap.put("facSystem",facSystem );
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
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
