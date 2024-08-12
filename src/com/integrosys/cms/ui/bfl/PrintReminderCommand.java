/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/bfl/PrintReminderCommand.java,v 1.7 2006/11/09 10:21:11 jitendra Exp $
 */
package com.integrosys.cms.ui.bfl;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class is used to list the company borrowers based on some search
 * contsraints
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/11/09 10:21:11 $ Tag: $Name: $
 */
public class PrintReminderCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrintReminderCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "tatEntry", "com.integrosys.cms.app.limit.bus.ITATEntry", FORM_SCOPE },
				{ "cpcchecker", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE },
				{ "trxValue", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		String event = (String) map.get("event");

		DefaultLogger.debug(this, "Inside doExecute() with event:" + event);

		try {
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			if (null == custOB) {
				throw new CommandProcessingException("ICMSCustomer is null in session!");
			}
			result.put("customerOb", custOB);

			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			if (null != limitProfileOB) {
				ILimitProfileTrxValue lpTrx = limitProxy.getTrxLimitProfile(limitProfileOB.getLimitProfileID());
				// added by Jitendra...CMS-3615
				if ((lpTrx.getLimitProfile() != null) && lpTrx.getLimitProfile().getBFLRequiredInd()) {
				}
				else {
					LimitException e = new LimitException("Invalid TAT Entry! BFL is not Required ");
					e.setErrorCode(ICMSErrorCodes.BFL_ISSUE_NOT_REQUIRED);
					throw e;
				}
				// end of ..CMS-3615
				result.put("trxValue", lpTrx);
				result.put("limitprofileOb", lpTrx.getLimitProfile());

				String cpcchecker = (String) map.get("cpcchecker");
				if ((cpcchecker != null) && cpcchecker.equals("true") && (event != null)
						&& event.equals("confirm_print_bfl_reminder")) {
					OBTrxContext trxCtx = (OBTrxContext) map.get("theOBTrxContext");
					if (trxCtx == null) {
						throw new CommandProcessingException("Transaction Context is null");
					}
					ITATEntry tatEntry = (ITATEntry) map.get("tatEntry");
					trxCtx.setTrxCountryOrigin(lpTrx.getLimitProfile().getOriginatingLocation().getCountryCode());
					trxCtx.setTrxOrganisationOrigin(lpTrx.getLimitProfile().getOriginatingLocation()
							.getOrganisationCode());
					ICMSTrxResult trxResult = limitProxy.printReminderBFL(trxCtx, lpTrx, tatEntry);
					result.put("request.ITrxResult", trxResult);
				}
			}
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
		}
		catch (LimitException e) {
			DefaultLogger.error(this, "Caught LimitException!", e);

			if (null != e.getErrorCode()) {
				throw new AccessDeniedException("Caught AccessDeniedException!", e);
			}
			else {
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "got exception in doExecute", e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}
}