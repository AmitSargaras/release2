package com.integrosys.cms.ui.checklist.facilityreceipt;

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

import com.integrosys.cms.ui.checklist.secreceipt.SecurityReceiptFormValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class FacilityReceiptFormValidator {
	public static ActionErrors validateInput(FacilityReceiptForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();

		Date d = DateUtil.getDate();
		d = DateUtil.clearTime(d);
	
		if("save_receive".equals(event)||"save_waiver".equals(event)||"save_defer".equals(event)||"save_update".equals(event)){	
		if (!(errorCode =Validator.checkAmount(UIUtil.removeComma(aForm.getDocAmt()), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)){
			errors.add("docAmt", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			
			}
		
		String hdfcAmt = UIUtil.removeComma(aForm.getHdfcAmt());
	//	if(!aForm.getHdfcAmt().equals("")&& null!=aForm.getHdfcAmt()){
		
		if(!hdfcAmt.equals("")&& null!=hdfcAmt){
		if (!(errorCode =Validator.checkAmount(hdfcAmt, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)){
			errors.add("hdfcAmt", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
				
			}/*else if (UIUtil.mapStringToBigDecimal(aForm.getHdfcAmt()).compareTo(UIUtil.mapStringToBigDecimal(aForm.getActionParty()))==1){
				errors.add("hdfcAmt", new ActionMessage("error.amount.not.greaterthan"," HDFC Amount", aForm.getActionParty()));
			}*/
		}
			if("save_update".equals(event)){
				if ((aForm.getOriginalTargetDate() == null) || (aForm.getOriginalTargetDate().length() <= 0)) {
					errors.add("originalTargetDate", new ActionMessage("error.date.mandatory"));
				}
			}
			// Obervation from HDFC Bank 19-Sep-2013 By Abhijit R
		/*if ((aForm.getDocDate() == null) || (aForm.getDocDate().length() <= 0)) {
			errors.add("docDate", new ActionMessage("error.date.mandatory"));
		}	*/	
		
		if((aForm.getDocDate() != null) && (aForm.getDocDate().length() > 0)){
			if(d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocDate())))) {
				errors.add("docDate", new ActionMessage("error.date.future"));
			}
		}
		
		
			
			if((aForm.getDocExpDate() != null) && (aForm.getDocExpDate().length() > 0)){
				if(d.after(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDocExpDate())))) {
					errors.add("docExpDate", new ActionMessage("error.date.past"));
				}
			}
			
		}
		
		if("save_receive".equals(event)){
			
				
				// Obervation from HDFC Bank 19-Sep-2013 By Abhijit R
				/*if ((aForm.getOriginalTargetDate() == null) || (aForm.getOriginalTargetDate().length() <= 0)) {
					errors.add("originalTargetDate", new ActionMessage("error.date.mandatory"));
				}*/
				if ((aForm.getReceivedDate() == null) || (aForm.getReceivedDate().length() <= 0)) {
					errors.add("receivedDate", new ActionMessage("error.date.mandatory"));
				}		
				
				if((aForm.getReceivedDate() != null) && (aForm.getReceivedDate().length() > 0)){
					if(d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())))) {
						errors.add("receivedDate", new ActionMessage("error.date.future"));
					}
					
					if((aForm.getDeferDate() != null) &&  (aForm.getDeferDate().length() > 0) ){
						if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate()))))){
							errors.add("receivedDate", new ActionMessage("error.date.compareDate"," Received Date", " Defer Date"));
						}
						
					}
				}
				
				
				if ((aForm.getDocDate() == null) || (aForm.getDocDate().length() <= 0)) {
					errors.add("docDate", new ActionMessage("error.date.mandatory"));
				}
				
//				if(aForm.getFacImageTagUntagId() == null || "".equals(aForm.getFacImageTagUntagId()) || aForm.getFacImageTagUntagImgName() == null || "".equals(aForm.getFacImageTagUntagImgName())
//						|| aForm.getFacImageTagUntagStatus() == null || "".equals(aForm.getFacImageTagUntagStatus())) {
//					errors.add("imagetaguntag", new ActionMessage("error.string.mandatory"));
//				}
				
