/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ViewSecuritiesCustomerCommand.java,v 1.23 2005/11/11 16:18:10 lyng Exp $
 */

package com.integrosys.cms.ui.customer;

// ---------------------------------/
// - Imported classes and packages -/
// ---------------------------------/

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
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashfd.OBCashFd;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.bus.ICustomerSearchResult;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2005/11/11 16:18:10 $ Tag: $Name: $
 */
public class ViewSecuritiesCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ViewSecuritiesCustomerCommand() {

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
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE }, { "from", "java.lang.String", REQUEST_SCOPE },
				{"activeDeleteFlag","java.lang.String", REQUEST_SCOPE}}); //Added by Uma Khot: Phase 3 CR:Search based on Active and deleted security
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
				{ "securityOb", "java.util.HashMap", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "from", "java.lang.String", REQUEST_SCOPE },
				{"collCodeDescMap", "java.util.Map",REQUEST_SCOPE},
				{"countryCodeNameMap", "java.util.Map",REQUEST_SCOPE},
				{"sysBankBranchCodeNameMap", "java.util.Map",REQUEST_SCOPE},
				{"activeDeleteFlag","java.lang.String", REQUEST_SCOPE},
				
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
		HashMap lmtcolmap = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		CustomerSearchCriteria objSearch = (CustomerSearchCriteria) map
				.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		if (objSearch == null) {
			objSearch = new CustomerSearchCriteria();
			result.put("startIndex", map.get("startIndex"));
		}
		else {
			result.put("startIndex", String.valueOf(objSearch.getStartIndex()));
		}
		objSearch.setFrompage((String) map.get("frompage"));
		String from = (String) map.get("from");
		result.put("from", from);
		result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, objSearch);

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		String strLimitProfileId = (String) map.get("limitProfileID");
		String customerId = (String) map.get("customerID");
		String transactionID = (String) map.get("transactionID");
		ILimitProxy limitProxy = LimitProxyFactory.getProxy();
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (limitProfileOB == null) {
			throw new CommandProcessingException("ILimitProfile is null in session!");
		}
		ILimit[] limits = limitProfileOB.getLimits();
		try {
			limits = limitProxy.getFilteredNilColCheckListLimits(theOBTrxContext, limitProfileOB);
		}
		catch (LimitException ex) {
			throw new CommandProcessingException("Failed to filtered no collateral checklist for the limits ["
					+ limitProfileOB + "]");
		}
		//Added by Uma Khot: Start: Phase 3 CR:Search based on Active and deleted security
		
		String activeDeleteFlag= map.get("activeDeleteFlag") == null ? "Active": (String)map.get("activeDeleteFlag");
		
		result.put("activeDeleteFlag",activeDeleteFlag);
		lmtcolmap = getCollateralLimitMapForActiveAndDeleted(limitProfileOB,activeDeleteFlag);
		
		//Added by Uma Khot: End: Phase 3 CR:Search based on Active and deleted security
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
			        
			        if("AB100".equalsIgnoreCase(obcol.getCollateralSubType().getSubTypeCode())) {
//				        if(obcol instanceof OBGeneralCharge) {
				        	double ac=CollateralDAOFactory.getDAO().getCalculateddpValueByDueDate(String.valueOf(obcol.getCollateralID()));
				        	ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
				        	 List retrivedDueDateList=(List)collateralProxy.getRecurrentDueDateListByCustomerAndCollatralID(limitProfileOB.getCustomerID(),obcol.getCollateralID());
				        	 if(retrivedDueDateList.size()>0) {
				        	 Amount ab = new Amount(ac,obcol.getCurrencyCode());
				        	 obcol.setCMV(ab);
				        	 System.out.println("latest CaculatedDp(Drawing Power value) by  due date ==== "+ab);
				        	 }
				        	 else {
				        		 obcol.setCMV(null); 
				        	 }
			        }
			        
			 }
			
		}

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

		// CR 13 link inner to outer limit bca
		ICustomerProxy customerproxy = CustomerProxyFactory.getProxy();
		CustomerSearchCriteria searchCriteria = new CustomerSearchCriteria();
		searchCriteria.setCtx(theOBTrxContext);
		searchCriteria.setLimits(limits);
		SearchResult bcaResult = customerproxy.searchCustomer(searchCriteria);

		if (bcaResult != null) {
			Collection resultCollection = bcaResult.getResultList();
			if (resultCollection != null) {
				HashMap colBcaList = new HashMap();
				HashMap bcaInfo = null;
				Iterator itor = resultCollection.iterator();
				while (itor.hasNext()) {
					bcaInfo = new HashMap();
					ICustomerSearchResult customerSearchResult = (ICustomerSearchResult) itor.next();
					bcaInfo.put("bcaRef", customerSearchResult.getInstructionRefNo());
					bcaInfo.put("bkgLoc", customerSearchResult.getOrigLocCntry());
					bcaInfo.put("leId", customerSearchResult.getLegalReference());
					bcaInfo.put("custName", customerSearchResult.getCustomerName());
					colBcaList.put(String.valueOf(customerSearchResult.getInnerLimitID()), bcaInfo);
				}
				result.put("customerObList", colBcaList);
			}
			else {
				result.put("customerObList", new HashMap());
			}
		}
		else {
			result.put("customerObList", new HashMap());
		}
		result.put("securityOb", sortedCollateralLimitMap);
		result.put("limitProfileID", strLimitProfileId);
		result.put("customerID", customerId);
		if (map.get("startIndex") != null) {
			result.put(IGlobalConstant.GLOBAL_TRX_ID, null);
		}
		else {
			if (transactionID != null) {
				result.put(IGlobalConstant.GLOBAL_TRX_ID, transactionID);
			}
		}
		result.put("collCodeDescMap", getCollateralCodeList());
		result.put("countryCodeNameMap", getCountryNameList());
		result.put("sysBankBranchCodeNameMap", getSysBankBranchNameList());
		
		DefaultLogger.debug(this, "Going out of doExecute() of ViewSecuritiesCustomerCommand");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}
	
	//Add By Govind S:Get collateral code with desc,24/10/2011
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
	
