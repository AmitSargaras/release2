/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/CreateDocLocationCommand.java,v 1.7 2005/10/24 08:12:17 hshii Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.documentlocation.proxy.DocumentLocationProxyManagerFactory;
import com.integrosys.cms.app.documentlocation.proxy.IDocumentLocationProxyManager;
import com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/24 08:12:17 $ Tag: $Name: $
 */

public class CreateDocLocationCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "session.docOriginCountry", "java.lang.String", SERVICE_SCOPE },
				{ "docLocationObj", "com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", FORM_SCOPE },
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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.documentlocation.trx.ICCDocumentLocationTrxValue", REQUEST_SCOPE }, });
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

		IDocumentLocationProxyManager proxyManager = DocumentLocationProxyManagerFactory.getProxyManager();

		try {
			String ccOriginLocation = (String) map.get("session.docOriginCountry");
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			ICCDocumentLocation docLoc = (ICCDocumentLocation) map.get("docLocationObj");
			if (customer.getNonBorrowerInd()) {
				theOBTrxContext.setTrxCountryOrigin(DocumentLocationUtil.getDocumentLocationTrxCountry(theOBTrxContext,
						ccOriginLocation, null, customer));
			}
			else {
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				theOBTrxContext.setTrxCountryOrigin(DocumentLocationUtil.getDocumentLocationTrxCountry(theOBTrxContext,
						ccOriginLocation, limit, null));
			}

			ICCDocumentLocationTrxValue docLocTrxValue = proxyManager.makerCreateDocumentLocation(theOBTrxContext,
					docLoc);

			result.put("request.ITrxValue", docLocTrxValue);
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
