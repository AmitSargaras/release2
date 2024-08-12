package com.integrosys.cms.ui.collateral.insprotection.insswap.cds;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class CDSItemValidator {
	public static ActionErrors validateInput(CDSItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		final double MAX_MARGIN = 100;

		if (!(errorCode = Validator.checkString(aForm.getBankEntity(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("bankEntity",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getHedgeRef(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("hedgeRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getCdsRef(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("cdsRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getTradeID(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("tradeID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getTradeDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("tradeDate",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getDealDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors
					.add("dealDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
		}
		else if (!(errorCode = UIValidator.compareDateEarlier(aForm.getDealDate(), aForm.getCdsMaturityDate(), locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("dealDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "Deal Date",
					"CDS Maturity Date"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getStartDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("startDate",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getCdsMaturityDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("cdsMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		boolean isMandatory = ((aForm.getTenorUnit() != null) && (aForm.getTenorUnit().length() > 0));
		if (!(errorCode = Validator.checkNumber(aForm.getTenor(), isMandatory, 0, 9999)).equals(Validator.ERROR_NONE)) {
			errors.add("tenor", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "9999"));
		}
		isMandatory = ((aForm.getTenor() != null) && (aForm.getTenor().length() > 0));
		if (!(errorCode = Validator.checkString(aForm.getTenorUnit(), isMandatory, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("tenorUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "3"));
		}
		if (!(errorCode = Validator.checkString(aForm.getTradeCurrency(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("tradeCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"3"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getNotionalHedgeAmt(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("notionalHedgeAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR, "2"));
		}
		if (!(errorCode = Validator.checkString(aForm.getReferenceEntity(), false, 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("referenceEntity", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getReferenceAsset(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("referenceAsset", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getIssuer(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("issuer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkString(aForm.getIssuerID(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("issuerID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "50"));
		}
		if (!(errorCode = Validator.checkString(aForm.getDetailsIssuer(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("detailsIssuer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"250"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getDealtPrice(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3, 4, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("dealtPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3_STR, "3"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getResidualMaturityField(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("residualMaturityField", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4_STR, "4"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getParValue(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3, 4, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("parValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3_STR, "3"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getDeclineMarketValue(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3, 4, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("declineMarketValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_3_STR, "3"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getEventDeterminationDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("eventDeterminationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}

		// validate valuation details
		if (!(errorCode = Validator.checkDate(aForm.getValuationDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getValuationCurrency(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", "3"));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getNominalValue(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 0, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("nominalValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_STR));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getValuationCMV(), true, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationCMV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_STR,"2"));
		}
		isMandatory = ((aForm.getNonStdFreqUnit() != null) && (aForm.getNonStdFreqUnit().length() > 0));
		if (!(errorCode = Validator.checkNumber(aForm.getNonStdFreq(), isMandatory, 0, 999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nonStdFreq",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "999"));
		}
		isMandatory = ((aForm.getNonStdFreq() != null) && (aForm.getNonStdFreq().length() > 0));
		if (!(errorCode = Validator.checkString(aForm.getNonStdFreqUnit(), isMandatory, 0, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("nonStdFreqUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"3"));
		}
		if (!(errorCode = Validator.checkNumber(aForm.getCdsMargin(), false, 0, MAX_MARGIN))
				.equals(Validator.ERROR_NONE)) {
			errors
					.add("cdsMargin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							"100"));
		}

		return errors;
	}

}
