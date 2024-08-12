/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.IShareDoc;
import com.integrosys.cms.app.checklist.bus.OBShareDoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.30 $
 * @since $Date: 2006/10/09 09:53:40 $ Tag: $Name: $
 */

public class CheckListItemMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CheckListItemMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }, // used bt
																		// R1.5,
																		// CR17:
																		// Share
																		// Checklist
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, ICMSCustomer.class.getName() , GLOBAL_SCOPE }
		});
	}
	
	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		SecurityReceiptForm aForm = (SecurityReceiptForm) cForm;
		String event = aForm.getEvent();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICheckListItem temp = (ICheckListItem) map.get("checkListItem");
	
		if(!"prepare_tag_untag_image_view".equals(event)) {
		if(aForm.getDeferExtendedDate()!=null){
			temp.setDeferExtendedDate(DateUtil.convertDate(locale, aForm.getDeferExtendedDate()));
			}
		if(aForm.getCreditApprover()!=null){
		temp.setCreditApprover(aForm.getCreditApprover());
		}
		if(aForm.getItemStatus()!=null){
			temp.setItemStatus(aForm.getItemStatus());
			}
		if(aForm.getDocumentStatus()!=null && !aForm.getDocumentStatus().equals("") && !aForm.getDocumentStatus().equals("ACTIVE") &&!aForm.getDocumentStatus().equals("INACTIVE") ){
			if("UPDATE_RECEIVED".equals(temp.getItemStatus()) && "UPDATE_RECEIVED".equals(aForm.getItemStatus())) {
//				temp.setItemStatus(aForm.getDocumentStatus());
				}else {
					temp.setItemStatus(aForm.getDocumentStatus());
				}
		}
		if(aForm.getTenureCount()!=null && !aForm.getTenureCount().trim().equals("")){
			temp.setTenureCount(Integer.parseInt(aForm.getTenureCount()));
			}
		if(aForm.getTenureType()!=null && !aForm.getTenureType().trim().equals("")){
			temp.setTenureType(aForm.getTenureType());
			}
		if(aForm.getDeferCount()!=null && !aForm.getDeferCount().trim().equals("")){
			temp.setDeferCount(aForm.getDeferCount());
			}
		if(aForm.getDeferedDays()!=null && !aForm.getDeferedDays().trim().equals("")){
			temp.setDeferedDays(aForm.getDeferedDays());
			}
		if(aForm.getOriginalTargetDate()!=null){
			temp.setOriginalTargetDate(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()));
		}
		if(aForm.getHdfcAmt()!=null){
			temp.setHdfcAmt(UIUtil.removeComma(aForm.getHdfcAmt()));
		}
		if(aForm.getDocAmt()!=null){
			temp.setDocAmt(UIUtil.removeComma(aForm.getDocAmt()));
		}
		if(aForm.getCurrency()!=null){
			temp.setCurrency(aForm.getCurrency());
		}
		if(StringUtils.isNotBlank(aForm.getRocCharge())) {
			temp.setRocCharge(aForm.getRocCharge());
		}
		
		if("prepare_tag_untag_image".equals(event)){
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
            temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
            temp.setReceivedDate(DateUtil.convertDate(locale, aForm.getReceivedDate()));
			temp.setRemarks(aForm.getDocRemarks());
		}
		
		temp.setOriginalTargetDate(DateUtil.convertDate(locale,aForm.getOriginalTargetDate()));
		// Start for CR-17
		if (!("save_delete".equals(event) || "save_perm_uplift".equals(event) || "save_temp_uplift".equals(event) || "save_relodge"
				.equals(event))) {
			temp.setShared("true".equals(aForm.getShared()));
			IShareDoc[] aIShareDoc = this.getIShareDocFormToOB(aForm);
			temp.setShareCheckList(aIShareDoc);
		}
		
		DefaultLogger.debug(this, ">>>>>>>>>>>..... event: " + event);
		
		if("save_relodge".equals(event)){
			temp.setLodgedDate(DateUtil.convertDate(locale, aForm.getLodgedDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			
			DefaultLogger.debug(this, ">>>>>>>>>>>go to event: " + event);
		}

		if (SecurityReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
			String prevEvent = (String) map.get("prev_event");
			if ((prevEvent != null) && !prevEvent.equals("view_ok")) {
				event = "save_" + prevEvent.toLowerCase();
			}
			DefaultLogger.debug(this, ">>>>>>>>>>>..... prev event: " + prevEvent);
			DefaultLogger.debug(this, ">>>>>>>>>>>..... new  event: " + event);
		}
		// End for CR-17

		if ("save_defer_req".equals(event) || "save_defer".equals(event)) {
			temp.setDeferExpiryDate(DateUtil.convertDate(locale, aForm.getDeferDate()));
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
			 temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			temp.setExpectedReturnDate(DateUtil.convertDate(locale, aForm.getExpectedReturnDate())); // cr
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>---go to save_defer event: ");
			DefaultLogger.debug(this, "ExpectedReturnDate is >>>>>>>>>>>>>>" + aForm.getExpectedReturnDate());
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate())); // cr122
																							// cms
																							// -
																							// 2582
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			temp.setIsApprovedInd(false);
			
			return temp;
		}
		if ("save_receive".equals(event) || aForm.getAwaiting().equals("true") || "save_discrepancy".equals(event)) {
			temp.setDocRef(aForm.getDocRef());
			temp.setFormNo(aForm.getFormNo());
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate())); // cr122
																							// cms
																							// -
																							// 2582

            temp.getItem().setMonitorType(aForm.getMonitorType());      //monitor type is maintain in the system now
