package com.integrosys.cms.host.eai.limit.validator;

import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.MandatoryFieldMissingException;
import com.integrosys.cms.host.eai.core.EaiValidationHelper;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IEaiMessageValidator;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.actualtrxhandler.LimitActualHelper;
import com.integrosys.cms.host.eai.limit.bus.ChargeDetail;
import com.integrosys.cms.host.eai.limit.bus.IChargeDetailJdbc;
import com.integrosys.cms.host.eai.limit.bus.ILimitDao;
import com.integrosys.cms.host.eai.security.NoSuchSecurityException;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.ValidationMandatoryFieldFactory;

/**
 * EAI Message Validator to validate <tt>ChargeDetail</tt> instance
 * @author Chong Jun Yong
 * 
 */
public class ChargeDetailValidator implements IEaiMessageValidator {

	private static final EaiValidationHelper validator = EaiValidationHelper.getInstance();

	private static final LimitActualHelper limitActualHelper = new LimitActualHelper();

	private ValidationMandatoryFieldFactory validationMandatoryFieldFactory;

	private IChargeDetailJdbc chargeDetailJdbcDao;

	private ILimitDao limitDao;

	private String[] sourceIdsNatureOfChargeApplicable;

	private String[] securitySubtypeIdsNatureOfChargeApplicable;

	private String[] sourceIdsChargePendingRedemptionApplicable;

	private Map securitySubtypeIdNatureOfChargeStandardCodeNumMap;

	public void setValidationMandatoryFieldFactory(ValidationMandatoryFieldFactory validationMandatoryFieldFactory) {
		this.validationMandatoryFieldFactory = validationMandatoryFieldFactory;
	}

	public void setChargeDetailJdbcDao(IChargeDetailJdbc chargeDetailJdbcDao) {
		this.chargeDetailJdbcDao = chargeDetailJdbcDao;
	}

	public void setLimitDao(ILimitDao limitDao) {
		this.limitDao = limitDao;
	}

	public void setSourceIdsNatureOfChargeApplicable(String[] sourceIdsNatureOfChargeApplicable) {
		this.sourceIdsNatureOfChargeApplicable = sourceIdsNatureOfChargeApplicable;
	}

	public void setSecuritySubtypeIdsNatureOfChargeApplicable(String[] sourceIdsNatureOfChargeApplicable) {
		this.sourceIdsNatureOfChargeApplicable = sourceIdsNatureOfChargeApplicable;
	}

	public void setSourceIdsChargePendingRedemptionApplicable(String[] sourceIdsChargePendingRedemptionApplicable) {
		this.sourceIdsChargePendingRedemptionApplicable = sourceIdsChargePendingRedemptionApplicable;
	}

	public void setSecuritySubtypeIdNatureOfChargeStandardCodeNumMap(
			Map securitySubtypeIdNatureOfChargeStandardCodeNumMap) {
		this.securitySubtypeIdNatureOfChargeStandardCodeNumMap = securitySubtypeIdNatureOfChargeStandardCodeNumMap;
	}

