package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class CancleFilterCmd extends AbstractCommand implements
		ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public CancleFilterCmd() {

	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
			{ "customerSearchCriteria", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", FORM_SCOPE },
			{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "1", "java.lang.String", REQUEST_SCOPE },
			{ "customerID", "java.lang.String", REQUEST_SCOPE },
			{ "hiddenItemID", "java.lang.String", REQUEST_SCOPE },
			{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
			{ "indicator", "java.lang.String", REQUEST_SCOPE },
			{ "aCustomerSearchCriteria","com.integrosys.cms.ui.image.ImageUploadForm", SERVICE_SCOPE },
			
			{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
			{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
			{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
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
			{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
			{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
			{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
			{ "aCustomerSearchCriteria","com.integrosys.cms.ui.image.ImageUploadForm", SERVICE_SCOPE },
			{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
			{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
			{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
			{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },
			{ "partyName", "java.lang.String", REQUEST_SCOPE },
			{ "partyId", "java.lang.String", REQUEST_SCOPE },
			{ "facilityDocNameMasterList", "java.util.ArrayList", REQUEST_SCOPE },
			{ "securityDocNameMasterList", "java.util.ArrayList", REQUEST_SCOPE },
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");
		String indicator = (String) map.get("indicator");
		String id=(String)map.get("1");
		String customerID=(String)map.get("customerID");
		String thiddenItemID=(String)map.get("hiddenItemID");
		//int hiddenItemID=Integer.parseInt(thiddenItemID);
		//int selectId=hiddenItemID-1;
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		SearchResult sr=(SearchResult)map.get("customerList");
		ArrayList resultList = new ArrayList();
		if(sr != null) {
		resultList= (ArrayList) sr.getResultList();
		}
		//OBCustomerSearchResult searchResult =(OBCustomerSearchResult)resultList.get(selectId);
//		String customerName = searchResult.getLegalName();
//		String partyId = searchResult.getLegalReference();
		
		
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
		List<String> facilityDocNameMasterList=new ArrayList<String>();
		facilityDocNameMasterList = imageTagDaoImpl.getFacilityDocFromMasterList();
		
		List<String> statementDocList=new ArrayList<String>();
		statementDocList = imageTagDaoImpl.getStatementDocumentList ();
		
		List<String> camDocList=new ArrayList<String>();
		camDocList = imageTagDaoImpl.getCamDocumentList ();
		
		List<String> otherMasterDocList=new ArrayList<String>();
		otherMasterDocList = imageTagDaoImpl.getOtherMasterDocumentList ();
		
		List<String> typeOfDocList=new ArrayList<String>();
		typeOfDocList = imageTagDaoImpl.getTypeOfDocumentList ();
		
		List<String> securityDocNameMasterList=new ArrayList<String>();
		securityDocNameMasterList = imageTagDaoImpl.getSecurityDocFromMasterList();
		
		resultMap.put("customerList", sr);
		resultMap.put("statementDocList", statementDocList);
		resultMap.put("camDocList", camDocList);
		resultMap.put("otherMasterDocList", otherMasterDocList);
		resultMap.put("typeOfDocList", typeOfDocList);
		resultMap.put("facilityDocNameMasterList", facilityDocNameMasterList);
		resultMap.put("securityDocNameMasterList", securityDocNameMasterList);
		
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
