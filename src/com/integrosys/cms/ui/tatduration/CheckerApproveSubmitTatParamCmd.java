package com.integrosys.cms.ui.tatduration;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * Title: CLIMS Description: for Checker to approve the update request for
 * Property Index Copyright: Integro Technologies Sdn Bhd Author: Andy Wong
 * Date: Feb 12, 2008
 */

public class CheckerApproveSubmitTatParamCmd extends TatDurationCommand
		implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerApproveSubmitTatParamCmd()
	{
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
				{"ITatParamTrxValue","com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue",	SERVICE_SCOPE },
				{"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException 
	{
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		try
		{
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ITatParamTrxValue trxValueIn = (ITatParamTrxValue) map.get("ITatParamTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);

			ITatParamTrxValue trxValueOut = getTatParamProxy().checkerApproveTatParam(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		} 
		catch (Exception e) 
		{
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
