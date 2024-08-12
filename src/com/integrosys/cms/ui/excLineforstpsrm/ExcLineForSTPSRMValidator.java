package com.integrosys.cms.ui.excLineforstpsrm;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.excLineforstpsrm.bus.IExcLineForSTPSRMJdbc;

public class ExcLineForSTPSRMValidator {

	public static ActionErrors validateInput(ExcLineForSTPSRMForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();

		if(aForm.getLineCode() == null || aForm.getLineCode().length() == 0) {
			errors.add("lineCode",  new ActionMessage("error.string.mandatory"));
		}
		
		IExcLineForSTPSRMJdbc excLineForSTPSRMJdbc = (IExcLineForSTPSRMJdbc) BeanHouse.get("excLineForSTPSRMJdbc");
		boolean isDuplicate = excLineForSTPSRMJdbc.isExcluded(aForm.getLineCode(), false);
		
		if(isDuplicate) {
			errors.add("master",  new ActionMessage("error.exc.line.for.stp.srm.master.duplicate"));
		}
		
		
		return errors;
	}
	
}