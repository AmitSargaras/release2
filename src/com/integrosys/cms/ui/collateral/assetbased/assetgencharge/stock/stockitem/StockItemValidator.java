/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/stockitem/StockItemValidator.java,v 1.7 2005/04/20 05:43:23 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.techinfra.validation.ValidatorConstant;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeValidator;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/04/20 05:43:23 $ Tag: $Name: $
 */

public class StockItemValidator {

	private static String STOCK_AMOUNT = "amount";

	private static String STOCK_MARGIN = "margin";

	public static ActionErrors validateInput(StockItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = GeneralChargeSubTypeValidator.validateInput(aForm, locale);
		;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (!(errorCode = Validator.checkAmount(aForm.getCreditor(), false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("creditor", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
		}

		boolean isMandatory = (aForm.getEvent().equals(StockItemAction.EVENT_CREATE) || aForm.getEvent().equals(
				StockItemAction.EVENT_UPDATE));

		boolean isStockTypeInput = false;
		// validate stock types
		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRawMaterialsAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getRawMaterialsMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getRawMaterialsAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "rawMaterialsAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getRawMaterialsMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "rawMaterialsMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getGoodsInTransitAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getGoodsInTransitMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getGoodsInTransitAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "goodsInTransitAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getGoodsInTransitMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "goodsInTransitMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getFinishedGoodsAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getFinishedGoodsMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getFinishedGoodsAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "finishedGoodsAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getFinishedGoodsMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "finishedGoodsMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getStoresSparesAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getStoresSparesMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getStoresSparesAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "storesSparesAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getStoresSparesMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "storesSparesMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getWipAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getWipMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getWipAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "wipAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getWipMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "wipMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getOthMerchandiseAmt())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getOthMerchandiseMargin())) {
			if (!(errorCode = Validator.checkAmount(aForm.getOthMerchandiseAmt(), (isMandatory && true), 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "othMerchandiseAmt", STOCK_AMOUNT);
			}
			if (!(errorCode = Validator.checkInteger(aForm.getOthMerchandiseMargin(), (isMandatory && true), 0, 100))
					.equals(Validator.ERROR_NONE)) {
				setStockTypeError(errors, errorCode, "othMerchandiseMargin", STOCK_MARGIN);
			}
			isStockTypeInput = true;
		}

		if (!isStockTypeInput && isMandatory) {
			errors.add("stockTypeErr", new ActionMessage("error.collateral.security.assetgencharge.stocktype"));
		}

		if (!(errorCode = Validator.checkString(aForm.getPhysicalInspection(), (isMandatory && true), 0, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("physicalInspection", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 530 + ""));
		}

		if (ICMSConstant.TRUE_VALUE.equals(aForm.getPhysicalInspection())) {
			boolean validateError = false;
			if (!(errorCode = Validator.checkInteger(aForm.getPhyInsFreqNum(), (isMandatory && true), 1, 99))
					.equals(Validator.ERROR_NONE)) {
				errors.add("phyInsFreqNum", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", 99 + ""));
				validateError = true;
			}
			if (!validateError
					&& !(errorCode = Validator.checkString(aForm.getPhyInsFreqUnit(), (isMandatory && true), 0, 50))
							.equals(Validator.ERROR_NONE)) {
				errors.add("phyInsFreqUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 50 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getLastPhyInsDate(), (isMandatory && true), locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("lastPhyInsDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						250 + ""));
			}
			else {
				UIValidator.validatePreviousDate(errors, aForm.getLastPhyInsDate(), "lastPhyInsDate",
						"Last Physical Inspection Date", locale);
			}
		}
		else if (isMandatory && ICMSConstant.FALSE_VALUE.equals(aForm.getPhysicalInspection())) {
			if (!aForm.getPhyInsFreqNum().equals("") || !aForm.getPhyInsFreqUnit().equals("")) {
				errors.add("phyInsFreqNum", new ActionMessage("error.string.empty"));
			}
			if (!aForm.getLastPhyInsDate().equals("")) {
				errors.add("lastPhyInsDate", new ActionMessage("error.string.empty"));
			}
		}

		return errors;
	}

	private static void setStockTypeError(ActionErrors errors, String errorCode, String fieldname, String type) {
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		if (errorCode.equals(ValidatorConstant.ERROR_MANDATORY)) {
			errors.add(fieldname, new ActionMessage("error.collateral.assetgencharge.stocktype.mandatory", type
					.equals(STOCK_AMOUNT) ? "margin" : "amount"));
		}
		else {
			if (type.equals(STOCK_AMOUNT)) {
				errors.add(fieldname, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}
			else {
				errors.add(fieldname, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						"100"));
			}
		}
	}
}
