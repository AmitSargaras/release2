package com.integrosys.cms.ui.excludedfacility;

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
import com.integrosys.cms.app.excludedfacility.bus.ExcludedFacilityException;
import com.integrosys.cms.app.excludedfacility.bus.IExcludedFacility;
import com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class MakerReadExcludedFacilityCmd extends AbstractCommand implements ICommonEventConstant {

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
	public MakerReadExcludedFacilityCmd() {
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
			 	{"facilityCode", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "excludedFacilityObj", "com.integrosys.cms.app.excludedfacility.bus.OBExcludedFacility", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
				{"IExcludedFacilityTrxValue", "com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue", SERVICE_SCOPE}
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
		try {
			IExcludedFacility excludedFacility;
			IExcludedFacilityTrxValue trxValue=null;
			String facilityCode=(String) (map.get("facilityCode"));
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			
			trxValue = (OBExcludedFacilityTrxValue) getExcludedFacilityProxy().getExcludedFacilityTrxValue(Long.parseLong(facilityCode));
			excludedFacility = (OBExcludedFacility) trxValue.getExcludedFacility();

			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||(trxValue.getStatus().equals("REJECTED"))||(trxValue.getStatus().equals("DRAFT")))
			{
				resultMap.put("wip", "wip");
			}
			/*LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_EXCLUDED_FACILITY",excludedFacility.getId(),"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			resultMap.put("event", event);	
			resultMap.put("startIndex",startIdx);	
			resultMap.put("IExcludedFacilityTrxValue", trxValue);
			resultMap.put("excludedFacilityObj", excludedFacility);
			 resultMap.put("facilityCategoryList",getFacilityCategoryList() );
			 
		}catch (ExcludedFacilityException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
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
