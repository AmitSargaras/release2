package com.integrosys.cms.ui.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RefreshRegionCmd extends AbstractCommand{

	private ICityProxyManager cityProxy;
	
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"countryId", "java.lang.String", REQUEST_SCOPE},
	            {"regionId", "java.lang.String", REQUEST_SCOPE},
	            {"stateId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "regionList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
			});
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        try {
	        	String event = (String) map.get("event");
	        	if( event.equals("refresh_region_id")){	        		
	        		long countryId = Long.parseLong((String) map.get("countryId"));
	        		resultMap.put("regionList", getRegionList(countryId));
	        	}
	        	
		        resultMap.put("event", event);
	        } catch (NoSuchGeographyException nsge) {
	        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
				cpe.initCause(nsge);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
		
		private List getRegionList(long countryId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getCityProxy().getRegionList(countryId);				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
					if( region.getStatus().equals("ACTIVE")) {
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
}
