package com.integrosys.cms.ui.imageTag;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;


/**
 * ImageTagValidator class to validate ImageTag Search for corresponding Customer
 * event...
 * @author abhijit.rudrakshawar
 */
public class ImageTagValidator {

	public static ActionErrors validateInput(ActionForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {
			CommonForm cForm=(CommonForm) form;
			if ("list_image".equals(cForm.getEvent())||ImageTagAction.EVENT_UNTAG_CUSTOMER_SEARCH_LIST.equals(cForm.getEvent())) {
				return validateImageTagCusSearchform((ImageTagForm) cForm, errors);
			}
			if (ImageTagAction.EVENT_SAVE_TAG_PAGE.equals(cForm.getEvent())||ImageTagAction.EVENT_PREPARE_UNTAG_IMAGE_LIST.equals(cForm.getEvent())) {
				return validateImageTagForm((ImageTagMapForm) cForm, errors);
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			//TODO--- log
		}
		return errors;

	}

	private static ActionErrors validateImageTagForm(ImageTagMapForm form, ActionErrors errors) {
		String errorCode = "";
		
		DefaultLogger.debug("ImageTagValidator ", "Inside validateImageTagForm");
		
		if (!(errorCode = Validator.checkString(form.getDocType(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("docTypeError",  new ActionMessage("label.please.select.option"));
		}else if(IImageTagConstants.CAM_DOC.equals(form.getDocType())
				||IImageTagConstants.RECURRENTDOC_DOC.equals(form.getDocType())
				||IImageTagConstants.OTHER_DOC.equals(form.getDocType())||IImageTagConstants.LAD_DOC.equals(form.getDocType()))
		 	{
			if (!(errorCode = Validator.checkString(form.getDocDesc(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("docDescError",  new ActionMessage("label.please.select.option"));
			}	
		}else if(IImageTagConstants.FACILITY_DOC.equals(form.getDocType())){
			if (!(errorCode = Validator.checkString(form.getFacilityId(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("facilityIdError",  new ActionMessage("label.please.select.option"));
			}	
			if (!(errorCode = Validator.checkString(form.getDocDesc(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("docDescError",  new ActionMessage("label.please.select.option"));
			}	
		}else if(IImageTagConstants.SECURITY_DOC.equals(form.getDocType())){
			if (!(errorCode = Validator.checkString(form.getSecType(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("secTypeError",  new ActionMessage("label.please.select.option"));
			}	
			if (!(errorCode = Validator.checkString(form.getSecSubtype(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("secSubtypeError",  new ActionMessage("label.please.select.option"));
			}	
			if (!(errorCode = Validator.checkString(form.getSecurityId(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("securityIdError",  new ActionMessage("label.please.select.option"));
			}
			if (!(errorCode = Validator.checkString(form.getDocDesc(), true, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("docDescError",  new ActionMessage("label.please.select.option"));
			}
		}
		
		return errors;
	}

	private static ActionErrors validateImageTagCusSearchform(ImageTagForm form, ActionErrors errors) {
		String errorCode = "";

//		DefaultLogger.debug("ImageTagCusSearch ", " - Code: " + form.getCustomerName());
		if (form.getGobutton().equals("1")) {
		if (form.getCustomerName().equals("")) {
			if (!(errorCode = Validator.checkString(form.getCustomerName(), true, 3, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("customerNameError",  new ActionMessage("error.customerName.mandatory"));
			}
		}
		}else if (form.getGobutton().equals("2")) {
			if (!(errorCode = Validator.checkString(form.getLegalID(), true, 3, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("legalIDError",  new ActionMessage("error.legalID.mandatory"));
			}
		}
		return errors;
	}
}