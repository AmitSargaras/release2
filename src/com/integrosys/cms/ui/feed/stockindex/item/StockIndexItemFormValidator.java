package com.integrosys.cms.ui.feed.stockindex.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.feed.FeedUIValidator;

/**
 * This class implements validation
 */
public class StockIndexItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = StockIndexItemFormValidator.class.getName();

	public static ActionErrors validateInput(StockIndexItemForm form, Locale locale) {

		// Only for save event.

		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		String name = form.getName();
		String isinCode = form.getIsinCode();
		String ric = form.getRic();
		String unitPrice = form.getUnitPrice();

		if (!(errorCode = Validator.checkString(name, true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("name", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(50)));
		}

		// MBB-802
		/*
		 * if (!(errorCode = Validator.checkString(isinCode, true, 1,
		 * 12)).equals(Validator.ERROR_NONE)) { errors.add("isinCode", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * new Integer( 1), new Integer(12))); }
		 */

		/*
		 * if (!(errorCode = Validator.checkString(ric, true, 1,
		 * 20)).equals(Validator.ERROR_NONE)) { errors.add("ric", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * new Integer(1), new Integer(20))); }
		 */

		FeedUIValidator.validateUnitPrices(unitPrice, errors);

		/*
		 * if (!"".equals(unitPrice) && !Validator.checkPattern(unitPrice,
		 * FeedConstants.INDEX_REGEX)) { errors.add("unitPrice", new
		 * ActionMessage(FeedConstants.ERROR_INVALID)); } else if (!(errorCode =
		 * Validator.checkNumber(unitPrice, false, 0, 1000000000)).equals(
		 * Validator.ERROR_NONE)) { errors.add("unitPrice", new ActionMessage(
		 * ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), new Integer(0),
		 * new Integer(1000000000))); }
		 */

		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());

		return errors;
	}
	// end validateInput

}
