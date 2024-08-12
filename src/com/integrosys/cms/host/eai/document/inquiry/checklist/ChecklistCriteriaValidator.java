package com.integrosys.cms.host.eai.document.inquiry.checklist;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.document.DocumentConstantsCla;
import com.integrosys.cms.host.eai.document.IncorrectDocumentMessageInfoException;
import com.integrosys.cms.host.eai.document.bus.CCChecklist;
import com.integrosys.cms.host.eai.document.bus.SCChecklist;

/**
 * EAI Message Validator for checklistCriteria
 * 
 * @author Iwan Satria
 * @author Chong Jun Yong
 */
public class ChecklistCriteriaValidator implements IEaiMessageValidator {

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		ChecklistCriteria checkListCriteria = EAIDocChecklistInquiryHelper.getInstance().retrieveCheckListCriteria(
				scimessage);
		CCChecklist cc = checkListCriteria.getCCChecklist();
		SCChecklist sc = checkListCriteria.getSCChecklist();
		EaiValidationHelper validator = EaiValidationHelper.getInstance();

		if (cc == null && sc == null) {
			throw new MandatoryFieldMissingException("CCChecklist or SCChecklist");
		}

		if (cc != null && sc != null) {
			throw new IncorrectDocumentMessageInfoException(scimessage.getMsgHeader());
		}

		validator.validateMandatoryFieldForString("LOSAANumber", checkListCriteria.getLOSAANumber());
		validator.validateMandatoryFieldForString("ChecklistType", checkListCriteria.getChecklistType());

		if (!ArrayUtils.contains(DocumentConstantsCla.getChecklistTypes(), checkListCriteria.getChecklistType())) {
			throw new FieldValueNotAllowedException("ChecklistType", checkListCriteria.getChecklistType(),
					DocumentConstantsCla.getChecklistTypes());
		}

		if (cc != null) {
			validator.validateMandatoryFieldForString("CustomerType", cc.getCustomerType());
			validator.validateMandatoryFieldForString("CIFNo", cc.getCIFNo());

			if (!ArrayUtils.contains(DocumentConstantsCla.getChecklistCategories(), cc.getCustomerType())) {
				throw new FieldValueNotAllowedException("CustomerType", cc.getCustomerType(),
						DocumentConstantsCla.getChecklistCategories());
			}
		}

		if (sc != null) {
			validator.validateMandatoryFieldForLong("CMSCollateralID", sc.getCmsCollateralID());
		}
	}
}
