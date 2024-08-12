/**
 * 
 */
package com.integrosys.cms.ui.discrepency;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerDeleteDiscrepencyCommand extends AbstractCommand {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerDeleteDiscrepencyCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",
						FORM_SCOPE },
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{ "request.ITrxValue",
						"com.integrosys.cms.app.transaction.ICMSTrxValue",
						REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {

			IDiscrepency discrepency = (OBDiscrepency) map.get("discrepencyObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyTrxValue trxValueIn = (OBDiscrepencyTrxValue) map
					.get("IDiscrepencyTrxValue");
			IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();

			if (event.equals("maker_delete_discrepency")) {
				trxValueOut = getDiscrepencyProxy().makerDeleteDiscrepency(ctx, trxValueIn,
						discrepency);
			} else if (event.equals("maker_activate_discrepency")) {
				trxValueOut = getDiscrepencyProxy().makerActivateDiscrepency(ctx, trxValueIn,
						discrepency);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getDiscrepencyProxy().makerEditRejectedDiscrepency(ctx,
						trxValueIn, discrepency);
			}
			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (NoSuchGeographyException obe) {
			CommandProcessingException cpe = new CommandProcessingException(obe
					.getMessage());
			cpe.initCause(obe);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
