

package com.integrosys.cms.ui.facilityNewMaster;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.facilityNewMaster.proxy.IFacilityNewMasterProxyManager;

/**
*@author $Author: Abhijit R$
*Command for pagination of FacilityNewMaster
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private IFacilityNewMasterProxyManager facilityNewMasterProxy;

	public IFacilityNewMasterProxyManager getFacilityNewMasterProxy() {
		return facilityNewMasterProxy;
	}

	public void setFacilityNewMasterProxy(IFacilityNewMasterProxyManager facilityNewMasterProxy) {
		this.facilityNewMasterProxy = facilityNewMasterProxy;
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
				{ "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
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
				 {"facilityNewMasterList", "java.util.ArrayList", REQUEST_SCOPE},
				 { "startIndex", "java.lang.String", REQUEST_SCOPE },
				 { "offset", "java.lang.Integer", SERVICE_SCOPE },
					{ "length", "java.lang.Integer", SERVICE_SCOPE },
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIdx = (String) map.get("startIndex");
			SearchResult facilityNewMasterList = new SearchResult();
			int offset = 0,length=0;
			if(null!=map.get("offset"))
			offset = ((Integer) map.get("offset")).intValue();
			if(null!=map.get("length"))
			length = ((Integer) map.get("length")).intValue();
			DefaultLogger.debug(this, "Name: " + name);
			DefaultLogger.debug(this, "Login: " + login);
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			DefaultLogger.debug(this, "Length: " + length);
			DefaultLogger.debug(this, "Offset: " + offset);
			
			/*
			 * maintain code in session when clicking on next
			 */
			String facCode = "";
			String facCodeSession=(String) map.get("facCodeSession");
			if("".equals(facCodeSession))
				facCodeSession=null;
			if(facCodeSession!=null){
				facCode=facCodeSession;
			}else{
				facCode = (String) map.get("facCode");
				facCodeSession=facCode;
			}
			/*
			 * maintain name in session when clicking on next
			 */
			String facName = "";
			String facNameSession=(String) map.get("facNameSession");
			if("".equals(facNameSession))
				facNameSession=null;
			if(facNameSession!=null){
				facName=facNameSession;
			}else{
				facName = (String) map.get("facName");
				facNameSession=facName;
			}
			/*
			 * maintain category in session when clicking on next
			 */
			String facCategory = "";
			String facCategorySession=(String) map.get("facCategorySession");
			if("".equals(facCategorySession))
				facCategorySession=null;
			if(facCategorySession!=null){
				facCategory=facCategorySession;
			}else{
				facCategory = (String) map.get("facCategory");
				facCategorySession=facCategory;
			}
			/*
			 * maintain System in session when clicking on next
			 */
			String facSystem = "";
			String facSystemSession=(String) map.get("facSystemSession");
			if("".equals(facSystemSession))
				facSystemSession=null;
			if(facSystemSession!=null){
				facSystem=facSystemSession;
			}else{
				facSystem = (String) map.get("facSystem");
				facSystemSession=facSystem;
			}
			/*
			 * maintain Type in session when clicking on next
			 */
			String facType = "";
			String facTypeSession=(String) map.get("facTypeSession");
			if("".equals(facTypeSession))
				facTypeSession=null;
			if(facTypeSession!=null){
				facType=facTypeSession;
			}else{
				facType = (String) map.get("facType");
				facTypeSession=facType;
			}
			/*
			 * maintain Line in session when clicking on next
			 */
			String facLine = "";
			String facLineSession=(String) map.get("facLineSession");
			if("".equals(facLineSession))
				facLineSession=null;
			if(facLineSession!=null){
				facLine=facLineSession;
			}else{
				facLine = (String) map.get("facLine");
				facLineSession=facLine;
			}
			facilityNewMasterList= getFacilityNewMasterProxy().getFilteredActualFacilityNewMaster(facCode, facName, facCategory, facType, facSystem, facLine);
		//	SearchResult facilityNewMasterSearchResult = new SearchResult(0, 10, 100, facilityNewMasterList);
			String targetOffset=startIdx;			
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put("startIndex", startIdx);
		//	resultMap.put("facilityNewMasterList", facilityNewMasterSearchResult);
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
			resultMap.put("facilityNewMasterList", facilityNewMasterList);
			
			
		}catch (SystemBankException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
