package com.integrosys.cms.ui.contractfinancing;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancingSummary;
import com.integrosys.cms.app.contractfinancing.proxy.ContractFinancingProxyManagerFactory;
import com.integrosys.cms.app.contractfinancing.proxy.IContractFinancingProxyManager;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ListContractFinancingSummaryCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ListContractFinancingSummaryCommand() {
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
		// {"theOBTrxContext",
		// "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
		{ "limitProfileID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "contractFinancingSummary", "java.util.HashMap", SERVICE_SCOPE }, });
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

		IContractFinancingSummary[] contractFinancingSummary;
		try {

			IContractFinancingProxyManager proxy = ContractFinancingProxyManagerFactory
					.getContractFinancingProxyManager();

			String limitProfileID = (String) map.get("limitProfileID");
			contractFinancingSummary = proxy.getContractFinancingSummaryList(Long.parseLong(limitProfileID));

			resultMap.put("contractFinancingSummary", contractFinancingSummary);

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
