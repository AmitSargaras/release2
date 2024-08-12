package com.integrosys.cms.ui.user;

import java.rmi.RemoteException;
import java.util.*;
import java.util.HashMap;

import org.apache.struts.util.LabelValueBean;


import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.exception.EntityNotFoundException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bizstructure.proxy.CMSTeamProxy;
import com.integrosys.cms.app.bizstructure.proxy.ICMSTeamProxy;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.proxy.SystemBankBranchProxyManagerImpl;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.user.proxy.CMSUserProxy;
import com.integrosys.cms.app.user.trx.OBUserTrxValue;
import com.integrosys.cms.ui.bizstructure.AbstractTeamCommand;
import com.integrosys.cms.ui.geography.city.CityProxyManagerImpl;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeamType;
import com.integrosys.component.bizstructure.app.bus.ITeamTypeMembership;
import com.integrosys.component.common.transaction.ICompTrxResult;
import com.integrosys.component.user.app.bus.OBCommonUser;
import com.integrosys.component.user.app.bus.UserException;
import com.integrosys.component.user.app.constant.UserConstant;

public class MakerPrepareAddUserCmd extends AbstractCommand implements ICommonEventConstant {
	
	/**
	 * Command class to load the add page for user creation
	 * @author $Author: Archana Panchapakesan
	 * @version $Revision: 1.0 $
	 * @since $Date: 2011/06/09 
	 */
	public MakerPrepareAddUserCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "CommonUser", "com.integrosys.cms.app.user.bus.OBCMSUser", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "CommonUserTrxValue", "com.integrosys.cms.app.user.trx.OBUserTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "empId", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "request.ITrxResult", "com.integrosys.component.common.transaction.ICompTrxResult", REQUEST_SCOPE },
				{ "Error_EmpId", "java.lang.String", REQUEST_SCOPE }, 
//				{ "stateList", "java.util.List", SERVICE_SCOPE },
//				{ "countryList", "java.util.List", SERVICE_SCOPE },
//				{ "regionList", "java.util.List", SERVICE_SCOPE },
//				{ "cityList", "java.util.List", SERVICE_SCOPE },
//				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{"TeamTypeMembershipLst","java.util.List",SERVICE_SCOPE},});
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
		ArrayList teamTypeMembershipLst = new ArrayList();
		OBCommonUser user = (OBCommonUser) map.get("CommonUser");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		String status = (String) map.get("status");
		long stateId = 0, countryId = 0;
		try {
			
			ICMSTeamProxy proxy = new CMSTeamProxy();
			ITeamType[] teamTypearr = proxy.getNodeTeamTypes();
			for(int i =0;i<teamTypearr.length;i++){
				ITeamTypeMembership[] teamTypeMembershipArr = teamTypearr[i].getTeamTypeMemberships();
				for (int j = 0; j < teamTypeMembershipArr.length; j++) {
					teamTypeMembershipLst.add(teamTypeMembershipArr[j]);
				}
			}
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		resultMap.put("TeamTypeMembershipLst", teamTypeMembershipLst);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		 resultMap.put("stateList", getStateList());
//		  resultMap.put("countryList", getCountryList(countryId));
//		  resultMap.put("regionList", getRegionList(stateId));
//		  resultMap.put("cityList", getCityList());
//		  resultMap.put("branchList", getBranchList());
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
	
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			ICityProxyManager cityProxyManager = new CityProxyManagerImpl();
			List idList = (List)cityProxyManager.getCountryList(countryId);
//			System.out.println("Country listing ::::::::::::::::::::::"+idList);
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
		return (List) CommonUtil.sortDropdown(lbValList);
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
		}
		return (List) CommonUtil.sortDropdown(lbValList);
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
		return (List) CommonUtil.sortDropdown(lbValList);
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
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			ISystemBankBranchProxyManager bankBranchProxyManager = new SystemBankBranchProxyManagerImpl();
			 SearchResult searchResult = bankBranchProxyManager.getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
//			 System.out.println("branch listing :::::::::::::::"+idList);
			for (int i = 0; i < idList.size(); i++) {
				ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
				String id = Long.toString(bankBranch.getId());
				String val = bankBranch.getSystemBankBranchName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}



}
