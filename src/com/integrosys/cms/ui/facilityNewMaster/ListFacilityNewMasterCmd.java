package com.integrosys.cms.ui.facilityNewMaster;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.facilityNewMaster.bus.IFacilityNewMasterJdbc;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitSysXRef;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.productMaster.bus.IProductMaster;
import com.integrosys.cms.app.productMaster.bus.IProductMasterDao;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 $Author: Abhijit R $
 Command for list FacilityNewMaster
 */
public class ListFacilityNewMasterCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	//private IFacilityNewMasterJdbc facilityNewMasterJdbc;
	
	private IFacilityNewMasterProxyManager facilityNewMasterProxy;

	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
	}

	public ListFacilityNewMasterCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "go", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "facCode", "java.lang.String", REQUEST_SCOPE },
				{ "facName", "java.lang.String", REQUEST_SCOPE },
				{ "facCategory", "java.lang.String", REQUEST_SCOPE },
				{ "facType", "java.lang.String", REQUEST_SCOPE },
				{ "facSystem", "java.lang.String", REQUEST_SCOPE },
				{ "facLine", "java.lang.String", REQUEST_SCOPE },
				{ "facCodeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facNameSession", "java.lang.String", SERVICE_SCOPE },
				{ "facCategorySession", "java.lang.String", SERVICE_SCOPE },
				{ "facTypeSession", "java.lang.String", SERVICE_SCOPE },
				{ "facSystemSession", "java.lang.String", SERVICE_SCOPE },
				{ "facLineSession", "java.lang.String", SERVICE_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ "sourceRefNo", "java.lang.String", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"facilityNewMasterList", "java.util.ArrayList", REQUEST_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "facCode", "java.lang.String", REQUEST_SCOPE },
					{ "facName", "java.lang.String", REQUEST_SCOPE },
					{ "facCategory", "java.lang.String", REQUEST_SCOPE },
					{ "facType", "java.lang.String", REQUEST_SCOPE },
					{ "facSystem", "java.lang.String", REQUEST_SCOPE },
					{ "facLine", "java.lang.String", REQUEST_SCOPE },
					{ "facCodeSession", "java.lang.String", SERVICE_SCOPE },
					{ "facNameSession", "java.lang.String", SERVICE_SCOPE },
					{ "facCategorySession", "java.lang.String", SERVICE_SCOPE },
					{ "facTypeSession", "java.lang.String", SERVICE_SCOPE },
					{ "facSystemSession", "java.lang.String", SERVICE_SCOPE },
					{ "facLineSession", "java.lang.String", SERVICE_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
					{ "facilityCategoryList", "java.util.List", SERVICE_SCOPE },
					{ "facilityTypeList", "java.util.List", SERVICE_SCOPE },
					{ "systemList", "java.util.List", SERVICE_SCOPE },
					{ "productAllowedList", "java.util.List", SERVICE_SCOPE },
					{ "lineCurrencyList", "java.util.List", SERVICE_SCOPE },
					
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
	        
	        String facCode = (String) map.get("facCode");
	        String facCodeSession = (String) map.get("facCodeSession");
	        String facName = (String) map.get("facName");
	        String facNameSession = (String) map.get("facNameSession");
	        String facCategory = (String) map.get("facCategory");
	        String facCategorySession = (String) map.get("facCategorySession");
	        String facType = (String) map.get("facType");
	        String facTypeSession = (String) map.get("facTypeSession");
	        String facSystem = (String) map.get("facSystem");
	        String facSystemSession = (String) map.get("facSystemSession");
	        String facLine = (String) map.get("facLine");
	        String facLineSession = (String) map.get("facLineSession");
			String from_event = (String) map.get("fromEvent");
			String index =(String) map.get("startIndex");
			String sourceRefNo=(String) map.get("sourceRefNo");
			
			String event = (String) map.get("event");
 	        String go = (String) map.get("go");
	        int stindex = 0;
	        
	        try {
	 	     // remove all values from session if facility master is freshly entered
	 	if((event.equals("checker_list_facilityNewMaster")||event.equals("maker_list_facilityNewMaster"))&&go==null)
	 		facCodeSession=facNameSession=facCategorySession=facTypeSession=facLineSession=facSystemSession=null;
	 			//--> END removing values from session.
	 			
	 	// if go button is clicked then put values in session
	 	if(go!=null){
	 	if(go.equalsIgnoreCase("y")){
	 		facCodeSession=facCode;
	 		facNameSession=facName;
	 		facCategorySession=facCategory;
	 		facTypeSession=facType;
	 		facSystemSession=facSystem;
	 		facLineSession=facLine;
	 	}/*else{
	 		valuationAgencyCodeSession=valuationAgencyCode;
	 		valuationAgencyNameSession=valuationAgencyName;
	 	}*/
	 	}
	 	
	 	// get values from session.
	 	facCode=facCodeSession;
	 	facName=facNameSession;
	 	facCategory=facCategorySession;
	 	facType=facTypeSession;
	 	facSystem=facSystemSession;
	 	facLine=facLineSession;
 		
 		if(null==facCode||"null".equalsIgnoreCase(facCode))
 			facCode="";
 		if(null==facName||"null".equalsIgnoreCase(facName))
 			facName="";
 		if(null==facCategory||"null".equalsIgnoreCase(facCategory))
 			facCategory="";
 		if(null==facType||"null".equalsIgnoreCase(facType))
 			facType="";
 		if(null==facSystem||"null".equalsIgnoreCase(facSystem))
 			facSystem="";
 		if(null==facLine||"null".equalsIgnoreCase(facLine))
 			facLine="";
 		
 		facCode=facCode.trim();
 		facLine=facLine.trim();
 		facName=facName.trim();
 		
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	SearchResult facilityNewMasterList = new SearchResult();
	        	if(ASSTValidator.isValidFacilityName(facCode))
	        		facCode="";
	        	if(ASSTValidator.isValidFacilityName(facName))
	        		facName="";
	        	if(ASSTValidator.isValidFacilityName(facLine))
	        		facLine="";
	        	if(facCode.equals("")&&facName.equals("")&&facCategory.equals("")&&facType.equals("")&&facSystem.equals("")&&facLine.equals(""))
	            facilityNewMasterList= (SearchResult)  getFacilityNewMasterProxy().getAllActualFacilityNewMaster();// if no input criteria is entered then get all facility list
	        	else
	            facilityNewMasterList= (SearchResult)  getFacilityNewMasterProxy().getFilteredActualFacilityNewMaster(facCode,facName,facCategory,facType,facSystem,facLine);// get facility list based on input criteria
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
	            
	            resultMap.put("startIndex", startIndex);
	            resultMap.put("event",event);
	            resultMap.put("facCode",facCode);
	            resultMap.put("facCodeSession",facCodeSession);
	            resultMap.put("facName",facName);
	            resultMap.put("facNameSession",facNameSession);
	            resultMap.put("facCategory", facCategory);
	            resultMap.put("facCategorySession", facCategorySession);
	            resultMap.put("facType",facType);
	            resultMap.put("facTypeSession", facTypeSession);
	            resultMap.put("facSystem", facSystem);
	            resultMap.put("facSystemSession", facSystemSession);
	            resultMap.put("facLine", facLine);
	            resultMap.put("facLineSession", facLineSession);
	            resultMap.put("facilityCategoryList",getFacilityCategoryList() );
				 resultMap.put("facilityTypeList",getFacilityTypeList() );
				 resultMap.put("systemList",getSystemList() );
	            resultMap.put("facilityNewMasterList", facilityNewMasterList);
	            resultMap.put("productAllowedList", getProductAllowedList());
	            resultMap.put("lineCurrencyList", getLineCurrencyList());
	            
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
	     * getFacilityCategoryList - get dropdown for category field from common code.
	     */
	    private List getFacilityCategoryList() {
			List lbValList = new ArrayList();
			HashMap facilityCategoryMap;
			 ArrayList facilityCategoryLabel = new ArrayList();

				ArrayList facilityCategoryValue = new ArrayList();

				facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_CATEGORY);
				facilityCategoryValue.addAll(facilityCategoryMap.keySet());
				facilityCategoryLabel.addAll(facilityCategoryMap.values());
			try {
			
				for (int i = 0; i < facilityCategoryLabel.size(); i++) {
					String id = facilityCategoryLabel.get(i).toString();
					String val = facilityCategoryValue.get(i).toString();
					LabelValueBean lvBean = new LabelValueBean(id, val);
					lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}
	    /*
	     * getFacilityTypeList - get dropdown for type field from common code.
	     */
		private List getFacilityTypeList() {
			List lbValList = new ArrayList();
			HashMap facilityCategoryMap;
			 ArrayList facilityCategoryLabel = new ArrayList();

				ArrayList facilityCategoryValue = new ArrayList();

				facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.FACILITY_TYPE);
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
	     * getSystemList - get dropdown for system field from common code.
	     */
		private List getSystemList() {
			List lbValList = new ArrayList();
			HashMap facilityCategoryMap;
			 ArrayList facilityCategoryLabel = new ArrayList();

				ArrayList facilityCategoryValue = new ArrayList();

				facilityCategoryMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.SYSTEM);
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
		
	   private List getProductAllowedList() {
			
		IProductMasterDao productMasterDao = (IProductMasterDao) BeanHouse.get("productMasterDao");
		List productAllowedList = productMasterDao.getProductMasterList();
		List productMasterLbValList = new ArrayList();
		try {

			for (int i = 0; i < productAllowedList.size(); i++) {
				IProductMaster productMaster = (IProductMaster) productAllowedList.get(i);
				if (productMaster.getStatus().equals("ACTIVE")) {

					String id = productMaster.getProductCode();
					String val = productMaster.getProductCode();


					LabelValueBean lvBean = new LabelValueBean(val, id);
					productMasterLbValList.add(lvBean);

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return productAllowedList = CommonUtil.sortDropdown(productMasterLbValList);
	}
	   
	 private List getLineCurrencyList() {
			
			ILimitDAO dao = LimitDAOFactory.getDAO();
			List lineCurrencyList = dao.getCurrencyList();
			return lineCurrencyList;
		
  }
}



