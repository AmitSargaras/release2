/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

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
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * 
 * @author $Author: Abhijit R $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectEditImageTagFacReceiptCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IImageTagProxyManager imageTagProxyManager;

	
	

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditImageTagFacReceiptCmd() {
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
				{"IImageTagTrxValue","com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },});
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
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
	        HashMap exceptionMap = new HashMap();
	        IImageTagProxyManager itmageTagProxyManager = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
	        OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	        IImageTagTrxValue trxValueIn = (OBImageTagTrxValue) map.get("IImageTagTrxValue");
	        if(trxValueIn != null) {
	        try {
	        	ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
				ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
				ICheckListTrxValue checkListTrxVal = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
				ICheckListItem[] actCheckListItems=checkListTrxVal.getCheckList().getCheckListItemList();
				ICheckListItem[] actCheckListItems1 = checkListTrxValNew.getStagingCheckList().getCheckListItemList();
				
				
				String checklistOldId = "";
				String imStatus1 = "";
				String statusOfItem = "";
				boolean flag = false;
				for (int j = 0; j < actCheckListItems1.length; j++) {					
					statusOfItem = actCheckListItems1[j].getItemStatus();
					if("UPDATE_RECEIVED".equals(statusOfItem) || "PENDING_RECEIVED".equals(statusOfItem) ) {
						imStatus1=actCheckListItems1[j].getFacImageTagUntagImgName();
						checklistOldId =String.valueOf(actCheckListItems1[j].getCheckListItemID());
						flag = true;
					}
				}
				
//	            String event = (String) map.get("event");
				
				
				Long trxId = Long.valueOf(checkListTrxValNew.getCurrentTrxHistoryID());
				trxId = trxId - 1;
				System.out.println("trxId=>"+trxId);
				String trxds1 = String.valueOf(trxId);
				System.out.println("trxds1=>"+trxds1);
				String statusOfTrxImage = trxValueIn.getStatus();
				String instance = trxValueIn.getInstanceName();
				String trxhistId = trxValueIn.getCurrentTrxHistoryID();
				boolean flagForImageTag = false;
				if(trxds1.equals(trxhistId)) {
					flagForImageTag = true;
					System.out.println("Trx id is similar.");
					System.out.println("CheckerRejectEditImageFacReceiptcmd => statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
				}else {
					System.out.println("Trx id is not similar.");
					System.out.println("CheckerRejectEditImageFacReceiptcmd => statusOfTrxImage=>"+statusOfTrxImage+" instance=>"+instance+" trxIdofimagetag=>"+trxhistId);
				}
				
	            String remarks = (String) map.get("remarks");
	            
	            if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("createPartyRemarksError", new ActionMessage("error.reject.remark"));
					ICMSCustomerTrxValue trxValueInValidate = null;
					
	           }else{
	            ctx.setRemarks(remarks);
	             //for slight change by Abhijit R 
	            if(flag == true && imStatus1 != null && !"".equals(imStatus1)) {
	            	if(flagForImageTag == true) {
	            IImageTagTrxValue trxValueOut = itmageTagProxyManager.checkerRejectImageTag(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            	}
	            }
//	          if(event.equals("checker_reject_edit")){  
//	          IImageTagTrxValue trxValueOut = getImageTagProxy().checkerEditRejectImageTag(ctx, trxValueIn);
//	          resultMap.put("request.ITrxValue", trxValueOut);
//	          }
//	          if(event.equals("checker_reject_delete")){  
//		          IImageTagTrxValue trxValueOut = getImageTagProxy().checkerDeleteRejectImageTag(ctx, trxValueIn);
//		          resultMap.put("request.ITrxValue", trxValueOut);
//	          }
//	          if(event.equals("checker_reject_create")){  
//		          IImageTagTrxValue trxValueOut = getImageTagProxy().checkerCreateRejectImageTag(ctx, trxValueIn);
//		          resultMap.put("request.ITrxValue", trxValueOut);
//	          }
		}  
	            
	        }catch (ImageTagException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        resultMap.put("TrxId", trxValueIn.getTransactionID());
	      }
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
