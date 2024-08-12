package com.integrosys.cms.host.eai.security.validator;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.document.DocumentSecurity;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Message validator to validate instance of <tt>DocumentSecurity</tt>
 * @author Chong Jun Yong
 * 
 */
public class DocumentSecurityValidator extends SecurityValidator {

	private String[] securitySubtypeIdsIsdaIfemaDocuments;

	private String[] securitySubtypeIdsDeedOfAssignmentDocuments;

	private String[] securitySubtypeIdsLeaseAgreementDocuments;

	private String[] securitySubtypeIdsDocumentAmountApplicable;

	private String[] securitySubtypeIdsLetterOfUndertakingFlagApplicable;

	private String[] securitySubtypeIdsBlanketAssignmentApplicable;

	public void setSecuritySubtypeIdsIsdaIfemaDocuments(String[] securitySubtypeIdsIsdaIfemaDocuments) {
		this.securitySubtypeIdsIsdaIfemaDocuments = securitySubtypeIdsIsdaIfemaDocuments;
	}

	public void setSecuritySubtypeIdsDeedOfAssignmentDocuments(String[] securitySubtypeIdsDeedOfAssignmentDocuments) {
		this.securitySubtypeIdsDeedOfAssignmentDocuments = securitySubtypeIdsDeedOfAssignmentDocuments;
	}

	public void setSecuritySubtypeIdsLeaseAgreementDocuments(String[] securitySubtypeIdsLeaseAgreementDocuments) {
		this.securitySubtypeIdsLeaseAgreementDocuments = securitySubtypeIdsLeaseAgreementDocuments;
	}

	public void setSecuritySubtypeIdsDocumentAmountApplicable(String[] securitySubtypeIdsDocumentAmountApplicable) {
		this.securitySubtypeIdsDocumentAmountApplicable = securitySubtypeIdsDocumentAmountApplicable;
	}

	public void setSecuritySubtypeIdsLetterOfUndertakingFlagApplicable(
			String[] securitySubtypeIdsLetterOfUndertakingFlagApplicable) {
		this.securitySubtypeIdsLetterOfUndertakingFlagApplicable = securitySubtypeIdsLetterOfUndertakingFlagApplicable;
	}

