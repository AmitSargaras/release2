/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/AddHedginExtensionCommand.java,v 1.4 2004/06/18 13:12:24 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBHedgePriceExtension;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/06/18 13:12:24 $ Tag: $Name: $
 */
public class AddHedginExtensionCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hedgingObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "hedgingObj", "com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal", FORM_SCOPE }, });
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

		ICommodityDeal dealObj = (ICommodityDeal) map.get("hedgingObj");
		IHedgePriceExtension[] hedgeExtensionList = addHedgeExtension(dealObj.getHedgePriceExtension());
		if (hedgeExtensionList != null) {
			DefaultLogger.debug(this, "========= hedgeExtension length: " + hedgeExtensionList.length);
		}
		dealObj.setHedgePriceExtension(hedgeExtensionList);
		result.put("hedgingObj", dealObj);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	public static IHedgePriceExtension[] addHedgeExtension(IHedgePriceExtension[] existingArray) {
		int arrayLength = 0;
		if (existingArray != null) {
			arrayLength = existingArray.length;
		}
		IHedgePriceExtension[] newArray = new IHedgePriceExtension[arrayLength + 1];
		if (existingArray != null) {
			System.arraycopy(existingArray, 0, newArray, 0, arrayLength);
		}

		OBHedgePriceExtension newObj = new OBHedgePriceExtension();
		newObj.setExtensionID(ICMSConstant.LONG_INVALID_VALUE);
		if (arrayLength > 0) {
			newObj.setStartDate(newArray[arrayLength - 1].getEndDate());
		}
		newArray[arrayLength] = newObj;

		return newArray;
	}

}
