package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RefreshCityTownCommand extends AbstractCommand{
	
	private IOtherBankProxyManager otherBankProxyManager;
	
	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}


	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
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
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				 {"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE}
			});
	    }
	

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "cityList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
	            {"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE}
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
	        	String id = (String) map.get("stateId");
	        	ICollateralTrxValue trxValue = (OBCollateralTrxValue) map.get("serviceColObj");
	        	IOtherBankBranchTrxValue otherBankBranchTrxValue =(OBOtherBankBranchTrxValue) (map.get("IOtherBankBranchTrxValue"));
	        	resultMap.put("IOtherBankBranchTrxValue", otherBankBranchTrxValue);
	        	resultMap.put("serviceColObj", trxValue);
	        	resultMap.put("cityList", getCityList(id));
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
		
		private List getCityList(String stateId) {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getOtherBankProxyManager().getCityList(stateId);	 	
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
