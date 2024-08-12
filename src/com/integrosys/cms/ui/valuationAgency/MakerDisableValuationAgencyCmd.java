package com.integrosys.cms.ui.valuationAgency;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency;
import com.integrosys.cms.app.valuationAgency.bus.ValuationAgencyException;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;

/**
 * @author rajib.aich For Valuation Agency Command for checker to approve edit .
 */
public class MakerDisableValuationAgencyCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IValuationAgencyProxyManager valuationAgencyProxy;

	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}

	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerDisableValuationAgencyCmd() {
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
				{
						"IValuationAgencyTrxValue",
						"com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{
						"valuationObj",
						"com.integrosys.cms.app.valuationAgency.bus.OBValuationAgency",
						FORM_SCOPE }

		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
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
		try {
			OBValuationAgency valuationAgency = (OBValuationAgency) map
					.get("valuationObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IValuationAgencyTrxValue trxValueIn = (OBValuationAgencyTrxValue) map
					.get("IValuationAgencyTrxValue");
			IValuationAgencyTrxValue trxValueOut = new OBValuationAgencyTrxValue();
			if (event.equals("maker_disable_valuationAgency")) {
				trxValueOut = getValuationAgencyProxy()
						.makerDisableValuationAgency(ctx, trxValueIn,
								valuationAgency);
			} else {
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getValuationAgencyProxy()
						.makerEditRejectedValuationAgency(ctx, trxValueIn,
								valuationAgency);
			}

			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (ValuationAgencyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
