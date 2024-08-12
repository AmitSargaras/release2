package com.integrosys.cms.ui.feed.propertyindex.item;

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
public class PropertyIndexItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = PropertyIndexItemFormValidator.class.getName();

	public static ActionErrors validateInput(PropertyIndexItemForm form, Locale locale) {

		// Only for save event.

		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		String type = form.getType();
		String region = form.getRegion();
		String unitPrice = form.getUnitPrice();

		if (!(errorCode = Validator.checkString(type, true, 1, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("type", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), new Integer(1),
					new Integer(50)));
		}

		if (!(errorCode = Validator.checkString(region, true, 1, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("region", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					new Integer(1), new Integer(30)));
		}

		FeedUIValidator.validateUnitPrices(unitPrice, errors);

		/*
		 * if (!"".equals(unitPrice) && !Validator.checkPattern(unitPrice,
		 * FeedConstants.INDEX_REGEX)) { errors.add("unitPrice", new
		 * ActionMessage(FeedConstants.ERROR_INVALID)); } else if (!(errorCode =
		 * Validator.checkNumber(unitPrice, true, 0,
		 * 1000000000)).equals(Validator.ERROR_NONE)) { errors.add("unitPrice",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), new Integer(0), new Integer(1000000000))); }
		 */

		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());
		return errors;
	}
	// end validateInput

}
