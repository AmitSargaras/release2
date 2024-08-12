/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.LADreceipt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.lad.bus.ILAD;
import com.integrosys.cms.app.lad.bus.ILADDao;
import com.integrosys.cms.app.lad.bus.ILADItem;
import com.integrosys.cms.app.lad.bus.ILADSubItem;
import com.integrosys.cms.app.lad.bus.OBLADItem;
import com.integrosys.cms.app.lad.bus.OBLADSubItem;
import com.integrosys.cms.app.lad.proxy.ILADProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/11 07:26:47 $ Tag: $Name: $
 */
public class PrepareUpdateLADReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdateLADReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
				 { "checkListID1", "java.lang.String", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "isOperationAllowed", "java.lang.String", REQUEST_SCOPE },
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
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "checkListID1", "java.lang.String", SERVICE_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "isOperationAllowed", "java.lang.String", SERVICE_SCOPE },
				{ "docNos", "java.util.ArrayList", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
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
		 List transactionHistoryList= new ArrayList();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			String isOperationAllowed = (String) map.get("isOperationAllowed");
			String tCheckListID = (String) map.get("checkListID");
			if(tCheckListID==null||"".equals(tCheckListID)||"null".equalsIgnoreCase(tCheckListID))
			{
				tCheckListID = (String) map.get("checkListID1");
			}
			long checkListID = Long.parseLong(tCheckListID);
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckList checkList = null;
			OBCollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		//	proxy.getCheckList(checkListID).getStagingCheckList();
//			ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
			int wip = 0;
			DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			String event = (String) map.get("event");
			resultMap.put("event", event);
				
			if ((ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) && (event != null) && event.equals("prepare_update")) {
				resultMap.put("wip", "wip");
			}
			else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					resultMap.put("no_template", "true");
					/*
					 * DefaultLogger.debug(this,
					 * ">>>>>>>>>>>>>>>>>>Checklist id is long.min value");
					 * String legalConstitution = (String)
					 * map.get("legalConstitution"); String limitBkgLoc =
					 * (String) map.get("limitBkgLoc");
					 * DefaultLogger.debug(this, "legalConstitution----------->"
					 * + legalConstitution); DefaultLogger.debug(this,
					 * "limitBkgLoc----------->" + limitBkgLoc); checkList =
					 * proxy
					 * .getDefaultCollateralCheckList(owner,secType,secSubType
					 * ,limitBkgLoc); resultMap.put("checkListTrxVal", null);
					 */
				}
				else {
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is " + checkListID);
					ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();
					if(! (checkListTrxVal.getStatus().trim().equalsIgnoreCase("ACTIVE"))){
						resultMap.put("wip", "wip");
					}
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
					 transactionHistoryList = customerDAO.getTransactionHistoryList(checkListTrxVal.getTransactionID());
					 resultMap.put("transactionHistoryList", transactionHistoryList);
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
					ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					ICheckListItem[] newList=new ICheckListItem[sortedItems.length-1];
					/*boolean chk=false;
					for(int b=0;b<sortedItems.length;b++){
						if(sortedItems[b].getIsDisplay().trim().equals("N")){
							chk=true;
						}						
					}
					if(chk){
					for(int a=0;a<sortedItems.length;a++){
						if(!sortedItems[a].getIsDisplay().trim().equals("N")){
							newList[a]=sortedItems[a];
							}
						}
					}
					
					if(chk){
					checkList.setCheckListItemList(newList);
					}
					else{
						checkList.setCheckListItemList(sortedItems);
					}*/
					checkList.setCheckListItemList(sortedItems);
					/*long docID=sortedItems[sortedItems.length-1].getCheckListItemID();
					ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
					 List ladList=iladDao.getUpdateLad(docID);
					 for(int s=0;s<ladList.size();s++){
						 ILADSubItem obj=(ILADSubItem)ladList.get(s);
						 DateFormat formatter ; 
				    		formatter = new SimpleDateFormat("dd/MMM/yyyy");
						 DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> Checklist ID >"+obj.getChklistDocId());
						 ICheckListItem item=CheckListDAOFactory.getCheckListDAO().getCheckListItem(obj.getChklistDocId());
						 String amt=item.getDocAmt();
						 String version=item.getDocumentVersion();
						 String status=item.getDocumentStatus();
						 String docDate=formatter.format(item.getDocDate());
						 String expiryDate=formatter.format(item.getExpiryDate());
						 DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> update id >"+obj.getChklistDocId());
						 int count=CheckListDAOFactory.getCheckListDAO().updateLAD(obj.getChklistDocId(),version,status,amt,docDate,expiryDate);
						// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>> update count >"+count);
					 }*/
					
				}
				resultMap.put("frame", "true");// used to apply frames
                resultMap.put("checkListForm", checkList);
                resultMap.put("checkList", checkList);
                resultMap.put("checkList", checkList);

				/*if (checkList != null) {
					ArrayList docNos = new ArrayList();
					ICheckListItem[] itemList = checkList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo);
					}
				}*/

				/*// CR-380 starts
				String countryCode = "none";
				if ((checkList != null) && (checkList.getCheckListLocation() != null)
						&& (checkList.getCheckListLocation().getCountryCode() != null)) {
					countryCode = checkList.getCheckListLocation().getCountryCode();
					// System.out.println("Country Code Cmd Class????????:"+
					// countryCode);
				}
				LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
				resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
				resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());
				// CR-380 ends
*/			}
			
			/*ILADDao iladDao= (ILADDao) BeanHouse.get("ladDao");
			ILAD  ilad=(ILAD)iladDao.getLADNormal(limitProfileID).get(0);
			//resultMap.put("isOperationAllowed", ilad.getIsOperationAllowed());
			ILADProxyManager ladProxy =(ILADProxyManager)BeanHouse.get("ladProxy");	*/
			////////////////////////////////////////////////////////////////////////////
			

			/*List listDate=new ArrayList();
			//String alimitProfileId=(String)updateLad.get(String.valueOf(b));
			//long limitProfileId=Long.parseLong(alimitProfileId);
			ILimitProxy limitProxy = LimitProxyFactory.getProxy();
			ILimitProfile limit2 = limitProxy.getLimitProfile(limitProfileID);
			CheckListSearchResult ladCheckList= proxy.getCAMCheckListByCategoryAndProfileID("LAD",limitProfileID);
						
			 ICheckList[] checkLists= proxy.getAllCheckList(limit2);
			 ICheckList[] finalCheckLists =null; 
		     ArrayList expList= new ArrayList();*/
			
			
	//	     DefaultLogger.debug(this, "----------LAD 13-----------"+checkLists);
		     /*if(checkLists!=null){
		    	 finalCheckLists = new ICheckList[checkLists.length];
		    	 int a=0;
		    	 for (int y = 0; y < checkLists.length; y++) {
		    		 if(checkLists[y].getCheckListType().equals("F")||checkLists[y].getCheckListType().equals("S")||checkLists[y].getCheckListType().equals("O")){

		    			 ICheckListItem[] curLadList = (ICheckListItem[]) checkLists[y].getCheckListItemList();
		    			 if(curLadList !=null){
		    				 ArrayList expList2= new ArrayList();
		    				 for (int z = 0; z < curLadList.length; z++) {

		    					 ICheckListItem item = (ICheckListItem) curLadList[z];
		    					 if(item!=null){
		    						 if(item.getItemStatus().equals("RECEIVED")){
		    							 if(item.getDocumentStatus()!=null && item.getDocumentStatus().equals("ACTIVE")){
		    							 if(item.getExpiryDate()!=null){
		    								 expList2.add(item.getExpiryDate());
		    							 }
		    							 }
		    						 }
		    					 }
		    				 }
		    				 if(expList2.size()>0){
		    					 finalCheckLists[a]=checkLists[y];
		    					 a++;
		    				 }
		    			 }
		    		 }
		    	 }
		     }*/
			
			
			// boolean generateLad=false;
		        
		                                                                                            
			 /*ArrayList newItemList = new ArrayList();
				ArrayList oldItemList = new ArrayList(); 
			 if(finalCheckLists!=null && finalCheckLists.length > 0){
				 for(int k=0;k<finalCheckLists.length;k++){
					 if(finalCheckLists[k]!=null){
						 if(!finalCheckLists[k].getCheckListType().equals("O")){
							 if(finalCheckLists[k].getCheckListItemList()!=null){
								 ICheckListItem[] checkListItems=finalCheckLists[k].getCheckListItemList();

								 for(int m=0;m<checkListItems.length;m++){
									 if(checkListItems[m].getExpiryDate()!=null){
										 if(checkListItems[m].getItemStatus().equals("RECEIVED")){
											 if(checkListItems[m].getDocumentStatus()!=null && checkListItems[m].getDocumentStatus().equals("ACTIVE")){
											 if(checkListItems[m].getExpiryDate()!=null){
												 listDate.add(checkListItems[m].getExpiryDate());
												 newItemList.add(checkListItems[m]);
												 generateLad=true;
											 }
											 }
										 }
									 }
								 }
								 Collections.sort(listDate);
							 }
						 }else{

							 if(finalCheckLists[k].getCheckListItemList()!=null){
								 ICheckListItem[] checkListItems=finalCheckLists[k].getCheckListItemList();

								 for(int m=0;m<checkListItems.length;m++){
									 if(checkListItems[m].getExpiryDate()!=null){
										 if(checkListItems[m].getItemStatus().equals("RECEIVED")){
											 if(checkListItems[m].getDocumentStatus()!=null && checkListItems[m].getDocumentStatus().equals("ACTIVE")){
												 newItemList.add(checkListItems[m]);
											 }
										 }
									 }
								 }
								 Collections.sort(listDate);
							 }
						 
						 }
					 }
				 }
			 }*/
			 /*DefaultLogger.debug(this, "newItemList count " + newItemList.size());
			 List ladItemListold= ladProxy.getLADItem(ilad.getLad_id());
			 if(ladItemListold!=null){
				 if(ladItemListold.size()!=0){
					 for(int i=0;i<ladItemListold.size();i++){
						 ILADItem ilad2=(ILADItem)ladItemListold.get(i);
						 if(ilad2!=null){
							 oldItemList.addAll(ladProxy.getLADSubItem(ilad2.getDoc_item_id()));
							 DefaultLogger.debug(this, "oldItemList count " + oldItemList.size());
						 }
					 }
				 }
			 }
			//Janki has added last conditions "listDate.size()>0" on 26 Mar 2012
			 
//			 if(oldItemList.size()!=newItemList.size()){
				 DefaultLogger.debug(this, "Count not matched");
			 if(finalCheckLists!=null && finalCheckLists.length > 0 ){
				 boolean isNotPreviousGenerated=true;
				 //ILAD  ilad=(ILAD)iladDao.getLADNormal(limitProfileId).get(0);
				 if("Y".equals(ilad.getIsOperationAllowed())){
					 isNotPreviousGenerated=false;
				 }
				// ilad.setIsOperationAllowed("Y");
				 //Date changedDueDate = null;
				 if(listDate!=null && listDate.size()>0){
					 Date date=(Date)listDate.get(0);
					 // changedDueDate  = CommonUtil.rollUpDateByYears(date, 3);
					 ilad.setLad_due_date(date);
				 }
				// ilad=ladProxy.updateLAD(ilad);
				 //iladDao.updateLADOperation("Y", limitProfileId);
				 List ladItemList= ladProxy.getLADItem(ilad.getLad_id());
				 if(ladItemList!=null){
					 if(ladItemList.size()!=0){
						 for(int i=0;i<ladItemList.size();i++){
							 ILADItem ilad2=(ILADItem)ladItemList.get(i);
							 if(ilad2!=null){
								 ladProxy.deleteLADSubItem(ilad2.getDoc_item_id());
							 }
						 }
					 }
				 }
				 ladProxy.deleteLADItem(ilad.getLad_id());
				 //	if(isNotPreviousGenerated){
				 for(int i=0;i<finalCheckLists.length;i++){
					 ILADItem iladItem= new OBLADItem();
					 if(finalCheckLists[i]!=null){
						 iladItem.setCategory(finalCheckLists[i].getCheckListType());
						 iladItem.setLad_id(ilad.getLad_id());
						 iladItem.setDoc_item_id(finalCheckLists[i].getCheckListID());
						 DefaultLogger.debug(this, "inserting in lad item table.");
						 iladItem=ladProxy.createLADItem(iladItem);

						 if(finalCheckLists[i].getCheckListItemList()!=null){
							 ICheckListItem[] checkListItems=finalCheckLists[i].getCheckListItemList();
							 for(int j=0;j<checkListItems.length;j++){
								 if(checkListItems[j].getExpiryDate()!=null){
									 if(checkListItems[j].getItemStatus().equals("RECEIVED")){
										 if(checkListItems[j].getDocumentStatus()!=null && checkListItems[j].getDocumentStatus().equals("ACTIVE")){
										 ILADSubItem iladSubItem= new OBLADSubItem();
										 iladSubItem.setDoc_item_id(iladItem.getDoc_item_id());
										 iladSubItem.setCategory(finalCheckLists[i].getCheckListType());
										 iladSubItem.setDoc_description(checkListItems[j].getItemDesc());
										 iladSubItem.setExpiry_date(checkListItems[j].getExpiryDate());
										 iladSubItem.setDoc_sub_item_id(checkListItems[j].getCheckListItemID());
										 DefaultLogger.debug(this, "inserting in lad sub item table.");
										 ladProxy.createLADSubItem(iladSubItem);
										 }
									 }
								 }
							 }
						 }
					 }

				 }
				 //	}

			 }*/
		
//		}
			
			////////////////////////////////////////////////////////////////////////////
			
			
			
			
			
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute of PrepareUpdateLADReceiptCommand " + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		resultMap.put("deferCreditApproverList", getAllDeferCreditApprover());
		resultMap.put("waiverCreditApproverList", getAllWaiveCreditApprover());
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getAllDeferCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List defer = (List)proxy.getAllDeferCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < defer.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)defer.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getAllWaiveCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List waive = (List)proxy.getAllWaiveCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < waive.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)waive.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
