/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/PrepareCommodityUOMItemCommand.java,v 1.2 2004/06/04 05:11:44 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.commodityglobal.commodityuom.MarketUOMList;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:44 $ Tag: $Name: $
 */

public class PrepareCommodityUOMItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityCategory", "java.lang.String", SERVICE_SCOPE },
				{ "commodityProductType", "java.lang.String", SERVICE_SCOPE },
				{ "productSubTypeMap", "java.util.Map", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "productSubTypeID", "java.util.Collection", REQUEST_SCOPE },
				{ "productSubTypeValue", "java.util.Collection", REQUEST_SCOPE },
				{ "marketUOMID", "java.util.Collection", REQUEST_SCOPE },
				{ "marketUOMValue", "java.util.Collection", REQUEST_SCOPE },
				{ "metricUOMID", "java.util.Collection", REQUEST_SCOPE },
				{ "metricUOMValue", "java.util.Collection", REQUEST_SCOPE } });
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

		Collection productSubTypeID = new ArrayList();
		Collection productSubTypeValue = new ArrayList();

		try {
			Map productSubType = (Map) map.get("productSubTypeMap");
			if (productSubType != null) {
				productSubTypeID = productSubType.keySet();
				Iterator itr = productSubTypeID.iterator();
				while (itr.hasNext()) {
					productSubTypeValue.add(productSubType.get(itr.next()));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}
		result.put("productSubTypeID", productSubTypeID);
		result.put("productSubTypeValue", productSubTypeValue);

		MarketUOMList uomList = MarketUOMList.getInstance();
		result.put("marketUOMID", uomList.getMarketUOMID());
		result.put("marketUOMValue", uomList.getMarketUOMValue());

		result.put("metricUOMID", uomList.getMetricUOMID());
		result.put("metricUOMValue", uomList.getMetricUOMValue());

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
