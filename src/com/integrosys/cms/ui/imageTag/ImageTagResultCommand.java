package com.integrosys.cms.ui.imageTag;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.CollateralComparator;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.OBCollateral;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMasterJdbc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.OBImageUploadAdd;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.LimitListSummaryItemBase;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.SecurityTypeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

/**
 * This Command Shows result set of Image Tag
 * 
 * @author Anil Pandey
 */

public class ImageTagResultCommand extends AbstractCommand {
	private IImageTagProxyManager imageTagProxyManager;
	private ICheckListProxyManager checklistProxyManager;
	
	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public IImageTagProxyManager getImageTagProxyManager() {
		return imageTagProxyManager;
	}

	public void setImageTagProxyManager(IImageTagProxyManager imageTagProxyManager) {
		this.imageTagProxyManager = imageTagProxyManager;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
			
				{ "imageId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
//				{ "transactionID", "java.lang.String", REQUEST_SCOPE },
//				{ IGlobalConstant.GLOBAL_TRX_ID, "java.lang.String",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ,"com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
//				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE },
//				{"customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
//				{"customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
//				{ "ImageTagAddObj","com.integrosys.cms.app.imageTag.bus.OBImageTagAdd",FORM_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
//				{IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "frompage", "java.lang.String", REQUEST_SCOPE },
				{ "reset", "java.lang.String", REQUEST_SCOPE },
//				{ "from", "java.lang.String", REQUEST_SCOPE },
//				{ "indicator", "java.lang.String", REQUEST_SCOPE }, 
				});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				//Added By Anil Start
				{ "custId", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "custLimitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "documentItemList", "java.util.List", REQUEST_SCOPE },
				{ "facilityIdList", "java.util.List", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secTypeList", "java.util.List", REQUEST_SCOPE },
				{ "secSubtypeList", "java.util.List", REQUEST_SCOPE },
				{ "securityIdList", "java.util.List", REQUEST_SCOPE },
				{ "facilityDocTagModuleList", "java.util.List", REQUEST_SCOPE },
//				{ "securityOb", "java.util.List", REQUEST_SCOPE },
//				{ "customerObList", "java.util.Collection", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "obImageTagAddList", "java.util.ArrayList", SERVICE_SCOPE },
				
				{ "ImageTagMapObj", "com.integrosys.cms.app.imageTag.bus.IImageTagDetails", FORM_SCOPE },
				{ "subfolderNameList", "java.util.List", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "searchResult", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "docNameList", "java.util.List", REQUEST_SCOPE },
				// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
				{ "taggedImageIdSet", "java.util.HashSet", SERVICE_SCOPE },
				// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
				{"bankList","java.util.List",SERVICE_SCOPE },
				{ "custIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "facilityCodeList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "securityNameIdList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "otherSecDocList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "statementDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camNumberList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "camDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "securityDocTagModuleList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "otherMasterDocList", "java.util.ArrayList", REQUEST_SCOPE },
				{ "typeOfDocList", "java.util.ArrayList", REQUEST_SCOPE },

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------1.1.1-------->" +DateUtil.getDate().getTime());
		HashMap returnMap = new HashMap();
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap collateralCodeMap = getCollateralInfo();
		String category=(String) map.get("category");
		String startIdx = (String) map.get("startIndex");
		String imageId =(String) map.get("imageId");
		String unCheckId =(String) map.get("unCheckId");
		String event = (String) map.get("event");
		String reset = (String) map.get("reset");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
		selectedArrayMap = new HashMap();
		}
		
		if("Y".equals(reset)){
			selectedArrayMap.clear();
		}
		
		List obImageTagAddList = new ArrayList();
		//retriving image list available for tagging
		obImageTagAddList =(List) map.get("obImageTagAddList");
		result.put("startIndex", startIdx);
		IImageTagDetails details=(OBImageTagDetails) map.get("ImageTagMapObj");
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
 
		String strLimitProfileId=(String) map.get("custLimitProfileID");
		long limitProfileID=Long.parseLong(strLimitProfileId);
		DefaultLogger.debug(this, "selectedArrayMap.size()============ImageTagResultCommand============1====================>"+selectedArrayMap.size());
		try{
		if(details!=null){
			DefaultLogger.debug(this, "details.getCategory()============================================>"+details.getCategory());
			DefaultLogger.debug(this, "details.getSelectedArray()============================================>"+details.getSelectedArray());
			category=details.getCategory();
			String selectedArrayString=details.getSelectedArray();
			if(selectedArrayString!=null && !selectedArrayString.equals("") ){
			String[] selected=selectedArrayString.split("-");
			if(selected!=null){
			for(int k=0;k<selected.length;k++){
				OBImageUploadAdd uploadImage = new OBImageUploadAdd();
				if(obImageTagAddList!= null)
				{
			 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(selected[k])-1);
				}
			selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
			}
			}
			}else{
				DefaultLogger.debug(this, "ImageTagResultCommand====================193========================>"+imageId);
			if(imageId!=null && !imageId.equals("")){
				String[] selected=imageId.split("-");
				if(selected!=null){
				for(int k=0;k<selected.length;k++){
					OBImageUploadAdd uploadImage = new OBImageUploadAdd();
					if(obImageTagAddList!= null)
					{
				 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(selected[k])-1);
					}
				selectedArrayMap.put(String.valueOf(uploadImage.getImgId()),String.valueOf(uploadImage.getImgId()));
				}
				}
			}
				
			}
			
			String unCheckArrayString=details.getUnCheckArray();
			if(unCheckArrayString!=null && !unCheckArrayString.equals("")){
				String[] unchecked=unCheckArrayString.split("-");
				if(unchecked!=null){
					for(int ak=0;ak<unchecked.length;ak++){
						OBImageUploadAdd uploadImage = new OBImageUploadAdd();
						if(obImageTagAddList!= null)
						{
					 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(unchecked[ak])-1);
						}
						selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
					}
					}
			}else{
				DefaultLogger.debug(this, "ImageTagResultCommand====================214========================>"+unCheckId);
				if(unCheckId!=null && !unCheckId.equals("")){
					String[] unchecked=unCheckId.split("-");
					if(unchecked!=null){
						for(int ak=0;ak<unchecked.length;ak++){
							OBImageUploadAdd uploadImage = new OBImageUploadAdd();
							if(obImageTagAddList!= null)
							{
						 uploadImage = (OBImageUploadAdd)obImageTagAddList.get(Integer.parseInt(unchecked[ak])-1);
							}
							selectedArrayMap.remove(String.valueOf(uploadImage.getImgId()));
						}
						}
				}
				
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		DefaultLogger.debug(this, "selectedArrayMap.size()============ImageTagResultCommand=============2===================>"+selectedArrayMap.size());
		result.put("selectedArrayMap", selectedArrayMap);
		String custId = (String) map.get("custId");
		result.put("custId", custId);
		if(!"".equals(category)){
		result.put("category", category);

		try {
			List proxyMgr = new ArrayList();
			//retriving image list available for tagging
			if("paginate".equals(event)){
				proxyMgr =(List) map.get("obImageTagAddList");
			}else{
				proxyMgr = (List) getImageTagProxyManager().getCustImageListByCriteria(details);
				
			}
			ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
			if(("list_tag_page".equals(event) && !proxyMgr.isEmpty())) {
				OBImageUploadAdd obAdd = new OBImageUploadAdd();
				String typeOfDoc = "";
				for(int i=0;i<proxyMgr.size();i++) {
					obAdd = (OBImageUploadAdd) proxyMgr.get(i);
					typeOfDoc = imageTagDaoImpl.getEntryNameFromCode(obAdd.getCategory());
					obAdd.setTypeOfDocument(typeOfDoc);
				}
			}
			
			result.put("obImageTagAddList", proxyMgr);
			SearchResult searchResult= new SearchResult(0, proxyMgr.size(), proxyMgr.size(), proxyMgr);
			
			// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
			DefaultLogger.debug(this, "search result iterator started");
			List<String> imageIdList =new ArrayList<String>();
			if(null!=searchResult){
				Collection<OBImageUploadAdd> resultList = searchResult.getResultList();
				DefaultLogger.debug(this, "searchResult.getResultList():::"+searchResult.getResultList());
			
					Iterator<OBImageUploadAdd> iterator = resultList.iterator();
					DefaultLogger.debug(this, "iterator initialized():::");
					
					while(iterator.hasNext()){
						OBImageUploadAdd oBImageUploadAdd=	 iterator.next();
						imageIdList.add(String.valueOf(oBImageUploadAdd.getImgId()));
							
						}
					}
			DefaultLogger.debug(this, "while loop completed:::");
			
			List<String> tagIdList = getImageTagProxyManager().getTagId(custId);
			DefaultLogger.debug(this, "tagIdList().size():::"+tagIdList.size());
			DefaultLogger.debug(this, "imageIdList.size():::"+imageIdList.size());
			
			Set<String> taggedImageIdSet=new HashSet<String>();
			int batchSize=200;
			if(null!=tagIdList && null!=imageIdList){
				for(int i=0; i<imageIdList.size();i+=batchSize){
					
					List<String> imageIdListBatch=imageIdList.subList(i, i+batchSize > imageIdList.size()? imageIdList.size() : i+batchSize );
					DefaultLogger.debug(this, "tagIdList.size():::"+tagIdList.size());
					for(int j=0 ; j< tagIdList.size() ; j+=batchSize){
						List<String> tagIdListBatch= tagIdList.subList(j, j+batchSize > tagIdList.size()? tagIdList.size() : j+batchSize );
						DefaultLogger.debug(this, "tagIdListBatch.size():::"+tagIdListBatch.size());
						taggedImageIdSet.addAll(getImageTagProxyManager().getTaggedImageId(imageIdListBatch, tagIdListBatch));
					}
					DefaultLogger.debug(this, "inner for loop completed");
				}
			}
			result.put("taggedImageIdSet", taggedImageIdSet);
			// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
			result.put("searchResult", searchResult);
			DefaultLogger.debug(this, "try block completed");
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			DefaultLogger.error(this, e);
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

		List documentItemList = new ArrayList();	
		List facilityIdList = new ArrayList();	
		List secTypeList = new ArrayList();	
		List secSubtypeList = new ArrayList();	
		List securityIdList = new ArrayList();	
		
		DefaultLogger.debug(this, "Details::"+details);
		DefaultLogger.debug(this, "Before checking DocType"+details.getDocType());
		if(details!=null && !"".equals(details.getDocType())){
			DefaultLogger.debug(this, "Got the form ");
			String docType = details.getDocType();
			 if(IImageTagConstants.CAM_DOC.equals(docType)){
				 //In case of doc type CAM  populate the documentItemList only
				 DefaultLogger.debug(this, "docType1:::"+docType);
					try {
						CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
						if(camCheckList!=null){
							 	long camCheckListID = camCheckList.getCheckListID();
								ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
								ICheckList checkList = checkListTrxValue.getCheckList();
								ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
								for (int j = 0; j < checkListItemList.length; j++) {
									ICheckListItem iCheckListItem = checkListItemList[j];
									DefaultLogger.debug(this, "In Test 6. Got the item list ");
									DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
									DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
									DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
									String label=iCheckListItem.getItemDesc();
									String value= String.valueOf(iCheckListItem.getCheckListItemID());
									LabelValueBean lvBean = new LabelValueBean(label,value);
									documentItemList.add(lvBean);
								}
						}/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
					} catch (CheckListException e) {
						e.printStackTrace();
					}
					
			}else if(IImageTagConstants.CAM_NOTE.equals(docType) ){
				DefaultLogger.debug(this, "docType2:::"+docType);
				//			For doc type CAM			
				try {
					
					HashMap camCheckListMap = CheckListDAOFactory.getCheckListDAO().getBulkCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
					ArrayList camCheckListArray = new ArrayList();
					camCheckListArray=(ArrayList) camCheckListMap.get("NORMAL_LIST");
					
					if(camCheckListArray!=null){
						for (int j = 0; j < camCheckListArray.size(); j++) {
							CheckListSearchResult checkListSearchResult =(CheckListSearchResult) camCheckListArray.get(j);
							DefaultLogger.debug(this, "In Test 6. Got the item list ");
							DefaultLogger.debug(this, "=="+checkListSearchResult.getCamNumber());
							DefaultLogger.debug(this, "=="+checkListSearchResult.getCamType()+"-"+checkListSearchResult.getCamNumber()+"-"+checkListSearchResult.getCamDate());
							DefaultLogger.debug(this, "Going out of Test 6. ");
//							String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							String label=checkListSearchResult.getCamNumber();
							String value= checkListSearchResult.getCamNumber()+"-"+checkListSearchResult.getCamType()+"-"+checkListSearchResult.getCamDate();
							LabelValueBean lvBean = new LabelValueBean(label,value);
							documentItemList.add(lvBean);
							
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(IImageTagConstants.RECURRENTDOC_DOC.equals(docType)){
				DefaultLogger.debug(this, "docType3:::"+docType);
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("REC",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.OTHER_DOC.equals(docType)){
				 DefaultLogger.debug(this, "docType4:::"+docType);
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }else if(IImageTagConstants.LAD_DOC.equals(docType)){
				 DefaultLogger.debug(this, "docType5:::"+docType);
				 //In case of doc type CAM  populate the documentItemList only
				 try {
					 CheckListSearchResult camCheckList= this.checklistProxyManager.getCAMCheckListByCategoryAndProfileID("O",limitProfileID);
					 if(camCheckList!=null){
						 long camCheckListID = camCheckList.getCheckListID();
						 ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(camCheckListID);
						 ICheckList checkList = checkListTrxValue.getCheckList();
						 ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
						 for (int j = 0; j < checkListItemList.length; j++) {
							 ICheckListItem iCheckListItem = checkListItemList[j];
							 DefaultLogger.debug(this, "In Test 6. Got the item list ");
							 DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
							 DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
							 DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
							 String label=iCheckListItem.getItemDesc();
							 String value= String.valueOf(iCheckListItem.getCheckListItemID());
							 LabelValueBean lvBean = new LabelValueBean(label,value);
							 documentItemList.add(lvBean);
						 }
					 }/*else{
							//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
						}*/
				 } catch (CheckListException e) {
					 e.printStackTrace();
				 }
				 
			 }
			 else if(IImageTagConstants.FACILITY_DOC.equals(docType)){
				 DefaultLogger.debug(this, "docType6:::"+docType);
				// In case of doc type Facility populate the facilityIdList
				
				MILimitUIHelper helper = new MILimitUIHelper();
				SBMILmtProxy proxy = helper.getSBMILmtProxy();
				try {
					List lmtList = proxy.getLimitSummaryListByAA(Long.toString(limitProfileID));
					if(lmtList!=null && lmtList.size()>0){
						String label;
						String value;
						for (int i = 0; i < lmtList.size(); i++) {
							LimitListSummaryItemBase limitSummaryItem=(LimitListSummaryItemBase) lmtList.get(i);
							label=limitSummaryItem.getCmsLimitId() +" - "+limitSummaryItem.getProdTypeCode();
							value= limitSummaryItem.getCmsLimitId();
							LabelValueBean lvBean = new LabelValueBean(label,value);
							facilityIdList.add(lvBean);
						}
					}
				} catch (LimitException e) {
					e.printStackTrace();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				// if user has selected the facility id populate the documentItemList
				if(details.getFacilityId()!=0){
					try {
						CheckListSearchResult checkListSearchResult=checklistProxyManager.getCheckListByCollateralID(details.getFacilityId());
						if(checkListSearchResult!=null){
							long facilityCheckListID = checkListSearchResult.getCheckListID();
							
							if(facilityCheckListID!=ICMSConstant.LONG_INVALID_VALUE){
								ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(facilityCheckListID);
								ICheckList checkList = checkListTrxValue.getCheckList();
								ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
								for (int j = 0; j < checkListItemList.length; j++) {
									ICheckListItem iCheckListItem = checkListItemList[j];
									DefaultLogger.debug(this, "In Test 6. Got the item list ");
									DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
									DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
									DefaultLogger.debug(this, "Going out of Test 6. ");
//									String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
									String label=iCheckListItem.getItemDesc();
									String value= String.valueOf(iCheckListItem.getCheckListItemID());
									LabelValueBean lvBean = new LabelValueBean(label,value);
									documentItemList.add(lvBean);
								}
							}
						}/*else{
						//commented will be used to show proper message
							exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","Facility"));
						}*/

					  } catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (CheckListException e) {
						e.printStackTrace();
					}		

				}
			}else if(IImageTagConstants.SECURITY_DOC.equals(docType)){
				DefaultLogger.debug(this, "docType7:::"+docType);
				//if doc type is Security  populate the secTypeList
				secTypeList=getSecurityTypeList();
				//check if secType is selected then populate the secSubtypeList 
				if(!"".equals(details.getSecType())){
					secSubtypeList= getSecuritySubtypeList(details.getSecType());
					//check if secSubtype is selected then populate the securityIdList 
					if(!"".equals(details.getSecSubtype())){
						HashMap lmtcolmap = new HashMap();
						ILimitProxy limitProxy = LimitProxyFactory.getProxy();
						ILimitProfile limitProfileOB=new OBLimitProfile();
						try {
							limitProfileOB = limitProxy.getLimitProfile(limitProfileID);
						} catch (LimitException e1) {
							e1.printStackTrace();
						}

						lmtcolmap = limitProxy.getCollateralLimitMap(limitProfileOB);
	
						Map sortedCollateralLimitMap = new TreeMap(new Comparator() {
							public int compare(Object thisObj, Object thatObj) {
								ICollateral thisCol = (ICollateral) thisObj;
								ICollateral thatCol = (ICollateral) thatObj;
	
								long thisValue = thisCol.getCollateralID();
								long thatValue = thatCol.getCollateralID();
	
								return (thisValue < thatValue ? -1
										: (thisValue == thatValue ? 0 : 1));
							}
						});
						sortedCollateralLimitMap.putAll(lmtcolmap);
						OBCollateral obcol = new OBCollateral();
						String secSubType = details.getSecSubtype();
						Set set = lmtcolmap.keySet();
						ICollateral[] cols = (ICollateral[]) set.toArray(new ICollateral[0]);
						Arrays.sort(cols, new CollateralComparator());
						Iterator i = Arrays.asList(cols).iterator();
						String label;
						String value;
						while (i.hasNext()) {
							obcol = ((OBCollateral) i.next());
							if (obcol.getCollateralSubType().getSubTypeCode().equals(secSubType)) {
								label = obcol.getCollateralID() + " - " + collateralCodeMap.get(obcol.getCollateralCode());
								value = String.valueOf(obcol.getCollateralID());
								LabelValueBean lvBean = new LabelValueBean(label,value);
								securityIdList.add(lvBean);
							}
	
						}
						//check if securityID is selected then populate the documentItemList	
						if(details.getSecurityId()!=0){
							try {
								HashMap checkListMap = this.checklistProxyManager.getAllCollateralCheckListSummaryList(theOBTrxContext, limitProfileID);
								long checkListID=ICMSConstant.LONG_INVALID_VALUE;
								if (checkListMap != null) {
									CollateralCheckListSummary[] colChkList = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
									if(colChkList!=null){
									for (int n = 0; n < colChkList.length; n++) {
										CollateralCheckListSummary collateralCheckListSummary = colChkList[n];
										if(collateralCheckListSummary.getCollateralID()==details.getSecurityId()){
											checkListID = collateralCheckListSummary.getCheckListID();
											if(checkListID!=ICMSConstant.LONG_INVALID_VALUE){
												ICheckListTrxValue checkListTrxValue = this.checklistProxyManager.getCheckList(checkListID);
												ICheckList checkList = checkListTrxValue.getCheckList();
												ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
												for (int j = 0; j < checkListItemList.length; j++) {
													ICheckListItem iCheckListItem = checkListItemList[j];
													DefaultLogger.debug(this, "In Test 4. Got the item list ");
													DefaultLogger.debug(this, "=="+iCheckListItem.getItemCode());
													DefaultLogger.debug(this, "=="+iCheckListItem.getCheckListItemID());
													DefaultLogger.debug(this, "Going out of Test 4. ");
//													String label=iCheckListItem.getItemCode()+"("+iCheckListItem.getCheckListItemID()+")";
													String labelCC=iCheckListItem.getItemDesc();
													String valueCC= String.valueOf(iCheckListItem.getCheckListItemID());
													LabelValueBean lvBean = new LabelValueBean(labelCC,valueCC);
													documentItemList.add(lvBean);
												}
												
											}
											break;
										}
										
									}
									}
								}/*
								//commented will be used to show proper message
								  else{
									exceptionMap.put("docDescError", new ActionMessage("error.document.maintain.checklist","CAM"));
								}*/
	
							} catch (CheckListException e) {
								e.printStackTrace();
							}
						}
						
					}
				}
			}
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
			else if(IImageTagConstants.EXCHANGE_OF_INFO.equals(docType)){
				DefaultLogger.debug(this, "docType8:::"+docType);
				LabelValueBean lvBean = new LabelValueBean(IImageTagConstants.EXCHANGE_OF_INFO,IImageTagConstants.EXCHANGE_OF_INFO);
				documentItemList.add(lvBean);
			 }
			//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		}
		
		//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
		
		DefaultLogger.debug(this, "IF CONDITION COMPLETED...");
		
		List<String> systemBankBranchName=new ArrayList<String>();
		List<String> otherBankBranchName=new ArrayList<String>();
		List<String> allBankBranchName=new ArrayList<String>();
		List<String> ifscBankBranchName=new ArrayList<String>();
	//	ImageTagMapForm imageTagMapForm=new ImageTagMapForm();
		
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
		String systemBankId = imageTagDaoImpl.getSystemBankId(custId);
		DefaultLogger.debug(this,"systemBankId:"+systemBankId);
		String otherBankId = imageTagDaoImpl.getOtherBankId(custId);
		DefaultLogger.debug(this,"otherBankId:"+otherBankId);
		
		if(null!=systemBankId && !systemBankId.isEmpty()){
			if(systemBankId.lastIndexOf(",")!=-1){
			systemBankId=systemBankId.substring(0, systemBankId.lastIndexOf(","));
			}
			 systemBankBranchName = imageTagDaoImpl.getSystemBankBranchName(systemBankId);
		}
		if(null!=otherBankId && !otherBankId.isEmpty()){
			if(otherBankId.lastIndexOf(",")!=-1){
				otherBankId=otherBankId.substring(0, otherBankId.lastIndexOf(","));
			}
			 otherBankBranchName = imageTagDaoImpl.getOtherBankBranchName(otherBankId);
		}
		
		ifscBankBranchName = imageTagDaoImpl.getIFSCBankBranchName(custId);
			
		List bankList=new ArrayList();
		bankList.addAll(imageTagDaoImpl.populateBankList(systemBankBranchName));
		bankList.addAll(imageTagDaoImpl.populateBankList(otherBankBranchName));
		bankList.addAll(imageTagDaoImpl.populateBankList(ifscBankBranchName));
	//	imageTagMapForm.setBankList(bankList);
	//	result.put("bankList", imageTagMapForm.getBankList());
		result.put("bankList", bankList);
		//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
		
		List<String> custIdList=new ArrayList<String>();
		custIdList = imageTagDaoImpl.getFacilityNames(custId);
				
		List<String> facilityCodeList=new ArrayList<String>();
		facilityCodeList = imageTagDaoImpl.getFacilityCodes(custId);
		
		List<String> otherDocList=new ArrayList<String>();
		otherDocList = imageTagDaoImpl.getOtherDocumentList();
		
		List<String> securityNameIdList=new ArrayList<String>();
		securityNameIdList = imageTagDaoImpl.getSecurityNameIds(custId);
		
		List<String> otherSecDocList=new ArrayList<String>();
		otherSecDocList = imageTagDaoImpl.getSecurityOtherDocumentList ();
		
		List<String> facilityDocTagModuleList=new ArrayList<String>();
		facilityDocTagModuleList = imageTagDaoImpl.getFacilityDocTagModuleList(custId);
		
		List<String> statementDocList=new ArrayList<String>();
		statementDocList = imageTagDaoImpl.getStatementDocumentList ();
		
		List<String> camDocList=new ArrayList<String>();
		camDocList = imageTagDaoImpl.getCamDocumentList ();
		
		List<String> otherMasterDocList=new ArrayList<String>();
		otherMasterDocList = imageTagDaoImpl.getOtherMasterDocumentList ();
		
		List<String> securityDocTagModuleList=new ArrayList<String>();
		securityDocTagModuleList = imageTagDaoImpl.getSecurityDocTagModuleList(custId);
		
		List<String> typeOfDocList=new ArrayList<String>();
		typeOfDocList = imageTagDaoImpl.getTypeOfDocumentList ();
				
		result.put("custIdList", custIdList);
		result.put("facilityCodeList", facilityCodeList);
		result.put("otherDocList", otherDocList);
		result.put("securityNameIdList", securityNameIdList);
		result.put("otherSecDocList", otherSecDocList);
		result.put("ImageTagMapObj", details);
		result.put("facilityIdList", facilityIdList);
		result.put("secTypeList", secTypeList);
		result.put("secSubtypeList", secSubtypeList);
		result.put("securityIdList", securityIdList);
		result.put("documentItemList", documentItemList);
		result.put("securityDocTagModuleList", securityDocTagModuleList);
		result.put("facilityDocTagModuleList", facilityDocTagModuleList);
		result.put("statementDocList", statementDocList);
		result.put("camDocList", camDocList);
		result.put("otherMasterDocList", otherMasterDocList);
		result.put("typeOfDocList", typeOfDocList);
		
		result.put("subfolderNameList", new ArrayList());
		result.put("docNameList", new ArrayList());
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "getUploadImageList() Inside command----------2.2.2-------->" +DateUtil.getDate().getTime());
		return returnMap;
	}

	private List getSecurityTypeList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeProperty());
			List valList = (List) (SecurityTypeList.getInstance()
					.getSecurityTypeLabel(null));
			for (int i = 0; i < idList.size(); i++) {
				String id = idList.get(i).toString();
				String val = valList.get(i).toString();
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

	private List getSecuritySubtypeList(String secTypeValue) {
		List lbValList = new ArrayList();
		try {
			if (secTypeValue != null) {
				MISecurityUIHelper helper = new MISecurityUIHelper();
				ICollateralSubType[] subtypeLst = helper.getSBMISecProxy()
						.getCollateralSubTypesByTypeCode(secTypeValue);
				if (subtypeLst != null) {
					for (int i = 0; i < subtypeLst.length; i++) {
						ICollateralSubType nextSubtype = subtypeLst[i];
						String id = nextSubtype.getSubTypeCode();
						String value = nextSubtype.getSubTypeName();
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	public HashMap getCollateralInfo() {
		HashMap map = new HashMap();
		ICollateralNewMasterJdbc collateralNewMasterJdbc = (ICollateralNewMasterJdbc) BeanHouse.get("collateralNewMasterJdbc");
		SearchResult result = collateralNewMasterJdbc.getAllCollateralNewMaster();
		ArrayList list = (ArrayList) result.getResultList();
		for (int ab = 0; ab < list.size(); ab++) {
			ICollateralNewMaster newMaster = (ICollateralNewMaster) list.get(ab);
			map.put(newMaster.getNewCollateralCode(), newMaster.getNewCollateralDescription());

		}
		return map;
	}

}
