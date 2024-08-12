/**
 * 
 */
package com.integrosys.cms.ui.geography.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.bus.OBRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerDeleteRegionCommand extends AbstractCommand {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerDeleteRegionCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "regionObj",
						"com.integrosys.cms.app.geography.region.bus.IRegion",
						FORM_SCOPE },
				{
						"IRegionTrxValue",
						"com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",
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
						REQUEST_SCOPE },
				{ "countryList", "java.util.List", REQUEST_SCOPE } });
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

			IRegion region = (OBRegion) map.get("regionObj");
			String event = (String) map.get("event");
			long countryId = 0;
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IRegionTrxValue trxValueIn = (OBRegionTrxValue) map
					.get("IRegionTrxValue");
			IRegionTrxValue trxValueOut = new OBRegionTrxValue();

			if (event.equals("maker_delete_region")) {
				trxValueOut = getRegionProxy().makerDeleteRegion(ctx,
						trxValueIn, region);
			} else if (event.equals("maker_activate_region")) {
				trxValueOut = getRegionProxy().makerActivateRegion(ctx,
						trxValueIn, region);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getRegionProxy().makerEditRejectedRegion(ctx,
						trxValueIn, region);
			}
			resultMap.put("countryList", getCountryList(countryId));
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

	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getRegionProxy().getCountryList(countryId);
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry) idList.get(i);
				String id = Long.toString(country.getIdCountry());
				String val = country.getCountryName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
