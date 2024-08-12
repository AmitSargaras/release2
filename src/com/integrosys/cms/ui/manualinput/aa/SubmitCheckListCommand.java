/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.time.DateUtils;

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
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateDAOFactory;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentDAO;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICriInfo;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.document.bus.DocumentDaoJdbcImpl;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/10/09 05:39:20 $ Tag: $Name: $
 */
public class SubmitCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	/*
	 * This command will generate new document list with pending state
	 * when new CAM is created.
	 * 
	 * */

	
	/**
	 * Default Constructor
	 */
	public SubmitCheckListCommand() {
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
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },{ "mandatoryDisplayRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
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
		return (new String[][] { { "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult", REQUEST_SCOPE },
		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	//throws CommandProcessingException, CommandValidationException
	public HashMap doExecute(HashMap map)  {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		System.out.println("Inside SubmitCheckListCommand.java");
		ICMSTrxResult res = (ICMSTrxResult) map.get("request.ITrxResult");
		DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<<doExecute>>>>>>>>>>>>>>>>>>>>>>>>>");
		if(res!=null) {
		DefaultLogger.debug(this, "Retrieve  CMSTrxResult");
		ILimitProfileTrxValue limitProfileTrxValOld=(ILimitProfileTrxValue )map.get("limitProfileTrxVal");
		ILimitProfile limitProfileOld= limitProfileTrxValOld.getLimitProfile();
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Complete CMSTrxResult >> "+res);
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction History >> "+res.getTransactionHistoryID());
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> Transaction Object >> "+res.getTrxValue());
		System.out.println(">>>>>>>>>>>>>>>>> Complete CMSTrxResult >> "+res+" ** >>>>>>>>>>>>>>>>> Transaction History >> "+res.getTransactionHistoryID()+"  ** >>>>>>>>>>>>>>>>> Transaction Object >> "+res.getTrxValue());
		ILimitProfileTrxValue limitProfileTrxVal =(ILimitProfileTrxValue) res.getTrxValue();
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> After Transaction Value >> ");
		ILimitProfile limitProfile= limitProfileTrxVal.getLimitProfile();
		ILimitProfile limitProfileStage= limitProfileTrxVal.getStagingLimitProfile();
		DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		try{
		ICheckListTrxValue checkListTrxVal = null;
		 ICheckListProxyManager checklistProxyManager =  (ICheckListProxyManager)BeanHouse.get("checklistProxy"); 
		ICheckList checkList = null;
		ICheckList newCheckList = null;
		CheckListSearchResult camCheckList= checklistProxyManager.getCAMCheckListByCategoryAndProfileID("CAM",limitProfile.getLimitProfileID());
		DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1 camCheckList@@@@@@@@@@@@@@@@@@@@@@@@@@@"+camCheckList);
		
		if(limitProfile!=null){
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			if(limitProfileOld!=null){
				DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				System.out.println("Inside limitProfileOld!=null if condition. ");
				
		if(!limitProfileOld.getBCAReference().equals(limitProfileStage.getBCAReference())				
				&& limitProfileOld.getApprovalDate().compareTo(limitProfileStage.getApprovalDate())!=0){
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@4@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("camCheckList is null?? =>"+camCheckList);
			if(camCheckList==null){
				ICheckList checkListNext = null;
				DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@7@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				long limitProfileID = limitProfile.getLimitProfileID();
				String custCategory = "MAIN_BORROWER";
				String applicationType = "COM";
//				String tCollateralID = "200701010000130";
				long collateralID = 0L;
				ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
						applicationType);
				checkListNext = checklistProxyManager.getDefaultCAMCheckList(owner,"IN", "CAM", "CAM", "", "", "");
				checkListNext = linkInsuranceReceipt(checkListNext, checklistProxyManager);
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				checkListNext= setNewCAMValues(checkListNext,limitProfileStage);
				checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkListNext);
				DefaultLogger.debug(this, "Create New Checklist and item list if No checklist 1 ");
				System.out.println("Create New Checklist and item list if No checklist 1 ");
			}else{
		checkListTrxVal = checklistProxyManager.getCheckList(camCheckList.getCheckListID());
		checkList = checkListTrxVal.getCheckList();
		newCheckList =getNewChecklistAtApproval(checkList);
		newCheckList = linkInsuranceReceipt(newCheckList, checklistProxyManager);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		newCheckList= setNewCAMValues(newCheckList,limitProfileStage);
		updateOldCheckLists(limitProfile.getLimitProfileID());
		checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, newCheckList);
		DefaultLogger.debug(this, "Create New Checklist  and item list if checklist is present 2 ");
		System.out.println("Create New Checklist  and item list if checklist is present 2 ");
			}
		}
		}else{
			DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@6@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			System.out.println("Inside else condition. ");
			if(camCheckList==null){
				ICheckList checkListNext2 = null;
				ICheckList checkListNext3 = null;
				DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@7@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				long limitProfileID = limitProfile.getLimitProfileID();
				String custCategory = "MAIN_BORROWER";
				String applicationType = "COM";
//				String tCollateralID = "200701010000130";
				long collateralID = 0L;
				ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,applicationType);
				checkListNext2 = checklistProxyManager.getDefaultCAMCheckList(owner,"IN", "CAM", "CAM", "", "", "");
				checkListNext2 = linkInsuranceReceipt(checkListNext2, checklistProxyManager);
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				checkListNext2= setNewCAMValues(checkListNext2,limitProfileStage);
				
				ICheckListDAO checkListDAO =CheckListDAOFactory.getCheckListDAO();
				DocumentDaoJdbcImpl docImpl=new DocumentDaoJdbcImpl();
				
				//Start Ram rating CR Started to insert new document on selection of customer Fyclouser in CRI details
							checkListNext3 = checklistProxyManager.getDefaultCAMCheckList(owner, "IN", "REC", "REC", "", "", "");
							checkListNext3 = linkInsuranceReceipt(checkListNext3, checklistProxyManager);
							//checkListNext3 = setNewCAMValues(checkListNext3, limitProfileStage);

							ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
							ILimitProfile limit = limitProfileTrxVal.getStagingLimitProfile();

							String custClassification = limit.getAssetClassification();
							String custRating = limit.getRamRating();
							double custTotalSancAmt = limit.getTotalSactionedAmount();
							String custSegment = customer.getCustomerSegment();
							String custGuarantor = customer.getCMSLegalEntity().getSubLine();
							boolean isStockReceivable = false;
							
							if(limit.getLimits()!=null){
								for (int q = 0; q < limit.getLimits().length; q++) {
									ILimit iLimit = limit.getLimits()[q];
									if (iLimit != null) {
										if (iLimit.getCollateralAllocations() != null) {
											for (int w = 0; w < iLimit.getCollateralAllocations().length; w++) {
												ICollateralAllocation allocation = iLimit.getCollateralAllocations()[w];
												if ("AB100".equals(allocation.getCollateral().getCollateralSubType().getSubTypeCode())) {
													isStockReceivable = true;
												}
											}
										}
									}
								}
							}

							ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
									.getCheckListTemplateProxyManager();
							ArrayList globalDocChkList = new ArrayList();
							DocumentSearchCriteria criteria = new DocumentSearchCriteria();
							criteria.setDocumentType("REC");
							// String[] splitList = chkTemplateType.split("-");
							SearchResult sr = null;
							try {
								sr = proxy.getDocumentItemList(criteria);
							} catch (CheckListTemplateException ex) {
								throw new CommandProcessingException(
										"failed to retrieve document item list using criteria [" + criteria + "]", ex);
							}
							try {
								System.out.println("Inside try block.");
								if (sr != null && sr.getResultList() != null) {
									java.util.Vector vector = (java.util.Vector) sr.getResultList();
									// System.out.println(":::::vector.size:::::::"+vector.size());
									System.out.println("Inside vector.size()=>."+vector.size());
									for (int i = 0; i < vector.size(); i++) {
										// boolean isRecurrent=false;
										boolean isRating = true;
										boolean isSegment = true;
										boolean isTotalSancAmt = true;
										boolean isClassification = true;
										boolean isGuarantor = true;
										boolean showAll = false;
										// System.out.println(":::::0000000000000000:::::::"+vector.get(i));
										DocumentSearchResultItem resultItem = (DocumentSearchResultItem) vector.get(i);

										String rating = resultItem.getRating();
										String[] ratingArray = rating.split("-");
										String segment = resultItem.getSegment();
										String[] segmentArray = segment.split("-");
										String totalSancAmt = resultItem.getTotalSancAmt();
										String classification = resultItem.getClassification();
										String[] classificationArray = classification.split("-");
										String guarantor = resultItem.getGuarantor();

										if (rating.equals("") && segment.equals("") && totalSancAmt.equals("")
												&& classification.equals("") && guarantor.equals("")) {
											showAll = true;
											// continue;
										}

										if (totalSancAmt != null && !totalSancAmt.equals("")) {
											double dtotalSancAmt = Double.parseDouble(totalSancAmt);
											if (!(dtotalSancAmt <= custTotalSancAmt))
												isTotalSancAmt = false;
										}

										if (custClassification != null) {
											if (classificationArray != null && !classification.equals("")) {
												boolean value = false;
												for (int c = 0; c < classificationArray.length; c++) {

													if (custClassification.equalsIgnoreCase(classificationArray[c])) {
														value = true;
													}

												}
												if (!value) {
													isClassification = false;
												}
											}
										}

										if (custSegment != null) {
											if (segmentArray != null && !segment.equals("")) {
												boolean value = false;
												for (int b = 0; b < segmentArray.length; b++) {

													if (custSegment.equalsIgnoreCase(segmentArray[b])) {
														value = true;
													}

												}
												if (!value) {
													isSegment = false;
												}
											}
										}
										if (custRating != null) {
											if (ratingArray != null && !rating.equals("")) {
												boolean value = false;
												for (int a = 0; a < ratingArray.length; a++) {

													if (custRating.equalsIgnoreCase(ratingArray[a])) {
														value = true;
													}

												}
												if (!value) {
													isRating = false;
												}
											}
										}

										if (custGuarantor != null) {
											if (guarantor != null && !guarantor.equals("")) {
												if (!custGuarantor.equalsIgnoreCase(guarantor))
													isGuarantor = false;
											}
										}

										if (showAll) {
											// globalDocChkList.add(resultItem);
										} else {
											if (isClassification && isGuarantor && isRating && isSegment
													&& isTotalSancAmt) {
												globalDocChkList.add(resultItem);
											}
										}

									}

									Collections.sort(globalDocChkList);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							ArrayList checkListMap = new ArrayList();
							if (checkListNext3.getCheckListItemList() != null) {
								Arrays.sort(checkListNext3.getCheckListItemList());
								System.out.println("checkListNext3.getCheckListItemList().length=>."+checkListNext3.getCheckListItemList().length);
								for (int i = 0; i < checkListNext3.getCheckListItemList().length; i++) {
									ICheckListItem resultItem = (ICheckListItem) checkListNext3.getCheckListItemList()[i];

									System.out.println("inside first for loop =>checkListNext3.getCheckListItemList()=> i=>"+i+"   ** globalDocChkList.size() =>"+globalDocChkList.size());
									
									for (int ab = 0; ab < globalDocChkList.size(); ab++) {
										DocumentSearchResultItem resultDocItem = (DocumentSearchResultItem) globalDocChkList
												.get(ab);
										
										if (resultDocItem.getItemCode().equals(resultItem.getItemCode())) {  
											if (resultDocItem.getStatementType().equals("STOCK_STATEMENT")) {
												if (isStockReceivable) {
													System.out.println("Inside if resultDocItem.getStatementType().equals(-STOCK_STATEMENT-)  resultItem.getItemCode()=>"+resultItem.getItemCode());
													resultItem.setExpiryDate(resultDocItem.getExpiryDate());
													checkListMap.add(resultItem);
												}

											} else {
												System.out.println("Inside else resultDocItem.getStatementType().equals(-STOCK_STATEMENT-)  ");
												resultItem.setExpiryDate(resultDocItem.getExpiryDate());
												checkListMap.add(resultItem);
											}
										}
									}

								}
							}

							// by abhijit R iterate the loop for checklistMap and set proper checklist item
							// according to criteria.
							ICheckListItem[] checkListItems = new ICheckListItem[checkListMap.size()];
							for (int abc = 0; abc < checkListMap.size(); abc++) {
								checkListItems[abc] = (ICheckListItem) checkListMap.get(abc);
							}
							//checkListNext3.setCheckListItemList(checkListItems);
							int i = 0;
							OBDocumentItem ramRatingChecklist = new OBDocumentItem();
							ICriInfo[] criList = ctx.getCustomer().getCMSLegalEntity().getCriList();
							if (("December Ending").equals(criList[i].getCustomerFyClouser())) {
								DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<<December Ending>>>>>>>>>>>>>>>>>>>>>>>>>");
								
								ramRatingChecklist = docImpl.getAllRamratingDocumentlist(criList);
								try{
								checkListNext3 = setNewDocList(checkListNext3, ramRatingChecklist, limitProfileStage,
										criList, checklistProxyManager);
								System.out.println("<<December Ending>> inside if setNewDocList() method called and going for checklistProxyManager.makerCreateCheckListWithoutApproval() method   =>checkListNext3=>"+checkListNext3);
								checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkListNext3);
								}catch(Exception ex){
									ex.printStackTrace();
								}

							}

							else if (("March Ending").equals(criList[i].getCustomerFyClouser())) {
								DefaultLogger.debug(this, "<<<<<<<<<<<<<<<<<<<<<<<March Ending>>>>>>>>>>>>>>>>>>>>>>>>>");
								ramRatingChecklist = docImpl.getAllRamratingDocumentlist(criList);
								try{
								checkListNext3 = setNewDocList(checkListNext3, ramRatingChecklist, limitProfileStage,
										criList, checklistProxyManager);
								System.out.println("<<March Ending>> inside else setNewDocList() method called and going for checklistProxyManager.makerCreateCheckListWithoutApproval() method   =>checkListNext3=>"+checkListNext3);
								checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkListNext3);
								}catch(Exception ex){
									ex.printStackTrace();
								}

							}
				//End Ram rating CR Started to insert new document on selection of customer Fyclouser in CRI details
							System.out.println("outside if else=> going for checklistProxyManager.makerCreateCheckListWithoutApproval() method .");
				checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkListNext2);
				DefaultLogger.debug(this, "Create New Checklist  and item list if checklist is present 3");
				System.out.println("Create New Checklist  and item list if checklist is present 3");
				}
			}
		}
		
		DefaultLogger.debug(this, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@9@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@9@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		}catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(e.getMessage());
			cpe.initCause(e);
			throw cpe;
		}
		}//if end
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		System.out.println("Going out of doExecute() => Done.");
		resultMap.put("request.ITrxValue", res);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	

	private void updateOldCheckLists(long limitProfileID) {

		ICheckListDAO checkListDAO =CheckListDAOFactory.getCheckListDAO();
		checkListDAO.updateOldCheckLists(limitProfileID);
		
	}

	private ICheckList setNewCAMValues(ICheckList newCheckList, ILimitProfile limitProfile) {
		
		newCheckList.setCamDate(limitProfile.getApprovalDate());
		newCheckList.setCamNumber(limitProfile.getBCAReference());
		newCheckList.setCamType(limitProfile.getCamType());
		newCheckList.setIsLatest("Y");
		
		return newCheckList;
	}

	private HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		return hm;
	}

	private boolean isItemDeleted(ICheckList checkList, int i) {
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}
	
	
	private ICheckList getNewChecklistAtApproval(ICheckList oldcheckList) {
		
		ICheckList checkList = new OBCheckList(oldcheckList.getCheckListOwner());
		checkList.setCheckListType(ICMSConstant.DOC_TYPE_CAM);
		checkList.setCheckListLocation(new OBBookingLocation(oldcheckList.getCheckListLocation().getCountryCode(), null));
		checkList.setTemplateID(oldcheckList.getTemplateID());
		ICheckListItem[] checkListItems=getNewCheckListItemList(oldcheckList.getCheckListItemList());
		checkList.setCheckListItemList(checkListItems);
		
		
		return checkList;
		
	}

	private ICheckListItem[] getNewCheckListItemList(ICheckListItem[]  checkListItems) {
		ArrayList itemList = new ArrayList();
		for(int i=0;i<checkListItems.length;i++){
			OBCheckListItem oldcheckListItem=(OBCheckListItem) checkListItems[i];
			OBCheckListItem checkListItem= new OBCheckListItem();
			checkListItem.setIsMandatoryInd(oldcheckListItem.getIsMandatoryInd());
			checkListItem.setIsMandatoryDisplayInd(oldcheckListItem.getIsMandatoryDisplayInd());
			checkListItem.setTenureCount(oldcheckListItem.getTenureCount());
			checkListItem.setTenureType(oldcheckListItem.getTenureType());
			//setDeferCount(oldcheckListItem.getDeferCount());
			checkListItem.setIsInVaultInd(oldcheckListItem.getIsInVaultInd());
			checkListItem.setIsExtCustInd(oldcheckListItem.getIsExtCustInd());
			checkListItem.setIsAuditInd(oldcheckListItem.getIsAuditInd());
			checkListItem.setItem(oldcheckListItem.getItem());
			checkListItem.setParentItemID(oldcheckListItem.getParentItemID());
			itemList.add(checkListItem);
		}
		return (ICheckListItem[]) itemList.toArray(new ICheckListItem[0]);
		
	}
	private ICheckList linkInsuranceReceipt(ICheckList checkList,ICheckListProxyManager checklistProxyManager) throws CheckListException {
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
						long ref = checklistProxyManager.generateCheckListItemSeqNo();
						parentItem.setCheckListItemRef(ref);
						childItem.setParentCheckListItemRef(ref);
					}
				}
			}
		}
		return checkList;
	}
	
 
  private ICheckList setNewDocList(ICheckList newCheckList, OBDocumentItem ramRatingChecklist,ILimitProfile limitProfileStage,ICriInfo[] criList,ICheckListProxyManager checklistProxyManager ) throws CheckListException, ParseException {
	  	DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  	System.out.println("Inside >>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  	IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		
		Date dateApplication=new Date();
		int k=0;
		for(int j=0;j<generalParamEntries.length;j++){
		if(generalParamEntries[j].getParamCode().equals("APPLICATION_DATE")){
		dateApplication=new Date(generalParamEntries[j].getParamValue());
		}
		}
		//DateFormat formatter = new SimpleDateFormat("yyyy");
		//String todayYear = formatter.format(dateApplication);
		String ramRatingYear=limitProfileStage.getRamRatingYear();
		
		//Calendar cal = Calendar.getInstance();
		//cal.setTime(dateApplication);
		//cal.add(Calendar.MONTH, 8);
		//Date newDate=cal.getTime();
        //String year= formatter.format(newDate);
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String time="12:00 AM";
      
	   /*try{*/
		   System.out.println("nside try block setNewDocList()");
		   System.out.println("nside try block setNewDocList()");
		   OBCheckListItem checkListItem= new OBCheckListItem();
			checkListItem.setCheckListItemDesc(ramRatingChecklist.getItemDesc());
			checkListItem.setCheckListItemID(ramRatingChecklist.getItemID());
			checkListItem.setItemCode(ramRatingChecklist.getItemCode());
			
			int newRAMYear=Integer.valueOf(ramRatingYear)+1;
			Calendar cal = Calendar.getInstance();
			//Date dateApplication=new Date();
			String date="";
			String newExpDate="";
			
			if(criList[k].getCustomerFyClouser().equals("December Ending")){
				 //String decDate="01/08/";
				date="01-Dec-"+newRAMYear;
				dateApplication=new Date(date);
				cal.setTime(dateApplication);
				cal.add(Calendar.MONTH, 8);
				Date newDate=cal.getTime();
				newExpDate=new SimpleDateFormat("dd/MM/yyyy").format(newDate);
				Date newDecDate=df.parse(newExpDate+ " " + time);
				
				checkListItem.setExpiryDate(newDecDate); 
			}
		   if(criList[k].getCustomerFyClouser().equals("March Ending")){
			   //String marchDate="01/11/";
			   date="01-Mar-"+newRAMYear;
				dateApplication=new Date(date);
				cal.setTime(dateApplication);
				cal.add(Calendar.MONTH, 8);
				Date newDate=cal.getTime();
				newExpDate=new SimpleDateFormat("dd/MM/yyyy").format(newDate);
			    Date newMarchDate=df.parse(newExpDate+ " " + time);
			   
			    checkListItem.setExpiryDate(newMarchDate);
		    }
			//if(todayYear.equals(ramRatingYear)){
			checkListItem.setItemStatus("AWAITING");
			/*}else{
				checkListItem.setItemStatus("AWAITING");
			}*/
			checkListItem.setStatementType(ramRatingChecklist.getStatementType());
			System.out.println("ramRatingChecklist.getStatementType()=>"+ramRatingChecklist.getStatementType());
			long ref = checklistProxyManager.generateCheckListItemSeqNo();
			checkListItem.setCheckListItemRef(ref);
//			checkListItem.setParentItemID(ramRatingChecklist.getItemID());
	      
	   /*ICheckListItem[] checklistItem = new ICheckListItem [newCheckList.getCheckListItemList().length+1];
	   for(int i = 0; i < newCheckList.getCheckListItemList().length; i++)
       {
		   checklistItem[i] = newCheckList.getCheckListItemList()[i];
       }
	   checklistItem[newCheckList.getCheckListItemList().length] = checkListItem;*/
			
		ICheckListItem[] checklistItem = new ICheckListItem [1];
		checklistItem[0] = checkListItem;
			   
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>>>>>>>>>>>>>>>>>>>"+checkListItem);
		System.out.println(">>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>>>>>>>>>>>>>>>>>>>"+checkListItem);
		newCheckList.setCheckListItemList(checklistItem);
	     
	   /*}catch(Exception ex){
		   ex.printStackTrace();
	   }*/
		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>newCheckList.getCheckListItemList().length = "+newCheckList.getCheckListItemList().length);
		System.out.println(">>>>>>>>>>>>>>>>>>setNewDocList()>>>>>>>>newCheckList.getCheckListItemList().length = "+newCheckList.getCheckListItemList().length);
		return  newCheckList;
	}
  
      public static String toddMMyy(Date day){ 
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
		String date = formatter.format(day); 
		return date; 
		}
	
}
