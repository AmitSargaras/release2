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
import com.integrosys.cms.ui.common.CurrencyList;

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
public class PrepareLienDepositCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return ( new String[][] {
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },

	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
	            {"newEvent", "java.lang.String", REQUEST_SCOPE},
			});

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
				{ "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "lienList", "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE  },
				
				{ "collateralList", "java.util.List", REQUEST_SCOPE },
	            { "systemBankBranch", "com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch", REQUEST_SCOPE },
	            { "countryNme", "java.lang.String", REQUEST_SCOPE },
	            
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"systemId", "java.lang.String", REQUEST_SCOPE},
	            {"newEvent", "java.lang.String", REQUEST_SCOPE},
	            {"facilityName", "java.lang.String", REQUEST_SCOPE},
	            {"facilityId", "java.lang.String", REQUEST_SCOPE},
	            {"lcnNo", "java.lang.String", REQUEST_SCOPE},
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
			HashMap returnMap = new HashMap();
			String indexID = (String) map.get("indexID");
			String event = (String )map.get("event");
			List list = (List)map.get("lienList");
			result.put("lienList", list);
			result.put("event", event);
			result.put("indexID", indexID);
			
			result.put("systemName", map.get("systemName"));
			result.put("systemId", map.get("systemId"));
			result.put("facilityName", "");
			result.put("facilityId", "");
			result.put("lcnNo", "");
		    result.put("collateralList", map.get("collateralList"));
			result.put("systemBankBranch", map.get("systemBankBranch"));
			result.put("countryNme", map.get("countryNme"));
			result.put("newEvent", map.get("newEvent"));
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			return returnMap;
	}

}
