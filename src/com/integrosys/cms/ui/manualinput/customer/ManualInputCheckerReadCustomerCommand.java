package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CO_BORROWER_DETAILS_KEY;
import static com.integrosys.cms.ui.manualinput.IManualInputConstants.SESSION_CUSTOMER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.customer.bus.IBankingMethod;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICoBorrowerDetails;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICriFac;
import com.integrosys.cms.app.customer.bus.IDirector;
import com.integrosys.cms.app.customer.bus.ISubline;
import com.integrosys.cms.app.customer.bus.ISystem;
import com.integrosys.cms.app.customer.bus.IVendor;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.manualinput.party.IIfscCodeDao;
import com.integrosys.cms.app.otherbank.bus.IOtherBankDAO;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupJdbc;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.cms.app.customer.bus.CustomerDAO;


	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 28-03-2011
	 *
	 */

public class ManualInputCheckerReadCustomerCommand extends AbstractCommand{
	
	public ManualInputCheckerReadCustomerCommand() {
	}
	private IOtherBankProxyManager otherBankProxyManager ;
	
	private IPartyGroupProxyManager partyGroupProxy;
	
	
	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return (IOtherBankProxyManager)BeanHouse.get("otherBankProxyManager");
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
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
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
		
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
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "viewOtherSystemList", "java.util.List", SERVICE_SCOPE },
				{ "vendorList", "java.util.List", SERVICE_SCOPE },
				{ "viewOtherBankList", "java.util.List", SERVICE_SCOPE },
				{ "viewPartyGrpList", "java.util.List", SERVICE_SCOPE },
				{ "viewDirectorList", "java.util.List", SERVICE_SCOPE },
				 { "partyGroupName", "java.lang.String", SERVICE_SCOPE },
				 { "otherBankList", "java.util.List", SERVICE_SCOPE },
				{ "facList", "java.util.List", SERVICE_SCOPE },		
		     	 { "cityName", "java.lang.String", SERVICE_SCOPE },		   	 
				 { "stateName", "java.lang.String", SERVICE_SCOPE },	
					{ "currencyList", "java.util.List", SERVICE_SCOPE },
				 { "regionName", "java.lang.String", SERVICE_SCOPE },		  	
				 { "countryName", "java.lang.String", SERVICE_SCOPE },			   		
				 { "cityRegName", "java.lang.String", SERVICE_SCOPE },		  
				 { "stateRegName", "java.lang.String", SERVICE_SCOPE },		   	
				 { "regionRegName", "java.lang.String", SERVICE_SCOPE }, 	
				 { "countryRegName", "java.lang.String", SERVICE_SCOPE },
				 { "rmRegionName", "java.lang.String", SERVICE_SCOPE },
				 { "relManagerName", "java.lang.String", SERVICE_SCOPE },
				 { "partyGroupList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "facNameList", "java.util.List", SERVICE_SCOPE },
					{ "countryList", "java.util.List", SERVICE_SCOPE },
					{ "securityNameList", "java.util.List", SERVICE_SCOPE },
				{"ICMSCustomerTrxValue", "com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue", SERVICE_SCOPE},
				{ "partyNamePanMap", "java.lang.String", SERVICE_SCOPE }, //Valid rating CR
				{ "ifscBranchList", "java.util.List", SERVICE_SCOPE },
				{ SESSION_CO_BORROWER_DETAILS_KEY, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_CUSTOMER, ICMSCustomer.class.getName(), SERVICE_SCOPE },
				
				/* { IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
					"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },*/
				{ "scfStatus", "java.lang.String", SERVICE_SCOPE }, { "scfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "scfFlag", "java.lang.String", SERVICE_SCOPE }, { "ecbfStatus", "java.lang.String", SERVICE_SCOPE },
				{ "ecbfErrMsg", "java.lang.String", SERVICE_SCOPE },
				{ "ecbfFlag", "java.lang.String", SERVICE_SCOPE }
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
			String trxId=(String) map.get("trxId");
			List viewOtherSystemList = new ArrayList();
			List viewOtherBankList = new ArrayList();
			List viewPartyGrpList = new ArrayList();
			List viewDirectorList = new ArrayList();
			List vendorList = new ArrayList();
			List viewFacList = new ArrayList();
			// function to get system bank Trx value
			trxValue = (OBCMSCustomerTrxValue) CustomerProxyFactory.getProxy().getTrxCustomer(trxId) ;
			customer = (OBCMSCustomer) trxValue.getStagingCustomer();
			ISystem  system[] =	customer.getCMSLegalEntity().getOtherSystem();
	    	IBankingMethod bankingMethod[] =	customer.getCMSLegalEntity().getBankList();
	    	ISubline subline[] = customer.getCMSLegalEntity().getSublineParty();
	    	IDirector director[] = customer.getCMSLegalEntity().getDirector();
	    	IVendor vendor[] = customer.getCMSLegalEntity().getVendor();
	    	ICriFac criFac[] = customer.getCMSLegalEntity().getCriFacList();
	    	String rmRegionId = customer.getRmRegion();
	    	String relationshipMgrId = customer.getRelationshipMgr();
	    	
	    	IContact[] contact = customer.getCMSLegalEntity()
			.getRegisteredAddress();

	OBContact address = new OBContact();
	OBContact addressReg = new OBContact();
	OBContact addressCopy = new OBContact();
	for (int i = 0; contact.length > i; i++) {
		if (contact[i].getContactType().equals("CORPORATE")) {
			address = (OBContact) contact[i];
		}
		else if (contact[i].getContactType().equals("REGISTERED")) {
			addressReg = (OBContact) contact[i];
		}
		
	}
	 
	String cityId = address.getCity();
	String countryId = address.getCountryCode();
	String stateId = address.getState();
	String regionId = address.getRegion();
	String cityRegId = addressReg.getCity();
	String countryRegId = addressReg.getCountryCode();
	String stateRegId = addressReg.getState();
	String regionRegId = addressReg.getRegion();
	partycustID = customer.getCifId();
	
	String cityName=null;
	String stateName=null;
	String regionName=null;
	String countryName=null;
	String cityRegName=null;
	String stateRegName=null;
	String regionRegName=null;
	String countryRegName=null;
	String rmRegionName = null;
	
	if(cityId!=null && !cityId.equals(""))
		cityName = getOtherBankProxyManager().getCityName(cityId);
	
	if(stateId!=null && !stateId.equals(""))
		stateName = getOtherBankProxyManager().getStateName(stateId);
	
	if(regionId!=null && !regionId.equals(""))
		regionName = getOtherBankProxyManager().getRegionName(regionId);
	
	if(countryId!=null && !countryId.equals(""))
		countryName = getOtherBankProxyManager().getCountryName(countryId);
	
	if(cityRegId!=null && !cityRegId.equals(""))
		cityRegName = getOtherBankProxyManager().getCityName(cityRegId);
	
	if(stateRegId!=null && !stateRegId.equals(""))
		stateRegName = getOtherBankProxyManager().getStateName(stateRegId);
	
	if(regionRegId!=null && !regionRegId.equals(""))
		regionRegName = getOtherBankProxyManager().getRegionName(regionRegId);
	
	if(countryRegId!=null && !countryRegId.equals(""))
		countryRegName = getOtherBankProxyManager().getCountryName(countryRegId);
	
	if(rmRegionId!=null && !rmRegionId.equals(""))
//		rmRegionName = getOtherBankProxyManager().getRegionName(rmRegionId);
		rmRegionName = rmRegionId;
	    	
	    	String partyId = (String)customer.getPartyGroupName();
	    	IPartyGroupDao partyDao = (IPartyGroupDao)BeanHouse.get("partyGroupDao");
	    	String partyGroupName= "";
	    	 if(partyId!=null && !partyId.equals(""))
		       {
	       IPartyGroup party =(IPartyGroup)	partyDao.getPartyGroupById(new Long (partyId));
	        partyGroupName = party.getPartyName();
		       }
	       IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
	       String relManagerName = "";
	       if(relationshipMgrId!=null && !relationshipMgrId.equals(""))
	       {
//	    IRelationshipMgr relManager = (IRelationshipMgr)  relationshipmgr.getRelationshipMgrById(Long.parseLong(relationshipMgrId));
//	    relManagerName = relManager.getRelationshipMgrName();
	    	   relManagerName = relationshipMgrId;
	       }
	       
	       
	       //Shiv
	       IPartyGroupJdbc partyJdbc = (IPartyGroupJdbc)BeanHouse.get("partyGroupJdbc");
	    		ArrayList PartyGroupList = (ArrayList) partyJdbc.getAllPartyGroup();
	    		resultMap.put("partyGroupList", PartyGroupList);
	       
	       resultMap.put("partyGroupName", partyGroupName);  
	       resultMap.put("cityName", cityName);
	   	resultMap.put("stateName", stateName);	 
	   	resultMap.put("regionName", regionName);	
	   		resultMap.put("countryName", countryName); 	
	   	resultMap.put("cityRegName", cityRegName);   	
	   	resultMap.put("stateRegName", stateRegName); 	 
	   	resultMap.put("regionRegName", regionRegName);
	   	resultMap.put("countryRegName", countryRegName);
	   	resultMap.put("currencyList", getCurrencyList());
	   	resultMap.put("rmRegionName", rmRegionName);	
	 	resultMap.put("relManagerName", relManagerName);	
	 	
		
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
	 	

	   	
		//Start:Uma:Valid RAting CR
//		String event  = (String)map.get("event");
//		String partyNamePanMap="";
//		
//		if("create_checker_process_partymaker".equals(event) || "maker_update_customer_partymaker".equals(event)){
//		String cifId=customer.getCifId();
//		String pan = customer.getPan();
//		
//		if(null!=cifId && !"".equals(cifId) && null!=pan && !"".equals(pan)){
//			CustomerDAO customerDao = new CustomerDAO();
//			List<String> partyNamePanList=new ArrayList<String>();
//			partyNamePanList=customerDao.getPanDetails(cifId,pan);
//			
//			if(null!=partyNamePanList && partyNamePanList.size() >0){
//				for(int i=0; i<partyNamePanList.size() ; i++){
//					if(i != (partyNamePanList.size()-1 )){
//						partyNamePanMap=partyNamePanMap+partyNamePanList.get(i)+ ",";
//					}
//					else{
//						partyNamePanMap=partyNamePanMap+partyNamePanList.get(i);	
//					}
//					}
//				}
//		}
//		}
//
//		DefaultLogger.debug(this,"partyNamePanMap:"+ partyNamePanMap);
//		resultMap.put("partyNamePanMap", partyNamePanMap);
		//End:Uma:Valid Rating CR
		
	       
    	if (system != null) {
            for (int i = 0; i < system.length; i++) {
            	viewOtherSystemList.add(system[i]);
               
            }
        }
    	
    	if (director != null) {
            for (int i = 0; i < director.length; i++) {
            	viewDirectorList.add(director[i]);
               
            }
        }
    	
    	if (vendor != null) {
            for (int i = 0; i < vendor.length; i++) {
            	vendorList.add(vendor[i]);
               
            }
        }
    	
    	
    	if (bankingMethod != null) {
            for (int i = 0; i < bankingMethod.length; i++) {
            	viewOtherBankList.add(bankingMethod[i]);
               
            }
        }
    	
    	
    	if (subline != null) {
            for (int i = 0; i < subline.length; i++) {
            	viewPartyGrpList.add(subline[i]);
               
            }
        }
    	
    	if (criFac != null) {
            for (int i = 0; i < criFac.length; i++) {
            	viewFacList.add(criFac[i]);
               
            }
        }
    		resultMap.put("otherBankList", getOtherBankList());
			resultMap.put("ICMSCustomerTrxValue", trxValue);
			resultMap.put("OBCMSCustomer", customer);
			resultMap.put("viewOtherSystemList", viewOtherSystemList);
			resultMap.put("viewOtherBankList", viewOtherBankList);
			resultMap.put("viewPartyGrpList", viewPartyGrpList);
			resultMap.put("viewDirectorList", viewDirectorList);
			resultMap.put("vendorList", vendorList);
			resultMap.put("facList", viewFacList);
			//resultMap.put(IGlobalConstant.GLOBAL_CUSTOMER_OBJ, customer);
			if(map.get(SESSION_CO_BORROWER_DETAILS_KEY)==null) {
				if(customer.getCMSLegalEntity()!=null && customer.getCMSLegalEntity().getCoBorrowerDetails()!=null) {
					resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, customer.getCMSLegalEntity().getCoBorrowerDetails());
				}else {
					resultMap.put(SESSION_CO_BORROWER_DETAILS_KEY, new ArrayList<ICoBorrowerDetails>());
				}
			}
			//get IFSC CODE list
			SearchResult ifscSearchList= new SearchResult();
			IIfscCodeDao ifscCodeDao = (IIfscCodeDao) BeanHouse.get("ifscCodeDao");
			ifscSearchList = ifscCodeDao.getStageIfscCodeList(trxValue.getStagingReferenceID());
			ArrayList ifscCodeList=(ArrayList) ifscSearchList.getResultList();
			resultMap.put("ifscBranchList", ifscCodeList);
			
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
		CustomerDAO customerDao = new CustomerDAO();
		// list(query)-status, errMsg, flag
		List scfStatusList = null;
		if(partycustID!=null) {
			scfFlag = customerDao.getScfFlagUsingMainProfileId(partycustID);
			System.out.println("scfFlag----------------"+scfFlag);
			scfStatusList = customerDao.getScfAndEcbfStatusById(ICMSConstant.SCF, ICMSConstant.CUSTOMER, partycustID);
		}
		if (scfStatusList != null && !scfStatusList.isEmpty()) {

			String[] scfStatusListValues = (String[]) scfStatusList.get(0);
			
			/*scfFlag = scfStatusListValues[2];*/
			if (scfFlag != null) {
				scfStatus = scfStatusListValues[0];
				if (scfFlag.equalsIgnoreCase("Yes")) { //changed to "Yes"
					if (scfStatus.equalsIgnoreCase("Success")) {
					} else if (scfStatus.equalsIgnoreCase("Fail")) {
						scfErrMsg = (String) scfStatusListValues[1];
					} else if (scfStatus.equalsIgnoreCase("Pending")) {
						// scfStatus = "Pending";
					}
					else if (scfStatus.equalsIgnoreCase("Error")) {
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
				if (ecbfFlag.equalsIgnoreCase("Yes")) {
					if (ecbfStatus.equalsIgnoreCase("S")) {
						ecbfStatus = "Success";
					} else if (ecbfStatus.equalsIgnoreCase("F")) {
						ecbfStatus = "Failed";
						ecbfErrMsg = (String) ecbfStatusListValues[1];
					} else if (ecbfStatus.equalsIgnoreCase("Pending")) {
						// ecbfStatus = "Pending";
					} else if (ecbfStatus.equalsIgnoreCase("Error")) {
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
	
	
	private List getCurrencyList() {
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				IForexFeedEntry[] currency = CollateralDAOFactory.getDAO().getCurrencyList();
				
				if (currency != null) {
					for (int i = 0; i < currency.length; i++) {
						IForexFeedEntry lst = currency[i];
//						String id = lst.getCurrencyIsoCode().trim();
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
}
