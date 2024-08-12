package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAO;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.valuationAgency.bus.ICity;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.component.user.app.bus.OBCommonUser;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 17-03-2011
	 *
	 */

public class ManualInputPrepareCreateCustomerCommand extends AbstractCommand{
	
private IRelationshipMgrProxyManager relationshipMgrProxyManager;

private IPartyGroupProxyManager partyGroupProxy;

private IValuationAgencyProxyManager valuationAgencyProxy;

private IOtherBankProxyManager otherBankProxyManager;
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
	return otherBankProxyManager;
}

public void setOtherBankProxyManager(
		IOtherBankProxyManager otherBankProxyManager) {
	this.otherBankProxyManager = otherBankProxyManager;
}

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
	return valuationAgencyProxy;
}

public void setValuationAgencyProxy(
		IValuationAgencyProxyManager valuationAgencyProxy) {
	this.valuationAgencyProxy = valuationAgencyProxy;
}

	public IPartyGroupProxyManager getPartyGroupProxy() {
	return partyGroupProxy;
}

public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
	this.partyGroupProxy = partyGroupProxy;
}

	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
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
				
				{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
				{"event","java.lang.String",REQUEST_SCOPE}, //Valid rating CR
				
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
				{ "partyGroupList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "systemBranchList", "java.util.ArrayList", SERVICE_SCOPE },
				   {"relationshipMgrList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
				   { "countryList", "java.util.List", SERVICE_SCOPE },
					{ "regionList", "java.util.List", SERVICE_SCOPE },
					{ "cityList", "java.util.List", SERVICE_SCOPE },
					{ "stateList", "java.util.List", SERVICE_SCOPE },
					//{ "branchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch",SERVICE_SCOPE },
					{ "partyGrpList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "directorList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "vendorList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "rbiIndustryCodeList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "validate", "java.lang.String", SERVICE_SCOPE },
					{ "systemList", "java.util.List", SERVICE_SCOPE },
					{"customerCifId","java.lang.String",REQUEST_SCOPE},
					{ "otherBankList", "java.util.List", SERVICE_SCOPE },
					{ "branchList", "java.util.List", SERVICE_SCOPE },
					{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
					{ "classActivityList", "java.util.List", SERVICE_SCOPE },
					{ "currencyList", "java.util.List", SERVICE_SCOPE },
					{ "rmRegionList", "java.util.List", SERVICE_SCOPE },
					{ "facNameList", "java.util.List", SERVICE_SCOPE },
					{ "countryList", "java.util.List", SERVICE_SCOPE },
					{ "securityNameList", "java.util.List", SERVICE_SCOPE },
					{ "securityCodeTypeList", "java.util.HashMap", SERVICE_SCOPE },
					{ "facList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "partyNamePanMap", "java.util.HashMap", SERVICE_SCOPE }, //Valid rating CR
					{ "bankMethodListNew1", "java.util.List", SERVICE_SCOPE },
					{ "bankMethodListNew2", "java.util.List", SERVICE_SCOPE },
			});
		
		}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		long stateId = 0, countryId = 0;
		//relationshipMgr dropdown-----------------start-----------------------------------------
		String rmCode = (String) map.get("RMCode");
		String rmName = (String) map.get("RMName");
		String validate = "";
      //  SearchResult relationshipMgrList= getRelationshipMgrProxyManager().getRelationshipMgrList(rmCode,rmName);
        map.put("relationshipMgrList", getCityList(stateId));
        //------------------------------------------end--------------------------------------------
        OBCommonUser globalUser = (OBCommonUser) map.get(IGlobalConstant.GLOBAL_LOS_USER);
        String branchCode= globalUser.getEjbBranchCode();
    	ISystemBankBranchProxyManager systemBankBranchProxyManager=(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
    	 SearchResult searchResult = null;
		try {
			searchResult = systemBankBranchProxyManager.getAllActualBranch();
		} catch (SystemBankBranchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TrxParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransactionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		IPartyGroupDao partyDao = (IPartyGroupDao)BeanHouse.get("partyGroupDao");
		map.put("customerCifId", partyDao.getCustomerIdCode());
		
    	 List branchList= new ArrayList();
    	 List ifscBranchList= new ArrayList();
		 List idList = (List) searchResult.getResultList();
		/*for (int i = 0; i < idList.size(); i++) {
			ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
			if(branchCode.equals(bankBranch.getSystemBankBranchCode())){
				
				map.put("branchObj", bankBranch);
				break;
			}
		}*/
        //partyGroup dropdown-----------------start-----------------------------------------
        ArrayList PartyGroupList = new ArrayList();
		try {
			PartyGroupList = (ArrayList) getPartyGroupProxy().getAllActual();
		} catch (PartyGroupException e) {
						e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		} 
		
		map.put("partyGroupList", PartyGroupList);
		map.put("systemBranchList", idList);
		
		 //------------------------------------------end--------------------------------------------
		
		//country, region, state, city dropdown-----------------start-----------------------------------------
		map.put("validate",validate);
		map.put("stateList", getStateList());
		map.put("countryList", getCountryList(countryId));
		map.put("regionList", getRegionList(stateId));
		map.put("cityList", getCityList(stateId));
		map.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		map.put("systemList", new ArrayList());
		map.put("otherBankList", getOtherBankList());
		map.put("partyGrpList", new ArrayList());
		map.put("directorList", new ArrayList());
		map.put("vendorList", new ArrayList());
		map.put("classActivityList", getClassActivityList());
		map.put("rbiIndustryCodeList", new ArrayList());
		map.put("branchList",new ArrayList());
		map.put("ifscBranchList",new ArrayList());
		map.put("facList", new ArrayList());
		map.put("status", "ACTIVE");
		map.put("bankMethodListNew1", getBankingMethodList());
		map.put("bankMethodListNew2",new ArrayList());
		
		
		List facNameList = new ArrayList();
		List securityNameList=new ArrayList();
				
		try{
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			facNameList = limitDAO.getFacNameList();
			
			
			ICollateralDAO collateralDao = CollateralDAOFactory.getDAO();
			securityNameList=collateralDao.getSeurityNames();
			
			
		}catch(Exception e){
			DefaultLogger.error(this,e.getCause());
			e.printStackTrace();
		}
		
		map.put("facNameList", facNameList);
		map.put("countryList", getCountryList());
		map.put("securityNameList",securityNameList);
		//------------------------------------------end--------------------------------------------
		//---------RM region--------------dropdown--------------------start---------------
		map.put("rmRegionList", getRegionList());
		//-------------------------------------------------end----------------------------------
		map.put("currencyList", getCurrencyList());
		
		//Start:Uma:Valid RAting CR
/*		String event  = (String)map.get("event");
		Map<String,String> partyNamePanMap=new HashMap<String,String>();
		
		if("prepare_create_customer_partymaker".equals(event)){
		String cifId=(String)map.get("customerCifId");
		CustomerDAO customerDao = new CustomerDAO();
		
		if(null!=cifId && !"".equals(cifId))
			partyNamePanMap=customerDao.getPanDetails(cifId);

		}

		DefaultLogger.debug(this,"partyNamePanMap size"+ partyNamePanMap.size());
		map.put("partyNamePanMap", partyNamePanMap);*/
		//End:Uma:Valid Rating CR
		
			
		DefaultLogger.debug(this, "Inside doExecute() ManualInputPrepareCreateCustomerCommand");
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		return returnMap;
	
		
	
	}
	
	private List getOtherBankList() {
		SearchResult otherBankSearchList= new SearchResult();
		IOtherBankDAO otherBankDao = (IOtherBankDAO)BeanHouse.get("otherBankDao");
		otherBankSearchList = otherBankDao.getOtherBankList();
		ArrayList otherBankList=(ArrayList) otherBankSearchList.getResultList();
		List lbValList = new ArrayList();
		try {
			for (int i = 0; i < otherBankList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) otherBankList.get(i);
				if (otherBank.getStatus().equals("ACTIVE")) {
					String code = otherBank.getOtherBankCode();
					String name = otherBank.getOtherBankName();
					LabelValueBean lvBean = new LabelValueBean(name, code);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	
	private List getCountryList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCountryList();
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
	
	//country, region, state, city dropdown-----------------start-----------------------------------------
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List)getValuationAgencyProxy().getCountryList(countryId);
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
	
	private List getCityList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = new ArrayList();//(List) getValuationAgencyProxy().getCityList(stateId);	 	
			for (int i = 0; i < idList.size(); i++) {
					ICity city = (ICity)idList.get(i);
					if( city.getStatus().equals("ACTIVE")) {
						String id = "";
						String val = "";
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
//-----------------------------------------------------end---------------------------------------------------
	
	//------------relationship Mgr Region --------------------------start-------------------------------
	 private List getRegionList() {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getRelationshipMgrProxyManager().getRegionList(PropertyManager.getValue("clims.application.country"));				
			
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
	 
	 
	 private List getClassActivityList() {
			List classActivityList= new ArrayList();
			CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_RBI_CODE);
			Map labelValueMap = commonCode.getLabelValueMap();			
			Iterator iterator = labelValueMap.entrySet().iterator();
			String label;
			String value;
			while (iterator.hasNext()) {
		        Map.Entry pairs = (Map.Entry)iterator.next();
		        value=pairs.getKey().toString();
		        label=pairs.getKey()+"-"+pairs.getValue();
				LabelValueBean lvBean = new LabelValueBean(label,value);
				classActivityList.add(lvBean);
		    }
			return CommonUtil.sortDropdown(classActivityList);
		}	 
	 

		private List getCurrencyList() {
			List lbValList = new ArrayList();
			try {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
					
					if (currency != null) {
						for (int i = 0; i < currency.length; i++) {
							IForexFeedEntry lst = currency[i];
//							String id = lst.getCurrencyIsoCode().trim();
							String id = lst.getBuyCurrency().trim();
							String value = lst.getCurrencyIsoCode().trim();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							lbValList.add(lvBean);
						}
					}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		private List getBankingMethodList() {
			List bankingMethodList= new ArrayList();
			CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.BANKING_METHOD);
			Map labelValueMap = commonCode.getLabelValueMap();			
			Iterator iterator = labelValueMap.entrySet().iterator();
			String label;
			String value;
			String valueOnly;
			while (iterator.hasNext()) {
		        Map.Entry pairs = (Map.Entry)iterator.next();
		        value=pairs.getKey().toString();
		        label=pairs.getKey()+"-"+pairs.getValue();
		        valueOnly=(String) pairs.getValue();
//				LabelValueBean lvBean = new LabelValueBean(label,value);
		        LabelValueBean lvBean = new LabelValueBean(label,valueOnly);
				bankingMethodList.add(lvBean);
		    }
			return CommonUtil.sortDropdown(bankingMethodList);
		}	 
		
	//------------------------------------------------------------end---------------------------------------
		
		
		
}