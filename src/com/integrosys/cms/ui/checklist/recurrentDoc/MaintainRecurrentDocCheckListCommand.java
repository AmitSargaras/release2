/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.host.eai.document.bus.DocumentDaoJdbcImpl;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/11/06 06:47:35 $ Tag: $Name: $
 */
public class MaintainRecurrentDocCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	private ICheckListTemplateProxyManager checklistTemplateProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public void setCheckListTemplateProxyManager(ICheckListTemplateProxyManager checklistTemplateProxyManager) {
		this.checklistTemplateProxyManager = checklistTemplateProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public MaintainRecurrentDocCheckListCommand() {
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
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE }, { "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "dispatchToMaintain", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
					{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "no_template", "java.lang.String", REQUEST_SCOPE },
				 { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE }, { "docNos", "java.util.ArrayList", SERVICE_SCOPE },
//                { "colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", REQUEST_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE },
                { "stockDocChkList", "java.util.HashMap", SERVICE_SCOPE },
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
        boolean isViewFlag = false;
        boolean isStockReceivable = false;
        HashMap stockDocChkList = new HashMap();
        boolean isRamDocumentAvailable=false;
        boolean isRamDocForChecklistIdAlreadyExist=false;
        DocumentDaoJdbcImpl docImpl=new DocumentDaoJdbcImpl();
     
		String tCheckListID = (String) map.get("checkListID");
		long checkListID = Long.parseLong(tCheckListID);
		String secType = (String) map.get("secType");
		String secSubType = (String) map.get("secSubType");
		String limitBkgLoc = (String) map.get("limitBkgLoc");
		String orgCode = (String) map.get("orgCode");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		for(int q=0;q<limit.getLimits().length;q++){
			ILimit iLimit=limit.getLimits()[q];
			if(iLimit!=null){
				if(iLimit.getCollateralAllocations()!=null){
					for(int w=0;w<iLimit.getCollateralAllocations().length;w++){
						ICollateralAllocation allocation=iLimit.getCollateralAllocations()[w];
						if("AB100".equals(allocation.getCollateral().getCollateralSubType().getSubTypeCode())){
							isStockReceivable=true;
						}
						
					}
				}
			}
			
		}
		String custClassification=limit.getAssetClassification();
		String custRating=limit.getRamRating();
		//double custTotalSancAmt=limit.getRequiredSecurityCoverage();
		double custTotalSancAmt=limit.getTotalSactionedAmount();
		String custSegment=customer.getCustomerSegment();
		String custGuarantor =customer.getCMSLegalEntity().getSubLine();
		long limitProfileID = limit.getLimitProfileID();
		String custCategory = "MAIN_BORROWER";
		String applicationType = "COM";
//		String tCollateralID = "200701010000130";
		long collateralID = 0L;
		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
				applicationType);
	//	ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		ICheckList checkList = null;
		try {
	//		int wip = this.checklistProxyManager.allowCheckListTrx(owner);
			
//			int wip =0;
//			if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
//				resultMap.put("wip", "wip");
//			}
//			else {
			
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			if(checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE){
				
				int count=dao.getPendingStageChecklistCount("REC",limitProfileID);
				if(count>0){
				resultMap.put("wip", "wip");
				}
				}else{
				//	ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
					int count=dao.getPendingChecklistCount(checkListID);
					if(count>0){
					resultMap.put("wip", "wip");
					}
				}
			if(null==resultMap.get("wip")){
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

					if ((orgCode != null) && orgCode.equals("null")) {
						orgCode = null;
					}
				//	secType="CAM";
				//	secSubType="CAM";
					try{
					checkList = this.checklistProxyManager.getDefaultCAMCheckList(owner,"IN", secType, secSubType, "", "", "");
					}catch (TemplateNotSetupException e) {
						resultMap.put("no_template", "true");
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						return returnMap;
					}
					checkList = linkInsuranceReceipt(checkList);
					resultMap.put("checkListTrxVal", null);
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					
					ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
					ArrayList globalDocChkList = new ArrayList();
					DocumentSearchCriteria criteria = new DocumentSearchCriteria();
					criteria.setDocumentType("REC");
					//String[] splitList = chkTemplateType.split("-");
					SearchResult sr = null;
					try {
						sr = proxy.getDocumentItemList(criteria);
					}
					catch (CheckListTemplateException ex) {
						throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
								+ "]", ex);
					}
					try{
						
						if (sr != null && sr.getResultList() != null) {
							java.util.Vector vector= (java.util.Vector) sr.getResultList();
							//System.out.println(":::::vector.size:::::::"+vector.size());
							for(int i=0;i<vector.size();i++){
								//boolean isRecurrent=false;
								boolean isRating=true;
								boolean isSegment=true;
								boolean isTotalSancAmt=true;
								boolean isClassification=true;
								boolean isGuarantor=true;
								boolean showAll=false;
								//System.out.println(":::::0000000000000000:::::::"+vector.get(i));
								DocumentSearchResultItem resultItem=(DocumentSearchResultItem)vector.get(i);
									
								
										String rating= resultItem.getRating();
				                        String[] ratingArray=rating.split("-");
				                        String segment= resultItem.getSegment();
				                        String[] segmentArray=segment.split("-");
				                        String totalSancAmt=resultItem.getTotalSancAmt();
				                        String classification= resultItem.getClassification();
				                        String[] classificationArray=classification.split("-");
				                        String guarantor=resultItem.getGuarantor();
				                     
				                        if(rating.equals("")&&segment.equals("")&&totalSancAmt.equals("")&&classification.equals("")&&guarantor.equals("")){
				                        	showAll=true;
				                        	//continue;
				                        }
				                        
				                        if(totalSancAmt!=null&&!totalSancAmt.equals("")){
				                        double dtotalSancAmt=Double.parseDouble(totalSancAmt);
				                        if(!(dtotalSancAmt<=custTotalSancAmt))
				                            isTotalSancAmt=false;
				                        }
				                      
				                        if(custClassification!=null){
										  if(classificationArray!=null&& !classification.equals("")){
											  boolean value=false;
										  for(int c =0; c< classificationArray.length ;c++){
											 
											  if(custClassification.equalsIgnoreCase(classificationArray[c])){
												  value=true;
											  }
											  
											 
												  
										  }
										  if(!value){
											  isClassification=false;
										  }
										  }
				                        }
				                        
				                        if(custSegment!=null){
										  if(segmentArray!=null && ! segment.equals("")){
											  boolean value=false;
										  for(int b =0; b< segmentArray.length ;b++){
											
											  if(custSegment.equalsIgnoreCase(segmentArray[b]))
											  {
												  value=true;
											  }
											 
										  }
										  if(!value){
											  isSegment=false;
										  }
										  }
				                        }
				                        if(custRating!=null){ 
				                        if(ratingArray!=null&& ! rating.equals("")){
				                        	boolean value=false;
					                        	for(int a =0; a< ratingArray.length ;a++){
					                        		
					                        		if(custRating.equalsIgnoreCase(ratingArray[a]))
					                        		{
					                        			value=true;
													  }
					                        		
					                        	}
					                        	if(!value){
													  isRating=false;
												  }
					                        }
				                        }
				                        
				                       
				                        
				                        if(custGuarantor!=null){
				                        	 if(guarantor!=null&&!guarantor.equals("")){	
				                        if(!custGuarantor.equalsIgnoreCase(guarantor))
				                        	isGuarantor=false;
				                        }
				                        }
				                        
				                        if(showAll){
				                        	//globalDocChkList.add(resultItem);
				                        }else{
				                        if(isClassification&&isGuarantor&&isRating&&isSegment&&isTotalSancAmt){
				                        	globalDocChkList.add(resultItem);    	
				                        }
				                        }	
				                        
				                     
								
										
									
								
							}
							
							Collections.sort(globalDocChkList);
							
						}
						}catch (Exception e) {
							e.printStackTrace();
						}
					
					
					ArrayList checkListMap=new ArrayList();
					if (checkList.getCheckListItemList() != null) {
						Arrays.sort(checkList.getCheckListItemList());
					
							for(int i=0;i<checkList.getCheckListItemList().length;i++){
								ICheckListItem resultItem=(ICheckListItem)checkList.getCheckListItemList()[i];
								
								for(int ab=0;ab<globalDocChkList.size();ab++){
									DocumentSearchResultItem resultDocItem=(DocumentSearchResultItem)globalDocChkList.get(ab);
									if(resultDocItem.getItemCode().equals(resultItem.getItemCode())){
										if(resultDocItem.getStatementType().equals("STOCK_STATEMENT")){
											if(isStockReceivable){
												resultItem.setExpiryDate(resultDocItem.getExpiryDate());
												checkListMap.add(resultItem);
											}
											
										}else{
										resultItem.setExpiryDate(resultDocItem.getExpiryDate());
										checkListMap.add(resultItem);
										}
									}
								}
								
							
							}
					}
					
					
				// by abhijit R iterate the loop for checklistMap and set proper checklist item according to criteria.
					ICheckListItem[] checkListItems= new ICheckListItem[checkListMap.size()];
					for(int abc=0;abc<checkListMap.size();abc++){
						checkListItems[abc]=(ICheckListItem)checkListMap.get(abc);
					}
					checkList.setCheckListItemList(checkListItems);
					
					for(int i=0;i<checkList.getCheckListItemList().length;i++){
						ICheckListItem resultItem=(ICheckListItem)checkList.getCheckListItemList()[i];
					
							if("STOCK_STATEMENT".equals(resultItem.getStatementType())){
						stockDocChkList.put(resultItem.getItemCode(), resultItem.getItemCode());		
					}
					}
				}
				else {
					ICheckListTrxValue checkListTrxVal = this.checklistProxyManager.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();

//                    //[Start] Customize for alliance
//                    ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
//                    CustodianSearchCriteria searchCriteria = new CustodianSearchCriteria();
//
//                    searchCriteria.setCheckListID(checkList.getCheckListID());
//                    searchCriteria.setCollateralID(owner.getCollateralID());
//                    searchCriteria.setDocType(ICMSConstant.DOC_TYPE_CAM);
//
//                    HashMap srmap =  proxy.getDocWithOwnerInfo(searchCriteria);
//                    resultMap.put("colowner", srmap.get(ICMSConstant.SEC_OWNER));
//                    //[End]
					for(int i=0;i<checkList.getCheckListItemList().length;i++){
						ICheckListItem resultItem=(ICheckListItem)checkList.getCheckListItemList()[i];
					
							if("STOCK_STATEMENT".equals(resultItem.getStatementType())){
						stockDocChkList.put(resultItem.getItemCode(), resultItem.getItemCode());		
					}
					}
					if (checkList.getTemplateID() <= 0) {
						DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
								+ "], retrieving template id");

						ITemplate template = this.checklistTemplateProxyManager.getCollateralTemplate(secType,
								secSubType, limitBkgLoc);
						if (template != null) {
							checkList.setTemplateID(template.getTemplateID());
						}
					}

					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
				}
				
				
				
				
				resultMap.put("stockDocChkList", stockDocChkList);
				String checkListStatus = checkList.getCheckListStatus();
				// perform sorting only if checklist status is not NEW
				if ((checkListStatus == null)
						|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
					ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					checkList.setCheckListItemList(sortedItems);
				}

				String dispatchToMaintain = ("Y".equals(map.get("dispatchToMaintain")) || "Y".equals(checkList
						.getDisableCollaborationInd())) ? "Y" : "N";
				checkList.setDisableCollaborationInd(dispatchToMaintain);

				// CR-236
				String event = (String) map.get("event");
				if ("delete".equals(event)) {
					((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
				}else if("view".equals(event)){
                    isViewFlag = true;
                }
            
				/*//Ram rating CR(Check ram rating doc already exist then set existing record for ram statement.)
				isRamDocForChecklistIdAlreadyExist=dao.isRamDocAlreadyExist(limitProfileID);
				if(isRamDocForChecklistIdAlreadyExist){
					resultMap.put("checkList", checkList);
				}
				else{
				//Ram rating CR(Check ram rating statement is  not available then set new record for ram statement.)
				isRamDocumentAvailable=dao.getRamRatingDocument(limitProfileID);
				if(isRamDocumentAvailable){
					checkList=docImpl.getRamRatingDocList(checkList,limitProfileID);
					resultMap.put("checkList", checkList);
				}
				}*/
				resultMap.put("checkList", checkList);
				
                resultMap.put("isViewFlag",new Boolean(isViewFlag));
			}
			resultMap.put("frame", "true");// used to hide frames when user
			// comes from to do list

			ArrayList outputDocIds = null;
			if (checkList != null) {
				ArrayList docNos = new ArrayList();
				ICheckListItem[] itemList = checkList.getCheckListItemList();
				for (int count = 0; count < itemList.length; count++) {
					ICheckListItem item = itemList[count];
					long docNoLong = item.getCheckListItemRef();
					String docNo = String.valueOf(docNoLong);
					docNos.add(docNo);
				}
				outputDocIds = this.checklistProxyManager.getDocumentIdsForCheckList(docNos);
			}
			resultMap.put("docNos", outputDocIds);
			
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_CHECKLIST",checkListID,"CHECKLIST_ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch (TemplateNotSetupException e) {
			resultMap.put("no_template", "true");
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("fail to retrieve checklist template of security, type [" + secType
					+ "], subtype [" + secSubType + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("fail to maintain security checklist", ex);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Add newItems to the checklist. This will also spawn a specific insurance
	 * policy a predefined premium receipt. As for now, the matrix is stored in
	 * common code catetory entry.
	 * 
	 * @param checkList of type ICheckList
	 * @return ICheckList checklist updated with new checklist items
	 * @throws com.integrosys.cms.app.checklist.bus.CheckListException on any
	 *         errors encountered
	 */
	private ICheckList linkInsuranceReceipt(ICheckList checkList) throws CheckListException {
		ICheckListItem[] existingItems = checkList.getCheckListItemList();

		HashMap receiptMap = CheckListHelper.getPremiumReceiptMap();

		for (int i = existingItems.length - 1; i >= 0; i--) {
			ICheckListItem parentItem = existingItems[i];
			if (parentItem.getItem().getMonitorType() != null) {
				if (parentItem.getItem().getMonitorType().equals(ICMSConstant.INSURANCE_POLICY)
						&& !CheckListHelper.isExpired(parentItem.getItem())) {
					String childCode = (String) receiptMap.get(parentItem.getItem().getItemCode());
					if (childCode == null) {
						continue; // no receipt tied to the policy, so no need
						// to spawn.
					}
					ICheckListItem childItem = CheckListHelper.getPremiumReceiptItem(childCode, existingItems);
					if (childItem != null) {
						long ref = this.checklistProxyManager.generateCheckListItemSeqNo();
						parentItem.setCheckListItemRef(ref);
						childItem.setParentCheckListItemRef(ref);
					}
				}
			}
		}
		return checkList;
	}
}
