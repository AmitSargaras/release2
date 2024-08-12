/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/RefreshDocLocationCommand.java,v 1.6 2004/04/07 03:05:43 hltan Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/04/07 03:05:43 $ Tag: $Name: $
 */

public class RefreshDocLocationCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", FORM_SCOPE },
				{ "docLocTrxValue", "com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue",
						SERVICE_SCOPE }, });
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
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", FORM_SCOPE },
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", SERVICE_SCOPE },
				{ "docLocTrxValue", "com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue",
						SERVICE_SCOPE }, });
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

		try {
			ICCDocumentLocation docLoc = (ICCDocumentLocation) map.get("docLocationObj");
			ICCDocumentLocationTrxValue docLocTrxValue = (ICCDocumentLocationTrxValue) map.get("docLocTrxValue");
			if (docLocTrxValue != null) {
				docLocTrxValue.setStagingCCDocumentLocation(docLoc);
				result.put("docLocTrxValue", docLocTrxValue);
			}
			DefaultLogger.debug(this, "docLocTrxValue: " + docLocTrxValue);
			DefaultLogger.debug(this, "docLocationObj: " + docLoc);

			result.put("docLocationObj", docLoc);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
