package com.integrosys.cms.ui.valuationAgency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.valuationAgency.bus.ICity;
import com.integrosys.cms.app.valuationAgency.proxy.IValuationAgencyProxyManager;
import com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue;
import com.integrosys.cms.app.valuationAgency.trx.OBValuationAgencyTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RefreshCityTownCommand extends AbstractCommand{
	
	private IValuationAgencyProxyManager valuationAgencyProxy;
	
	

	


	public IValuationAgencyProxyManager getValuationAgencyProxy() {
		return valuationAgencyProxy;
	}


	public void setValuationAgencyProxy(
			IValuationAgencyProxyManager valuationAgencyProxy) {
		this.valuationAgencyProxy = valuationAgencyProxy;
	}


	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"stateId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {
					"IValuationAgencyTrxValue",
					"com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue",
					SERVICE_SCOPE },
			});
	    }
	

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "cityList", "java.util.List",REQUEST_SCOPE },
	            {
					"IValuationAgencyTrxValue",
					"com.integrosys.cms.app.valuationAgency.trx.IValuationAgencyTrxValue",
					SERVICE_SCOPE },
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
	        	long id = Long.parseLong((String) map.get("stateId"));
	        	
	        	IValuationAgencyTrxValue trxValueIn = (OBValuationAgencyTrxValue) map
				.get("IValuationAgencyTrxValue");
	        	resultMap.put("cityList", getCityList(id));
	        	resultMap.put("IValuationAgencyTrxValue",trxValueIn);
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
		
		private List getCityList(long stateId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getValuationAgencyProxy().getCityList(stateId);	 	
				for (int i = 0; i < idList.size(); i++) {
						ICity city = (ICity)idList.get(i);
						if( city.getStatus().equals("ACTIVE")) {
							String id = Long.toString(city.getIdCity());
							String val = city.getCityName();
							LabelValueBean lvBean = new LabelValueBean(val, id);
							lbValList.add(lvBean);
						}
					}
				} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
}
