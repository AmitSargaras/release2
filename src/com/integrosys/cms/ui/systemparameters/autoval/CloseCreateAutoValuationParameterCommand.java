/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemparameters.autoval;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.OBPrPaTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to close the create request for Auto
 * Valuation Parameters Description: command that help the Maker to close the
 * create request for Auto Valuation Parameters
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class CloseCreateAutoValuationParameterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CloseCreateAutoValuationParameterCommand() {
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
				// {"OBPropertyParameters",
				// "com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters"
				// ,SERVICE_SCOPE},
				{ "IPrPaTrxValue", "com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
		// {"remarks","java.lang.String",REQUEST_SCOPE}
		});
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
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {

			// OBPropertyParameters obPropertyParameters =
			// (OBPropertyParameters)map.get("OBPropertyParameters");
			// DefaultLogger.debug(this,
			// "OBPropertyParameters before close"+obPropertyParameters);

			IPrPaProxyManager proxy = PrPaProxyManagerFactory.getProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IPrPaTrxValue iPrPaTrxValue = (OBPrPaTrxValue) map.get("IPrPaTrxValue");

			// String remarks = (String)map.get("remarks");
			// ctx.setRemarks(remarks);

			IPrPaTrxValue trxValue = proxy.makerCloseRejectedDocumentLocation(ctx, iPrPaTrxValue);

			resultMap.put("request.ITrxValue", trxValue);

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
