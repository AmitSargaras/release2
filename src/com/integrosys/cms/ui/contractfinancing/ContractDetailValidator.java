/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Mar/09 $ Tag: $Name: $
 */
public class ContractDetailValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.contractfinancing.ContractDetailForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode;

		try {

			boolean isSubmit = false;
			if (ContractFinancingAction.EVENT_SUBMIT.equals(form.getEvent())) {
				isSubmit = true;
			}

			if (!(errorCode = Validator.checkString(form.getContractNumber(), isSubmit, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "50"));
			}
			if (!(errorCode = Validator.checkDate(form.getContractDate(), isSubmit, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getAwarderType(), false, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("awarderType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"3"));
			}
			if (!(errorCode = Validator.checkString(form.getAwarderName(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("awarderName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"100"));
			}
			if (!(errorCode = Validator.checkString(form.getContractType(), false, 0, 2)).equals(Validator.ERROR_NONE)) {
				errors.add("contractType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"2"));
			}
			if (!(errorCode = Validator.checkDate(form.getStartDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("startDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkDate(form.getExpiryDate(), isSubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkDate(form.getExtendedDate(), isSubmit, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("extendedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!form.getExpiryDate().equals("") && !form.getExtendedDate().equals("")) {
				int a = DateUtil.convertDate(locale, form.getExtendedDate()).compareTo(
						DateUtil.convertDate(locale, form.getExpiryDate()));
				if (a < 0) {
					errors.add("compareDate", new ActionMessage("error.date.compareDate.cannotBelater",
							"Expiry Date of Contract", "Extended Date of Contract"));
				}
			}

			if (!(errorCode = Validator.checkString(form.getContractCurrency(), isSubmit, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getContractAmount(), isSubmit, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("contractAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkString(form.getActualFinanceCurrency(), false, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actualFinanceCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getActualFinanceAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("actualFinanceAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.AMOUNT, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkInteger(form.getFinancePercent(), isSubmit, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("financePercent", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", "100"));
			}
			if (!(errorCode = Validator.checkString(form.getProjectedProfitCurrency(), false, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("projectedProfitCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", "3"));
			}
			if (!(errorCode = Validator.checkAmount(form.getProjectedProfitAmount(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, "0", locale)).equals(Validator.ERROR_NONE)) {
				errors.add("projectedProfitAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errorCode = Validator.checkString(form.getCollectionAccount(), false, 0, 25))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collectionAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "25"));
			}
			if (!(errorCode = Validator.checkString(form.getProjectAccount(), false, 0, 25))
					.equals(Validator.ERROR_NONE)) {
				errors.add("projectAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "25"));
			}
			if (!(errorCode = Validator.checkDate(form.getFacilityExpiryDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("facilityExpiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
			}
			if (!(errorCode = Validator.checkString(form.getSinkingFundInd(), false, 0, 1))
					.equals(Validator.ERROR_NONE)) {
				errors.add("sinkingFundInd", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "1"));
			}
			if (!(errorCode = Validator.checkString(form.getSinkingFundParty(), false, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("sinkingFundParty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "3"));
			}
			if (!(errorCode = Validator.checkInteger(form.getBuildUpFdr(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("buildUpFdr", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0",
						"100"));
			}
			if (!(errorCode = Validator.checkString(form.getContractDescription(), false, 0, 2000))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractDescription", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "0", "2000"));
			}
			if (!(errorCode = Validator.checkString(form.getRemark(), false, 0, 2000)).equals(Validator.ERROR_NONE)) {
				errors.add("remark", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"2000"));
			}

			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}