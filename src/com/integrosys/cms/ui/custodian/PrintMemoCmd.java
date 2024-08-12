/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItemSearchResult;
import com.integrosys.cms.app.custodian.bus.ICustodianDocSearchResult;
import com.integrosys.cms.app.custodian.bus.IMemo;
import com.integrosys.cms.app.custodian.bus.OBCustAuthorize;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Command class to print the (lodgement/withdrawl) memo by CPC Maker
 * @author $Author: lini $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/16 10:08:08 $ Tag: $Name: $
 */
public class PrintMemoCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrintMemoCmd() {
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
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theMemo", "java.util.ArrayList", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "memoType", "java.lang.String", REQUEST_SCOPE } } // added the
																	// above 3
																	// lines for
																	// CMS-465
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "memo", "com.integrosys.cms.app.custodian.bus.IMemo", SERVICE_SCOPE },
				{ "memoREQ", "com.integrosys.cms.app.custodian.bus.IMemo", REQUEST_SCOPE },
				{ "printDocList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE } });
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
		ArrayList list = (ArrayList) map.get("theMemo");
		OBCustAuthorize[] custAuthzArr = (OBCustAuthorize[]) list.get(0);
		String[] docIdList = (String[]) list.get(1);
		HashMap checkListIDMap = (HashMap) list.get(2);
		Long[] docIds = new Long[docIdList.length];
		Vector filteredItemRefsVect = new Vector();
		for (int i = 0; i < docIdList.length; i++) {
			docIds[i] = new Long(Long.parseLong(docIdList[i]));
			filteredItemRefsVect.add(Long.valueOf(docIdList[i]));
			// DefaultLogger.debug(this,"> PrintMemoCmd: Added "+docIdList[i]+
			// " to filteredItemRefsVect");
		}
		// DefaultLogger.debug(this,
		// "Inside doExecute() >> custAuthz  = "+docIdList+custAuthzArr);
		// for(int i=0;i<custAuthzArr.length;i++){
		// DefaultLogger.debug(this,
		// "Inside doExecute() >> custAuthz = "+custAuthzArr[i]);
		// DefaultLogger.debug(this,
		// "Inside doExecute() >> custAuthz = "+custAuthzArr[i].getAuthzDate());
		// DefaultLogger.debug(this,
		// "Inside doExecute() >> custAuthz = "+custAuthzArr
		// [i].getCheckListItemRef());
		// }
		try {
			ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			proxy.persistPrintAuthzDetails(custAuthzArr);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);

			// bernard - added the following block for CMS-465
			ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			String memoType = (String) map.get("memoType");
			// DefaultLogger.debug(this,"> PrintMemoCmd: memoType="+memoType);

			SearchResult sr = null;
			// this if-else block populates SearchResult (sr) with the correct
			// list of documents
			if ((memoType != null) && memoType.equals("lodgement")) {
				if (cust.getNonBorrowerInd()) {
					if (limitProfile != null) {
						sr = proxy.getPendingLodgementListForNonBorrower(theOBTrxContext, limitProfile
								.getLimitProfileID(), cust.getCustomerID());
					}
					else // if limit profile is null
					{
						sr = proxy.getPendingLodgementListForNonBorrower(theOBTrxContext, cust.getCustomerID());
					}
				}
				else {
					// DefaultLogger.debug(this,
					// "Inside doExecute() searchCriteria = "
					// +limitProfile.getLimitProfileID());
					sr = proxy.getPendingLodgementList(theOBTrxContext, limitProfile.getLimitProfileID());
				}
			}
			else if ((memoType != null) && memoType.equals("withdrawal")) {
				if (cust.getNonBorrowerInd()) {
					if (limitProfile != null) {
						sr = proxy.getPendingWithdrawalListForNonBorrower(theOBTrxContext, limitProfile
								.getLimitProfileID(), cust.getCustomerID());
					}
					else {
						sr = proxy.getPendingWithdrawalListForNonBorrower(theOBTrxContext, cust.getCustomerID());
					}
				}
				else {
					// DefaultLogger.debug(this,
					// "Inside doExecute() searchCriteria = "
					// +limitProfile.getLimitProfileID());
					sr = proxy.getPendingWithdrawalList(theOBTrxContext, limitProfile.getLimitProfileID());
				}
			}
            else if ((memoType != null) && memoType.equals("reversal")) {
				if (cust.getNonBorrowerInd()) {
					if (limitProfile != null) {
						sr = proxy.getPendingReversalListForNonBorrower(theOBTrxContext, limitProfile
								.getLimitProfileID(), cust.getCustomerID());
					}
					else {
						sr = proxy.getPendingReversalListForNonBorrower(theOBTrxContext, cust.getCustomerID());
					}
				}
				else {
					// DefaultLogger.debug(this,
					// "Inside doExecute() searchCriteria = "
					// +limitProfile.getLimitProfileID());
					sr = proxy.getPendingReversalList(theOBTrxContext, limitProfile.getLimitProfileID());
				}
			}

            ArrayList custodianDocs = null;
			if (sr != null) {
				custodianDocs = (ArrayList) sr.getResultList();
			}
			else {
				custodianDocs = new ArrayList(); // just populate with empty
													// ArrayList
			}

			ArrayList filteredDocList = new ArrayList();
			ICustodianDocSearchResult custodianDoc = null;
			ICustodianDocItemSearchResult[] custodianDocItems = null;
			HashMap filteredCustodianDocMap = new HashMap();
			boolean isIncluded = false;
			int itemCount = 0;
			int filteredItemCount = 0;
			// iterates through the entire lodgement/withdrawal list
			for (int i = 0; i < custodianDocs.size(); i++) {
				isIncluded = false;
				custodianDoc = (ICustodianDocSearchResult) custodianDocs.get(i);
				custodianDocItems = custodianDoc.getCustodianDocItems();
				ArrayList filteredItemList = null;
				// iterates through the items and filters off those that were
				// not selected
				for (int j = 0; j < custodianDocItems.length; j++) {
					long checklistItemRef = custodianDocItems[j].getDocItemRef();
					if (filteredItemRefsVect.contains(new Long(checklistItemRef))) {
						if (filteredItemList == null) {
							filteredItemList = new ArrayList();
						}
						filteredItemList.add(custodianDocItems[j]);
						isIncluded = true;
						filteredItemCount++;
					}
					itemCount++;
				}
				// add custdoc with selected items into results list
				if (isIncluded) {
					ICustodianDocSearchResult newCustDoc = (ICustodianDocSearchResult) CommonUtil
							.deepClone(custodianDoc);
					newCustDoc.setCustodianDocItems((ICustodianDocItemSearchResult[]) filteredItemList
							.toArray(new ICustodianDocItemSearchResult[filteredItemList.size()]));
					filteredDocList.add(newCustDoc);
				}
			}
			DefaultLogger.debug(this, "Before filtering : No. of docs - " + custodianDocs.size() + " No. of items - "
					+ itemCount);
			DefaultLogger.debug(this, "After filtering : No. of docs - " + filteredDocList.size() + " No. of items - "
					+ filteredItemCount);
			sr = new SearchResult(0, 0, filteredDocList.size(), filteredDocList); // creates
																					// new
																					// SearchResult
																					// object
																					// with
																					// filtered
																					// items
			resultMap.put("printDocList", sr);

			/*
			 * //iterates through the entire lodgement/withdrawal list and
			 * filters off those that were not selected for (int i=0;
			 * i<custDocList.size(); i++) { ICustodianDocSearchResult
			 * custSearchResult = (ICustodianDocSearchResult)custDocList.get(i);
			 * long checkListItemRef = custSearchResult.getDocItemRef(); //gets
			 * the checkListItemRef for the current iteration
			 * DefaultLogger.debug(this,
			 * "Comparing "+checkListItemRef+" with filteredItemRefsVect"); if
			 * (filteredItemRefsVect.contains(new Long(checkListItemRef))) //if
			 * current custodian doc is selected
			 * filteredItemList.add(custSearchResult); //add it to be displayed
			 * } DefaultLogger.debug(this,
			 * "No. of docs before filtering="+custDocList.size());
			 * DefaultLogger.debug(this,
			 * "No. of docs after filtering="+filteredItemList.size()); sr = new
			 * SearchResult(0, 0, filteredItemList.size(), filteredItemList);
			 * //creates new SearchResult object with filtered items
			 * resultMap.put("printDocList", sr);
			 */

			// IMemo memo = proxy.getPrintMemo(cust, docIds);
			// Changed to pass in Checklist IDs and ChecklistItemIDs in HashMap
			IMemo memo = proxy.getPrintMemo(cust, checkListIDMap);
			resultMap.put("memo", memo);
			resultMap.put("memoREQ", memo);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
