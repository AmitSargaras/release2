package com.integrosys.cms.host.eai.limit.validator;

import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.EaiLimitProfileMessageException;
import com.integrosys.cms.host.eai.limit.bus.FacilityIncremental;
import com.integrosys.cms.host.eai.limit.bus.FacilityIslamicRentalRenewal;
import com.integrosys.cms.host.eai.limit.bus.FacilityIslamicSecurityDeposit;
import com.integrosys.cms.host.eai.limit.bus.FacilityMessage;
import com.integrosys.cms.host.eai.limit.bus.FacilityReduction;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.stp.common.IStpConstants;

/**
 * EAI Validator to validate the corporate facilities.
 * @author Chong Jun Yong
 * 
 */
public class CorporateFacilityValidator extends FacilityValidator {

	private final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private String[] corporateApplicationTypes;

	public void setCorporateApplicationTypes(String[] corporateApplicationTypes) {
		this.corporateApplicationTypes = corporateApplicationTypes;
	}

	public void validate(EAIMessage msg) throws EAIMessageValidationException {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();

		String aaType = aaMsgBody.getLimitProfile().getAAType();
		String aaLawType = aaMsgBody.getLimitProfile().getAALawType();
		String losAaNumber = aaMsgBody.getLimitProfile().getLOSAANumber();

		if (getFacilityNotRequiredAppTypeList().contains(aaType)) {
			return;
		}

		if (aaMsgBody.getLimits() == null || aaMsgBody.getLimits().isEmpty()) {
			return;
		}

		for (Iterator itr = aaMsgBody.getLimits().iterator(); itr.hasNext();) {
			Limits limit = (Limits) itr.next();
			if (!ArrayUtils.contains(this.corporateApplicationTypes, aaType)) {
				if (limit.getFacilityMessages() != null && !limit.getFacilityMessages().isEmpty()) {
					throw new InvalidCorporateFacilitiesException(losAaNumber, "FacilityMessage");
				}
				if (limit.getFacilityIslamicRentalRenewal() != null) {
					throw new InvalidCorporateFacilitiesException(losAaNumber, "FacilityIslamicRentalRenewal");
				}
				if (limit.getFacilityIslamicSecurityDeposit() != null) {
					throw new InvalidCorporateFacilitiesException(losAaNumber, "FacilityIslamicSecurityDeposit");
				}
				if (limit.getFacilityIncrementals() != null) {
					throw new InvalidCorporateFacilitiesException(losAaNumber, "FacilityIncremental");
				}
				if (limit.getFacilityReductions() != null) {
					throw new InvalidCorporateFacilitiesException(losAaNumber, "FacilityReduction");
				}
			}
			else {
				doValidateFacilityMessages(limit);
				doValidateFacilityIncrementals(limit);
				doValidateFacilityReductions(limit);
				if (IEaiConstant.AA_LAW_TYPE_ISL.equals(aaLawType)) {
					/* to check whether the loan is a corporate one */
					String islamicLoanType = getStpJdbcDao().getStpIslamicLoanType(
							limit.getLimitGeneral().getProductType().getStandardCodeValue(),
							limit.getLimitGeneral().getFacilityType().getStandardCodeValue());

					if (!IStpConstants.STP_ISLAMIC_LOAN_TYPE_CORPORATE.equals(islamicLoanType)) {
						if (limit.getFacilityIslamicRentalRenewal() != null) {
							throw new InvalidIslamicCorporateFacilitiesException(limit.getLimitGeneral()
									.getLOSLimitId(), "FacilityIslamicRentalRenewal");
						}
						if (limit.getFacilityIslamicSecurityDeposit() != null) {
							throw new InvalidIslamicCorporateFacilitiesException(limit.getLimitGeneral()
									.getLOSLimitId(), "FacilityIslamicSecurityDeposit");
						}
					}

					if (limit.getFacilityIslamicRentalRenewal() != null) {
						doValidateFacilityIslamicRentalRenewals(limit.getFacilityIslamicRentalRenewal());
					}

					if (limit.getFacilityIslamicSecurityDeposit() != null) {
						doValidateFacilityIslamicSecurityDeposits(limit.getFacilityIslamicSecurityDeposit());
					}
				}
			}
		}

	}

