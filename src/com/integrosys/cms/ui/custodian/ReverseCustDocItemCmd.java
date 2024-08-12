/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/custodian/ReverseCustDocItemCmd.java,v 1.4 2005/08/27 07:14:04 wltan Exp $
 */
package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;

/**
 * Command class to read a custodian doc by its transaction id...
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/27 07:14:04 $ Tag: $Name: $
 */
public class ReverseCustDocItemCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReverseCustDocItemCmd() {
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
				{ "aCustodianDoc", "com.integrosys.cms.app.custodian.bus.OBCustodianDoc", FORM_SCOPE },
                { "trxId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE }, // CR
																					// -
																					// 107
				{ "reverseStatus", "java.lang.String", REQUEST_SCOPE },
				{ "reversalRemarks", "java.lang.String", REQUEST_SCOPE } });
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
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "actualDocItem", "com.integrosys.cms.app.custodian.bus.ICustodianDocItem", SERVICE_SCOPE },
				{ "stagingDocItem", "com.integrosys.cms.app.custodian.bus.ICustodianDocItem", SERVICE_SCOPE },
				{ "session.trxId", "java.lang.String", SERVICE_SCOPE }, { "forId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
				{ "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here the custodian Doc item status is
	 * changed to pending reverse.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String trxID = (String) map.get("trxId");
		String strReverseStatus = (String) map.get("reverseStatus");
		String checkListItemRef = (String) map.get("checkListItemRef");
		ICustodianTrxValue custodianTrxVal = null;
		ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
		ICustodianTrxValue sessCustTrxVal = (ICustodianTrxValue) map.get("custodianTrxVal");
		if (trxID != null) {
			resultMap.put("session.trxId", trxID);
		}
		else {
			trxID = (String) map.get("session.trxId");
		}
		try {
			if ((checkListItemRef != null) && !checkListItemRef.trim().equals("")) {
				long lcheckListItemRef = 0;
				if ((sessCustTrxVal == null) && (trxID != null) && (trxID.trim().length() > 0)) {
					custodianTrxVal = proxy.getDocByTrxID(trxID);
					sessCustTrxVal = custodianTrxVal;
				}
				if (sessCustTrxVal != null) {
					custodianTrxVal = sessCustTrxVal;
					ICustodianDoc actualDoc = sessCustTrxVal.getCustodianDoc();
					ICustodianDoc stagingDoc = sessCustTrxVal.getStagingCustodianDoc();
					String actualRevRemarks = actualDoc != null ? actualDoc.getReversalRemarks() : "";
					String stagingRevRemarks = (String) map.get("reversalRemarks");
					String forId = "";
					if (actualDoc != null) {
						forId = actualDoc.getDocType();
					}
					else {
						forId = stagingDoc.getDocType();
					}
					try {
						lcheckListItemRef = Long.parseLong(checkListItemRef);
					}
					catch (Exception e) {
					}
					ICustodianDocItem actualItem = null;
					ICustodianDocItem stagingItem = null;
					ICustodianDocItem item = null;

					// find actual item
					if (actualDoc != null) {
						if (actualDoc.getCustodianDocItems() != null) {
							item = null;
							for (Iterator iterator = actualDoc.getCustodianDocItems().iterator(); iterator.hasNext();) {
								item = (ICustodianDocItem) iterator.next();
								if ((item != null) && (item.getCheckListItemRefID() == lcheckListItemRef)) {
									actualItem = item;
									// Preformance : terminate when item found
									break;
								}
							}
						}
					}

					// find staging item
					if (stagingDoc.getCustodianDocItems() != null) {
						item = null;
						for (Iterator iterator = stagingDoc.getCustodianDocItems().iterator(); iterator.hasNext();) {
							item = (ICustodianDocItem) iterator.next();
							if ((item != null) && (item.getCheckListItemRefID() == lcheckListItemRef)) {
								item.setStatus(strReverseStatus);
								// CMS-2183 : timestamp item when changed
								// Reversal does not go through mapper so
								// timestamp here
								item.setLastUpdateDate(DateUtil.getDate());
								// Performance : terminate when item found
								break;
							}
						}
					}

					resultMap.put("sess.reversalRemarks", stagingRevRemarks);
					resultMap.put("forId", forId);
					resultMap.put("stagingDocItem", stagingItem);
					resultMap.put("actualDocItem", actualItem);

				}

				// update the list of changed item
				ArrayList checkListItemRefList = (java.util.ArrayList) map.get("checkListItemRefList"); // CR
																										// -
																										// 107
				if (checkListItemRefList == null) {
					checkListItemRefList = new ArrayList();
				}
				checkListItemRefList.add(checkListItemRef);
				resultMap.put("checkListItemRefList", checkListItemRefList); // CR
																				// -
																				// 107
			}
			resultMap.put("custodianTrxVal", custodianTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}