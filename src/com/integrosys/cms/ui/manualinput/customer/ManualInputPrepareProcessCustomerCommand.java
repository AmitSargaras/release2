package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICriFac;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.bus.OBCriFac;
import com.integrosys.cms.app.customer.bus.OBDirector;
import com.integrosys.cms.app.customer.bus.OBSubline;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.customer.bus.OBVendor;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.lei.json.dao.ILEIValidationLogDao;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.manualinput.party.bus.IPanValidationLogDao;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.rbicategory.bus.OBIndustryCodeCategory;
import com.integrosys.cms.app.rbicategory.bus.OBRbiCategory;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.OBCommonUser;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 28-03-2011
	 *
	 */

public class ManualInputPrepareProcessCustomerCommand extends AbstractCommand{
	
	private ICityProxyManager cityProxy;
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	private IPartyGroupProxyManager partyGroupProxy;

	private IValuationAgencyProxyManager valuationAgencyProxy;

	private IOtherBankProxyManager otherBankProxyManager;
	
	private IRbiCategoryProxyManager rbiCategoryProxy;
	
	
	
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public ManualInputPrepareProcessCustomerCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "trxId", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "coBorrowerList", List.class.getName(), SERVICE_SCOPE},
				{ "OBCMSCustomer", ICMSCustomer.class.getName(), SERVICE_SCOPE },
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
				
				{IGlobalConstant.GLOBAL_LOS_USER, "com.integrosys.component.user.app.bus.ICommonUser",GLOBAL_SCOPE},
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
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "partyGroupList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "directorList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "vendorList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "facList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "relationshipMgrList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "cityList", "java.util.List", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "validate", "java.lang.String", SERVICE_SCOPE },
				{ "systemBranchList", "java.util.List", SERVICE_SCOPE },
				//{ "branchObj", "com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch",SERVICE_SCOPE },
				{ "otherBankList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "rbiIndustryCodeList", "java.util.List", SERVICE_SCOPE },
				{ "currencyList", "java.util.List", SERVICE_SCOPE },
				{ "partyGrpList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "systemList", "java.util.List", SERVICE_SCOPE },
				{ "branchList", "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "classActivityList", "java.util.List", SERVICE_SCOPE },
				{ "rmRegionList", "java.util.List", SERVICE_SCOPE },
				{ "OBCMSCustomerNew","com.integrosys.cms.app.customer.bus.ICMSCustomer",SERVICE_SCOPE },
				{ "lastValidatedDate","java.util.Date",SERVICE_SCOPE },
				{ "lastValidatedLeiDate","java.util.Date",SERVICE_SCOPE },
				{ "facNameList","java.util.Date",SERVICE_SCOPE },
				{ "securityNameList","java.util.Date",SERVICE_SCOPE },
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{ "partyNamePanMap", "java.util.HashMap", SERVICE_SCOPE }, //Valid rating CR 
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
				{ "camTypeForParty", "java.lang.String", SERVICE_SCOPE },
				{ "bankMethodListNew1", "java.util.List", SERVICE_SCOPE },
				{ "bankMethodListNew2", "java.util.List", SERVICE_SCOPE },
				{ "scfStatus", "java.lang.String", SERVICE_SCOPE }, { "scfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "scfFlag", "java.lang.String", SERVICE_SCOPE }, { "ecbfStatus", "java.lang.String", SERVICE_SCOPE },
				{ "ecbfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "ecbfFlag", "java.lang.String", SERVICE_SCOPE },
		});
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,CustomerException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		
		String partycustID = "";
		String scfErrMsg = "";
		String scfStatus = "";
		String scfFlag = "";
		String ecbfErrMsg = "";
		String ecbfStatus = "";
		String ecbfFlag = "";
		try {
			ICMSCustomer customer;
			ICMSCustomerTrxValue trxValue=null;
			Date lastValidatedDate = null;
			Date lastValidatedLeiDate = null;
			String trxId=(String) map.get("trxId");
			CustomerDAO customerDao = new CustomerDAO();
			
			
		
			// function to get system bank Trx value
			String cityId = "";
			String countryId = "";
			String stateId = "";
			String regionId = "";
			String industryName = "";
			String partyId = "";
			String rmRegion = "";
			long country = 0;
			String validate = "";
			
			try {
			trxValue = (OBCMSCustomerTrxValue) CustomerProxyFactory.getProxy().getTrxCustomer(trxId) ;
			customer = (OBCMSCustomer) trxValue.getStagingCustomer();
			
			IContact[] contact = customer.getCMSLegalEntity()
			.getRegisteredAddress();
	OBContact address = new OBContact();
	for (int i = 0; contact.length > i; i++) {
		if (contact[i].getContactType().equals("CORPORATE")) {
			address = (OBContact) contact[i];
		}
	}

	cityId = address.getCity();
	countryId = address.getCountryCode();
	stateId = address.getState();
	regionId = address.getRegion();
	partyId = customer.getPartyGroupName();
	rmRegion = customer.getRmRegion();
	industryName = customer.getIndustryName();
	partycustID = customer.getCifId();
} catch (CustomerException e) {
	CommandProcessingException cpe = new CommandProcessingException(e
			.getMessage());
	cpe.initCause(e);
	throw cpe;
} 
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
 List branchList= new ArrayList();
 List idList = (List) searchResult.getResultList();
 ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();

/*for (int i = 0; i < idList.size(); i++) {
	ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
	if(branchCode.equals(bankBranch.getSystemBankBranchCode())){
		
		resultMap.put("branchObj", bankBranch);
		break;
	}
}*/
if (!(rmRegion == null || rmRegion.equals(""))) {
	resultMap.put("relationshipMgrList",
			getRelationshipMgrList(rmRegion));
} else {
	resultMap.put("relationshipMgrList", getList(country));
}

if (!(stateId == null) && !(stateId.equals(""))) {
	resultMap.put("cityList", getCityList(Long.parseLong(stateId)));
} else {
	resultMap.put("cityList", getList(country));
}
resultMap.put("validate",validate);
if (!(regionId == null) && !(regionId.equals(""))) {
	resultMap.put("stateList", getStateList(Long.parseLong(regionId)));
} else {
	resultMap.put("stateList", getList(country));
}
if (!(industryName == null) && !(industryName.equals(""))) {
	resultMap.put("rbiIndustryCodeList", getRbiIndustryCodeList(industryName));
} else {
	resultMap.put("rbiIndustryCodeList", getList(country));
}

if (!(countryId == null) && !(countryId.equals(""))) {
	resultMap.put("regionList", getRegionList(Long.parseLong(countryId
			.trim())));
} else {
	resultMap.put("regionList", getList(country));
}

try {
	ArrayList PartyGroupList = (ArrayList) getPartyGroupProxy()
			.getAllActual();
	resultMap.put("partyGroupList", PartyGroupList);
} catch (PartyGroupException e) {
	e.printStackTrace();
} catch (TrxParameterException e) {
	e.printStackTrace();
} catch (TransactionException e) {
	e.printStackTrace();
}

ISystem system[] = customer.getCMSLegalEntity().getOtherSystem();
List list = new ArrayList();
if (system != null) {
	for (int i = 0; i < system.length; i++) {
		ISystem sysObj = new OBSystem();
		sysObj = (ISystem) system[i];
		list.add(sysObj);

	}
}
IDirector director[] = customer.getCMSLegalEntity().getDirector();
List dir = new ArrayList();
if (director != null) {
	for (int i = 0; i < director.length; i++) {
		IDirector sysObj = new OBDirector();
		sysObj = (IDirector) director[i];
		dir.add(sysObj);

	}
}

	
IVendor vendor[] = customer.getCMSLegalEntity().getVendor();
List name = new ArrayList();
if (vendor != null) {
	for (int i = 0; i < vendor.length; i++) {
		IVendor sysObj = new OBVendor();
		sysObj = (IVendor) vendor[i];
		name.add(sysObj);
	}
}


ISubline subline[] = customer.getCMSLegalEntity().getSublineParty();
List sublineList = new ArrayList();
if (subline != null) {
	for (int i = 0; i < subline.length; i++) {
		ISubline sysObj = new OBSubline();
		sysObj = (ISubline) subline[i];
		sublineList.add(sysObj);

	}
}
IBankingMethod banking[] = customer.getCMSLegalEntity().getBankList();
List bankList = new ArrayList();
if (banking != null) {
	for (int i = 0; i < banking.length; i++) {
		IBankingMethod sysObj = new OBBankingMethod();
		sysObj = (IBankingMethod) banking[i];
		bankList.add(sysObj);

	}
}

ICriFac criFac[] = customer.getCMSLegalEntity().getCriFacList();
List criFacList = new ArrayList();
if (criFac != null) {
	for (int i = 0; i < criFac.length; i++) {
		ICriFac criObj = new OBCriFac();
		criObj = (ICriFac) criFac[i];
		criFacList.add(criObj);

	}
}

			// Start:Uma:Valid RAting CR
/*			String event = (String) map.get("event");
			Map<String, String> partyNamePanMap = new HashMap<String, String>();

			if ("process_page_partymaker".equals(event) || "maker_save_process_partymaker".equals(event)) {
				String cifId = customer.getCifId();

				if (null != cifId && !"".equals(cifId)){
					CustomerDAO customerDao = new CustomerDAO();
					partyNamePanMap = customerDao.getPanDetails(cifId);
				}

			}

			DefaultLogger.debug(this, "partyNamePanMap size"+ partyNamePanMap.size());
			resultMap.put("partyNamePanMap", partyNamePanMap);*/
			// End:Uma:Valid Rating CR

if(null == map.get(SESSION_CO_BORROWER_DETAILS_KEY)) {
	if(null != customer.getCMSLegalEntity() &&  null != customer.getCMSLegalEntity().getCoBorrowerDetails()) {
		resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, customer.getCMSLegalEntity().getCoBorrowerDetails());
	}else {
		resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, new ArrayList<ICoBorrowerDetails>());
	}
}
try {
IPanValidationLogDao panValidationLogDao = (IPanValidationLogDao)BeanHouse.get("panValidationLogDao");
lastValidatedDate = panValidationLogDao.fetchLastValidatedDate("actualPANValidationLog", customer.getPan(), customer.getCifId());
}catch(Exception ee) {
	System.out.println("PAN DATE  ERROR:: "+ee.getMessage());
}

try {
ILEIValidationLogDao leiLogDao = (ILEIValidationLogDao) BeanHouse.get("logLeiDao");
lastValidatedLeiDate = leiLogDao.fetchLastValidatedDate("actualLEIValidationLog", customer.getLeiCode(),customer.getCifId());
}catch(Exception ee) {
	System.out.println("LEI DATE  ERROR:: "+ee.getMessage());
}

List transactionHistoryList = customerDAO.getTransactionHistoryList(trxValue.getTransactionID());

String camTypeForParty = customerDao.getCamtypeForParty(customer.getCustomerName());
resultMap.put("camTypeForParty", camTypeForParty);
resultMap.put("systemBranchList", idList);
resultMap.put("classActivityList", getClassActivityList());
resultMap.put("countryList", getCountryList(country));
resultMap.put("systemList", list);
resultMap.put("branchList", bankList);
resultMap.put("directorList", dir);
resultMap.put("vendorList", name);
resultMap.put("facList", criFacList);
resultMap.put("partyGrpList", sublineList);
resultMap.put("currencyList", getCurrencyList());
resultMap.put("otherBankList", getOtherBankList());
resultMap.put("bankMethodListNew1", getBankingMethodList());
resultMap.put("bankMethodListNew2",new ArrayList());
//get IFSC CODE list
SearchResult ifscSearchList= new SearchResult();
try {
	IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
	ifscSearchList = ifscCodeDao.getStageIfscCodeList(trxValue.getStagingReferenceID());
	ArrayList ifscCodeList=(ArrayList) ifscSearchList.getResultList();
	if(!(null == ifscSearchList.getResultList()))
	resultMap.put("ifscBranchList", ifscCodeList);
}catch(Exception ee) {
		resultMap.put("ifscBranchList", "");
		System.out.println("IFSC CODE ERROR:: "+ee.getMessage());
		ee.printStackTrace();
	}

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

resultMap.put("facNameList", facNameList);
resultMap.put("countryList", getCountryList());
resultMap.put("securityNameList",securityNameList);




resultMap.put("rmRegionList", getRegionList());




resultMap.put("facNameList", facNameList);
resultMap.put("countryList", getCountryList());
resultMap.put("securityNameList",securityNameList);
		
	
    	
resultMap.put("transactionHistoryList", transactionHistoryList);
			resultMap.put("ICMSCustomerTrxValue", trxValue);
			resultMap.put("trxId", trxId);
			resultMap.put("OBCMSCustomer", customer);
			resultMap.put("OBCMSCustomerNew", customer);
			resultMap.put("lastValidatedDate", lastValidatedDate);
			resultMap.put("lastValidatedLeiDate", lastValidatedLeiDate);
			
		} catch (CustomerException e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

		
		
		
		
		
		
		

		/* PARTY scf ecbf starts */
//		ApprovalMatrixDaoImpl newDao= new  ApprovalMatrixDaoImpl();
		CustomerDAO customerDao = new CustomerDAO();
		// list(query)-status, errMsg, flag
		scfFlag = customerDao.getScfFlagUsingMainProfileId(partycustID);
		System.out.println("scfFlag----------------"+scfFlag);
		List scfStatusList = customerDao.getScfAndEcbfStatusById(ICMSConstant.SCF, ICMSConstant.CUSTOMER, partycustID);
		if (scfStatusList != null && !scfStatusList.isEmpty()) {

			String[] scfStatusListValues = (String[]) scfStatusList.get(0);
			
			/*scfFlag = scfStatusListValues[2];*/
			if (scfFlag != null) {
				scfStatus = scfStatusListValues[0];
				if (scfFlag.equalsIgnoreCase("Yes")) {
					if (scfStatus.equalsIgnoreCase("Success")) {
					} else if (scfStatus.equalsIgnoreCase("Fail")) {
						scfErrMsg = (String) scfStatusListValues[1];
					} else if (scfStatus.equalsIgnoreCase("Pending")) {
						// scfStatus = "Pending";
					}else if (scfStatus.equalsIgnoreCase("Error")) {
						scfErrMsg = (String) scfStatusListValues[1];
					}
				} else if (scfFlag.equalsIgnoreCase("No")) { // scf from Yes->No , be any status - F/S //
															// (!scfStatus.isEmpty())
					scfStatus = "Stopped";
				}
			}
		} else if (scfStatusList == null || scfStatusList.isEmpty())  { // non - scm, where scfStatusList.isEmpty()
			scfStatus = "NA";
		}

		resultMap.put("scfStatus", scfStatus);
		resultMap.put("scfErrMsg", scfErrMsg);


		// list(query)-status, errMsg
		List ecbfStatusList = null;
		if (partycustID != null) {
			ecbfFlag = customerDao.getEcbfFlagUsingMainProfileId(partycustID);
			ecbfStatusList = customerDao.getScfAndEcbfStatusById(ICMSConstant.ECBF, ICMSConstant.CUSTOMER, partycustID);
		}
		if (ecbfStatusList != null && !ecbfStatusList.isEmpty()) {

			String[] ecbfStatusListValues = (String[]) ecbfStatusList.get(0);
			ecbfStatus = ecbfStatusListValues[0];

			if(null != ecbfStatus){
			if (null != ecbfFlag) {
				ecbfStatus = ecbfStatusListValues[0];
				if (ecbfFlag.equalsIgnoreCase("Yes")) {
					if (ecbfStatus.equalsIgnoreCase("S")) {
						ecbfStatus = "Success";
					} else if (ecbfStatus.equalsIgnoreCase("F")) {
						ecbfStatus = "Failed";
						ecbfErrMsg = (String) ecbfStatusListValues[1];
					} else if (ecbfStatus.equalsIgnoreCase("Pending")) {
						// ecbfStatus = "Pending";
					}else if (ecbfStatus.equalsIgnoreCase("Error")) {
						ecbfErrMsg = (String) ecbfStatusListValues[1];
					}
				} else if (ecbfFlag.equalsIgnoreCase("No")) { // ecbf from Yes->No , be any status - F/S //
																// (!ecbfStatus.isEmpty())
					ecbfStatus = "Stopped";
				}
			}
			}
		} else if (ecbfStatusList == null || ecbfStatusList.isEmpty()) { // non - ecbf, where ecbfStatusList.isEmpty()
			ecbfStatus = "NA";
		}

		resultMap.put("ecbfStatus", ecbfStatus);
		resultMap.put("ecbfErrMsg", ecbfErrMsg);
		

		/* PARTY scf ecbf ends */
		
		
		
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
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

	private List getStateList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getStateList(stateId);
			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				if (state.getStatus().equals("ACTIVE")) {
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

	private List getRegionList(long regionId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getRegionList(regionId);

			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				if (region.getStatus().equals("ACTIVE")) {
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

	private List getCityList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCityList(stateId);

			for (int i = 0; i < idList.size(); i++) {
				ICity city = (ICity) idList.get(i);
				if (city.getStatus().equals("ACTIVE")) {
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

	private List getRelationshipMgrList(String rmRegion) {
		List lbValList = new ArrayList();
		List idList = new ArrayList();
		try {
			SearchResult idListsr = (SearchResult) getRelationshipMgrProxyManager()
					.getRelationshipMgrList(rmRegion);

			if (idListsr != null) {
				idList = new ArrayList(idListsr.getResultList());
			}

			for (int i = 0; i < idList.size(); i++) {
				IRelationshipMgr mgr = (IRelationshipMgr) idList.get(i);
				if (mgr.getStatus().equals("ACTIVE")) {
					String id = Long.toString(mgr.getId());
					String val = mgr.getRelationshipMgrName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	
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
			}
			return CommonUtil.sortDropdown(lbValList);
		}

	private List getList(long stateId) {
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
	private List getRbiIndustryCodeList(String indName) {
		List lbValList = new ArrayList();
		List rbiIndustryCodeList =new ArrayList();
		 HashMap rBICateCodeHashMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.HDFC_RBI_CODE);
		OBRbiCategory oBRbiCategory = new OBRbiCategory();
		try {
			List idList = (List) getRbiCategoryProxy().getRbiIndCodeByNameList(indName);
			oBRbiCategory = (OBRbiCategory)idList.get(0);
			Set rbiC = oBRbiCategory.getStageIndustryNameSet();
			Iterator itSet = rbiC.iterator();
			OBIndustryCodeCategory oBIndustryCodeCategory = new OBIndustryCodeCategory();
			int count = 0;
			while( itSet.hasNext() ){
				oBIndustryCodeCategory = (OBIndustryCodeCategory)itSet.next();
				
				//Uma :Rbi Category Master issue- Null Pointer Exception
				if(null != rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId())){
				String id = oBIndustryCodeCategory.getRbiCodeCategoryId();
				String value =oBIndustryCodeCategory.getRbiCodeCategoryId()+"-"+rBICateCodeHashMap.get(oBIndustryCodeCategory.getRbiCodeCategoryId()).toString();
				LabelValueBean lvBean = new LabelValueBean(value, id);
				lbValList.add(lvBean);
				}
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
		
}