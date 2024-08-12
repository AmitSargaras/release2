/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/loanschedule/LoanScheduleMapper.java,v 1.10 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency.loanschedule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanSchedule;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanSchedule;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */
public class LoanScheduleMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		LoanScheduleForm aForm = (LoanScheduleForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ILoanAgency obToChange = null;
		try {
			obToChange = (ILoanAgency) AccessorUtil.deepClone(inputs.get("serviceLoanAgency"));
		}
		catch (Exception e) {
			throw new MapperException(e.getMessage());
		}

		String[] paymentDate = aForm.getPaymentDate();
		String[] principalAmt = aForm.getPrincipalAmtDue();
		String[] interestAmt = aForm.getInterestAmtDue();

		ILoanSchedule[] scheduleList = obToChange.getLoanSchedules();
		String ccyCode = null;
		if (obToChange.getPrincipleAmount() != null) {
			ccyCode = obToChange.getPrincipleAmount().getCurrencyCode();
		}
		if (scheduleList != null) {
			for (int i = 0; i < scheduleList.length; i++) {
				OBLoanSchedule temp = (OBLoanSchedule) scheduleList[i];
				temp.setPaymentDate(CollateralMapper.compareDate(locale, temp.getPaymentDate(), paymentDate[i]));
				if (isEmptyOrNull(principalAmt[i])) {
					temp.setPrincipalDueAmount(null);
				}
				else {
					temp.setPrincipalDueAmount(UIUtil.convertToAmount(locale, ccyCode, principalAmt[i]));
				}
				/*
				 * if (isEmptyOrNull(principalAmt[i])) {
				 * temp.setPrincipalDueAmount
				 * (ICMSConstant.DOUBLE_INVALID_VALUE); } else { try {
				 * temp.setPrincipalDueAmount
				 * (MapperUtil.mapStringToDouble(principalAmt[i], locale)); }
				 * catch (Exception e) { throw new
				 * MapperException(e.getMessage()); } }
				 */
				if (isEmptyOrNull(interestAmt[i])) {
					temp.setInterestDueAmount(null);
				}
				else {
					temp.setInterestDueAmount(UIUtil.convertToAmount(locale, ccyCode, interestAmt[i]));
				}
				/*
				 * if (isEmptyOrNull(interestAmt[i])) {
				 * temp.setInterestDueAmount(ICMSConstant.DOUBLE_INVALID_VALUE);
				 * } else { try {
				 * temp.setInterestDueAmount(MapperUtil.mapStringToDouble
				 * (interestAmt[i], locale)); } catch (Exception e) { throw new
				 * MapperException(e.getMessage()); } }
				 */
				scheduleList[i] = temp;
			}
		}
		((OBLoanAgency) obToChange).setLoanSchedules(scheduleList);

		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		LoanScheduleForm aForm = (LoanScheduleForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ILoanAgency loanAgencyObj = (ILoanAgency) obj;

		String[] paymentDate = null;
		String[] principalAmt = null;
		String[] interestAmt = null;
		String[] totalPayment = null;

		ILoanSchedule[] scheduleList = loanAgencyObj.getLoanSchedules();
		if (scheduleList != null) {
			DefaultLogger.debug(this, "scheduleList length: " + scheduleList.length);
			paymentDate = new String[scheduleList.length];
			principalAmt = new String[scheduleList.length];
			interestAmt = new String[scheduleList.length];
			totalPayment = new String[scheduleList.length];
			for (int i = 0; i < scheduleList.length; i++) {
				ILoanSchedule temp = scheduleList[i];
				paymentDate[i] = DateUtil.formatDate(locale, temp.getPaymentDate());
				BigDecimal total = null;
				if (temp.getPrincipalDueAmount() != null) {
					try {
						principalAmt[i] = UIUtil.formatNumber(temp.getPrincipalDueAmount().getAmountAsBigDecimal(), 2,
								locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					total = temp.getPrincipalDueAmount().getAmountAsBigDecimal();
				}
				else {
					principalAmt[i] = "";
				}
				if (temp.getInterestDueAmount() != null) {
					try {
						interestAmt[i] = UIUtil.formatNumber(temp.getInterestDueAmount().getAmountAsBigDecimal(), 2,
								locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
					if ((total != null) && (temp.getInterestDueAmount().getAmountAsBigDecimal() != null)) {
						total = total.add(temp.getInterestDueAmount().getAmountAsBigDecimal());
					}
					else if (total == null) {
						total = temp.getInterestDueAmount().getAmountAsBigDecimal();
					}
				}
				else {
					interestAmt[i] = "";
				}
				if (total != null) {
					try {
						totalPayment[i] = UIUtil.formatNumber(total, 2, locale);
					}
					catch (Exception e) {
						throw new MapperException(e.getMessage());
					}
				}
				else {
					totalPayment[i] = "";
				}
			}
		}
		aForm.setPaymentDate(paymentDate);
		aForm.setPrincipalAmtDue(principalAmt);
		aForm.setInterestAmtDue(interestAmt);
		aForm.setTotalPaymentDue(totalPayment);

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceLoanAgency", "com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency",
						SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
}
