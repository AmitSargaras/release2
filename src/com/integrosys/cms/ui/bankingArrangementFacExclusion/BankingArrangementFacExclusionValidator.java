package com.integrosys.cms.ui.bankingArrangementFacExclusion;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.bankingArrangementFacExclusion.bus.IBankingArrangementFacExclusionJdbc;

public class BankingArrangementFacExclusionValidator {

	public static ActionErrors validateInput(BankingArrangementFacExclusionForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();

		if(aForm.getSystem() == null || aForm.getSystem().length() == 0) {
			errors.add("system",  new ActionMessage("error.string.mandatory"));
		}
		
		IBankingArrangementFacExclusionJdbc exclusionJdbc = (IBankingArrangementFacExclusionJdbc) BeanHouse.get("bankingArrangementFacExclusionJdbc");
		boolean isDuplicate = exclusionJdbc.isExcluded(aForm.getSystem(),
				aForm.getFacCategory(), aForm.getFacName(), false);
		
		if(isDuplicate) {
			errors.add("master",  new ActionMessage("error.banking.arr.exc.master.duplicate"));
		}
		
		return errors;
	}
	
}