//			if ((aForm.getIsPolicy() != null) && aForm.getIsPolicy().equals("true")) {
//				if (aForm.getIsEffDateChanged().equals("true")) {
//					temp.setEffectiveDate(DateUtil.convertDate(locale, aForm.getDocEffDate()));
//				}
//			}

            temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
            temp.setReceivedDate(DateUtil.convertDate(locale, aForm.getReceivedDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			temp.setIsApprovedInd(false);
			//Added for image tag untag with security receipt
			//temp.setSecImageTagUntagId(aForm.getSecImageTagUntagId());
			temp.setSecImageTagUntagImgName(aForm.getSecImageTagUntagImgName());
			//temp.setSecImageTagUntagStatus(aForm.getSecImageTagUntagStatus());
//			temp.setSecImageTagUntagId(aForm.getDocDesc());
//			temp.setSecImageTagUntagStatus(aForm.getDocumentStatus());
			//End:Added for image tag untag with security receipt
			
            return temp;
		}
		if ("save_remind".equals(event) || "save_waiver_req".equals(event) || "save_complete".equals(event)
				|| "save_waiver".equals(event) || "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event)
				|| "save_renew".equals(event) || "save_update_narration".equals(event) || "save_redeem".equals(event)) {
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			temp.setIsApprovedInd(false);
			if("save_complete".equals(event)){
				temp.setCompletedDate(DateUtil.convertDate(locale, aForm.getCompletedDate()));
			}
			if("save_waiver".equals(event)){
				temp.setWaivedDate(DateUtil.convertDate(locale, aForm.getWaivedDate()));
				temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
				temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			}
			if (!"save_update_narration".equals(event)) {
				temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			}
			if("save_temp_uplift".equals(event)){
				temp.setTempUpliftedDate(DateUtil.convertDate(locale, aForm.getTempUpliftedDate()));
				temp.setExpectedReturnDate(DateUtil.convertDate(locale,aForm.getExpectedReturnDate()));
			}
			if ("save_perm_uplift".equals(event)) {
				temp.setPermUpliftedDate(DateUtil.convertDate(locale, aForm.getPermUpliftedDate()));
			}
			if("save_renew".equals(event) ){
			    temp.setReceivedDate(null);
			}
			return temp;
		}
		if ("view_ok".equals(event)) {
			return temp;
		}
		if ("save_delete".equals(event)) {
			temp.setRemarks(aForm.getDocRemarks());
			temp.setIsApprovedInd(false);
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate())); // CR122
																							// cms
																							// -
																							// 2582
			return temp;
		}

		if ("save_update".equals(event)) {
			temp.setDocRef(aForm.getDocRef());
			temp.setFormNo(aForm.getFormNo());
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
            temp.getItem().setMonitorType(aForm.getMonitorType());      //monitor type is maintain in the system now
//			if ((aForm.getIsPolicy() != null) && aForm.getIsPolicy().equals("true")) {
//				if (aForm.getIsEffDateChanged().equals("true")) {
//					temp.setEffectiveDate(DateUtil.convertDate(locale, aForm.getDocEffDate()));
//				}
//			}
			temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
		}
		// CMSSP-619 Update action for renewed documents
		if ("save_update_renewal".equals(event)) {
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setRemarks(aForm.getDocRemarks());
		}
		}

		DefaultLogger.debug(this, "Going out of  Map Form to OB ");
		return temp;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		SecurityReceiptForm aForm = (SecurityReceiptForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		if (obj != null) {
			ICheckListItem tempOb = (ICheckListItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setActionParty(tempOb.getActionParty());
			aForm.setDocumentStatus(tempOb.getDocumentStatus());
//			aForm.setCreditApprover(tempOb.getCreditApprover());
			if(tempOb.getTenureType()!=null)
			aForm.setTenureType(tempOb.getTenureType());
			if(tempOb.getDeferCount()!=null)
				aForm.setDeferCount(tempOb.getDeferCount());
			if(tempOb.getDeferedDays()!=null)
				aForm.setDeferedDays(tempOb.getDeferedDays());
			if(tempOb.getTenureCount()!=0)
			aForm.setTenureCount(String.valueOf(tempOb.getTenureCount()));
//			aForm.setDocRemarks(tempOb.getRemarks());
			aForm.setDocRef(tempOb.getDocRef());
			aForm.setFormNo(tempOb.getFormNo());
            aForm.setMonitorType(tempOb.getItem().getMonitorType());
//			aForm.setDocDate(DateUtil.formatDate(locale, tempOb.getDocDate()));
//			aForm.setDocExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
			aForm.setIdentifyDate(DateUtil.formatDate(locale, tempOb.getIdentifyDate()));
			aForm.setDeferDate(DateUtil.formatDate(locale, tempOb.getDeferExpiryDate()));
			aForm.setDocEffDate(DateUtil.formatDate(locale, tempOb.getEffectiveDate()));
			aForm.setDocOldEffDate(DateUtil.formatDate(locale, tempOb.getEffectiveDate()));
			aForm.setTempUpliftedDate(DateUtil.formatDate(locale, tempOb.getTempUpliftedDate()));
			aForm.setExpectedReturnDate(DateUtil.formatDate(locale, tempOb.getExpectedReturnDate()));
			aForm.setLodgedDate(DateUtil.formatDate(locale, tempOb.getLodgedDate()));
			aForm.setReceivedDate(DateUtil.formatDate(locale, tempOb.getReceivedDate()));
			aForm.setPermUpliftedDate(DateUtil.formatDate(locale, tempOb.getPermUpliftedDate()));
			aForm.setDeferExtendedDate(DateUtil.formatDate(locale, tempOb.getDeferExtendedDate())); 
			if(tempOb.getParentCheckListItemRef()>0){
				aForm.setParentCheckListItemRef(String.valueOf(tempOb.getParentCheckListItemRef()));
			}
																							// cr
																							// 36
			aForm.setCompletedDate(DateUtil.formatDate(locale, tempOb.getCompletedDate()));
			aForm.setWaivedDate(DateUtil.formatDate(locale, tempOb.getWaivedDate()));
			aForm.setDocNo(String.valueOf(tempOb.getCheckListItemRef()));
			aForm.setOrigDeferDate(DateUtil.formatDate(locale, tempOb.getDeferExpiryDate()));
			
//			aForm.setHdfcAmt(UIUtil.formatWithCommaAndDecimal(tempOb.getHdfcAmt()));
			aForm.setCurrency(tempOb.getCurrency());
//			aForm.setDocAmt(UIUtil.formatWithCommaAndDecimal(tempOb.getDocAmt()));
//			aForm.setOriginalTargetDate(DateUtil.formatDate(locale, tempOb.getOriginalTargetDate()));
			aForm.setItemStatus(tempOb.getItemStatus());

			boolean isCustomerCorporate = CheckListHelper.getAllowedCorporateEntities().contains(customer.getEntity());
			System.out.println("/secreceipt/checklistItemMapper.java=>isCustomerCorporate=>"+isCustomerCorporate);
			if(isCustomerCorporate && StringUtils.isBlank(tempOb.getRocCharge())) {
				aForm.setRocCharge(ICMSConstant.YES);
			}
			else {
				aForm.setRocCharge(tempOb.getRocCharge());
			}
			
			
			//Added for image tag untag with security receipt
//			aForm.setSecImageTagUntagId(tempOb.getSecImageTagUntagId());
//			aForm.setSecImageTagUntagImgName(tempOb.getSecImageTagUntagImgName());
//			aForm.setSecImageTagUntagStatus(tempOb.getSecImageTagUntagStatus());
			//End:Added for image tag untag with security receipt
			
			
			if(!"imagetaguntaglist".equals(aForm.getEvent()) && !"securityreceipt_image_tag_untag_submit".equals(aForm.getEvent())) {
				aForm.setCreditApprover(tempOb.getCreditApprover());
				aForm.setDocDate(DateUtil.formatDate(locale, tempOb.getDocDate()));
				aForm.setDocExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
				aForm.setHdfcAmt(UIUtil.formatWithCommaAndDecimal(tempOb.getHdfcAmt()));
				aForm.setDocAmt(UIUtil.formatWithCommaAndDecimal(tempOb.getDocAmt()));
				aForm.setOriginalTargetDate(DateUtil.formatDate(locale, tempOb.getOriginalTargetDate()));
				aForm.setDocRemarks(tempOb.getRemarks());
				}
				
				if("securityreceipt_image_tag_untag_submit".equals(aForm.getEvent()) || "RECEIVECANCEL".equals(aForm.getEvent())) {
					String combinedStrng = aForm.getSecImageTagUntagId();
					String[] st = new String[7];
					st=combinedStrng.split("-");
					aForm.setDocAmt(st[0].trim());
					aForm.setHdfcAmt(st[1].trim());
					aForm.setDocDate(st[2].trim());
					aForm.setOriginalTargetDate(st[3].trim());
					aForm.setDocExpDate(st[4].trim());
					aForm.setCreditApprover(st[5].trim());
					aForm.setDocRemarks(st[6].trim());
				}
				
				if("cancel_tag_untag_image".equals(aForm.getEvent()) || "tag_untag_image".equals(aForm.getEvent())){
					aForm.setDocDate(DateUtil.formatDate(locale, tempOb.getDocDate()));
					aForm.setDocExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
					aForm.setReceivedDate(DateUtil.formatDate(locale, tempOb.getReceivedDate()));
					aForm.setRemarks(aForm.getDocRemarks());
				}
//				if("RECEIVECANCEL".equals(aForm.getEvent())) {
//					aForm.setSecImageTagUntagImgName("");
//				}
				
				if("RECEIVE".equals(aForm.getEvent()) && ("UPDATE_RECEIVED".equals(tempOb.getItemStatus()) || ("PENDING_RECEIVED".equals(tempOb.getItemStatus())))) {
					aForm.setSecImageTagUntagImgName(tempOb.getSecImageTagUntagImgName());
				}else if("RECEIVE".equals(aForm.getEvent()) && !"UPDATE_RECEIVED".equals(tempOb.getItemStatus()) && !("PENDING_RECEIVED".equals(tempOb.getItemStatus()))) {
					aForm.setSecImageTagUntagId("");
					aForm.setSecImageTagUntagImgName("");
					aForm.setSecImageTagUntagStatus("");
				}
//
			
			// CMSSP
																								// -
																								// 522
			aForm.setOrigDeferExtendedDate(DateUtil.formatDate(locale, tempOb.getDeferExtendedDate())); // CMSSP
																										// -
																										// 522
			
			// Start for CR-17
			if (tempOb.getShared()) {
				aForm.setShared("true");
			}
			else {
				aForm.setShared("false");
			}

			IShareDoc[] aShareDoc = tempOb.getShareCheckList();
			if ((aShareDoc != null) && (aShareDoc.length > 0)) {
				DefaultLogger.debug(this, ">>>>> CheckListItemMapper: Mapping from OB to form \n");
				String[] sno = new String[aShareDoc.length];
				String[] details = new String[aShareDoc.length];
				String[] checkListId = new String[aShareDoc.length];
				// String[] isDeletedInd = new String[aShareDoc.length];
				String[] docShareId = new String[aShareDoc.length];
				String[] docShareIdRef = new String[aShareDoc.length];
				String[] profileId = new String[aShareDoc.length];
				String[] subProfileId = new String[aShareDoc.length];
				String[] pledgorDtlId = new String[aShareDoc.length];
				String[] collateralId = new String[aShareDoc.length];

				// String[] leID = new String[aShareDoc.length];
				// String[] leName = new String[aShareDoc.length];
				String[] securityDtlId = new String[aShareDoc.length];
				String[] securityType = new String[aShareDoc.length];
				String[] securitySubType = new String[aShareDoc.length];

				for (int index = 0; index < aShareDoc.length; index++) {
					DefaultLogger.debug(this, ">>>>> [" + index + "]:" + aShareDoc[index]);
					sno[index] = (index + 1) + "";
					checkListId[index] = aShareDoc[index].getCheckListId() + "";
					// isDeletedInd[index] = aShareDoc[index].getIsDeletedInd()
					// + "";
					details[index] = aShareDoc[index].getDetails();
					details[index] = (aShareDoc[index].getDetails() != null ? aShareDoc[index].getDetails() : "");
					docShareId[index] = aShareDoc[index].getDocShareId() + "";
					docShareIdRef[index] = aShareDoc[index].getDocShareIdRef() + "";
					profileId[index] = aShareDoc[index].getProfileId() + "";
					subProfileId[index] = aShareDoc[index].getSubProfileId() + "";
					pledgorDtlId[index] = aShareDoc[index].getPledgorDtlId() + "";
					collateralId[index] = aShareDoc[index].getCollateralId() + "";
					// OBShareDoc oBShareDoc =
					// this.getSecuritySubTypeDetailsFromDB(profileId[index],
					// collateralId[index]);
					// leID[index] = oBShareDoc.getLeID();
					// leName[index] = oBShareDoc.getLeName();
					// securityDtlId[index] = (oBShareDoc.getSecurityDtlId() !=
					// null ? oBShareDoc.getSecurityDtlId() : "");
					// securityType[index] = (oBShareDoc.getSecurityType() !=
					// null ? oBShareDoc.getSecurityType() : "");
					// securitySubType[index] = (oBShareDoc.getSecuritySubType()
					// != null ? oBShareDoc.getSecuritySubType() : "");
					securityDtlId[index] = aShareDoc[index].getSecurityDtlId();
					securityType[index] = aShareDoc[index].getSecurityType();
					securitySubType[index] = aShareDoc[index].getSecuritySubType();

				}
				aForm.setSno(sno);
				aForm.setCheckListId(checkListId);
				// aForm.setIsDeletedInd(isDeletedInd);
				aForm.setDetails(details);
				aForm.setDocShareId(docShareId);
				aForm.setDocShareIdRef(docShareIdRef);
				aForm.setProfileId(profileId);
				aForm.setSubProfileId(subProfileId);
				aForm.setPledgorDtlId(pledgorDtlId);
				aForm.setCollateralId(collateralId);

				// aForm.setLeID(leID);
				// aForm.setLeName(leName);
				aForm.setSecurityDtlId(securityDtlId);
				aForm.setSecurityType(securityType);
				aForm.setSecuritySubType(securitySubType);
			}
			else {
				aForm.setSno(null);
				aForm.setCheckListId(null);
				// aForm.setIsDeletedInd(null);
				aForm.setDetails(null);
				aForm.setDocShareId(null);
				aForm.setDocShareIdRef(null);
				aForm.setProfileId(null);
				aForm.setSubProfileId(null);
				aForm.setPledgorDtlId(null);
				aForm.setCollateralId(null);
				aForm.setSecurityDtlId(null);
				aForm.setSecurityType(null);
				aForm.setSecuritySubType(null);
			}
			// End for CR-17

		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	private IShareDoc[] getIShareDocFormToOB(SecurityReceiptForm aForm) {

		String[] sno = aForm.getSno();
		String[] checkListId = aForm.getCheckListId();
		String[] profileId = aForm.getProfileId();
		String[] subProfileId = aForm.getSubProfileId();
		String[] pledgorDtlId = aForm.getPledgorDtlId();
		String[] collateralId = aForm.getCollateralId();
		String[] details = aForm.getDetails();
		// String[] isDeletedInd = aForm.getIsDeletedInd();
		String[] docShareId = aForm.getDocShareId();
		String[] docShareIdRef = aForm.getDocShareIdRef();

		String[] securityDtlId = aForm.getSecurityDtlId();
		String[] securityType = aForm.getSecurityType();
		String[] securitySubType = aForm.getSecuritySubType();

		String[] deleteCheckListId = aForm.getDeleteCheckListId();
		String[] existingChkListId = aForm.getExistingChkListId();
		int deleteIdCount = 0;
		List deleteCheckListIdList = new ArrayList();
		if ((deleteCheckListId != null) && (deleteCheckListId.length > 0)) {
			deleteCheckListIdList = Arrays.asList(deleteCheckListId);
		}
		ArrayList shareCheckList = new ArrayList();
		if ((sno != null) && (sno.length > 0)) {
			DefaultLogger.debug(this, " Input ShareCheckList Records = " + sno.length);
			for (int index = 0; index < sno.length; index++) {
				OBShareDoc obj = new OBShareDoc();
				try {
					obj.setDocShareId(docShareId[index] != null ? Long.parseLong(docShareId[index]) : 0);
					obj.setDocShareIdRef(docShareIdRef[index] != null ? Long.parseLong(docShareIdRef[index]) : 0);
					obj.setCheckListId(((checkListId[index] != null) && !("".equals(checkListId[index]))) ? Long
							.parseLong(checkListId[index]) : 0);
					obj.setProfileId(profileId[index] != null ? Long.parseLong(profileId[index]) : 0);
					obj.setSubProfileId(subProfileId[index] != null ? Long.parseLong(subProfileId[index]) : 0);
					obj.setPledgorDtlId(pledgorDtlId[index] != null ? Long.parseLong(pledgorDtlId[index]) : 0);
					obj.setCollateralId(collateralId[index] != null ? Long.parseLong(collateralId[index]) : 0);
					obj.setDetails(details[index] != null ? details[index] : "");
					// if (isDeletedInd[index] != null &&
					// isDeletedInd[index].equals("true")) {
					// obj.setIsDeletedInd(true);
					// obj.setStatus("PENDING");
					// }
				}
				catch (Exception e) {
				}
				// obj.setLeID(leID[index] != null ? leID[index] : "");
				// obj.setLeName(leName[index] != null ? leID[index] : "");
				obj.setSecurityDtlId(securityDtlId[index] != null ? securityDtlId[index] : "");
				obj.setSecurityType(securityDtlId[index] != null ? securityType[index] : "");
				obj.setSecuritySubType(securityDtlId[index] != null ? securitySubType[index] : "");

				if ((deleteCheckListIdList != null) && deleteCheckListIdList.contains(index + "")) {
					DefaultLogger.debug(this, " Not adding Record Index = " + index + " CheckListId()"
							+ obj.getCheckListId());
					obj.setDeleteCheckListId(deleteCheckListId[deleteIdCount]);
					obj.setExistingChkListId(existingChkListId[index]);
					shareCheckList.add(obj);
					deleteIdCount++;
				}
				else {
					if (obj.getDocShareId() == 0) {
						DefaultLogger.debug(this, " Adding new Record Index = " + index + " CheckListId()"
								+ obj.getCheckListId());
						shareCheckList.add(obj);
					}
					else {
						DefaultLogger.debug(this, " Existing Record to the updated Index = " + index + " CheckListId()"
								+ obj.getCheckListId());
						shareCheckList.add(obj);
					}
				}

				/*
				 * if (obj.getDocShareId() == 0) { if (deleteCheckListIdList !=
				 * null && deleteCheckListIdList.contains(index + "")) { } else
				 * { shareCheckList.add(obj); obj.setStatus("CREATE"); } } else
				 * { if (deleteCheckListIdList != null &&
				 * deleteCheckListIdList.contains(index + "")) {
				 * obj.setDeleteCheckListId(index + ""); //
				 * obj.setIsDeletedInd(true); obj.setStatus("PENDING"); }
				 * shareCheckList.add(obj); }
				 */
			}

		}
		DefaultLogger.debug(this, " OutPut ShareCheckList Records = " + shareCheckList.size());
		IShareDoc[] aIShareDoc = (IShareDoc[]) shareCheckList.toArray(new IShareDoc[0]);
		return aIShareDoc;
	}
}
