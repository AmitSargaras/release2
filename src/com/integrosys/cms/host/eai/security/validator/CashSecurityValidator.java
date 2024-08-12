package com.integrosys.cms.host.eai.security.validator;

import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.core.EaiConstantCla;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.cash.CashDeposit;
import com.integrosys.cms.host.eai.security.bus.cash.CashSecurity;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * EAI Message validator to validate instance of <tt>CashSecurity</tt>
 * 
 * @author Chong Jun Yong
 * 
 */
public class CashSecurityValidator extends SecurityValidator {

	private static final Logger logger = LoggerFactory.getLogger(CashSecurityValidator.class);

	private String[] securitySubtypeIdsFixedDeposit;

	public void setSecuritySubtypeIdsFixedDeposit(String[] securitySubtypeIdsFixedDeposit) {
		this.securitySubtypeIdsFixedDeposit = securitySubtypeIdsFixedDeposit;
	}

	public void validate(EAIMessage scimsg) throws EAIMessageValidationException {
		SecurityMessageBody msg = (SecurityMessageBody) scimsg.getMsgBody();
		String source = scimsg.getMsgHeader().getSource();
		ApprovedSecurity security = msg.getSecurityDetail();
		String secSubType = security.getSecuritySubType().getStandardCodeValue();
		String commonUpdateStatusInd = security.getUpdateStatusIndicator();
		String commonChangeInd = security.getChangeIndicator();

		if (ICMSConstant.SECURITY_TYPE_CASH.equals(security.getSecurityType().getStandardCodeValue())) {
			CashSecurity cash = msg.getCashDetail();

			validator.rejectIfNull(cash, "CashDetail");

			validator.validateString(cash.getLOSSecurityId(), "CashDetail - LOSSecurityId", true, 1, 20);

			validator.validateString(cash.getInterestCapital(), "CashDetail - InterestCapital", false, 0, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if ((ICMSConstant.COLTYPE_CASH_FD).equals(secSubType)
					&& this.validationMandatoryFieldFactory.isMandatoryField(source, CashSecurity.class,
							"securityIssuer")) {
				validator.validateString(cash.getSecurityIssuer(), "CashDetail - SecurityIssuer", true, 1, 40);
			}

			if (ArrayUtils.contains(this.securitySubtypeIdsFixedDeposit, secSubType)) {
				validator.validateString(cash.getCCRefNo(), "CashDetail - CCRefNo", false, 0, 20);

				validator.validateString(cash.getFDDesc(), "CashDetail - FDDesc", false, 0, 100);
			}

			validator.validateString(cash.getUpdateStatusIndicator(), "UpdateStatusIndicator", false, 1, 1,
					EaiConstantCla.getAllowedValuesUpdateStatusIndicators());

			validator.validateString(cash.getChangeIndicator(), "ChangeIndicator", false, 1, 1,
					EaiConstantCla.getAllowedValuesYesNo());

			if ((security.getUpdateStatusIndicator() != null) && (security.getChangeIndicator() != null)) {
				validateIndicator(commonUpdateStatusInd, commonChangeInd, cash.getUpdateStatusIndicator(), cash
						.getChangeIndicator());
			}

			validateDepositDetail(msg);
		}
	}

	private void validateDepositDetail(SecurityMessageBody msg) throws EAIMessageValidationException {
		ApprovedSecurity sec = msg.getSecurityDetail();
		String secSubType = sec.getSecuritySubType().getStandardCodeValue();

		String cmvCurrency = sec.getCmvCurrency();

		if (StringUtils.isEmpty(cmvCurrency)) {
			cmvCurrency = sec.getCurrency();
			sec.setCmvCurrency(cmvCurrency);
		}

		Amount cmv = new Amount(0, cmvCurrency);

		Vector deposits = msg.getDepositDetail();

		validator.rejectIfNull(deposits, "DepositDetail");

		for (Iterator itr = deposits.iterator(); itr.hasNext();) {
			CashDeposit deposit = (CashDeposit) itr.next();

			validator.validateString(deposit.getSecurityId(), "DepositDetail - LOSSecurityId", true, 1, 20);

			if (IEaiConstant.CHANGE_INDICATOR_YES.equals(deposit.getChangeIndicator())
					&& IEaiConstant.UPDATE_STATUS_IND_UPDATE.equals(deposit.getUpdateStatusIndicator())) {
				validator.validateLong("DepositDetail - CMSDepositId", deposit.getCashDepositId(), 1,
						IEaiConstant.ALLOWED_LONG_VALUE_LENGTH_19);
			}

			validator.validateString(deposit.getReferenceNo(), "DepositDetail - ReferenceNo", false, 0, 20);

			validator.validateString(deposit.getAmountCurrency(), "DepositDetail - AmountCurrency", true, 3, 3);

			validator.validateDoubleDigit(deposit.getAmount(), "DepositDetail - Amount", true, 13, 2, false);

			validator.validateDate(deposit.getMaturityDate(), "DepositDetail - MaturityDate", false);

			if (ArrayUtils.contains(this.securitySubtypeIdsFixedDeposit, secSubType)) {
				validator.validateString(deposit.getOwnOtherBankInd(), "DepositDetail - OwnOtherBankInd", false, 0, 1,
						EaiConstantCla.getAllowedValuesYesNo());

				validator.validateString(deposit.getGroupAcctNo(), "DepositDetail - GroupAcctNo", false, 0, 20);

				if ("O".equals(deposit.getOwnOtherBankInd())) {
					validator.validateNumber(deposit.getTenure(), "DepositDetail - Tenure", true, 1, 999);

					validator.validateString(deposit.getTenurePeriodType(), "DepositDetail - TenurePeriodType", true,
							1, 1, new String[] { "M", "Y" });

				}

				if (deposit.getGroupAcctNo() != null) {
					validator.validateString(deposit.getHoldCode(), "DepositDetail - HoldCode", true, 1, 1,
							new String[] { "C", "L" });
				}
			}

			validator.validateString(deposit.getUpdateStatusIndicator(), "UpdateStatusIndicator", false, 0, 1);

			validator.validateString(deposit.getChangeIndicator(), "ChangeIndicator", false, 0, 1);

			try {
				Amount amt = new Amount(deposit.getAmount().doubleValue(), deposit.getAmountCurrency());
				cmv = ForexHelper.addAmount(cmv, amt);
			}
			catch (Exception ex) {
				logger.debug("failed to add amount [" + deposit.getAmount() + "] from deposit to total [" + cmv + "]",
						ex);
			}
		}

		sec.setCmvCurrency(cmv.getCurrencyCode());
		sec.setCmv(new Double(cmv.getAmountAsDouble()));

		if ((sec.getForcedSaleValue() != null) && (sec.getForcedSaleValue().doubleValue() <= 0)) {
			sec.setFsvCurrency(cmv.getCurrencyCode());
			sec.setForcedSaleValue(sec.getCmv());
			sec.setFsv(sec.getCmv());
		}

		sec.setFsv(sec.getForcedSaleValue());
	}
}
