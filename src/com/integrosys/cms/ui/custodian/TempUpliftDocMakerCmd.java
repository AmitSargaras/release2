/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.bus.ICustodianDoc;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.custodian.trx.OBCustodianTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class to temp uplift custodian doc by maker..
 * @author $Author: vishal $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/07/25 18:28:13 $ Tag: $Name: $
 */
public class TempUpliftDocMakerCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public TempUpliftDocMakerCmd() {
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
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				// CR - 34
				{ "forId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRef", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
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
				{ "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE },
				// CR-34 modification
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "forId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListItemRefList", "java.util.ArrayList", SERVICE_SCOPE },// CR
																					// -
																					// 107
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		ICustodianTrxValue lodgeCustodianTrxVal = (OBCustodianTrxValue) map.get("custodianTrxVal");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//theOBTrxContext.setTrxCountryOrigin(lodgeCustodianTrxVal.getTrxContext
		// ().getTrxCountryOrigin());
		ICustodianDoc custDoc = (ICustodianDoc) map.get("aCustodianDoc");
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			// CR-34 modification
			/*
			 * theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getTrxCountry(theOBTrxContext
			 * .getLimitProfile(), custDoc.getCheckListID()));
			 * ICustodianProxyManager proxy =
			 * CustodianProxyManagerFactory.getCustodianProxyManager();
			 * ICMSTrxValue trxValue =
			 * proxy.tempUplift(theOBTrxContext,lodgeCustodianTrxVal,custDoc);
			 * resultMap.put("request.ITrxValue",trxValue);
			 */
			resultMap.put("custodianTrxVal", lodgeCustodianTrxVal);
			String forID = (String) map.get("forId");
			String checkListItemRef = (String) map.get("checkListItemRef");
			ArrayList checkListItemRefList = (java.util.ArrayList) map.get("checkListItemRefList");// CR
																									// -
																									// 107
			if ((checkListItemRefList == null) || checkListItemRefList.isEmpty()) {
				checkListItemRefList = new ArrayList();
				checkListItemRefList.add(checkListItemRef);
			}
			else {
				checkListItemRefList.add(checkListItemRef);
			}
			resultMap.put("forId", forID);
			resultMap.put("checkListItemRefList", checkListItemRefList);//CR-107
			// CR-34 modification
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