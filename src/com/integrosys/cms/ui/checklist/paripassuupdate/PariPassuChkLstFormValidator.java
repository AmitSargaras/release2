package com.integrosys.cms.ui.checklist.paripassuupdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class PariPassuChkLstFormValidator {
	public static ActionErrors validateInput(UpdatePariPassuCheckListForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();

		Date d = DateUtil.getDate();
		d = DateUtil.clearTime(d);

		

		if(aForm.getActionTypeName().equals("UPDATE_DEFERRED")){
			if ((aForm.getDeferExtendedDate() == null) || (aForm.getDeferExtendedDate().length() <= 0)) {
				errors.add("deferExtendedDate", new ActionMessage("error.date.mandatory"));
			}else{
					if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))){
						errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate", "Next Due Date",
							"Last Due Date"));
					}
				}
			}
		if ("save_receive".equals(event) || aForm.getAwaiting().equals("true")) {

	//		if (!(errorCode = Validator.checkString(aForm.getDocRef(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
	//			errors.add("docRef",
	//					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
	//		}
			if ((aForm.getIsPolicy() != null) && aForm.getIsPolicy().equals("true")) {

				if (!(errorCode = Validator.checkDate(aForm.getDocEffDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					if (aForm.getDocEffDate().trim().length() > 0) {
						errors.add("docEffDate", new ActionMessage("error.date.format"));
					}
					else {
						errors.add("docEffDate", new ActionMessage("error.date.mandatory"));
					}
				}
			}
			if (!(errorCode = Validator.checkString(aForm.getFormNo(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors
						.add("formNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
								"50"));
			}

			if (!(errorCode = Validator.checkDate(aForm.getDocDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				if (aForm.getDocDate().length() > 0) {
					errors.add("docDate", new ActionMessage("error.date.format"));
				}
				else {
					errors.add("docDate", new ActionMessage("error.date.mandatory"));
				}
			}
			else {
				if (aForm.getDocDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("docDate", new ActionMessage("error.date.compareDate.more", "Doc Date",
								"Current Date"));
					}
				}
			}

			if (!(errorCode = Validator.checkDate(aForm.getDocExpDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("docExpDate", new ActionMessage("error.date.format"));
			}
			if ((aForm.getDocDate().trim().length() > 0) && (aForm.getDocExpDate().trim().length() > 0)) {
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocExpDate())).before(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocDate())))) {
					errors
							.add("docExpDate", new ActionMessage("error.date.compareDate", "Doc Expiry Date",
									"Doc Date"));
				}
			}

			/*
			 * DefaultLogger.debug(SecurityReceiptFormValidator.class.getName(),"entered date"
			 * +DateUtil.convertDate(locale,aForm.getDocDate()));
			 * DefaultLogger.debug
			 * (SecurityReceiptFormValidator.class.getName(),"System Date"+d);
			 * if(aForm.getDocDate().length() > 0) { int a =
			 * d.compareTo(DateUtil
			 * .clearTime(DateUtil.convertDate(locale,aForm.getDocDate())));
			 * DefaultLogger
			 * .debug("vaidation ***********************************"
			 * ,"Eroororr date "+ a); if(a<0){ errors.add("docDate", new
			 * ActionMessage
			 * ("error.date.compareDate.more","Doc Date","Current Date")); } }
			 */

        }

		if (!(errorCode = Validator.checkDate(aForm.getIdentifyDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("identifyDate", new ActionMessage("error.date.format"));     //this is generic checking??? 
		}

		if ("save_defer_req".equals(event) || "save_defer".equals(event)) {
			DefaultLogger.debug("aForm.getOrigDeferDate()", aForm.getOrigDeferDate());
			DefaultLogger.debug("aForm.getDeferDate()", aForm.getDeferDate());

			if ((aForm.getDeferDate() == null) || (aForm.getDeferDate().length() <= 0)) {
				errors.add("deferDate", new ActionMessage("error.date.mandatory"));
			}else{
				if(StringUtils.isNotBlank(aForm.getExpectedReturnDate())){
					if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))))){
						errors.add("expectedReturnDate", new ActionMessage("error.date.compareDate", "Next Due Date",
							"Defer Date"));
					}
				}
				
				
			}
			if ((aForm.getExpectedReturnDate() == null) || (aForm.getExpectedReturnDate().length() <= 0)) {
				errors.add("expectedReturnDate", new ActionMessage("error.date.mandatory"));
			}
			if ((aForm.getCreditApprover() == null) || (aForm.getCreditApprover().length() <= 0)) {
				errors.add("creditApprover", new ActionMessage("error.date.mandatory"));
			}

			if (!aForm.getOrigDeferDate().equals(aForm.getDeferDate())) {
				if (!(errorCode = Validator.checkDate(aForm.getDeferDate(), true, locale)).equals(Validator.ERROR_NONE)) {
					if (aForm.getDeferDate().length() > 0) {
						errors.add("deferDate", new ActionMessage("error.date.format"));
					}// else{ already captured by the null and length check
						// previously
					// errors.add("deferDate", new
					// ActionMessage("error.date.mandatory"));
					// }
				}
				else {
					if (aForm.getDeferDate().length() > 0) {
						// int a = d.compareTo();
						DefaultLogger.debug(PariPassuChkLstFormValidator.class.getName(), "Current Date " + d);
						DefaultLogger.debug(PariPassuChkLstFormValidator.class.getName(), "date from Form"
								+ aForm.getDeferDate());
						DefaultLogger.debug("Date", " Date from Form after local-------->"
								+ DateUtil.formatDate(locale, DateUtil.convertDate(locale, aForm.getDeferDate())));
						/*if (d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate())))) {
							errors.add("deferDate", new ActionMessage("error.date.future"));
						}*/
						if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate())).before(DateUtil.clearTime(DateUtil.getDate()))) {
							errors.add("deferDate", new ActionMessage("error.date.compareDate","Defer Date","Current Date"));
						}
						else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
								(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate())).before(
								DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
							errors.add("deferDate", new ActionMessage("error.date.compareDate", "Defer Date",
								"Received Date"));
						}
