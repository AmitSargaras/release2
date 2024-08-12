package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.proxy.IGeographyProxyManager;

/**
 * This Class is used for Deleting Country This will Result in Delete of State
 * and City
 * 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 18/02/2011 02:37:00 $ Tag: $Name: $
 */

public class RemoveCountryCommand extends AbstractCommand {

	private ICountryProxyManager countryProxy;

	public ICountryProxyManager getCountryProxy() {
		return countryProxy;
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "countryId", "java.lang.String",
				REQUEST_SCOPE } });

	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here Deletion of Country is done which 
	 * will result in deleting the its States and its respective Cities.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		String countryId = (String) map.get("countryId");
		DefaultLogger.debug(this, "doExecute countryId" + countryId);
//		long id = Long.parseLong(countryId);
		try {
			long id = Long.parseLong(countryId);
//			getCountryProxy().deleteCountry(id);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing");
			cpe.initCause(e);
			throw cpe;
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
