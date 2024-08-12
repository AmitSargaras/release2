package com.integrosys.cms.ui.genlad;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * GenerateDDNFormValidator Validator for DDN form inputs Purpose: validation
 * Description:
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.27 $
 * @since $Date: 2006/11/23 11:49:43 $ Tag: $Name: $
 */
public class GenerateLADFormValidator {
	public static ActionErrors validateInput(GenerateLADForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
//		String ddnLimit[] = aForm.getDdnLimit();
//		Date ddnApprDate = DateUtil.convertDate(locale, aForm.getAppDate());
//		// String ddnLimitLeft[] = aForm.getDdnLimitLeft();
//		String appLimit[] = aForm.getAppLimit();
//		String errorCode = "";
//		DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "Inside Validation");
//		Date bcaAppDate = DateUtil.convertDate(locale, aForm.getBcaAppDate());
//		Date ddnApproveDate = DateUtil.convertDate(locale, aForm.getAppDate());
//		Date d = DateUtil.clearTime(DateUtil.getDate());
//		String event = aForm.getEvent();
//		if ((aForm.getAppDate() != null) && (aForm.getAppDate().length() > 0)) {
//			if (d.before(ddnApprDate)) {
//				errors.add("ddnAppDate", new ActionMessage("error.date.future"));
//			}
//		}
//		// DDN Amount
//		if ((ddnLimit != null) && (ddnLimit.length > 0)) {
//			HashMap tempMap = new HashMap();
//			try {
//				String partIndex = aForm.getCheckedIndexes();
//				StringTokenizer st = new StringTokenizer(partIndex, ",");
//				while (st.hasMoreTokens()) {
//					tempMap.put(st.nextToken(), "ooo");
//				}
//			}
//			catch (Exception ex) {
//				// do nothing
//			}
//
//			try {
//				for (int i = 0; i < ddnLimit.length; i++) {
//					String temp = ddnLimit[i];
//					String temp1 = appLimit[i];
//					// String temp2 = ddnLimitLeft[i];//cr36
//
//					boolean mandatoryChk = (event.equals("submit_ddn") || event.equals("total_ddn")) ? true : false;
//					if (tempMap.containsKey(String.valueOf(i))) {
//						if (!(errorCode = Validator.checkAmount(temp, mandatoryChk, 0,
//								IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
//								.equals(Validator.ERROR_NONE)) {
//							DefaultLogger.debug("GenerateDDNFormValidator\n\n", UIUtil.trimDecimal(appLimit[i]));
//							DefaultLogger.debug("GenerateDDNFormValidator\n\n", ErrorKeyMapper.map(
//									ErrorKeyMapper.AMOUNT, errorCode));
//							errors.add("ddnLimit" + i, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
//									errorCode), "0", UIUtil.trimDecimal(appLimit[i])));
//						}
//						else {
//							// cr 36
//							// checks that the ddn limit does not exceeds the
//							// approved limit, only if the user input is a valid
//							// amount
//							if (CurrencyManager.convertToAmount(locale, aForm.getAppAmtCurrCode()[i], temp)
//									.getAmountAsDouble() > CurrencyManager.convertToAmount(locale,
//									aForm.getAppAmtCurrCode()[i], temp1).getAmountAsDouble()) {
//								// errors.add("ddnLimit"+i, new
//								// ActionMessage("error.amount.not.greaterthan"
//								// ,"DDN Limit","Balanced DDN Limit"));
//								errors.add("ddnLimit" + i, new ActionMessage("error.amount.not.greaterthan",
//										"DDN Limit", "Approved Limit"));
//							}
//						}
//					}
//					else {
//						if ((temp != null) && (temp.trim().length() > 0)) {
//							if (!(temp.trim().equals(""))) {
//								errors.add("issueDDN" + i, new ActionMessage("error.string.mandatory"));
//								if (!(temp.trim().equals(""))) {
//									if (!(errorCode = Validator.checkAmount(temp, false, 0.0,
//											IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY,
//											locale)).equals(Validator.ERROR_NONE)) {
//										DefaultLogger.debug("GenerateDDNFormValidator\n\n", appLimit[i]);
//										DefaultLogger.debug("GenerateDDNFormValidator\n\n", ErrorKeyMapper.map(
//												ErrorKeyMapper.AMOUNT, errorCode));
//										errors.add("ddnLimit" + i,
//												new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
//														"0", UIUtil.trimDecimal(appLimit[i])));
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			catch (Exception ex) {
//				DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$Failed", "Exception inside DDN limit validation");
//			}
//		}
//
//		// DDN Days
//		if (!(errorCode = Validator.checkInteger(aForm.getDdnDays(), false, 0, Integer.MAX_VALUE))
//				.equals(Validator.ERROR_NONE)) {
//			errors.add("ddnDays", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), String
//					.valueOf(0), String.valueOf(Integer.MAX_VALUE)));
//		}
//
//		// the following checks are only needed when its a submit event
//		boolean mandatoryChk = (event.equals("submit_ddn"));
//		if (mandatoryChk) { // R1.5 CMS-3467, Defect#99
//			// DDN Date
//			// cms-1481 starts.(27/Oct/04) Add new Mandatory validation for DDN
//			// Date
//			if (!(aForm.getDdnDate().length() > 0)) {
//				errors.add("ddnDate", new ActionMessage("error.date.mandatory"));
//			}
//			// cms-1481 ends
//
//			if (aForm.getDdnDate().length() > 0) { // cms 1534 nt req'd this...
//				/*
//				 * Date ddnDate =
//				 * DateUtil.clearTime(DateUtil.convertDate(locale,
//				 * aForm.getDdnDate())); int a = d.compareTo(ddnDate);
//				 * DefaultLogger
//				 * .debug("validation ***********************************"
//				 * ,"Error date "+ a); if (a>0) { errors.add("ddnDate", new
//				 * ActionMessage
//				 * ("error.date.compareDate","Deferred Date","Current Date")); }
//				 * DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "BEFORE DateDiff, bca approval date: " + bcaAppDate);
//				 * DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "BEFORE DateDiff, ddn date: " + ddnDate); long daysDiff =
//				 * CommonUtil.dateDiff(bcaAppDate, ddnDate, Calendar.DATE) + 1;
//				 * DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "bca approval date: " + bcaAppDate);
//				 * DefaultLogger.debug("GenerateDDNFormValidator", "ddn date: "
//				 * + ddnDate); DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "daysDiff: " + daysDiff); if (daysDiff > 90 &&
//				 * ddnDate.after(DateUtil.clearTime(bcaAppDate))) {
//				 * errors.add("ddnDate", new
//				 * ActionMessage("error.date.compareDate.greater"
//				 * ,"Deferral Date",
//				 * "90 calendar days from BCA approval date")); }
//				 */
//			}
//			else if (aForm.getDdnExtDate().length() > 0) {// cms-1297
//				errors.add("ddnExtDate", new ActionMessage("error.date.notAllowDate", "Deferral Date",
//						"Deferral Extended Date"));
//			}
//
//			// DDN Extended Date
//			if ((aForm.getDdnExtDate().length() > 0) && (aForm.getDdnDate().length() > 0)) {
//				Date ddnExtDate = DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDdnExtDate()));
//
//				// cms-1297
//				Date ddnDate = DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDdnDate()));
//				int b = ddnDate.compareTo(DateUtil.clearTime(ddnExtDate));
//				if (b > 0) {
//					errors.add("ddnExtDate", new ActionMessage("error.date.compareDate", "Extended Expiry Date",
//							"Expiry Date"));
//				}
//
//				// cms-1297
//				int a = d.compareTo(DateUtil.clearTime(ddnExtDate));
//				if (a > 0) {
//					errors.add("ddnExtDate", new ActionMessage("error.date.compareDate", "Extended Expiry Date",
//							"Current Date"));
//				}
//
//				// -- CR36 CMS-1884 - verification done in jsp and is no
//				// blocker, still can be submited.
//				/*
//				 * DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "ddn approval date: " + ddnApproveDate);
//				 * DefaultLogger.debug("GenerateDDNFormValidator",
//				 * "ddn ext date: " + ddnExtDate); long daysDiff =
//				 * CommonUtil.dateDiff(ddnApproveDate, ddnExtDate,
//				 * Calendar.DATE) + 1;
//				 * DefaultLogger.debug("GenerateDDNFormValidator", "daysDiff: "
//				 * + daysDiff); if (daysDiff > 90 &&
//				 * ddnExtDate.after(DateUtil.clearTime(bcaAppDate))) {
//				 * errors.add("ddnExtDate", new
//				 * ActionMessage("error.date.compareDate.greater"
//				 * ,"Extended Expiry Date",
//				 * "90 calendar days from DDN approval date")); }
//				 */
//			}
//
//			// DDN Days //shifted up to before the mandatory check
//			// if(!(errorCode =
//			// Validator.checkInteger(aForm.getDdnDays(),false,0
//			// ,Integer.MAX_VALUE)).equals(Validator.ERROR_NONE)){
//			// errors.add("ddnDays",new
//			// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper
//			// .INTEGER,errorCode)
//			// ,String.valueOf(0),String.valueOf(Integer.MAX_VALUE)));
//			// }
//			if (!(errorCode = Validator.checkString(aForm.getCreditOfficerName(), true, 1, 50))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("creditOfficerName", new ActionMessage("error.string.mandatory", "1", "50"));
//			}
//			if (!(errorCode = Validator.checkString(aForm.getCreditOfficerSgnNo(), true, 1, 20))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("creditOfficerSgnNo", new ActionMessage("error.string.mandatory", "1", "20"));
//			}
//		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

}
