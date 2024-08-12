/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ProcessDetailsCustomerCommand.java,v 1.19 2006/11/08 07:57:02 jzhai Exp $
 */

package com.integrosys.cms.ui.customer;

// ---------------------------------/
// - Imported classes and packages -/
// ---------------------------------/

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.IContact;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.customer.bus.OBContact;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.partygroup.bus.IPartyGroup;
import com.integrosys.cms.app.partygroup.bus.IPartyGroupDao;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.aa.AAUIHelper;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.ui.relationshipmgr.IRelationshipMgr;
import com.integrosys.cms.app.customer.bus.ISystem;
/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/11/08 07:57:02 $ Tag: $Name: $
 */
public class ProcessDetailsCustomerCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ProcessDetailsCustomerCommand() {

	}
	private IOtherBankProxyManager otherBankProxyManager ;
	
	
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
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
						{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "fam", "java.lang.String", REQUEST_SCOPE }, { "famcode", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "sub_profile_id", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", FORM_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE }, { "fam", "java.lang.String", SERVICE_SCOPE },
				{ "famcode", "java.lang.String", SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "isMainBorrowerOnly", "java.lang.String", GLOBAL_SCOPE },
				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
				 { "partyGroupName", "java.lang.String", REQUEST_SCOPE },
				 { "cityName", "java.lang.String", REQUEST_SCOPE },		   	 
				 { "stateName", "java.lang.String", REQUEST_SCOPE },		   
				 { "regionName", "java.lang.String", REQUEST_SCOPE },		  	
				 { "countryName", "java.lang.String", REQUEST_SCOPE },			   		
				 { "rmRegionName", "java.lang.String", REQUEST_SCOPE },
				 { "relManagerName", "java.lang.String", REQUEST_SCOPE },
				 { "collCodeDescMap", "java.util.Map",REQUEST_SCOPE},
				 {"collCodeDescMap", "java.util.Map",REQUEST_SCOPE},
				 {"countryCodeNameMap", "java.util.Map",REQUEST_SCOPE},
				 {"sysBankBranchCodeNameMap", "java.util.Map",REQUEST_SCOPE},
				 {"viewSystemList","java.util.List",REQUEST_SCOPE }, //Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
				 { "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				 { "camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE},//Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
				 {"creditApprovalMap","java.util.Map",SERVICE_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String event = (String) map.get("event");
		HashMap result = new HashMap();
		HashMap lmtcolmap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
		List viewSystemList = new ArrayList();
		//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
		String transactionID = (String) map.get("transactionID");
		//String fam = new String();
		//String famcode = new String();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			result.put("event", event);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			DefaultLogger.debug(this, "after setting ob");

			ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			/*if (null == custOB) {
				throw new CommandProcessingException("ICMSCustomer is null in session!");
			}*/
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			if (null != custOB) {
			

			String subProfileID = (String) map.get("sub_profile_id");
			if ((subProfileID != null) && !subProfileID.equals(String.valueOf(custOB.getCustomerID()))) {
				// retrieve joint customer by subProfileID;
				custOB = custproxy.getCustomer(Long.parseLong(subProfileID));
			}
			result.put("customerOb", custOB);
			result.put("OBCMSCustomer", custOB);
			
			//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)

			ISystem  system[] =	custOB.getCMSLegalEntity().getOtherSystem();
	    	if (system != null) {
	            for (int i = 0; i < system.length; i++) {
	            	viewSystemList.add(system[i]);
	             }
	           
	        }
	    	 result.put("viewSystemList", viewSystemList);
	         DefaultLogger.debug(this, "viewSystemList size:"+viewSystemList.size());
	    	//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
			}
			

			//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
			AAUIHelper helper = new AAUIHelper();
			SBMIAAProxy proxy = helper.getSBMIAAProxy();
			result.put("creditApprovalMap", getCreditApproverList(proxy.getCreditApproverList()));
			//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
			
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (null != limitProfileOB) {
				long limitProfileID = limitProfileOB.getLimitProfileID();

				ILimitProfileTrxValue trxLimitProfile = limitProxy.getTrxLimitProfile(limitProfileID);
				
				//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
				MILimitUIHelper limitHelper = new MILimitUIHelper();
				SBMILmtProxy sbiproxy = limitHelper.getSBMILmtProxy();
				List lmtList = sbiproxy.getLimitSummaryListByAA(String.valueOf(limitProfileOB.getLimitProfileID()));
				LimitListSummaryItemBase objlmtListSummary=null;
				BigDecimal exchangeRate = new BigDecimal("1");
				BigDecimal  sancAmountVal = new BigDecimal("0");
				for(int i=0;i<lmtList.size();i++)
				{
					objlmtListSummary=new LimitListSummaryItemBase();
					objlmtListSummary=(LimitListSummaryItemBase)lmtList.get(i);
					
					if( !AbstractCommonMapper.isEmptyOrNull(objlmtListSummary.getCurrencyCode())){
						IForexFeedProxy frxPxy = (IForexFeedProxy)BeanHouse.get("forexFeedProxy");
						 exchangeRate = frxPxy.getExchangeRateWithINR(objlmtListSummary.getCurrencyCode().trim());
					}
					
					if(objlmtListSummary.getActualSecCoverage()!=null){
						 sancAmountVal = exchangeRate.multiply(new BigDecimal(objlmtListSummary.getActualSecCoverage().replaceAll(",", ""))).add(sancAmountVal);
					}
					
					if(objlmtListSummary.getIsAdhoc() !=null && objlmtListSummary.getIsAdhoc().equals("Y") && objlmtListSummary.getAdhocAmount() != null){
						 sancAmountVal = exchangeRate.multiply(new BigDecimal(objlmtListSummary.getAdhocAmount().replaceAll(",", ""))).add(sancAmountVal);
					}
				}
				
				trxLimitProfile.getLimitProfile().setTotalSanctionedAmountFacLevel(sancAmountVal.toString());
				
				//To get Cam Image Checklist Details
				try {
					
					HashMap camCheckListMap=new HashMap();
					ArrayList camChecklistArray=new ArrayList();
					camCheckListMap= CheckListDAOFactory.getCheckListDAO().getBulkCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
					camChecklistArray =(ArrayList) camCheckListMap.get("NORMAL_LIST");
					result.put("camChecklistArray", camChecklistArray);
				}
				catch (SearchDAOException e) {
					e.printStackTrace();
				} 
				ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
				List transactionHistoryList = customerDAO.getTransactionHistoryList(trxLimitProfile.getTransactionID());
				result.put("transactionHistoryList", transactionHistoryList);
				//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
				
				result.put("trxValue", trxLimitProfile);
				result.put("limitprofileOb", trxLimitProfile.getLimitProfile());
			}
			else{
				result.put("camChecklistArray", new ArrayList());
			}
			/*if (limitProfileOB == null) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}*/
			if (null != limitProfileOB) {
			ILimit[] limits = limitProfileOB.getLimits();
			try {
				limits = limitProxy.getFilteredNilColCheckListLimits(theOBTrxContext, limitProfileOB);
			}
			catch (LimitException ex) {
				throw new CommandProcessingException("Failed to filtered no collateral checklist for the limits ["
						+ limitProfileOB + "]");
			}

			lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);

			//Start 11th November 2014:By Sachin Patil ->for OMV value not captured on customer detail view 'Production issue'
			 Map rs = lmtcolmap;
					Set set = rs.keySet();
					Collection col = rs.values();
					OBCollateral obcol = new OBCollateral();
					if (set.size() != 0){
						ICollateral[] cols = (ICollateral[]) set.toArray(new ICollateral[0]);
						Iterator j = Arrays.asList(cols).iterator();
						 while (j.hasNext()) {
						        obcol = ((OBCollateral) j.next());
						        if(obcol instanceof OBCashFd)
						        {
						        	double lienAmount = CollateralDAOFactory.getDAO().getTotalLienAmountByCollID(String.valueOf(obcol.getCollateralID()));
						     Amount a = new Amount(lienAmount,obcol.getCurrencyCode());
						        	obcol.setCMV(a);
						        }
						 }
						
					} 
					//End 11th November 2014:By Sachin Patil
			
			Map sortedCollateralLimitMap = new TreeMap(new Comparator() {

				public int compare(Object thisObj, Object thatObj) {
					ICollateral thisCol = (ICollateral) thisObj;
					ICollateral thatCol = (ICollateral) thatObj;

					long thisValue = thisCol.getCollateralID();
					long thatValue = thatCol.getCollateralID();

					return (thisValue < thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
				}
			});
			sortedCollateralLimitMap.putAll(lmtcolmap);
			result.put("securityOb", sortedCollateralLimitMap);
			}
			/*
			if (custOB.getNonBorrowerInd()) {
				if (Long.toString(custOB.getCustomerID()) != null) {
					fam = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_NAME);
				}
				famcode = (String) limitProxy.getFAMNameByCustomer(custOB.getCustomerID()).get(ICMSConstant.FAM_CODE);
			}
			else {
				if (Long.toString(limitProfileOB.getLimitProfileID()) != null) {

					fam = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(ICMSConstant.FAM_NAME);
					famcode = (String) limitProxy.getFAMName(limitProfileOB.getLimitProfileID()).get(
							ICMSConstant.FAM_CODE);
				}
			}*/

			ArrayList mainBorrowerList = custproxy.getMBlistByCBleId(custOB.getCustomerID());
			String isMainBorrowerOnly = "N";
			if ((mainBorrowerList == null) || (mainBorrowerList.size() == 0)) {
				isMainBorrowerOnly = "Y";
			}
			result.put("isMainBorrowerOnly", isMainBorrowerOnly);


	/******************************************************/
	IContact[] contact = custOB.getCMSLegalEntity().getRegisteredAddress();
	String rmRegionId = custOB.getRmRegion();
	String relationshipMgrId = custOB.getRelationshipMgr();
	OBContact address = new OBContact();
	OBContact addressReg = new OBContact();
	OBContact addressCopy = new OBContact();
	if(contact != null)
	{
	for (int i = 0; contact.length > i; i++) {
		if (contact[i].getContactType().equals("CORPORATE")) {
			address = (OBContact) contact[i];
		}
		else if (contact[i].getContactType().equals("REGISTERED")) {
			addressReg = (OBContact) contact[i];
		}
		
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
	
	
	String cityName=null;
	String stateName=null;
	String regionName=null;
	String countryName=null;
	String rmRegionName = null;
	
	if(cityId!=null && !cityId.equals(""))
		cityName = getOtherBankProxyManager().getCityName(cityId);
	
	if(stateId!=null && !stateId.equals(""))
		stateName = getOtherBankProxyManager().getStateName(stateId);
	
	if(regionId!=null && !regionId.equals(""))
		regionName = getOtherBankProxyManager().getRegionName(regionId);
	
	if(countryId!=null && !countryId.equals(""))
		countryName = getOtherBankProxyManager().getCountryName(countryId);
	
	if(rmRegionId!=null && !rmRegionId.equals(""))
		rmRegionName = getOtherBankProxyManager().getRegionName(rmRegionId);
	if(rmRegionId!=null && !rmRegionId.equals(""))
		rmRegionName = getOtherBankProxyManager().getRegionName(rmRegionId);
	    	
	    	String partyId = (String)custOB.getPartyGroupName();
	    	IPartyGroupDao partyDao = (IPartyGroupDao)BeanHouse.get("partyGroupDao");
	    	
	        String partyGroupName = "";
	    	 if(partyId!=null && !partyId.equals(""))
		       {
	       IPartyGroup party =(IPartyGroup)	partyDao.getPartyGroupById(new Long (partyId));
	        partyGroupName = party.getPartyName();
		       }
	       IRelationshipMgrDAO relationshipmgr = (IRelationshipMgrDAO)BeanHouse.get("relationshipMgrDAO");
	       String relManagerName = "";
	       if(relationshipMgrId!=null && !relationshipMgrId.equals(""))
	       {
	    IRelationshipMgr relManager = (IRelationshipMgr)  relationshipmgr.getRelationshipMgrById(Long.parseLong(relationshipMgrId));
	    relManagerName = relManager.getRelationshipMgrName();
	       }

			 /*****************************************************/
	result.put("partyGroupName", partyGroupName); 
	result.put("cityName", cityName);
	result.put("stateName", stateName);	 
	result.put("regionName", regionName);	
	result.put("countryName", countryName); 	
	result.put("rmRegionName", rmRegionName);
	result.put("relManagerName", relManagerName);
	
	result.put("collCodeDescMap", getCollateralCodeList());
	result.put("collCodeDescMap", getCollateralCodeList());
	result.put("countryCodeNameMap", getCountryNameList());
	result.put("sysBankBranchCodeNameMap", getSysBankBranchNameList());
			
			//result.put("fam", fam);
			//result.put("famcode", famcode);
			result.put("transactionID", transactionID);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}
	
	

	//Add By Govind S:Get collateral code with desc,04/11/2011
	// TODO :Need to re check
	private Map getCollateralCodeList() {
		Map collCodeDescMap = null;
		List lbValList = new ArrayList();
		try {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				collCodeDescMap = new HashMap();
				List colCodeLst = CollateralDAOFactory.getDAO().getCollateralCodeDesc();
				if (colCodeLst != null) {
					
					for (int i = 0; i < colCodeLst.size(); i++) {
						String[] codeLst = (String[]) colCodeLst.get(i);
						String code = codeLst[0];
						String desc = codeLst[1];
						collCodeDescMap.put(code, desc);
					}
				
			}
		}
		catch (Exception ex) {
		}
		return collCodeDescMap;
	}
	
	
	// TODO :Need to re check
	private Map getCountryNameList() {
		Map countryIdNameMap = null;
		try {
			    countryIdNameMap = new HashMap();
				List countryList = CollateralDAOFactory.getDAO().getCountryNameList();
				if (countryList != null) {
					
					for (int i = 0; i < countryList.size(); i++) {
						String[] codeLst = (String[]) countryList.get(i);
						String code = codeLst[0];
						String countryName = codeLst[1];
						countryIdNameMap.put(code, countryName);
					}
				}
			}
			catch (Exception ex) {
			}
			return countryIdNameMap;
		}
	
	// TODO :Need to re check
	private Map getSysBankBranchNameList() {
		Map sysBankBranchCodeNameMap = null;
		try {
			sysBankBranchCodeNameMap = new HashMap();
				List sysBankBranchNameList = CollateralDAOFactory.getDAO().getSysBankBranchNameList();
				if (sysBankBranchNameList != null) {
					
					for (int i = 0; i < sysBankBranchNameList.size(); i++) {
						String[] codeLst = (String[]) sysBankBranchNameList.get(i);
						String code = codeLst[0];
						String sysBankBranchName = codeLst[1];
						sysBankBranchCodeNameMap.put(code, sysBankBranchName);
					}
				}
			}
			catch (Exception ex) {
			}
			return sysBankBranchCodeNameMap;
		}

	//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
	private Map<String,String> getCreditApproverList(List lst) {
		Map creditApproverMap = new HashMap<String,String>();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] creditApprover = (String[])lst.get(i);
				String val = creditApprover[0];
				String name = creditApprover[1];
				if(creditApprover[2].equals("Y")){
					name = name+ " (Senior)";
				}
				
				creditApproverMap.put(val, name);
		}
	} catch (Exception ex) {
	}
	return creditApproverMap;
}
	//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
}