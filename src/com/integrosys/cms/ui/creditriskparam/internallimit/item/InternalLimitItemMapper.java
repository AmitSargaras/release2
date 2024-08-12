/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ExchangeRateListMapper.java,v 1.39 2005/08/05 10:12:13 hshii Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IInternalLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBInternalLimitParameter;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */
public class InternalLimitItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object,
			HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");

		InternalLimitItemForm form = (InternalLimitItemForm) aForm;
		IInternalLimitParameter ilParam = (IInternalLimitParameter) object;
		if (ilParam == null) {
			return form;
		}
	//	form.setDescription(ilParam.getDescription());
		form.setInternalLimitPercentage(toString(ilParam
				.getInternalLimitPercentage()));
		//form.setCurrency(ilParam.getCurrency());
		form.setCapitalFundAmount(toString(ilParam.getCapitalFundAmount()));
		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		InternalLimitItemForm form = (InternalLimitItemForm) aForm;
		IInternalLimitParameter ilParam = new OBInternalLimitParameter();

		//ilParam.setDescription(form.getDescription());
		ilParam.setInternalLimitPercentage(Double.parseDouble(form
				.getInternalLimitPercentage()));
		//ilParam.setCurrency(form.getCurrency());
		ilParam.setCapitalFundAmount(Double.parseDouble(form
				.getCapitalFundAmount()));

		return ilParam;
	}

	private String toString(double aDouble) {
		if (ICMSConstant.LONG_INVALID_VALUE == aDouble) {
			return "";
		} else {
			return String.valueOf(aDouble);
		}
	}
}
