package com.integrosys.cms.host.eai.security.validator;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * EAI Message validator to validate instance of <tt>PropertySecurity</tt>
 * @author Chong Jun Yong
 * 
 */
public class PropertySecurityValidator extends SecurityValidator {

	private String[] sourceIdsDeveloperGroupCompanyApplicable;

	public void setSourceIdsDeveloperGroupCompanyApplicable(String[] sourceIdsDeveloperGroupCompanyApplicable) {
		this.sourceIdsDeveloperGroupCompanyApplicable = sourceIdsDeveloperGroupCompanyApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(security.getSecurityType().getStandardCodeValue())) {
			PropertySecurity propertyDetail = msg.getPropertyDetail();

			validator.rejectIfNull(propertyDetail, "PropertyDetail");

			validator.validateString(propertyDetail.getLOSSecurityId(), "PropertyDetail - LOSSecurityId", true, 1, 20);

			validator
					.validateStdCode(propertyDetail.getPropertyType(), source, CategoryCodeConstant.CATEGORY_PROP_TYPE);

			validator.validateDoubleDigit(propertyDetail.getSnpAgreementValue(), "PropertyDetail - SnpAgreementValue",
					false, 13, 2, false);

			validator.validateStdCode(propertyDetail.getPropertyUsage(), source,
					CategoryCodeConstant.PROPERTY_USAGE_LIST);

			validator.validateStdCodeAllowNull(propertyDetail.getCategoryOfLandUse(), source, "BUILDING_TYPE");

			validator.validateStdCode(propertyDetail.getPropertyCompletionStatus(), source,
					"PROPERTY_COMPLETION_STATUS");

			validator.validateStdCodeAllowNull(propertyDetail.getCompletionStage(), source, "COMPLETION_STAGE");

			validator.validateStdCodeAllowNull(propertyDetail.getDeveloperName(), source,
					CategoryCodeConstant.DEVELOPER_CODE);

			validator.validateStdCodeAllowNull(propertyDetail.getProjectName(), source, "DEALER");

			validator.validateString(propertyDetail.getPhaseNo(), "PropertyDetail - PhaseNo", false, 0, 20);

			validator.validateStdCode(propertyDetail.getTitleType(), source, "TITLE_TYPE");

			validator.validateStdCode(propertyDetail.getTitleNumberPrefix(), source, "TITLE_NUMBER");

			validator.validateString(propertyDetail.getTitleNumber(), "PropertyDetail - TitleNumber", true, 1, 100);

			validator.validateStdCodeAllowNull(propertyDetail.getLotNoType(), source, "LOT_NO");

			validator.validateString(propertyDetail.getLotNo(), "PropertyDetail - LotNo", false, 0, 50);

			validator.validateStdCode(propertyDetail.getState(), source, CategoryCodeConstant.STATE_CATEGORY_CODE);

			validator
					.validateStdCode(propertyDetail.getDistrict(), source, CategoryCodeConstant.DISTRICT_CATEGORY_CODE);

			validator.validateStdCode(propertyDetail.getMukim(), source, CategoryCodeConstant.MUKIM_CATEGORY_CODE);

			validator.validateString(propertyDetail.getTaman(), "PropertyDetail - Taman", false, 0, 50);

			validator.validateString(propertyDetail.getPostCode(), "PropertyDetail - PostCode", false, 0, 15);

			validator.validateDoubleDigit(propertyDetail.getLandArea(), "PropertyDetail - Land Area", false, 18, 8,
					false);

			if (propertyDetail.getLandArea() != null) {
				validator.validateStdCode(propertyDetail.getLandAreaUOM(), source, CategoryCodeConstant.AREA_UOM);
			}

			validator.validateDoubleDigit(propertyDetail.getBuiltUpArea(), "PropertyDetail - BuiltUpArea", false, 13,
					6, false);

			if (propertyDetail.getBuiltUpArea() != null) {
				validator.validateStdCode(propertyDetail.getBuiltUpAreaUOM(), source, CategoryCodeConstant.AREA_UOM);
			}

			validator.validateString(propertyDetail.getUnitParcelNo(), "PropertyDetail - UnitParcelNo", false, 0, 10);

			validator.validateString(propertyDetail.getTenureType(), "PropertyDetail - TenureType", true, 1, 1,
					new String[] { IEaiConstant.TENURE_TYPE_FREEHOLDER, IEaiConstant.TENURE_TYPE_LEASEHOLD });

			if (IEaiConstant.TENURE_TYPE_LEASEHOLD.equals(propertyDetail.getTenureType())) {
				validator.validateNumber(propertyDetail.getTenure(), "PropertyDetail - Tenure", true, 1, 99999);
			}

			validator.validateString(propertyDetail.getQuitRentInd(), "PropertyDetail - QuitRentInd", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if (ICMSConstant.TRUE_VALUE.equals(propertyDetail.getQuitRentInd())) {
				validator.validateDoubleDigit(propertyDetail.getQuitRentAmountPaid(),
						"PropertyDetail - QuitRentAmountPaid", true, 15, 0, false);

				validator.rejectIfNull(propertyDetail.getNextQuitRentDate(), "PropertyDetail - NextQuitRentDate");

				validator.rejectIfNull(propertyDetail.getQuitRentPaymentDate(), "PropertyDetail - QuitRentPaymentDate");
			}

			validator.validateString(propertyDetail.getAssessment(), "PropertyDetail - Assessment", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if (ICMSConstant.TRUE_VALUE.equals(propertyDetail.getAssessment())) {
				validator.validateDoubleDigit(propertyDetail.getAssessmentRate(), "PropertyDetail - AssessmentRate",
						true, 13, 2, false);

				validator.validateStdCode(propertyDetail.getAssessmentPeriod(), source,
						CategoryCodeConstant.ASSESSMENT_PERIOD);

				validator.validateNumber(propertyDetail.getAssessmentYear(), "PropertyDetail - AssessmentYear", true,
						1, 9999);

				validator.rejectIfNull(propertyDetail.getAssessmentPaymentDate(),
						"PropertyDetail - AssessmentPaymentDate");
			}

			validator.validateDoubleDigit(propertyDetail.getAuctionPrice(), "PropertyDetail - AuctionPrice", false, 13,
					2, false);

			validator.validateDoubleDigit(propertyDetail.getAmountRedeem(), "PropertyDetail - AmountRedeem", false, 13,
					2, false);

			validator.validateDoubleDigit(propertyDetail.getPricePerUnit(), "PropertyDetail - PricePerUnit", false, 9,
					2, false);

			validator.validateString(propertyDetail.getExpressedCondition(), "PropertyDetail - ExpressedCondition",
					false, 0, 250);

			validator.validateString(propertyDetail.getPropertyAddress1(), "PropertyDetail - PropertyAddress1", false,
					0, 40);

			validator.validateString(propertyDetail.getPropertyAddress2(), "PropertyDetail - PropertyAddress2", false,
					0, 40);

			validator.validateString(propertyDetail.getPropertyAddress3(), "PropertyDetail - PropertyAddress3", false,
					0, 40);

			validator.validateString(propertyDetail.getPhysicalInspectionFlag(), "PropertyDetail - PhysicalInspection",
					false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

			if ((ICMSConstant.TRUE_VALUE).equals(propertyDetail.getPhysicalInspectionFlag())) {
				validator.validateDate(propertyDetail.getLastPhysicalInspectionDate(),
						"PropertyDetail - LastPhysicalInspectionDate", true);

				validator.validateString(propertyDetail.getPhysicalInspectionFrequencyUOM(),
						"PropertyDetail - PhysicalInspectionFrequencyUOM", true, 1, 1,
						EaiConstantCla.getAllowedValuesFrequencyUnit());

				validator.validateNumber(propertyDetail.getPhysicalInspectionFrequencyUnit(),
						"PropertyDetail - PhysicalInspectionFrequencyUnit", true, 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_10);
			}

			validator.validateString(propertyDetail.getSecurityEnvironmentallyRisky(),
					"PropertyDetail - EnvironmentallyRiskyStatus", false, 0, 1, EaiConstantCla.getAllowedValuesYesNoNa());
			if ((ICMSConstant.TRUE_VALUE).equals(propertyDetail.getSecurityEnvironmentallyRisky())) {
				validator.validateDate(propertyDetail.getSecurityEnvironmentallyRiskyConfirmedDate(),
						"PropertyDetail - EnvironmentallyRiskyDate", true);
			}

			validator.validateString(propertyDetail.getSecurityEnvironmentallyRiskyRemarks(),
					"PropertyDetail - EnvironmentallyRiskyRemark", false, 0, 250);

			validator.validateStdCodeAllowNull(propertyDetail.getNonPreferredLocation(), source, "SCHEDULED_LOCATION");

			validator.validateString(propertyDetail.getIndependentValuerFlag(), "PropertyDetail - IndependentValuer",
					false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());

			validator.validateString(propertyDetail.getAbandonedProject(), "PropertyDetail - AbandonedProject", false,
					0, 1, EaiConstantCla.getAllowedValuesYesNo());

			validator.validateString(propertyDetail.getRestrictionCondition(), "PropertyDetail - RestrictionCondition",
					false, 0, 250);

			validator.validateString(propertyDetail.getRemarks(), "PropertyDetail - Remarks", false, 0, 250);

			validator.validateString(propertyDetail.getRegisteredHolderOwner(),
					"PropertyDetail - RegisteredHolderOwner", false, 0, 250);

			validator.validateStdCodeAllowNull(propertyDetail.getStoreyNo(), source, "STOREY_NO");

			validator.validateString(propertyDetail.getSectionNo(), "PropertyDetail - SectionNo", false, 0, 50);

			if (ArrayUtils.contains(this.sourceIdsDeveloperGroupCompanyApplicable, source)) {
				validator.validateStdCodeAllowNull(propertyDetail.getDeveloperGroupName(), source, "DEV_GRP_CO");
			}

			validator.validateString(propertyDetail.getUpdateStatusIndicator(), "UpdateStatusIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(propertyDetail.getChangeIndicator(), "ChangeIndicator", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, propertyDetail.getUpdateStatusIndicator(),
						propertyDetail.getChangeIndicator());
			}
		}
	}
}
