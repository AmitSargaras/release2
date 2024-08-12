/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.custodian;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.custodian.proxy.CustodianProxyManagerFactory;
import com.integrosys.cms.app.custodian.proxy.ICustodianProxyManager;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.securityenvelope.proxy.ISecEnvelopeProxyManager;
import com.integrosys.cms.app.securityenvelope.proxy.SecEnvelopeProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Command class to get the list of documents based on the document type set on
 * the search criteria
 * @author $Author: btan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/04/23 02:59:47 $ Tag: $Name: $
 */
public class ListWithdrawalMemoCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListWithdrawalMemoCmd() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
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
		return (new String[][] { 
				{ "docList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE }
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
		ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long bid = cust.getCustomerID();

		// bernard - added to retrieve IContext
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		if ((theOBTrxContext != null) && (theOBTrxContext.getUser() != null)) {
			DefaultLogger.debug(this, "loginID=" + theOBTrxContext.getUser().getLoginID());
			ITeam team = theOBTrxContext.getTeam();
			DefaultLogger.debug(this, "Team abbreviation=" + team.getAbbreviation());
			String[] countryCodes = team.getCountryCodes();
		}
		else {
			DefaultLogger.debug(this, "Login ID is null!");
		}

		try {
			ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
			
			SearchResult sr = null;
			List secEnvListing = null;
			if (cust.getNonBorrowerInd()) {
				if (limitProfile != null) {
					sr = proxy.getPendingWithdrawalListForNonBorrower(theOBTrxContext,limitProfile.getLimitProfileID(), cust.getCustomerID());

					
				}
				else {
					sr = proxy.getPendingWithdrawalListForNonBorrower(theOBTrxContext, cust.getCustomerID());
				}
			}
			else {
				DefaultLogger.debug(this, "Inside doExecute() searchCriteria = " + limitProfile.getLimitProfileID());
				sr = proxy.getPendingWithdrawalList(theOBTrxContext, limitProfile.getLimitProfileID());
			}
			resultMap.put("docList", sr);
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
