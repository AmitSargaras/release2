/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
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
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.checklist.CheckListHelper;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveRecurrentDocReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveRecurrentDocReceiptCommand() {
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
		DefaultLogger.debug(this, "Inside doExecute() recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java..");
		try {
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			// this.updateShareCheeckListStatus(checkListTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal before approve " +
			// checkListTrxVal);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(
			// checkListTrxVal.getStagingCheckList()));

			/*if (CheckListUtil.isInCountry(ctx.getTeam(), ctx.getLimitProfile())) {
				ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
				ctx.setTrxOrganisationOrigin(ctx.getLimitProfile().getOriginatingLocation().getOrganisationCode());
			}
			else {
				ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(checkListTrxVal.getStagingCheckList()));
				ctx.setTrxOrganisationOrigin(checkListTrxVal.getStagingCheckList().getCheckListLocation()
						.getOrganisationCode());
			}*/

			Map<Long,String> chkItemIdMap = new HashMap<Long,String>();
			
			if(checkListTrxVal.getStagingCheckList().getCheckListItemList() != null && checkListTrxVal.getCheckList().getCheckListItemList() != null) {
				for(ICheckListItem stgChkItem : checkListTrxVal.getStagingCheckList().getCheckListItemList()) {
					for(ICheckListItem actualChkItem : checkListTrxVal.getCheckList().getCheckListItemList()) {
						if(actualChkItem.getItemCode() != null) {
						if(actualChkItem.getItemCode().equals(stgChkItem.getItemCode())) {
							chkItemIdMap.put(stgChkItem.getCheckListItemID(), String.valueOf(actualChkItem.getCheckListItemID()));
						}
						}
					}
				}
			}
			DefaultLogger.debug(this, "recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java.. line no 113.");
			ILimitJdbc limitJdbc = (ILimitJdbc) BeanHouse.get("limitJdbc");
			String cifNumber = limitJdbc.getCifNumberByCmsLimitProfileId(checkListTrxVal.getLimitProfileID());
			
			ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			for (int j = 0; j < iCheckListItems.length; j++) {					
				CheckListHelper.processImageDetailsTrx(iCheckListItems[j].getCheckListItemImageDetail(), iCheckListItems[j].getCheckListItemID(), chkItemIdMap, 
						checkListTrxVal.getCheckList().getCheckListType(), cifNumber, ctx);	
				
				if(iCheckListItems[j].getCheckListItemImageDetail() != null) {
					List<ICheckListItemImageDetail> chkImglist = Arrays.asList(iCheckListItems[j].getCheckListItemImageDetail());
					
					for(ICheckListItemImageDetail dtl : chkImglist) {
						dtl.setIsSelectedInd(ICMSConstant.NO);
					}
					iCheckListItems[j].setCheckListItemImageDetail((ICheckListItemImageDetail[]) chkImglist.toArray(new ICheckListItemImageDetail[0]));
				}
			}
			
			// Begin OFFICE
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java...Current status-----" + preStatus);
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
				DefaultLogger.debug(this, "<<<>>> Before calling checkerApproveCheckListReceipt() recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java");
				checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
				DefaultLogger.debug(this, "<<<>>> After calling checkerApproveCheckListReceipt() recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java");
			}
			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// checkListTrxVal);
			proxy.updateSharedChecklistStatus(checkListTrxVal); // R1.5 CR17
			DefaultLogger.debug(this, "<<<>>> After calling updateSharedChecklistStatus(checkListTrxVal).");
			resultMap.put("request.ITrxValue", checkListTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute() recurrentDocReceipt/ApproveRecurrentDocReceiptCommand.java");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
