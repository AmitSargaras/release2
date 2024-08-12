/**
 * Copyright Integro Technologies Pte Ltd 
 * $Header:
 */

package com.integrosys.cms.ui.manualinput.partygroup;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.partygroup.trx.OBPartyGroupTrxValue;

/**
 *@author $Author: Bharat waghela$ Command to read PartyGroup
 */
public class MakerPrepareCreatePartyGroupCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IPartyGroupProxyManager partyGroupProxy;

	public IPartyGroupProxyManager getPartyGroupProxy() {
		return partyGroupProxy;
	}

	public void setPartyGroupProxy(IPartyGroupProxyManager partyGroupProxy) {
		this.partyGroupProxy = partyGroupProxy;
	}

	/**
	 * Default Constructor
	 */
	public MakerPrepareCreatePartyGroupCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext",
				"com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }

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
		return (new String[][] { { "IPartyGroupTrxValue",
				"com.integrosys.cms.app.partyGroup.trx.OBPartyGroupTrxValue",
				SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String startIdx = (String) map.get("startIndex");

		OBPartyGroupTrxValue partyGroupTrxValue = new OBPartyGroupTrxValue();

		// resultMap.put("IPartyGroupTrxValue", partyGroupTrxValue);
		// resultMap.put("hubValueList",getHUBList() );
		resultMap.put("startIndex", startIdx);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}

}
