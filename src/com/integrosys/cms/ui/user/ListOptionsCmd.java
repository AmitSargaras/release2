package com.integrosys.cms.ui.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.proxy.SystemBankBranchProxyManagerImpl;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 $Author: Abhijit R $
 
 */
public class ListOptionsCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}
	public ListOptionsCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				
				
			};
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "userRegionList", "java.util.List", SERVICE_SCOPE },
				{ "departmentList", "java.util.List", SERVICE_SCOPE },
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
		long userCountryId = 0;
		
		List list= (List) getCityProxy().getCountryList(countryId);
		for (int i = 0; i < list.size(); i++) {
			ICountry country = (ICountry) list.get(i);
			if (country.getCountryCode().trim().equalsIgnoreCase("IN")) {
				 userCountryId = country.getIdCountry();
			}
		}
		 
		//======================
		//CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		
		//======================
		
		
		
		
		  //resultMap.put("ISystemBankBranchTrxValue", systemBankBranchTrxValue);
		resultMap.put("departmentList", getDepartmentList());
		resultMap.put("stateList", getStateList());
		  resultMap.put("countryList", getCountryList(countryId));
		  resultMap.put("regionList", getRegionList(stateId));
		  resultMap.put("cityList", getCityList());
		  resultMap.put("branchList", getBranchList());
		  resultMap.put("userRegionList", getUserRegionList());

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
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getRegionList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = "";
				String val = "";
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
			ex.printStackTrace();
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
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = bankBranch.getSystemBankBranchCode();
				String val = bankBranch.getSystemBankBranchName();
				String value=id+" ("+val + ")";
				LabelValueBean lvBean = new LabelValueBean(value, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	
	 private List getUserRegionList() {
			List lbValList = new ArrayList();
			try {
				IRelationshipMgrProxyManager proxyManager =(IRelationshipMgrProxyManager) BeanHouse.get("relationshipMgrProxyManager");
				List idList = (List) proxyManager.getRegionList(PropertyManager.getValue("clims.application.country"));				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return CommonUtil.sortDropdown(lbValList);
		}

	 private List getDepartmentList() {
		List departmentList= new ArrayList();
		CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();			
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)iterator.next();
	        value=pairs.getKey().toString();
	        label=pairs.getKey()+" ("+pairs.getValue()+")";
			LabelValueBean lvBean = new LabelValueBean(label,value);
			departmentList.add(lvBean);
	    }
		return CommonUtil.sortDropdown(departmentList);
	}	 
}