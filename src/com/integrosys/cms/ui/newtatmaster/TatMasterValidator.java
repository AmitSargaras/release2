package com.integrosys.cms.ui.newtatmaster;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;

public class TatMasterValidator {
	
	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		MaintainNewTatForm form=(MaintainNewTatForm)commonform;
		//boolean a=validInput(form.getStartTime());
		
		boolean startHour =false;
		boolean startMin =false;
		boolean startChar =false;
		if(form.getStartTime()==null||"".equals(form.getStartTime())){
			errors.add("startTimeError", new ActionMessage("error.string.mandatory"));
		}else if(form.getStartTime().length()<5){
			startChar=true;
			errors.add("startTimeError", new ActionMessage("error.string.tat.invalid.format"));
		}
		else if(form.getStartTime()!=null){
			startHour = ASSTValidator.isValidAlphaNumStringWithSpace(form.getStartTime().substring(0, 2));
			startMin = ASSTValidator.isValidAlphaNumStringWithSpace(form.getStartTime().substring(3,5));
			
			if( startHour == true||startMin == true){
				startChar=true;
				errors.add("startTimeError", new ActionMessage("error.string.invalidCharacter"));
			}else if(form.getStartTime().matches(".*[a-zA-Z]+.*")){
				startChar=true;
				errors.add("startTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!form.getStartTime().matches(".*[:]+.*")){
				startChar=true;
				errors.add("startTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!startChar &&validInput(form.getStartTime())){
				errors.add("startTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}
		}
		
		boolean endHour =false;
		boolean endMin =false;
		boolean endChar =false;
		if(form.getEndTime()==null||"".equals(form.getEndTime())){
			errors.add("endTimeError", new ActionMessage("error.string.mandatory"));
		}else if(form.getEndTime().length()<5){
			endChar=true;
			errors.add("endTimeError", new ActionMessage("error.string.tat.invalid.format"));
		}else if(form.getEndTime()!=null){
			endHour = ASSTValidator.isValidAlphaNumStringWithSpace(form.getEndTime().substring(0, 2));
			endMin = ASSTValidator.isValidAlphaNumStringWithSpace(form.getEndTime().substring(3,5));
			
			if( endHour == true||endMin == true){
				endChar=true;
				errors.add("endTimeError", new ActionMessage("error.string.invalidCharacter"));
			}else if(form.getEndTime().matches(".*[a-zA-Z]+.*")){
				endChar=true;
				errors.add("endTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!form.getEndTime().matches(".*[:]+.*")){
				endChar=true;
				errors.add("endTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!endChar &&validInput(form.getEndTime())){
				errors.add("endTimeError", new ActionMessage("error.string.tat.invalid.format"));
			}
		}
		
		boolean timeHour =false;
		boolean timeMin =false;
		boolean timeChar =false;
		if(form.getTiming()==null||"".equals(form.getTiming())){
			errors.add("timingError", new ActionMessage("error.string.mandatory"));
		}else if(form.getTiming().length()<5){
			timeChar=true;
			errors.add("timingError", new ActionMessage("error.string.tat.invalid.format"));
		}else if(form.getTiming()!=null){
			timeHour = ASSTValidator.isValidAlphaNumStringWithSpace(form.getTiming().substring(0, 2));
			timeMin = ASSTValidator.isValidAlphaNumStringWithSpace(form.getTiming().substring(3,5));
			
			if( timeHour == true||timeMin == true){
				timeChar=true;
				errors.add("timingError", new ActionMessage("error.string.invalidCharacter"));
			}else if(form.getTiming().matches(".*[a-zA-Z]+.*")){
				timeChar=true;
				errors.add("timingError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!form.getTiming().matches(".*[:]+.*")){
				timeChar=true;
				errors.add("timingError", new ActionMessage("error.string.tat.invalid.format"));
			}else if(!timeChar && validInput(form.getTiming())){
				errors.add("timingError", new ActionMessage("error.string.tat.invalid.format"));
			}
		}
		
		if(form.getStartTime()!=null && form.getEndTime()!=null){
			if(!startChar && !endChar){
			if(!form.getStartTime().equals("") && !form.getEndTime().equals("")){
			if(getDiff(form.getStartTime(),form.getEndTime())){
				errors.add("endTimeError", new ActionMessage("error.string.tat.end.greater"));
			}
			}
		}
		}
		
		
		
		return errors;
		
	}
	
	public static boolean validInput(String time){
		boolean valid=false;
		
		if(time.length()<5){
			valid=true;
		}
		if(!valid){
		String hour=time.substring(0, 2);
		String min=time.substring(3,5);
		
		if(!valid && Integer.parseInt(hour)>24){
			valid=true;
		}
		if(!valid &&(Integer.parseInt(hour)==24 && Integer.parseInt(min)>0)){
			valid=true;
		}
		if(!valid && Integer.parseInt(min)>59){
			valid=true;
		}
		}
		
		return valid;
		
	}
	
	public static boolean getDiff(String startTime,String endTime){
		boolean valid= false;
		
		String startHour=startTime.substring(0, 2);
		String endHour=endTime.substring(0, 2);
		String startMin=startTime.substring(3,5);
		String endMin=endTime.substring(3,5);
		if(Integer.parseInt(startHour)>Integer.parseInt(endHour)){
			valid=true;
		}
		if((Integer.parseInt(endHour)-Integer.parseInt(startHour))<=0){
		if(!valid &&(Integer.parseInt(startMin)>Integer.parseInt(endMin))){
			valid=true;
		}
		}
		return valid;
	}
	
	
}
