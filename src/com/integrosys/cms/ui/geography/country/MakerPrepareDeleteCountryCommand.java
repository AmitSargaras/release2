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
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 * @author sandiip.shinde
 * 
 */
public class MakerPrepareDeleteCountryCommand extends AbstractCommand {

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

	public MakerPrepareDeleteCountryCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "countryId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{
						"countryObj",
						"com.integrosys.cms.app.geography.country.bus.ICountry",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{
						"ICountryTrxValue",
						"com.integrosys.cms.app.geography.country.trx.ICountryTrxValue",
						SERVICE_SCOPE } });
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
			String id = (String) map.get("countryId");
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			ICountryTrxValue countryTrxValue = null;
			ICountry country = new OBCountry();

			countryTrxValue = getCountryProxy().getCountryTrxValue(
					Long.parseLong(id));
			country = countryTrxValue.getActualCountry();

			if (countryTrxValue.getStatus().equals("PENDING_CREATE")
					|| countryTrxValue.getStatus().equals("PENDING_UPDATE")
					|| countryTrxValue.getStatus().equals("PENDING_DELETE")
					|| countryTrxValue.getStatus().equals("REJECTED")
					|| countryTrxValue.getStatus().equals("DRAFT")) {
				resultMap.put("wip", "wip");
			}

			boolean yesNoRegion = getCountryProxy().checkActiveRegion(country);
			String flag = "";
			if (yesNoRegion == true)
				flag = "true";
			else
				flag = "false";
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_COUNTRY",Long.parseLong(id),"ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultMap.put("flag", flag);
			resultMap.put("event", event);
			resultMap.put("startIndex", startIdx);
			resultMap.put("countryObj", country);
			resultMap.put("ICountryTrxValue", countryTrxValue);
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
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
