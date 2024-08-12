package com.integrosys.cms.ui.udf;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UDFConstants;

public class UdfValidator {
	public static ActionErrors validateInput(ActionForm commonform,Locale locale) {
		ActionErrors errors = new ActionErrors();
		UdfForm form = (UdfForm)commonform;
	System.out.println("udfform.getOperationName:"+form.getOperationName());
		String event = form.getEvent();
		if (form.getSequence() == null || form.getSequence().trim().equals("") ) {
			errors.add("sequenceError", new ActionMessage("error.sequence.mandatory"));
		}
		else {
			System.out.println("UDF getModuleId:: "+form.getModuleId());
			if("3".equalsIgnoreCase(form.getModuleId()) ) {
				
				if (null == form.getSequence()  || !Validator.ERROR_NONE.equals(Validator.checkInteger(form.getSequence(), true, 0, 115))) {
					errors.add("sequenceError", new ActionMessage("error.sequence.invalid_limitmodule"));
				}
				else if (Integer.parseInt(form.getSequence()) > UDFConstants.UDF_MAXLIMIT_LIMITMODULE) {
					errors.add("sequenceError", new ActionMessage("error.sequence.invalid_limitmodule"));
				}else if (Integer.parseInt(form.getSequence()) < 1) {
					errors.add("sequenceError", new ActionMessage("error.sequence.invalid_limitmodule"));
				}
			}
			else {
			if (form.getSequence() == null || !Validator.ERROR_NONE.equals(Validator.checkInteger(form.getSequence(), true, 0, 100))) {
				errors.add("sequenceError", new ActionMessage("error.sequence.invalid"));
			}
			else if (Integer.parseInt(form.getSequence()) > UDFConstants.UDF_MAXLIMIT) {
				errors.add("sequenceError", new ActionMessage("error.sequence.invalid"));
			}else if (Integer.parseInt(form.getSequence()) < 1) {
				errors.add("sequenceError", new ActionMessage("error.sequence.invalid"));
			}
			}
		}
			
		if(!("maker_edit_udf".equals(event) || "checker_approve_edit".equals(event)  || "maker_confirm_resubmit_delete".equals(event))) {
		if (form.getFieldName() == null || form.getFieldName().trim().equals("") ) {
			errors.add("fieldNameError", new ActionMessage("error.fieldName.mandatory"));
		}else{
			IUdfDao udfDao=(IUdfDao) BeanHouse.get("udfDao");
			boolean isNameValid=false;
			isNameValid=udfDao.findUdfByName(form.getFieldName().trim().toUpperCase(),form.getModuleId());
			 if (isNameValid) {
					errors.add("fieldNameError", new ActionMessage("error.fieldName.duplicate"));
				}
		}
		}
		
		if ((form.getFieldTypeId().equals(Long.toString(UDFConstants.FIELDTYPEID_SELECT))
				|| form.getFieldTypeId().equals(Long.toString(UDFConstants.FIELDTYPEID_CHECKBOX))
				|| form.getFieldTypeId().equals(Long.toString(UDFConstants.FIELDTYPEID_RADIOBUTTON)))
				&& (form.getOptions() == null || form.getOptions().trim().equals(""))) {
			errors.add("optionsError", new ActionMessage("error.options.mandatory"));
		}
		
		if ((form.getFieldTypeId().equals(Long.toString(UDFConstants.FIELDTYPEID_NUMERIC_TEXT))||form.getFieldTypeId().equals(Long.toString(UDFConstants.FIELDTYPEID_TEXT)))
				){
			if(form.getNumericLength() == null || form.getNumericLength().trim().equals("")) {
			errors.add("numericLengthError", new ActionMessage("error.number.format"));
			}else {
				if (form.getNumericLength() == null || !Validator.ERROR_NONE.equals(Validator.checkNumber(form.getNumericLength(), true, 0, 9999999999999999.D))) {
					errors.add("numericLengthError", new ActionMessage("error.sequence.invalid"));
				}
				
			}
			
		}
		return errors;
	}
}
