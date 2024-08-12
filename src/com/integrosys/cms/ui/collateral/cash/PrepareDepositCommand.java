/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/PrepareDepositCommand.java,v 1.2 2004/01/12 06:13:51 hshii Exp $
 */

package com.integrosys.cms.ui.collateral.cash;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.manualinput.customer.LEIDTypeList;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/01/12 06:13:51 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 2, 2003 Time: 1:26:03 PM
 * To change this template use Options | File Templates.
 */
public class PrepareDepositCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "itemType", "java.lang.String", REQUEST_SCOPE },
				{ "systemIdList", "java.util.List", SERVICE_SCOPE },
				{"systemId", "java.lang.String", REQUEST_SCOPE},
		};
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
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "systemIdList", "java.util.List", REQUEST_SCOPE },
				{"systemId", "java.lang.String", REQUEST_SCOPE},
				{"fdWebServiceFlag","java.lang.String",REQUEST_SCOPE},
				 { "facilityIdList", "java.util.List", REQUEST_SCOPE }		
				});
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
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String indexID = (String) map.get("indexID");
		// get the LE listing
		List list = (List) map.get("lienList");
		String event = (String) map.get("event");// || event.equals("prepare")
		if (event.equals("prepare_update_sub")) {
			list = null;
		}
		if (event.equals("prepare")) {
			String itemType = (String) map.get("itemType");
			if (itemType != null) {
				if (itemType.equals("DEPOSIT")) {
					list = null;
				}
			}
		}
		// Added to get FD_ENABLE column value from db: start
		// String fdWebServiceFlag="Y";
		String fdWebServiceFlag = CollateralDAOFactory.getDAO()
				.getFdEnableFlag();

		// Added to get FD_ENABLE column value from db: end
        result.put("facilityIdList", "");
		resultMap.put("systemIdList", (List) map.get("systemIdList"));
		resultMap.put("systemId", (String) map.get("systemId"));
		resultMap.put("lienList", list);
		result.put("indexID", indexID);
		resultMap.put("fdWebServiceFlag", fdWebServiceFlag); // added for
																// Webservice
																// call in case
																// of FD
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

}
