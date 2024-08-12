package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RefreshRbiIndustryCodeCommand extends AbstractCommand {

	private IRbiCategoryProxyManager rbiCategoryProxy;

	
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	public void setRbiCategoryProxy(
			IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "industryName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "rbiIndustryCodeList", "java.util.List", REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			String event = (String) map.get("event");
			if (event.equals("refresh_rbi_industry_code")) {
				String indName = (String) map.get("industryName");
				resultMap.put("rbiIndustryCodeList",getRbiIndustryCodeList(indName));
			}
			resultMap.put("event", event);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private List getRbiIndustryCodeList(String indName) {
		List lbValList = new ArrayList();
		List rbiIndustryCodeList =new ArrayList();
		 HashMap rBICateCodeHashMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.HDFC_RBI_CODE);
		OBRbiCategory oBRbiCategory = new OBRbiCategory();
		try {
			List idList = (List) getRbiCategoryProxy().getRbiIndCodeByNameList(indName);
			oBRbiCategory = (OBRbiCategory)idList.get(0);
			Set rbiC = oBRbiCategory.getStageIndustryNameSet();
			Iterator itSet = rbiC.iterator();
			OBIndustryCodeCategory oBIndustryCodeCategory = new OBIndustryCodeCategory();
			int count = 0;
			while( itSet.hasNext() ){
				oBIndustryCodeCategory = (OBIndustryCodeCategory)itSet.next();
				
				//Uma :Rbi Category Master issue- Null Pointer Exception
				if(null != rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId())){
				String id = oBIndustryCodeCategory.getRbiCodeCategoryId();
				String value =oBIndustryCodeCategory.getRbiCodeCategoryId()+"-"+rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId()).toString();
				LabelValueBean lvBean = new LabelValueBean(value, id);
				lbValList.add(lvBean);
				}
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);		
	}
}
