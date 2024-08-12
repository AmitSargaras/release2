/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/warehouse/TitleDocWarehouseValidator.java,v 1.16 2005/11/12 02:55:19 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc.warehouse;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/11/12 02:55:19 $ Tag: $Name: $
 */

public class TitleDocWarehouseValidator {
	public static ActionErrors validateInput(TitleDocWarehouseForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			if (!(errorCode = UIValidator.checkNumber(aForm.getQuantity(), false, 0, CommodityDealConstant.MAX_QTY, 5,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("quantity", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "4"));
			}
			if (!(errorCode = Validator.checkString(aForm.getOrigPaperRecNo(), false, 0, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("origPaperRecNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 50 + ""));
			}
		}
		else {
			if (!(errorCode = UIValidator.checkNumber(aForm.getQuantity(), true, 0, CommodityDealConstant.MAX_QTY, 5,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("quantity", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "4"));
			}
			if (!(errorCode = Validator.checkString(aForm.getOrigPaperRecNo(), true, 0, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("origPaperRecNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 50 + ""));
			}
		}
		if (!(errorCode = Validator.checkDate(aForm.getIssuedOn(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors
					.add("issuedOn", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getIssuedOn(), "issuedOn", "Issued On", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getLastModified(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lastModified", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getLastModified(), "lastModified", "Last Modified On", locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getStatus(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("status", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getExchangeID(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("exchangeID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getConvertEWRDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("convertEWRDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getConvertEWRDate(), "convertEWRDate", "Converted to EWR On",
					locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getPaperRecNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("paperRecNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getConvertPWRDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("convertPWRDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getConvertPWRDate(), "convertPWRDate", "Converted to PWR On",
					locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getTitleHolder(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("titleHolder", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getTitleHolderChangedDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("titleHolderChangedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getTitleHolderChangedDate(), "titleHolderChangedDate",
					"Title Holder Changed On", locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getTitleHolderNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("titleHolderNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getBeneficiary(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("beneficiary", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getBeneficiaryNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("beneficiaryNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getBeneficiaryChangedDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("beneficiaryChangedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
					"0", 256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getBeneficiaryChangedDate(), "beneficiaryChangedDate",
					"Beneficiary Changed On", locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getWarehouseLocStoreNo(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("warehouseLocStoreNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getCargoNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("cargoNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getProduct(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("product",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getOrigin(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("origin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getContainerNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("containerNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getSealNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("sealNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getCropYear(), false, 0, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("cropYear",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getIcoMark(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors
					.add("icoMark", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getAdditionalMark(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("additionalMark", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getVesselName(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("vesselName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getCarrier(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("carrier",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getVoyageNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("voyageNo",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getBillLadingNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("billLadingNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getBillLadingDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("billLadingDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getBillLadingDate(), "billLadingDate", "Bill of Lading Date",
					locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getDateAssignment(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("dateAssignment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validatePreviousDate(errors, aForm.getDateAssignment(), "dateAssignment", "Date of Assignment",
					locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getDateBankRelease(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("dateBankRelease", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDeliverOrder(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("deliverOrder", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getFreeTimeExpiry(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("freeTimeExpiry", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (!aForm.getEvent().equals(TitleDocWarehouseAction.EVENT_REFRESH)) {
			errors = validateFutureDate(errors, aForm.getFreeTimeExpiry(), "freeTimeExpiry", "Free Time Expiry", locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getGradeCert(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("gradeCert", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getWeightNote(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("weightNote", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getFdaCustomerEntryNo(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("fdaCustomerEntryNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getWarehouseRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("warehouseRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
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

	private static ActionErrors validateFutureDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.futuredate", desc));
			}
		}
		return errors;
	}
}
