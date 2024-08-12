/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/list/PrepareCommodityUOMCommand.java,v 1.2 2004/06/04 05:11:44 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.commodityglobal.CommodityCategoryList;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:44 $ Tag: $Name: $
 */

public class PrepareCommodityUOMCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "category", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "categoryID", "java.lang.String", REQUEST_SCOPE },
				{ "cateogryValue", "java.lang.String", REQUEST_SCOPE },
				{ "productTypeID", "java.lang.String", REQUEST_SCOPE },
				{ "productTypeValue", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		CommodityCategoryList categoryList = CommodityCategoryList.getInstance();
		result.put("categoryID", categoryList.getCommCategoryID());
		result.put("cateogryValue", categoryList.getCommCategoryValue());

		String category = (String) map.get("category");
		if (category == null) {
			result.put("productTypeID", new ArrayList());
			result.put("productTypeValue", new ArrayList());
		}
		else {
			result.put("productTypeID", categoryList.getCommProductID(category));
			result.put("productTypeValue", categoryList.getCommProductValue(category));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
