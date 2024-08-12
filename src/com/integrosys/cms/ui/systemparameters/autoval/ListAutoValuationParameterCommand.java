/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemparameters.autoval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.ui.common.SecuritySubTypeList;

/**
 * Describe this class. Purpose: for Maker to view the list of Auto Valuation
 * Parameters Description: command that help the Maker to view the list of Auto
 * Valuation Parameters
 * 
 * @author $Author:$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name:$
 */

public class ListAutoValuationParameterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListAutoValuationParameterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "autoValParamList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "collateralSubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			ArrayList autoValuationParamList = new ArrayList();
			SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();

			IPrPaProxyManager proxy = PrPaProxyManagerFactory.getProxyManager();
			autoValuationParamList = (ArrayList) proxy.getAllActural();

			// get list for security sub type
			Collection fullList = subTypeList.getSecuritySubTypeProperty();
			ArrayList collateralSubTypeFullList = new ArrayList();
			ArrayList collateralSubTypeFullListLabel = new ArrayList();

			// String[][] itemList = new String[0][0];

			setSecuritySubTypeToList(fullList, collateralSubTypeFullList, collateralSubTypeFullListLabel, subTypeList,
					locale);

			resultMap.put("collateralSubTypeFullList", collateralSubTypeFullList);
			resultMap.put("collateralSubTypeFullListLabel", collateralSubTypeFullListLabel);
			resultMap.put("autoValParamList", autoValuationParamList);
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

	private void setSecuritySubTypeToList(Collection fullList, ArrayList listValue, ArrayList listLabel,
			SecuritySubTypeList subTypeList, Locale locale) {
		// filter out sub type for property type only
		Iterator iter = fullList.iterator();
		HashMap subTypeLabelValMap = new HashMap();
		while (iter.hasNext()) {
			String subType = (String) iter.next();
			if ((subType != null) && (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
				subTypeLabelValMap.put(subTypeList.getSecuritySubTypeValue(subType, locale), subType);
			}
		}

		// sort the list by label
		String[] subTypeLabel = (String[]) subTypeLabelValMap.keySet().toArray(new String[0]);
		Arrays.sort(subTypeLabel);

		listLabel.addAll(new ArrayList(Arrays.asList(subTypeLabel)));
		for (int i = 0; i < subTypeLabel.length; i++) {
			listValue.add(subTypeLabelValMap.get(subTypeLabel[i]));
		}
	}

}
