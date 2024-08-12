/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genreqwaiver;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generatereq.bus.GenerateRequestException;
import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.generatereq.proxy.GenerateRequestProxyManagerFactory;
import com.integrosys.cms.app.generatereq.proxy.IGenerateRequestProxyManager;
import com.integrosys.cms.app.generatereq.trx.IWaiverRequestTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/12/10 10:49:38 $ Tag: $Name: $
 */
public class GenerateReqWaiverCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public GenerateReqWaiverCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
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
		return (new String[][] {
				{ "waiverReq", "com.integrosys.cms.app.generatereq.bus.IWaiverRequest", SERVICE_SCOPE },
				{ "iTrxValue", "com.integrosys.cms.app.generatereq.trx.IWaiverRequestTrxValue", SERVICE_SCOPE },
				{ "waiverReq", "com.integrosys.cms.app.generatereq.bus.IWaiverRequest", FORM_SCOPE },
				{ "error", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			IGenerateRequestProxyManager proxy = GenerateRequestProxyManagerFactory.getProxyManager();
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			IWaiverRequestTrxValue iTrxValue = null;
			if (cust.getNonBorrowerInd()) {
				iTrxValue = proxy.getNewWaiverRequestTrxValue(cust);
			}
			else {
				ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				DefaultLogger.debug(this, "Limit profile " + limit);
				iTrxValue = proxy.getNewWaiverRequestTrxValue(limit, cust);
			}
			IWaiverRequest waiverReq = iTrxValue.getStagingWaiverRequest();
			resultMap.put("waiverReq", waiverReq);
			resultMap.put("iTrxValue", iTrxValue);

		}
		catch (GenerateRequestException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);

			if (ICMSErrorCodes.WAIVER_NOT_REQUIRED.equals(e.getErrorCode())
					|| ICMSErrorCodes.WAIVER_PENDING_TRX_EXIST.equals(e.getErrorCode())) {
				resultMap.put("error", e.getErrorCode());
			}
			else {
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
