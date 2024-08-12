/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/RefreshPurchaseSaleCommand.java,v 1.1 2004/07/21 13:45:00 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/07/21 13:45:00 $ Tag: $Name: $
 */
public class RefreshPurchaseSaleCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "purchaseSaleObj",
				"com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "purchaseSaleObj",
				"com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails", FORM_SCOPE }, });
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

		IPurchaseAndSalesDetails purchaseSales = (IPurchaseAndSalesDetails) map.get("purchaseSaleObj");
		result.put("purchaseSaleObj", purchaseSales);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
