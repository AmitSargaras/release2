/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.systemBank;

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
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;
import com.integrosys.cms.app.systemBank.trx.OBSystemBankTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 *$Author: Abhijit R $
 *Command for checker to read System bank Trx value
 */
public class CheckerReadSystemBankCmd extends AbstractCommand implements ICommonEventConstant {
	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}
	
	private ISystemBankProxyManager systemBankProxy;

	public ISystemBankProxyManager getSystemBankProxy() {
		return systemBankProxy;
	}

	public void setSystemBankProxy(ISystemBankProxyManager systemBankProxy) {
		this.systemBankProxy = systemBankProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadSystemBankCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
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
				{ "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", FORM_SCOPE },
				{"ISystemBankTrxValue", "com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				 { "TrxId", "java.lang.String", SERVICE_SCOPE },
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
			ISystemBank systemBank;
			ISystemBankTrxValue trxValue=null;
			String bankCode=(String)map.get("TrxId");
		    String event = (String) map.get("event");
			// function to get system bank Trx value
			trxValue = (OBSystemBankTrxValue) getSystemBankProxy().getSystemBankByTrxID(bankCode);
			//   systemBank = (OBSystemBank) trxValue.getSystemBank();
			// function to get stging value of system bank trx value
			systemBank = (OBSystemBank) trxValue.getStagingSystemBank();
			resultMap.put("ISystemBankTrxValue", trxValue);
			resultMap.put("systemBankObj", systemBank);
			resultMap.put("TrxId", bankCode);
			resultMap.put("event", event);
			if(systemBank.getState()!=null){
				long stateId = systemBank.getState().getIdState();
				 resultMap.put("cityList", getCityList(stateId));
			}else{
				 resultMap.put("cityList", getCityList());
			}
			if(systemBank.getRegion()!=null){
				long regionId= systemBank.getRegion().getIdRegion();
				resultMap.put("stateList", getStateList(regionId));
			}else{
				 resultMap.put("stateList", getStateList());
			}

			if(systemBank.getCityTown()!=null){
				long cityId= systemBank.getCityTown().getIdCity();
				
			}
			if(systemBank.getCountry()!=null){
				long countryId = systemBank.getCountry().getIdCountry();
				  resultMap.put("regionList", getRegionList(countryId));
			}else{
				long initcountryId=0;
				 resultMap.put("regionList", getRegionList(initcountryId));
			}
			
			
			long initcountryId=0;
			
			resultMap.put("countryList", getCountryList(initcountryId));
		} catch (SystemBankException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCountryList(countryId);
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry) idList.get(i);
				if (country.getStatus().equals("ACTIVE")) {
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

	private List getRegionList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getRegionList(countryId);				
		
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

	private List getStateList(long regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getStateList(regionId);				
		
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
	
	private List getCityList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCityList(stateId);				
		
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
	
	private List getStateList() {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				String id = Long.toString(state.getIdState());
				String val = state.getStateName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private List getCityList() {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				String id = Long.toString(state.getIdState());
				String val = state.getStateName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}


}
