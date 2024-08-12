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
import com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.propertyparameters.trx.IPrPaTrxValue;
import com.integrosys.cms.app.propertyparameters.trx.OBPrPaTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Describe this class. Purpose: for Maker to delete existing Auto Valuation
 * Parameters Description: command that help the Maker to delete existing Auto
 * Valuation Parameters
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class DeleteAutoValuationParameterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public DeleteAutoValuationParameterCommand() {
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
				// {"autoValuationParameter",
				// "com.integrosys.cms.app.propertyparameters.bus.OBPropertyParameters"
				// ,FORM_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "remarks", "java.lang.String", REQUEST_SCOPE } });
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
			// OBPropertyParameters paramItem =
			// (OBPropertyParameters)map.get("autoValuationParameter");
			// OBPropertyParameters paramItem = new OBPropertyParameters();
			// DefaultLogger.debug(this, "valParam before delete "+ paramItem);

			String event = (String) map.get("event");
			DefaultLogger.debug(this, "event here : " + event);
			// if(event.equals("maker_confirm_resubmit_delete")){
			// }

			IPrPaProxyManager proxy = PrPaProxyManagerFactory.getProxyManager();
			IPrPaTrxValue iPrPaTrxValue = (OBPrPaTrxValue) map.get("IPrPaTrxValue");
			OBPropertyParameters paramItem = (OBPropertyParameters) iPrPaTrxValue.getPrPa();

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IPrPaTrxValue trxValue = new OBPrPaTrxValue();

			if (event.equals("maker_confirm_delete")) {
				// 'maker_confirm_delete'
				trxValue = proxy.makerDeleteDocumentLocation(ctx, iPrPaTrxValue, paramItem);
			}
			else {
				// 'maker_confirm_resubmit_delete'
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);

				trxValue = proxy.makerEditRejectedDocumentLocation(ctx, iPrPaTrxValue, paramItem);
			}

			resultMap.put("request.ITrxValue", trxValue);
			// DefaultLogger.debug(this,"History ID>>>>>>>>>>>>>"+paramTrxObj.
			// getCurrentTrxHistoryID());
			DefaultLogger.debug(this, "trxValue after update" + trxValue);

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
