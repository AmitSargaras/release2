/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/PrepareUserProcessCommand.java,v 1.4 2004/08/04 01:56:36 jhe Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;

/**
 * Description
 * 
 * @author $Author: jhe $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/04 01:56:36 $ Tag: $Name: $
 */
public class PrepareUserProcessCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "commodityDealTrxValue",
				"com.integrosys.cms.app.commodity.deal.trx.ICommodityDealTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "forwardID", "java.util.Collection", REQUEST_SCOPE },
				{ "forwardValue", "java.util.Collection", REQUEST_SCOPE }, });
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

		ICommodityDealTrxValue trxValue = (ICommodityDealTrxValue) map.get("commodityDealTrxValue");
		OBCMSTrxRouteInfo[] forwardList = trxValue.getNextRouteList();
		Collection forwardID = new ArrayList();
		Collection forwardValue = new ArrayList();
		if (forwardList != null) {
			DefaultLogger.debug(this, "forward list length: " + forwardList.length);
			for (int i = 0; i < forwardList.length; i++) {
				if (forwardList[i].getUserID() != null) {
					forwardID.add(forwardList[i].getUserID());
					forwardValue.add(forwardList[i].getUserName() + " (" + forwardList[i].getTeamName() + ")");
				}
			}
		}
		result.put("forwardID", forwardID);
		result.put("forwardValue", forwardValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
