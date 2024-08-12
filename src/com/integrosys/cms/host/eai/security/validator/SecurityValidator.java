/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.host.eai.security.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.EitherFieldRequiredException;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityJdbc;
import com.integrosys.cms.host.eai.security.bus.SecurityLocation;
import com.integrosys.cms.host.eai.support.ValidationMandatoryFieldFactory;

/**
 * Message validator for security on general info, ie instance of
 * <tt>ApprovedSecurity</tt>.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 15.11.2003
 */
public class SecurityValidator implements IEaiMessageValidator {

	protected static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	protected ISecurityJdbc securityJdbc;

	protected ValidationMandatoryFieldFactory validationMandatoryFieldFactory;

	private String[] sourceIdsLinkToCgcGuaranteeApplicable;

	private String[] securitySubtypeIdsLinkToCgcGuaranteeApplicable;

	private String[] securityTypeIdsLinkToCgcGuaranteeApplicable;

	private String[] securitySubtypeIdsCustodianInfoMandatory;

	private String[] securityTypeIdsCustodianInfoMandatory;

	private String[] securityTypeIdsCurrencyMandatory;

	/**
	 * Security Type ids which general info is applicable, such as Security
	 * Expiry Date, Legal Enforcebility, Borrower Dependency on Collateral
	 */
	private String[] securityTypeIdsGeneralInfoApplicable;

	public void setSecurityJdbc(ISecurityJdbc securityJdbc) {
		this.securityJdbc = securityJdbc;
	}

	public void setValidationMandatoryFieldFactory(ValidationMandatoryFieldFactory validationMandatoryFieldFactory) {
		this.validationMandatoryFieldFactory = validationMandatoryFieldFactory;
	}

	public void setSourceIdsLinkToCgcGuaranteeApplicable(String[] sourceIdsLinkToCgcGuaranteeApplicable) {
		this.sourceIdsLinkToCgcGuaranteeApplicable = sourceIdsLinkToCgcGuaranteeApplicable;
	}

	public void setSecuritySubtypeIdsLinkToCgcGuaranteeApplicable(
			String[] securitySubtypeIdsLinkToCgcGuaranteeApplicable) {
		this.securitySubtypeIdsLinkToCgcGuaranteeApplicable = securitySubtypeIdsLinkToCgcGuaranteeApplicable;
	}

	public void setSecurityTypeIdsLinkToCgcGuaranteeApplicable(String[] securityTypeIdsLinkToCgcGuaranteeApplicable) {
		this.securityTypeIdsLinkToCgcGuaranteeApplicable = securityTypeIdsLinkToCgcGuaranteeApplicable;
	}

	public void setSecuritySubtypeIdsCustodianInfoMandatory(String[] securitySubtypeIdsCustodianInfoMandatory) {
		this.securitySubtypeIdsCustodianInfoMandatory = securitySubtypeIdsCustodianInfoMandatory;
	}

	public void setSecurityTypeIdsCustodianInfoMandatory(String[] securityTypeIdsCustodianInfoMandatory) {
		this.securityTypeIdsCustodianInfoMandatory = securityTypeIdsCustodianInfoMandatory;
	}

	public void setSecurityTypeIdsCurrencyMandatory(String[] securityTypeIdsCurrencyMandatory) {
		this.securityTypeIdsCurrencyMandatory = securityTypeIdsCurrencyMandatory;
	}

