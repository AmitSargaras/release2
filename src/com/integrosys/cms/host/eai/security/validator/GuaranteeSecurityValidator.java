package com.integrosys.cms.host.eai.security.validator;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.guarantee.GuaranteeSecurity;

/**
 * Message validator to validate instance of <tt>GuaranteeSecurity</tt>
 * @author Chong Jun Yong
 * 
 */
public class GuaranteeSecurityValidator extends SecurityValidator {
	private String[] securitySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable;

	private String[] sourceIdsGuaranteeSecuredUnsecuredPercentageApplicable;

	private String[] securitySubtypeIdsReimbursementBankApplicable;

	private String[] securitySubtypeIdsSchemeApplicable;

	private String[] securitySubtypeIdsRefNoApplicable;

	private String[] sourceIdsRefNoApplicable;

	private String[] securitySubtypeIdsSecurityIssuerAndBenificiarApplicable;

	private String[] sourceIdsSecurityIssuerAndBenificiarApplicable;

	public void setSecuritySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable(
			String[] securitySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable) {
		this.securitySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable = securitySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable;
	}

	public void setSourceIdsGuaranteeSecuredUnsecuredPercentageApplicable(
			String[] sourceIdsGuaranteeSecuredUnsecuredPercentageApplicable) {
		this.sourceIdsGuaranteeSecuredUnsecuredPercentageApplicable = sourceIdsGuaranteeSecuredUnsecuredPercentageApplicable;
	}

	public void setSecuritySubtypeIdsReimbursementBankApplicable(String[] securitySubtypeIdsReimbursementBankApplicable) {
		this.securitySubtypeIdsReimbursementBankApplicable = securitySubtypeIdsReimbursementBankApplicable;
	}

	public void setSecuritySubtypeIdsSchemeApplicable(String[] securitySubtypeIdsSchemeApplicable) {
		this.securitySubtypeIdsSchemeApplicable = securitySubtypeIdsSchemeApplicable;
	}

	public void setSecuritySubtypeIdsRefNoApplicable(String[] securitySubtypeIdsRefNoApplicable) {
		this.securitySubtypeIdsRefNoApplicable = securitySubtypeIdsRefNoApplicable;
	}

	public void setSourceIdsRefNoApplicable(String[] sourceIdsRefNoApplicable) {
		this.sourceIdsRefNoApplicable = sourceIdsRefNoApplicable;
	}

	public void setSecuritySubtypeIdsSecurityIssuerAndBenificiarApplicable(
			String[] securitySubtypeIdsSecurityIssuerAndBenificiarApplicable) {
		this.securitySubtypeIdsSecurityIssuerAndBenificiarApplicable = securitySubtypeIdsSecurityIssuerAndBenificiarApplicable;
	}

