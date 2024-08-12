package com.integrosys.cms.ui.bfltatparameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CountryList;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/19 08:18:43 $ Tag: $Name: $
 */
public class PrepareBflTatParametersCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Retrieving Country List ");
		try {
			// get country list and labels
			CountryList countries = CountryList.getInstance();
			Collection countryLabels = new ArrayList(countries.getCountryLabels());
			Collection countryValues = new ArrayList(countries.getCountryValues());

			resultMap.put("countryLabels", countryLabels);
			resultMap.put("countryValues", countryValues);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;

	}// end doExecute()

}// end class
