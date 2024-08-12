/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/RemoveHedgingExtensionCommand.java,v 1.3 2004/06/16 10:42:23 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/16 10:42:23 $ Tag: $Name: $
 */
public class RemoveHedgingExtensionCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "hedgingObj", "java.util.HashMap", FORM_SCOPE }, });
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

		HashMap dealObjMap = (HashMap) map.get("hedgingObj");
		ICommodityDeal dealObj = (ICommodityDeal) dealObjMap.get("obj");
		String[] deleteArr = (String[]) dealObjMap.get("deleteArr");

		IHedgePriceExtension[] priceExtension = deleteArray(dealObj.getHedgePriceExtension(), deleteArr);
		dealObj.setHedgePriceExtension(priceExtension);

		result.put("hedgingObj", dealObj);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private IHedgePriceExtension[] deleteArray(IHedgePriceExtension[] oldArr, String[] chkDelete) {
		IHedgePriceExtension[] newList = null;
		if (chkDelete != null) {
			if (chkDelete.length <= oldArr.length) {
				int numDelete = 0;
				for (int i = 0; i < chkDelete.length; i++) {
					if (Integer.parseInt(chkDelete[i]) < oldArr.length) {
						numDelete++;
					}
				}
				if (numDelete != 0) {
					newList = new IHedgePriceExtension[oldArr.length - numDelete];
					int i = 0, j = 0;
					while (i < oldArr.length) {
						if ((j < chkDelete.length) && (Integer.parseInt(chkDelete[j]) == i)) {
							j++;
						}
						else {
							newList[i - j] = oldArr[i];
						}
						i++;
					}
				}
			}
		}
		else {
			newList = oldArr;
		}
		return newList;
	}
}
