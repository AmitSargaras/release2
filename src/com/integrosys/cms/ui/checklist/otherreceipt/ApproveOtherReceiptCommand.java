/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBTemplateItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLAD;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveOtherReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveOtherReceiptCommand() {
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
					{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
		DefaultLogger.debug(this, "Inside otherreceipt/ApproveOtherReceiptCommand.java doExecute()");
		System.out.println("Inside otherreceipt/ApproveOtherReceiptCommand.java doExecute()");
		try {
			ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
			//ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			//ICheckListItem[] iCheckListItemsActual=checkListTrxVal.getCheckList().getCheckListItemList();
			ICheckListTrxValue checkListTrxVal = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			
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
			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	
			//DefaultLogger.debug(this, "Testing compilation.....................");
			List listDate=new ArrayList();
			DefaultLogger.debug(this, "Testing compilation...........1.......... otherreceipt/ApproveOtherReceiptCommand.java ");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			DefaultLogger.debug(this, "Testing compilation...........2.......... otherreceipt/ApproveOtherReceiptCommand.java ");

			long limitProfileID = limit.getLimitProfileID();
			
			ILimitJdbc limitJdbc = (ILimitJdbc) BeanHouse.get("limitJdbc");
			String cifNumber = limitJdbc.getCifNumberByCmsLimitProfileId(checkListTrxVal.getLimitProfileID());
			
			
			ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			for (int j = 0; j < iCheckListItems.length; j++) {					
				String status=iCheckListItems[j].getDocumentStatus();
				if(status.equals("PENDING_INACTIVE")){
					iCheckListItems[j].setDocumentStatus("INACTIVE");	
				}
				if(iCheckListItems[j].getItemStatus().equals("PENDING_VERSION")){
					iCheckListItems[j].setItemStatus("AWAITING");
				}
				if(iCheckListItems[j].getItemStatus().equals("PENDING_DEFER")){
					if(iCheckListItems[j].getDeferExtendedDate()!=null){
						iCheckListItems[j].setExpectedReturnDate(iCheckListItems[j].getDeferExtendedDate());
						iCheckListItems[j].setDeferExtendedDate(null);
					}
				}
				if(iCheckListItems[j].getItemStatus().equals("PENDING_DISCREPANCY") || iCheckListItems[j].getItemStatus().equals("UPDATE_DISCREPANCY")){
					DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java >>>>>>>>>>>>>>>>>>>> Inside PENDING_DISCREPANCY or UPDATE_DISCREPANCY>"+iCheckListItems[j].getCheckListItemID());
					iCheckListItems[j].setItemStatus("DISCREPANCY");
					
				}	
				
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
		
			DefaultLogger.debug(this, "Testing compilation...........3.......... otherreceipt/ApproveOtherReceiptCommand.java ");

			boolean isReceivedDoc=false;
			for(int i =0;i<iCheckListItems.length;i++){
				if(iCheckListItems[i].getItemStatus().equals("PENDING_RECEIVED")){
					iCheckListItems[i].setItemStatus("RECEIVED");
					/*if(iCheckListItems[i].getExpiryDate()!=null){
						isReceivedDoc=true;
	        			listDate.add(iCheckListItems[i].getExpiryDate());
	        			
	        			}*/
				}
			}
			
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
			
			
			List res = CompareOBUtil.compOBArrayCheckList(checkListTrxVal.getStagingCheckList().getCheckListItemList(),checkListTrxVal.getCheckList().getCheckListItemList());
			ArrayList list = new ArrayList(res);
			ArrayList resultList = new ArrayList();
			for(int i =0;i<list.size();i++){
				CompareResult compareResult =(CompareResult) list.get(i);
				ICheckListItem OB = (ICheckListItem) compareResult.getObj();
				if(compareResult.isAdded()||compareResult.isDeleted()||compareResult.isModified()){
					
					OB.setApprovedBy(user.getLoginID());
					OB.setApprovedDate(applicationDate);
				
				}
				
				resultList.add(OB);
				
			}
			checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[resultList.size()]));
			
			if(listDate!=null){
				Collections.sort(listDate);
			}
			DefaultLogger.debug(this, "Testing compilation...........4.......... otherreceipt/ApproveOtherReceiptCommand.java ");
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
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
			DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Current status-----" + preStatus);
			if (ICMSConstant.STATE_PENDING_AUTH.equals(preStatus)
					|| ICMSConstant.STATE_PENDING_OFFICE.equals(preStatus))
			// approve by OFFICE
			{
				checkListTrxVal.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_CPC_MAKER);
				checkListTrxVal.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_APPROVE);
				checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);
				DefaultLogger.debug(this, "-----After Approve by OFFICE  otherreceipt/ApproveOtherReceiptCommand.java ");

			}
			// End OFFICE
			
			else if (ICMSConstant.STATE_PENDING_MGR_VERIFY.equals(preStatus)) {
				checkListTrxVal = proxy.managerApproveCheckListReceipt(ctx, checkListTrxVal);
			}
			else {
				DefaultLogger.debug(this, "Testing compilation...........10.......... otherreceipt/ApproveOtherReceiptCommand.java ");
				checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
			}
			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// checkListTrxVal);
			DefaultLogger.debug(this, "Testing compilation...........11.......... otherreceipt/ApproveOtherReceiptCommand.java ");
			proxy.updateSharedChecklistStatus(checkListTrxVal); // R1.5 CR17
			DefaultLogger.debug(this, "Testing compilation...........12.......... otherreceipt/ApproveOtherReceiptCommand.java ");
			
			 //Uma Khot::Insurance Deferral maintainance
			
			IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
			String updateInsuranceDetailStg = insuranceGCJdbc.updateInsuranceDetailStg(checkListTrxVal.getCheckList().getCheckListID());
			
			DefaultLogger.debug(this,"otherreceipt/ApproveOtherReceiptCommand.java updateInsuranceDetailStg:"+updateInsuranceDetailStg);
			System.out.println("otherreceipt/ApproveOtherReceiptCommand.java updateInsuranceDetailStg:"+updateInsuranceDetailStg);
			if("success".equals(updateInsuranceDetailStg)){
			String updateInsuranceDetail = insuranceGCJdbc.updateInsuranceDetail(checkListTrxVal.getCheckList().getCheckListID());
			
			DefaultLogger.debug(this,"otherreceipt/ApproveOtherReceiptCommand.java updateInsuranceDetail:"+updateInsuranceDetail);
			System.out.println("otherreceipt/ApproveOtherReceiptCommand.java updateInsuranceDetail:"+updateInsuranceDetail);
			}
			
			
			
			if(isReceivedDoc){
				ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
				DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........5.........."+iladDao);
				if(iladDao!=null){
				List ladList= iladDao.getLADNormal(limitProfileID);
				DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........6.........."+ladList);
				if(ladList.size()==0){
					
					ICheckListTrxValue checkListTrxVal2=null;
					String custCategory = "MAIN_BORROWER";
					String applicationType = "COM";
//					String tCollateralID = "200701010000130";
					long collateralID = 0L;
					ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
							applicationType);
					ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) owner;
					ICheckList checkList=new OBCheckList(colOwner);
					checkList.setCheckListType(ICMSConstant.DOC_TYPE_LAD);
					checkList.setTemplateID(0l);
					ITemplateItem templateItem=new OBTemplateItem();
					OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
					ICheckListItem[] checkListItems=new ICheckListItem[1];
					checkListItems[0]=checkListItem;
					checkListItem.setItemCode("LAD-1");
					checkListItem.setItemDesc("LAD-1");
					checkListItem.setItemStatus("AWAITING");
					checkListItem.setIsInherited(true);
				
				
					
					// By abhijit R  : Remember : Need to add colomn in cms_lad table for checklist id. 
					// and set the above checklist id in lad object.
					
					
		    			Date dateApplication=new Date();
		    			for(int i=0;i<generalParamEntries.length;i++){
		    				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
		    					dateApplication=new Date(generalParamEntries[i].getParamValue());
		    				}
		    			}
					 ILAD ilad= new OBLAD();
				        Date date=(Date)listDate.get(0);
				        ilad.setLimit_profile_id(limitProfileID);
				        //Date changedDueDate = null;
				        // Changes made to logic 
				      //  changedDueDate  = CommonUtil.rollUpDateByYears(date, 3);
				        ilad.setLad_due_date(date);
				        ilad.setLad_name(customer.getCustomerName()+"-"+new Date().getTime());
				        ilad.setStatus("GEN");
				        ilad.setIsOperationAllowed("N");
				  
				        ilad.setGeneration_date(dateApplication);
				        // Setting the lad due date and generation in checklistitem.
				        checkListItem.setIdentifyDate(dateApplication);
				      //  checkListItem.setDocDate(dateApplication);
				        checkListItem.setCompletedDate(date);
				       // checkListItem.setExpiryDate(date);
				    	checkList.setCheckListItemList(checkListItems);
				    	try {
				    		DefaultLogger.debug(this, "Testing compilation...........7.......... otherreceipt/ApproveOtherReceiptCommand.java ");
							checkListTrxVal2 = proxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException(" otherreceipt/ApproveOtherReceiptCommand.java failed to submit checklist creation workflow", ex);
						}
						DefaultLogger.debug(this, "Testing compilation...........8.......... otherreceipt/ApproveOtherReceiptCommand.java ");
					      ilad.setChecklistId(checkListTrxVal2.getCheckList().getCheckListID());
				        
				        ilad=iladDao.createLAD(ilad);
				        DefaultLogger.debug(this, "Testing compilation...........9.......... otherreceipt/ApproveOtherReceiptCommand.java ");
					
					/*  ILADItem iladItem= new OBLADItem();
		        		iladItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
		        	iladItem.setLad_id(ilad.getLad_id());
		        	iladItem=iladDao.createLADItem(iladItem);
		        	
		        		ICheckListItem[] checkListItems=checkListTrxVal.getCheckList().getCheckListItemList();
		        		for(int j=0;j<checkListItems.length;j++){
		        			if(checkListItems[j].getExpiryDate()!=null){
		        			if(checkListItems[j].getItemStatus().equals("RECEIVED")){
		        			ILADSubItem iladSubItem= new OBLADSubItem();
		        			iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
		        			iladSubItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
		        			iladSubItem.setDoc_description(checkListItems[j].getItemDesc());
		        			iladSubItem.setExpiry_date(checkListItems[j].getExpiryDate());
		        			iladDao.createLADSubItem(iladSubItem);
		        			}
		        		}
		        		}*/
		        	}else{

		        		DefaultLogger.debug(this, "Testing compilation...........287.......... otherreceipt/ApproveOtherReceiptCommand.java ");

						 //boolean isNotPreviousGenerated=true;
						 ILAD  ilad=(ILAD)ladList.get(0);
						
						// ilad.setIsOperationAllowed("Y");
						 //Date changedDueDate = null;
						 //For HDFC bank : Not to consider due date of other in LAD By Abhijit R 
						 /*if(listDate!=null && listDate.size()>0){
							 Date date=(Date)listDate.get(0);
							 // changedDueDate  = CommonUtil.rollUpDateByYears(date, 3);
							 if(ilad.getLad_due_date().after(date)){
								 ilad.setLad_due_date(date);	 
							 }
							 
						 }*/
						 ilad=ladProxy.updateLAD(ilad);
						 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........304......ilad...."+ilad);
						 //iladDao.updateLADOperation("Y", limitProfileId);
						 HashMap ladItemId= new HashMap();
						 HashMap ladSubItemId= new HashMap();
						 List ladItemList= ladProxy.getLADItem(ilad.getLad_id());
						 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........309.....ladItemList....."+ladItemList);
						 if(ladItemList!=null){
							 if(ladItemList.size()!=0){
								 for(int i=0;i<ladItemList.size();i++){
									 ILADItem ilad2=(ILADItem)ladItemList.get(i);
									 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........314.....ilad2....."+ilad2);
									 if(ilad2!=null){
										 //ladProxy.deleteLADSubItem(ilad2.getDoc_item_id());
										 ladItemId.put(String.valueOf(ilad2.getDoc_item_id()), String.valueOf(ilad2.getDoc_item_id()));
										 List ladSubItemList= ladProxy.getLADSubItem(ilad2.getDoc_item_id());
										 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........319.....ladSubItemList....."+ladSubItemList);
										 if(ladSubItemList!=null){
											 if(ladSubItemList.size()!=0){
												 for(int ia=0;ia<ladSubItemList.size();ia++){
													 ILADSubItem iladSubItem=(ILADSubItem)ladSubItemList.get(ia);
													 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........324.....iladSubItem....."+iladSubItem);
													 if(iladSubItem!=null){
														 //ladProxy.deleteLADSubItem(ilad2.getDoc_item_id());
														 ladSubItemId.put(String.valueOf(iladSubItem.getDoc_sub_item_id()), String.valueOf(iladSubItem.getDoc_sub_item_id()));
														 
													 }
												 }
											 }
										 }
										 
										 
										 
									 }
								 }
							 }
						 }
						 //ladProxy.deleteLADItem(ilad.getLad_id());
						 //	if(isNotPreviousGenerated){
							 ILADItem iladItem= new OBLADItem();
							 if(checkListTrxVal.getCheckList()!=null){
								 DefaultLogger.debug(this, "Testing compilation...........344.......... otherreceipt/ApproveOtherReceiptCommand.java ");
								 if(ladItemId.containsKey(String.valueOf(checkListTrxVal.getCheckList().getCheckListID()))){
									 
									 if(iCheckListItems!=null){
										 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........348.....iCheckListItems.length....."+iCheckListItems.length);
										 //ICheckListItem[] checkListItems=finalCheckLists[i].getCheckListItemList();
										 for(int j=0;j<iCheckListItems.length;j++){
											 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........351.........."+checkListTrxVal.getCheckList().getCheckListItemList()[j]+"........"+"........j..."+j);
											 DefaultLogger.debug(this, " otherreceipt/ApproveOtherReceiptCommand.java Testing compilation...........352.......iCheckListItems.length..."+String.valueOf(iCheckListItems.length));
											 if(!ladSubItemId.containsKey(String.valueOf(checkListTrxVal.getCheckList().getCheckListItemList()[j].getCheckListItemID()))){
												 DefaultLogger.debug(this, "Testing compilation...........353.......... otherreceipt/ApproveOtherReceiptCommand.java ");
												 if(iCheckListItems[j].getExpiryDate()!=null){
												 DefaultLogger.debug(this, "Testing compilation...........354.......... otherreceipt/ApproveOtherReceiptCommand.java ");
												 if(iCheckListItems[j].getItemStatus().equals("RECEIVED")){
													 if(iCheckListItems[j].getDocumentStatus()!=null && iCheckListItems[j].getDocumentStatus().equals("ACTIVE")){
													 ILADSubItem iladSubItem= new OBLADSubItem();
													 iladSubItem.setDoc_item_id(checkListTrxVal.getCheckList().getCheckListID());
													 iladSubItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
													 iladSubItem.setDoc_description(iCheckListItems[j].getItemDesc());
													 iladSubItem.setExpiry_date(iCheckListItems[j].getExpiryDate());
													 iladSubItem.setDoc_sub_item_id(checkListTrxVal.getCheckList().getCheckListItemList()[j].getCheckListItemID());
													 ladProxy.createLADSubItem(iladSubItem);
												 }
												 }
											 }
										 }
										 }
										 DefaultLogger.debug(this, "Testing compilation...........369.......... otherreceipt/ApproveOtherReceiptCommand.java ");
									 }
									 
									 
								 }else{
									 DefaultLogger.debug(this, "Testing compilation...........373.......... otherreceipt/ApproveOtherReceiptCommand.java ");
									 iladItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
									 iladItem.setLad_id(ilad.getLad_id());
									 iladItem.setDoc_item_id(checkListTrxVal.getCheckList().getCheckListID());
									 iladItem=ladProxy.createLADItem(iladItem);
									 if(iCheckListItems!=null){
										 //ICheckListItem[] checkListItems=finalCheckLists[i].getCheckListItemList();
										 for(int j=0;j<iCheckListItems.length;j++){
											 DefaultLogger.debug(this, "Testing compilation...........381.......... otherreceipt/ApproveOtherReceiptCommand.java ");
											 if(iCheckListItems[j].getExpiryDate()!=null){
												 if(iCheckListItems[j].getItemStatus().equals("RECEIVED")){
													 if(iCheckListItems[j].getDocumentStatus()!=null && iCheckListItems[j].getDocumentStatus().equals("ACTIVE")){
													 ILADSubItem iladSubItem= new OBLADSubItem();
													 iladSubItem.setDoc_item_id(checkListTrxVal.getCheckList().getCheckListID());
													 iladSubItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
													 iladSubItem.setDoc_description(iCheckListItems[j].getItemDesc());
													 iladSubItem.setExpiry_date(iCheckListItems[j].getExpiryDate());
													 ladProxy.createLADSubItem(iladSubItem);
												 }
												 }
											 }
										 }
									 }
									 
								 }
								 
								 

								
								 
								 
								 
							 }

						 //	}

					 
		        		
		        		
		        		
		        		
		        		
		        	
		        	}
				
				}
				
				
				
				}
			
			
			ICheckListItem[] checkListItemList = checkListTrxVal.getCheckList().getCheckListItemList();
			System.out.println("otherreceipt/ApproveOtherReceiptCommand.java Line 532=>checkListItemList.length=>"+checkListItemList.length);
			for(int i = 0; i < checkListItemList.length;i++){
				if( null != checkListItemList[i].getInsuranceId()  && checkListItemList[i].getItemDesc().contains("Property") && null!=checkListTrxVal.getReferenceID()){
					
//					String updateChecklistForAllCustomers = insuranceGCJdbc.updateChecklistForAllCustomers(checkListTrxVal.getReferenceID(),checkListItemList[i].getInsuranceId());
					String updateChecklistForAllCustomers = insuranceGCJdbc.updateChecklistForAllCustomers(checkListTrxVal.getStagingReferenceID(),checkListItemList[i].getInsuranceId());
					String updateChecklistForAllCustomersStg = insuranceGCJdbc.updateChecklistForAllCustomersStg(checkListTrxVal.getReferenceID(),checkListItemList[i].getInsuranceId());
					
				}
				
			}
			
			
			resultMap.put("request.ITrxValue", checkListTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute  otherreceipt/ApproveOtherReceiptCommand.java =>e=>" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute() otherreceipt/ApproveOtherReceiptCommand.java ");
		System.out.println("Going out of doExecute() otherreceipt/ApproveOtherReceiptCommand.java ");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
