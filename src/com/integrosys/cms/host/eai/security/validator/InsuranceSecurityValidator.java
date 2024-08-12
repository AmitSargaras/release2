package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.insurance.CreditDefaultSwapsDetail;
import com.integrosys.cms.host.eai.security.bus.insurance.InsuranceSecurity;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * Message validator to validate instance of <tt>InsuranceSecurity</tt> and
 * possible <tt>CreditDefaultSwapsDetail</tt>
 * @author Chong Jun Yong
 * 
 */
public class InsuranceSecurityValidator extends SecurityValidator {
	private String[] securitySubtypeIdsCreditInsurance;

	private String[] securitySubtypeIdsLifeInsurance;

	private String[] securitySubtypeIdsCreditDerivativesAndDefaultSwap;

	private String[] securitySubtypeIdsInsuranceDescriptionApplicable;

	public void setSecuritySubtypeIdsCreditInsurance(String[] securitySubtypeIdsCreditInsurance) {
		this.securitySubtypeIdsCreditInsurance = securitySubtypeIdsCreditInsurance;
	}

	public void setSecuritySubtypeIdsLifeInsurance(String[] securitySubtypeIdsLifeInsurance) {
		this.securitySubtypeIdsLifeInsurance = securitySubtypeIdsLifeInsurance;
	}

	public void setSecuritySubtypeIdsCreditDerivativesAndDefaultSwap(
			String[] securitySubtypeIdsCreditDerivativesAndDefaultSwap) {
		this.securitySubtypeIdsCreditDerivativesAndDefaultSwap = securitySubtypeIdsCreditDerivativesAndDefaultSwap;
	}

