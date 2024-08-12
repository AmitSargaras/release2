/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.OBCustodianDocItem;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: lini $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/16 10:08:08 $ Tag: $Name: $
 */
public class ListDocSessionCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListDocSessionCmd() {
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
				{ "docList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "CustodianSearchCriteria", "com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria", FORM_SCOPE },
				{ "instructionLoc", "java.lang.String", REQUEST_SCOPE },
				{ "instructionBkLoc", "java.lang.String", SERVICE_SCOPE },
				{ "displayBCA", "java.lang.String", REQUEST_SCOPE },
				{ "displayBCALoc", "java.lang.String", SERVICE_SCOPE },
				{ "securityLoc", "java.lang.String", REQUEST_SCOPE },
				{ "securityLocation", "java.lang.String", SERVICE_SCOPE },
				{ "reversalRemarks", "java.lang.String", REQUEST_SCOPE },
                { "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
                { "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
                { "checklistid", "java.lang.String", REQUEST_SCOPE },
                { "securityid", "java.lang.String", REQUEST_SCOPE },
                { "action", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "docList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "ccowner", "com.integrosys.cms.app.custodian.bus.CCCustodianInfo", REQUEST_SCOPE },
				{ "colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", REQUEST_SCOPE },
				{ "ownertype", "java.lang.String", REQUEST_SCOPE },
				{ "instructionBkLoc", "java.lang.String", SERVICE_SCOPE },
				{ "displayBCALoc", "java.lang.String", SERVICE_SCOPE },
				{ "securityLocation", "java.lang.String", SERVICE_SCOPE },
				{ "CustodianSearchCriteria", "com.integrosys.cms.app.custodian.bus.CustodianSearchCriteria", SERVICE_SCOPE },
                { "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE },
                { "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE }, });
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
		ICustodianTrxValue custodianTrxValue = null;
		String revRem = null;
		if (map.get("docList") != null) {
			try {
				ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
				CustodianSearchCriteria criteria = (CustodianSearchCriteria) map.get("CustodianSearchCriteria");
				DefaultLogger.debug(this, "Criteria: " + criteria.getCheckListID());
				DefaultLogger.debug(this, "Criteria: " + criteria.getDocType());
				DefaultLogger.debug(this, "Criteria: " + criteria.getDocSubType());
				resultMap.put("CustodianSearchCriteria", criteria);
				SearchResult sr = null;
				if (criteria.getCheckListID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					HashMap srmap = proxy.getDocListWithOwnerInfo(criteria);
					sr = (SearchResult) srmap.get(ICMSConstant.SEARCH_RESULT);
					resultMap.put("ownertype", srmap.get(ICMSConstant.SUB_CATEGORY));
					resultMap.put("ccowner", srmap.get(ICMSConstant.CC_OWNER));
					resultMap.put("colowner", srmap.get(ICMSConstant.SEC_OWNER));
					custodianTrxValue = (ICustodianTrxValue) srmap.get("trxValue");
				}
				resultMap.put("docList", sr);
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

				ICustodianDoc stagingDoc = null;
				if (custodianTrxValue != null) {
					stagingDoc = custodianTrxValue.getStagingCustodianDoc();
				}
				if (stagingDoc != null) {
					revRem = stagingDoc.getReversalRemarks();
				}

				if ((map.get("reversalRemarks") != null) && !map.get("reversalRemarks").equals("")) {
					revRem = (String) map.get("reversalRemarks");
				}

				if ((revRem != null) && !revRem.equals("")) {
					resultMap.put("sess.reversalRemarks", revRem);
				}
				else {
					resultMap.put("sess.reversalRemarks", (String) map.get("reversalRemarks"));
				}

			}
			catch (Exception e) {
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		else {
			DefaultLogger.debug(this, "Getting from session");
			resultMap.put("docList", map.get("docList"));

            if (map.get("action") != null && map.get("action").toString().equals("cancel")) {
                //will need to revert to previous data since this invoke from the Cancel statement for CC and Security
                long checkListItemRef = Long.parseLong((String) map.get("checkListItemRef"));

                ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();

                CustodianSearchCriteria criteria = new CustodianSearchCriteria();
                criteria.setCheckListID(Long.parseLong((String) map.get("checklistid")));

                if (map.get("securityid") != null) {
                    //security
                    criteria.setDocType(ICMSConstant.DOC_TYPE_SECURITY);
                    criteria.setCollateralID(Long.parseLong((String) map.get("securityid")));
                } else {
                    //cc
                    criteria.setDocType(ICMSConstant.DOC_TYPE_CC);
                }

                ICustodianTrxValue oriCustodianTrxValue = null;
                ICustodianDoc oriStagingDoc = null;

                if (criteria.getCheckListID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
                    HashMap srmap = proxy.getDocWithOwnerInfo(criteria);
                    oriCustodianTrxValue = (ICustodianTrxValue) srmap.get("trxValue");
                }

                if (oriCustodianTrxValue != null) {
                    oriStagingDoc = oriCustodianTrxValue.getStagingCustodianDoc();

                    if (oriStagingDoc != null) {
                        ICustodianTrxValue custodianTrxVal = (OBCustodianTrxValue) map.get("custodianTrxVal");

                        ArrayList oriCustDocItems = oriStagingDoc.getCustodianDocItems();
                        for (int i = 0; i < oriCustDocItems.size(); i++) {
                            OBCustodianDocItem oriOB = (OBCustodianDocItem) oriCustDocItems.get(i);

                            //not the one that working on now. Escape.
                            if (oriOB.getCheckListItemRefID() != checkListItemRef) continue;

                            //found then reset
                            if (custodianTrxVal != null) {
                                ICustodianDoc stagingDoc = custodianTrxVal.getStagingCustodianDoc();
                                ArrayList custDocItems = stagingDoc.getCustodianDocItems();
                                custDocItems.set(i, oriOB);
                            }
                        }
                    }
                }
            }
		}
        
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
