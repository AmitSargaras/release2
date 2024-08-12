/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/RedirectMakerCommand.java,v 1.2 2005/09/09 11:56:07 hshii Exp $
 */

package com.integrosys.cms.ui.systemparameters;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue;

/**
 * Command class to reject a custodian doc state by checker..
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/09 11:56:07 $ Tag: $Name: $
 */
public class RedirectMakerCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RedirectMakerCommand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }, { "redirectMaker", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "entered RedirectMakerCommand" + "");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String trxID = (String) map.get("TrxId");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event= " + trxID);
		try {

			SystemParametersProxy proxy = new SystemParametersProxy();
			OBSystemParametersTrxValue businessParameterGroupTrxVal = (OBSystemParametersTrxValue) proxy
					.getBusinessParameterGroup(Long.parseLong(trxID.trim()));
			if (event != null) {
				if (event.equals("maker_edit_sysparams_redirect_read_rejected")) {
					if (businessParameterGroupTrxVal.getTransactionSubType().equals("SP")) {
						resultMap.put("redirectMaker", "SP");
					}
					else {
						resultMap.put("redirectMaker", "CR");
					}
				}
				else {
					if (businessParameterGroupTrxVal.getTransactionSubType().equals("SP")) {
						resultMap.put("redirectMakerClose", "SP");
					}
					else {
						resultMap.put("redirectMakerClose", "CR");
					}
				}

			}
			resultMap.put("event", event);
			resultMap.put("TrxId", trxID);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;

	}

}