	public void setSecurityTypeIdsGeneralInfoApplicable(String[] securityTypeIdsGeneralInfoApplicable) {
		this.securityTypeIdsGeneralInfoApplicable = securityTypeIdsGeneralInfoApplicable;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();

		String secType = security.getSecurityType().getStandardCodeValue();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();

		validator.rejectIfNull(msg.getSecurityDetail(), "SecurityDetail");

		String[] nonNullFieldNames = getNonNullFieldNames(msg);
		if (nonNullFieldNames.length > 1) {
			throw new EitherFieldRequiredException(nonNullFieldNames);
		}

		validator.validateString(security.getLOSSecurityId(), "SecurityDetail - LOSSecurityId", true, 1, 20);

		boolean isReferenceNoteMandatory = this.validationMandatoryFieldFactory.isMandatoryField(source,
				ApprovedSecurity.class, "securityReferenceNote");
		validator.validateString(security.getSecurityReferenceNote(), "SecurityDetail - Collateral Name",
				isReferenceNoteMandatory, 1, 40);

		validator.validateStdCode(security.getSourceSecurityType(), source, "SOURCE_SEC_TYPE");

		validator.validateStdCode(security.getSecurityType(), source, "31");

		validator.validateStdCode(security.getSecuritySubType(), source, "54");

		if (ArrayUtils.contains(this.securityTypeIdsCurrencyMandatory, secType)) {
			validator.validateString(security.getOriginalCurrency(), "SecurityDetail - OriginalCurrency", true, 3, 3);
		}

		if (StringUtils.isEmpty(security.getCurrency())) {
			security.setCurrency(security.getOriginalCurrency());
		}

		validateSecurityLocation(security.getSecurityLocation(), source);

		if (ArrayUtils.contains(this.securityTypeIdsCustodianInfoMandatory, secType)
				|| ArrayUtils.contains(this.securitySubtypeIdsCustodianInfoMandatory, secSubType)) {

			validator.validateString(security.getCustodianType(), "SecurityDetail - CustodianType", true, 1, 1,
					EaiConstantCla.getAllowedValuesCustodianTypes());

			validator.validateString(security.getCustodian(), "SecurityDetail - Custodian", true, 1, 100);
		}

		if (ArrayUtils.contains(this.securityTypeIdsGeneralInfoApplicable, secType)) {
			validator.validateString(security.getSecurityExpiryDate(), "SecurityDetail - SecurityExpiryDate", false, 0,
					8);

			validator.validateString(security.getLegalEnforcebilityFlag(), "SecurityDetail - LegalEnforcebilityFlag",
					false, 0, 1, EaiConstantCla.getAllowedValuesYesNoNa());

			if (ICMSConstant.TRUE_VALUE.equals(security.getLegalEnforcebilityFlag())) {
				validator.validateDate(security.getLegalEnforcebilityDate(), "SecurityDetail - LegalEnforcebilityDate",
						true);
			}

			validator.validateString(security.getBorrowerDependencyCol(), "SecurityDetail - BorrowerDependencyCol",
					false, 0, 1, EaiConstantCla.getAllowedValuesYesNo());
		}

		validator.validateString(security.getToBeDischargedInd(), "SecurityDetail - ToBeDischargedInd", false, 0, 1,
				EaiConstantCla.getAllowedValuesYesNo());

		if ((ArrayUtils.contains(this.securitySubtypeIdsLinkToCgcGuaranteeApplicable, secSubType) || ArrayUtils
				.contains(this.securityTypeIdsLinkToCgcGuaranteeApplicable, secType))
				&& ArrayUtils.contains(this.sourceIdsLinkToCgcGuaranteeApplicable, source)) {
			validator.validateString(security.getLinkToCgcGuarantee(), "SecurityDetail - LinkToCGCGuarantee", false, 0,
					1, EaiConstantCla.getAllowedValuesYesNo());
		}

		validator.validateString(security.getUpdateStatusIndicator(), "SecurityDetail - UpdateStatusIndicator", false,
				0, 1, EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

		validator.validateString(security.getChangeIndicator(), "SecurityDetail - ChangeIndicator", false, 0, 1,
				EaiConstantCla.getAllowedValuesYesNo());
	}

	private String[] getNonNullFieldNames(SecurityMessageBody msg) {
		List nonNullFieldNames = new ArrayList();
		if (msg.getAssetDetail() != null) {
			nonNullFieldNames.add("AssetDetail");
		}
		if (msg.getCashDetail() != null) {
			nonNullFieldNames.add("CashDetail");
		}
		if (msg.getCleanDetail() != null) {
			nonNullFieldNames.add("CleanDetail");
		}
		if (msg.getDocumentationDetail() != null) {
			nonNullFieldNames.add("DocumentationDetail");
		}
		if (msg.getInsuranceDetail() != null) {
			nonNullFieldNames.add("InsuranceDetail");
		}
		if (msg.getGuaranteeDetail() != null) {
			nonNullFieldNames.add("GuaranteeDetail");
		}
		if (msg.getMarketableSecDetail() != null) {
			nonNullFieldNames.add("MarketableSecDetail");
		}
		if (msg.getOtherDetail() != null) {
			nonNullFieldNames.add("OtherDetail");
		}
		if (msg.getPropertyDetail() != null) {
			nonNullFieldNames.add("PropertyDetail");
		}

		return (String[]) nonNullFieldNames.toArray(new String[0]);
	}

	protected void validateIndicator(String commonUpdateStatusInd, String commonChangeInd,
			String detailUpdateStatusInd, String detailChangeInd) throws EAIMessageValidationException {
		if (!commonChangeInd.equals(detailChangeInd)) {
			throw new ChangeUpdateStatusIndicatorNotMatchException("Change Indicator", commonChangeInd, detailChangeInd);
		}

		if (!commonUpdateStatusInd.equals(detailUpdateStatusInd)) {
			throw new ChangeUpdateStatusIndicatorNotMatchException("Update Status Indicator", commonUpdateStatusInd,
					detailUpdateStatusInd);
		}
	}

	protected void validateSecurityLocation(SecurityLocation location, String source)
			throws EAIMessageValidationException {
		validator.rejectIfNull(location, "SecurityDetail - SecurityLocation");

		validator.validateString(location.getLocationCountry(), "SecurityLocation - LocationCountry", true, 1, 2);

		validator.validateString(location.getLocationOrganisation(), "SecurityLocation - LocationOrganisation", true,
				1, 40);

		validator.validateStdCode(new StandardCode("40", location.getLocationOrganisation(), location
				.getLocationOrganisationDesc()), source, "40");
	}

	/**
	 * Exception to be raised whenever the detail change or update status
	 * indicator not match with the one in ApprovedSecurity.
	 */
	private class ChangeUpdateStatusIndicatorNotMatchException extends EAIMessageValidationException {

		private static final long serialVersionUID = -1786622103469804840L;

		private static final String INDICATOR_NOT_MATCH_ERROR_CODE = "COL_INDICATOR";

		public ChangeUpdateStatusIndicatorNotMatchException(String indicatorType, String commonIndicatorValue,
				String detailIndicatorValue) {
			super("Indicator [" + indicatorType + "], the one in Detail, value [" + detailIndicatorValue
					+ "], not match with the one of ApproveSecurity [" + commonIndicatorValue + "]");
		}

		public String getErrorCode() {
			return INDICATOR_NOT_MATCH_ERROR_CODE;
		}
	}
}