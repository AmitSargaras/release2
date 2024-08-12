package com.integrosys.cms.ui.collateral.property;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeaircraft.OBSpecificChargeAircraft;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargeplant.OBSpecificChargePlant;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1Dao;
import com.integrosys.cms.app.collateral.bus.type.property.OBPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.subtype.comgeneral.OBCommercialGeneral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterDAOFactory;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterDao;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.bus.forex.ForexFeedGroupException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMappingBusManager;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.IValuationAgency;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.host.eai.security.bus.SecurityDaoImpl;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.insurancecoverage.IInsuranceCoverage;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.component.commondata.app.CommonDataSingleton;


public class ViewPreviousValuationCmd extends AbstractCommand {

private IOtherBankProxyManager otherBankProxyManager ;
private IForexFeedProxy forexFeedProxy;
	
	

	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	
	
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
		          //  { "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
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
					{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
					
					{ "collateralID", "java.lang.String", REQUEST_SCOPE },
					{ "collateralID", "java.lang.String", SERVICE_SCOPE },
				
					{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
					{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
					{ "flag1", "java.lang.String", SERVICE_SCOPE },
					{ "trxID", "java.lang.String", REQUEST_SCOPE },
					{ "userName", "java.lang.String", REQUEST_SCOPE },
					//{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
					
					{ "from_page", "java.lang.String", SERVICE_SCOPE },
					{ "bcaLocationList", "java.util.ArrayList", SERVICE_SCOPE },
				
					{ "frame", "java.lang.String", SERVICE_SCOPE }, { "from", "java.lang.String", SERVICE_SCOPE },
			//	{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", FORM_SCOPE },
			//	{ "form.collateralObject", "com.integrosys.cms.app.collateral.bus.ICollateral", SERVICE_SCOPE },
					{ "branchList", "java.util.List", ICommonEventConstant.SERVICE_SCOPE },
					{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
					{ "countryList","java.util.List",SERVICE_SCOPE},
		            { "regionList","java.util.List",SERVICE_SCOPE},
		            { "stateList","java.util.List",SERVICE_SCOPE},
		            { "cityList","java.util.List",SERVICE_SCOPE},
		         
		          //  { "collateralList", "java.util.List", REQUEST_SCOPE },
		            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
		            { "countryNme", "java.lang.String", REQUEST_SCOPE },
		            { "valuationAgencyList","java.util.List",SERVICE_SCOPE},
		            { "valuationAgencyName_v1", "java.lang.String", REQUEST_SCOPE },	            
		            
		            { "collateralList", "java.util.List", SERVICE_SCOPE },
		        //    { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", SERVICE_SCOPE },
		         //   { "countryNme", "java.lang.String", SERVICE_SCOPE },
		        
		       { "currencyList", "java.util.List", SERVICE_SCOPE },
		       { "insuranceList",  "java.util.List", SERVICE_SCOPE },
		         
		     //       { "insuranceCoverageList",  "java.util.List", SERVICE_SCOPE },
		             { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
		      //       { "insuranceCheck", "java.lang.String", SERVICE_SCOPE },
		     
		             { "cityName_v1", "java.lang.String", SERVICE_SCOPE },
			            { "stateName_v1", "java.lang.String", SERVICE_SCOPE },
			            { "regionName_v1", "java.lang.String", SERVICE_SCOPE },
			            { "countryName_v1", "java.lang.String", SERVICE_SCOPE },
			            { "preValDateList_v1","java.util.List",SERVICE_SCOPE},
			            { "preMortgageCreationList","java.util.List",SERVICE_SCOPE}, 
			            { "propertyValuation1","com.integrosys.cms.app.collateral.bus.type.property.IPropertyValuation1",SERVICE_SCOPE}, 
			});

		}

		/**
		 * This method does the Business operations with the HashMap and put the
		 * results back into the HashMap.Here reading for Company Borrower is done.
		 * 
		 * @param map is of type HashMap
		 * @return HashMap with the Result
		 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
		 *         on errors
		 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
		 *         on errors
		 */
		public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
				AccessDeniedException {

		//	String camStartDate =null;
			String  llPLeId =null;
			HashMap result = new HashMap();
			HashMap exceptionMap = new HashMap();
			HashMap temp = new HashMap();
			List insuranceList = (List) map.get("insuranceList");
			if(insuranceList ==null){
				insuranceList=new ArrayList();
			}
			
		
			ICollateralTrxValue itrxValue1 = (ICollateralTrxValue)map.get("serviceColObj");
			ICollateral iCol = (ICollateral)map.get("form.collateralObject"); 
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext"); 
			ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
			List collateralList = new ArrayList();
			ISystemBankBranch systemBankBranch = null;
			String countryNme = "";
			String valuationAgencyName_v1 = "";
			//HashMap docCodeWithStocks = new HashMap();		
			String event = (String) map.get("event");
			/*String flag = (String) map.get("flag");
			String flag1 = (String) map.get("flag1");*/
			BigDecimal exchangeRate = null;
		/*	String trxID = (String) map.get("trxID");
	        String userName=(String)map.get("userName");*/
	        String idStrVal = (String) map.get("collateralID");
	    /*    String countryId = (String) map.get("countryId");
	    	String regionId = (String) map.get("regionId");
	    	String stateId = (String) map.get("stateId");
*/
	    	Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
	    //	String LegalID = (String) map.get("LegalID");
	     	IValuationAgencyProxyManager valuationProxy = (IValuationAgencyProxyManager)BeanHouse.get("valuationAgencyProxy");
	        
			DefaultLogger.debug(this, "event is : " + event);
		
					
		
				IPropertyValuation1Dao iPropertyValuation1Dao = (IPropertyValuation1Dao)BeanHouse.get("propertyValuation1Dao");
				IPropertyCollateral ipropertyCol=(IPropertyCollateral)iCol;
				 String  preValId=ipropertyCol.getPreValDate_v1();
				 IPropertyValuation1 propertyValuation1=null;
				 if(null!=preValId || !preValId.isEmpty()) {
					 propertyValuation1 = iPropertyValuation1Dao.getPropertyValuation1(new Long(preValId));
				 }
				//result.put("form.collateralObject", propertyValuation1);
				result.put("propertyValuation1", propertyValuation1);
			
		
			/*try { //Add By Govind S:05/09/2011
				if(propertyValuation1!=null){
			    exchangeRate = getForexFeedProxy().getExchangeRateWithINR(itrxValue.getCollateral().getCurrencyCode());
			    collateralList = getCollateralCodeList(itrxValue.getStagingCollateral().getCollateralSubType().getSubTypeCode());
			    String countryCode = itrxValue.getStagingCollateral().getCollateralLocation();
			    String branchCode = itrxValue.getStagingCollateral().getSecurityOrganization();
			   
			    systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, branchCode);
			   
			    countryNme = getCountryNamebyCode(countryCode);
			              
				}
			} catch (ForexFeedGroupException e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
			} catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
			}*/
				
				if(propertyValuation1!=null) {
					 collateralList = getCollateralCodeList("PT701");
					 String countryCode =propertyValuation1.getSecLocation();
				  systemBankBranch = getSysBankBranchByCuntryAndBranchCode(countryCode, propertyValuation1.getSecOrganization());
				  
				  countryNme = getCountryNamebyCode(countryCode);
				}
			List regionList = new ArrayList();
			List stateList = new ArrayList();
			List cityList = new ArrayList();
			
			List preValDateList_v1=new ArrayList();
			List preMortgageCreationList=new ArrayList();
			
		/*	String cityName=null;
			String stateName=null;
			String regionName=null;
			String countryName=null;*/
			
			String cityName_v1=null;
			String stateName_v1=null;
			String regionName_v1=null;
			String countryName_v1=null;
			
			String thirdPartyCity = null;
			String thirdPartyState = null;
			String thirdPartyStateId = null;
			
		//	IBankSameCurrency iCol1 = new OBBankSameCurrency();
			
			
			/*	if(iCol5.getCountry()!=null){
		    		regionList = getRegionList(iCol5.getCountry());
		    		stateList = getStateList(iCol5.getRegion());
		    		cityList = getCityList(iCol5.getLocationState());
		    	}
		    	if(iCol5.getNearestCity()!=null && !iCol5.getNearestCity().equals(""))
		    		cityName = getOtherBankProxyManager().getCityName(iCol5.getNearestCity());
		    	
		    	if(iCol5.getLocationState()!=null && !iCol5.getLocationState().equals(""))
		    		stateName = getOtherBankProxyManager().getStateName(iCol5.getLocationState());
		    	
		    	if(iCol5.getRegion()!=null && !iCol5.getRegion().equals(""))
		    		regionName = getOtherBankProxyManager().getRegionName(iCol5.getRegion());
		    	
		    	if(iCol5.getCountry()!=null && !iCol5.getCountry().equals(""))
		    		countryName = getOtherBankProxyManager().getCountryName(iCol5.getCountry());
		    	
		    	valuationAgencyName = valuationProxy.getValuationAgencyName(iCol5.getValuatorCompany());*/
				if(null!=propertyValuation1) {
				if(propertyValuation1.getCountryV1()!=null){
		    		regionList = getRegionList(propertyValuation1.getCountryV1());
		    		stateList = getStateList(propertyValuation1.getRegionV1());
		    		cityList = getCityList(propertyValuation1.getStateV1());
		    	}
		    	if(propertyValuation1.getNearestCityV1()!=null && !propertyValuation1.getNearestCityV1().equals(""))
		    		cityName_v1 = getOtherBankProxyManager().getCityName(propertyValuation1.getNearestCityV1());
		    	
		    	if(propertyValuation1.getStateV1()!=null && !propertyValuation1.getStateV1().equals(""))
		    		stateName_v1 = getOtherBankProxyManager().getStateName(propertyValuation1.getStateV1());
		    	
		    	if(propertyValuation1.getRegionV1()!=null && !propertyValuation1.getRegionV1().equals(""))
		    		regionName_v1 = getOtherBankProxyManager().getRegionName(propertyValuation1.getRegionV1());
		    	
		    	if(propertyValuation1.getCountryV1()!=null && !propertyValuation1.getCountryV1().equals(""))
		    		countryName_v1 = getOtherBankProxyManager().getCountryName(propertyValuation1.getCountryV1());
		    	
		    	valuationAgencyName_v1 = valuationProxy.getValuationAgencyName(propertyValuation1.getValuatorCompanyV1());
		    	
				}
		    	ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
		    	preValDateList_v1=collateralDAO.getPreviousValList(propertyValuation1.getCollateralId());
		    	preMortgageCreationList=collateralDAO.getPreMortgageCreationList(propertyValuation1.getCollateralId());
			
			
			/*	IInsuranceCoverageProxyManager insuranceCoverageProxyManager = (IInsuranceCoverageProxyManager) BeanHouse.get("insuranceCoverageProxyManager");
				
				SearchResult sr = insuranceCoverageProxyManager.getInsuranceCoverageList(null,null);
				HashMap insuranceCoverageMap = new HashMap();
				ArrayList resultList = (ArrayList)sr.getResultList();
				for (int i = 0; i < resultList.size(); i++) {
					IInsuranceCoverage insuranceCoverage = (IInsuranceCoverage) resultList.get(i);
					String id = Long.toString(insuranceCoverage.getId());
					String val = insuranceCoverage.getCompanyName();
					insuranceCoverageMap.put(id, val);
				}
				result.put("insuranceCoverageMap", insuranceCoverageMap);
			
			*/
			
		/*	HashMap orgMap = new HashMap();
			try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(itrxValue.getCollateral().getCollateralLocation());
				
				if (branch != null) {
					for (int i = 0; i < branch.length; i++) {
						ISystemBankBranch lst = branch[i];
						String id = lst.getSystemBankBranchCode();
						String value = lst.getSystemBankBranchName();
						orgMap.put(id,value);
						result.put("orgMap", orgMap);
					}
				}
			}
			catch (Exception ex) {
			}*/
			
			
			//CERSAI CR
			Map collateralMaster = null;
			String collateralCategory = null;	
			String cersaiApplicableInd = null;
		
			if(null!=propertyValuation1) {
				collateralMaster = CollateralDAOFactory.getDAO().getCollateralCategoryAndCersaiInd(propertyValuation1.getCollateralId());
				if(null != collateralMaster ) {
					collateralCategory = (String) collateralMaster.get("COLLATERAL_CATEGORY"); 
					cersaiApplicableInd = (String) collateralMaster.get("CERSAI_IND");
				}
			
				thirdPartyStateId = propertyValuation1.getThirdPartyState();
				if(StringUtils.isNotBlank(thirdPartyStateId)) {
					thirdPartyState = getOtherBankProxyManager().getStateName(thirdPartyStateId);
				}
				if(StringUtils.isNotBlank(propertyValuation1.getThirdPartyCity())) {
					thirdPartyCity = getOtherBankProxyManager().getCityName(propertyValuation1.getThirdPartyCity());
				}
			}
	    	
			List mandatoryThirdPartyEntitiesList = CollateralHelper.getMandatoryEntitiesForCinForThirdParty();
			String mandatoryThirdPartyEntitiesStr = UIUtil.getDelimitedStringFromList(mandatoryThirdPartyEntitiesList, ",");
			
		  /*  List transactionHistoryList = customerDAO.getTransactionHistoryList(itrxValue.getTransactionID());

		    result.put("transactionHistoryList", transactionHistoryList);*/
		    
		//	result.put("orgList", getListAllSystemBankBranch(itrxValue.getStagingCollateral().getCollateralLocation()));
			
			/*result.put("valuationAgencyList",getValuationAgencyList(valuationProxy));*/
			
			result.put("valuationAgencyName_v1", valuationAgencyName_v1);
			result.put("cityName_v1",cityName_v1);
	    	result.put("stateName_v1",stateName_v1);
	    	result.put("regionName_v1",regionName_v1);
	    	result.put("countryName_v1",countryName_v1);
	    	result.put("preValDateList_v1",preValDateList_v1);
	    	result.put("preMortgageCreationList",preMortgageCreationList);
	    	
			/*result.put("cityName",cityName);
	    	result.put("stateName",stateName);
	    	result.put("regionName",regionName);
	    	result.put("countryName",countryName);*/
	    	
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
			
		/*	SecurityDaoImpl securityDaoImpl = new SecurityDaoImpl();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = securityDaoImpl.getSecurityMigreted("CMS_SECURITY", itrxValue.getCollateral().getCollateralID());
			
			if(status)
			{
				migratedFlag= "Y";
			}
			result.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		//	result.put("isEntryFmToDo", "");
		//	result.put("frame", frame);
		//	result.put("docCodeWithStocks",docCodeWithStocks);
		//	result.put("trxID", trxID);
		//	result.put("userName", userName);
			result.put("collateralID", idStrVal);
			result.put("from", map.get("from"));
			result.put("branchList", getBranchList());
			result.put("collateralList", collateralList);
			result.put("systemBankBranch", systemBankBranch);
			result.put("countryNme", countryNme);
			result.put("bankList",getBankList());
			result.put("bankListMap", getBankListHashMap());
			result.put("insuranceList", insuranceList);
		//	result.put("componentList", componentList);
		//	result.put("insuranceCoverageList", insuranceCoverageList);
		//	result.put("camStartDate", camStartDate); // Added By Dayananda Laishram : For CR Bill As colleteral validation on 05-May-2015 
			ICMSCustomer custOBNew = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICustomerProxy custProxy = CustomerProxyFactory.getProxy();
			/*if (custOBNew == null)
			{
			ICMSCustomer custOB = null;
			ICustomerDAO customerDAO1 = CustomerDAOFactory.getDAO();
			if(null != LegalID && LegalID != "")
			{
			List customerList = customerDAO1.searchCustomerByCIFNumber(LegalID);
			 custOB = (ICMSCustomer)  customerList.get(0);
			}
		        if (custOB != null) {
	                
	            	result.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, custOB);
	            	if(StringUtils.isBlank(custOB.getEntity()) || StringUtils.isBlank(custOB.getCustomerName())) {
	            		ICMSCustomer cust = custProxy.getCustomer(custOB.getCustomerID());
	            		result.put("customerEntity", cust == null ? "" : cust.getEntity());
	            		result.put("customerPartyName", cust == null ? "" : cust.getCustomerName());
	            	}
	            }
			}
			else if(StringUtils.isBlank(custOBNew.getEntity()) || StringUtils.isBlank(custOBNew.getCustomerName())) {
				ICMSCustomer cust = custProxy.getCustomer(custOBNew.getCustomerID());
				result.put("customerEntity", cust == null ? "" : cust.getEntity());
				result.put("customerPartyName", cust == null ? "" : cust.getCustomerName());
			}
			else {
				result.put("customerEntity", custOBNew.getEntity());
				result.put("customerPartyName", custOBNew.getCustomerName());
			}
			
			String collateralCode = itrxValue.getCollateral().getCollateralCode();
			ICollateralNewMasterDao collateralDao = CollateralNewMasterDAOFactory.getCollateralNewMasterDAO();
			String insuranceCheck = collateralDao.getInsuranceByCode(collateralCode);
			
			result.put("insuranceCheck", insuranceCheck); 
			
			
			if(itrxValue.getCollateral() instanceof OBCommercialGeneral){
				boolean checklistIsActive=true;
				
				ICustomerDAO customerDao =  CustomerDAOFactory.getDAO();
				List limitIdList;
				try {
					limitIdList = customerDao.getCollateralMappedCustomerLimitIdList(itrxValue.getCollateral().getCollateralID());
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				
				for(int i=0;i< limitIdList.size();i++){
					String limitId = (String) limitIdList.get(i);
					String checklistId = dao.getChecklistIdByLimitId("O",Long.parseLong(limitId));
					if(null!=checklistId){
					int otherChecklistCount = dao.getOtherChecklistCount("CHECKLIST",checklistId);
					if(otherChecklistCount>0){
						checklistIsActive=false;
						break;
					}
					}
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				result.put("checklistIsActive", checklistIsActive);
			}
			*/
			
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
		}
		
		private ICMSCustomer getCustomer(String sub_profile_id) {
	        ICMSCustomer custOB = null;
	        ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
	        try {
	            custOB = custproxy.getCustomer(Long.parseLong(sub_profile_id));
	            if (custOB != null) {
	                return custOB;
	            }
	        } catch (Exception e) {

	        }
	        return custOB;

	    }
		private List getListAllSystemBankBranch(String country) {
			List lbValList = new ArrayList();
			try {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					ISystemBankBranch[] branch = CollateralDAOFactory.getDAO().getListAllSystemBankBranch(country);
					
					if (branch != null) {
						for (int i = 0; i < branch.length; i++) {
							ISystemBankBranch lst = branch[i];
							String id = lst.getSystemBankBranchCode();
							String value = lst.getSystemBankBranchName();
							LabelValueBean lvBean = new LabelValueBean(value, id);
							lbValList.add(lvBean);
						}
					}
			}
			catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
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

		

		/**
		 * @return the forexFeedProxy
		 */
		public IForexFeedProxy getForexFeedProxy() {
			return (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
		}

		/**
		 * @param forexFeedProxy the forexFeedProxy to set
		 */
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
		
		//Add By Govind S:Get collateral code with desc,05/09/2011
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
