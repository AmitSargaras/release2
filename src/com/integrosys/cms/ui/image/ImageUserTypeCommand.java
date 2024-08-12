package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;

public class ImageUserTypeCommand extends AbstractCommand {

	/**
	 * default constructor
	 */

	public ImageUserTypeCommand() {

	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				
				{ "fromMenu", "java.lang.String", REQUEST_SCOPE },
				{ "userType", "java.lang.String", REQUEST_SCOPE },
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "userType", "java.lang.String", SERVICE_SCOPE },
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },
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
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultSet = new HashMap();
		String fromMenu=(String) map.get("fromMenu");
		String userType=(String) map.get("userType");

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
		
		DefaultLogger.debug(this, "==========userType==================>"+userType);
		if("Y".equals(fromMenu)){
			result.put("userType", userType);
		}
		result.put("obImageUploadAddList", new ArrayList());
//		result.put("securityDocTagModuleList", securityDocTagModuleList);
//		result.put("facilityDocTagModuleList", facilityDocTagModuleList);
		result.put("statementDocList", statementDocList);
		result.put("camDocList", camDocList);
		result.put("otherMasterDocList", otherMasterDocList);
		result.put("typeOfDocList", typeOfDocList);
		result.put("facilityDocNameMasterList", facilityDocNameMasterList);
		result.put("securityDocNameMasterList", securityDocNameMasterList);
		resultSet.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		resultSet.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultSet;
	}
}
