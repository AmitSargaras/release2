/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
*@author $Author: Abhijit R$
*Command to read CollateralNewMaster
 */
public class MakerPrepareCreateCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	
	
	
	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(
			ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateCollateralNewMasterCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				  
					 
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
				{"ICollateralNewMasterTrxValue", "com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue", SERVICE_SCOPE},
				{ "timeFreqList", "java.util.List", SERVICE_SCOPE },
				{ "colCategoryList", "java.util.List", SERVICE_SCOPE },
				 });
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
		
		  OBCollateralNewMasterTrxValue collateralNewMasterTrxValue = new OBCollateralNewMasterTrxValue();
		  resultMap.put("timeFreqList",getTimeFrequencyList() );
		  resultMap.put("colCategoryList",getColCategoryList() );

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private List getTimeFrequencyList() {
		List lbValList = new ArrayList();
		HashMap timeFreqMap;
		 ArrayList timeFreqLabel = new ArrayList();

			ArrayList timeFreqValue = new ArrayList();

			timeFreqMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.TIME_FREQ);
			timeFreqValue.addAll(timeFreqMap.keySet());
			timeFreqLabel.addAll(timeFreqMap.values());
		try {
		
			for (int i = 0; i < timeFreqLabel.size(); i++) {
				String id = timeFreqLabel.get(i).toString();
				String val = timeFreqValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getColCategoryList() {
		List lbValList = new ArrayList();
		HashMap colCategoryMap;
		 ArrayList colCategoryLabel = new ArrayList();

			ArrayList colCategoryValue = new ArrayList();

			colCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.COMMON_CODE_COLLATERAL_CATEGORY);
			colCategoryValue.addAll(colCategoryMap.keySet());
			colCategoryLabel.addAll(colCategoryMap.values());
		try {
		
			for (int i = 0; i < colCategoryLabel.size(); i++) {
				String id = colCategoryLabel.get(i).toString();
				String val = colCategoryValue.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	

}
