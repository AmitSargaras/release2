package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
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
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.geography.city.bus.ICity;
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
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.component.user.app.trx.ICommonUserTrxValue;

/**
 $Author: Abhijit R $
 Command for list System Bank Branch
 */
public class UpdateListOptionsCmd extends AbstractCommand implements ICommonEventConstant {

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
	public UpdateListOptionsCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "UserId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "isUnlock", "java.lang.String", REQUEST_SCOPE },
				
				
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
				{"TeamTypeMembershipLst","java.util.List",SERVICE_SCOPE},
				{ "isUnlock", "java.lang.String", REQUEST_SCOPE },
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	@SuppressWarnings("unchecked")
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this,"::::::::::::::::::::In Do execute of UpdateListOptions Cmd::::::::::::::::::");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String userId = (String) map.get("UserId");
		String trxId = (String) map.get("TrxId");
		String event = (String) map.get("event");
//		System.out.println("Event is ::::::::::::::::::"+event);
		ArrayList teamTypeMembershipLst = new ArrayList();
		CMSUserProxy proxy = new CMSUserProxy();
		ICommonUserTrxValue userTrxVal;
		ICommonUser user;
		long initcountryId=0;
		try {
			
			if(event.equalsIgnoreCase("checker_add_read")||event.equalsIgnoreCase("checker_edit_read")||event.equalsIgnoreCase("checker_delete_read")||event.equalsIgnoreCase("rejected_read")||event.equalsIgnoreCase("rejected_delete_read")){
			 try {
				userTrxVal = proxy.getUser(Long.parseLong(trxId.trim()));
				user= userTrxVal.getStagingUser();
				if(user.getEjbStateId()!=null){
				long stateId = Long.parseLong(user.getEjbStateId().toString());
				 resultMap.put("cityList", getCityList(stateId));
				}else{
					 resultMap.put("cityList", new ArrayList());
				}
				if(user.getEjbRegion()!=null){
				long regionId= Long.parseLong(user.getEjbRegion().toString());
				resultMap.put("stateList", getStateList(regionId));
				}else{
					resultMap.put("stateList", new ArrayList());
				}
				
//				long cityId= Long.parseLong(user.getEjbCityId().toString());
				
				if(user.getEjbCountryId()!=null){
				long countryId = Long.parseLong(user.getEjbCountryId().toString());
				resultMap.put("regionList", getRegionList(countryId));
				}else{
					resultMap.put("regionList", new ArrayList());
				}
				long userCountryId = 0;
				
				List list= (List) getCityProxy().getCountryList(userCountryId);
				for (int i = 0; i < list.size(); i++) {
					ICountry country = (ICountry) list.get(i);
					if (country.getCountryCode().trim().equalsIgnoreCase("IN")) {
						 userCountryId = country.getIdCountry();
					}
				}
				 resultMap.put("userRegionList", getUserRegionList());
				
				  resultMap.put("countryList", getCountryList(initcountryId));
				  
				 
				  resultMap.put("branchList", getBranchList());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			}else{
			userTrxVal = proxy.getUserByPK(userId.trim());
			user=userTrxVal.getUser();
			if(user.getEjbStateId()!=null){
				long stateId = Long.parseLong(user.getEjbStateId().toString());
				 resultMap.put("cityList", getCityList(stateId));
				}else{
					 resultMap.put("cityList", new ArrayList());
				}
				if(user.getEjbRegion()!=null){
				long regionId= Long.parseLong(user.getEjbRegion().toString());
				resultMap.put("stateList", getStateList(regionId));
				}else{
					resultMap.put("stateList", new ArrayList());
				}
				
//				long cityId= Long.parseLong(user.getEjbCityId().toString());
				
				if(user.getEjbCountryId()!=null){
				long countryId = Long.parseLong(user.getEjbCountryId().toString());
				resultMap.put("regionList", getRegionList(countryId));
				}else{
					resultMap.put("regionList", new ArrayList());
				}
			long userCountryId = 0;
			
			List list= (List) getCityProxy().getCountryList(userCountryId);
			for (int i = 0; i < list.size(); i++) {
				ICountry country = (ICountry) list.get(i);
				if (country.getCountryCode().trim().equalsIgnoreCase("IN")) {
					 userCountryId = country.getIdCountry();
				}
			}
			 resultMap.put("userRegionList", getUserRegionList());
			  resultMap.put("countryList", getCountryList(initcountryId));
			  resultMap.put("branchList", getBranchList());
			}
		
		//long stateId = 0, countryId = 0;
		ICMSTeamProxy proxyTeam = new CMSTeamProxy();
		ITeamType[] teamTypearr = proxyTeam.getNodeTeamTypes();
		for(int i =0;i<teamTypearr.length;i++){
			ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
			for (int j = 0; j < teamTypeMembershipArr.length; j++) {
				teamTypeMembershipLst.add(teamTypeMembershipArr[j]);
			}
		}
		
		resultMap.put("departmentList", getDepartmentList());
		resultMap.put("TeamTypeMembershipLst", teamTypeMembershipLst);
		resultMap.put("isUnlock", map.get("isUnlock"));
		
		  //resultMap.put("ISystemBankBranchTrxValue", systemBankBranchTrxValue);
//		  resultMap.put("stateList", getStateList());
//		  resultMap.put("countryList", getCountryList(countryId));
//		  resultMap.put("regionList", getRegionList(stateId));
//		  resultMap.put("cityList", getCityList());
//		  resultMap.put("branchList", getBranchList());
		} catch (EntityNotFoundException e) {
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
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = bankBranch.getSystemBankBranchCode();
				String val = bankBranch.getSystemBankBranchName();
				String value=id+"-"+val;
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
