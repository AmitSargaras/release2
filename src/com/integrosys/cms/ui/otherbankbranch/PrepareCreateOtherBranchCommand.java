package com.integrosys.cms.ui.otherbankbranch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * This command Prepares the other bank branch for creation
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 * @since $Date: 2011/03/23 11:32:23
 */
public class PrepareCreateOtherBranchCommand extends AbstractCommand implements ICommonEventConstant {
	
	IOtherBankProxyManager otherBankProxyManager;
	
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
	 * Default Constructor
	 */
	public PrepareCreateOtherBranchCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"branchCountryId","java.lang.String",REQUEST_SCOPE},
				{"branchRegionId","java.lang.String",REQUEST_SCOPE},
				{"branchStateId","java.lang.String",REQUEST_SCOPE},
				{"otherBankId","java.lang.String",REQUEST_SCOPE},
				{"otherBankCode","java.lang.String",REQUEST_SCOPE},
				{"otherBankName","java.lang.String",REQUEST_SCOPE},
				{"IOtherBankTrxValue", "com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue", SERVICE_SCOPE},
	            {"OtherBankObj","com.integrosys.cms.app.otherbranch.bus.OBOtherBank",FORM_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
	            {"OtherBankId","java.lang.String",REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
	            {"otherBankList","com.integrosys.base.businfra.search.SearchResult",REQUEST_SCOPE},
	            {"otherBank","com.integrosys.cms.app.otherbankbranch.bus.OBOtherBank",REQUEST_SCOPE},
	            {"otherBankCode","java.lang.String",REQUEST_SCOPE},
				{"otherBankName","java.lang.String",REQUEST_SCOPE},
				{"otherBankId","java.lang.String",REQUEST_SCOPE},
				{"countryList","java.util.List",REQUEST_SCOPE},
	            {"regionList","java.util.List",REQUEST_SCOPE},
	            {"stateList","java.util.List",REQUEST_SCOPE},
	            {"cityList","java.util.List",REQUEST_SCOPE}
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
        	String searchVal = (String) map.get("searchval");
        	SearchResult sr = getOtherBankProxyManager().getOtherBankList(searchVal,searchVal);
        	
        	String bankId = (String) map.get("otherBankId");
        	String bankCode = (String) map.get("otherBankCode");
        	String bankName = (String) map.get("otherBankName");
        	String countryId = (String) map.get("countryId");
        	String regionId = (String) map.get("regionId");
        	String stateId = (String) map.get("stateId");
        	
        	resultMap.put("countryList",getCountryList());
        	resultMap.put("regionList",getRegionList(countryId));
        	resultMap.put("stateList",getStateList(regionId));
        	resultMap.put("cityList",getCityList(stateId));
        	
        	if(bankId != null && bankId.trim() !=""){
        		IOtherBank otherBank = getOtherBankProxyManager().getOtherBankById(Long.parseLong(bankId));
        		resultMap.put("otherBank", otherBank);
        	}
        	resultMap.put("otherBankId", bankId);
        	resultMap.put("otherBankCode", bankCode);
        	resultMap.put("otherBankName", bankName);
        	resultMap.put("otherBankList", sr);
        } catch (OtherBankException obe) {
        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
			cpe.initCause(obe);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
	
	private List getCountryList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getOtherBankProxyManager().getCountryList();				
		
			for (int i = 0; i < idList.size(); i++) {
				ICountry country = (ICountry)idList.get(i);
				if( country.getStatus().equals("ACTIVE")) {
					String id = Long.toString(country.getIdCountry());
					String val = country.getCountryName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getRegionList(String countryId) {
		List lbValList = new ArrayList();
		try {
			if(countryId!=null && !countryId.equals("")){
				List idList = (List) getOtherBankProxyManager().getRegionList(countryId);				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
					if( region.getStatus().equals("ACTIVE")) {
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getStateList(String regionId) {
		List lbValList = new ArrayList();
		try {
			if(regionId!=null && !regionId.equals("")){
				List idList = (List) getOtherBankProxyManager().getStateList(regionId);				
			
				for (int i = 0; i < idList.size(); i++) {
					IState state = (IState)idList.get(i);
					if( state.getStatus().equals("ACTIVE")) {
						String id = Long.toString(state.getIdState());
						String val = state.getStateName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
					}
				}
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getCityList(String stateId) {
		List lbValList = new ArrayList();
		try {if(stateId !=null && !stateId.equals("")){
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
			}	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}



