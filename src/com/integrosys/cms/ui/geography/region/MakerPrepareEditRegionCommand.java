package com.integrosys.cms.ui.geography.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.ibm.disthub2.spi.Service;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerPrepareEditRegionCommand extends AbstractCommand {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	public MakerPrepareEditRegionCommand() {
	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "regionId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } 
			});
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "regionObj",
						"com.integrosys.cms.app.geography.region.bus.IRegion",
						FORM_SCOPE },
				{
						"IRegionTrxValue",
						"com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",
						SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE }, });
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
		long id = Long.parseLong((String) map.get("regionId"));
		try {
			
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			long countryId = 0;
			IRegionTrxValue regionTrxValue = (IRegionTrxValue) getRegionProxy()
					.getRegionById(id);
			IRegion region = regionTrxValue.getActualRegion();
			if (regionTrxValue.getStatus().equals("PENDING_CREATE")
					|| regionTrxValue.getStatus().equals("PENDING_UPDATE")
					|| regionTrxValue.getStatus().equals("PENDING_DELETE")
					|| regionTrxValue.getStatus().equals("REJECTED")
					|| regionTrxValue.getStatus().equals("DRAFT"))
				resultMap.put("wip", "wip");

			resultMap.put("event", event);
			resultMap.put("regionObj", region);
			resultMap.put("startIndex", startIdx);
			resultMap.put("countryList", getCountryList(countryId));
			resultMap.put("IRegionTrxValue", regionTrxValue);
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

		LimitDAO limitDao = new LimitDAO();
		try {
		String migratedFlag = "N";	
		boolean status = false;	
		 status = limitDao.getCAMMigreted("CMS_REGION",id,"ID");
		
		if(status)
		{
			migratedFlag= "Y";
		}
		resultMap.put("migratedFlag", migratedFlag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				if (country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
