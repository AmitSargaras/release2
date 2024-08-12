/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: jychong $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/11/07 02:03:28 $ Tag: $Name: $
 */
public class ListDocByDocTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListDocByDocTypeCmd() {
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
				{ "CustodianSearchCriteria", "com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria", FORM_SCOPE },
				{ "instructionLoc", "java.lang.String", REQUEST_SCOPE },
				{ "instructionBkLoc", "java.lang.String", SERVICE_SCOPE },
				{ "displayBCA", "java.lang.String", REQUEST_SCOPE },
				{ "displayBCALoc", "java.lang.String", SERVICE_SCOPE },
				{ "securityLoc", "java.lang.String", REQUEST_SCOPE },
				{ "securityLocation", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "docList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE },
				{ "ccowner", "com.integrosys.cms.app.custodian.bus.CCCustodianInfo", REQUEST_SCOPE },
				{ "colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", REQUEST_SCOPE },
				{ "ownertype", "java.lang.String", REQUEST_SCOPE },
				{ "instructionBkLoc", "java.lang.String", SERVICE_SCOPE },
				{ "displayBCALoc", "java.lang.String", SERVICE_SCOPE },
				{ "securityLocation", "java.lang.String", SERVICE_SCOPE },
				{ "CustodianSearchCriteria", "com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria",
						SERVICE_SCOPE },
				{ "session.ccowner", "com.integrosys.cms.app.custodian.bus.CCCustodianInfo", SERVICE_SCOPE },
				{ "session.colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", SERVICE_SCOPE },
				{ "session.ownertype", "java.lang.String", SERVICE_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE },
				{ "checkListStatus", "java.lang.String", SERVICE_SCOPE },
				{ "customerCategory", "java.lang.String", SERVICE_SCOPE },
				{ "docNos", "java.util.ArrayList", SERVICE_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		String event = (String) map.get("event");
		ICustodianTrxValue custodianTrxValue = null;
		try {
			ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			CustodianSearchCriteria criteria = (CustodianSearchCriteria) map.get("CustodianSearchCriteria");
			/*
			 * DefaultLogger.debug(this, "Criteria: " +
			 * criteria.getLimitProfileID()); DefaultLogger.debug(this,
			 * "Criteria: " + criteria.getSubProfileID());
			 * DefaultLogger.debug(this, "Criteria: " +
			 * criteria.getCheckListID()); DefaultLogger.debug(this,
			 * "Criteria: " + criteria.getDocType()); DefaultLogger.debug(this,
			 * "Criteria: " + criteria.getDocSubType());
			 */
			resultMap.put("CustodianSearchCriteria", criteria);
			SearchResult sr = null;
			if (criteria.getCheckListID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				HashMap srmap = proxy.getDocWithOwnerInfo(criteria);
				ICheckListProxyManager checkListProxyManager = CheckListProxyManagerFactory.getCheckListProxyManager();
				ICheckList iCheckList = checkListProxyManager.getCheckListByID(criteria.getCheckListID());
				String checkListStatus = iCheckList.getCheckListStatus();
				sr = (SearchResult) srmap.get(ICMSConstant.SEARCH_RESULT);
				resultMap.put("ownertype", srmap.get(ICMSConstant.SUB_CATEGORY));
				resultMap.put("ccowner", srmap.get(ICMSConstant.CC_OWNER));
				resultMap.put("colowner", srmap.get(ICMSConstant.SEC_OWNER));
				custodianTrxValue = (ICustodianTrxValue) srmap.get("trxValue");
				ICustodianDoc stagingDoc = null;
				if (custodianTrxValue != null) {
					stagingDoc = custodianTrxValue.getStagingCustodianDoc();
				}
				if (stagingDoc != null) {
					resultMap.put("sess.reversalRemarks", stagingDoc.getReversalRemarks());
				}

				String customerCategory = null;
				if ((iCheckList != null) && (iCheckList.getCheckListOwner() != null)) {
					customerCategory = iCheckList.getCheckListOwner().getSubOwnerType();
				}

				// ------- CR100a, linked Insurance Policy ------- Start
				ArrayList outputDocIds = new ArrayList();
				if (ICMSConstant.DOC_TYPE_SECURITY.equals(iCheckList.getCheckListType())) {
					ArrayList docNos = new ArrayList();
					ICheckListItem[] itemList = iCheckList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo);
					}

					try {
						outputDocIds = checkListProxyManager.getDocumentIdsForCheckList(docNos);
					}
					catch (Exception e) {
						DefaultLogger.error(this, e.getMessage(), e);
						e.printStackTrace();
						throw (new CommandProcessingException(e.getMessage()));
					}
				}

				// ------- CR100a, linked Insurance Policy ------- End

				resultMap.put("session.ownertype", srmap.get(ICMSConstant.SUB_CATEGORY));
				resultMap.put("session.ccowner", srmap.get(ICMSConstant.CC_OWNER));
				resultMap.put("session.colowner", srmap.get(ICMSConstant.SEC_OWNER));
				resultMap.put("checkListStatus", checkListStatus);
				resultMap.put("customerCategory", customerCategory);
				resultMap.put("docNos", outputDocIds);
			}
			resultMap.put("custodianTrxVal", custodianTrxValue);
			// Checking for Active Transaction else Work in Progress
			if ((custodianTrxValue != null) && (event.equals("cc_doc_list") || event.equals("security_doc_list"))) {
				if (!custodianTrxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
						&& !custodianTrxValue.getStatus().equals(ICMSConstant.STATE_ND)
						&& !custodianTrxValue.getStatus().equals(ICMSConstant.STATE_CLOSED)) {
					resultMap.put("wip", "wip");
				}
			}
			// Transaction object used instead of SearchResult
			// resultMap.put("docList", sr);
			if (map.get("instructionLoc") == null) {
				resultMap.put("instructionBkLoc", map.get("instructionBkLoc"));
			}
			else {
				resultMap.put("instructionBkLoc", map.get("instructionLoc"));
			}

			if (map.get("displayBCA") == null) {
				resultMap.put("displayBCALoc", map.get("displayBCALoc"));
			}
			else {
				resultMap.put("displayBCALoc", map.get("displayBCA"));
			}

			if (map.get("securityLoc") == null) {
				resultMap.put("securityLocation", map.get("securityLocation"));
			}
			else {
				resultMap.put("securityLocation", map.get("securityLoc"));
			}

			resultMap.put("frame", "true");
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