//				if(aForm.getFacImageTagUntagImgName() == null || "".equals(aForm.getFacImageTagUntagImgName())) {
//					errors.add("imagetaguntag", new ActionMessage("error.string.mandatory"));
//				}
					
			}
		
		if("save_waiver".equals(event)){
			
			if((aForm.getCreditApprover().trim()== null) || (aForm.getCreditApprover().trim().equals(""))
					||(aForm.getCreditApprover().trim().length()<=0)){
				errors.add("creditApprover", new ActionMessage("error.date.mandatory"));
			}
			
			if(!aForm.getItemStatus().equals("DEFERRED")){
			
				/*if ((aForm.getOriginalTargetDate() == null) || (aForm.getOriginalTargetDate().length() <= 0)) {
					errors.add("originalTargetDate", new ActionMessage("error.date.mandatory"));
				}*/
			
			if ((aForm.getWaivedDate() == null) || (aForm.getWaivedDate().length() <= 0)) {
				errors.add("waivedDate", new ActionMessage("error.date.mandatory"));
			}		
			
			if((aForm.getWaivedDate() != null) && (aForm.getWaivedDate().length() > 0)){
				if(d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getWaivedDate())))) {
					errors.add("waivedDate", new ActionMessage("error.date.future"));
				}
			}
			
			
			}
		
			if(aForm.getItemStatus().equals("DEFERRED")){
				if ((aForm.getWaivedDate() == null) || (aForm.getWaivedDate().length() <= 0)) {
					errors.add("waivedDate", new ActionMessage("error.date.mandatory"));
				}else 
					if(d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getWaivedDate())))) {
						errors.add("waivedDate", new ActionMessage("error.date.future"));
					
				}else if((aForm.getDeferDate().length() > 0) && (aForm.getWaivedDate().length() > 0)){
					if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getWaivedDate()))))){
						errors.add("waivedDate", new ActionMessage("error.date.compareDate"," Waived Date", " Defer Date"));
					}
					
				}
				
			}
				
			}
		
		
		if("save_defer".equals(event)){
			
			if ((aForm.getOriginalTargetDate() == null) || (aForm.getOriginalTargetDate().length() <= 0)) {
				errors.add("originalTargetDate", new ActionMessage("error.date.mandatory"));
			}
			
			if((aForm.getCreditApprover().trim()== null) || (aForm.getCreditApprover().trim().equals(""))
					||(aForm.getCreditApprover().trim().length()<=0)){
				errors.add("creditApprover", new ActionMessage("error.date.mandatory"));
			}
			
			if ((aForm.getDeferDate() == null) || (aForm.getDeferDate().length() <= 0)) {
				errors.add("deferDate", new ActionMessage("error.date.mandatory"));
			}
			
			if((aForm.getDeferDate() != null) && (aForm.getDeferDate().length() > 0)){
				if(d.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate())))) {
					errors.add("deferDate", new ActionMessage("error.date.future"));
				}
			}
			
		
			
			if((aForm.getDeferDate().length() > 0) && (aForm.getOriginalTargetDate().length() > 0)){
				if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).
						equals(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate())))){
					
					errors.add("deferDate", new ActionMessage("error.date.compareDate.same"," Defer Date", " Original Target Date"));
				}
			}
			
			if(aForm.getDeferCount().equals("1")){
				if ((aForm.getExpectedReturnDate() == null) || (aForm.getExpectedReturnDate().length() <= 0)) {
					errors.add("expectedReturnDate", new ActionMessage("error.date.mandatory"));
				}
				
				if((aForm.getDeferDate().length() > 0) && (aForm.getOriginalTargetDate().length() > 0)&&(aForm.getExpectedReturnDate().length() > 0)){
					if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))){
						errors.add("expectedReturnDate", new ActionMessage("error.date.compareDate"," Next Due Date", " Defer Date"));
					}
					else if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))){
						errors.add("expectedReturnDate", new ActionMessage("error.date.compareDate"," Next Due Date", " Original Target Date"));
					}
					else if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))
							&& (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))){
						errors.add("expectedReturnDate", new ActionMessage("error.date.compareDate.more1"," Next Due Date", " Original Target Date","Defer Date "));
					}
				}
			}
			
			if(!aForm.getDeferCount().equals("1")){

				if ((aForm.getDeferExtendedDate() == null) || (aForm.getDeferExtendedDate().length() <= 0)) {
					errors.add("deferExtendedDate", new ActionMessage("error.date.mandatory"));
				}else{
						if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate())).before(
								DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpectedReturnDate()))))){
							errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate", "Next Due Date",
								"Last Due Date"));
						}
					}
				
				if((aForm.getDeferDate().length() > 0) && (aForm.getOriginalTargetDate().length() > 0)&&(aForm.getDeferExtendedDate().length() > 0)){
					if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate()))))){
						errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate"," Next Due Date", " Defer Date"));
					}
					else if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate()))))){
						errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate"," Next Due Date", " Original Target Date"));
					}
					else if((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate()))))
							&& (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()))).after((DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDeferExtendedDate()))))){
						errors.add("deferExtendedDate", new ActionMessage("error.date.compareDate.more1"," Next Due Date", " Original Target Date","Defer Date "));
					}
				}
				
			}
			if(errors.size()>0){
				int count1=0;
				String count=aForm.getDeferCount();
				count1=Integer.parseInt(count);
				if(!aForm.getDocumentStatus().equals("PENDING_DEFER")){
				count1=count1-1;
				}
				count=String.valueOf(count1);
				aForm.setDeferCount(count);
				aForm.setItemStatus(aForm.getDocumentStatus());
			}
		}
		
		if (!("save_perm_uplift".equals(event) || "save_temp_uplift".equals(event) || "save_relodge".equals(event)
				|| "save_delete".equals(event) || FacilityReceiptAction.EVENT_SUBMIT.equals(event) || "submiting".equals(event) || "save"
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
		
		if(FacilityReceiptAction.EVENT_SUBMIT.equals(event) || "submiting".equals(event)) {
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
	        
	        if("save_discrepancy".equals(event)){	
	    		if (!(errorCode =Validator.checkAmount(UIUtil.removeComma(aForm.getDocAmt()), false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
	    				.equals(Validator.ERROR_NONE)){
	    			errors.add("docAmt", new ActionMessage(
	    					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
	    			
	    			}
	    		
	    		String hdfcAmt = UIUtil.removeComma(aForm.getHdfcAmt());
	    
	    		
	    		if(!hdfcAmt.equals("")&& null!=hdfcAmt){
	    		if (!(errorCode =Validator.checkAmount(hdfcAmt, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
	    				.equals(Validator.ERROR_NONE)){
	    			errors.add("hdfcAmt", new ActionMessage(
	    					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
	    				
	    			}
	    		}
	        }
	        
		return errors;
	}

}
