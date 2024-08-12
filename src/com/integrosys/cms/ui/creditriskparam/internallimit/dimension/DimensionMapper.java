/*
 Copyright Integro Technologies Pte Ltd
 $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ExchangeRateListMapper.java,v 1.39 2005/08/05 10:12:13 hshii Exp $
 */
package com.integrosys.cms.ui.creditriskparam.internallimit.dimension;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.IDimension;
import com.integrosys.cms.app.creditriskparam.bus.internallimit.OBDimension;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */
public class DimensionMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object,
			HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "inside mapOBToForm.");
		Locale locale = (Locale) hashMap
				.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DimensionForm form = (DimensionForm) aForm;
		IDimension dimention = (IDimension) object;
		if (dimention == null) {
			return form;
		}
		form.setSubLimitType(dimention.getSubLimitType());
		form.setDescription(dimention.getDescription());
		form.setLimitAmount(toString(dimention.getLimitAmount()));
		form.setLimitCurrency(dimention.getLimitCurrency());

		form.setLastReviewedDate(DateUtil.formatDate(locale, dimention
				.getLastReviewedDate()));
		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap)
			throws MapperException {
		DefaultLogger.debug(this, "inside mapFormToOB.");
		Locale locale = (Locale) hashMap
		.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DimensionForm form = (DimensionForm) aForm;
		IDimension dimention = new OBDimension();

		dimention.setSubLimitType(form.getSubLimitType());
		dimention.setDescription(form.getDescription());
		dimention.setLimitAmount(Double.parseDouble(form.getLimitAmount()));
		dimention.setLimitCurrency(form.getLimitCurrency());
		dimention.setLastReviewedDate(UIUtil.convertDate(locale,form.getLastReviewedDate()));
		return dimention;
	}

	private String toString(double aDouble) {
		if (ICMSConstant.LONG_INVALID_VALUE == aDouble) {
			return "";
		} else {
			return String.valueOf(aDouble);
		}
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { {
				com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,
				"java.util.Locale", GLOBAL_SCOPE } });
	}

}