	protected void doValidateFacilityMessages(Limits limit) {
		if (limit.getFacilityMessages() == null || limit.getFacilityMessages().isEmpty()) {
			return;
		}

		for (Iterator itr = limit.getFacilityMessages().iterator(); itr.hasNext();) {
			FacilityMessage message = (FacilityMessage) itr.next();
			validator.validateString(message.getMessage(), "FacilityMessage.Message", true, 1, 258);
			validator.validateStdCodeAllowNull(message.getMessageTypeCode(), EAIMessageSynchronizationManager
					.getMessageSource(), "MESSAGE_TYPE");
			validator.validateStdCodeAllowNull(message.getMessageSeverityCode(), EAIMessageSynchronizationManager
					.getMessageSource(), "MESSAGE_SEVERITY");
		}
	}

	protected void doValidateFacilityIncrementals(Limits limit) {
		if (limit.getFacilityIncrementals() == null || limit.getFacilityIncrementals().isEmpty()) {
			return;
		}

		for (Iterator itr = limit.getFacilityIncrementals().iterator(); itr.hasNext();) {
			FacilityIncremental incremental = (FacilityIncremental) itr.next();
			if (incremental.getIncrementalNumber() != null) {
				validator.validateLong("FacilityIncremental - IncrementalNumber", incremental.getIncrementalNumber()
						.longValue(), 0, 999);
			}

			if (incremental.getAmountApplied() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - AmountApplied");
			}
			validator.validateDoubleDigit(incremental.getAmountApplied().toString(),
					"FacilityIncremental - AmountApplied", true, 15, 2, false);

			if (incremental.getIncrementalLimit() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - IncrementalLimit");
			}
			validator.validateDoubleDigit(incremental.getAmountApplied().toString(),
					"FacilityIncremental - IncrementalLimit", true, 15, 2, false);

			if (incremental.getOriginalLimit() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - OriginalLimit");
			}
			validator.validateDoubleDigit(incremental.getOriginalLimit().toString(),
					"FacilityIncremental - OriginalLimit", true, 15, 2, false);

			validator.validateStdCode(incremental.getLoanPurposeCode(), EAIMessageSynchronizationManager
					.getMessageSource(), "LOAN_PURPOSE");

			if (incremental.getApplicationDate() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - ApplicationDate");
			}

			if (incremental.getDateOfOffer() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - DateOfOffer");
			}

			if (incremental.getDateOfferAccepted() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - DateOfferAccepted");
			}

			if (incremental.getDateApproved() == null) {
				throw new MandatoryFieldMissingException("FacilityIncremental - DateApproved");
			}

			validator.validateStdCode(incremental.getApprovedBy(), EAIMessageSynchronizationManager.getMessageSource(),
					"APPROVED_BY");

			validator.validateStdCode(new StandardCode("FACILITY_STATUS", incremental.getFacilityStatus()),
					EAIMessageSynchronizationManager.getMessageSource(), "FACILITY_STATUS");

			if (ArrayUtils.contains(new String[] { "R", "C" }, incremental.getFacilityStatus())) {
				if (incremental.getCancelRejectCode() == null) {
					throw new MandatoryFieldMissingException("FacilityIncremental - CancelRejectCode");
				}
				validator.validateStdCode(incremental.getCancelRejectCode(), EAIMessageSynchronizationManager
						.getMessageSource(), "CANC_REJ_CODE");

				if (incremental.getCancelRejectDate() == null) {
					throw new MandatoryFieldMissingException("FacilityIncremental - CancelRejectDate");
				}
			}

			if (incremental.getSolicitorName() != null) {
				validator.validateString(incremental.getSolicitorName(), "FacilityIncremental - SolicitorName", false,
						0, 20);
			}

			validator.validateStdCode(incremental.getRequestReason(), EAIMessageSynchronizationManager
					.getMessageSource(), "REQUEST_REASON");

			validator.validateStdCode(incremental.getDocumentationStatus(), EAIMessageSynchronizationManager
					.getMessageSource(), "LMT_STATUS");

			validator.validateStdCodeAllowNull(incremental.getLawyerCode(), EAIMessageSynchronizationManager
					.getMessageSource(), "SOLICITOR");
		}
	}

