package com.integrosys.cms.ui.discrepency;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;

public class DiscrepencyValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
	
		ActionErrors errors = new ActionErrors();
		DiscrepencyForm form = (DiscrepencyForm) commonform;
		String event=form.getEvent();
		
		if("WS_Add_Discrepency".equalsIgnoreCase(form.getEvent())){
			/*if(form.getCreditApprover()==null || form.getCreditApprover().trim().isEmpty()){
				errors.add("creditApprover", new ActionMessage("error.string.mandatory"));
			}*/
			if(form.getCpsDiscrepancyId()==null || form.getCpsDiscrepancyId().trim().isEmpty()){
				errors.add("cpsDiscrepancyIdError", new ActionMessage("error.string.mandatory"));
			}
			if(form.getOriginalTargetDate()==null || form.getOriginalTargetDate().trim().isEmpty()){
				errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}else{
				try{
					if(DateUtil.convertDate(locale, form.getOriginalTargetDate().trim())==null){
						errors.add("originalTargetDateError", new ActionMessage("error.date.format2"));
					}
				}catch (Exception e) {
				}
			}
		}
		
		
		if(form.getCreationDate()== null || form.getCreationDate().trim().equals("") ){
			errors.add("creationDateError", new ActionMessage("error.string.mandatory"));
			}else{
				if(DateUtil.convertDate(locale, form.getCreationDate().trim())!=null){
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate().trim())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("creationDateError", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
					}
				}else{
					errors.add("creationDateError", new ActionMessage("error.date.format2"));
				}
			}
		
		
		if(form.getOriginalTargetDate()== null || form.getOriginalTargetDate().trim().equals("") ){
			errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}/*else{
				if(form.getCreationDate()!= null && !form.getCreationDate().trim().equals("") ){
					if (form.getCreationDate().trim().length() > 0){
						if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate())).after(
							DateUtil.clearTime(DateUtil.convertDate(locale, form.getOriginalTargetDate())))) {
							errors.add("originalTargetDateError", new ActionMessage("error.date.compareDate.more", "Creation Date",
								" Original Target Date"));
						}
					}
					
					}
			}*/
		
		if(form.getNextDueDate()!= null && !form.getNextDueDate().trim().equals("") ){
		if (form.getNextDueDate().trim().length() > 0 && form.getNextDueDate().trim().length() > 0) {
			if(form.getOriginalTargetDate()!= null && !form.getOriginalTargetDate().trim().equals("") ){
			if (form.getOriginalTargetDate().trim().length() > 0){
				if(DateUtil.convertDate(locale, form.getOriginalTargetDate())!=null){
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getOriginalTargetDate())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, form.getNextDueDate())))) {
						errors.add("nextDueDateError", new ActionMessage("error.date.compareDate.more", "Original Target Date",
							"Next Due Date"));
					}
				}else{
					errors.add("originalTargetDateError", new ActionMessage("error.date.format2"));
				}
			}else
				errors.add("originalTargetDateError", new ActionMessage("error.string.originalTargetDate"));
		}}
		}
		
		
		if(event != null && (event.equals("maker_edit_discrepency_close")||event.equals("maker_submit_close_discrepancy"))){
			if(form.getRecDate()== null || form.getRecDate().trim().equals("") ){
				errors.add("recDateError", new ActionMessage("error.string.mandatory"));
			}else{
				if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getRecDate())).after(
						DateUtil.clearTime(DateUtil.getDate()))) {
					errors.add("recDateError", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
				}else if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getRecDate())))) {
					errors.add("recDateError", new ActionMessage("error.date.compareDate.more", "Creation Date",
						"Closed Date"));
				
					
				}else if(form.getAcceptedDate()!= null && !form.getAcceptedDate().trim().equals("") ){
					
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getAcceptedDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getRecDate())))) {
					errors.add("recDateError", new ActionMessage("error.date.compareDate.more", "Approved Date",
						"Closed Date"));
				}
			}else if(form.getDeferDate()!= null && !form.getDeferDate().trim().equals("") ){
					
					
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getDeferDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getRecDate())))) {
					errors.add("recDateError", new ActionMessage("error.date.compareDate.more", "Defer Date",
						"Closed Date"));
				
					
					}
				}
			}
			if(form.getDocRemarks() != null && !(form.getDocRemarks().trim().equals("")) ){
				if(form.getDocRemarks().trim().length() > 500 ) 
					errors.add("docRemarks", new ActionMessage("error.string.remarks","500"));
			}

		}
		else if(event != null && (event.equals("maker_edit_discrepency_waive")||event.equals("maker_submit_waive_discrepancy"))){
			if(form.getWaiveDate()== null || form.getWaiveDate().trim().equals("") ){
				errors.add("waiveDateError", new ActionMessage("error.string.mandatory"));
			}else{
				if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getWaiveDate())).after(
						DateUtil.clearTime(DateUtil.getDate()))) {
					errors.add("waiveDateError", new ActionMessage("error.date.compareDate.more", "Waived Date",
							"Current Date"));
				}else if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getWaiveDate())))) {
					errors.add("waiveDateError", new ActionMessage("error.date.compareDate.more", "Creation Date",
						"Waived Date"));
				
					
				}else if(form.getAcceptedDate()!= null && !form.getAcceptedDate().trim().equals("") ){
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getAcceptedDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getWaiveDate())))) {
					errors.add("waiveDateError", new ActionMessage("error.date.compareDate.more", "Approved Date",
						"Waived Date"));
				}
			}else if(form.getDeferDate()!= null && !form.getDeferDate().trim().equals("") ){
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getDeferDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getWaiveDate())))) {
					errors.add("waiveDateError", new ActionMessage("error.date.compareDate.more", "Defer Date",
						"Waived Date"));
				}
			}
			}
			if ((form.getCreditApprover() == null) || (form.getCreditApprover().length() <= 0)) {
				errors.add("creditApprover", new ActionMessage("error.date.mandatory"));
			}
			if(form.getDocRemarks() != null && !(form.getDocRemarks().trim().equals("")) ){
				if(form.getDocRemarks().trim().length() > 500 ) 
					errors.add("docRemarks", new ActionMessage("error.string.remarks","500"));
			}
		}else if(event != null && (event.equals("maker_edit_discrepency_defer")||event.equals("maker_submit_defer_discrepancy"))){
			if(form.getDeferDate()== null || form.getDeferDate().trim().equals("") ){
				errors.add("deferDateError", new ActionMessage("error.string.mandatory"));
			}else{
				if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getDeferDate())).after(
						DateUtil.clearTime(DateUtil.getDate()))) {
					errors.add("deferDateError", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
				}else if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getDeferDate())))) {
					errors.add("deferDateError", new ActionMessage("error.date.compareDate.more", "Creation Date",
						"Defer Date"));
				
					
				}else if(form.getAcceptedDate()!= null && !form.getAcceptedDate().trim().equals("") ){
					
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getAcceptedDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getDeferDate())))) {
					errors.add("deferDateError", new ActionMessage("error.date.compareDate.more", "Approved Date",
						"Defer Date"));
				
					
				}}
				
			}
			
			if(form.getNextDueDate()== null || form.getNextDueDate().trim().equals("") ){
				errors.add("nextDueDateError", new ActionMessage("error.string.mandatory"));
			}
			if ((form.getCreditApprover() == null) || (form.getCreditApprover().length() <= 0)) {
				errors.add("creditApprover", new ActionMessage("error.date.mandatory"));
			}
		}else
		{
			if("maker_create_discrepency_temp".equals(event)){
			if(form.getDiscrepencyType()!=null && form.getDiscrepencyType().equalsIgnoreCase("FACILITY")){
				if(form.getHiddenList()==null || form.getHiddenList().equals("")){
					errors.add("facilityError", new ActionMessage("error.discrepancy.facility.mandatory"));
				}
			}
			}
			
			if(form.getAcceptedDate()!=null){
			if (form.getAcceptedDate().trim().length() > 0) {
			if (form.getCreationDate().trim().length() > 0){			
				/*if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getCreationDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, form.getAcceptedDate())))) {
					errors.add("acceptedDateError", new ActionMessage("error.date.compareDate.more", "Creation Date",
						"Approved Date"));
				}else*/ 
				if(DateUtil.convertDate(locale, form.getAcceptedDate())!=null){
					if (DateUtil.clearTime(DateUtil.convertDate(locale, form.getAcceptedDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("acceptedDateError", new ActionMessage("error.date.compareDate.cannot.date", "Future date"));
					}
				}else{
					errors.add("acceptedDateError", new ActionMessage("error.date.format2"));
				}
			}
			
		}
		}
		
		
		if(form.getCritical()== null || form.getCritical().trim().equals("") )
			errors.add("criticalError", new ActionMessage("error.string.mandatory"));
		
		if(!("WS_Add_Discrepency".equalsIgnoreCase(form.getEvent()))){
			if(form.getApprovedBy() == null || form.getApprovedBy().trim().equals("")){
				errors.add("approvedByError", new ActionMessage("error.string.mandatory"));
			}
		}
		if(form.getDiscrepency() == null || form.getDiscrepency().trim().equals("") )
			errors.add("discrepencyError", new ActionMessage("error.string.mandatory"));
		
		if(form.getDiscrepencyRemark() != null && !(form.getDiscrepencyRemark().trim().equals("")) ){
			if(form.getDiscrepencyRemark().trim().length() > 500 ) 
				errors.add("discrepencyRemarkError", new ActionMessage("error.string.remarks","500"));
		}
		if(form.getDocRemarks() != null && !(form.getDocRemarks().trim().equals("")) ){
			if(form.getDocRemarks().trim().length() > 500 ) 
				errors.add("docRemarks", new ActionMessage("error.string.remarks","500"));
		}

		}
		if(form.getDiscrepencyRemark()==null||form.getDiscrepencyRemark().trim().equals("")){
			errors.add("discrepencyRemarkError", new ActionMessage("error.string.mandatory"));
		}
		return errors;
    }
}
