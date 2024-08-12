package com.integrosys.cms.ui.newTat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMaster;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.newTat.bus.INewTatJdbc;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RefreshFacilityNameCommmand extends AbstractCommand{
	
	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"facilityCategory", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	           // { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }
			});
	    }
	

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "facilityNameList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	           // { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }
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
	        	String facilityCategory = ((String) map.get("facilityCategory"));
	        	//ICollateralTrxValue trxValue = (OBCollateralTrxValue) map.get("serviceColObj");
	        	//resultMap.put("serviceColObj", trxValue);
	        	
	        	SearchResult facilityNewMasterList = new SearchResult();
	        	IFacilityNewMasterJdbc facMaster = (IFacilityNewMasterJdbc)BeanHouse.get("facilityNewMasterJdbc");
	        	 List facilityNameList=null;
	        	 facilityNewMasterList= (SearchResult) facMaster.getFilteredActualFacilityNewMaster("", "", facilityCategory, "", "", "");
	        	 List lbValList = new ArrayList();
	        	 if(facilityNewMasterList!=null){
	        		 facilityNameList = new ArrayList(facilityNewMasterList.getResultList());
	        		 for (int i = 0; i < facilityNameList.size(); i++) {
	 						IFacilityNewMaster fac = (IFacilityNewMaster)facilityNameList.get(i);
	 						if( fac.getStatus().equals("ACTIVE")) {
	 							String id = Long.toString(fac.getId());
	 							String val = fac.getNewFacilityName();
	 							LabelValueBean lvBean = new LabelValueBean(val, id);
	 							lbValList.add(lvBean);
	 						}
	 					}
	 			}
	        	resultMap.put("facilityNameList",  CommonUtil.sortDropdown(lbValList));
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
		
		
}