	public void setSecuritySubtypeIdsInsuranceDescriptionApplicable(
			String[] securitySubtypeIdsInsuranceDescriptionApplicable) {
		this.securitySubtypeIdsInsuranceDescriptionApplicable = securitySubtypeIdsInsuranceDescriptionApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_INSURANCE.equals(security.getSecurityType().getStandardCodeValue())) {
			InsuranceSecurity insuranceDetail = msg.getInsuranceDetail();

			validator.rejectIfNull(insuranceDetail, "InsuranceDetail");

			validator
					.validateString(insuranceDetail.getLOSSecurityId(), "InsuranceDetail - LOSSecurityId", true, 1, 20);

			if (ArrayUtils.contains(this.securitySubtypeIdsInsuranceDescriptionApplicable, secSubType)) {
				validator.validateString(insuranceDetail.getInsuranceDescription(),
						"InsuranceDetail - InsuranceDescription", false, 0, 250);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsCreditInsurance, secSubType)
					|| ArrayUtils.contains(this.securitySubtypeIdsLifeInsurance, secSubType)) {
				validator.validateStdCode(insuranceDetail.getInsuranceName(), source, "INSURER_NAME");

				validator.validateStdCode(insuranceDetail.getInsuranceType(), source, "INSURANCE_TYPE");

				validator.validateDoubleDigit(insuranceDetail.getInsuredAmount(), "InsuranceDetail - InsuredAmount",
						true, 13, 2, false);

				insuranceDetail.setInsuredAmountCurrency(security.getCurrency());

				validator.validateDoubleDigit(insuranceDetail.getInsurancePremium(), "InsuranceDetail - Premium",
						false, 13, 2, false);

				validator.validateString(insuranceDetail.getInsurancePremiumCurrency(),
						"InsuranceDetail - PremiumCurrency", false, 0, 3);

				if (this.validationMandatoryFieldFactory.isMandatoryField(source, InsuranceSecurity.class,
						"effectiveDate")) {
					validator.validateDate(insuranceDetail.getEffectiveDate(), "InsuranceDetail - EffectiveDate", true);
				}

				validator.validateString(insuranceDetail.getPolicyNo(), "InsuranceDetail - PolicyNo", false, 0, 60);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsCreditInsurance, secSubType)) {
				validator.validateString(insuranceDetail.getExternalLegalCounsel(),
						"InsuranceDetail - ExternalLegalCounsel", false, 0, 100);

				validator.validateString(insuranceDetail.getAccelerationClause(),
						"InsuranceDetail - AccelerationClause", false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

				validator.validateString(insuranceDetail.getLocalCurrencyInCM(), "InsuranceDetail - LocalCurrencyInCM",
						false, 0, 3);

				validator.validateStdCodeAllowNull(insuranceDetail.getCoreMarket(), source,
						CategoryCodeConstant.CORE_MARKET);

				validator.validateStdCodeAllowNull(insuranceDetail.getBankCustArrangeIns(), source,
						CategoryCodeConstant.ARR_INSURER);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsLifeInsurance, secSubType)) {
				validator.validateString(insuranceDetail.getBankInterestNoted(), "InsuranceDetail - BankInterestNoted",
						false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsCreditDerivativesAndDefaultSwap, secSubType)) {
				validator.validateDate(insuranceDetail.getISDADate(), "InsuranceDetail - ISDADate", true);

				validator.validateDate(insuranceDetail.getTreasuryDate(), "InsuranceDetail - TreasuryDate", true);
			}

			validator.validateString(insuranceDetail.getChangeIndicator(), "InsuranceDetail - ChangeIndicator", false,
					0, 1, EaiConstantCla.getAllowedValuesYesNo());

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, insuranceDetail.getUpdateStatusIndicator(),
						insuranceDetail.getChangeIndicator());
			}

			if ((security.getCmv() != null) && (security.getCmv().doubleValue() <= 0)) {
				security.setCmv(insuranceDetail.getInsuredAmount());
				security.setCmvCurrency(insuranceDetail.getInsuredAmountCurrency());
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

			validateCreditDefaultSwaps(msg, source);
		}
	}

	private void validateCreditDefaultSwaps(SecurityMessageBody msg, String source)
			throws EAIMessageValidationException {
		Vector creditDefaultSwaps = msg.getCreditDefaultSwapsDetail();
		if (creditDefaultSwaps == null) {
			return;
		}
		for (Iterator itr = creditDefaultSwaps.iterator(); itr.hasNext();) {
			CreditDefaultSwapsDetail cds = (CreditDefaultSwapsDetail) itr.next();

			validator.validateString(cds.getSecurityId(), "CreditDefaultSwapsDetail - LOSSecurityId", true, 1, 20);

			if (IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(cds.getUpdateStatusIndicator())
					&& IEaiConstant.CHANGE_INDICATOR_YES.equals(cds.getChangeIndicator())) {
				validator.validateNumber(new Long(cds.getCDSId()), "CreditDefaultSwapsDetail - CMSCDSId ", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
			}

			validator.validateString(cds.getBankEntity(), "CreditDefaultSwapsDetail - BankEntity", false, 0, 150);

			validator.validateString(cds.getHedgeType(), "CreditDefaultSwapsDetail - HedgeType", false, 0, 150);

			validator.validateString(cds.getHedgeReference(), "CreditDefaultSwapsDetail - HedgeReference", false, 0,
					150);

			validator.validateString(cds.getCDSReference(), "CreditDefaultSwapsDetail - CDSReference", false, 0, 150);

			validator.validateString(cds.getTradeId(), "CreditDefaultSwapsDetail - TradeId", false, 0, 150);

			validator.validateNumber(cds.getTenor(), "CreditDefaultSwapsDetail - Tenor", false, 0, 9999);

			validator.validateString(cds.getTenorUnit(), "CreditDefaultSwapsDetail - TenorUnit", false, 0, 150);

			validator.validateString(cds.getTradeCurrency(), "CreditDefaultSwapsDetail - TradeCurrency", false, 0, 3);

			validator.validateDoubleDigit(cds.getNotionalHedgedAmount(),
					"CreditDefaultSwapsDetail - NotionalHedgedAmount", false, 15, 2, false);

			validator.validateString(cds.getReferenceEntity(), "CreditDefaultSwapsDetail - ReferenceEntity", false, 0,
					100);

			validator.validateStdCodeAllowNull(cds.getLoanBondBookingLocation(), source, "40");

			validator.validateStdCodeAllowNull(cds.getCDSBookingLocation(), source, "40");

			validator.validateString(cds.getReferenceAsset(), "CreditDefaultSwapsDetail - ReferenceAsset", false, 0,
					100);

			validator.validateString(cds.getIssuer(), "CreditDefaultSwapsDetail - Issuer", false, 0, 100);

			validator.validateString(cds.getIssuerId(), "CreditDefaultSwapsDetail - IssuerId", false, 0, 50);

			validator.validateString(cds.getIssuerDetail(), "CreditDefaultSwapsDetail - IssuerDetail", false, 0, 250);

			validator.validateDoubleDigit(cds.getDealtPrice(), "CreditDefaultSwapsDetail - DealtPrice", false, 15, 2,
					false);

			validator.validateDoubleDigit(cds.getResidualMaturity(), "CreditDefaultSwapsDetail - ResidualMaturity",
					false, 15, 2, false);

			validator.validateString(cds.getSettlement(), "CreditDefaultSwapsDetail - Settlement", false, 0, 150);

			validator
					.validateDoubleDigit(cds.getParValue(), "CreditDefaultSwapsDetail - ParValue", false, 15, 2, false);

			validator.validateDoubleDigit(cds.getDeclineMarketValue(), "CreditDefaultSwapsDetail - DeclineMarketValue",
					false, 15, 2, false);

			validator.validateString(cds.getComplianceCertificate(),
					"CreditDefaultSwapsDetail - ComplianceCertificate", false, 0, 150);

			validator.validateString(cds.getValuationCurrency(), "CreditDefaultSwapsDetail - ValuationCurrency", false,
					0, 3);

			validator.validateDoubleDigit(cds.getMargin(), "CreditDefaultSwapsDetail - Margin", false, 3, 2, false);

			validator.validateNumber(cds.getMargin(), "CreditDefaultSwapsDetail - Margin", false, 0, 100);

			validator.validateDoubleDigit(cds.getNominalValue(), "CreditDefaultSwapsDetail - NominalValue", false, 15,
					2, false);

			validator.validateDoubleDigit(cds.getCMV(), "CreditDefaultSwapsDetail - CMV", true, 15, 2, false);

			validator.validateDoubleDigit(cds.getFSV(), "CreditDefaultSwapsDetail - FSV", true, 15, 2, false);

			validator.validateString(cds.getChangeIndicator(), "CreditDefaultSwapsDetail - ChangeIndicator", false, 0,
					1, EaiConstantCla.getAllowedValuesYesNo());

			validator.validateString(cds.getUpdateStatusIndicator(),
					"CreditDefaultSwapsDetail - UpdateStatusIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

		}
	}
}
