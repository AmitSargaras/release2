package com.integrosys.cms.host.eai.limit.validator;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.EitherFieldRequiredException;
import com.integrosys.cms.host.eai.FieldValueNotAllowedException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.OriginatingBookingLocation;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.JointBorrower;
import com.integrosys.cms.host.eai.limit.bus.LimitCreditGrade;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.limit.bus.LimitsApprovedSecurityMap;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.ui.common.CurrencyList;

/**
 * Validator to validate the whole AA message, limits, facilities, limit
 * security map, charge detail.
 * @author marvin
 * @author Thurein
 * @author Chong Jun Yong
 */
public class AAValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] sourceIdsDrawAmountOrPercentageMandatory;

	public void setSourceIdsDrawAmountOrPercentageMandatory(String[] sourceIdsDrawAmountOrPercentageMandatory) {
		this.sourceIdsDrawAmountOrPercentageMandatory = sourceIdsDrawAmountOrPercentageMandatory;
	}

	public void validate(EAIMessage eaimessage) throws EAIMessageValidationException {
		AAMessageBody body = (AAMessageBody) eaimessage.getMsgBody();
		LimitProfile limitProfile = body.getLimitProfile();
		Vector jointBorrowers = body.getJointBorrower();
		Vector limit = body.getLimits();
		String source = eaimessage.getMsgHeader().getSource();

		if ((limitProfile != null)) {
			validateLimitProfile(limitProfile, source);

			if (body.getCreditGrade() != null) {
				validateCreditGrade(body.getCreditGrade(), source);
			}

			if ((jointBorrowers != null) && (jointBorrowers.size() > 0)) {
				for (Iterator borrowers = jointBorrowers.iterator(); borrowers.hasNext();) {
					JointBorrower borrower = (JointBorrower) borrowers.next();
					validateJoinBorrower(borrower);
					validateCreditGrade(borrower.getCreditGrade(), source);
				}
			}

			OriginatingBookingLocation orgLocation = limitProfile.getOriginatingLocation();

			if (orgLocation != null) {
				validateOriginatingBookingLocation(orgLocation, source);
			}
		}

		if ((limit != null) && !limit.isEmpty()) {
			validateLimits(limit, source);
		}

		if ((body.getLimitsApprovedSecurityMap() != null) && !body.getLimitsApprovedSecurityMap().isEmpty()) {
			validateLimitsApprovedSecurityMap(body.getLimitsApprovedSecurityMap());
		}
	}

	private void validateLimitProfile(LimitProfile profile, String source) throws EAIMessageValidationException {

		validator.validateString(profile.getCIFId(), "LimitProfile - CIFId", true, 1, 20);

		validator.validateString(profile.getCIFSource(), "LimitProfile - CIFSource", true, 1, 10);

		validator.validateMandatoryFieldForLong("LimitProfile - SubProfileId", profile.getSubProfileId());

		if (profile.getSubProfileId() != 1L) {
			throw new FieldValueNotAllowedException("LimitProfile - SubProfileId", String.valueOf(profile
					.getSubProfileId()), new String[] { "1" });
		}

		validator.validateString(profile.getLOSAANumber(), "LimitProfile - LOSAANumber", true, 1, 35);

		validator.validateString(profile.getHostAANumber(), "LimitProfile - HostAANumber", true, 1, 20);

		validator.validateDate(profile.getAAApproveDate(), "LimitProfile - AAApproveDate", false);

		validator.validateDate(profile.getAnnualReviewDate(), "LimitProfile - AnnualReviewDate", false);

		validator.validateDate(profile.getExtensionDate(), "LimitProfile - ExtensionDate", false);

		validator.validateString(profile.getAAStatus(), "LimitProfile - AAStatus", true, 1, 50);

		validator.validateDate(profile.getAACreateDate(), "LimitProfile - AACreateDate", true);

		validator.validateString(profile.getLORequired(), "LimitProfile - LORequired", false, 0, 1);

		validator.validateString(profile.getAAType(), "LimitProfile - AAType", true, 1, 40);

		validator.validateString(profile.getAALawType(), "LimitProfile - AALawType", true, 1, 3);

		validator.validateDate(profile.getExtensionDateLOAcceptance(), "LimitProfile - ExtensionDateLOAcceptance",
				false);

		profile.setAAStatus(profile.getAAStatus().trim());
	}

	private void validateOriginatingBookingLocation(OriginatingBookingLocation location, String source)
			throws EAIMessageValidationException {
		validator.validateString(location.getOriginatingLocationCountry(), "Originating Location Country", true, 1, 2);
		validator.validateString(location.getOriginatingLocationOrganisation(), "Originating Location Organisation",
				true, 1, 40);

		validator.validateStdCode(new StandardCode("40", location.getOriginatingLocationOrganisation(), location
				.getOriginatingLocationOrganisationDesc()), source, "40");
	}

	private void validateCreditGrade(Vector vector, String source) throws EAIMessageValidationException {
		if ((vector == null) || (vector.size() == 0)) {
			return;
		}

		for (Iterator iter = vector.iterator(); iter.hasNext();) {
			LimitCreditGrade credit = (LimitCreditGrade) iter.next();

			validator.validateString(credit.getLOSAANumber(), "CreditGrade - LOSAANumber", true, 1, 35);

			validator.validateMandatoryFieldForLong("CreditGrade - CreditGradeId", credit.getCreditGradeId());

			if ((credit.getCreditGradeId() < 1)) {
				throw new MandatoryFieldMissingException("CreditGrade - CreditGradeId");
			}

			validator.validateStdCode(credit.getCreditGradeType(), source, "18");

			validator.validateStdCode(credit.getCreditGradeCode(), source, "19");

			validator.validateString(credit.getCreditGradeReasonForChange(),
					"CreditGrade - CreditGradeReasonForChange", false, 1, 100);

			validator.validateDate(credit.getCreditGradeEffectiveDate(), "CreditGrade - CreditGradeEffectiveDate",
					false);
		}
	}

	private void validateJoinBorrower(JointBorrower borrower) throws EAIMessageValidationException {
		if (borrower == null) {
			throw new MandatoryFieldMissingException("Joint Borrower");
		}

		validator.validateString(borrower.getCIFId(), "JointBorrower - CIFId", true, 1, 20);

		if ((borrower.getSubProfileId() < 1)) {
			throw new MandatoryFieldMissingException("JointBorrower - SubProfileId");
		}

		validator.validateString(borrower.getAANumber(), "JointBorrower - AANumber", true, 1, 35);
	}

	private void validateLimits(Vector vector, String source) throws EAIMessageValidationException {
		if ((vector == null) || (vector.size() == 0)) {
			throw new MandatoryFieldMissingException("Limits");
		}

		for (Iterator iter = vector.iterator(); iter.hasNext();) {
			Limits limit = (Limits) iter.next();

			if ((limit.getLimitGeneral().getChangeIndicator() != null)
					&& limit.getLimitGeneral().getChangeIndicator().equals(ICMSConstant.FALSE_VALUE)) {
				continue;
			}
			validator.validateString(limit.getLimitGeneral().getCIFId(), "LimitGeneral - CIFId", true, 1, 20);

			validator.validateMandatoryFieldForLong("LimitGeneral - SubProfileId", limit.getLimitGeneral()
					.getSubProfileId());

			if ((limit.getLimitGeneral().getSubProfileId() < 1)) {
				throw new MandatoryFieldMissingException("LimitGeneral - SubProfileId");
			}

			String limitLosAANumber = limit.getLimitGeneral().getLOSAANumber();
			validator.validateString(limitLosAANumber, "LimitGeneral - LOSAANumber", true, 1, 35);

			validator.validateString(limit.getLimitGeneral().getLOSLimitId(), "LimitGeneral - LOSLimitId", true, 1, 20);

			if (((limit.getLimitGeneral().getUpdateStatusIndicator() != null) && limit.getLimitGeneral()
					.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.UPDATEINDICATOR)))
					|| ((limit.getLimitGeneral().getUpdateStatusIndicator() != null) && limit.getLimitGeneral()
							.getUpdateStatusIndicator().equals(String.valueOf(IEaiConstant.DELETEINDICATOR)))) {
				validator.validateMandatoryFieldForLong("LimitGeneral - CMSLimitId", limit.getLimitGeneral()
						.getCMSLimitId());
				if (limit.getLimitGeneral().getCMSOuterLimitId() != null) {
					validator.validateMandatoryFieldForLong("LimitGeneral - CMSOuterLimitId", limit.getLimitGeneral()
							.getCMSOuterLimitId().longValue());
				}

				if (limit.getLimitGeneral().getCMSLimitId() < 1) {
					throw new MandatoryFieldMissingException("LimitGeneral - CMSLimitID");
				}
			}

			validator.validateString(limit.getLimitGeneral().getLOSOuterLimitId(), "LimitGeneral - LOSOuterLimitsId",
					false, 1, 20);

			validateOriginatingBookingLocation(limit.getLimitGeneral().getOriginatingBookingLocation(), source);

			validator.validateStdCode(limit.getLimitGeneral().getProductType(), source, "27");

			validator.validateStdCode(limit.getLimitGeneral().getFacilityType(), source, "26");

			validator.validateString(limit.getLimitGeneral().getLimitCurrency(), "LimitGeneral - LimitCurrency", true,
					3, 3);

			Collection col = CurrencyList.getInstance().getCountryValues();

			if ((col != null) && (col.size() != 0)) {
				boolean found = false;

				for (Iterator iter2 = col.iterator(); iter2.hasNext();) {
					String code = (String) iter2.next();

					if (limit.getLimitGeneral().getLimitCurrency().trim().equalsIgnoreCase(code)) {
						found = true;
						break;
					}
				}

				if (!found) {
					throw new FieldValueNotAllowedException("LimitGeneral - LimitCurrency", limit.getLimitGeneral()
							.getLimitCurrency(), (String[]) col.toArray(new String[0]));
				}
			}
			if (limit.getLimitGeneral().getApprovedLimit() == null) {
				throw new MandatoryFieldMissingException("LimitGeneral - ApprovedLimit");
			}
			validator.validateDoubleDigit(String.valueOf(limit.getLimitGeneral().getApprovedLimit()),
					"LimitGeneral - Approved Limit", true, 17, 2, true);

			if (limit.getLimitGeneral().getDrawingLimit() == null) {
				throw new MandatoryFieldMissingException("LimitGeneral - DrawingLimit");
			}
			validator.validateDoubleDigit(String.valueOf(limit.getLimitGeneral().getDrawingLimit()),
					"LimitGeneral - Drawing Limit", true, 17, 2, true);

			if (limit.getLimitGeneral().getLimitTenorBasis() != null) {
				validator.validateStdCode("LimitGeneral - LimitTenorBasis", "28", limit.getLimitGeneral()
						.getLimitTenorBasis().getStandardCodeValue(), true);
			}
		}
	}

	private void validateLimitsApprovedSecurityMap(Vector vector) throws EAIMessageValidationException {
		if ((vector == null) || (vector.size() == 0)) {
			return;
		}

		for (Iterator iter = vector.iterator(); iter.hasNext();) {
			LimitsApprovedSecurityMap limitSecMap = (LimitsApprovedSecurityMap) iter.next();

			if ((limitSecMap.getChangeIndicator() != null)
					&& limitSecMap.getChangeIndicator().equals(ICMSConstant.FALSE_VALUE)) {
				continue;
			}

			validator.validateString(limitSecMap.getCIFId(), "LimitsApprovedSecurityMap - CIFId", true, 1, 20);

			if (limitSecMap.getSubProfileId() == null) {
				throw new MandatoryFieldMissingException("LimitsApprovedSecurityMap - SubProfileId");
			}

			if (limitSecMap.getSubProfileId().longValue() < 1) {
				throw new MandatoryFieldMissingException("LimitsApprovedSecurityMap - SubProfileId");
			}

			validator.validateString(limitSecMap.getAANumber(), "LimitsApprovedSecurityMap - AA Number", true, 1, 35);

			if (((limitSecMap.getUpdateStatusIndicator() != null) && limitSecMap.getUpdateStatusIndicator().equals(
					String.valueOf(IEaiConstant.UPDATEINDICATOR)))
					|| ((limitSecMap.getUpdateStatusIndicator() != null) && limitSecMap.getUpdateStatusIndicator()
							.equals(String.valueOf(IEaiConstant.DELETEINDICATOR)))) {
				validator.validateDoubleDigit(String.valueOf(limitSecMap.getLimitsApprovedSecurityMapId()),
						"LimitsApprovedSecurityMap - CMSLimitSecMapId", true, 19, 0, false);
				validator.validateDoubleDigit(String.valueOf(limitSecMap.getCmsSecurityId()),
						"LimitsApprovedSecurityMap - CMSSecurityId", true, 19, 0, false);
				validator.validateDoubleDigit(String.valueOf(limitSecMap.getCmsLimitId()),
						"LimitsApprovedSecurityMap - CMSLimitId", true, 19, 0, false);
			}

			validator.validateString(limitSecMap.getLimitId(), "LimitsApprovedSecurityMap - LOSLimitId", true, 1, 20);

			validator.validateString(limitSecMap.getSecurityId(), "LimitsApprovedSecurityMap - LOSSecurityId", true, 1,
					20);

			if ((limitSecMap.getPercentPledge() == null) && (limitSecMap.getAmtPledge() == null)) {
				throw new MandatoryFieldMissingException("LimitsApprovedSecurityMap - PercentPledge / AmtPledge");
			}
			if ((limitSecMap.getPercentPledge() != null) && (limitSecMap.getAmtPledge() != null)) {
				throw new EitherFieldRequiredException("LimitsApprovedSecurityMap - PercentPledge",
						"LimitsApprovedSecurityMap - AmtPledge");
			}

			if (limitSecMap.getPercentPledge() != null) {
				validator.validateDoubleDigit(String.valueOf(limitSecMap.getPercentPledge()), "PercentPledge", true, 4, 7,
						false);
			}

			if (limitSecMap.getAmtPledge() != null && limitSecMap.getAmtPledge().doubleValue() > 0) {
				validator.validateString(limitSecMap.getAmtPledgeCcy(), "AmtPledgeCcy", true, 1, 3);
			}

			if (ArrayUtils.contains(this.sourceIdsDrawAmountOrPercentageMandatory, EAIMessageSynchronizationManager
					.getMessageSource())) {
				if ((limitSecMap.getPercentDraw() == null) && (limitSecMap.getAmtDraw() == null)) {
					throw new MandatoryFieldMissingException("LimitsApprovedSecurityMap - PercentDraw / AmtDraw");
				}
				if ((limitSecMap.getPercentDraw() != null) && (limitSecMap.getAmtDraw() != null)) {
					throw new EitherFieldRequiredException("LimitsApprovedSecurityMap - PercentDraw",
							"LimitsApprovedSecurityMap - AmtDraw");
				}

				if ((limitSecMap.getPercentDraw() != null)) {
					validator.validateDoubleDigit(limitSecMap.getPercentDraw().toString(),
							"LimitsApprovedSecurityMap - PercentDraw", true, 2, 9, false);
				}
				if ((limitSecMap.getAmtDraw() != null)) {
					validator.validateDoubleDigit(limitSecMap.getAmtDraw().toString(),
							"LimitsApprovedSecurityMap - AmtDraw", true, 13, 2, false);
				}
			}
		}

	}
}