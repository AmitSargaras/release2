package com.integrosys.cms.ui.collateral.property;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2Dao;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.component.commondata.app.CommonDataSingleton;


public class ViewPreviousValuation2Cmd extends AbstractCommand {

	private IOtherBankProxyManager otherBankProxyManager ;
	private IForexFeedProxy forexFeedProxy;
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	}
	private ISystemBankBranchProxyManager systemBankBranchProxy;
		 
	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}
	
	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
	private IStateProxyManager stateProxy = (IStateProxyManager)BeanHouse.get("stateProxy");
	
	private IPincodeMappingBusManager pincodeMappingBusManager = (IPincodeMappingBusManager)BeanHouse.get("pincodeMappingBusManager");
	
	public String[][] getParameterDescriptor() {
			return (new String[][] {
					{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				    { "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
					{ "trxID", "java.lang.String", REQUEST_SCOPE },
					{ "userName", "java.lang.String", REQUEST_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
					{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
							GLOBAL_SCOPE },
					{ "frame", "java.lang.String", SERVICE_SCOPE },
					{ "from", "java.lang.String", REQUEST_SCOPE },
					{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
					{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
		            { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },	
				});
		}
	public String[][] getResultDescriptor() {
			return (new String[][] {
					{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
					{ "collateralID", "java.lang.String", REQUEST_SCOPE },
					{ "collateralID", "java.lang.String", SERVICE_SCOPE },
					{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
					{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
					{ "flag1", "java.lang.String", SERVICE_SCOPE },
					{ "trxID", "java.lang.String", REQUEST_SCOPE },
					{ "userName", "java.lang.String", REQUEST_SCOPE },
					{ "from_page", "java.lang.String", SERVICE_SCOPE },
					{ "bcaLocationList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "frame", "java.lang.String", SERVICE_SCOPE }, { "from", "java.lang.String", SERVICE_SCOPE },
					{ "branchList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
					{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
					{ "countryList","java.util.List",SERVICE_SCOPE},
		            { "regionList","java.util.List",SERVICE_SCOPE},
		            { "stateList","java.util.List",SERVICE_SCOPE},
		            { "cityList","java.util.List",SERVICE_SCOPE},
		            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
		            { "countryNme", "java.lang.String", REQUEST_SCOPE },
		            { "valuationAgencyList","java.util.List",SERVICE_SCOPE},
		            { "valuationAgencyName_v2", "java.lang.String", REQUEST_SCOPE },	            
		            { "collateralList", "java.util.List", SERVICE_SCOPE },
		            { "currencyList", "java.util.List", SERVICE_SCOPE },
		            { "insuranceList",  "java.util.List", SERVICE_SCOPE },
		            { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
		            { "cityName_v2", "java.lang.String", SERVICE_SCOPE },
			        { "stateName_v2", "java.lang.String", SERVICE_SCOPE },
			        { "regionName_v2", "java.lang.String", SERVICE_SCOPE },
			        { "countryName_v2", "java.lang.String", SERVICE_SCOPE },
			        { "preValDateList_v2","java.util.List",SERVICE_SCOPE},
			        { "preMortgageCreationList","java.util.List",SERVICE_SCOPE}, 
			        { "propertyValuation2","com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation2",SERVICE_SCOPE}, 
			});
		}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
				AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		List insuranceList = (List) map.get("insuranceList");
		if(insuranceList ==null){
			insuranceList=new ArrayList();
		}
	
		ICollateral iCol = (ICollateral)map.get("form.collateralObject"); 
		List collateralList = new ArrayList();
		ISystemBankBranch systemBankBranch = null;
		String countryNme = "";
		String valuationAgencyName_v2 = "";
		String event = (String) map.get("event");
		BigDecimal exchangeRate = null;
	    String idStrVal = (String) map.get("collateralID");
	 	IValuationAgencyProxyManager valuationProxy = (IValuationAgencyProxyManager)BeanHouse.get("valuationAgencyProxy");
		DefaultLogger.debug(this, "event is : " + event);
		
		IPropertyValuation2Dao iPropertyValuation2Dao = (IPropertyValuation2Dao)BeanHouse.get("propertyValuation2Dao");
		IPropertyCollateral ipropertyCol=(IPropertyCollateral)iCol;
		 String  preValId=ipropertyCol.getPreValDate_v2();
		 IPropertyValuation2 propertyValuation2=null;
		 if(null!=preValId && !"".equals(preValId)) {
			 propertyValuation2 = iPropertyValuation2Dao.getPropertyValuation2(new Long(preValId));
		 }
		result.put("propertyValuation2", propertyValuation2);

		if(propertyValuation2!=null) {
			collateralList = getCollateralCodeList("PT701");
			String countryCode =propertyValuation2.getSecLocation();
			systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, propertyValuation2.getSecOrganization());
			countryNme = getCountryNamebyCode(countryCode);
		}
		List regionList = new ArrayList();
		List stateList = new ArrayList();
		List cityList = new ArrayList();
		
		List preValDateList_v2=new ArrayList();
		List preMortgageCreationList=new ArrayList();
		
		String cityName_v2=null;
		String stateName_v2=null;
		String regionName_v2=null;
		String countryName_v2=null;
		
		String thirdPartyCity = null;
		String thirdPartyState = null;
		String thirdPartyStateId = null;
		if(null!=propertyValuation2) {
			if(propertyValuation2.getCountryV2()!=null){
				regionList = getRegionList(propertyValuation2.getCountryV2());
				stateList = getStateList(propertyValuation2.getRegionV2());
				cityList = getCityList(propertyValuation2.getStateV2());
			}
			if(propertyValuation2.getNearestCityV2()!=null && !propertyValuation2.getNearestCityV2().equals(""))
				cityName_v2 = getOtherBankProxyManager().getCityName(propertyValuation2.getNearestCityV2());
			
			if(propertyValuation2.getStateV2()!=null && !propertyValuation2.getStateV2().equals(""))
				stateName_v2 = getOtherBankProxyManager().getStateName(propertyValuation2.getStateV2());
			
			if(propertyValuation2.getRegionV2()!=null && !propertyValuation2.getRegionV2().equals(""))
				regionName_v2 = getOtherBankProxyManager().getRegionName(propertyValuation2.getRegionV2());
			
			if(propertyValuation2.getCountryV2()!=null && !propertyValuation2.getCountryV2().equals(""))
				countryName_v2 = getOtherBankProxyManager().getCountryName(propertyValuation2.getCountryV2());
			
			valuationAgencyName_v2 = valuationProxy.getValuationAgencyName(propertyValuation2.getValuatorCompanyV2());
    	
		}
    	ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
    	preValDateList_v2=collateralDAO.getPreviousValList2(propertyValuation2.getCollateralId());
    	preMortgageCreationList=collateralDAO.getPreMortgageCreationList(propertyValuation2.getCollateralId());

		Map collateralMaster = null;
		String collateralCategory = null;	
		String cersaiApplicableInd = null;
		if(null!=propertyValuation2) {
			collateralMaster = CollateralDAOFactory.getDAO().getCollateralCategoryAndCersaiInd(propertyValuation2.getCollateralId());
			if(null != collateralMaster ) {
				collateralCategory = (String) collateralMaster.get("COLLATERAL_CATEGORY"); 
				cersaiApplicableInd = (String) collateralMaster.get("CERSAI_IND");
			}
		
			thirdPartyStateId = propertyValuation2.getThirdPartyState();
			if(StringUtils.isNotBlank(thirdPartyStateId)) {
				thirdPartyState = getOtherBankProxyManager().getStateName(thirdPartyStateId);
			}
			if(StringUtils.isNotBlank(propertyValuation2.getThirdPartyCity())) {
				thirdPartyCity = getOtherBankProxyManager().getCityName(propertyValuation2.getThirdPartyCity());
			}
		}
	    	
		List mandatoryThirdPartyEntitiesList = CollateralHelper.getMandatoryEntitiesForCinForThirdParty();
		String mandatoryThirdPartyEntitiesStr = UIUtil.getDelimitedStringFromList(mandatoryThirdPartyEntitiesList, ",");
			
		result.put("valuationAgencyName_v2", valuationAgencyName_v2);
		result.put("cityName_v2",cityName_v2);
    	result.put("stateName_v2",stateName_v2);
    	result.put("regionName_v2",regionName_v2);
    	result.put("countryName_v2",countryName_v2);
    	result.put("preValDateList_v2",preValDateList_v2);
    	result.put("preMortgageCreationList",preMortgageCreationList);
    	
    	result.put("countryList",getCountryList());
    	result.put("regionList",regionList);
    	result.put("stateList",stateList);
    	result.put("cityList",cityList);
    	result.put("currencyList", getCurrencyList());
    	
    	//Third Party City
    	result.put("securityOwnership",getSecurityOwnership());
    	result.put("thirdPartyStateList",getThirdPartyStateList());
    	result.put("thirdPartyCityList",getThirdPartyCityList(thirdPartyStateId));
    	result.put("thirdPartyState", thirdPartyState);
    	result.put("thirdPartyCity", thirdPartyCity);
    	result.put("pincodesString",getStatePincodeString());
    	
    	result.put("collateralCategory",collateralCategory);
    	result.put("cersaiApplicableInd",cersaiApplicableInd);
    	result.put("mandatoryThirdPartyEntitiesStr",mandatoryThirdPartyEntitiesStr);
    	
		if(exchangeRate!= null)
		{
			result.put("exchangeRate", exchangeRate.toString());
		}
		else
		{
			result.put("exchangeRate", exchangeRate);
		}
		

		result.put("collateralID", idStrVal);
		result.put("from", map.get("from"));
		result.put("branchList", getBranchList());
		result.put("collateralList", collateralList);
		result.put("systemBankBranch", systemBankBranch);
		result.put("countryNme", countryNme);
		result.put("bankList",getBankList());
		result.put("bankListMap", getBankListHashMap());
		result.put("insuranceList", insuranceList);


		
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	private List getBankList() {
		List lbValList = new ArrayList();
		try {
			
			ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");
			List systemBankList = systemBankProxy.getAllActual();
			
			for (int i = 0; i < systemBankList.size(); i++) {
				ISystemBank systemBank = (ISystemBank) systemBankList.get(i);
				String id = Long.toString(systemBank.getId());
				String val = systemBank.getSystemBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
			
			IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager) BeanHouse.get("otherBankProxyManager");
			SearchResult sr = otherBankProxyManager.getOtherBankList("", "");
			List otherBankList = (List) sr.getResultList();
			for (int i = 0; i < otherBankList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) otherBankList.get(i);
				String id = Long.toString(otherBank.getId());
				String val = otherBank.getOtherBankName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	private HashMap getBankListHashMap() {
		HashMap bankListMap = new HashMap();
		try {
			
			ISystemBankProxyManager systemBankProxy = (ISystemBankProxyManager) BeanHouse.get("systemBankProxy");
			List systemBankList = systemBankProxy.getAllActual();
			
			for (int i = 0; i < systemBankList.size(); i++) {
				ISystemBank systemBank = (ISystemBank) systemBankList.get(i);
				String id = Long.toString(systemBank.getId());
				String val = systemBank.getSystemBankName();
				bankListMap.put(id, val);
			}
			
			IOtherBankProxyManager otherBankProxyManager = (IOtherBankProxyManager) BeanHouse.get("otherBankProxyManager");
			SearchResult sr = otherBankProxyManager.getOtherBankList("", "");
			List otherBankList = (List) sr.getResultList();
			for (int i = 0; i < otherBankList.size(); i++) {
				IOtherBank otherBank = (IOtherBank) otherBankList.get(i);
				String id = Long.toString(otherBank.getId());
				String val = otherBank.getOtherBankName();
				bankListMap.put(id, val);
			}
		} catch (Exception ex) {
		}
		return bankListMap;
	}
		
	private List getBranchList() {
		List lbValList = new ArrayList();
		try {
			
			 SearchResult searchResult = getSystemBankBranchProxy().getAllActualBranch();
			 List idList = (List) searchResult.getResultList();
			
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

	public IForexFeedProxy getForexFeedProxy() {
		return (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
	}

	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
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
		
	private List getCollateralCodeList(String subTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (subTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				List colCodeLst = helper.getSBMISecProxy().getCollateralCodeBySubTypes(subTypeValue);
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String name = codeLst[1];
						LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(name), UIUtil
								.replaceSpecialCharForXml(code));
						lbValList.add(lvBean);
					}
				}
			}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private ISystemBankBranch getSysBankBranchByCuntryAndBranchCode(String country , String branchCode) {
		ISystemBankBranch branch = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				 branch = CollateralDAOFactory.getDAO().getSysBankBranchByCuntryAndBranchCode(country , branchCode);
		}
		catch (Exception ex) {
		}
		return branch;
	}
	private String getCountryNamebyCode(String countryCode) {
		List lbValList = new ArrayList();
		String value = null;
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICountry[] country = CollateralDAOFactory.getDAO().getCountryNamebyCode(countryCode);
				
				
				if (country != null) {
					for (int i = 0; i < country.length; i++) {
						ICountry lst = country[i];
						String id = lst.getCountryCode();
						value = lst.getCountryName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
		}
		return value;
	}
	private List getValuationAgencyList(IValuationAgencyProxyManager valuationProxy) {
		List lbValList = new ArrayList();
		try {
			
			ArrayList valuationAgencyList = new ArrayList();
			valuationAgencyList = (ArrayList) valuationProxy.getAllActual();
			
			for (int i = 0; i < valuationAgencyList.size(); i++) {
				IValuationAgency valuationAgency = (IValuationAgency) valuationAgencyList.get(i);
				String id = Long.toString(valuationAgency.getId());
				String val = valuationAgency.getValuationAgencyName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
				
	private String getInsurerNameFromCode(String insurerName) {
		List lbValList = new ArrayList();
		String insurerNam= new String();
		try {
			
		
			
		
			 SearchResult searchResult =  getOtherBankProxyManager().getInsurerNameFromCode(insurerName);
			 List insurerNamValue = (List) searchResult.getResultList();
			 
			 for (int i = 0; i < insurerNamValue.size(); i++) {
				 IInsuranceCoverage insurernam = (IInsuranceCoverage) insurerNamValue.get(i);
					String id = insurernam.getCompanyName();
					String val =null;
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
					insurerNam=id;
				}
			
		} catch (Exception ex) {
		}
		
		return insurerNam;
		
		
	
	}
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
						String id = lst.getBuyCurrency().trim();
						String value = lst.getCurrencyIsoCode().trim();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
				
	private List getThirdPartyStateList() {
		List lbValList = new ArrayList();
		try {
			if(null != stateProxy) {
				SearchResult stateListResult = stateProxy.listState(null, null);
				List stateList = (List) stateListResult.getResultList();

				if(null != stateList) {
					for (int i = 0; i < stateList.size(); i++) {
						IState state = (IState) stateList.get(i);
						if (ICMSConstant.TRX_STATE_ACTIVE.equals(state.getStatus())) {
							String id = Long.toString(state.getIdState());
							String val = state.getStateName();
							LabelValueBean lvBean = new LabelValueBean(val, id);
							lbValList.add(lvBean);
						}
					}
				}
			}
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
		
	private List getSecurityOwnership() {
		List lbValList = new ArrayList();
		HashMap colCategoryMap;
		ArrayList colCategoryLabel = new ArrayList();
		ArrayList colCategoryValue = new ArrayList();

		colCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.COMMON_CODE_SECURITY_OWNERSHIP);
		colCategoryValue.addAll(colCategoryMap.keySet());
		colCategoryLabel.addAll(colCategoryMap.values());
		try {
			if (null != colCategoryLabel) {
				for (int i = 0; i < colCategoryLabel.size(); i++) {
					String id = colCategoryLabel.get(i).toString();
					String val = colCategoryValue.get(i).toString();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return CommonUtil.sortDropdown(lbValList);
	}
		
	private List getThirdPartyCityList(String thirdPartyStateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCityList(thirdPartyStateId);				
		
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
		
		if(null != pincodeMappingBusManager) {
			pincodeMap = (HashMap<String, String>) pincodeMappingBusManager.getActiveStatePinCodeMap();
		}
		
		String pincodesStr = UIUtil.getDelimitedStringFromMap(pincodeMap, ",", "=");
		
		return pincodesStr;
	}
}
