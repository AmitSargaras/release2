package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;

/**
 * $Author: Abhijit R $ Command for checker to approve edit .
 */

public class CheckerApproveCreateImageTagSecReceiptCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IImageTagProxyManager imageTagProxyManager;

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(
			IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateImageTagSecReceiptCmd() {
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
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE }, 
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
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
		return (new String[][] { { "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
		try {
			
			ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
			ICheckListTrxValue checkListTrxVal = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			ICheckListItem[] actCheckListItems=checkListTrxVal.getCheckList().getCheckListItemList();
			ICheckListItem[] actCheckListItems1 = checkListTrxValNew.getStagingCheckList().getCheckListItemList();
			String checklistOldId = "";
			String imStatus = "";
			String imStatus1 = "";
			String imageId = "";
			String statusOfItem = "";
			boolean flag = false;
//			for (int j = 0; j < actCheckListItems.length; j++) {
			for (int j = 0; j < actCheckListItems1.length; j++) {
				
				statusOfItem = actCheckListItems1[j].getItemStatus();
				if("UPDATE_RECEIVED".equals(statusOfItem) || "PENDING_RECEIVED".equals(statusOfItem) ) {
					imStatus1=actCheckListItems1[j].getSecImageTagUntagImgName();
					checklistOldId =String.valueOf(actCheckListItems1[j].getCheckListItemID());
					flag = true;
				}
//				if(imStatus1 != null){
//					imStatus = imStatus1;
//					checklistOldId =String.valueOf(actCheckListItems[j].getCheckListItemID());
//				}
			}
			
//			String[] facListImages1 = imStatus.split(",");
//			for(int i=0;i<facListImages1.length;i=i+3){
//				imageId = imageId +	facListImages1[i+2] + ",";
//			}
			
			Long trxId = Long.valueOf(checkListTrxValNew.getCurrentTrxHistoryID());
			trxId = trxId - 1;
			System.out.println("checkerapproveimagesecreceiptcmd => trxId=>"+trxId);
			String trxds1 = String.valueOf(trxId);
			System.out.println("checkerapproveimagesecreceiptcmd => trxds1=>"+trxds1);

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// Image Tag Trx value
			IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map
					.get("IImageTagTrxValue");
			if(trxValueIn != null) {
//			String imageId = map.get("imageId").toString();
			
			String statusOfTrxImage = trxValueIn.getStatus();
			String instance = trxValueIn.getInstanceName();
			String trxhistId = trxValueIn.getCurrentTrxHistoryID();
			boolean flagForImageTag = false;
			if(trxds1.equals(trxhistId)) {
				flagForImageTag = true;
				System.out.println("Trx id is similar.");
				System.out.println("checkerapproveimagesecreceiptcmd => statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
			}else {
				System.out.println("Trx id is not similar.");
				System.out.println("checkerapproveimagesecreceiptcmd => statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
			}
			
			
			String fromPage = "TAG";
			
			SearchResult searchResult=(SearchResult) map.get("searchResult");
			Collection resultList = null;
			if (searchResult != null) {
				resultList = searchResult.getResultList();
			}
			if(flag == true && imStatus1 != null && !"".equals(imStatus1)) {
//			if(imStatus != null) {
			if(resultList != null) {
				
			if(flagForImageTag == true) {
			String[] imageIdArray = new String[resultList.size()] ;
			Iterator  itr =resultList.iterator();
			int ab=0;
			while (itr.hasNext()){
				
				OBImageUploadAdd  obImageUploadAdd=(OBImageUploadAdd)itr.next();
				imageIdArray[ab]=Long.toString(obImageUploadAdd.getImgId());
				ab++;
			}
			
			IImageTagMap imageTagMapvalues = new OBImageTagMap();
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function to approve updated Image Tag Trx
			IImageTagTrxValue trxValueOut = itmageTagProxyManager.checkerApproveImageTag(ctx, trxValueIn);
			if (trxValueOut.getReferenceID() != null) {
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getReferenceID()));
			} else {
				imageTagMapvalues.setTagId(Long.parseLong(trxValueOut.getStagingReferenceID()));
			}
			if(IImageTagConstants.TAG.equals(fromPage)){
				//Retriving existing image list
				List imageList = new ArrayList();
				imageList = itmageTagProxyManager.getTagImageList(Long.toString(imageTagMapvalues.getTagId()),IImageTagConstants.STATUS_ALL);
				HashMap existingImages=new HashMap(); 
				if(imageList!=null && imageList.size()>0){
					for (int i = 0; i < imageList.size(); i++) {
						IImageUploadAdd imageDetail = (IImageUploadAdd) imageList.get(i);
						existingImages.put(Long.toString(imageDetail.getImgId()),imageDetail);
					} 
				}
				
				
				for (int i = 0; i < imageIdArray.length; i++) {
					if (!(imageIdArray[i].equals(""))) {
						//Skipping the image tag map creation if it exist
						if(!existingImages.containsKey(imageIdArray[i])){
							imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
							itmageTagProxyManager.checkerCreateImageTagMap(imageTagMapvalues);
						}else{
							//
							imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
							imageTagMapvalues.setUntaggedStatus(IImageTagConstants.STATUS_NO);
							itmageTagProxyManager.checkerApproveUpdateImageTagMap(imageTagMapvalues);
						}
					}
				}
		}else if(IImageTagConstants.UNTAG.equals(fromPage)){
			for (int i = 0; i < imageIdArray.length; i++) {
				if (!(imageIdArray[i].equals(""))) {
						imageTagMapvalues.setImageId(Long.parseLong(imageIdArray[i]));
						imageTagMapvalues.setUntaggedStatus("Y");
						itmageTagProxyManager.checkerApproveUpdateImageTagMap(imageTagMapvalues);
				}
			}
		}
			resultMap.put("request.ITrxValue", trxValueOut);
		}}
			}}
		} catch (SystemBankException ex) {
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

	private String[] commaSeparatedStringToStringArray(String aString) {
		String[] splittArray = null;
		if (aString != null && !aString.equalsIgnoreCase("")) {
			splittArray = aString.split("\\,");
		}
		return splittArray;
	}
}
