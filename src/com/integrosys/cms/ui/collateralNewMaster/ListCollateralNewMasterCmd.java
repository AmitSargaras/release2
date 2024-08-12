package com.integrosys.cms.ui.collateralNewMaster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateralNewMaster.proxy.ICollateralNewMasterProxyManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 $Author: Abhijit R $
 Command for list CollateralNewMaster
 */
public class ListCollateralNewMasterCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	private ICollateralNewMasterProxyManager collateralNewMasterProxy;

	public ICollateralNewMasterProxyManager getCollateralNewMasterProxy() {
		return collateralNewMasterProxy;
	}

	public void setCollateralNewMasterProxy(ICollateralNewMasterProxyManager collateralNewMasterProxy) {
		this.collateralNewMasterProxy = collateralNewMasterProxy;
	}

	public ListCollateralNewMasterCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "go", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralDescriptionSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralMainTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralSubTypeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "newCollateralCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralDescriptionSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralMainTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "newCollateralSubTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	        		{ "newCollateralCodeSearch", "java.lang.String", REQUEST_SCOPE },
					{ "newCollateralDescriptionSearch", "java.lang.String", REQUEST_SCOPE },
					{ "newCollateralMainTypeSearch", "java.lang.String", REQUEST_SCOPE },
					{ "newCollateralSubTypeSearch", "java.lang.String", REQUEST_SCOPE },
					{ "newCollateralCodeSession", "java.lang.String", SERVICE_SCOPE },
					{ "newCollateralDescriptionSession", "java.lang.String", SERVICE_SCOPE },
					{ "newCollateralMainTypeSession", "java.lang.String", SERVICE_SCOPE },
					{ "newCollateralSubTypeSession", "java.lang.String", SERVICE_SCOPE },
	                {"collateralNewMasterList", "java.util.ArrayList", REQUEST_SCOPE},
	                { "collateralMainTypeList", "java.util.List", SERVICE_SCOPE },
	                { "collateralSubTypeList", "java.util.List", SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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
	        String startIndex = (String) map.get("startIndex");
	        int stindex = 0;
	        String event = (String) map.get("event");
	        String go = (String) map.get("go");
	        String newCollateralDescriptionSession=(String) map.get("newCollateralDescriptionSession");
	        String newCollateralDescriptionSearch = (String) map.get("newCollateralDescriptionSearch");
            String newCollateralCodeSession=(String) map.get("newCollateralCodeSession");
	        String newCollateralCodeSearch = (String) map.get("newCollateralCodeSearch");
            String newCollateralMainTypeSession=(String) map.get("newCollateralMainTypeSession");
	        String newCollateralMainTypeSearch = (String) map.get("newCollateralMainTypeSearch");
            String newCollateralSubTypeSession=(String) map.get("newCollateralSubTypeSession");
	        String newCollateralSubTypeSearch = (String) map.get("newCollateralSubTypeSearch");
	     // remove all values from session if collateral master is freshly entered
	if((event.equals("checker_list_collateralNewMaster")||event.equals("maker_list_collateralNewMaster"))&&go==null)
		newCollateralCodeSearch=newCollateralCodeSession=newCollateralDescriptionSearch=newCollateralDescriptionSession=newCollateralMainTypeSearch=newCollateralMainTypeSession=newCollateralSubTypeSearch=newCollateralSubTypeSession=null;
			//--> END removing values from session.
			
	// if go button is clicked then put values in session
	if(go!=null){
	if(go.equalsIgnoreCase("y")){
		newCollateralCodeSession=newCollateralCodeSearch;
		newCollateralDescriptionSession=newCollateralDescriptionSearch;
		newCollateralMainTypeSession=newCollateralMainTypeSearch;
		newCollateralSubTypeSession=newCollateralSubTypeSearch;
	}/*else{
		valuationAgencyCodeSession=valuationAgencyCode;
		valuationAgencyNameSession=valuationAgencyName;
	}*/
	}
	
	// get values from session.
	newCollateralCodeSearch=newCollateralCodeSession;
	newCollateralDescriptionSearch=newCollateralDescriptionSession;
	newCollateralMainTypeSearch=newCollateralMainTypeSession;
	newCollateralSubTypeSearch=newCollateralSubTypeSession;
	        try {
	        	
	        	if(null!=newCollateralCodeSearch)
	        		newCollateralCodeSearch=newCollateralCodeSearch.trim();
	        	if(null!=newCollateralDescriptionSearch)
	        		newCollateralDescriptionSearch=newCollateralDescriptionSearch.trim();
	        	
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	SearchResult collateralNewMasterList = new SearchResult();
	        	
	        	if(ASSTValidator.isValidANDName(newCollateralCodeSearch))
	        		newCollateralCodeSearch="";
	        	if(ASSTValidator.isValidANDName(newCollateralDescriptionSearch))
	        		newCollateralDescriptionSearch="";
	            collateralNewMasterList= (SearchResult)  getCollateralNewMasterProxy().getFilteredCollateral(newCollateralCodeSearch, newCollateralDescriptionSearch, newCollateralMainTypeSearch, newCollateralSubTypeSearch);
	            
	            if (StringUtils.isBlank(startIndex)) {
					if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
						stindex = 0;
						startIndex = String.valueOf(stindex);
						resultMap.put("startIndex", startIndex);

					}
					else {
						stindex = Integer.parseInt(globalStartIndex);
						startIndex = globalStartIndex;
						resultMap.put("startIndex", startIndex);
					}
				}
				else {
					stindex = Integer.parseInt(startIndex);
					resultMap.put("startIndex", startIndex);
				}

	                  resultMap.put("collateralNewMasterList", collateralNewMasterList);
	                resultMap.put("newCollateralCodeSearch", newCollateralCodeSearch);
	                  resultMap.put("newCollateralCodeSession", newCollateralCodeSession);
	                  resultMap.put("newCollateralDescriptionSearch", newCollateralDescriptionSearch);
	                  resultMap.put("newCollateralDescriptionSession", newCollateralDescriptionSession);
	                  resultMap.put("newCollateralMainTypeSearch", newCollateralMainTypeSearch);
	                  resultMap.put("newCollateralMainTypeSession", newCollateralMainTypeSession);
	                  resultMap.put("newCollateralSubTypeSearch", newCollateralSubTypeSearch);
	                  resultMap.put("newCollateralSubTypeSession", newCollateralSubTypeSession);
	                  resultMap.put("collateralMainTypeList", getCollateralMainType());
	                  resultMap.put("collateralSubTypeList", getCollateralSubType(newCollateralMainTypeSearch));
	        }catch (SystemBankException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			} catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	    
	    /*
	     * getCollateralMainType - get dropdown for Collateral Main Type field from common code.
	     */
		private List getCollateralMainType() {
			List lbValList = new ArrayList();
			HashMap facilityCategoryMap;
			 ArrayList facilityCategoryLabel = new ArrayList();

				ArrayList facilityCategoryValue = new ArrayList();

				facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.COMMON_CODE_SECURITY_TYPE);
				facilityCategoryValue.addAll(facilityCategoryMap.keySet());
				facilityCategoryLabel.addAll(facilityCategoryMap.values());
			try {
			
				for (int i = 0; i < facilityCategoryLabel.size(); i++) {
					String id = facilityCategoryLabel.get(i).toString();
					String val = facilityCategoryValue.get(i).toString();
					LabelValueBean lvBean = new LabelValueBean(id,val);
					lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
		
		 /*
	     * getCollateralSubType - get dropdown for Collateral Sub Type field from common code.
	     */
		private List getCollateralSubType(String newCollateralMainType) {
			List lbValList = new ArrayList();
			HashMap facilityCategoryMap;
			 ArrayList facilityCategoryLabel = new ArrayList();

				ArrayList facilityCategoryValue = new ArrayList();

				facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.COMMON_CODE_SECURITY_SUB_TYPE);
				facilityCategoryValue.addAll(facilityCategoryMap.keySet());
				facilityCategoryLabel.addAll(facilityCategoryMap.values());
			try {
			
				for (int i = 0; i < facilityCategoryLabel.size(); i++) {
					String id = facilityCategoryLabel.get(i).toString();
					String val = facilityCategoryValue.get(i).toString();
					LabelValueBean lvBean = null;
					if(null!=newCollateralMainType){
					if(val.substring(0,2).equals(newCollateralMainType)){
					lvBean = new LabelValueBean(id,val);
					lbValList.add(lvBean);
					}
					}
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
}



