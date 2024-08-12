/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/PrepareDocLocationListCommand.java,v 1.7 2005/09/08 09:04:10 hshii Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.documentlocation.bus.CCDocumentLocationSummary;
import com.integrosys.cms.app.documentlocation.bus.DocumentLocationException;
import com.integrosys.cms.app.documentlocation.proxy.DocumentLocationProxyManagerFactory;
import com.integrosys.cms.app.documentlocation.proxy.IDocumentLocationProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/09/08 09:04:10 $ Tag: $Name: $
 */

public class PrepareDocLocationListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "docLocationList", "java.util.Collection", SERVICE_SCOPE }, });
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

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		CCDocumentLocationSummary[] documentList = null;
		IDocumentLocationProxyManager proxyManager = DocumentLocationProxyManagerFactory.getProxyManager();
		// DefaultLogger.debug(this,
		// "<<<<<<<<< OBTrxContext: "+theOBTrxContext);
		if (limit != null) {
			try {
				documentList = proxyManager.getCCSummaryList(theOBTrxContext, limit);
			}
			catch (DocumentLocationException e) {
				if (ICMSErrorCodes.CC_DOC_LOC_NOT_REQUIRED.equals(e.getErrorCode())) {
					result.put("doc_loc", "doc_loc_not_required");
				}
				else {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}
		else {
			try {
				documentList = proxyManager.getCCSummaryList(theOBTrxContext, customer);
			}
			catch (DocumentLocationException e) {
				if (ICMSErrorCodes.CC_DOC_LOC_NOT_REQUIRED.equals(e.getErrorCode())) {
					result.put("doc_loc", "doc_loc_not_required");
				}
				else {
					e.printStackTrace();
					throw new CommandProcessingException(e.getMessage());
				}
			}
		}
		DefaultLogger.debug(this, "is update documentation originatin location: " + result.get("doc_loc"));
		if (documentList != null) {
			result.put("docLocationList", Arrays.asList(documentList));
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
