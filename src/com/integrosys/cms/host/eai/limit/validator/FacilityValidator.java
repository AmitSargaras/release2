package com.integrosys.cms.host.eai.limit.validator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.FacilityBBAVariPackage;
import com.integrosys.cms.host.eai.limit.bus.FacilityBNM;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.FacilityMultiTierFinancing;
import com.integrosys.cms.host.eai.limit.bus.FacilityOfficer;
import com.integrosys.cms.host.eai.limit.bus.FacilityRelationship;
import com.integrosys.cms.host.eai.limit.bus.IslamicFacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.Limits;
import com.integrosys.cms.host.eai.support.ValidationMandatoryFieldFactory;
import com.integrosys.cms.host.stp.bus.IStpTransJdbc;
import com.integrosys.cms.host.stp.common.IStpConstants;

/**
 * Validator to check against facility chunk in the AA message body.
 * 
 * @author Thurein
 * @author Chong Jun Yong
 * @since 23-Nov-2008
 * 
 */
public class FacilityValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	/** used in islamic facility validation **/
	private static final String[] GPP_PAY_MODE = new String[] { "Y", "N", "I", "E" };

	private static final String[] REFUND_FUL_REL = new String[] { "P", "" };

	private static final String[] FUL_REL = new String[] { "F", "" };

	private static final String[] VALID_CHANGE_IND = new String[] { "Y", "N", "" };

	private static final String[] VALID_UPDATE_IND = new String[] { "I", "U", "D", "" };

	private static final String TIER_TERM_CODE = "28";

	/**
	 * List of application type, which are not including the facility master
	 * information. For those application, processing of the facility master
	 * need to be skipped.
	 */
	private List facilityNotRequiredAppTypeList;

	private IStpTransJdbc stpJdbcDao;

	private ValidationMandatoryFieldFactory validationMandatoryFieldFactory;

	public List getFacilityNotRequiredAppTypeList() {
		return facilityNotRequiredAppTypeList;
	}

	public void setFacilityNotRequiredAppTypeList(List facilityNotRequiredAppTypeList) {
		this.facilityNotRequiredAppTypeList = facilityNotRequiredAppTypeList;
	}

	public void setStpJdbcDao(IStpTransJdbc stpJdbcDao) {
		this.stpJdbcDao = stpJdbcDao;
	}

	protected IStpTransJdbc getStpJdbcDao() {
		return this.stpJdbcDao;
	}

	public void setValidationMandatoryFieldFactory(ValidationMandatoryFieldFactory validationMandatoryFieldFactory) {
		this.validationMandatoryFieldFactory = validationMandatoryFieldFactory;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		AAMessageBody body = (AAMessageBody) scimsg.getMsgBody();

		if (body.getLimitProfile() == null) {
			throw new MandatoryFieldMissingException("MsgBody - LimitProfile");
		}

		String aaLawType = body.getLimitProfile().getAALawType();
		String aaType = body.getLimitProfile().getAAType();

		if (getFacilityNotRequiredAppTypeList().contains(aaType)) {
			return;
		}

		Vector limits = body.getLimits();

		if (limits == null) {
			return;
		}

		String source = scimsg.getMsgHeader().getSource();

		for (Iterator limitsIter = limits.iterator(); limitsIter.hasNext();) {
			Limits limit = (Limits) limitsIter.next();

			// if aa type is CC (Credit card) there is no facility info in the
			// xml
			if (!getFacilityNotRequiredAppTypeList().contains(aaType)) {
				validateFacilityMaster(limit.getFacilityMaster(), source);
			}

			validateFacilityBNM(limit.getFacilityBNM(), source);

			validateFacilityOfficer(limit.getFacilityOfficer(), source);

			validateFacilityRelationship(limit.getFacilityRelationship(), source);

			if (aaLawType.equals(IEaiConstant.AA_LAW_TYPE_ISL)) {
				doIslamicRelatedValidation(limit, source);
			}
			else if (aaLawType.equals(IEaiConstant.AA_LAW_TYPE_CON)) {
				validateFacilityMultiTierFinancing(limit.getFacilityMultiTierFinancings(), source);
			}
		}
	}

	private void validateFacilityBNM(FacilityBNM facilityBNM, String source) {
		if (facilityBNM == null) {
			return;
		}

		validator.validateStdCode(facilityBNM.getBNMIndustryCode(), source, "BNM_INDUSTRY");

		validator.validateStdCode(facilityBNM.getBNMSectorCode(), source, "8");

		validator.validateStdCode(facilityBNM.getBNMStateCode(), source, "BNM_STATE");

		validator.validateStdCode(facilityBNM.getBNMBumiNRCCCode(), source, "BNM_BUMI_NRCC");

		validator.validateStdCode(facilityBNM.getBNMSmallScaleCode(), source, "BNM_SML_SCALE");

		validator.validateStdCode(facilityBNM.getBNMPrescribedRateCode(), source, "BNM_PRSCB_RT");

		validator.validateStdCode(facilityBNM.getBNMRelationshipCode(), source, "BNM_RELSHIP");

		validator.validateMandatoryFieldForString("BNM Exempt", facilityBNM.getBNMExempt());

		if (facilityBNM.getBNMExempt().equals("Y")) {
			if (facilityBNM.getBNMExemptCode() == null) {
				throw new MandatoryFieldMissingException("FacilityBNM - BNMExemptCode");
			}

			validator.validateStdCode(facilityBNM.getBNMExemptCode(), source, "BNM_EXMPT_CODE");
		}

		validator.validateStdCode(facilityBNM.getBNMPurposeCode(), source, "BNM_PURPOSE");

		validator.validateStdCodeAllowNull(facilityBNM.getBaselIRB(), source, "BASEL_IRB");

		validator.validateStdCodeAllowNull(facilityBNM.getBaselSAFinalised(), source, "BASEL_SA_FINALIST");

		validator.validateStdCodeAllowNull(facilityBNM.getBaselSAConcept(), source, "BASEL_SA_CONCEPT");

		if (facilityBNM.getUpdateStatusIndicator() != null) {
			validator.validateString(facilityBNM.getUpdateStatusIndicator(), "Facility BNM Update Status Indicator",
					false, 0, 1);

			validator.validateString(facilityBNM.getChangeIndicator(), "Facility BNM Change Indicator", false, 0, 1);

			validator.validateString(facilityBNM.getUpdateStatusIndicator(), "Update Status Indicator", false, 0, 1,
					new String[] { "I", "U", "D" });
		}
	}

	private void validateFacilityMaster(FacilityMaster facilityMst, String source) {
		List cancleRejectStatus = Arrays.asList(new String[] { "C", "R" });

		if (facilityMst == null) {
			throw new MandatoryFieldMissingException("Facility Master");
		}
		if (facilityMst.getFinancedAmt() == null) {
			throw new MandatoryFieldMissingException("Facility Master - Financed Amount");
		}
		validator.validateDoubleDigit(facilityMst.getFinancedAmt().toString(), "Facility Master - Financed Amount",
				true, 17, 2, false);

		validator.validateString(facilityMst.getACFNo(), "Facility Master - ACF No", false, 0, 20);

		validator.validateStdCode(facilityMst.getLoanPurposeCode(), source, "LOAN_PURPOSE");

		validator.validateStdCodeAllowNull(facilityMst.getProductPackageCode(), source, "PRODUCT_PACKAGE");

		validator.validateStdCodeAllowNull(facilityMst.getCancelRejectCode(), source, "CANC_REJ_CODE");

		if (((facilityMst.getCancelRejectCode() != null) && (facilityMst.getCancelRejectCode().getStandardCodeValue() != null))) {
			validator.validateDate(facilityMst.getCancelRejectDate(), "Facility Master - Cancel Reject Date", true);
		}

		validator.validateStdCode(facilityMst.getOfficer(), source, "OFFICER");

		if ((facilityMst.getLimitStatus() == null)) {
			throw new MandatoryFieldMissingException("Facility Master - Limit Status");
		}
		validator.validateStdCode(facilityMst.getLimitStatus(), source, "LMT_STATUS");

		validator.validateString(facilityMst.getFacilityStatusEntryCode(), "Facility Master - Facility Status", true,
				1, 40);

		StandardCode facilityStatus = new StandardCode();
		facilityStatus.setStandardCodeNumber("FACILITY_STATUS");
		facilityStatus.setStandardCodeValue(facilityMst.getFacilityStatusEntryCode());
		validator.validateStdCode(facilityStatus, source, "FACILITY_STATUS");

		if (!cancleRejectStatus.contains(facilityMst.getFacilityStatusEntryCode())) {
			validator.validateDate(facilityMst.getOfferAcceptedDate(), "Facility Master - Offer Accepted Date",
					this.validationMandatoryFieldFactory.isMandatoryField(source, FacilityMaster.class,
							"offerAcceptedDate"));

			validator.validateDate(facilityMst.getApproveDate(), "Facility Master - Approved Date", true);

			validator.validateDate(facilityMst.getOfferDate(), "Facility Master - Offer Date",
					this.validationMandatoryFieldFactory.isMandatoryField(source, FacilityMaster.class, "offerDate"));

			if ((facilityMst.getApprovedBy() == null)) {
				throw new MandatoryFieldMissingException("Facility Master - Approved By");
			}
			validator.validateStdCode(facilityMst.getApprovedBy(), source, "APPROVED_BY");
		}

		/*
		 * vH.validateMandatoryFieldForDouble("Facility Master - Spread",
		 * facilityMst.getSpread());
		 * 
		 * if ((facilityMst.getSpread() >= 100 || facilityMst.getSpread() == 0))
		 * { throw new LimitMessageValidationException(" Spread : " +
		 * facilityMst.getSpread() + " , is invalid."); }
		 */
		if (facilityMst.getRateType() != null) {
			validator.validateStdCode(facilityMst.getRateType(), source, "FAC_RATE");
		}

		validator.validateStdCode(facilityMst.getApplicationSource(), source, "APPLICATION_SOURCE");

		validator.validateStdCodeAllowNull(facilityMst.getDealerLPPCode(), source, "DEALER");

		validator.validateDate(facilityMst.getInstructedDate(), "Facility Master - Instructed Date", false);

		validator.validateStdCode(facilityMst.getDepartmentCode(), source, "DEPARTMENT");

		validator.validateString(facilityMst.getSolicitorName(), "Facility Master - Solicitor Name", false, 0, 255);

		validator.validateStdCodeAllowNull(facilityMst.getLawyerCode(), source, "SOLICITOR");

		if (facilityMst.getOdExcessRateVar() != null) {
			validator.validateDoubleDigit(String.valueOf(facilityMst.getOdExcessRateVar()),
					"Facility Master - OD Excess Rate", false, 2, 9, true);
		}

		validator.validateString(facilityMst.getOdExcessRateVarCode(), "Facility Master - OD Excess Rate Var Code",
				false, 0, 1);

		validator.validateMandatoryFieldForString("Facility Master - Main Facility Ind", facilityMst
				.getMainFacilityInd());

		validator.validateString(facilityMst.getMainFacilityInd(), "Facility Master - Main Facility Ind", true, 1, 1,
				new String[] { "Y", "N" });

		if ((facilityMst.getMainFacilityInd() != null)
				&& facilityMst.getMainFacilityInd().equals(ICMSConstant.FALSE_VALUE)) {
			validator.validateString(facilityMst.getMainFacilityAANo(), "Facility Master - MainFacilityAANo", true, 1,
					20);

			validator.validateString(facilityMst.getMainFacilityCode(), "Facility Master - MainFacilityCode", true, 1,
					3);
		}

		validator.validateMandatoryFieldForString("Facility Master - Standby Line ID", facilityMst.getStandbyLine());
		validator.validateString(facilityMst.getMainFacilityInd(), "Facility Master - Standby Line ID", true, 1, 1,
				new String[] { "Y", "N" });

		validator.validateDate(facilityMst.getApplicationDate(), "Facility Master - Application Date", true);

		if (facilityMst.getInterestRate() == null) {
			throw new MandatoryFieldMissingException("Facility Master - Interest Rate");
		}
		validator.validateDoubleDigit(String.valueOf(facilityMst.getInterestRate()), "Facility Master - Interest Rate",
				true, 2, 9, false);

		validator.validateString(facilityMst.getUpdateStatusIndicator(), "Facility Master - Update Status Indicator",
				false, 0, 1);

		validator.validateString(facilityMst.getChangeIndicator(), "Facility Master - Change Indicator", false, 0, 1);
	}

	private void validateFacilityOfficer(Vector facilityOff, String source) {
		if (((facilityOff == null) || (facilityOff.size() < 1))) {
			return;
		}

		for (Iterator iter = facilityOff.iterator(); iter.hasNext();) {
			FacilityOfficer facOff = (FacilityOfficer) iter.next();

			if (((facOff.getUpdateStatusIndicator() != null) && facOff.getUpdateStatusIndicator().equals(
					String.valueOf(IEaiConstant.UPDATEINDICATOR)))
					|| ((facOff.getUpdateStatusIndicator() != null) && facOff.getUpdateStatusIndicator().equals(
							String.valueOf(IEaiConstant.DELETEINDICATOR)))) {
				validator.validateMandatoryFieldForLong("Facility Officer  - CMS Officer ID", facOff.getCMSOfficerId());
			}

			validator.validateStdCode(facOff.getRelationshipCode(), source, "OFFICER_RELATIONSHIP");

			validator.validateStdCode(facOff.getOfficer(), source, "OFFICER");

			validator.validateStdCode(facOff.getOfficerType(), source, "OFFICER_TYPE");

			validator.validateString(facOff.getUpdateStatusIndicator(), "Update Status Indicator", false, 0, 1);

			validator.validateString(facOff.getChangeIndicator(), "Change Indicator", false, 0, 1);
		}

	}

	private void validateFacilityRelationship(Vector facilityRel, String source) {

		if (((facilityRel == null) || (facilityRel.size() < 1))) {
			return;
		}

		for (Iterator iter = facilityRel.iterator(); iter.hasNext();) {
			FacilityRelationship facRel = (FacilityRelationship) iter.next();

			if (((facRel.getUpdateStatusIndicator() != null) && facRel.getUpdateStatusIndicator().equals(
					String.valueOf(IEaiConstant.UPDATEINDICATOR)))
					|| ((facRel.getUpdateStatusIndicator() != null) && facRel.getUpdateStatusIndicator().equals(
							String.valueOf(IEaiConstant.DELETEINDICATOR)))) {
				validator.validateMandatoryFieldForLong("Facility Relationship - CMS Facility Relationship ID", facRel
						.getCMSFacilityRelationshipID());
			}

			validator.validateString(facRel.getCIFId(), "Facility Relationship - CIF ID", true, 1, 20);

			if (facRel.getAccountRelationship() == null) {
				throw new MandatoryFieldMissingException("Facility Relationship - Account Relationship");
			}

			validator.validateStdCode(facRel.getAccountRelationship(), source, "RELATIONSHIP");

			if (facRel.getAccountRelationship().getStandardCodeValue() == "GO") {
				if (facRel.getGuaranteeAmt() == null) {
					throw new MandatoryFieldMissingException("Facility Relationship - Gurantee Amount");
				}
				validator.validateMandatoryFieldForDouble("Facility Relationship - Gurantee Amount", facRel
						.getGuaranteeAmt().doubleValue());
			}

			if (facRel.getAddressSeqNum() == null) {
				throw new MandatoryFieldMissingException("Facility Relationship - Address Seq Num");
			}
			validator.validateMandatoryFieldForLong("Facility Relationship - Address Seq Num", facRel
					.getAddressSeqNum().longValue());

			if (facRel.getAddressSeqNum().longValue() == 0) {
				throw new MandatoryFieldMissingException("Facility Relationship - Address Sequence Number");
			}

			validator.validateString(facRel.getUpdateStatusIndicator(),
					"Facility Relationship - Update Status Indicator", false, 0, 1);

			validator.validateString(facRel.getChangeIndicator(), "Facility Relationship - Change Indicator", false, 0,
					1);

		}
	}

	private void doIslamicRelatedValidation(Limits limit, String source) {

		String productType = limit.getLimitGeneral().getProductType().getStandardCodeValue();

		String islamicLoanType = this.stpJdbcDao.getStpIslamicLoanType(productType, limit.getLimitGeneral()
				.getFacilityType().getStandardCodeValue());

		if (IStpConstants.STP_ISLAMIC_LOAN_TYPE_MASTER.equals(islamicLoanType)
				&& (limit.getIslamicFacilityMaster() == null)) {
			throw new MandatoryFieldMissingException("IslamicFacilityMaster");
		}
		else if (limit.getIslamicFacilityMaster() != null) {
			validateIslamicFacilityMaster(limit.getIslamicFacilityMaster(), source);
		}

		if (IStpConstants.STP_ISLAMIC_LOAN_TYPE_BBA.equals(islamicLoanType)
				&& (limit.getFacilityBBAVariPackage() == null)) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage");
		}
		else if (limit.getFacilityBBAVariPackage() != null) {
			validateFacilityBBAVariPackage(limit.getFacilityBBAVariPackage(), source);
		}

		if ((IStpConstants.STP_ISLAMIC_LOAN_TYPE_MASTER.equals(islamicLoanType) || IStpConstants.STP_ISLAMIC_LOAN_TYPE_BBA
				.equals(islamicLoanType))
				&& (limit.getFacilityMultiTierFinancings() == null)) {
			throw new MandatoryFieldMissingException("FacilityMultiTierFinancing");
		}
		else if (limit.getFacilityMultiTierFinancings() != null) {
			validateFacilityMultiTierFinancing(limit.getFacilityMultiTierFinancings(), source);
		}

	}

	private void validateIslamicFacilityMaster(IslamicFacilityMaster iMaster, String source) {
		List GPPPaymentMandatoryValues = Arrays.asList(new String[] { "Y", "N", "E" });
		String[] validRefundGppProfit = new String[] { "E", "F", "P", "" };

		validator.validateString(iMaster.getGPPPaymentMode(), "IslamicFacilityMaster-GPPPaymentMode", true, 1, 1,
				GPP_PAY_MODE);

		if (GPPPaymentMandatoryValues.contains(iMaster.getGPPPaymentMode())) {

			if (iMaster.getGPPTerm() == null) {
				throw new MandatoryFieldMissingException("IslamicFacilityMaster - GPPTerm");
			}

			validator.validateString(iMaster.getGPPTermCode(), "IslamicFacilityMaster-GPPTermCode", true, 1, 1);

			validator.validateString(iMaster.getGPPCalcMethod(), "IslamicFacilityMaster-GPPCalcMethod", true, 1, 1);

		}
		else {
			validator.validateString(iMaster.getGPPTermCode(), "IslamicFacilityMaster-GPPTermCode", false, 0, 1);

			validator.validateString(iMaster.getGPPCalcMethod(), "IslamicFacilityMaster-GPPCalcMethod", false, 0, 1);
		}

		validator.validateString(iMaster.getRefundGppProfit(), "IslamicFacilityMaster-RefundGppProfit", false, 0, 1,
				validRefundGppProfit);

		if (iMaster.getCustomerInterestRate() == null) {
			throw new MandatoryFieldMissingException("IslamicFacilityMaster-Customer Interest Rate");
		}
		validator.validateDoubleDigit(iMaster.getCustomerInterestRate().toString(),
				"IslamicFacilityMaster-Customer Interest Rate", true, 4, 7, true);

		validator.validateString(iMaster.getCompoundingMethod(), "IslamicFacilityMaster-Compounding Method", true, 1,
				1, new String[] { ICMSConstant.TRUE_VALUE, ICMSConstant.FALSE_VALUE });

		if (iMaster.getCompoundingMethod().equals(ICMSConstant.TRUE_VALUE)) {
			validator.validateDate(iMaster.getDateStopCompounding(), "IslamicFacilityMaster-DateStopCompounding", true);
		}
		else {
			validator
					.validateDate(iMaster.getDateStopCompounding(), "IslamicFacilityMaster-DateStopCompounding", false);
		}

		validator.validateString(iMaster.getFulrelProfitCalMehod(), "IslamicFacilityMaster-Fulrel Profit CalMehod",
				false, 0, 1, FUL_REL);

		if ((iMaster.getFulrelProfitCalMehod() != null) && iMaster.getFulrelProfitCalMehod().equals("F")) {
			if (iMaster.getRefundFulRelProfit() == null) {
				throw new MandatoryFieldMissingException("IslamicFacilityMaster - RefundFulRelProfit");
			}
		}
		else {
			validator.validateString(iMaster.getRefundFulRelProfit(), "IslamicFacilityMaster-RefundFulRelProfit",
					false, 0, 1, REFUND_FUL_REL);
		}

		validator.validateString(iMaster.getExcCMPInPMTAMT(), "IslamicFacilityMaster-ExcCMPInPMTAMT", false, 0, 1,
				new String[] { ICMSConstant.TRUE_VALUE, ICMSConstant.FALSE_VALUE, "" });

		if (iMaster.getSellingPrice() == null) {
			throw new MandatoryFieldMissingException("IslamicFacilityMaster-SellingPrice");
		}
		validator.validateMandatoryFieldForDouble("IslamicFacilityMaster-SellingPrice", iMaster.getSellingPrice()
				.doubleValue());

		if (iMaster.getTotalGPPAmt() == null) {
			throw new MandatoryFieldMissingException("IslamicFacilityMaster-TotalGPPAmt");
		}
		validator.validateMandatoryFieldForDouble("IslamicFacilityMaster-TotalGPPAmt", iMaster.getTotalGPPAmt()
				.doubleValue());

		validator.validateString(iMaster.getChangeIndicator(), "IslamicFacilityMaster-ChangeIndicator", false, 0, 1,
				VALID_CHANGE_IND);

		validator.validateString(iMaster.getUpdateStatusIndicator(), "IslamicFacilityMaster-UpdateStatusIndicator",
				false, 0, 1, VALID_UPDATE_IND);

	}

	private void validateFacilityBBAVariPackage(FacilityBBAVariPackage bbaPackage, String source) {
		String[] validRebateMethod = new String[] { "E", "F", "P", "" };
		String[] validGPPCalcMethod = new String[] { "A", "D", "F", "" };

		if (bbaPackage.getCustProfRate() == null) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage - CustProfRate");
		}

		validator.validateDoubleDigit(bbaPackage.getCustProfRate().toString(), "FacilityBBAVariPackage-CustProfRate",
				true, 4, 7, true);

		validator.validateString(bbaPackage.getRebateMethod(), "FacilityBBAVariPackage-RebateMethod", false, 0, 1,
				validRebateMethod);

		validator.validateString(bbaPackage.getGPPPaymentMode(), "FacilityBBAVariPackage-GPPPaymentMode", true, 1, 1,
				GPP_PAY_MODE);

		validator.validateString(bbaPackage.getGPPCalcMethod(), "FacilityBBAVariPackage-GPPCalcMethod", true, 0, 1,
				validGPPCalcMethod);

		if (bbaPackage.getGPPTerm() == null) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage - GPPTerm");
		}

		validator.validateString(bbaPackage.getGPPTermCode(), "FacilityBBAVariPackage-GPPTermCode", true, 1, 1);

		validator.validateString(bbaPackage.getFullReleaseProfit(), "FacilityBBAVariPackage-FullReleaseProfit", false,
				0, 1, new String[] { ICMSConstant.TRUE_VALUE, ICMSConstant.FALSE_VALUE, "" });

		validator.validateString(bbaPackage.getFullReleaseProfitCalcMethod(),
				"FacilityBBAVariPackage-FullReleaseProfitCalcMethod", false, 0, 1, FUL_REL);

		validator.validateString(bbaPackage.getRefundFullReleaseProfit(),
				"FacilityBBAVariPackage-RefundFullReleaseProfit", false, 0, 1, REFUND_FUL_REL);

		if (bbaPackage.getInstallment() == null) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage - Installment");
		}

		if (bbaPackage.getFinalPaymentAmt() == null) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage - FinalPaymentAmt");
		}

		if (bbaPackage.getTotalGPPAmt() == null) {
			throw new MandatoryFieldMissingException("FacilityBBAVariPackage - TotalGPPAmt");
		}

		validator.validateDoubleDigit(bbaPackage.getInstallment().toString(), "FacilityBBAVariPackage-Installment",
				true, 13, 2, true);

		validator.validateDoubleDigit(bbaPackage.getFinalPaymentAmt().toString(),
				"FacilityBBAVariPackage-FinalPaymentAmt", true, 13, 2, true);

		validator.validateDoubleDigit(bbaPackage.getTotalGPPAmt().toString(), "FacilityBBAVariPackage-TotalGPPAmt",
				true, 13, 2, true);

		validator.validateString(bbaPackage.getChangeIndicator(), "FacilityBBAVariPackage-ChangeIndicator", false, 0,
				1, VALID_CHANGE_IND);

		validator.validateString(bbaPackage.getUpdateStatusIndicator(), "FacilityBBAVariPackage-UpdateStatusIndicator",
				false, 0, 1, VALID_UPDATE_IND);

	}

	private void validateFacilityMultiTierFinancing(Vector MultiFinancings, String source) {

		if (MultiFinancings == null) {
			return;
		}

		for (Iterator iter = MultiFinancings.iterator(); iter.hasNext();) {
			FacilityMultiTierFinancing MultiFinancing = (FacilityMultiTierFinancing) iter.next();

			if (MultiFinancing == null) {
				return;
			}

			if (MultiFinancing.getTierTerm() == null) {
				throw new MandatoryFieldMissingException("FacilityMultiTierFinancing - TierTerm");
			}

			validator.validateStdCode(MultiFinancing.getTierTermCode(), source, TIER_TERM_CODE);

			if (MultiFinancing.getRate() == null) {
				throw new MandatoryFieldMissingException("FacilityMultiTierFinancing - Rate");
			}

			validator.validateDoubleDigit(MultiFinancing.getRate().toString(), "FacilityMultiTierFinancing-Rate", true,
					4, 7, true);

			validator.validateString(MultiFinancing.getChangeIndicator(), "FacilityMultiTierFinancing-ChangeIndicator",
					false, 0, 1, VALID_CHANGE_IND);

			validator.validateString(MultiFinancing.getUpdateStatusIndicator(),
					"FacilityMultiTierFinancing-UpdateStatusIndicator", false, 0, 1, VALID_UPDATE_IND);
		}
	}
}
