package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;
import com.integrosys.cms.host.eai.support.ValidationMandatoryFieldFactory;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * EAI Message validator to validate instance of
 * <tt>SecurityInsurancePolicy</tt>
 * @author Chong Jun Yong
 * 
 */
public class InsurancePolicyValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] securityTypeIdsInsurancePolicyDetailApplicable;

	private ValidationMandatoryFieldFactory validationMandatoryFieldFactory;

	private String[] sourceIdsinsuranceCompanyNameApplicable;

	public void setSecurityTypeIdsInsurancePolicyDetailApplicable(
			String[] securityTypeIdsInsurancePolicyDetailApplicable) {
		this.securityTypeIdsInsurancePolicyDetailApplicable = securityTypeIdsInsurancePolicyDetailApplicable;
	}

	public void setValidationMandatoryFieldFactory(ValidationMandatoryFieldFactory validationMandatoryFieldFactory) {
		this.validationMandatoryFieldFactory = validationMandatoryFieldFactory;
	}

	public void setSourceIdsinsuranceCompanyNameApplicable(String[] sourceIdsinsuranceCompanyNameApplicable) {
		this.sourceIdsinsuranceCompanyNameApplicable = sourceIdsinsuranceCompanyNameApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();

		if (!ArrayUtils.contains(this.securityTypeIdsInsurancePolicyDetailApplicable, msg.getSecurityDetail()
				.getSecurityType().getStandardCodeValue())) {
			return;
		}

		Vector insurancePolicyDetails = msg.getInsurancePolicyDetail();
		if (insurancePolicyDetails == null || insurancePolicyDetails.isEmpty()) {
			return;
		}

		for (Iterator itr = insurancePolicyDetails.iterator(); itr.hasNext();) {
			SecurityInsurancePolicy insurancePolicy = (SecurityInsurancePolicy) itr.next();

			validator.validateString(insurancePolicy.getSecurityId(), "InsurancePolicyDetail - LOSSecurityId", true, 1,
					20);

			if (IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(insurancePolicy.getUpdateStatusIndicator())
					&& IEaiConstant.CHANGE_INDICATOR_YES.equals(insurancePolicy.getChangeIndicator())) {
				validator.validateNumber(new Long(insurancePolicy.getCMSSecurityId()),
						"InsurancePolicyDetail - CMSSecurityId", true, 1, IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);

				validator.validateNumber(new Long(insurancePolicy.getInsurancePolicyId()),
						"IInsurancePolicyDetail - CMSInsurancePolicyId", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
			}

			validator.validateString(insurancePolicy.getPolicyNumber(), "InsurancePolicyDetail - PolicyNumber", false,
					0, 20);

			validator.validateString(insurancePolicy.getCoverNoteNum(), "InsurancePolicyDetail - CoverNoteNum", false,
					0, 20);

			if (this.validationMandatoryFieldFactory.isMandatoryField(source, SecurityInsurancePolicy.class,
					"insurerName")) {
				validator.validateStdCode(insurancePolicy.getInsurerName(), source, CategoryCodeConstant.INSURER_NAME);
			}
			else {
				validator.validateStdCodeAllowNull(insurancePolicy.getInsurerName(), source,
						CategoryCodeConstant.INSURER_NAME);
			}

			if (this.validationMandatoryFieldFactory.isMandatoryField(source, SecurityInsurancePolicy.class,
					"insuranceType")) {
				validator.validateStdCode(insurancePolicy.getInsuranceType(), source,
						CategoryCodeConstant.INSURANCE_TYPE);
			}
			else {
				validator.validateStdCodeAllowNull(insurancePolicy.getInsuranceType(), source,
						CategoryCodeConstant.INSURANCE_TYPE);
			}

			validator.validateString(insurancePolicy.getInsurancePolicyCurrency(),
					"InsurancePolicyDetail - InsurancePolicyCurrency", true, 3, 3);

			validator.validateDoubleDigit(insurancePolicy.getInsurableAmount(),
					"InsurancePolicyDetail - InsurableAmount", false, 15, 2, false);

			boolean insuredAmountMandatory = this.validationMandatoryFieldFactory.isMandatoryField(source,
					SecurityInsurancePolicy.class, "insuredAmount");
			validator.validateDoubleDigit(insurancePolicy.getInsuredAmount(), "InsurancePolicyDetail - InsuredAmount",
					insuredAmountMandatory, 15, 2, false);

			boolean effectiveDateMandatory = this.validationMandatoryFieldFactory.isMandatoryField(source,
					SecurityInsurancePolicy.class, "effectiveDate");
			validator.validateDate(insurancePolicy.getEffectiveDate(), "InsurancePolicyDetail - EffectiveDate",
					effectiveDateMandatory);

			validator.validateString(insurancePolicy.getBankCustArrangeIns(),
					"InsurancePolicyDetail - BankCustArrangeIns", false, 0, 1, new String[] { "B", "D", "C" });

			if (insurancePolicy.getInsurerName() == null) {
				if (ArrayUtils.contains(this.sourceIdsinsuranceCompanyNameApplicable, source)) {
					validator.validateString(insurancePolicy.getInsuranceCompanyName(),
							"InsurancePolicyDetail - InsCompanyName", true, 0, 40);
				}
			}

			validator.validateString(insurancePolicy.getAutoDebit(), "InsurancePolicyDetail - AutoDebit", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if (ICMSConstant.TRUE_VALUE.equals(insurancePolicy.getAutoDebit())) {
				validator.validateString(insurancePolicy.getAcType(), "InsurancePolicyDetail - AccountType", true, 1,
						1, new String[] { "D", "L", "S" });

				validator.validateString(insurancePolicy.getDebitingACNo(), "InsurancePolicyDetail - AccountType",
						true, 1, 20);
			}

			validator.validateDoubleDigit(insurancePolicy.getInsPremium(), "InsurancePolicyDetail - InsPremium", false,
					11, 2, false);

			validator.validateString(insurancePolicy.getSchemeInd(), "InsurancePolicyDetail - SchemeInd", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			validator.validateDoubleDigit(insurancePolicy.getGrossPremium(), "InsurancePolicyDetail - GrossPremium",
					false, 11, 2, false);

			validator.validateDoubleDigit(insurancePolicy.getNettPermByBorrower(),
					"InsurancePolicyDetail - NettPremByBorrower", false, 11, 2, false);

			validator.validateDoubleDigit(insurancePolicy.getNettPermToInsCo(),
					"InsurancePolicyDetail - NettPremToInsCo", false, 11, 2, false);

			validator.validateDoubleDigit(insurancePolicy.getTakafulCommission(),
					"InsurancePolicyDetail - TakafulCommission", false, 11, 2, false);

			validator.validateDoubleDigit(insurancePolicy.getStampDuty(), "InsurancePolicyDetail - StampDuty", false,
					11, 2, false);

			validator.validateDoubleDigit(insurancePolicy.getNewAmtInsured(), "InsurancePolicyDetail - NewAmtInsured",
					false, 11, 2, false);

			validator.validateStdCodeAllowNull(insurancePolicy.getBuildingOccupation(), source, "BO");

			validator.validateString(insurancePolicy.getNatureOfWork(), "InsurancePolicyDetail - NatureOfWork", false,
					0, 40);

			validator.validateStdCodeAllowNull(insurancePolicy.getBuildingType(), source, "BUILDING_TYPE");

			// TODO: number of storey

			validator.validateStdCodeAllowNull(insurancePolicy.getWall(), source, "WALL_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getExtensionWalls(), source, "EXTENSION_WALL_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getRoof(), source, "ROOF_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getExtensionRoof(), source, "EXTENSION_ROOF_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getEndorsementCode(), source, "ENDORSEMENT");

			validator.validateStdCodeAllowNull(insurancePolicy.getPolicyCustodian(), source, "INSURER_TAG");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfFloor(), source, "FLOOR_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfPerils1(), source, "PERILS_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfPerils2(), source, "PERILS_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfPerils3(), source, "PERILS_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfPerils4(), source, "PERILS_TYPE");

			validator.validateStdCodeAllowNull(insurancePolicy.getTypeOfPerils5(), source, "PERILS_TYPE");

			validator.validateString(insurancePolicy.getRemark1(), "InsurancePolicyDetail - Remark1", false, 0, 60);

			validator.validateString(insurancePolicy.getRemark2(), "InsurancePolicyDetail - Remark2", false, 0, 60);

			validator.validateString(insurancePolicy.getRemark3(), "InsurancePolicyDetail - Remark3", false, 0, 60);

			validator.validateString(insurancePolicy.getInsuredAddress(), "InsurancePolicyDetail - InsuredAddress",
					false, 0, 250);

			validator.validateString(insurancePolicy.getInsuredAgainst(), "InsurancePolicyDetail - InsuredAgainst",
					false, 0, 250);

			if (StringUtils.isEmpty(insurancePolicy.getInsurancePolicyCurrency())) {
				insurancePolicy.setInsurancePolicyCurrency(msg.getSecurityDetail().getCurrency());
			}

		}
	}
}
