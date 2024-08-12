/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/02/04 03:19:21 $ Tag: $Name: $
 */
public class VersionCAMReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	private ICheckListProxyManager checklistProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	
	/**
	 * Default Constructor
	 */
	public VersionCAMReceiptCommand() {
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
                { "isViewFlag", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE }, });
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
		
		ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
		
		ICheckList checkListData = (ICheckList) map.get("session.checkList");
		int index = Integer.parseInt((String) map.get("index"));
		ICheckListItem temp[] = checkListData.getCheckListItemList();
		ICheckListItem checkListItem = temp[index];
		
		int lastVersion=0;
		int maxValue=0;
		String docCode = temp[index].getItemCode();
		int[] int_array= new int[temp.length];		
		ArrayList finalArray = new ArrayList();
		for (int j = 0; j < temp.length; j++) {
			finalArray.add(temp[j]);
			if(temp[j].getItemCode().equalsIgnoreCase(docCode)){
				if(temp[j].getDocumentVersion()!=null && !temp[j].getDocumentVersion().equals("")){
				int_array[j]=Integer.parseInt(temp[j].getDocumentVersion());
				}
			}
				
		}
		Arrays.sort(int_array);
		maxValue=int_array[int_array.length - 1];
		lastVersion=maxValue+1;
		ICheckListItem versionObject = new OBCheckListItem();
		versionObject=versionMe(checkListItem,versionObject,lastVersion);
		/*
		 * Code for Audit trail individual By Abhijit R
		 * 
		 */
		
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		Date d = DateUtil.getDate();
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				 applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}
		
		//date.setTime(d.getTime());
		applicationDate.setHours(d.getHours());
		applicationDate.setMinutes(d.getMinutes());
		applicationDate.setSeconds(d.getSeconds());
		DefaultLogger.debug(this,"date from general param:"+applicationDate);
		DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
		versionObject.setUpdatedBy(user.getLoginID());
		versionObject.setUpdatedDate(applicationDate);
		
		
		/*
		 * 
		 */
		finalArray.add(0,versionObject);
		
		checkListData.setCheckListItemList((ICheckListItem[]) finalArray.toArray(new ICheckListItem[finalArray.size()]));
		
		resultMap.put("session.checkList", checkListData);
		try {
			resultMap.put("checkList", (ICheckList)com.integrosys.cms.app.common.util.CommonUtil.deepClone((ICheckList)checkListData));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		
		
          	ILimit[] OB=limit.getLimits();
          	for(int i=0;i<OB.length;i++){
              ILimit obLimit= OB[i];
              long limitId= obLimit.getLimitID();
           
              try {
            	  CheckListSearchResult checkList=checklistProxyManager.getCheckListByCollateralID(limitId);
				checkListIDMap.put(new Long(limitId), checkList);
				resultMap.put("checkListIDMap",checkListIDMap);
			} catch (CheckListException e) {
				
				e.printStackTrace();
				throw new CommandProcessingException("failed to retrieve  checklist ", e);
			}
          	}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private ICheckListItem versionMe(ICheckListItem mainObject,ICheckListItem versionObject,int lastVersion){

		versionObject.setDocumentVersion(lastVersion+"");
		versionObject.setItem(mainObject.getItem());
		versionObject.setDocumentStatus("ACTIVE");
		versionObject.setItemStatus("PENDING_VERSION");
		versionObject.setItemDesc(mainObject.getItemDesc());
		versionObject.setItemCode(mainObject.getItemCode());
		versionObject.setIsMandatoryInd(mainObject.getIsMandatoryInd());
		versionObject.setIsMandatoryDisplayInd(mainObject.getIsMandatoryDisplayInd());
		versionObject.setIsInherited(mainObject.getIsInherited());
		versionObject.setTenureCount(mainObject.getTenureCount());
		versionObject.setTenureType(mainObject.getTenureType());
		return versionObject;
	}
}
