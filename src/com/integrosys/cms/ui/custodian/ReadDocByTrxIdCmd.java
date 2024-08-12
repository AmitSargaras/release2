/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;

/**
 * Command class to read a custodian doc by its transaction id...
 * @author $Author: jychong $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/11/07 02:03:28 $ Tag: $Name: $
 */
public class ReadDocByTrxIdCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadDocByTrxIdCmd() {
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
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
                { "sess.trxId", "java.lang.String", SERVICE_SCOPE },
				{ "sess.checkListItemRef", "java.lang.String", SERVICE_SCOPE },
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
				{ "reversalRemarks", "java.lang.String", REQUEST_SCOPE },
				{ "forId", "java.lang.String", SERVICE_SCOPE },
                { "frame", "java.lang.String", SERVICE_SCOPE }});
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
				{ "session.trxId", "java.lang.String", SERVICE_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
				{ "sess.reversalRemarks", "java.lang.String", SERVICE_SCOPE },
				{ "checkListStatus", "java.lang.String", SERVICE_SCOPE },
				{ "customerCategory", "java.lang.String", SERVICE_SCOPE },
				{ "forId", "java.lang.String", SERVICE_SCOPE }, { "docNos", "java.util.ArrayList", SERVICE_SCOPE },
                { "docItemSecEnvBarcode", "java.lang.String", REQUEST_SCOPE},
                { "sess.trxId", "java.lang.String", SERVICE_SCOPE },
				{ "sess.checkListItemRef", "java.lang.String", SERVICE_SCOPE },

		});
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

		resultMap.put("forId", (String) map.get("forId"));
		String trxID = (String) map.get("trxId");
		String checkListItemRef = (String) map.get("checkListItemRef");

        if (trxID == null){
            trxID = (String) map.get("sess.trxId");
        }
        if (checkListItemRef == null){
             checkListItemRef = (String) map.get("sess.checkListItemRef");
        }

		ICheckListProxyManager checkListProxyManager = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICheckList aCheckList = null;

		ICustodianTrxValue custodianTrxVal = null;
		ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
		ICustodianTrxValue sessCustTrxVal = (ICustodianTrxValue) map.get("custodianTrxVal");
		ArrayList checkListItemRefList = new ArrayList();
		String revRem = null;
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

					aCheckList = checkListProxyManager.getCheckListByID(stagingDoc.getCheckListID());

					try {
						lcheckListItemRef = Long.parseLong(checkListItemRef);
					}
					catch (Exception e) {
					}
					ICustodianDocItem actualItem = null;
					ICustodianDocItem stagingItem = null;
					ICustodianDocItem item = null;
					if (stagingDoc.getCustodianDocItems() != null) {
						item = null;
						checkListItemRefList = stagingDoc.getUpdatedCheckListItemRefArrayList();// CR
																								// -
																								// 107
						for (Iterator iterator = stagingDoc.getCustodianDocItems().iterator(); iterator.hasNext();) {
							item = (ICustodianDocItem) iterator.next();
							if ((item != null) && (item.getCheckListItemRefID() == lcheckListItemRef)) {
								stagingItem = item;
								// terminate loop when staging item found
								break;
							}
						}
						revRem = stagingDoc.getReversalRemarks();
					}
					if (actualDoc != null) {
						if (actualDoc.getCustodianDocItems() != null) {
							item = null;
							for (Iterator iterator = actualDoc.getCustodianDocItems().iterator(); iterator.hasNext();) {
								item = (ICustodianDocItem) iterator.next();
								if ((item != null) && (stagingItem != null)
										&& (item.getCheckListItemRefID() == stagingItem.getCheckListItemRefID())) {
									actualItem = item;
									// terminate loop when actual item found
									break;
								}
							}
						}
					}
					checkListItemRefList = (ArrayList) map.get("checkListItemRefList");// CR
																						// -
																						// 107
					String docItemSecEnvBarcode = stagingItem.getSecEnvelopeBarcode();
                    resultMap.put("docItemSecEnvBarcode", docItemSecEnvBarcode);
                    resultMap.put("stagingDocItem", stagingItem);
					resultMap.put("actualDocItem", actualItem);
					resultMap.put("checkListItemRefList", checkListItemRefList);// CR
																				// -
																				// 107
				}
			}
			else if ((trxID != null) && (trxID.trim().length() > 0)) {
				custodianTrxVal = proxy.getDocByTrxID(trxID);
				// DefaultLogger.debug(this, "###### <doExecute()> trx ref id: "
				// + custodianTrxVal.getReferenceID() +
				// " trx stg ref id: " +
				// custodianTrxVal.getStagingReferenceID());

				ICustodianDoc stagingDoc = custodianTrxVal.getStagingCustodianDoc();// CR
																					// -
																					// 107
				revRem = stagingDoc.getReversalRemarks();
				ICustodianDoc actualDoc = custodianTrxVal.getCustodianDoc();

				aCheckList = checkListProxyManager.getCheckListByID(stagingDoc.getCheckListID());
				String checkListStatus = aCheckList.getCheckListStatus();
				// DefaultLogger.debug(this, aCheckList.getCheckListStatus());
				resultMap.put("checkListStatus", checkListStatus);
				String customerCategory = null;
				if ((aCheckList != null) && (aCheckList.getCheckListOwner() != null)) {
					customerCategory = aCheckList.getCheckListOwner().getSubOwnerType();
				}
				resultMap.put("customerCategory", customerCategory);
				checkListItemRefList = stagingDoc.getUpdatedCheckListItemRefArrayList();// CR
																						// -
																						// 107
				if ((checkListItemRefList != null) && (!checkListItemRefList.isEmpty())) {
					resultMap.put("checkListItemRefList", checkListItemRefList);// CR
																				// -
																				// 107
				}
				else {
					// construct list of updated items
					ArrayList stagingDocItems = stagingDoc.getCustodianDocItems();
					if ((stagingDocItems != null) && (!stagingDocItems.isEmpty())) {
						for (int i = 0; i < stagingDocItems.size(); i++) {
							ICustodianDocItem stagingDocItem = (ICustodianDocItem) stagingDocItems.get(i);
							// add item to list if in pending state
							if (stagingDocItem.isStatusChanged()) {
								checkListItemRefList.add(Long.toString(stagingDocItem.getCheckListItemRefID()));
							}
						}
					}
					resultMap.put("checkListItemRefList", checkListItemRefList);// CR
																				// -
																				// 107
				}
				resultMap.put("frame", "false");
			}

			// ------- CR100a, linked Insurance Policy ------- Start
			ArrayList outputDocIds = new ArrayList();
			if (ICMSConstant.DOC_TYPE_SECURITY.equals(aCheckList.getCheckListType())) {
				ArrayList docNos = new ArrayList();
				ICheckListItem[] itemList = aCheckList.getCheckListItemList();
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

			if ((revRem != null) && !revRem.equals("")) {
				resultMap.put("sess.reversalRemarks", revRem);
			}
			else {
				resultMap.put("sess.reversalRemarks", (String) map.get("reversalRemarks"));
			}

			resultMap.put("docNos", outputDocIds);
			resultMap.put("custodianTrxVal", custodianTrxVal);
            resultMap.put("sess.trxId", trxID);
            resultMap.put("sess.checkListItemRef", checkListItemRef);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		// DefaultLogger.debug(this, "Going out of doExecute() " + resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}