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
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
/**
*@author $Author: Abhijit R$
*Command to read System Bank Branch
 */
public class MakerReadSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {
	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
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
	public MakerReadSystemBankBranchCmd() {
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
				 {"branchCode", "java.lang.String", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 {"event", "java.lang.String", REQUEST_SCOPE}		 
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
				{ "systemBankBranchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch", SERVICE_SCOPE },
				{ "systemBankBranchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch", FORM_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"ISystemBankBranchTrxValue", "com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue", SERVICE_SCOPE},
				{ "hubValueList", "java.util.List", SERVICE_SCOPE },
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
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
		try {
			ISystemBankBranch systemBankBranch;
			ISystemBankBranchTrxValue trxValue=null;
			String branchCode=(String) (map.get("branchCode"));
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			
			DefaultLogger.debug(this, "startIdx: " + startIdx);
		
			trxValue = (OBSystemBankBranchTrxValue) getSystemBankBranchProxy().getSystemBankBranchTrxValue(Long.parseLong(branchCode));
			systemBankBranch = (OBSystemBankBranch) trxValue.getSystemBankBranch();
			long stateId = systemBankBranch.getState().getIdState();
			long regionId= systemBankBranch.getRegion().getIdRegion();
			long cityId= systemBankBranch.getCityTown().getIdCity();
			long countryId = systemBankBranch.getCountry().getIdCountry();
			long initcountryId=0;
			if((trxValue.getStatus().equals("PENDING_CREATE"))
					||(trxValue.getStatus().equals("PENDING_UPDATE"))
					||(trxValue.getStatus().equals("PENDING_DELETE"))
					||(trxValue.getStatus().equals("REJECTED"))||(trxValue.getStatus().equals("DRAFT")))
			{
				resultMap.put("wip", "wip");
			}
			if(event.equals("maker_edit_systemBankBranch_read")
					||event.equals("maker_save_process")
					||event.equals("maker_view_systemBankBranch")
					||event.equals("checker_view_systemBankBranch")
					||event.equals("maker_delete_systemBankBranch_read")){
				 resultMap.put("hubValueList",getBranchList(systemBankBranch.getSystemBankBranchCode()) );
			}
			

			LimitDAO limitDao = new LimitDAO();
					try {
					String migratedFlag = "N";	
					boolean status = false;	
					 status = limitDao.getCAMMigreted("CMS_SYSTEM_BANK_BRANCH",systemBankBranch.getId(),"ID");
					
					if(status)
					{
						migratedFlag= "Y";
					}
					resultMap.put("migratedFlag", migratedFlag);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			resultMap.put("event", event);
			 resultMap.put("startIndex",startIdx);	
			resultMap.put("ISystemBankBranchTrxValue", trxValue);
			resultMap.put("systemBankBranchObj", systemBankBranch);
			resultMap.put("stateList", getStateList(regionId));
			  resultMap.put("countryList", getCountryList(initcountryId));
			  resultMap.put("regionList", getRegionList(countryId));
			  resultMap.put("cityList", getCityList(stateId));
			  resultMap.put("pincodesString",getStatePincodeString());
		}catch (SystemBankBranchException ex) {
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
	
	private List getBranchList(String branchCode) {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranchForHUB();
			 List idList = (List) searchResult.getResultList();
//			 System.out.println("branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				if(!bankBranch.getSystemBankBranchCode().equalsIgnoreCase(branchCode)){
				String id =bankBranch.getSystemBankBranchCode();
				String val = bankBranch.getSystemBankBranchName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
				}
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
	
	private String getStatePincodeString() {
		HashMap<String, String> pincodeMap = null;
		
		if(null != getPincodeMappingBusManager()) {
			pincodeMap = (HashMap<String, String>) getPincodeMappingBusManager().getActiveStatePinCodeMap();
		}
		
		String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
		
		return pincodesStr;
	}

}
