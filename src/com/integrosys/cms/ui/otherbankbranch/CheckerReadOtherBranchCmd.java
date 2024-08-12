package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 *$Author: Dattatray Thorat $
 *Command for checker to read Other Bank Branch Trx value
 */
public class CheckerReadOtherBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IOtherBranchProxyManager otherBranchProxyManager;
	
	private IOtherBankProxyManager otherBankProxyManager;

	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	/**
	 * @return the otherBranchProxyManager
	 */
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	/**
	 * @param otherBranchProxyManager the otherBranchProxyManager to set
	 */
	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerReadOtherBranchCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { {"countryId","java.lang.String",REQUEST_SCOPE},
				{"regionId","java.lang.String",REQUEST_SCOPE},
				{"stateId","java.lang.String",REQUEST_SCOPE},
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
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
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
				{ "OtherBranchObj", "com.integrosys.cms.app.otherbranch.bus.OBOtherBranch", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"countryList","java.util.List",SERVICE_SCOPE},
	            {"regionList","java.util.List",SERVICE_SCOPE},
	            {"stateList","java.util.List",SERVICE_SCOPE},
	            {"cityList","java.util.List",SERVICE_SCOPE}
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
			IOtherBranch otherBranch;
			IOtherBankBranchTrxValue trxValue=null;
			String branchCode=(String) (map.get("TrxId"));
			String event=(String) (map.get("event"));
			String countryId = (String) map.get("countryId");
        	String regionId = (String) map.get("regionId");
        	String stateId = (String) map.get("stateId");
        	List regionList = new ArrayList();
        	List stateList = new ArrayList();
        	List cityList = new ArrayList();
			// function to get other bank branch Trx value
			trxValue = (OBOtherBankBranchTrxValue) getOtherBranchProxyManager().getOtherBranchByTrxID(branchCode);
			// function to get stging value of other bank branch trx value
			otherBranch = (OBOtherBranch) trxValue.getStagingOtherBranch();
			
			if(otherBranch.getCountry()!=null)
        		regionList = getRegionList(Long.toString(otherBranch.getCountry().getIdCountry()));
        		/*stateList = getStateList(Long.toString(otherBranch.getRegion().getIdRegion()));
        		cityList = getCityList(Long.toString(otherBranch.getState().getIdState()));*/
        	
    		if(otherBranch.getRegion()!=null)
				stateList = getStateList(Long.toString(otherBranch.getRegion().getIdRegion()));
    		
			if(otherBranch.getState()!=null)
				cityList = getCityList(Long.toString(otherBranch.getState().getIdState()));
        	
        	resultMap.put("countryList",getCountryList());
        	resultMap.put("regionList",regionList);
        	resultMap.put("stateList",stateList);
        	resultMap.put("cityList",cityList);
			resultMap.put("TrxId", branchCode);
			resultMap.put("event", event);
			resultMap.put("IOtherBankBranchTrxValue", trxValue);
			resultMap.put("OtherBranchObj", otherBranch);
		} catch (OtherBranchException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private List getCountryList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCountryList();				
		
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry)idList.get(i);
				if( country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
    
    private List getRegionList(String countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getRegionList(countryId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				if( region.getStatus().equals("ACTIVE")) {
					String id = Long.toString(region.getIdRegion());
					String val = region.getRegionName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getStateList(String regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getStateList(regionId);				
		
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState)idList.get(i);
				if( state.getStatus().equals("ACTIVE")) {
					String id = Long.toString(state.getIdState());
					String val = state.getStateName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCityList(String stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCityList(stateId);				
		
			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity)idList.get(i);
				if( city.getStatus().equals("ACTIVE")) {
					String id = Long.toString(city.getIdCity());
					String val = city.getCityName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
