/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facilityreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreationDao;
import com.integrosys.cms.app.caseCreation.bus.OBCaseCreation;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.proxy.ICaseCreationProxyManager;
import com.integrosys.cms.app.caseCreationUpdate.trx.ICaseCreationTrxValue;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
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
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.OBTemplateItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.LADDaoImpl;
import com.integrosys.cms.app.lad.bus.OBLAD;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.document.bus.CheckListItem;
import com.integrosys.cms.host.eai.limit.bus.CMSTransaction;
import com.integrosys.cms.host.eai.limit.bus.ILimitJdbc;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveFacilityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveFacilityReceiptCommand() {
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
					{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
			ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
			//ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			//ICheckListItem[] iCheckListItemsActual=checkListTrxVal.getCheckList().getCheckListItemList();
			ICheckListTrxValue checkListTrxVal = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	
			//System.out.println("Testing compilation.....................");
			List listDate=new ArrayList();
			HashMap receicedDoc=new HashMap();
			List receicedDocStage=new ArrayList();
			List receicedDocActual=new ArrayList();
			List updateLadList=new ArrayList();
			HashMap receicedDocList=new HashMap();
			//OBCheckListItem receivedDoc=new OBCheckListItem();
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			long id=0 ;
			System.out.println("===============================limitProfileID inside Approve Facility Receipt Cmd is : "+limitProfileID);
			ILimitDAO limitDAO = LimitDAOFactory.getDAO();
			try{
			String limitProfileIDFromTable = limitDAO.checkLimitProfileID();
			if(limitProfileID==Long.parseLong(limitProfileIDFromTable)) {
				 limitDAO.deleteLimitProfileID();
			  }
			}catch(Exception e) {
				System.out.println("Exception is : "+e);
			}
			finally {
				String limitProfileIDFromTable = limitDAO.checkLimitProfileID();
				if(limitProfileID==Long.parseLong(limitProfileIDFromTable)) {
					 limitDAO.deleteLimitProfileID();
				  }
			}
			
			ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			ICheckListItem[] actCheckListItems=checkListTrxVal.getCheckList().getCheckListItemList();
			String checklistOldId = "";
			
			for (int j = 0; j < actCheckListItems.length; j++) {					
				String imStatus=actCheckListItems[j].getFacImageTagUntagImgName();
				if(imStatus != null){
					checklistOldId =String.valueOf(actCheckListItems[j].getCheckListItemID());
				}
			}
			
			
			/* Set the respective status to the document.  */
			for (int j = 0; j < iCheckListItems.length; j++) {					
				String status=iCheckListItems[j].getDocumentStatus();
				if(status.equals("PENDING_INACTIVE")){
					iCheckListItems[j].setDocumentStatus("INACTIVE");	
					updateLadList.add(iCheckListItems[j]);
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
				if(iCheckListItems[j].getItemStatus().equals("UPDATE_RECEIVED")){
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Inside UPDATE_RECEIVED >"+iCheckListItems[j].getCheckListItemID());
					iCheckListItems[j].setItemStatus("RECEIVED");
					updateLadList.add(iCheckListItems[j]);
				}
				
				if(iCheckListItems[j].getItemStatus().equals("PENDING_DISCREPANCY") || iCheckListItems[j].getItemStatus().equals("UPDATE_DISCREPANCY")){
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Inside PENDING_DISCREPANCY or UPDATE_DISCREPANCY>"+iCheckListItems[j].getCheckListItemID());
					iCheckListItems[j].setItemStatus("DISCREPANCY");
					
				}
				
			}
			boolean isReceivedDoc=false;
			for(int i =0;i<iCheckListItems.length;i++){
				if(iCheckListItems[i].getItemStatus().equals("PENDING_RECEIVED")){
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Received Item >"+iCheckListItems[i].getCheckListItemID());
					//here store stage doc which are received.
					isReceivedDoc=true; 
					receicedDocStage.add(iCheckListItems[i]);
					receicedDocList.put(String.valueOf(iCheckListItems[i].getCheckListItemRef()), iCheckListItems[i]);
					iCheckListItems[i].setItemStatus("RECEIVED");					
					if(iCheckListItems[i].getExpiryDate()!=null){
						listDate.add(iCheckListItems[i].getExpiryDate());
	        			}
				}
			}
			
			/*for(int i =0;i<iCheckListItems.length;i++){
				if(iCheckListItems[i].getItemStatus().equals("RECEIVED")){
					listDate.add(iCheckListItems[i].getExpiryDate());
				}
			}*/
			
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> isReceivedDoc >"+isReceivedDoc);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			Date d = DateUtil.getDate();
			
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
			
			/* Start Code for Application Date Change.
			This Code will increament Application Date by 1 to store LAD Generation Date.*/	
		
			//date.setTime(d.getTime());
			applicationDate.setHours(d.getHours());
			applicationDate.setMinutes(d.getMinutes());
			applicationDate.setSeconds(d.getSeconds());
			DefaultLogger.debug(this,"date from general param:"+applicationDate);
			DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
			
			
			List res = CompareOBUtil.compOBArrayCheckList(checkListTrxVal.getStagingCheckList().getCheckListItemList(),checkListTrxVal.getCheckList().getCheckListItemList());
			DefaultLogger.debug(this,"date from general param:>>>>>>>>>>>>>>>>>>>>>>>>>>1<<<<<<<<<<");
			ArrayList list = new ArrayList(res);
			ArrayList resultList = new ArrayList();
			for(int i =0;i<list.size();i++){
				DefaultLogger.debug(this,"date from general param:>>>>>>>>>>>>>>>>>>>>>>>>>>2<<<<<<<<<<"+i);
				CompareResult compareResult =(CompareResult) list.get(i);
				ICheckListItem OB = (ICheckListItem) compareResult.getObj();
				if(compareResult.isAdded()||compareResult.isDeleted()||compareResult.isModified()){
					
					OB.setApprovedBy(user.getLoginID());
					OB.setApprovedDate(applicationDate);
				
				}
				
				resultList.add(OB);
				
			}
			DefaultLogger.debug(this,"date from general param:>>>>>>>>>>>>>>>>>>>>>>>>>>3<<<<<<<<<<");
			checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[resultList.size()]));
			DefaultLogger.debug(this,"date from general param:>>>>>>>>>>>>>>>>>>>>>>>>>>4<<<<<<<<<<");
			if(listDate!=null){
				Collections.sort(listDate);
			}
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			// ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(
			// checkListTrxVal.getStagingCheckList()));
			
			
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
				
				//Autocase creation CR
				/*String loginId=checkListTrxVal.getLoginId();
				String branchCode=getBranchCode(loginId);*/
				String branchCode=user.getEjbBranchCode();
				System.out.println("ApproveFacilityreceiptCommand.java=>initiateCaseCreation()=>user.getEjbBranchCode()=>"+user.getEjbBranchCode());
				List receicedCheckListItems=new ArrayList(); 
				
				if(null!=actCheckListItems && null!=receicedDocList && ! receicedDocList.isEmpty()) {
					for(int i=0;i<actCheckListItems.length;i++) {
						if(receicedDocList.containsKey(String.valueOf(actCheckListItems[i].getCheckListItemRef()))){
							receicedCheckListItems.add(actCheckListItems[i]);
						}
					}
				}
				System.out.println("ApproveFacilityreceiptCommand.java=>initiateCaseCreation()=>branchCode=>"+branchCode);
				if(null!=receicedCheckListItems && ! receicedCheckListItems.isEmpty()) {
					initiateCaseCreation(limitProfileID,ctx,receicedCheckListItems,branchCode);
				}
				//End Autocase creation CR
			}
			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// checkListTrxVal);
			proxy.updateSharedChecklistStatus(checkListTrxVal); // R1.5 CR17
			
			ICheckListItem[] actualList=checkListTrxVal.getCheckList().getCheckListItemList();
			for(int z=0;z<actualList.length;z++){
				//In this  retrieve all received documents from facility receipt from actual list
				// so we can get all approved docs.
				if(actualList[z].getItemStatus().equals("RECEIVED")){		
					receicedDocActual.add(actualList[z]);
					receicedDoc.put(String.valueOf(actualList[z].getCheckListItemRef()), actualList[z]);
					
				}
			}
			
			List finalReceiveDoc=new ArrayList();
			for(int z=0;z<receicedDocStage.size();z++){
				OBCheckListItem obj=(OBCheckListItem)receicedDocStage.get(z);
				if(receicedDoc.containsKey(String.valueOf(obj.getCheckListItemRef()))){
					finalReceiveDoc.add(receicedDoc.get(String.valueOf(obj.getCheckListItemRef())));
				}
			}
			for(int c=0;c<finalReceiveDoc.size();c++){
				OBCheckListItem obj=(OBCheckListItem)finalReceiveDoc.get(c);
				
				int diff = 0;
			Date exp=obj.getExpiryDate();
			Date doc=obj.getDocDate();
			
			if(null!=exp) {
			 diff=exp.getYear()-doc.getYear();
			}
			if(diff>=99){
				finalReceiveDoc.remove(c);
			}
			}
			if(isReceivedDoc){
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> INSIDE IF isReceivedDoc");
				Calendar c=Calendar.getInstance();
				c.setTime(dateApplication);
				c.add(Calendar.DATE, 1);
				dateApplication=c.getTime();
				/*End Code for Application Date Change.*/
				Date date=null;
				Date dateExp=null;
				 if(listDate!=null){
				 DefaultLogger.debug(this, "----------------------listDate---------------------"+listDate.size());
			        if(listDate.size()!=0){	    
			        	Collections.sort(listDate);
				 	date=(Date)listDate.get(0);
			        }
				 }
				 Calendar cnd=Calendar.getInstance();
				 cnd.setTime(date);
				 cnd.add(cnd.YEAR, 3);
				 dateExp=cnd.getTime();
				
				ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
				String facInfo=(String)iladDao.getFacilityInfo(checkListTrxVal.getCheckList().getCheckListID());
				if(iladDao!=null){
				List ladList= iladDao.getLADNormal(limitProfileID);
				
				if(ladList.size()==0){
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> INSIDE FIRST TIME LAD");
					//First Time LAD
					//Below code will create new LAD for first time which is 1st LAD for that party
					ICheckListTrxValue checkListTrxVal2=null;
					String custCategory = "MAIN_BORROWER";
					String applicationType = "COM";
//					String tCollateralID = "200701010000130";
					long collateralID = 0L;
					ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
							applicationType);
					ICollateralCheckListOwner colOwner = (ICollateralCheckListOwner) owner;
					ICheckList checkList=new OBCheckList(colOwner);
					//Added by Pramod for LAD CR
					/*
					 * Below line set display status ="N"of checklist Which will set again in EOD &
					 * its value is "Y"*/
					checkList.setIsDisplay("N");
					//Ended by Pramod
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
					//checkListItem.setIsDisplay("Y");
					 ILAD ilad= new OBLAD();
					 
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
				       // checkListItem.setCompletedDate(date);
				        //checkListItem.setExpiryDate(dateExp);
				       // checkListItem.setExpiryDate(date);
				    	checkList.setCheckListItemList(checkListItems);
				    	try {
							
							checkListTrxVal2 = proxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
							
						}
						catch (CheckListException ex) {
							throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
						}
						
					      ilad.setChecklistId(checkListTrxVal2.getCheckList().getCheckListID());
					     
				        ilad=iladDao.createLAD(ilad);
				        //long id;
				        ICheckListItem[] checkListItemsLAD= checkListTrxVal2.getCheckList().getCheckListItemList();
					      for(int r=0;r<checkListItemsLAD.length;r++){
					    	  if(checkListItemsLAD[r].getItemStatus().equals("AWAITING")){
					    		  id=checkListItemsLAD[r].getCheckListItemID();
					    	  }
					      }
					  ILADItem iladItem= new OBLADItem();
		        		iladItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
		        		iladItem.setDoc_item_id(Long.parseLong(checkListTrxVal.getReferenceID()));
		        	iladItem.setLad_id(ilad.getLad_id());
		        	iladItem=iladDao.createLADItem(iladItem);
		        	
		        		
		        		for(int a=0;a<finalReceiveDoc.size();a++){
		        			OBCheckListItem obj=(OBCheckListItem)finalReceiveDoc.get(a);		        			
		        			ILADSubItem iladSubItem= new OBLADSubItem();
		        			iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
		        			iladSubItem.setCategory("F");
		        			iladSubItem.setDoc_description(obj.getItemDesc());
		        			iladSubItem.setExpiry_date(obj.getExpiryDate());
		        			iladSubItem.setDocChklistAmt(obj.getDocAmt());
		        			iladSubItem.setDocChklistVersion(obj.getDocumentVersion());
		        			//iladSubItem.setIsDisplay("Y");
		        			iladSubItem.setChklistDocItemId(id);
		        			iladSubItem.setDoc_date(obj.getDocDate());
		        			iladSubItem.setDocStatus("ACTIVE");
		        			iladSubItem.setChklistDocId(obj.getCheckListItemID());
		        			if(facInfo!=null && !"".equals(facInfo)){
		        			iladSubItem.setType(facInfo);
		        			}
		        			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> AT LINE 413");
		        			iladDao.createLADSubItem(iladSubItem);
		        		}
		        		//First Time LAD
			        	}else{
			        		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> INSIDE LAD EXSIST");
			        		//Below code is for,if LAD is already created
			        		long checkId=checkListTrxVal.getLimitProfileID();
			        		List ladpendingList=iladDao.checkPendingLAD(checkId);
			        		boolean isPending=false;
			        		for(int s=0;s<ladpendingList.size();s++){
			        			ICheckListItem item=(OBCheckListItem)ladpendingList.get(s);
			        			if(item.getItemStatus().equals("AWAITING")||item.getItemStatus().equals("DEFERRED")){
			        				isPending=true;	
			        				break;
			        			}
			        			
			        		}
			        		/*if(!isPending){
			        			//RECEIVED LAD
			        			//IF LAD is already created and that is Received then create 
			        			//new LAD Document and set its status ='pending' and add received doc to it.
			        			ICheckListTrxValue checkListTrxValStageLad=null; 
			        			ICheckListTrxValue checkListTrxValActualLad=null; 
								List trxIDList=iladDao.getTraxID(checkId);
								long trxID=0;
								for(int s=0;s<trxIDList.size();s++){
									CMSTransaction item=(CMSTransaction)trxIDList.get(s);
									trxID=item.getTransactionID();
				        			}
								ICheckListTrxValue checkListTrxValLAD = proxyManager.getCheckListByTrxID(String.valueOf(trxID));
								OBCheckList actualLADList=(OBCheckList)checkListTrxValLAD.getCheckList();
								
								OBCheckList stageLADList=(OBCheckList)checkListTrxValLAD.getStagingCheckList();
								ICheckListItem[] actualItem=actualLADList.getCheckListItemList();
								
								ITemplateItem templateItem=new OBTemplateItem();
								OBCheckListItem checkListItem = new OBCheckListItem(templateItem);
								OBItem newItem=new OBItem();
								ICheckListItem[] checkListItems=new ICheckListItem[actualItem.length+1];							
								
								for(int e=0;e<actualItem.length;e++){
									checkListItems[e]=actualItem[e];
								}
								
								checkListItems[actualItem.length]=checkListItem;
								String ladNo=String.valueOf(ladpendingList.size()+1);
								newItem.setItemCode("LAD-"+ladNo);
								newItem.setItemDesc("LAD-"+ladNo);
								checkListItem.setItemStatus("AWAITING");
								checkListItem.setIsInherited(true);	
								checkListItem.setIsDisplay("N");
								// Setting the lad due date and generation in checklistitem.
						        checkListItem.setIdentifyDate(dateApplication);
						      //  checkListItem.setDocDate(dateApplication);
						        checkListItem.setCompletedDate(date);
						        checkListItem.setExpiryDate(dateExp);
								//checkListItem.setCheckListItemID(Long.parseLong(checkListTrxValLAD.getStagingReferenceID()));
								checkListItem.setItem(newItem);
								checkListTrxValLAD.getCheckList().setCheckListItemList(checkListItems);
								
								ICheckListTrxValue checkListTrxVal2=null;
								
							    	try {
							    		checkListTrxValStageLad = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxValLAD,checkListTrxValLAD.getCheckList());
									
									}
									catch (CheckListException ex) {
										throw new CommandProcessingException("Fail to create Stage LAD in Facility", ex);
									}
									
								     try{
								    	 checkListTrxValActualLad=proxy.checkerApproveCheckListReceipt(ctx, checkListTrxValStageLad);
								     }
								     catch (CheckListException ex) {
											throw new CommandProcessingException("Fail to create Actual LAD in Facility", ex);
										}
								     
								    
								     
								     List actualId= iladDao.checkPendingLAD(checkListTrxValActualLad.getLimitProfileID());
								     OBCheckListItem finalDoc=null;
								     for(int s=0;s<actualId.size();s++){
								    	 OBCheckListItem docId=(OBCheckListItem)actualId.get(s);
								    	 if(docId.getItemStatus().equals("AWAITING")){
								    		 finalDoc=docId;
								    	 }
								     }
								     OBCheckListItem docId=(OBCheckListItem)actualId.get(0);
								    
								     ILAD ilad= (OBLAD)ladList.get(0);
								     ilad.setLad_due_date(date);
								     ilad=iladDao.updateLAD(ilad);
								     ILADItem iladItem= new OBLADItem();
						        		iladItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
						        		iladItem.setDoc_item_id(Long.parseLong(checkListTrxVal.getReferenceID()));
						        	iladItem.setLad_id(ilad.getLad_id());
						        	iladItem=iladDao.createLADItem(iladItem);
						        	
						        		
						        		for(int a=0;a<finalReceiveDoc.size();a++){
						        			OBCheckListItem obj=(OBCheckListItem)finalReceiveDoc.get(a);		        			
						        			ILADSubItem iladSubItem= new OBLADSubItem();
						        			iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
						        			iladSubItem.setCategory("F");
						        			iladSubItem.setDoc_description(obj.getItemDesc());
						        			iladSubItem.setExpiry_date(obj.getExpiryDate());
						        			iladSubItem.setDocChklistAmt(obj.getDocAmt());
						        			iladSubItem.setDocChklistVersion(obj.getDocumentVersion());
						        			iladSubItem.setIsDisplay("N");
						        			iladSubItem.setChklistDocItemId(finalDoc.getCheckListItemID());
						        			iladSubItem.setDoc_date(obj.getDocDate());
						        			iladSubItem.setDocStatus("ACTIVE");
						        			iladSubItem.setChklistDocId(obj.getCheckListItemID());
						        			if(facInfo!=null && !"".equals(facInfo)){
						        			iladSubItem.setType(facInfo);
						        			}
						        			iladDao.createLADSubItem(iladSubItem);
						        		}
					        		
								   //END OF RECEIVED LAD
					   		}*/if(isPending){
			        			//LAD With Pending state
			        			//If LAD is already created and is in pending state then attach received doc to it.
					   			long ladId=	iladDao.getLadid(limitProfileID);
					   			
					   			
					   			ILADItem iladItem= new OBLADItem();
				        		iladItem.setCategory(checkListTrxVal.getCheckList().getCheckListType());
				        		iladItem.setDoc_item_id(Long.parseLong(checkListTrxVal.getReferenceID()));
				        	iladItem.setLad_id(ladId);
				        	iladItem=iladDao.createLADItem(iladItem);
					   			
					   			
			        		long receivedLAD=iladDao.getReceivedLAD(checkId);
			        		HashMap chkMap=new HashMap();
			        		ILADSubItem ilad=null;
			        		ILADSubItem ilad1=null;
			        		/*for(int a=0;a<receivedLAD.size();a++){	     				
			        				ilad=(ILADSubItem)receivedLAD.get(a);
			        				break;
			        			
			        		}
			        		for(int a=0;a<receivedLAD.size();a++){	     				
		        				ilad1=(ILADSubItem)receivedLAD.get(a);
		        				chkMap.put(String.valueOf(ilad1.getChklistDocId()), ilad1);
		        			
		        		}*/
			        		ICaseCreationDao caseCreationDao1=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
			        	for(int a=0;a<finalReceiveDoc.size();a++){
		        			OBCheckListItem obj=(OBCheckListItem)finalReceiveDoc.get(a);
		        			if(!chkMap.containsKey(String.valueOf(obj.getCheckListItemID()))){
		        				ILADSubItem iladSubItem= new OBLADSubItem();
		        				
		        				String ladid=String.valueOf(obj.getCheckListItemID());
		        				//ILADSubItem iladSubItem2=(OBLADSubItem)receivedLAD.get(ladid);
		        				iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
			        			iladSubItem.setCategory("F");
			        			iladSubItem.setDoc_description(obj.getItemDesc());
			        			iladSubItem.setExpiry_date(obj.getExpiryDate());
			        			iladSubItem.setDocChklistAmt(obj.getDocAmt());
			        			iladSubItem.setDocChklistVersion(obj.getDocumentVersion());
			        			//iladSubItem.setIsDisplay("Y");
			        			iladSubItem.setChklistDocItemId(receivedLAD);
			        			iladSubItem.setDocStatus("ACTIVE");
			        			iladSubItem.setDoc_date(obj.getDocDate());
			        			iladSubItem.setChklistDocId(obj.getCheckListItemID());
			        			if(facInfo!=null && !"".equals(facInfo)){
				        			iladSubItem.setType(facInfo);
				        			}
			        			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> AT LINE 583");
			        			iladDao.createLADSubItem(iladSubItem);
		        				
//		        			if(obj.getFacImageTagUntagId() != null) {
//		        				String imgId = obj.getFacImageTagUntagId();
//		        				String imgStatus = obj.getFacImageTagUntagStatus();
//		        				String imageStatus = "";
//		        				if(imgStatus.equals("Tagged")) {
//		        					imageStatus = "Y";
//		        				}else if(imgStatus.equals("UnTagged")){
//		        					imageStatus = "N";
//		        				}
//		        				String checklistId = String.valueOf(obj.getCheckListItemID());
//		        				System.out.println("Updating cms_image_tag_map untagged_status with info => imgid = "+imgId+" imageStatus = "+imageStatus+" checklist id = "+checklistId);
//		        				caseCreationDao1.updateImageTagUntagStatus(imgId,checklistId,imageStatus);
//		        			}
			        			
			        			/*String facListImages = obj.getFacImageTagUntagImgName();
			        			if(facListImages != null) {
			        			if(!"".equals(facListImages)) {
								 String[] facListImages1 = facListImages.split(",");

								 ArrayList facImageListAdd = new ArrayList();
								 ArrayList facImageStatusListAdd = new ArrayList();
								 
								 for(int i=0;i<facListImages1.length;i=i+3){
									facImageStatusListAdd.add(facListImages1[i+1].trim());
								 	facImageListAdd.add(facListImages1[i+2].trim());
								 }
								 if(!facImageListAdd.isEmpty()) {
								 for(int i=0;i<facImageListAdd.size();i++) {
								 if(obj.getFacImageTagUntagImgName() != null) {
				        				String imgId = (String) facImageListAdd.get(i);
				        				String imgStatus = (String) facImageStatusListAdd.get(i);
				        				String imageStatus = "";
				        				if(imgStatus.equals("UnTagged")) {
				        					imageStatus = "N";
				        				}else if(imgStatus.equals("Tagged")){
				        					imageStatus = "Y";
				        				}
				        				//String checklistId = String.valueOf(obj.getCheckListItemID());
				        				System.out.println("Updating cms_image_tag_map untagged_status with info => imgid = "+imgId+" imageStatus = "+imageStatus+" checklist id = "+checklistOldId);
				        				caseCreationDao1.updateImageTagUntagStatus(imgId,checklistOldId,imageStatus);
				        			}
								 }
								 }
		        			}
		        			}*/
		        			
		        		}
			        	//LAD With Pending state
			        		}
	        }
			        }
				}
			}
			ICaseCreationDao caseCreationDao2=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
			if(updateLadList!=null && updateLadList.size()>0){
				for(int s=0;s<updateLadList.size();s++){
					ICheckListItem obj=(ICheckListItem)updateLadList.get(s);
					 //DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Checklist ID >"+obj.getChklistDocId());
					// ICheckListItem item=CheckListDAOFactory.getCheckListDAO().getCheckListItem(obj.getChklistDocId());
					DateFormat formatter ; 
		    		formatter = new SimpleDateFormat("dd/MMM/yyyy");
					 String amt=obj.getDocAmt();
					 String version=obj.getDocumentVersion();
					 String status=obj.getDocumentStatus();
					/* String docDate=formatter.format(obj.getDocDate());
					 String expiryDate=formatter.format(obj.getExpiryDate()); */
					 
					 //Uma :Prod Issue:start:Added to handle nullPointerException if docDate and expiryDate are null while making Doc as inactive
					 String docDate=null;
					 String expiryDate=null;
					 if(null!=obj.getDocDate()){
					     docDate=formatter.format(obj.getDocDate());
					 }
					 if(null!=obj.getExpiryDate()){
						  expiryDate=formatter.format(obj.getExpiryDate());
					 }
					//Uma :Prod Issue:End:Added to handle nullPointerException if docDate and expiryDate are null while making Doc as inactive
					 
					 for(int i=0;i<actCheckListItems.length;i++){
						if( actCheckListItems[i].getItemDesc().equals(obj.getItemDesc())&&
								(actCheckListItems[i].getDocumentVersion().equals(obj.getDocumentVersion()))){
							
							DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> update id >"+actCheckListItems[i].getCheckListItemID());
							 int count=CheckListDAOFactory.getCheckListDAO().updateLAD(actCheckListItems[i].getCheckListItemID(),version,status,amt,docDate,expiryDate);
							
						}
					 }
					 
					// checklistOldId;
					 
					 /*String facListImages = obj.getFacImageTagUntagImgName();
					 if(facListImages != null) {
					 	if(!"".equals(facListImages)) {
					 String[] facListImages1 = facListImages.split(",");

					 ArrayList facImageListAdd = new ArrayList();
					 ArrayList facImageStatusListAdd = new ArrayList();
					 
					 for(int i=0;i<facListImages1.length;i=i+3){
						facImageStatusListAdd.add(facListImages1[i+1].trim());
					 	facImageListAdd.add(facListImages1[i+2].trim());
					 }
					 if(!facImageListAdd.isEmpty()) {
					 for(int i=0;i<facImageListAdd.size();i++) {
					 if(obj.getFacImageTagUntagImgName() != null) {
	        				String imgId = (String) facImageListAdd.get(i);
	        				String imgStatus = (String) facImageStatusListAdd.get(i);
	        				String imageStatus = "";
	        				if(imgStatus.equals("UnTagged")) {
	        					imageStatus = "N";
	        				}else if(imgStatus.equals("Tagged")){
	        					imageStatus = "Y";
	        				}
	        				//String checklistId = String.valueOf(obj.getCheckListItemID());
	        				System.out.println("Updating cms_image_tag_map untagged_status with info => imgid = "+imgId+" imageStatus = "+imageStatus+" checklist id = "+checklistOldId);
	        				caseCreationDao2.updateImageTagUntagStatus(imgId,checklistOldId,imageStatus);
	        			}
					 }
					 }
				}}*/
					 
					// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> update count >"+count);
				 }
			}
			
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
	//Autocase creation CR
	/*private String getBranchCode(String loginId) {
		String sql="select BRANCH_CODE from CMS_USER where LOGIN_ID ='"+loginId+"' "; 
		DefaultLogger.debug(this, "getBranchCode :" + sql);
		DBUtil dbUtil=null;
		try {
			 dbUtil=new DBUtil();
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					return rs.getString("BRANCH_CODE");
				}
			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}*/
	//Autocase creation CR
	private void initiateCaseCreation(long limitProfileID,OBTrxContext ctx, List receicedCheckListItems,String branchCode) throws Exception {
	
		ICaseCreationTrxValue caseCreationTrxValue=null;
		ICaseCreationDao caseCreationDao=(ICaseCreationDao)BeanHouse.get("caseCreationDao");
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
        IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		Date  applicationDate= new Date(generalParamEntries.getParamValue());
		ICaseCreationProxyManager caseCreationProxyManager = (ICaseCreationProxyManager) BeanHouse.get("caseCreationProxy");
		System.out.println("Inside initiateCaseCreation=>branchCode=>"+branchCode);
			//Staging table entry
			com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation obCaseCreation= new com.integrosys.cms.app.caseCreationUpdate.bus.OBCaseCreation();
			obCaseCreation.setLimitProfileId(limitProfileID);
			obCaseCreation.setCreationDate(applicationDate);
			obCaseCreation.setVersionTime(0L);
			obCaseCreation.setCurrRemarks("");
			obCaseCreation.setPrevRemarks("");
			obCaseCreation.setStatus("OPEN");
			obCaseCreation.setBranchCode(branchCode);
			caseCreationTrxValue=  caseCreationProxyManager.makerCreateCaseCreation(ctx, obCaseCreation);
		
			Iterator itrNew = receicedCheckListItems.iterator();
			while (itrNew.hasNext()) {
				ICheckListItem item = (OBCheckListItem) itrNew.next();
				if(caseCreationTrxValue!=null){
					if(caseCreationTrxValue.getStagingCaseCreation()!=null){
						ICaseCreation creation =new OBCaseCreation();
						creation.setChecklistitemid(item.getCheckListItemID());
						creation.setRemark("");
						creation.setCaseDate(applicationDate);
						creation.setStatus("1");
						creation.setLimitProfileId(limitProfileID);
						creation.setCasecreationid(caseCreationTrxValue.getStagingCaseCreation().getId());
						creation.setRequestedDate(applicationDate);
						creation.setIsAutoCase("Y");
						caseCreationDao.createCaseCreation("stageCaseCreation", creation);
					}
				}
			}
			//Actual Table entry
			caseCreationTrxValue.getStagingCaseCreation().setStatus("Open");
			ICaseCreationTrxValue trxValueOut = caseCreationProxyManager.checkerApproveCaseCreation(ctx, caseCreationTrxValue);
			System.out.println("Inside initiateCaseCreation=>After Actual Table entry=>"+branchCode);
			Iterator itrNew1 = receicedCheckListItems.iterator();
			while (itrNew1.hasNext()) {
				ICheckListItem item = (OBCheckListItem) itrNew1.next();
				if(trxValueOut!=null){
					if(trxValueOut.getCaseCreation()!=null){
						ICaseCreation creation =new OBCaseCreation();
						creation.setChecklistitemid(item.getCheckListItemID());
						creation.setRemark("");
						creation.setCaseDate(applicationDate);
						creation.setStatus("1");
						creation.setLimitProfileId(trxValueOut.getCaseCreation().getLimitProfileId());
						creation.setCasecreationid(trxValueOut.getCaseCreation().getId());
						creation.setRequestedDate(applicationDate);
						creation.setIsAutoCase("Y");
						caseCreationDao.createCaseCreation("actualCaseCreation", creation);
					}
				}
			}
	}

}
