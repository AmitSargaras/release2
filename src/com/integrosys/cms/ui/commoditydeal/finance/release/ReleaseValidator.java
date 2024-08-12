/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/ReleaseValidator.java,v 1.5 2006/10/30 04:22:44 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/10/30 04:22:44 $ Tag: $Name: $
 */

public class ReleaseValidator {
	public static ActionErrors validateInput(ReleaseForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(ReleaseAction.EVENT_REFRESH)) {
			if (!(errorCode = Validator.checkDate(aForm.getPartialReleaseDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("partialReleaseDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getPartialReleaseDate(), "partialReleaseDate",
						"Date of Release", locale);
			}
		}
		else {
			if ((aForm.getSelectedWarehouseRec() == null) || (aForm.getSelectedWarehouseRec().length <= 0)) {
				errors.add("selectedWarehouseRec", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						Validator.ERROR_MANDATORY), "0", 256 + ""));
			}
			else {
				List selectedList = Arrays.asList(aForm.getSelectedWarehouseRec());
				if ((aForm.getReceiptNo() == null) || (aForm.getReceiptNo().length <= 0)) {
					errors.add("selectedReceipt", new ActionMessage(
							"error.commodity.deal.finance.release.mandatory.receiptinfo"));
				}
				else {
					if (selectedList.size() != aForm.getReceiptNo().length) {
						errors.add("selectedReceipt", new ActionMessage(
								"error.commodity.deal.finance.release.mandatory.receiptinfo"));
					}
					else {
						boolean isError = false;
						for (int i = 0; !isError && (i < aForm.getReceiptNo().length); i++) {
							if (!selectedList.contains(aForm.getReceiptNo()[i])) {
								errors.add("selectedReceipt", new ActionMessage(
										"error.commodity.deal.finance.release.mandatory.receiptinfo"));
								isError = true;
							}
						}
						if (!isError) {
							for (int i = 0; i < aForm.getQtyToReleased().length; i++) {
								int index = selectedList.indexOf(aForm.getReceiptNo()[i]);
								if (index >= 0) {
									try {
										double maxValue = MapperUtil.mapStringToDouble(aForm
												.getBalanceQtyToBeReleased()[i], locale);
										// int maxValue =
										// Integer.parseInt(aForm.
										// getBalanceQtyToBeReleased()[i]);
										String releaseQty = aForm.getQtyToReleased()[i];
										if (!(errorCode = UIValidator.checkNumber(releaseQty, true, 0, maxValue, 0,
												locale)).equals(Validator.ERROR_NONE)) {
											errors.add("qtyToReleased_" + aForm.getReceiptNo()[i], new ActionMessage(
													ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", aForm
															.getBalanceQtyToBeReleased()[i]));
										}
									}
									catch (Exception e) {
										DefaultLogger.error(ReleaseValidator.class.getName(),
												"error to convert balanceQtyToBeReleased to double: "
														+ aForm.getBalanceQtyToBeReleased()[i]);
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			if (!(errorCode = Validator.checkDate(aForm.getPartialReleaseDate(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("partialReleaseDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getPartialReleaseDate(), "partialReleaseDate",
						"Date of Partial Release of Collateral", locale);
			}
		}

		return errors;
	}

	private static ActionErrors validatePreviousDate(ActionErrors errors, String dateStr, String fieldName,
			String desc, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.after(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.not.futuredate", desc));
			}
		}
		return errors;
	}
}
