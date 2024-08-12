/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/MakerCnclEditCmd.java,v 1.2 2005/09/09 11:56:07 hshii Exp $
 */

package com.integrosys.cms.ui.systemparameters;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemparameters.proxy.SystemParametersProxy;
import com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.component.commondata.app.bus.CommonDataManagerException;

/**
 * Command class to Cancel Edited SystemParameters by maker
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/09/09 11:56:07 $ Tag: $Name: $
 */
public class MakerCnclEditCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerCnclEditCmd() {
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
				{ "SystemParametersTrxValue", "com.integrosys.cms.app.systemparameters.trx.OBSystemParametersTrxValue",
						SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxResult", "com.integrosys.base.businfra.transaction.ITrxResult",
				REQUEST_SCOPE } });
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
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBSystemParametersTrxValue systemParametersTrxVal = (OBSystemParametersTrxValue) map
				.get("SystemParametersTrxValue");
		DefaultLogger.debug(this, "Inside doExecute()  = " + trxContext);
		try {
			SystemParametersProxy proxy = new SystemParametersProxy();
			resultMap.put("request.ITrxResult", proxy.makerCancelUpdate(trxContext, systemParametersTrxVal));
		}
		catch (CommonDataManagerException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