	public void setSecuritySubtypeIdsBlanketAssignmentApplicable(String[] securitySubtypeIdsBlanketAssignmentApplicable) {
		this.securitySubtypeIdsBlanketAssignmentApplicable = securitySubtypeIdsBlanketAssignmentApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_DOCUMENT.equals(security.getSecurityType().getStandardCodeValue())) {
			DocumentSecurity documentDetail = msg.getDocumentationDetail();

			validator.rejectIfNull(documentDetail, "DocumentationDetail");

			validator.validateString(documentDetail.getLOSSecurityId(), "DocumentationDetail - LOSSecurityId", true, 1,
					20);

			validator.validateString(documentDetail.getIssuer(), "DocumentationDetail - Issuer", false, 0, 50);

			if (ArrayUtils.contains(this.securitySubtypeIdsIsdaIfemaDocuments, secSubType)) {
				validator.validateDate(documentDetail.getISDADate(), "DocumentationDetail - ISDADate", true);

				validator.validateStdCodeAllowNull(documentDetail.getISDAProductDescription(), source,
						CategoryCodeConstant.ISDA_PRODUCT);

				validator.validateDate(documentDetail.getIFEMADate(), "DocumentationDetail - IFEMADate", true);

				validator.validateStdCodeAllowNull(documentDetail.getIFEMAProductDescription(), source,
						CategoryCodeConstant.IFEMA_PRODUCT);

				validator.validateDate(documentDetail.getICOMDate(), "DocumentationDetail - ICOMDate", true);

				validator.validateStdCodeAllowNull(documentDetail.getICOMProductDescription(), source,
						CategoryCodeConstant.ICOM_PRODUCT);
			}

			validator.validateString(documentDetail.getDocumentReferenceNo(),
					"DocumentationDetail - DocumentReferenceNo", false, 0, 100);

			if (ArrayUtils.contains(this.securitySubtypeIdsDocumentAmountApplicable, secSubType)) {
				validator.validateDoubleDigit(documentDetail.getDocumentAmount(),
						"DocumentationDetail - DocumentAmount", false, 15, 0, false);
			}

			validator.validateDoubleDigit(documentDetail.getMinimumAmt(), "DocumentationDetail - MinimumAmt", false,
					15, 0, false);

			validator.validateDoubleDigit(documentDetail.getMaximumAmt(), "DocumentationDetail - MaximumAmt", false,
					15, 0, false);

			validator.validateString(documentDetail.getDocumentDescription(),
					"DocumentationDetail - DocumentDescription", false, 0, 250);

			if (ArrayUtils.contains(this.securitySubtypeIdsDeedOfAssignmentDocuments, secSubType)) {
				validator.validateString(documentDetail.getProjectName(), "DocumentationDetail - ProjectName", false,
						0, 250);

				validator.validateString(documentDetail.getLetterOfInstructionFlag(),
						"DocumentationDetail - LetterOfInstructionFlag", false, 0, 1,
						EaiConstantCla.getAllowedValuesYesNo());

				validator.validateStdCodeAllowNull(documentDetail.getDeedAssignmentType(), source,
						CategoryCodeConstant.Type_Of_Assignment);

				validator.validateString(documentDetail.getContractNumber(), "DocumentationDetail - ContractNumber",
						false, 0, 20);

				validator.validateString(documentDetail.getContractName(), "DocumentationDetail - ContractName", false,
						0, 40);

				validator.validateDoubleDigit(documentDetail.getContractAmount(),
						"DocumentationDetail - ContractAmount", false, 13, 2, false);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsLetterOfUndertakingFlagApplicable, secSubType)) {
				validator.validateString(documentDetail.getLetterOfUndertakingFlag(),
						"DocumentationDetail - LetterOfUndertakingFlag", false, 0, 1,
						EaiConstantCla.getAllowedValuesYesNo());
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsBlanketAssignmentApplicable, secSubType)) {
				validator.validateString(documentDetail.getBlanketAssignment(),
						"DocumentationDetail - BlanketAssignment", false, 0, 80);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsLeaseAgreementDocuments, secSubType)) {
				validator.validateStdCodeAllowNull(documentDetail.getLeaseType(), source,
						CategoryCodeConstant.LEASE_TYPE);

				validator.validateString(documentDetail.getLeaseRentalAgreement(),
						"DocumentationDetail - LeaseRentalAgreement", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

				validator.validateString(documentDetail.getLeaseLimitation(), "DocumentationDetail - LeaseLimitation",
						false, 0, 50);

				validator.validateStdCodeAllowNull(documentDetail.getPropertyType(), source,
						CategoryCodeConstant.PROPERTY_TYPE);

				validator.validateString(documentDetail.getLotsLocation(), "DocumentationDetail - LotsLocation", false,
						0, 15);

				validator.validateStdCodeAllowNull(documentDetail.getTitleType(), source,
						CategoryCodeConstant.TITLE_TYPE);

				validator.validateString(documentDetail.getTitleNumber(), "DocumentationDetail - TitleNumber", false,
						0, 20);

				validator.validateDoubleDigit(documentDetail.getBuybackValue(), "DocumentationDetail - BuybackValue",
						false, 15, 0, false);

				validator.validateDoubleDigit(documentDetail.getGuaranteeAmount(),
						"DocumentationDetail - GuaranteeAmount", false, 15, 0, false);
			}

			validator.validateString(documentDetail.getUpdateStatusIndicator(), "UpdateStatusIndicator", false, 0, 1);

			validator.validateString(documentDetail.getChangeIndicator(), "ChangeIndicator", false, 0, 1);
			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, documentDetail.getUpdateStatusIndicator(),
						documentDetail.getChangeIndicator());
			}
		}
	}
}
