/**
 * 
 */
package com.integrosys.cms.ui.geography.country;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.country.bus.OBCountry;
import com.integrosys.cms.app.geography.country.proxy.ICountryProxyManager;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.geography.country.trx.OBCountryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author sandiip.shinde
 * 
 */

public class MakerDeleteCountryCommand extends AbstractCommand {

	private ICountryProxyManager countryProxy;

	public ICountryProxyManager getCountryProxy() {
		return countryProxy;
	}

	public void setCountryProxy(ICountryProxyManager countryProxy) {
		this.countryProxy = countryProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerDeleteCountryCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{
						"countryObj",
						"com.integrosys.cms.app.geography.country.bus.ICountry",
						FORM_SCOPE },
				{
						"ICountryTrxValue",
						"com.integrosys.cms.app.geography.country.trx.ICountryTrxValue",
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
			ICountry country = (OBCountry) map.get("countryObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICountryTrxValue trxValueIn = (OBCountryTrxValue) map
					.get("ICountryTrxValue");
			ICountryTrxValue trxValueOut = new OBCountryTrxValue();

			if (event.equals("maker_delete_country")) {
				trxValueOut = getCountryProxy().makerDeleteCountry(ctx,
						trxValueIn, country);
			} else if (event.equals("maker_activate_country")) {
				trxValueOut = getCountryProxy().makerActivateCountry(ctx,
						trxValueIn, country);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getCountryProxy().makerEditRejectedCountry(ctx,
						trxValueIn, country);
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
