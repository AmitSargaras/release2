/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.systemBankBranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
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
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
*@author $Author: Abhijit R$
*Command to read System Bank Branch
 */
public class MakerPrepareCreateSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	
	IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager) BeanHouse.get("otherBankProxyManager");
	
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
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	
	
	
	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}
	
	private IPincodeMappingBusManager pincodeMappingBusManager;
	
	public IPincodeMappingBusManager getPincodeMappingBusManager() {
		return pincodeMappingBusManager;
	}

	public void setPincodeMappingBusManager(IPincodeMappingBusManager pincodeMappingBusManager) {
		this.pincodeMappingBusManager = pincodeMappingBusManager;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareCreateSystemBankBranchCmd() {
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
				{"country","java.lang.String",REQUEST_SCOPE},
				{"region","java.lang.String",REQUEST_SCOPE},
				{"state","java.lang.String",REQUEST_SCOPE},
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
				{"ISystemBankBranchTrxValue", "com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue", SERVICE_SCOPE},
				{ "hubValueList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "systemBankId", "java.lang.String", SERVICE_SCOPE },
				{ "systemBankCode", "java.lang.String", SERVICE_SCOPE },
				{ "systemBankName", "java.lang.String", SERVICE_SCOPE },
				{ "pincodesString", "java.util.String", SERVICE_SCOPE },
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
		List systemBankList= null;
		ISystemBank systemBank= null;
		long stateId = 0, countryId = 0;
		  OBSystemBankBranchTrxValue systemBankBranchTrxValue = new OBSystemBankBranchTrxValue();
		  try {
			systemBank=getSystemBankProxy().getSystemBankById(1);
			
				String systemBankCode=systemBank.getSystemBankCode();
				String systemBankId=String.valueOf(systemBank.getId());
				String systemBankName=systemBank.getSystemBankName();
				
				resultMap.put("systemBankCode",systemBankCode );
				resultMap.put("systemBankId",systemBankId );
				resultMap.put("systemBankName",systemBankName );
				
				String country = (String) map.get("country");
	        	String region = (String) map.get("region");
	        	String state = (String) map.get("state");
	        	
	        	List regionList = new ArrayList();
	        	List stateList = new ArrayList();
	        	List cityList = new ArrayList();
	        	
	        	if(country!=null && !country.equals(null))
	        		regionList = getRegionList(Long.parseLong(country));
	        	
	        	if(region!=null && !region.equals(null))
	        		stateList = getStateList(region);
	        	
	        	if(state!=null && !state.equals(null))
	        		cityList = getCityList(state);
	        	
	        	resultMap.put("hubValueList",getBranchList() );
	  		    resultMap.put("countryList",getCountryList(countryId));
	        	resultMap.put("regionList",regionList);
	        	resultMap.put("stateList",stateList);
	        	resultMap.put("cityList",cityList);
	        	resultMap.put("pincodesString",getStatePincodeString());
			
		} catch (SystemBankException e) {
			e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		  //resultMap.put("ISystemBankBranchTrxValue", systemBankBranchTrxValue);
		  

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranchForHUB();
			 List idList = (List) searchResult.getResultList();
			 DefaultLogger.debug(this,"branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = bankBranch.getSystemBankBranchCode();
				String val = bankBranch.getSystemBankBranchName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	private List getHUBList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getSystemBankBranchProxy().getAllHUBBranchValue();
			List valList = (List)getSystemBankBranchProxy().getAllHUBBranchId(); 
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
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
			List idList = (List) cityProxy.getRegionList(countryId);				
		
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
			List idList = (List) otherBankProxyManager.getStateList(regionId);				
		
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
			List idList = (List) otherBankProxyManager.getCityList(stateId);				
		
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
	
	private String getStatePincodeString() {
		HashMap<String, String> pincodeMap = null;
		
		if(null != getPincodeMappingBusManager()) {
			pincodeMap = (HashMap<String, String>) getPincodeMappingBusManager().getActiveStatePinCodeMap();
		}
		
		String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
		
		return pincodesStr;
	}

}
