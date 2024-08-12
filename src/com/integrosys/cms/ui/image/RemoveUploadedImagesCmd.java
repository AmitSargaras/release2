package com.integrosys.cms.ui.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;

/**
 * This command creates a Image Tag
 * 
 * @author abhijit.rudrakshawar
 * 
 * 
 */

public class RemoveUploadedImagesCmd extends AbstractCommand implements ICommonEventConstant{
	
	private IImageUploadProxyManager imageUploadProxyManager;
	
	/**
	 * @return the imageUploadProxyManager
	 */
	public IImageUploadProxyManager getImageUploadProxyManager() {
		return imageUploadProxyManager;
	}

	/**
	 * @param imageUploadProxyManager the imageUploadProxyManager to set
	 */
	public void setImageUploadProxyManager(
			IImageUploadProxyManager imageUploadProxyManager) {
		this.imageUploadProxyManager = imageUploadProxyManager;
	}


	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] {
					{ "removeIdx", "java.lang.String", REQUEST_SCOPE },
					{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE } 
				});
	}

	

	public String[][] getResultDescriptor() {
		DefaultLogger.debug(this, "********  getResultDescriptor Call: ");
		return (new String[][] {
				{ "obImageUploadAddList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE } ,
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				
				{ "custIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "facilityCodeList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "securityNameIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherSecDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "typeOfDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "statementDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "camNumberList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "camDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherMasterDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "facDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "secDocCode", "java.lang.String", REQUEST_SCOPE },
				{ "custIdList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "facilityCodeList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityNameIdList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherSecDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camNumberList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "facilityDocNameMasterList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityDocNameMasterList", "java.util.ArrayList", REQUEST_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		HashMap result = new HashMap();
		HashMap resultSet = new HashMap();
		
		String removeIdx = (String) map.get("removeIdx");
		List allImagesList=(ArrayList) map.get("obImageUploadAddList");
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
		String custId = "";
		
		StringTokenizer st = new StringTokenizer(removeIdx, ",");
		int removeAry[] = new int[st.countTokens()];
		// populating the remove array
		int j=0;
		while (st.hasMoreTokens()) {
			removeAry[j] = Integer.parseInt(st.nextToken());
			j++;
		}
		DefaultLogger.debug(this, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Removing Images Starts $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		for (int i = 0; i < removeAry.length; i++) {
			IImageUploadAdd obToRemove = (OBImageUploadAdd) allImagesList.get(removeAry[i]);
			custId = (String)obToRemove.getCustId();
			DefaultLogger.debug(this, "Removing Image '"+obToRemove.getImgFileName()+"' having Id :"+obToRemove.getImgId());
			//Marking the image as deprecated for soft delete.
			obToRemove.setImgDepricated("Y");
			getImageUploadProxyManager().removeUploadedImage(obToRemove);	
		}
		DefaultLogger.debug(this, "$$$$$$$$$$$$$$$$$$$$$$$$$$$$ Removing Images Ends $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		
		DefaultLogger.debug(this, "##################################################removeIdx#########"+removeIdx+"######################");
		DefaultLogger.debug(this, "##################################################allImagesList#########"+allImagesList+"######################");
		
		
		List<String> custIdList=new ArrayList<String>();
		custIdList = imageTagDaoImpl.getFacilityNames(custId);
		
		List<String> facilityCodeList=new ArrayList<String>();
		facilityCodeList = imageTagDaoImpl.getFacilityCodes(custId);
		
		List<String> otherDocList=new ArrayList<String>();
		otherDocList = imageTagDaoImpl.getOtherDocumentList();
		
		List<String> securityNameIdList=new ArrayList<String>();
		securityNameIdList = imageTagDaoImpl.getSecurityNameIds(custId);
		
		List<String> otherSecDocList=new ArrayList<String>();
		otherSecDocList = imageTagDaoImpl.getSecurityOtherDocumentList ();
		
		List<String> typeOfDocList=new ArrayList<String>();
		typeOfDocList = imageTagDaoImpl.getTypeOfDocumentList ();
		
		List<String> statementDocList=new ArrayList<String>();
		statementDocList = imageTagDaoImpl.getStatementDocumentList ();
		
		List<String> camNumberList=new ArrayList<String>();
		camNumberList = imageTagDaoImpl.getcamNumberList (custId);
		
		List<String> camDocList=new ArrayList<String>();
		camDocList = imageTagDaoImpl.getCamDocumentList ();
		
		List<String> otherMasterDocList=new ArrayList<String>();
		otherMasterDocList = imageTagDaoImpl.getOtherMasterDocumentList ();
		
		List<String> facilityDocNameMasterList=new ArrayList<String>();
		facilityDocNameMasterList = imageTagDaoImpl.getFacilityDocFromMasterList();
		
		List<String> securityDocNameMasterList=new ArrayList<String>();
		securityDocNameMasterList = imageTagDaoImpl.getSecurityDocFromMasterList();
		
		
		
		
		result.put("custIdList", custIdList);
		result.put("facilityCodeList", facilityCodeList);
		result.put("otherDocList", otherDocList);
		result.put("securityNameIdList", securityNameIdList);
		result.put("otherSecDocList", otherSecDocList);
		result.put("typeOfDocList", typeOfDocList);
		result.put("statementDocList", statementDocList);
		result.put("camNumberList", camNumberList);
		result.put("camDocList", camDocList);
		result.put("otherMasterDocList", otherMasterDocList);
		result.put("facilityDocNameMasterList", facilityDocNameMasterList);
		result.put("securityDocNameMasterList", securityDocNameMasterList);
		
		result.put("obImageUploadAddList", new ArrayList());
		result.put("customerID", "-999999999");
		result.put("limitProfileID", "-999999999");
		resultSet.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		DefaultLogger.debug(this, "Going out of doExecute()");
		return resultSet;
	}

}
