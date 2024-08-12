package com.integrosys.cms.ui.geography.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;

	/**
	 * This Class is used for Viewing a perticular Country
	 * 
	 * @author $Author: Sandeep Shinde
	 * @version 2.0
	 * @since $Date: 08/04/2011 01:41:00 $ Tag: $Name: $
	 */

public class ViewCityByIndexCommand extends AbstractCommand{

	private ICityProxyManager cityProxy;
		
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public ViewCityByIndexCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "cityId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});

	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "cityObj","com.integrosys.cms.app.geography.city.bus.ICity",FORM_SCOPE },
	            { "countryList", "java.util.List",REQUEST_SCOPE },
				{ "regionList", "java.util.List",REQUEST_SCOPE },
				{ "stateList", "java.util.List",REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of City is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		String event = (String) map.get("checkerEvent");
		String startIdx = (String) map.get("startIndex");
		long cityId = Long.parseLong((String) map.get("cityId"));
		
		DefaultLogger.debug(this, "============ ViewCityCommandByIndex ()" + cityId);
		try {
			ICityTrxValue cityTrx = getCityProxy().getCityById(cityId);
			ICity city = cityTrx.getActualCity();
			
			long countryId = city.getStateId().getRegionId().getCountryId().getIdCountry();
        	long regionId = city.getStateId().getRegionId().getIdRegion();
			
        	resultMap.put("cityObj", city);
        	resultMap.put("checkerEvent", event);
        	resultMap.put("startIndex", startIdx);
			resultMap.put("countryList", getCountryList(countryId));
			resultMap.put("regionList", getRegionList(countryId));
			resultMap.put("stateList", getStateList(regionId));
			
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
				 status = limitDao.getCAMMigreted("CMS_CITY",cityId,"ID");
				
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
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getCityProxy().getCountryList(countryId);		
			for (int i = 0; i < idList.size(); i++) {
					ICountry country = (ICountry)idList.get(i);
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
					IRegion region = (IRegion)idList.get(i);
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
					IState state = (IState)idList.get(i);
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
