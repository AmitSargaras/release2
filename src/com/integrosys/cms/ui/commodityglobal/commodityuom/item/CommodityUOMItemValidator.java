/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityuom/item/CommodityUOMItemValidator.java,v 1.7 2006/11/06 10:15:26 nkumar Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityuom.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;

/**
 * Description
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/11/06 10:15:26 $ Tag: $Name: $
 */

public class CommodityUOMItemValidator {
	public static ActionErrors validateInput(CommodityUOMItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getCommoditySubType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("commoditySubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"20"));
		}
		if (!(errorCode = Validator.checkString(aForm.getUnitOfMeasure(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("unitOfMeasure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"20"));
		}
		if (!(errorCode = Validator.checkNumber(aForm.getMarketUOMVal(), true, 0,
				CommodityGlobalConstants.MAX_QTY_VALUE, 5, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("marketUOMVal", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityGlobalConstants.MAX_QTY_VALUE_STR));
		}
		if (!(errorCode = Validator.checkString(aForm.getMarketUOMUnit(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("marketUOMUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"40"));
		}
		if (!(errorCode = Validator.checkNumber(aForm.getMetricUOMVal(), true, 0,
				CommodityGlobalConstants.MAX_QTY_VALUE, 5, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("metricUOMVal", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityGlobalConstants.MAX_QTY_VALUE_STR));
		}
		// if (aForm.getMetricUOMVal() != null &&
		// aForm.getMetricUOMVal().length() > 0) {
		// DefaultLogger.debug("CommodityUOMItemValidator",
		// "========== metricuomval: "+aForm.getMetricUOMVal()+"========");
		if (!(errorCode = Validator.checkString(aForm.getMetricUOMUnit(), true, 0, 40)).equals(Validator.ERROR_NONE)) {
			errors.add("metricUOMUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"40"));
		}
		// }
		return errors;
	}
}