	protected void doValidateFacilityReductions(Limits limit) {
		if (limit.getFacilityReductions() == null || limit.getFacilityReductions().isEmpty()) {
			return;
		}

		for (Iterator itr = limit.getFacilityReductions().iterator(); itr.hasNext();) {
			FacilityReduction reduction = (FacilityReduction) itr.next();
			if (reduction.getIncrementalNumber() != null) {
				validator.validateLong("FacilityReduction - IncrementalNumber", reduction.getIncrementalNumber()
						.longValue(), 0, 999);
			}

			if (reduction.getAmountApplied() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - AmountApplied");
			}
			validator.validateDoubleDigit(reduction.getAmountApplied().toString(), "FacilityReduction - AmountApplied",
					true, 15, 2, false);

			if (reduction.getIncrementalLimit() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - IncrementalLimit");
			}
			validator.validateDoubleDigit(reduction.getAmountApplied().toString(),
					"FacilityReduction - IncrementalLimit", true, 15, 2, false);

			if (reduction.getOriginalLimit() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - OriginalLimit");
			}
			validator.validateDoubleDigit(reduction.getOriginalLimit().toString(), "FacilityReduction - OriginalLimit",
					true, 15, 2, false);

			if (reduction.getApplicationDate() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - ApplicationDate");
			}

			if (reduction.getDateOfferAccepted() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - DateOfferAccepted");
			}

			if (reduction.getDateApproved() == null) {
				throw new MandatoryFieldMissingException("FacilityReduction - DateApproved");
			}

			validator.validateStdCode(reduction.getApprovedBy(), EAIMessageSynchronizationManager.getMessageSource(),
					"APPROVED_BY");

			validator.validateStdCode(new StandardCode("FACILITY_STATUS", reduction.getFacilityStatus()),
					EAIMessageSynchronizationManager.getMessageSource(), "FACILITY_STATUS");

			if (ArrayUtils.contains(new String[] { "R", "C" }, reduction.getFacilityStatus())) {
				if (reduction.getCancelRejectCode() == null) {
					throw new MandatoryFieldMissingException("FacilityReduction - CancelRejectCode");
				}
				validator.validateStdCode(reduction.getCancelRejectCode(), EAIMessageSynchronizationManager
						.getMessageSource(), "CANC_REJ_CODE");

				if (reduction.getCancelRejectDate() == null) {
					throw new MandatoryFieldMissingException("FacilityReduction - CancelRejectDate");
				}
			}

			if (reduction.getSolicitorName() != null) {
				validator.validateString(reduction.getSolicitorName(), "FacilityReduction - SolicitorName", false, 0,
						20);
			}

			validator.validateStdCode(reduction.getRequestReason(),
					EAIMessageSynchronizationManager.getMessageSource(), "REQUEST_REASON");

			validator.validateStdCode(reduction.getDocumentationStatus(), EAIMessageSynchronizationManager
					.getMessageSource(), "LMT_STATUS");

			validator.validateStdCodeAllowNull(reduction.getLawyerCode(), EAIMessageSynchronizationManager
					.getMessageSource(), "SOLICITOR");
		}
	}

