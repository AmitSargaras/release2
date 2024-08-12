package com.integrosys.cms.ui.baselmaster;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.baselmaster.bus.IBaselDao;
import com.integrosys.cms.app.baselmaster.bus.IBaselJdbc;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.component.bus.IComponentDao;
import com.integrosys.cms.ui.newtatmaster.MaintainNewTatForm;

public class BaselValidator {
	
	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		MaintainBaselForm form=(MaintainBaselForm)commonform;
		//boolean a=validInput(form.getStartTime());
		
		IBaselDao baselDAO=(IBaselDao)BeanHouse.get("baselMasterDao");		
		
		if(null==form.getSystem()||form.getSystem().equals("")){
			errors.add("systemError", new ActionMessage("error.string.basel.mandatory"));			
		}
		/*if(form.getSystem()!=null){			
			int count=baselDAO.isUniqueCode(form.getSystem());
			if("maker_create_basel".equalsIgnoreCase(form.getEvent())){
				if(count!=0){
				errors.add("systemError", new ActionMessage("error.string.basel.system"));
				}
			}
				if("maker_edit_basel".equalsIgnoreCase(form.getEvent())||"maker_confirm_resubmit_delete".equalsIgnoreCase(form.getEvent())){
					
					if(count!=1 && count !=0){
					errors.add("systemError", new ActionMessage("error.string.basel.system"));
					}
				}
			}*/
		
		
		if(null==form.getSystemValue()||form.getSystemValue().equals("")){
			errors.add("systemValueError", new ActionMessage("error.string.basel.mandatory"));
			
		}
		if(form.getSystemValue()!=null){
			//boolean format=true;
			//if(form.getSystemValue().matches(".*[\\-\\(\\)]+.*")){
			//	format=true;
			//}
			if(form.getSystemValue().matches(".*[\\!\\@\\#\\$\\%\\^\\&\\|\\*\\'\\:\\;\\.\\,\\<\\>\\?\\/\\_\\[\\]\\{\\}\\+\\=]+.*")){
				errors.add("systemValueError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		if(null==form.getExposureSource()||form.getExposureSource().equals("")){
			errors.add("exposureError", new ActionMessage("error.string.basel.mandatory"));
			
		}
		if(form.getExposureSource()!=null){
			//boolean format2=true;
			//if(form.getExposureSource().matches(".*[\\-\\(\\)]+.*")){
			//	format2=true;
			//}
			if(form.getExposureSource().matches(".*[\\!\\@\\#\\$\\%\\^\\&\\|\\*\\'\\:\\;\\.\\,\\<\\>\\?\\/\\_\\[\\]\\{\\}\\+\\=]+.*")){
				errors.add("exposureError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		if(null==form.getBaselValidation()||form.getBaselValidation().equals("")){
			errors.add("baselError", new ActionMessage("error.string.basel.mandatory"));
			
		}
		if(null==form.getReportHandOff()||form.getReportHandOff().equals("")){
			errors.add("handOffError", new ActionMessage("error.string.basel.mandatory"));
			
		}
		
		
		
		return errors;
		
	}

}
