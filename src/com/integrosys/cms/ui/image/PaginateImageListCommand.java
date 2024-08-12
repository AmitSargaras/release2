package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.ImageUploadCommand;

public class PaginateImageListCommand extends ImageUploadCommand {

	
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerName","java.lang.String",REQUEST_SCOPE },
				{ "legalName","java.lang.String",REQUEST_SCOPE },
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
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
				{ "searchResult", "java.util.List", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "customerName","java.lang.String",REQUEST_SCOPE },
				{ "legalName","java.lang.String",REQUEST_SCOPE },
				{ "checkerEvent", "java.lang.String", REQUEST_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			String event = (String) map.get("checkerEvent");
			String startIdx = (String) map.get("startIndex");
			DefaultLogger.debug(this, "StartIdx: " + startIdx);
			String custId= (String) map.get("legalName");
			String custName=(String) map.get("customerName");
			
			OBImageUploadAdd imageUploadAdd= new OBImageUploadAdd();
				imageUploadAdd.setCustId(custId);
				imageUploadAdd.setCustName(custName.replace('~', ' '));
			
				
//			ArrayList custImageList = (ArrayList) getImageUploadProxyManager().getCustImageList(imageUploadAdd);
		    ArrayList custImageList = (ArrayList) map.get("obImageUploadAddList");// retrieving the list from session
			SearchResult searchResult= new SearchResult(0, custImageList.size(), custImageList.size(), custImageList);
			
			resultMap.put("searchResult", searchResult);
			resultMap.put("startIndex", startIdx);
			resultMap.put("legalName", custId);
			resultMap.put("customerName", custName);
			resultMap.put("checkerEvent", event);
		} catch (NoSuchGeographyException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
