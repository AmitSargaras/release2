/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICMSLegalEntity;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/20 01:44:56 $ Tag: $Name: $
 */
public class ViewImageCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ViewImageCommand() {
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
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "index", "java.lang.String", REQUEST_SCOPE },
			{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }
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
				{ "tagDetailList", "java.util.List", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				//	{ "SecurityReceiptForm", "com.integrosys.cms.ui.checklist.secreceipt.SecurityReceiptForm", FORM_SCOPE },
					{ "tagDetailList", "java.util.List", SERVICE_SCOPE }
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICheckList checkList = (ICheckList) map.get("checkList");
			int index = Integer.parseInt((String) map.get("index"));
			ICheckListItem temp[] = checkList.getCheckListItemList();
			String id = String.valueOf(temp[index].getCheckListItemID());
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			String event = (String) map.get("event");
			ICMSLegalEntity obLegalEntity=(ICMSLegalEntity)cust.getLegalEntity();
			DefaultLogger.debug(this, "Inside doExecute() AND EVENT IS "+event);
			
			IImageTagDao tagDao =(IImageTagDao)BeanHouse.get("imageTagDao"); 
			IImageTagDetails existingTagDetails=tagDao.getCustImageListForView("actualOBImageTagDetails", obLegalEntity.getLEReference(), IImageTagConstants.FACILITY_DOC,id);
			if(existingTagDetails!=null){
					
					List tagDetailList=tagDao.getTagImageList("actualOBImageTagMap",String.valueOf(existingTagDetails.getId()), "N");
					
					if(tagDetailList != null) {
						if(!tagDetailList.isEmpty()) {
					for(int i=0;i<tagDetailList.size();i++) {
						OBImageUploadAdd sObject = (OBImageUploadAdd) tagDetailList.get(i);
						
						
						ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
						String categoryName = imageTagDaoImpl.getEntryNameFromCode(sObject.getCategory());
						sObject.setTypeOfDocument(categoryName);
					}
						}
					}
					
					resultMap.put("tagDetailList", tagDetailList);
			}
			
			resultMap.put("index", String.valueOf(index));
			resultMap.put("event", event);
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	

}
