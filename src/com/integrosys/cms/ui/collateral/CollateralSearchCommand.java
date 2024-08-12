/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralSearchCommand.java,v 1.5 2006/08/04 08:58:41 jzhai Exp $
 */

package com.integrosys.cms.ui.collateral;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the search the security details based on some
 * search criteria
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/08/04 08:58:41 $ Tag: $Name: $
 */
public class CollateralSearchCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public CollateralSearchCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "collateralSearchCriteria", "com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralId", "java.lang.String", REQUEST_SCOPE },
				{ "securityID", "java.lang.String", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_COLLATERALSEARCH_OBJ,
						"com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria", GLOBAL_SCOPE },
				{ "session_searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },

				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "securityList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "securityList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				// {"collateralId", "java.lang.String", REQUEST_SCOPE},
				{ "session_searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				// {"Global_OBTrxContext",
				// "com.integrosys.cms.app.transaction.OBTrxContext",
				// GLOBAL_SCOPE},
				{ IGlobalConstant.GLOBAL_COLLATERALSEARCH_OBJ,
						"com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria", GLOBAL_SCOPE },

				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, "java.lang.String", GLOBAL_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Inside doExecute()");

		int totalPageForPagination = Integer.valueOf(PropertyManager.getValue("pagination.total.page")).intValue();
		int recordsPerPageForPagination = Integer.valueOf(PropertyManager.getValue("pagination.records.per.page"))
				.intValue();

		int totalAvalaibleRecordsForPages = recordsPerPageForPagination * totalPageForPagination;

		CollateralSearchCriteria globalSearch = (CollateralSearchCriteria) map
				.get(IGlobalConstant.GLOBAL_COLLATERALSEARCH_OBJ);
		CollateralSearchCriteria objSearch = (CollateralSearchCriteria) map.get("collateralSearchCriteria");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		String event = (String) map.get("event");
		String collateralId = (String) map.get("collateralId");
		String totalCount = (String) map.get("totalCount");
		String startIndex = (String) map.get("startIndex");
		String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);

		int stindex = 0;
		int globalStartIndexValue = 0;

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		SearchResult sr = null;

		try {

			DefaultLogger.debug(this, "Before Doing Search");
			ICollateralProxy securityproxy = CollateralProxyFactory.getProxy();

			if (!isEmpty(startIndex)) {
				stindex = Integer.parseInt(startIndex);
			}

			if (stindex % totalAvalaibleRecordsForPages == 0) {
				objSearch.setTotalCountForCurrentTotalPages(null);
			}
			else {
				if (totalCount != null && Integer.valueOf(totalCount).intValue() > 0) {
					objSearch.setTotalCountForCurrentTotalPages(Integer.valueOf(totalCount));
				}
			}

			if (!isEmpty(globalStartIndex)) {
				globalStartIndexValue = Integer.parseInt(globalStartIndex);
			}

			objSearch.setStartIndex(stindex);

			if ("list1".equals(event)) {
				DefaultLogger.debug(this, "From Return Page with collateralId [" + collateralId
						+ "], globalStartIndex [" + globalStartIndex + "]");

				startIndex = String.valueOf(globalStartIndex);

				globalSearch.setStartIndex(globalStartIndexValue);
				globalSearch.setNItems(recordsPerPageForPagination);
				sr = securityproxy.searchCollateral(theOBTrxContext, globalSearch);

			}
			else if (!EVENT_SEARCH.equals(event)) {
				objSearch.setNItems(recordsPerPageForPagination);
				sr = securityproxy.searchCollateral(theOBTrxContext, objSearch);
				result.put(IGlobalConstant.GLOBAL_COLLATERALSEARCH_OBJ, objSearch);
			}

			result.put("startIndex", startIndex);
			result.put("securityList", sr);
			result.put("session_searchResult", sr);
			if (sr != null) {
				result.put("totalCount", Integer.toString(sr.getNItems()));
			}

			result.put(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX, startIndex);

		}
		catch (Exception e) {
			throw new CommandProcessingException("failed to search collateral", e);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}

	/**
	 * helper method to check empty
	 * @param aValue
	 * @return boolean
	 */

	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0) && !aValue.trim().equals("null")) {
			return false;
		}
		else {
			return true;
		}
	}

}
