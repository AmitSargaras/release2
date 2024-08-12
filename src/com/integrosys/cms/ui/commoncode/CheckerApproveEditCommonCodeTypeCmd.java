/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/CheckerApproveEditSystemParametersCmd.java,v 1.2 2003/09/05 06:01:21 dayanand Exp $
 */

package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.proxy.CommonCodeTypeManagerFactory;
import com.integrosys.cms.app.commoncode.proxy.ICommonCodeTypeProxy;
import com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Command class for CheckerApproval of Edit(SystemParameters)
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/05 06:01:21 $ Tag: $Name: $
 */
public class CheckerApproveEditCommonCodeTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditCommonCodeTypeCmd() {
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
				{ "OBCommonCodeTypeTrxValue", "com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue",
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
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
		OBCommonCodeTypeTrxValue obCommonCodeTypeTrxValue = (OBCommonCodeTypeTrxValue) map
				.get("OBCommonCodeTypeTrxValue");
		try {
			ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();
			proxy.checkerApproveCategory(trxContext, obCommonCodeTypeTrxValue);
			resultMap.put("request.ITrxValue", obCommonCodeTypeTrxValue);
		}
		catch (CommonCodeTypeException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
