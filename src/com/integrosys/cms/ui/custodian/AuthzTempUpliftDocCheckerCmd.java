/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.custodian.trx.ICustodianTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: jychong $<br>
 * @version $Revision: 1.4.12.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class AuthzTempUpliftDocCheckerCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AuthzTempUpliftDocCheckerCmd() {
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
				{ "custodianTrxVal", "com.integrosys.cms.app.custodian.trx.ICustodianTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {});
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
		ICustodianTrxValue iCustodianTrxValue = (ICustodianTrxValue) map.get("custodianTrxVal");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		//theOBTrxContext.setTrxCountryOrigin(iCustodianTrxValue.getTrxContext()
		// .getTrxCountryOrigin());
		DefaultLogger.debug(this, "Inside doExecute() >> NewCustodianDoc  = " + iCustodianTrxValue);
		try {
			// theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getTrxCountry(
			// theOBTrxContext.getLimitProfile(),
			// iCustodianTrxValue.getCustodianDoc().getCheckListID()));
			theOBTrxContext.setTrxCountryOrigin(CheckListUtil.getCustodianTrxCountry(theOBTrxContext.getTeam(),
					theOBTrxContext.getLimitProfile(), theOBTrxContext.getCustomer(), iCustodianTrxValue
							.getCustodianDoc().getCheckListID()));
			ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			proxy.authzTempUpliftChecker(theOBTrxContext, iCustodianTrxValue);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		return returnMap;
	}
}
