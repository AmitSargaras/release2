/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.LADreceipt;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBTemplateItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveLADReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveLADReceiptCommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
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
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			// this.updateShareCheeckListStatus(checkListTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal before approve " +
			// checkListTrxVal);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			
			
			if(checkListTrxVal.getStagingCheckList().getCheckListItemList().length!=checkListTrxVal.getCheckList().getCheckListItemList().length){
				
			ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
			ILAD  ilad=(ILAD)iladDao.getLADNormal(limitProfileID).get(0);
			ilad.setGeneration_date(checkListTrxVal.getStagingCheckList().getCheckListItemList()[checkListTrxVal.getStagingCheckList().getCheckListItemList().length-1].getIdentifyDate());
			ilad.setLad_due_date(checkListTrxVal.getStagingCheckList().getCheckListItemList()[checkListTrxVal.getStagingCheckList().getCheckListItemList().length-1].getCompletedDate());
			ilad.setIsOperationAllowed("N");
			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	
			ilad=ladProxy.updateLAD(ilad);
			}
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

			// Begin OFFICE
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "Current status-----" + preStatus);
			ICheckListItem[] stageItem=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			boolean isReceived=false;
			Date date=new Date();
			for(int a=0;a<stageItem.length;a++){
				if(stageItem[a].getItemStatus().equals("PENDING_RECEIVED")){
					isReceived=true;
					date=stageItem[a].getExpiryDate();
					stageItem[a].setItemStatus("RECEIVED");
				}
			}
			if(isReceived){
				IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
				IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
				IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
				Date applicationDate=new Date();
				Date dateApplication=new Date();
				for(int i=0;i<generalParamEntries.length;i++){
					if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
						 applicationDate=new Date(generalParamEntries[i].getParamValue());
						 dateApplication=new Date(generalParamEntries[i].getParamValue());
					}
				}
				ITemplateItem templateItem=new OBTemplateItem();
				OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
				OBItem newItem=new OBItem();
				ICheckListItem[] checkListItems=new ICheckListItem[stageItem.length+1];	
				for(int q=0;q<stageItem.length;q++){
					checkListItems[q]=stageItem[q];
				}
				
				checkListItems[stageItem.length]=checkListItem;
				String ladNo=String.valueOf(stageItem.length+1);
				//Date date=stageItem[stageItem.length-1].getExpiryDate();
				newItem.setItemCode("LAD-"+ladNo);
				newItem.setItemDesc("LAD-"+ladNo);
				checkListItem.setItemStatus("AWAITING");
				checkListItem.setIsInherited(true);	
				//*************Add date values for new lad
			//	checkListItem.setIsDisplay("N");
				// Setting the lad due date and generation in checklistitem.
		        checkListItem.setIdentifyDate(dateApplication);
		      //  checkListItem.setDocDate(dateApplication);
		        checkListItem.setCompletedDate(date);
		       // checkListItem.setExpiryDate(dateExp);
				//checkListItem.setCheckListItemID(Long.parseLong(checkListTrxValLAD.getStagingReferenceID()));
				checkListItem.setItem(newItem);
				checkListTrxVal.getStagingCheckList().setCheckListItemList(checkListItems);
				
			}
			
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
