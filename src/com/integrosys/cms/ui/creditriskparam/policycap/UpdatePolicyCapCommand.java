/**
 * Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.creditriskparam.policycap;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class UpdatePolicyCapCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UpdatePolicyCapCommand() {
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
				{ "policyCapMap", "java.util.HashMap", FORM_SCOPE }, // Collection
																		// of
																		// com.
																		// integrosys
																		// .cms.
																		// app.
																		// creditriskparam
																		// .bus.
																		// policycap
																		// .
																		// OBPolicyCap
				{ "policyCapTrxValue", "com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue",
						SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Updates to the policy cap is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> In Update Command!");

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		IPolicyCapTrxValue policyCapsTrxVal = (IPolicyCapTrxValue) map.get("policyCapTrxValue");
		DefaultLogger.debug(this, ">>>>>>>>>>> trxVal:\n" + policyCapsTrxVal.getTransactionID());
		HashMap policyCapMap = (HashMap) map.get("policyCapMap");
		DefaultLogger.debug(this, ">>>>>>>>>>> policyCapMap: " + policyCapMap);
		IPolicyCap[] obPolicyCaps = (IPolicyCap[]) policyCapMap.get("policyCapList");
		DefaultLogger.debug(this, ">>>>>>>>>>> obPolicyCaps: " + obPolicyCaps);
		IPolicyCapProxyManager proxy = PolicyCapProxyManagerFactory.getPolicyCapProxyManager();
		DefaultLogger.debug(this, ">>>>>>>>>>> proxy: " + proxy);

		try {
			IPolicyCapTrxValue trxValue = proxy.makerUpdatePolicyCap(trxContext, policyCapsTrxVal, obPolicyCaps);
			DefaultLogger.debug(this, "trxValue=" + trxValue);
			resultMap.put("PolicyCapTrxValue", trxValue);
			DefaultLogger.debug(this, "PolicyCapTrxValue");
			resultMap.put("request.ITrxValue", trxValue);
			DefaultLogger.debug(this, "request.ITrxValue");
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
