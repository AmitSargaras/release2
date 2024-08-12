/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
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
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ITATEntry;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Command class to reject a custodian doc state by checker..
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/11/09 10:21:11 $ Tag: $Name: $
 */
public class ConfirmSpecialIssueCleanTypeCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ConfirmSpecialIssueCleanTypeCommand() {
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
				{ "tatEntry", "com.integrosys.cms.app.limit.bus.ITATEntry", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		try {
			ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			if (null == limitProfileOB) {
				throw new CommandProcessingException("ILimitProfile is null in session!");
			}
			long limitProfileID = limitProfileOB.getLimitProfileID();
			String limitprofileid = Long.toString(limitProfileID);
			ILimitProxy limitproxy = LimitProxyFactory.getProxy();
			ILimitProfileTrxValue limitprofiletrxvalue = new OBLimitProfileTrxValue();
			DefaultLogger.debug(this, "limitprofileID is " + limitprofileid);
			limitprofiletrxvalue = limitproxy.getTrxLimitProfile(Long.parseLong(limitprofileid));
			// added by Jitendra...CMS-3615
			if ((limitprofiletrxvalue.getLimitProfile() != null)
					&& limitprofiletrxvalue.getLimitProfile().getBFLRequiredInd()) {
			}
			else {
				LimitException e = new LimitException("Invalid TAT Entry! BFL is not Required ");
				e.setErrorCode(ICMSErrorCodes.BFL_ISSUE_NOT_REQUIRED);
				throw e;
			}
			// end of ..CMS-3615
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ITATEntry tatEntry = (ITATEntry) map.get("tatEntry");
			theOBTrxContext.setTrxCountryOrigin(limitprofiletrxvalue.getLimitProfile().getOriginatingLocation()
					.getCountryCode());
			theOBTrxContext.setTrxOrganisationOrigin(limitprofiletrxvalue.getLimitProfile().getOriginatingLocation()
					.getOrganisationCode());
			ICMSTrxResult resultOb = limitproxy.specialIssueCleanTypeBFL(theOBTrxContext, limitprofiletrxvalue,
					tatEntry);
			resultMap.put("request.ITrxResult", resultOb);

		}
		catch (LimitException e) {
			DefaultLogger.error(this, "Caught LimitException!", e);

			String errorCode = e.getErrorCode();
			DefaultLogger.debug(this, "Error Code in Special Issue Clean Type Command: " + errorCode);
			if (null != errorCode) {
				throw new AccessDeniedException("Special Issue Not Allowed!", e);
			}
			else {
				throw new CommandProcessingException(e.getMessage());
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
