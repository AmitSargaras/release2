package com.integrosys.cms.ui.user;

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
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.geography.state.proxy.IStateProxyManager;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class RefreshBranchDetailsCmd extends AbstractCommand{

	private ICityProxyManager cityProxy;
	
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"branchCode", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "branchDetailsList", "java.util.List",REQUEST_SCOPE },
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
	        	
	        	String branchCode= (String)map.get("branchCode");
	        	ISystemBankBranchProxyManager systemBankBranchProxyManager=(ISystemBankBranchProxyManager) BeanHouse.get("systemBankBranchProxy");
	        	 SearchResult searchResult = systemBankBranchProxyManager.getAllActualBranch();
	        	 List branchList= new ArrayList();
				 List idList = (List) searchResult.getResultList();
				for (int i = 0; i < idList.size(); i++) {
					ISystemBankBranch bankBranch = (ISystemBankBranch) idList.get(i);
					if(branchCode.equals(bankBranch.getSystemBankBranchCode())){
						branchList.add(bankBranch);
						resultMap.put("branchDetailsList", branchList);
					}
				}
	        	
	        	
	        	
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
				List idList = (List) getCityProxy().getCityList(stateId);				
			
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
