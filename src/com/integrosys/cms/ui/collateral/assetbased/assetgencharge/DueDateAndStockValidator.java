package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL;
import static com.integrosys.cms.ui.common.constant.IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2_STR;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.util.MapperUtil;
import com.integrosys.cms.ui.common.NumberValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class DueDateAndStockValidator {

	
	public static ActionErrors validateInput(CommonForm commonForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		DueDateAndStockForm form = (DueDateAndStockForm) commonForm;
		String errorCode;

		if (!ERROR_NONE.equals(errorCode = Validator.checkNumber(form.getDpShare(), false,
						0, 100.00))) {
			errors.add("dpShareError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, 100.00));
		}
		
		boolean isDpAsPerStockStatementPassed = true;
		/*if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getDpAsPerStockStatement(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("dpAsPerStockStatementError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
			isDpAsPerStockStatementPassed = false;
		}*/
		
		boolean isDpForCashFlowOrBudgetPassed = true;
		/*if (!ERROR_NONE.equals(errorCode = NumberValidator.checkNumber(form.getDpForCashFlowOrBudget(), false,
				BigDecimal.ZERO, MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL, 3, locale))) {
			errors.add("dpForCashFlowOrBudgetError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0, MAXIMUM_ALLOWED_AMOUNT_25_2_STR));
			isDpForCashFlowOrBudgetPassed = false;
		}*/
		
		 if (!(errorCode = Validator.checkNumber(form.getDpForCashFlowOrBudget(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals("error.number.decimalexceeded")) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("drawingPowerAsPerLeadBankError", new ActionMessage(errorMessage, "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
				isDpForCashFlowOrBudgetPassed = false;
			}

		 
		if(isDpAsPerStockStatementPassed && isDpForCashFlowOrBudgetPassed) {
			if(StringUtils.isNotBlank(form.getDpAsPerStockStatement()) && StringUtils.isNotBlank(form.getDpForCashFlowOrBudget())) {
				BigDecimal dpAsPerStock = MapperUtil.stringToBigDecimal(UIUtil.removeComma(form.getDpAsPerStockStatement().trim()));
				BigDecimal dpForCashFlow = MapperUtil.stringToBigDecimal(UIUtil.removeComma(form.getDpForCashFlowOrBudget().trim()));
				if((BigDecimal.ZERO.compareTo(dpAsPerStock)!=0) && (BigDecimal.ZERO.compareTo(dpForCashFlow)!=0)){
					errors.add("dpForCashFlowOrBudgetError", new ActionMessage("error.dp.stock.or.clash.flow"));
				}
			}

		}
		
		if(null == form.getDocCode() || "".equals(form.getDocCode())) {
			errors.add("dueDateMandatoryerror", new ActionMessage("error.date.mandatory"));
		}
		
		if("Add".equalsIgnoreCase(form.getDueDateActionPage())) {
		if(null != form.getDueDateAlreadyReceived() && "Y".equals(form.getDueDateAlreadyReceived())) {
			errors.add("dueDateMandatoryerror", new ActionMessage("error.dueDate.already.received"));
		}
		}
		
		if(null == form.getStockDocMonth() || "".equals(form.getStockDocMonth())) {
			errors.add("stockDocMonthMandatoryerror", new ActionMessage("error.date.mandatory"));
		}
		
		if(null == form.getStockDocYear() || "".equals(form.getStockDocYear())) {
			errors.add("stockDocYearMandatoryerror", new ActionMessage("error.date.mandatory"));
		}
		
//		if(StringUtils.isNotBlank(form.getTotalReleasedAmount())) {
//			BigDecimal totalReleasedAmount = MapperUtil.stringToBigDecimal(form.getTotalReleasedAmount().trim());
//			BigDecimal dpAsPerStock = form.getDpAsPerStockStatement()!=null? MapperUtil.stringToBigDecimal(form.getDpAsPerStockStatement().trim()):BigDecimal.ZERO;
//			
//			if(totalReleasedAmount.compareTo(dpAsPerStock) >0) {
//				errors.add("dpAsPerStockStatementError", new ActionMessage("error.dp.cannot.be.less.released.amt"));
//			}
//		}

//		ICollateralDAO collateralDao = (ICollateralDAO) BeanHouse.get("collateralDao");
//		if(StringUtils.isNotBlank("collateralId")) {
//			List<Long> facilities = collateralDao.getNonApprovedLinkedFacilities(0L);
//			if(!CollectionUtils.isEmpty(facilities)) {
//				String facList = StringUtils.join(facilities.toArray(), ", ");
//				errors.add("dueDateAndStockCommonError",new ActionMessage("Below facility lines are pending for checker authorization. \n"+facList));
//			}	
//		}
		/*
		 * if(StringUtils.isNotBlank("collateralId")) { BigDecimal releasedAmt =
		 * collateralDao.getLinkedFacilitiesTotalLimitReleasedAmt(0L); BigDecimal dp =
		 * new BigDecimal(form.getDpAsPerStockStatement());
		 * if(releasedAmt.compareTo(dp)<0) errors.add("dpAsPerStockStatementError",new
		 * ActionMessage("")); }
		 */		
		return errors;
	}

}
