package com.integrosys.cms.ui.manualinput.limit;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBDocumentItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class GenerateDeferralsForSCOD {
	
	public static void generateDeferralsForSCOD(OBTrxContext ctx, ILimitTrxValue lmtTrxObj, ILimitProfile limit, Date applicationDate, ICMSTrxResult res) {
		
		ICheckListProxyManager checklistProxyManager = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICheckListTemplateProxyManager checklistTemplateProxyManager = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		ILimit lmt=lmtTrxObj.getStagingLimit();
		long limitID = getReferenceId(res.getTrxValue().getTransactionID());   //Facility ID
		String secType = lmt.getFacilityCode();  //FAC0000682 for facility id=20200311000004470 (i.e. facility code in facility master) 
		String secSubType = lmt.getFacilityCode(); //FAC0000682 for facility id=20200311000004470 (i.e. facility code in facility master)
		String limitBkgLoc = "IN";
		String orgCode = "000";
		long checkListID =-999999999;
		System.out.println("In generateDeferralsForSCOD()......");
        try {
      	  CheckListSearchResult checkList=checklistProxyManager.getCheckListByCollateralID(limitID);
      	  if(checkList!=null)
      		checkListID=checkList.getCheckListID();
			
		} catch (CheckListException e) {
			e.printStackTrace();
			throw new CommandProcessingException("failed to retrieve  checklist ", e);
		}
        
		long limitProfileID = limit.getLimitProfileID();
		System.out.println("In generateDeferralsForSCOD()...limitProfileID="+limitProfileID);
		String mandatoryDisplayRows = "";
		HashMap hmMandatoryDisplayRows = getMapFromString(mandatoryDisplayRows);
		String mandatoryRows = "";
		String checkedInVault = "";
		String checkedExtCustodian = "";
		String checkedAudit = "";
		HashMap hmMandatoryRows = getMapFromString(mandatoryRows);
		HashMap hmCheckedInVault = getMapFromString(checkedInVault);
		HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
		HashMap hmCheckedAudit = getMapFromString(checkedAudit);
		ICheckList checkList = null;
		ICheckListTrxValue checkListTrxVal = null;

		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, limitID);
		owner.setCollateralID(limitID);
		owner.setLimitProfileID(limitProfileID);
		
		try {
			
			if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				System.out.println("In generateDeferralsForSCOD()...checkListID="+checkListID);
				if ((orgCode != null) && orgCode.equals("null")) {
					orgCode = null;
				}
				checkList = checklistProxyManager.getDefaultFacilityCheckList(owner,"IN", secType, secSubType, "", "", "");
				checkList = linkInsuranceReceipt(checkList,checklistProxyManager);
			} else {
				System.out.println("In generateDeferralsForSCOD()...checkListID="+checkListID);
				checkListTrxVal = checklistProxyManager.getCheckList(checkListID);
				checkList = checkListTrxVal.getCheckList();
         
				if (checkList.getTemplateID() <= 0) {
					ITemplate template = checklistTemplateProxyManager.getCollateralTemplate(secType, secSubType, limitBkgLoc);
					if (template != null) {
						checkList.setTemplateID(template.getTemplateID());
					}
				}
			}
		
			if (checkList.getCheckListItemList() != null) {
				Arrays.sort(checkList.getCheckListItemList());
			}
		
			String checkListStatus = checkList.getCheckListStatus();
			// perform sorting only if checklist status is not NEW
			if ((checkListStatus == null)
				|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
				ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
				checkList.setCheckListItemList(sortedItems);
			}
			System.out.println("In generateDeferralsForSCOD()...checkListStatus="+checkListStatus);
		} catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("fail to retrieve checklist template of security, type [" + secType
					+ "], subtype [" + secSubType + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("fail to maintain security checklist", ex);
		}
		
		checkList.setUpdatedBy("SYSTEM");
		checkList.setUpdatedDate(applicationDate);
		checkList.setApprovedBy("SYSTEM");
		checkList.setApprovedDate(applicationDate);
		
		if(lmtTrxObj.getLimit()==null) {
			for(int i =0;i<checkList.getCheckListItemList().length;i++){
				if(checkList.getCheckListItemList()[i].getItemStatus().equals("AWAITING") 
						&& "Deferral on SCOD Change".equalsIgnoreCase(checkList.getCheckListItemList()[i].getItemDesc())){
					checkList.getCheckListItemList()[i].setDocDate(new Date());	
					checkList.getCheckListItemList()[i].setRemarks("SCOD = "+lmt.getScodDate());
					checkList.getCheckListItemList()[i].setUpdatedBy("SYSTEM");
					checkList.getCheckListItemList()[i].setUpdatedDate(applicationDate);
					checkList.getCheckListItemList()[i].setApprovedBy("SYSTEM");
					checkList.getCheckListItemList()[i].setApprovedDate(applicationDate);
				}
			}
		}
		
		if(lmtTrxObj.getLimit()!=null) {
			//Delete existing SCOD deferral
			for(int i =0;i<checkList.getCheckListItemList().length;i++){
				if(checkList.getCheckListItemList()[i].getItemStatus().equals("AWAITING") 
						&& "Deferral on SCOD Change".equalsIgnoreCase(checkList.getCheckListItemList()[i].getItemDesc())){
				checkList.getCheckListItemList()[i].setReceivedDate(applicationDate);
				checkList.getCheckListItemList()[i].setItemStatus("INACTIVE");
				checkList.getCheckListItemList()[i].setIsDeletedInd(true);
				checkList.getCheckListItemList()[i].setUpdatedBy("SYSTEM");
				checkList.getCheckListItemList()[i].setUpdatedDate(applicationDate);
				checkList.getCheckListItemList()[i].setApprovedBy("SYSTEM");
				checkList.getCheckListItemList()[i].setApprovedDate(applicationDate);
				}
			}
		}
		
		//To create checklist
		if (checkListTrxVal == null) {
			System.out.println("In generateDeferralsForSCOD()..if()....checkListTrxVal="+checkListTrxVal);
			try {
				checkListTrxVal = checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
			}
		}
		//To update checklist
		else {
			System.out.println("In generateDeferralsForSCOD()...else()....checkListTrxVal="+checkListTrxVal);
			ICheckListItem temp1[] = checkList.getCheckListItemList();
			if (temp1 != null) {
				for (int i = 0; i < temp1.length; i++) {

					if (isItemDeleted(checkList, i)) {
						continue;
					}
					if (!checkList.getCheckListItemList()[i].getIsInherited()) {
						if (hmMandatoryRows.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsMandatoryInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsMandatoryInd(false);
						}
					}
					if (!checkList.getCheckListItemList()[i].getIsInherited()) {
						if (hmMandatoryDisplayRows.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(false);
						}
					}
					if (hmCheckedInVault.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsInVaultInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsInVaultInd(false);
					}
					if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsExtCustInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsExtCustInd(false);
					}
					if (hmCheckedAudit.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsAuditInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsAuditInd(false);
					}
				}
			}
			try {
				System.out.println("In generateDeferralsForSCOD()...before makerUpdateCheckListWithoutApproval()");
				checkListTrxVal = checklistProxyManager.makerUpdateCheckListWithoutApproval(ctx,checkListTrxVal, checkList);
				System.out.println("In generateDeferralsForSCOD()...after makerUpdateCheckListWithoutApproval()");
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to submit checklist update workflow", ex);
			}
		}
		//to create new deferral
		if(lmtTrxObj.getLimit()!=null) {
			OBDocumentItem scodDocument = new OBDocumentItem();
			try {
				ILimitDAO dao = LimitDAOFactory.getDAO();
				scodDocument=dao.getSCODDocument();
				String docRefId=new SimpleDateFormat("yyyyMMdd").format(new Date())+dao.getDocSeqId();
				String docId=new SimpleDateFormat("yyyyMMdd").format(new Date())+dao.getDocSeqId();
				String remarks="SCOD = "+lmt.getScodDate();
				//create new Ram statement with pending status
				dao.insertSCODChecklistItem(docId,scodDocument,checkList.getCheckListID(),docRefId,remarks);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Add newItems to the checklist. This will also spawn a specific insurance
	 * policy a predefined premium receipt. As for now, the matrix is stored in
	 * common code catetory entry.
	 * 
	 * @param checkList of type ICheckList
	 * @param checklistProxyManager 
	 * @return ICheckList checklist updated with new checklist items
	 * @throws com.integrosys.cms.app.checklist.bus.CheckListException on any
	 *         errors encountered
	 */
	private static ICheckList linkInsuranceReceipt(ICheckList checkList, ICheckListProxyManager checklistProxyManager) throws CheckListException {
		System.out.println("In generateDeferralsForSCOD()...IN linkInsuranceReceipt()");
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
	
	private static boolean isItemDeleted(ICheckList checkList, int i) {
		System.out.println("In generateDeferralsForSCOD()...IN isItemDeleted()");
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}
	
	private static HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		return hm;
	}
	
	private static long getReferenceId(String trxId) {
		String referenceId="-999999999";
		ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
		referenceId=dao.getReferenceId(trxId);
		return Long.parseLong(referenceId);
	}

}
