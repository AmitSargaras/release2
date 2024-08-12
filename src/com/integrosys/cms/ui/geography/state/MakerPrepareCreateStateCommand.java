package com.integrosys.cms.ui.geography.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

	/**
	 * Class created by
	 * @author sandiip.shinde
	 * @since 11-04-2011
	 *
	 */
public class MakerPrepareCreateStateCommand extends AbstractCommand{
	
	private IStateProxyManager stateProxy;
	
	public IStateProxyManager getStateProxy() {
		return stateProxy;
	}

	public void setStateProxy(IStateProxyManager stateProxy) {
		this.stateProxy = stateProxy;
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
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{"countryOBId","java.lang.String",REQUEST_SCOPE}
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
//				{ "countryList", "java.util.List",SERVICE_SCOPE },
//				{ "regionList", "java.util.List",SERVICE_SCOPE }	/* original */
				
				{ "countryList", "java.util.List",REQUEST_SCOPE },
				{ "regionList", "java.util.List",REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute() MakerPrepareCreateCountryCommand");
		long countryId = 0, id = 0;
		String countryOBId =(String) map.get("countryOBId");
		
		if ( countryOBId != null && !countryOBId.equals("") ) {
			countryId = Long.parseLong(countryOBId);
		}
		
		resultMap.put("countryList", getCountryList(id));
		resultMap.put("regionList", getRegionList(countryId));
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		return returnMap;
	}


	private List getCountryList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getStateProxy().getCountryList(countryId);	
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry)idList.get(i);
				if( country.getStatus().equals("ACTIVE")){
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
	
	private List getRegionList(long countryId) {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getStateProxy().getRegionList(countryId);	
			for (int i = 0; i < idList.size(); i++) {
				IRegion region = (IRegion)idList.get(i);
				String id = Long.toString(region.getIdRegion());
				String val =  region.getRegionName();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
