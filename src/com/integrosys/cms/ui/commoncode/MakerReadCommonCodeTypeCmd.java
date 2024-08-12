/**
 * Copyright Integro Technologies Pte Ltd
 * $$
 */

package com.integrosys.cms.ui.commoncode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncode.Constants;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchCriteria;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeSearchResultItem;
import com.integrosys.cms.app.commoncode.proxy.CommonCodeTypeManagerFactory;
import com.integrosys.cms.app.commoncode.proxy.ICommonCodeTypeProxy;

/**
 * @author $Author: marvin $<br>
 * @version $$
 * @since $$
 */
public class MakerReadCommonCodeTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerReadCommonCodeTypeCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE } });
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
				{ "CommonCodeTypeTrxValue", "com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue",
						SERVICE_SCOPE },
				{ "CommonCodeTypeGroup", "com.integrosys.cms.app.commoncode.bus.OBCommonCodeTypeGroup", FORM_SCOPE },
				{ "commonCategoryList", "java.util.ArrayList", REQUEST_SCOPE }

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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ArrayList commonCategoryList = new ArrayList();
			ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();

			CommonCodeTypeSearchCriteria criteria = new CommonCodeTypeSearchCriteria();
			criteria.setCategoryType(Constants.CMS_DATA);
			criteria.setFirstSort("CATEGORY_NAME");
			SearchResult result = proxy.getCategoryList(criteria);
			if (result != null) {
				Collection col = result.getResultList();
				if (col != null) {
					commonCategoryList = sortTypes(col);
					// commonCategoryList = new ArrayList(col);
				}
			}
			resultMap.put("commonCategoryList", commonCategoryList);
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

	private ArrayList sortTypes(Collection collection) {
		ArrayList returnList = new ArrayList();

		if (collection != null) {
			// TreeMap map = new TreeMap ();
			Iterator iterator = collection.iterator();

			while (iterator.hasNext()) {
				CommonCodeTypeSearchResultItem item = (CommonCodeTypeSearchResultItem) iterator.next();
				// map.put (item.getCommonCategoryCode (), item);

				returnList.add(item);
			}

		}

		return returnList;
	}

}
