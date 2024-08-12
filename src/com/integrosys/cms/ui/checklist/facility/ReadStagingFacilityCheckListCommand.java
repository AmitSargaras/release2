/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facility;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.SharedDocumentsHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/07/20 06:19:09 $ Tag: $Name: $
 */
public class ReadStagingFacilityCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ReadStagingFacilityCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
			GLOBAL_SCOPE },
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", FORM_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "closeFlag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", SERVICE_SCOPE },
				{ "deleteInd", "java.lang.String", SERVICE_SCOPE },
				{ "checkListIDMap", "java.util.HashMap", SERVICE_SCOPE },
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
		HashMap checkListIDMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		String custTypeTrxID = (String) map.get("custTypeTrxID");
		ICheckListTrxValue checkListTrxVal;
		try {
			checkListTrxVal = this.checklistProxyManager.getCheckListByTrxID(custTypeTrxID);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("failed to retrieve checklist by transaction id [" + custTypeTrxID
					+ "]", ex);
		}
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		
      	ILimit[] OB=limit.getLimits();
      	for(int i=0;i<OB.length;i++){
          ILimit obLimit= OB[i];
          long limitId= obLimit.getLimitID();
       
			try {
			  CheckListSearchResult checkList=this.checklistProxyManager.getCheckListByCollateralID(limitId);
			  if(checkList!=null){
				  checkListIDMap.put(new Long(checkList.getCheckListID()), obLimit);
			  }
			} catch (CheckListException e) {
				e.printStackTrace();
				throw new CommandProcessingException("failed to retrieve  checklist ", e);
			}
      	}
		
        resultMap.put("checkListIDMap",checkListIDMap);
		// Sorts checklist before putting into resultMap
		ICheckList checkList = checkListTrxVal.getStagingCheckList();
		SharedDocumentsHelper.mergeViewableCheckListItemIntoStaging(checkListTrxVal.getCheckList(), checkList);
		ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
		checkList.setCheckListItemList(sortedItems);

		resultMap.put("checkList", checkList);
		resultMap.put("checkListTrxVal", checkListTrxVal);
		resultMap.put("ownerObj", checkListTrxVal.getStagingCheckList().getCheckListOwner());
		if(checkList!=null && checkList.getCheckListOwner()!=null){
			resultMap.put("limitID", String.valueOf(((OBCollateralCheckListOwner)checkList.getCheckListOwner()).getCollateralID()));
		}
		resultMap.put("closeFlag", "true");
		resultMap.put("frame", "false");
		
		if (ICMSConstant.STATE_DELETED.equals(checkListTrxVal.getStagingCheckList().getCheckListStatus())) {
			resultMap.put("deleteInd", "true");
		}
		else {
			resultMap.put("deleteInd", "false");
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