//Added by Uma Khot: Start: Phase 3 CR:Search based on Active and deleted security
	/**
	 * Get a HashMap containing the following information: 1. The HashMap keys
	 * are ICollateral objects 2. The HashMap values are ArrayList of ILimit
	 * objects that are linked to the ICollateral keys
	 * 
	 * @param limitProfile
	 * @return a HashMap with key ICollateral and value an ArrayList of ILimit
	 *         objects
	 */
	private HashMap getCollateralLimitMapForActiveAndDeleted(ILimitProfile limitProfile,String flag) {
		ILimit[] limitList = limitProfile.getLimits();

		HashMap hmap = new HashMap();
		int limitCount = limitList == null ? 0 : limitList.length;
		int colCount;
		for (int i = 0; i < limitCount; i++) {

			ICollateralAllocation[] allocList = limitList[i].getCollateralAllocations();
			colCount = allocList == null ? 0 : allocList.length;
			for (int j = 0; j < colCount; j++) {
				ICollateral col = allocList[j].getCollateral();
				if (col != null) {
					if("Active".equals(flag) && (allocList[j].getHostStatus()!=null && !(allocList[j].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)))){
					ArrayList alist = (ArrayList) hmap.get(col);
					if (alist == null) {
						alist = new ArrayList();
					}
					alist.add(limitList[i]);
					hmap.put(col, alist);
					}
					else if("Deleted".equals(flag) && (allocList[j].getHostStatus()!=null && (allocList[j].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)))){
						ArrayList alist = (ArrayList) hmap.get(col);
						if (alist == null) {
							alist = new ArrayList();
						}
						alist.add(limitList[i]);
						hmap.put(col, alist);
					}
				}
			}

			if (limitList[i].getCoBorrowerLimits() != null) {
				ICoBorrowerLimit[] colist = limitList[i].getCoBorrowerLimits();
				int limitCount1 = colist == null ? 0 : colist.length;
				for (int k = 0; k < limitCount1; k++) {
					ICollateralAllocation[] allocList1 = colist[k].getCollateralAllocations();
					int limitCount2 = allocList1 == null ? 0 : allocList1.length;
					for (int x = 0; x < limitCount2; x++) {
						if (allocList1[x].getCollateral() != null) {
							if("Active".equals(flag) && (allocList1[x].getHostStatus()!=null && !(allocList1[x].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)))){
							ICollateral col = allocList1[x].getCollateral();

							ArrayList alist = (ArrayList) hmap.get(col);
							if (alist == null) {
								alist = new ArrayList();
							}
							alist.add(colist[k]);
							hmap.put(col, alist);
						}
							else if("Deleted".equals(flag) && (allocList1[x].getHostStatus()!=null && !(allocList1[x].getHostStatus().equals(ICMSConstant.HOST_STATUS_DELETE)))){

								ICollateral col = allocList1[x].getCollateral();

								ArrayList alist = (ArrayList) hmap.get(col);
								if (alist == null) {
									alist = new ArrayList();
								}
								alist.add(colist[k]);
								hmap.put(col, alist);
							
							}
						}
					}
				}
			}

		} // end for main loop

		return hmap;
	}
	//Added by Uma Khot: End: Phase 3 CR:Search based on Active and deleted security
}