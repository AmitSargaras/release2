package com.integrosys.cms.ui.excludedfacility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class MakerPrepareCreateExcludedFacilityCmd extends AbstractCommand implements
ICommonEventConstant {

private IExcludedFacilityProxyManager excludedFacilityProxy;
	
	public IExcludedFacilityProxyManager getExcludedFacilityProxy() {
		return excludedFacilityProxy;
	}

	public void setExcludedFacilityProxy(IExcludedFacilityProxyManager excludedFacilityProxy) {
		this.excludedFacilityProxy = excludedFacilityProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateExcludedFacilityCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
		return (new String[][] {/* { "IPartyGroupTrxValue",
				"com.integrosys.cms.app.partyGroup.trx.OBPartyGroupTrxValue",
				SERVICE_SCOPE },*/
			{"IExcludedFacilityTrxValue", "com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue", SERVICE_SCOPE},
			{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE }

		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		//OBPartyGroupTrxValue partyGroupTrxValue = new OBPartyGroupTrxValue();

		// resultMap.put("IPartyGroupTrxValue", partyGroupTrxValue);
		// resultMap.put("hubValueList",getHUBList() );
		resultMap.put("facilityCategoryList",getFacilityCategoryList() );
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}
	private List getFacilityCategoryList() {
		List lbValList = new ArrayList();
		HashMap facilityCategoryMap;
		 ArrayList facilityCategoryLabel = new ArrayList();

			ArrayList facilityCategoryValue = new ArrayList();

			facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_CATEGORY);
			facilityCategoryLabel.addAll(facilityCategoryMap.keySet());
			facilityCategoryValue.addAll(facilityCategoryMap.values());
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