	public void validate(EAIMessage msg) throws EAIMessageValidationException {
		AAMessageBody aaMsgBody = (AAMessageBody) msg.getMsgBody();
		String source = msg.getMsgHeader().getSource();
		Vector chargeDetails = aaMsgBody.getChargeDetail();

		if ((chargeDetails == null) || (chargeDetails.size() == 0)) {
			return;
		}

		for (Iterator itr = chargeDetails.iterator(); itr.hasNext();) {
			ChargeDetail charge = (ChargeDetail) itr.next();

			if (ICMSConstant.FALSE_VALUE.equals(charge.getChangeIndicator())) {
				continue;
			}

			if (charge.getSecurityId() != null) {
				charge.setSecurityId(charge.getSecurityId().toUpperCase());
			}

			validator.validateString(charge.getSecurityId(), "ChargeDetail - LOSSecurityId", true, 1, 20);

			if (((charge.getUpdateStatusIndicator() != null) && charge.getUpdateStatusIndicator().equals(
					String.valueOf(IEaiConstant.UPDATEINDICATOR)))
					|| ((charge.getUpdateStatusIndicator() != null) && charge.getUpdateStatusIndicator().equals(
							String.valueOf(IEaiConstant.DELETEINDICATOR)))) {
				validator.validateMandatoryFieldForLong("ChargeDetail - CMSSecurityId", charge.getCmsSecurityId());
				validator.validateMandatoryFieldForLong("ChargeDetail - CMSChargeDetailId", charge.getChargeDetailId());
			}

			if (((charge.getLimitId() == null) || (charge.getLimitId().length == 0))
					&& ((charge.getCMSLimitId() == null) || (charge.getCMSLimitId().length == 0))) {
				throw new MandatoryFieldMissingException("ChargeDetail - LimitIDs/CMSLimitIDs");
			}

			validator.validateString(charge.getChargeType(), "ChargeDetail - ChargeType", true, 1, 1, new String[] {
					"G", "S" });

			if (charge.getChargeAmount() == null) {
				throw new MandatoryFieldMissingException("ChargeDetail - ChargeAmount");
			}
			validator.validateDoubleDigit(String.valueOf(charge.getChargeAmount()), "ChargeDetail - ChargeAmount",
					true, 13, 2, false);

			if (charge.getSecurityRanking() == null) {
				throw new MandatoryFieldMissingException("ChargeDetail - SecurityRank");
			}
			validator.validateLong("ChargeDetail - SecurityRank", charge.getSecurityRanking().longValue(), 1, 999);

			validator.validateDate(charge.getLegallyChargedDate(), "ChargeDetail - LegallyChargedDate", false);

			validator.validateDate(charge.getConfirmChargedDate(), "ChargeDetail - ConfirmChargedDate", false);

			validator.validateString(charge.getPriorChargeChargee(), "ChargeDetail - PriorChargee", false, 0, 100);

			validator.validateString(charge.getPriorChargeType(), "ChargeDetail - PriorChargeType", false, 0, 1,
					new String[] { "I", "E", "O" });

			if (charge.getPriorChargeAmount() != null) {
				validator.validateDoubleDigit(String.valueOf(charge.getPriorChargeAmount()),
						"ChargeDetail - PriorChargeAmount", false, 13, 2, false);
			}

			boolean isPartyChargeMandatory = this.validationMandatoryFieldFactory.isMandatoryField(source,
					ChargeDetail.class, "partyCharge");
			validator.validateString(charge.getPartyCharge(), "ChargeDetail - PartyCharge", isPartyChargeMandatory,
					(isPartyChargeMandatory ? 1 : 0), 1, new String[] { "1", "3" });

			if (ArrayUtils.contains(this.sourceIdsChargePendingRedemptionApplicable, source)) {
				validator.validateString(charge.getChargePendingRedemption(), "ChargeDetail - ChargePendingRedemption",
						false, 0, 1, new String[] { "Y", "N" });
			}

			long cmsSecurityId = 0;
			if (charge.getCmsSecurityId() > 0) {
				cmsSecurityId = charge.getCmsSecurityId();
			}
			else {
				cmsSecurityId = limitActualHelper.getInternalCollateralID(charge.getSecurityId(), aaMsgBody
						.getLimitsApprovedSecurityMap());

				if (cmsSecurityId == ICMSConstant.LONG_INVALID_VALUE || cmsSecurityId == 0) {
					cmsSecurityId = this.chargeDetailJdbcDao.getCmsSecurityIdByLosSecurityId(charge.getSecurityId(),
							source);
				}
			}

			if (cmsSecurityId > 0) {
				ApprovedSecurity security = (ApprovedSecurity) this.limitDao.retrieve(new Long(cmsSecurityId),
						ApprovedSecurity.class);
				if (security == null) {
					throw new NoSuchSecurityException(cmsSecurityId);
				}

				if (ArrayUtils.contains(this.sourceIdsNatureOfChargeApplicable, source)
						&& ArrayUtils.contains(this.securitySubtypeIdsNatureOfChargeApplicable, security
								.getSecuritySubType().getStandardCodeValue()) && charge.getChargeNature() != null) {
					String expectedNatureOfChargeCategoryCode = (String) this.securitySubtypeIdNatureOfChargeStandardCodeNumMap
							.get(security.getSecuritySubType().getStandardCodeValue());
					validator.validateStdCode(charge.getChargeNature(), source, expectedNatureOfChargeCategoryCode);
				}
			}
			else {
				throw new NoSuchSecurityException(charge.getSecurityId(), source);
			}

		}
	}
}
