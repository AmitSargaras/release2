/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to delete the value Description:
 * command that let the maker to set the status to deleted to the database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: $
 * @since $Date:$ Tag: $Name$
 */

public class DeleteAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
				{ "cannotDeleteAA", "java.lang.String", REQUEST_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBLimitProfile obLimitProfile = (OBLimitProfile) map.get("InitialLimitProfile");

		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		limitProfileTrxVal.setTransactionSubType(ICMSConstant.MANUAL_INPUT_TRX_TYPE);
		DefaultLogger.debug(this, "Inside doExecute()  before PROXY CALL the busValue 'obLimitProfile' = "
				+ obLimitProfile);
		DefaultLogger.debug(this, "Inside doExecute() trxContext  = " + trxContext);

		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();

			ICMSTrxResult res = proxy.makerDeleteLimitProfile(trxContext, limitProfileTrxVal);
			resultMap.put("request.ITrxResult", res);
			resultMap.put("limitProfileTrxVal", limitProfileTrxVal);

		}
		catch (LimitException e) {

			if ((e.getErrorCode() != null) && e.getErrorCode().equals(LimitException.ERR_CANNOT_DELETE_LMT_PROFILE)) {
				resultMap.put("cannotDeleteAA", "cannotDeleteAA");
				resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
				resultMap.put("InitialLimitProfile", limitProfileTrxVal.getLimitProfile());

			}
			else {

				DefaultLogger.debug(this, "got exception in doExecute" + e);
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}

		DefaultLogger.debug(this, "Skipping ...");
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