	protected void doValidateFacilityIslamicRentalRenewals(FacilityIslamicRentalRenewal renewal) {
		if (renewal.getRenewalTerm() != null) {
			validator.validateLong("FacilityIslamicRentalRenewal.RenewalTerm", renewal.getRenewalTerm().longValue(), 0,
					99999);
		}
		validator.validateStdCodeAllowNull(renewal.getRenewalTermCode(), EAIMessageSynchronizationManager
				.getMessageSource(), "PRIME_REVIEW_TERM_CODE");

		if (renewal.getRenewalRate() == null) {
			throw new MandatoryFieldMissingException("FacilityIslamicRentalRenewal.RenewalRate");
		}
		validator.validateDoubleDigit(renewal.getRenewalRate().toString(), "FacilityIslamicRentalRenewal.RenewalRate",
				true, 4, 7, false);

		validator.validateStdCode(renewal.getPrimeRateNumber(), EAIMessageSynchronizationManager.getMessageSource(),
				"FAC_RATE");

		if (renewal.getPrimeVariance() != null) {
			validator.validateDoubleDigit(renewal.getPrimeVariance().toString(),
					"FacilityIslamicRentalRenewal.PrimeVariance", false, 4, 7, false);
		}

		if (renewal.getPrimeVarianceCode() != null) {
			validator.validateString(renewal.getPrimeVarianceCode(), "FacilityIslamicRentalRenewal.PrimeVarianceCode",
					false, 0, 1, new String[] { "+", "-" });
		}
	}

	protected void doValidateFacilityIslamicSecurityDeposits(FacilityIslamicSecurityDeposit deposit) {

		if (deposit.getNumberOfMonth() == null) {
			throw new MandatoryFieldMissingException("FacilityIslamicSecurityDeposit.NumberOfMonth");
		}
		validator.validateLong("FacilityIslamicSecurityDeposit.NumberOfMonth", deposit.getNumberOfMonth().longValue(),
				1, 99);

		if (deposit.getSecurityDeposit() != null) {
			validator.validateDoubleDigit(deposit.getSecurityDeposit().toString(),
					"FacilityIslamicSecurityDeposit.SecurityDeposit", false, 4, 7, false);
		}

		if (deposit.getFixSecurityDepositAmount() != null) {
			validator.validateDoubleDigit(deposit.getFixSecurityDepositAmount().toString(),
					"FacilityIslamicSecurityDeposit.FixSecurityDepositAmt", false, 13, 2, false);
		}

		if (deposit.getOriginalSecurityDepositAmount() != null) {
			validator.validateDoubleDigit(deposit.getOriginalSecurityDepositAmount().toString(),
					"FacilityIslamicSecurityDeposit.OriginalSecurityDepositAmt", false, 13, 2, false);
		}
	}

	/**
	 * Exception to be raised to indicate that a facility doesn't belong to
	 * corporate loan, but having extra tag.
	 */
	private class InvalidCorporateFacilitiesException extends EaiLimitProfileMessageException {

		private static final long serialVersionUID = -5864966855110766047L;

		public InvalidCorporateFacilitiesException(String losAANumber, String tagName) {
			super("LOS AA [" + losAANumber + "] is not corporate loan application but having tag [" + tagName + "]");
		}

		public String getErrorCode() {
			return "INV_CORP_LOAN";
		}
	}

	/**
	 * Exception to be raised to indicate that a facility doesn't belong to
	 * corporate islamic loan, but having extra tag.
	 */
	private class InvalidIslamicCorporateFacilitiesException extends EaiLimitProfileMessageException {

		private static final long serialVersionUID = 8983760847526902045L;

		public InvalidIslamicCorporateFacilitiesException(String losLimitId, String tagName) {
			super("LOS Limit [" + losLimitId + "] is not a islamic corporate loan but having tag [" + tagName + "]");
		}

		public String getErrorCode() {
			return "INV_ISL_CORP_LOAN";
		}
	}

}
