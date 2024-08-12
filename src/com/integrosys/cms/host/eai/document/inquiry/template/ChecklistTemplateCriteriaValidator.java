package com.integrosys.cms.host.eai.document.inquiry.template;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.document.bus.CCTemplate;
import com.integrosys.cms.host.eai.document.bus.SCTemplate;

/**
 * EAI Message Validator for checklist
 * 
 * @author shphoon
 * @author Chong Jun Yong
 * @since 1.1
 */
public class ChecklistTemplateCriteriaValidator implements IEaiMessageValidator {

	private final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	public ChecklistTemplateCriteriaValidator() {
	}

	public void validate(EAIMessage scimessage) throws EAIMessageValidationException {
		ChecklistTemplateCriteria checkListTemplateCriteria = EAICheckListTemplateInquiryHelper.getInstance()
				.retrieveCheckListTemplateCriteria(scimessage);

		String source = scimessage.getMsgHeader().getSource();

		validator.validateMandatoryFieldForString("ChecklistType", checkListTemplateCriteria.getChecklistType());

		validator.validateMandatoryFieldForString("Country", checkListTemplateCriteria.getCountry());

		if (checkListTemplateCriteria.getChecklistType().equals("CC")) {
			if (checkListTemplateCriteria.getCCTemplate() != null) {
				CCTemplate ccTemplate = checkListTemplateCriteria.getCCTemplate();
				validator.validateString(ccTemplate.getCustomerType(), "CustomerType", true, 1, 1, new String[] { "B",
						"P" });
				validator.validateStdCode(ccTemplate.getCustomerClass(), source, "56");
				validator.validateString(ccTemplate.getApplicableLaw(), "ApplicableLaw", true, 3, 3, new String[] {
						"ISL", "CON" });
				validator.validateStdCode(ccTemplate.getApplicationType(), source, "AA_TYPE");
			}
			else {
				throw new MandatoryFieldMissingException("ChecklistTemplateCriteria - CCTemplate");
			}
		}
		else if (checkListTemplateCriteria.getChecklistType().equals("SC")) {
			if (checkListTemplateCriteria.getSCTemplate() != null) {
				SCTemplate scTemplate = checkListTemplateCriteria.getSCTemplate();
				validator.validateStdCode(scTemplate.getSecurityType(), source, "31");
				validator.validateStdCode(scTemplate.getSecuritySubType(), source, "54");
				validator.validateStdCode(scTemplate.getApplicationType(), source, "AA_TYPE");
				if (ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH.equals(scTemplate.getSecuritySubType())) {
					validator.validateStdCode(scTemplate.getGoodsStatus(), source, "GOODS_STATUS");
					validator.validateStdCode(scTemplate.getPBTPBRInd(), source, "PBR_PBT_IND");
				}
			}
			else {
				throw new MandatoryFieldMissingException("ChecklistTemplateCriteria - SCTemplate");
			}
		}

	}
}
