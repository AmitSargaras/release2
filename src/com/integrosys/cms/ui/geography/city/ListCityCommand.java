package com.integrosys.cms.ui.geography.city;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 * This Class is used for showing list of Cities
 * 
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class ListCityCommand extends AbstractCommand {

	private ICityProxyManager cityProxy;
	
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public ListCityCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "type", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "text", "java.lang.String", REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "cityList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of City is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		SearchResult cityList =null;

		String type = (String) map.get("type");
		String searchText = (String) map.get("text");
		String startIdx = (String) map.get("startIndex");
		String event = (String) map.get("checkerEvent");
		
		DefaultLogger.debug(this, " doExecute : ListCityCommand ");
		try {
			if( searchText!= null && ! searchText.trim().equals("") ){
				boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchText);
				if( searchTextFlag == true){
					exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter","Country"));
					searchText= "";
				}
			}
			 cityList = getCityProxy().listCity(type, searchText);
			resultMap.put("cityList", cityList);
			resultMap.put("startIndex", startIdx);
			resultMap.put("checkerEvent", event);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing '" + cityList + "'");
			cpe.initCause(e);
			throw cpe;
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