	public void setSourceIdsSecurityIssuerAndBenificiarApplicable(
			String[] sourceIdsSecurityIssuerAndBenificiarApplicable) {
		this.sourceIdsSecurityIssuerAndBenificiarApplicable = sourceIdsSecurityIssuerAndBenificiarApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String securitySubtypeId = security.getSecuritySubType().getStandardCodeValue();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_GUARANTEE.equals(security.getSecurityType().getStandardCodeValue())) {
			GuaranteeSecurity guaranteeDetail = msg.getGuaranteeDetail();

			validator.rejectIfNull(guaranteeDetail, "GuaranteeDetail");

			validator
					.validateString(guaranteeDetail.getLOSSecurityId(), "GuaranteeDetail - LOSSecurityId", true, 1, 20);

			validator.validateString(guaranteeDetail.getGuaranteesDescription(),
					"GuaranteeDetail - GuaranteesDescription", false, 0, 100);

			validator.validateNumber(guaranteeDetail.getClaimPeriod(), "GuaranteeDetail - ClaimPeriod", false, 0, 9999);

			validator.validateString(guaranteeDetail.getClaimPeriodUOM(), "GuaranteeDetail - ClaimPeriodUOM", false, 0,
					1, EaiConstantCla.getAllowedValuesFrequencyUnit());

			if (ArrayUtils.contains(this.securitySubtypeIdsRefNoApplicable, securitySubtypeId)
					&& ArrayUtils.contains(this.sourceIdsRefNoApplicable, source)) {
				validator.validateString(guaranteeDetail.getRefNo(), "GuaranteeDetail - RefNo", false, 0, 60);
			}

			validator.validateDoubleDigit(guaranteeDetail.getGuaranteeAmt(), "GuaranteeDetail - GuaranteeAmt", true,
					13, 2, false);

			if (ArrayUtils.contains(this.securitySubtypeIdsSecurityIssuerAndBenificiarApplicable, securitySubtypeId)
					&& ArrayUtils.contains(this.sourceIdsSecurityIssuerAndBenificiarApplicable, source)) {
				validator.validateString(guaranteeDetail.getIssuingBank(), "GuaranteeDetail - SecurityIssuer", false,
						0, 40);

				validator.validateString(guaranteeDetail.getIssuingBankCtry(), "GuaranteeDetail - SecurityIssuerCtry",
						false, 0, 2);

				validator.validateString(guaranteeDetail.getBeneficiaryName(), "GuaranteeDetail - BeneficiaryName",
						false, 0, 40);
			}

			validator.validateString(guaranteeDetail.getComments(), "GuaranteeDetail - Comments", false, 0, 100);

			if (ArrayUtils.contains(this.securitySubtypeIdsReimbursementBankApplicable, securitySubtypeId)) {
				validator
						.validateStdCodeAllowNull(guaranteeDetail.getReimbursementBank(), source, "REIMBURSEMENT_BANK");
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsSchemeApplicable, securitySubtypeId)) {
				validator.validateStdCodeAllowNull(guaranteeDetail.getScheme(), source, "SCHEME");
			}

			if (ArrayUtils
					.contains(this.securitySubtypeIdGuaranteeSecuredUnsecuredPortionApplicable, securitySubtypeId)) {
				validator.validateDoubleDigit(guaranteeDetail.getSecuredAmount(), "GuaranteeDetail - SecuredAmount",
						false, 15, 0, false);

				validator.validateDoubleDigit(guaranteeDetail.getUnsecuredAmount(),
						"GuaranteeDetail - UnsecuredAmount", false, 15, 0, false);

				if (ArrayUtils.contains(this.sourceIdsGuaranteeSecuredUnsecuredPercentageApplicable, source)) {
					validator.validateNumber(guaranteeDetail.getSecuredPercentage(),
							"GuaranteeDetail - SecuredPercentage", false, 0, 99);

					validator.validateNumber(guaranteeDetail.getUnsecuredPercentage(),
							"GuaranteeDetail - UnsecuredPercentage", false, 0, 99);
				}
			}

			validator.validateString(guaranteeDetail.getUpdateStatusIndicator(), "UpdateStatusIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(guaranteeDetail.getChangeIndicator(), "ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, guaranteeDetail.getUpdateStatusIndicator(),
						guaranteeDetail.getChangeIndicator());
			}

			if ((security.getCmv() != null) && (security.getCmv().doubleValue() <= 0)) {
				security.setCmv(guaranteeDetail.getGuaranteeAmt());
				security.setCmvCurrency(security.getOriginalCurrency());
			}

			double margin = 100;
			if ((security.getSecurityLocation() != null) && (security.getSecuritySubType() != null)) {
				margin = this.securityJdbc.retrieveMargin(security.getSecurityLocation().getLocationCountry(), security
						.getSecuritySubType().getStandardCodeValue());
			}
			if (((security.getCmv() != null) && (security.getCmv().doubleValue() > 0))
					&& ((security.getFsv() != null) && (security.getFsv().doubleValue() <= 0))) {
				security.setFsv(new Double(security.getCmv().doubleValue() * margin / 100.0));
				security.setFsvCurrency(security.getCmvCurrency());
			}

		}
	}
}
