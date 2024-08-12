package com.integrosys.cms.ui.bfltatparameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.BFLTATParameterSummary;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/19 08:18:43 $ Tag: $Name: $
 */

public class ListBflTatParametersCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "countryCode", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "newBCAParamList", "java.util.List", REQUEST_SCOPE },
				{ "renewalBCAParamList", "java.util.List", REQUEST_SCOPE }, });
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

		try {
			String country = (String) map.get("countryCode");
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			List bcaParamList = limitProxy.getBFLTATParameter(country);
			Iterator bcaParamListIt = bcaParamList.iterator();
			ArrayList newBCAParamList = new ArrayList();
			ArrayList renewalBCAParamList = new ArrayList();

			// Split the list into 2: NEW and RENEWAL
			while (bcaParamListIt.hasNext()) {
				BFLTATParameterSummary summary = (BFLTATParameterSummary) bcaParamListIt.next();
				if (summary.getBcaType().equals("NEW")) {
					newBCAParamList.add(summary);
				}
				else { // this is for the case:
						// if(summary.getBcaType().equals("RENEWAL"))
					renewalBCAParamList.add(summary);
				}
			}

			resultMap.put("newBCAParamList", newBCAParamList);
			resultMap.put("renewalBCAParamList", renewalBCAParamList);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;

	}// end doExecute()

}
