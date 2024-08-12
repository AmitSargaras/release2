/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveCAMReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveCAMReceiptCommand() {
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
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
			//ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			//ICheckListItem[] iCheckListItemsActual=checkListTrxVal.getCheckList().getCheckListItemList();
			ICheckListTrxValue checkListTrxVal = proxy.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			

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
			/*This code will set status to documents*/
			
			List res = CompareOBUtil.compOBArrayCheckList(checkListTrxVal.getStagingCheckList().getCheckListItemList(),checkListTrxVal.getCheckList().getCheckListItemList());
			ArrayList list = new ArrayList(res);
			ArrayList resultList = new ArrayList();
			for(int i =0;i<list.size();i++){
				CompareResult compareResult =(CompareResult) list.get(i);
				ICheckListItem OB = (ICheckListItem) compareResult.getObj();
				if(compareResult.isAdded()||compareResult.isDeleted()||compareResult.isModified()){
					if(OB.getItemStatus().equals("PENDING_RECEIVED")){
						OB.setItemStatus("RECEIVED");
					}
					if(OB.getItemStatus().equals("PENDING_VERSION")){
						OB.setItemStatus("AWAITING");
					}
					if(OB.getItemStatus().equals("PENDING_DEFER")){
						if(OB.getDeferExtendedDate()!=null){
							OB.setExpectedReturnDate(OB.getDeferExtendedDate());
							OB.setDeferExtendedDate(null);
						}
					}
					OB.setApprovedBy(user.getLoginID());
					OB.setApprovedDate(applicationDate);
				
				}
				
				resultList.add(OB);
				
			}
			checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[resultList.size()]));
			// this.updateShareCheeckListStatus(checkListTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal before approve " +
			// checkListTrxVal);
			
			Map<Long,String> chkItemIdMap = new HashMap<Long,String>();
			
			if(checkListTrxVal.getStagingCheckList().getCheckListItemList() != null && checkListTrxVal.getCheckList().getCheckListItemList() != null) {
				for(ICheckListItem stgChkItem : checkListTrxVal.getStagingCheckList().getCheckListItemList()) {
					for(ICheckListItem actualChkItem : checkListTrxVal.getCheckList().getCheckListItemList()) {
						if(actualChkItem.getItemCode().equals(stgChkItem.getItemCode())) {
							if(actualChkItem.getDocumentVersion().equals(stgChkItem.getDocumentVersion())) {
								chkItemIdMap.put(stgChkItem.getCheckListItemID(), String.valueOf(actualChkItem.getCheckListItemID()));
							}
						}
					}
				}
			}
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(
			// checkListTrxVal.getStagingCheckList()));
			/*Code to set INACTIVE Status to document
*/
			ILimitJdbc limitJdbc = (ILimitJdbc) BeanHouse.get("limitJdbc");
			String cifNumber = limitJdbc.getCifNumberByCmsLimitProfileId(checkListTrxVal.getLimitProfileID());
			
				ICheckListItem temp[] = checkListTrxVal.getStagingCheckList().getCheckListItemList();
				for (int j = 0; j < temp.length; j++) {					
						String status=temp[j].getDocumentStatus();
						if(status.equals("PENDING_INACTIVE")){
							temp[j].setDocumentStatus("INACTIVE");				
											
					}
					
						CheckListHelper.processImageDetailsTrx(temp[j].getCheckListItemImageDetail(), temp[j].getCheckListItemID(), chkItemIdMap, 
							checkListTrxVal.getCheckList().getCheckListType(), cifNumber, ctx);	
					
					if(temp[j].getCheckListItemImageDetail() != null) {
						List<ICheckListItemImageDetail> chkImglist = Arrays.asList(temp[j].getCheckListItemImageDetail());
						
						for(ICheckListItemImageDetail dtl : chkImglist) {
							dtl.setIsSelectedInd(ICMSConstant.NO);
						}
						temp[j].setCheckListItemImageDetail((ICheckListItemImageDetail[]) chkImglist.toArray(new ICheckListItemImageDetail[0]));
					}		
					
				}
				
			
			
			/*if (CheckListUtil.isInCountry(ctx.getTeam(), ctx.getLimitProfile())) {
				ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
				ctx.setTrxOrganisationOrigin(ctx.getLimitProfile().getOriginatingLocation().getOrganisationCode());
			}
			else {
				ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(checkListTrxVal.getStagingCheckList()));
				ctx.setTrxOrganisationOrigin(checkListTrxVal.getStagingCheckList().getCheckListLocation()
						.getOrganisationCode());
			}*/

			// Begin OFFICE
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "Current status-----" + preStatus);
			if (ICMSConstant.STATE_PENDING_AUTH.equals(preStatus)
					|| ICMSConstant.STATE_PENDING_OFFICE.equals(preStatus))
			// approve by OFFICE
			{
				checkListTrxVal.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_CPC_MAKER);
				checkListTrxVal.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_APPROVE);
				checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);
				DefaultLogger.debug(this, "-----After Approve by OFFICE ");

			}
			// End OFFICE
			else if (ICMSConstant.STATE_PENDING_MGR_VERIFY.equals(preStatus)) {
				checkListTrxVal = proxy.managerApproveCheckListReceipt(ctx, checkListTrxVal);
			}
			else {
				checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
			}
			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// checkListTrxVal);
			proxy.updateSharedChecklistStatus(checkListTrxVal); // R1.5 CR17

			resultMap.put("request.ITrxValue", checkListTrxVal);
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
