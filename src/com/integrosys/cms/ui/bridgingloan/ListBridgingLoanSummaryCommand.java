package com.integrosys.cms.ui.bridgingloan;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoanSummary;
import com.integrosys.cms.app.bridgingloan.proxy.BridgingLoanProxyManagerFactory;
import com.integrosys.cms.app.bridgingloan.proxy.IBridgingLoanProxyManager;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class ListBridgingLoanSummaryCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ListBridgingLoanSummaryCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "limitProfileID", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "bridgingLoanSummary", "com.integrosys.cms.ui.bridgingloan.BridgingLoanAction",
				SERVICE_SCOPE }, });
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
		DefaultLogger.debug(this, ">>>>>>>> Inside ListBridgingLoanSummaryCommand doExecute()  ");

		try {
			IBridgingLoanProxyManager proxy = BridgingLoanProxyManagerFactory.getBridgingLoanProxyManager();
			String limitProfileID = (String) map.get("limitProfileID");
			DefaultLogger.debug(this, "limitProfileID: " + limitProfileID);
			IBridgingLoanSummary[] bridgingLoanSummary = proxy.getBridgingLoanSummaryList(Long
					.parseLong(limitProfileID));
			resultMap.put("bridgingLoanSummary", bridgingLoanSummary);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e.toString());
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}