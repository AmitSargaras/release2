/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.collateralNewMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue;
import com.integrosys.cms.app.collateralNewMaster.trx.OBCollateralNewMasterTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 *$Author: Abhijit R $
 *Command for checker to read System bank Trx value
 */
public class CheckerReadCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadCollateralNewMasterCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				
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
				{ "collateralNewMasterObj", "com.integrosys.cms.app.collateralNewMaster.bus.OBCollateralNewMaster", FORM_SCOPE },
				{"ICollateralNewMasterTrxValue", "com.integrosys.cms.app.collateralNewMaster.trx.ICollateralNewMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			ICollateralNewMaster collateralNewMaster;
			ICollateralNewMasterTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			// function to get CollateralNewMaster Trx value
			trxValue = (OBCollateralNewMasterTrxValue) getCollateralNewMasterProxy().getCollateralNewMasterByTrxID(branchCode);
			
			collateralNewMaster = (OBCollateralNewMaster) trxValue.getStagingCollateralNewMaster();
			
			resultMap.put("ICollateralNewMasterTrxValue", trxValue);
			resultMap.put("collateralNewMasterObj", collateralNewMaster);
			resultMap.put("event", event);
			resultMap.put("timeFreqList",getTimeFrequencyList() );
			resultMap.put("colCategoryList",getColCategoryList() );
		} catch (CollateralNewMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

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
