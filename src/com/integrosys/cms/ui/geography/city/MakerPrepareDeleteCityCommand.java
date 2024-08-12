/**
 * 
 */
package com.integrosys.cms.ui.geography.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.bus.OBCity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * @author sandiip.shinde
 * 
 */
public class MakerPrepareDeleteCityCommand extends AbstractCommand {

	private ICityProxyManager cityProxy;

	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Default Constructor
	 */

	public MakerPrepareDeleteCityCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "cityId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "cityObj", "com.integrosys.cms.app.geography.city.bus.ICity",
						FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "flag", "java.lang.String", REQUEST_SCOPE },
				{ "countryList", "java.util.List", SERVICE_SCOPE },
				{ "regionList", "java.util.List", SERVICE_SCOPE },
				{ "stateList", "java.util.List", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{
						com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
						"java.util.Locale", GLOBAL_SCOPE },
				{
						"ICityTrxValue",
						"com.integrosys.cms.app.geography.country.trx.ICityTrxValue",
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
		String id = (String) map.get("cityId");
		try {
			
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			ICityTrxValue cityTrxValue = null;
			ICity city = new OBCity();
			long stateId = 0, countryId = 0;
			boolean yesNoRegion = false;
			String flag = "";

			cityTrxValue = getCityProxy().getCityTrxValue(Long.parseLong(id));
			city = cityTrxValue.getActualCity();

			if (cityTrxValue.getStatus().equals("PENDING_CREATE")
					|| cityTrxValue.getStatus().equals("PENDING_UPDATE")
					|| cityTrxValue.getStatus().equals("PENDING_DELETE")
					|| cityTrxValue.getStatus().equals("REJECTED")
					|| cityTrxValue.getStatus().equals("DRAFT"))
				resultMap.put("wip", "wip");

			if (event.equals("maker_prepare_activate_city"))
				yesNoRegion = getCityProxy().checkInActiveStates(city);

			if (yesNoRegion == true)
				flag = "true";
			else
				flag = "false";

			resultMap.put("flag", flag);
			resultMap.put("event", event);
			resultMap.put("cityObj", city);
			resultMap.put("startIndex", startIdx);
			resultMap.put("countryList", getCountryList(countryId));
			resultMap.put("regionList", getRegionList(stateId));
			resultMap.put("stateList", getStateList(stateId));
			resultMap.put("ICityTrxValue", cityTrxValue);
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
				 status = limitDao.getCAMMigreted("CMS_CITY",Long.parseLong(id),"ID");
				
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
			List idList = (List) getCityProxy().getCountryList(countryId);
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

	private List getRegionList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getRegionList(stateId);
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion) idList.get(i);
				String id = Long.toString(region.getIdRegion());
				String val = region.getRegionName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getStateList(long stateId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getStateList(stateId);

			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				String id = Long.toString(state.getIdState());
				String val = state.getStateName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