/*						if(StringUtils.isNotBlank(aForm.getExpectedReturnDate())){
							if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate())).before(
									DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))))){
								errors.add("expectedReturnDate", new ActionMessage("error.date.compareDate", "Expected Return Date",
									"Defer Date"));
							}
						}*/
					}
				}
			}
			if (!aForm.getOrigDeferExtendedDate().equals(aForm.getDeferExtendedDate())) {
				if (!(errorCode = Validator.checkDate(aForm.getDeferExtendedDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					if (aForm.getDeferExtendedDate().length() > 0) {
						errors.add("deferExtendedDate", new ActionMessage("error.date.format"));
					}// else{ //defer extended date is not a mandatory field.
						// Found when testing CMS-3425.
					// errors.add("deferExtendedDate", new
					// ActionMessage("error.date.mandatory"));
					// }
				}
				else {
/*					if (aForm.getDeferExtendedDate().length() > 0) {
						DefaultLogger.debug(PariPassuChkLstFormValidator.class.getName(), "date from Form"
								+ aForm.getDeferExtendedDate());

						if (d.after(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate())))) {
							errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate",
									"Extended Deferment Date", "Current Date"));
						}
					}*/
				}
			}

			if (!aForm.getOrigDeferDate().equals(aForm.getDeferDate())
					|| !aForm.getOrigDeferExtendedDate().equals(aForm.getDeferExtendedDate())) { // maintain
																									// for
																									// CMSSP
																									// -
																									// 522
/*				if ((aForm.getDeferDate().length() > 0) && (aForm.getDeferExtendedDate().length() > 0)) {
					Date extendedDeferDate = DateUtil.clearTime(DateUtil.convertDate(locale, aForm
							.getDeferExtendedDate()));
					Date deferDate = DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()));
					if (deferDate.after(extendedDeferDate)) {
						errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate",
								"Extended Deferment Date", "Defer To Date"));
					}
				}*/
			}

			if (!(errorCode = Validator.checkString(aForm.getDocRemarks(), false, 1, 1600))
					.equals(Validator.ERROR_NONE)) {
				errors.add("docRemarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"1600"));
			}

  //          if(aForm.getActionParty() == null || aForm.getActionParty().length()<=0) {
   //             errors.add("actionParty", new ActionMessage("error.mandatory"));
    //        }

        }

		if ("save_update".equals(event)) {
			// add by Naveen for cms-3384 start
			if ((aForm.getIsPolicy() != null) && aForm.getIsPolicy().equals("true")) {

				if (!(errorCode = Validator.checkDate(aForm.getDocEffDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					if (aForm.getDocEffDate().trim().length() > 0) {
						errors.add("docEffDate", new ActionMessage("error.date.format"));
					}
					else {
						errors.add("docEffDate", new ActionMessage("error.date.mandatory"));
					}
				}
			}
			// cms-3384 end
/*			if (!(errorCode = Validator.checkString(aForm.getDocRef(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("docRef",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
			}
*/
			if (!(errorCode = Validator.checkString(aForm.getFormNo(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("formNo",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
			}

			if (!(errorCode = Validator.checkDate(aForm.getDocExpDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("docExpDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkDate(aForm.getDocDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("docDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkDate(aForm.getIdentifyDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("identifyDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}

			if ((aForm.getDocDate().length() > 0) && (aForm.getDocExpDate().length() > 0)) {

				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocExpDate())).before(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocDate())))) {
					errors
							.add("docExpDate", new ActionMessage("error.date.compareDate", "Doc Expiry Date",
									"Doc Date"));
				}
			}
		}

		if ("save_remind".equals(event) || "save_receive".equals(event) || "save_waiver_req".equals(event)
				|| "save_waiver".equals(event) || "save_renew".equals(event) || "save_complete".equals(event)
				|| "save_update".equals(event) || "save_temp_uplift".equals(event) || "save_perm_uplift".equals(event)
				|| "save_delete".equals(event) || "save_relodge".equals(event)) {

			if (!(errorCode = Validator.checkString(aForm.getDocRemarks(), false, 1, 1600))
					.equals(Validator.ERROR_NONE)) {
				errors.add("docRemarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"1600"));
			}
			if("save_temp_uplift".equals(event)){
				if (aForm.getTempUpliftedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getTempUpliftedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("tempUpliftedDate", new ActionMessage("error.date.compareDate.more", "Temp Uplifted Date",
								"Current Date"));
					}
					else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
							(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getTempUpliftedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
						errors.add("tempUpliftedDate", new ActionMessage("error.date.compareDate", "Temp Uplifted Date",
							"Received Date"));
					}
				}else{
					errors.add("tempUpliftedDate", new ActionMessage("error.string.mandatory"));
				}
			}
			if("save_perm_uplift".equals(event)){
				if (aForm.getPermUpliftedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getPermUpliftedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("permUpliftedDate", new ActionMessage("error.date.compareDate.more", "Perm Uplifted Date",
								"Current Date"));
					}
					else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
							(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getPermUpliftedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
						errors.add("permUpliftedDate", new ActionMessage("error.date.compareDate", "Perm Uplifted Date",
							"Received Date"));
					}
				}else{
					errors.add("permUpliftedDate", new ActionMessage("error.string.mandatory"));
				}
			}
			if("save_receive".equals(event)){
				if (aForm.getReceivedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("receivedDate", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
					}else if((aForm.getDocDate().length() > 0)){
						if(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).before(
								DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocDate())))){
							errors.add("receivedDate", new ActionMessage("error.date.compareDate", "Receive Date",
									"Doc Date"));
						}
					}else if(StringUtils.isNotBlank(aForm.getReNewDocOldExpDate())){
						if(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).before(
								DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReNewDocOldExpDate())))){
							errors.add("receivedDate", new ActionMessage("error.date.compareDate", "Receive Date",
									"Initial Doc Expired Date"));
						}
					}
				}else{
					errors.add("receivedDate", new ActionMessage("error.string.mandatory"));
				}	
			}
			if("save_complete".equals(event)){
				if (aForm.getCompletedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getCompletedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("completedDate", new ActionMessage("error.date.compareDate.more", "Completed Date",
								"Current Date"));
					}
					else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
							(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getCompletedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
						errors.add("completedDate", new ActionMessage("error.date.compareDate", "Completed Date",
							"Received Date"));
					}
				}else{
					errors.add("completedDate", new ActionMessage("error.string.mandatory"));
				}
			}
			if("save_waiver".equals(event) ){
				if (aForm.getWaivedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getWaivedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("waivedDate", new ActionMessage("error.date.compareDate.more", "Waived Date",
								"Current Date"));
					}

					else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
							(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getWaivedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
						errors.add("waivedDate", new ActionMessage("error.date.compareDate", "Waived Date",
							"Received Date"));
					}
				}else{
					errors.add("waivedDate", new ActionMessage("error.string.mandatory"));
				}
				if(aForm.getCreditApprover()==null || aForm.getCreditApprover().trim().length()<=0)
				{
					errors.add("creditApprover", new ActionMessage("error.string.mandatory"));
				}
			}
			if("save_relodge".equals(event) ){
				if (aForm.getLodgedDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getLodgedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("lodgedDate", new ActionMessage("error.date.compareDate.more", "Lodged Date",
								"Current Date"));
					}
					else if(StringUtils.isNotBlank(aForm.getReceivedDate())&&
							(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getLodgedDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
						errors.add("lodgedDate", new ActionMessage("error.date.compareDate", "Lodged Date",
							"Received Date"));
					}
				}else{
					errors.add("lodgedDate", new ActionMessage("error.string.mandatory"));
				}
			}

   //         if(!"save_delete".equals(event)) {
   //             if(aForm.getActionParty() == null || aForm.getActionParty().length()<=0) {
    //                errors.add("actionParty", new ActionMessage("error.mandatory"));
    //            }
    //        }
        }

		// Start for cr-17
		// "save_update_narration" also not required to check, but currently the
		// action is not calling validator
		if (!("save_perm_uplift".equals(event) || "save_temp_uplift".equals(event) || "save_relodge".equals(event)
				|| "save_delete".equals(event) || UpdatePariPassuCheckListAction.EVENT_SUBMIT.equals(event) || "save"
				.equals(event))) {
			String[] checkListId = aForm.getCheckListId();
			String[] deleteCheckListId = aForm.getDeleteCheckListId();
			String itemCheckListId = aForm.getCheckListID();
			List deleteCheckListIdList = new ArrayList();
			if ((deleteCheckListId != null) && (deleteCheckListId.length > 0)) {
				deleteCheckListIdList = Arrays.asList(deleteCheckListId);
			}
			if ((aForm.getShared() != null) && ((aForm.getShared().length() == 0) || aForm.getShared().equals("true"))) {
				if ((checkListId == null) || (checkListId.length == 0)
						|| (checkListId.length == deleteCheckListIdList.size())) {
					errors.add("noChecklist", new ActionMessage("error.srChecklistId.noChecklist"));
				}
				else if ((checkListId != null) && (checkListId.length > 0)) {
					outer: for (int i = 0; i < checkListId.length; i++) {
						if (!((deleteCheckListIdList != null) && deleteCheckListIdList.contains(i + ""))) {
							if (StringUtils.isEmpty(checkListId[i]) || (checkListId[i].length() == 0)
									|| (checkListId[i].trim().length() == 0)) {
								errors.add("errCheckListId" + i, new ActionMessage("error.string.mandatory"));
							}
							else if (checkListId[i].equals(itemCheckListId)) {
								errors.add("errCheckListId" + i, new ActionMessage("error.checklistId.ownChecklist"));
							}
							else {
								if (!(StringUtils.isNumeric(checkListId[i]))) {
									errors.add("errCheckListId" + i, new ActionMessage("error.amount.format"));
								}
								else {
									for (int j = 0; j < checkListId.length; j++) {
										if (i != j) {
											if (checkListId[i].equals(checkListId[j])) {
												errors.add("errCheckListId" + i, new ActionMessage(
														"error.checklistId.duplicate"));
												break outer;
											}
										}
									} // end for loop
								}
							}
						} // end not deleted check
					}
				}// else{
				// errors.add("noChecklist", new
				// ActionMessage("error.srChecklistId.noChecklist"));
				// }

			}
			else if ((aForm.getShared() != null)
					&& ((aForm.getShared().length() == 0) || aForm.getShared().equals("false"))) {
				if ((checkListId != null) && (checkListId.length > 0)) {
					outer: for (int i = 0; i < checkListId.length; i++) {
						if (!((deleteCheckListIdList != null) && deleteCheckListIdList.contains(i + ""))) {
							errors.add("noChecklist", new ActionMessage("error.srChecklistId.deleteChecklist"));
							break outer;
						}
					}
				}
			}
		}
		// End for cr-17


        //check for lawyer information
        if(UpdatePariPassuCheckListAction.EVENT_SUBMIT.equals(event)) {
           // DefaultLogger.debug("SecurityFormValidator", ">>>>> Panel Flag = " + aForm.getLawFirmPanelFlag());
          //  DefaultLogger.debug("SecurityFormValidator", ">>>>> Panel Lawyer = " + aForm.getPanellawyerLegalFirm());
           // DefaultLogger.debug("SecurityFormValidator", ">>>>> Non Panel Lawyer = " + aForm.getNonPanellawyerLegalFirm());
          //  DefaultLogger.debug("SecurityFormValidator", ">>>>> Lawyer Address = " + aForm.getLawFirmAddress());
            if(aForm.getLawFirmPanelFlag() == null||aForm.getLawFirmPanelFlag().length()==0) {
                errors.add("lawFirmPanelFlag", new ActionMessage("error.mandatory"));
            } else if(("Y".equals(aForm.getLawFirmPanelFlag()))){
            	if(aForm.getPanellawyerLegalFirm() == null || aForm.getPanellawyerLegalFirm().length() <= 0){
            		errors.add("lawyerLegalFirm", new ActionMessage("error.mandatory"));
            	}
            	if(aForm.getLawFirmAddress() == null || aForm.getLawFirmAddress().length() <= 0) {
                    errors.add("lawFirmAddress", new ActionMessage("error.mandatory"));
              }
            }   
        }
        
        if (!(errorCode = Validator.checkString(aForm.getLawFirmAddress(), false, 1, 200)).equals(Validator.ERROR_NONE)) {
				errors.add("lawFirmAddress", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
								"200"));
			}
        
        if("reject_checklist_item".equals(event))
        {
        	if(aForm.getRemarks()== null||aForm.getRemarks().length()==0)
        	{
        		errors.add("remarks", new ActionMessage("error.mandatory"));
        	}
        }
        DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

}
