package com.integrosys.cms.ui.cersaiMapping;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.cersaiMapping.bus.CersaiMappingJdbcImpl;
import com.integrosys.cms.app.cersaiMapping.bus.ICersaiMapping;
import com.integrosys.cms.app.cersaiMapping.bus.OBCersaiMapping;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class CersaiMappingValidator {

	public static ActionErrors validateInput(ActionForm commonform, Locale locale) {
		ActionErrors errors = new ActionErrors();
		CersaiMappingForm form = (CersaiMappingForm) commonform;
		String errorCode = null;
		ICersaiMapping ic = new OBCersaiMapping();
		CersaiMappingJdbcImpl cersaiJdbc = new CersaiMappingJdbcImpl();
		String masterName = "";
		String[] updatedCersaiValue1 = form.getUpdatedCersaiValue();
		String[] updatedClimsValue = form.getUpdatedClimsValue();
		String[] climsValues = form.getClimsValues();
		String[] m1 = form.getMasterValueList();

		
		if (updatedClimsValue == null) {
			updatedClimsValue = form.getClimsValues();
		}

		if (updatedCersaiValue1 != null) {
			ICersaiMapping[] feedEntriesArr1 = new ICersaiMapping[updatedCersaiValue1.length];

			for (int i = 0; i < updatedCersaiValue1.length; i++) {
				feedEntriesArr1[i] = new OBCersaiMapping();
				feedEntriesArr1[i].setCersaiValue(updatedCersaiValue1[i]);
				if (updatedClimsValue == null || updatedClimsValue.length <= 0) {
					feedEntriesArr1[i].setClimsValue(climsValues[i]);
				} else {
					feedEntriesArr1[i].setClimsValue(updatedClimsValue[i]);
				}
			}

			String event = form.getEvent();
			if (form.getEvent().equalsIgnoreCase("maker_create_cersai_mapping")
					|| form.getEvent().equalsIgnoreCase("maker_edit_cersai_mapping")
					|| form.getEvent().equalsIgnoreCase("maker_save_update")
					|| form.getEvent().equalsIgnoreCase("maker_prepare_create_cersai_mapping")
					|| form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")
					|| form.getEvent().equalsIgnoreCase("submit")) {

				if (form.getUpdatedCersaiValue().length > 0) {
					String[] updatedCersaiValue = form.getUpdatedCersaiValue();
					for (int k = 0; k < updatedCersaiValue.length; k++) {
						boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(updatedCersaiValue[k]);
						if (codeFlag == true) {
							errors.add("cersaiValueError", new ActionMessage("error.string.invalidCharacter"));
							break;
						}
					}
				}

				/*if (form.getUpdatedCersaiValue().length > 0) {
					try {
						if (updatedClimsValue != null) {
							if (updatedClimsValue != null) {
								masterName = cersaiJdbc.getNameOfMaster(updatedClimsValue[0]);
							}
						}
						if (!(form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete"))) {
							for (int j = 0; j < updatedCersaiValue1.length; j++) {
								String clim1 = updatedClimsValue[j];
								String cersai1 = updatedCersaiValue1[j];

								String cersaivalue = cersaiJdbc.getCersaiValForValidationIssueCheck(clim1, masterName);
								if (cersai1.equals(cersaivalue)) {
									errors.add("cersaiValueSameError", new ActionMessage("error.string.value.similar"));
									break;
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
*/
				if (form.getUpdatedCersaiValue().length > 0) {
					try {
						if (updatedClimsValue != null) {
							masterName = cersaiJdbc.getNameOfMaster(updatedClimsValue[0]);
						}

						for (int j = 0; j < updatedCersaiValue1.length; j++) {
							String clim1 = updatedClimsValue[j];
							String cersai1 = updatedCersaiValue1[j];

							String climvalue = cersaiJdbc.getClimsValForValidationIssueCheck(clim1,masterName);
							if (cersai1.equals(climvalue)) {
								errors.add("cersaiValueSameBothValueError",
										new ActionMessage("error.string.value.bothsimilar"));
								break;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		}else {
			if(updatedCersaiValue1 == null && form.getEvent().equalsIgnoreCase("submit")) {
				errors.add("updateatleastonevalue", new ActionMessage("error.string.value.updateatleastonevalue"));
			}
		}
		
		return errors;
	}
}
