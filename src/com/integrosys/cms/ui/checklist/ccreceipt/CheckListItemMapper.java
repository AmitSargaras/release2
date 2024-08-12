/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

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

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.31 $
 * @since $Date: 2006/08/22 03:58:35 $ Tag: $Name: $
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
		CCReceiptForm aForm = (CCReceiptForm) cForm;
		String event = aForm.getEvent();
		ICheckListItem temp = (ICheckListItem) map.get("checkListItem");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		// Start for CR-17
		if (!("save_delete".equals(event) || "save_perm_uplift".equals(event) || "save_temp_uplift".equals(event) || "save_relodge"
				.equals(event))) {
			temp.setShared("true".equals(aForm.getShared()));
			IShareDoc[] aIShareDoc = this.getIShareDocFormToOB(aForm);
			temp.setShareCheckList(aIShareDoc);
		}
		DefaultLogger.debug(this, ">>>>>>>>>>>..... event: " + event);

		if (CCReceiptAction.EVENT_REFRESH_SHARE_CHECKLIST.equals(event)) {
			String prevEvent = (String) map.get("prev_event");
			if ((prevEvent != null) && !prevEvent.equals("view_ok")) {
				// if(prevEvent.equals("ALLOW_RELODGE") ||
				// prevEvent.equals("ALLOW_TEMP_UPLIFT") ||
				// prevEvent.equals("ALLOW_PERM_UPLIFT")) {
				// event = prevEvent;
				// } else {
				event = "save_" + prevEvent.toLowerCase();
				// }
			}
			DefaultLogger.debug(this, ">>>>>>>>>>>..... prev event: " + prevEvent);
			DefaultLogger.debug(this, ">>>>>>>>>>>..... new  event: " + event);
		}
		// End for CR-17

		if ("save_defer_req".equals(event) || "save_defer".equals(event)) {
			temp.setDeferExpiryDate(DateUtil.convertDate(locale, aForm.getDeferDate()));
			temp.setExpectedReturnDate(DateUtil.convertDate(locale, aForm.getExpectedReturnDate())); // cr
																									// 36
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			temp.setIsApprovedInd(false);
			if ("save_defer".equals(event)) {
				temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			}
			return temp;
		}
		if ("save_receive".equals(event)) {
			temp.setDocRef(aForm.getDocRef());
			temp.setFormNo(aForm.getFormNo());
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
			temp.setReceivedDate(DateUtil.convertDate(locale, aForm.getReceivedDate()));
			temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setIsApprovedInd(false);
			temp.setRemarks(aForm.getDocRemarks());
			return temp;
		}
		if (aForm.getAwaiting().equals("true")) {
			temp.setDocRef(aForm.getDocRef());
			temp.setFormNo(aForm.getFormNo());
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
			temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setIsApprovedInd(false);
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setRemarks(aForm.getDocRemarks());
			return temp;
		}
		if ("save_remind".equals(event) || "save_waiver_req".equals(event)
				|| "save_complete".equals(event)
				|| "save_update_narration".equals(event) // CMSSP-619
				|| "save_waiver".equals(event)|| "save_renew".equals(event)
                || "save_redeem".equals(event)) {
			temp.setActionParty(aForm.getActionParty());
			temp.setIsApprovedInd(false);
			temp.setRemarks(aForm.getDocRemarks());
			if (!"save_update_narration".equals(event)) {
				temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			}
			if("save_complete".equals(event)){
				temp.setCompletedDate(DateUtil.convertDate(locale, aForm.getCompletedDate()));
			}
			if("save_waiver".equals(event) ){
				temp.setWaivedDate(DateUtil.convertDate(locale, aForm.getWaivedDate()));
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
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));

			return temp;
		}

		if ("save_update".equals(event)) {
			temp.setDocRef(aForm.getDocRef());
			temp.setFormNo(aForm.getFormNo());
			temp.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
			temp.setExpiryDate(DateUtil.convertDate(locale, aForm.getDocExpDate()));
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
		}
		// CMSSP-619
		if ("save_update_renewal".equals(event)) {
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setRemarks(aForm.getDocRemarks());
		}

		if ("ALLOW_RELODGE".equals(event) || "ALLOW_TEMP_UPLIFT".equals(event) || "ALLOW_PERM_UPLIFT".equals(event)
				|| "save_relodge".equals(event) || "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event)) {
			temp.setIdentifyDate(DateUtil.convertDate(locale, aForm.getIdentifyDate()));
			temp.setActionParty(aForm.getActionParty());
			temp.setRemarks(aForm.getDocRemarks());
			if("save_relodge".equals(event)){
				temp.setLodgedDate(DateUtil.convertDate(locale, aForm.getLodgedDate()));	
			}
			if("save_temp_uplift".equals(event)){
				temp.setExpectedReturnDate(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()));
				temp.setTempUpliftedDate(DateUtil.convertDate(locale, aForm.getTempUpliftedDate()));	
			}
			if("save_perm_uplift".equals(event)){
				temp.setPermUpliftedDate(DateUtil.convertDate(locale, aForm.getPermUpliftedDate()));	
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
		CCReceiptForm aForm = (CCReceiptForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			ICheckListItem tempOb = (ICheckListItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setActionParty(tempOb.getActionParty());
			aForm.setDocRemarks(tempOb.getRemarks());
			DefaultLogger.debug(this, "Remarks: " + aForm.getDocRemarks());
			aForm.setDocRef(tempOb.getDocRef());
			aForm.setFormNo(tempOb.getFormNo());
			aForm.setDocDate(DateUtil.formatDate(locale, tempOb.getDocDate()));
			aForm.setWaivedDate(DateUtil.formatDate(locale, tempOb.getWaivedDate()));
			aForm.setDocExpDate(DateUtil.formatDate(locale, tempOb.getExpiryDate()));
			aForm.setExpectedReturnDate(DateUtil.formatDate(locale, tempOb.getExpectedReturnDate()));
			aForm.setReceivedDate(DateUtil.formatDate(locale, tempOb.getReceivedDate()));
			aForm.setIdentifyDate(DateUtil.formatDate(locale, tempOb.getIdentifyDate()));
			aForm.setDeferDate(DateUtil.formatDate(locale, tempOb.getDeferExpiryDate()));
			aForm.setDeferExtendedDate(DateUtil.formatDate(locale, tempOb.getDeferExtendedDate()));
			aForm.setCompletedDate(DateUtil.formatDate(locale, tempOb.getCompletedDate()));
			aForm.setLodgedDate(DateUtil.formatDate(locale, tempOb.getLodgedDate()));
			aForm.setTempUpliftedDate(DateUtil.formatDate(locale, tempOb.getTempUpliftedDate()));
			aForm.setPermUpliftedDate(DateUtil.formatDate(locale, tempOb.getPermUpliftedDate()));
																									// cr
																									// 36
			aForm.setDocNo(String.valueOf(tempOb.getCheckListItemRef()));
			aForm.setOrigDeferDate(DateUtil.formatDate(locale, tempOb.getDeferExpiryDate()));// CMSSP
																								// -
																								// 522
			aForm.setOrigDeferExtendedDate(DateUtil.formatDate(locale, tempOb.getDeferExtendedDate()));// CMSSP
																										// -
																										// 522
			if(tempOb.getParentCheckListItemRef()>0){
				aForm.setParentCheckListItemRef(String.valueOf(tempOb.getParentCheckListItemRef()));
			}

			// Start for CR-17
			DefaultLogger.debug(this, "mapOBToForm --> tempOb.getShared() = " + tempOb.getShared());
			if (tempOb.getShared()) {
				aForm.setShared("true");
			}
			else {
				aForm.setShared("false");
			}

			IShareDoc[] aShareDoc = tempOb.getShareCheckList();
			if ((aShareDoc != null) && (aShareDoc.length > 0)) {
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
				String[] leID = new String[aShareDoc.length];
				String[] leName = new String[aShareDoc.length];
				// String[] securityDtlId = new String[aShareDoc.length] ;
				// String[] securityType = new String[aShareDoc.length] ;
				// String[] securitySubType = new String[aShareDoc.length] ;

				for (int index = 0; index < aShareDoc.length; index++) {
					sno[index] = (index + 1) + "";
					checkListId[index] = aShareDoc[index].getCheckListId() + "";
					// isDeletedInd[index] = aShareDoc[index].getIsDeletedInd()
					// + "";
					details[index] = (aShareDoc[index].getDetails() != null ? aShareDoc[index].getDetails() : "");
					docShareId[index] = aShareDoc[index].getDocShareId() + "";
					docShareIdRef[index] = aShareDoc[index].getDocShareIdRef() + "";
					profileId[index] = aShareDoc[index].getProfileId() + "";
					subProfileId[index] = aShareDoc[index].getSubProfileId() + "";
					pledgorDtlId[index] = aShareDoc[index].getPledgorDtlId() + "";
					collateralId[index] = aShareDoc[index].getCollateralId() + "";
					// OBShareDoc oBShareDoc =
					// this.setLeNameFromDB(profileId[index]);
					// leID[index] =( oBShareDoc.getLeID() != null ?
					// oBShareDoc.getLeID() : "");
					// leName[index] =( oBShareDoc.getLeName() != null ?
					// oBShareDoc.getLeName() : "");
					leID[index] = aShareDoc[index].getLeID() + "";
					leName[index] = aShareDoc[index].getLeName() + "";
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
				aForm.setLeID(leID);
				aForm.setLeName(leName);
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
				aForm.setLeID(null);
				aForm.setLeName(null);
			}
			// End for CR-17
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	private IShareDoc[] getIShareDocFormToOB(CCReceiptForm aForm) {

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
		String[] leID = aForm.getLeID();
		String[] leName = aForm.getLeName();

		String[] deleteCheckListId = aForm.getDeleteCheckListId();
		List deleteCheckListIdList = new ArrayList();
		if ((deleteCheckListId != null) && (deleteCheckListId.length > 0)) {
			deleteCheckListIdList = Arrays.asList(deleteCheckListId);
		}
		DefaultLogger.debug(this, " deleteCheckListIdList = " + deleteCheckListIdList);
		ArrayList shareCheckList = new ArrayList();
		if ((sno != null) && (sno.length > 0)) {
			DefaultLogger.debug(this, " Input ShareCheckList Records = " + sno.length);
			for (int index = 0; index < sno.length; index++) {
				OBShareDoc obj = new OBShareDoc();
				try {
					obj.setDocShareId(docShareId[index] != null ? Long.parseLong(docShareId[index]) : 0);
					obj.setDocShareIdRef(docShareIdRef[index] != null ? Long.parseLong(docShareIdRef[index]) : 0);
					obj.setCheckListId(((checkListId[index] != null) && !("".equals(checkListId[index]))) ? new Long(
							checkListId[index]).longValue() : 0);
					obj.setProfileId(profileId[index] != null ? Long.parseLong(profileId[index]) : 0);
					obj.setSubProfileId(subProfileId[index] != null ? Long.parseLong(subProfileId[index]) : 0);
					obj.setPledgorDtlId(pledgorDtlId[index] != null ? Long.parseLong(pledgorDtlId[index]) : 0);
					obj.setCollateralId(collateralId[index] != null ? Long.parseLong(collateralId[index]) : 0);
					obj.setDetails(details[index] != null ? details[index] : " ");
					// if (isDeletedInd[index] != null &&
					// isDeletedInd[index].equals("true")) {
					// obj.setIsDeletedInd(true);
					// obj.setStatus("AWAITING"); //not required; DB to store
					// either "" or "DELETED" status
					// obj.setStatus("");
					// }
				}
				catch (Exception e) {
				}
				obj.setLeID(leID[index] != null ? leID[index] : "");
				obj.setLeName(leName[index] != null ? leName[index] : "");

				if ((deleteCheckListIdList != null) && deleteCheckListIdList.contains(index + "")) {
					DefaultLogger.debug(this, " Not adding Record Index = " + index + " CheckListId()"
							+ obj.getCheckListId());
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
				 * null && deleteCheckListIdList.contains(index + "")) {
				 * DefaultLogger.debug(this, " Not adding new Record Index = " +
				 * index + " CheckListId()" + obj.getCheckListId()); } else {
				 * DefaultLogger.debug(this, " Adding new Record Index = " +
				 * index + " CheckListId()" + obj.getCheckListId());
				 * shareCheckList.add(obj); //obj.setStatus("CREATE"); } } else
				 * { if (deleteCheckListIdList != null &&
				 * deleteCheckListIdList.contains(index + "")) {
				 * obj.setDeleteCheckListId(index + ""); //
				 * obj.setIsDeletedInd(true); obj.setStatus("AWAITING");
				 * DefaultLogger.debug(this,
				 * " Existing Record to the deleted Index = " + index +
				 * " CheckListId()" + obj.getCheckListId()); }else{
				 * shareCheckList.add(obj); } }
				 */
			}

		}
		DefaultLogger.debug(this, " OutPut ShareCheckList Records = " + shareCheckList.size());
		IShareDoc[] aIShareDoc = (IShareDoc[]) shareCheckList.toArray(new IShareDoc[0]);
		return aIShareDoc;
	}
}
