package com.integrosys.cms.ui.feed.gold.item;

import java.math.BigDecimal;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedEntry;

public class GoldItemMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		GoldItemForm form = (GoldItemForm) aForm;
		String event = form.getEvent();

		if (GoldItemAction.EVENT_SAVE.equals(event)) {
			// Will return a IGoldFeedEntry.

			IGoldFeedEntry feedEntry = new OBGoldFeedEntry();
			feedEntry.setGoldGradeNum(form.getGoldGradeNum());
			feedEntry.setUnitMeasurementNum(form.getUnitMeasurementNum());
			feedEntry.setCurrencyCode(form.getCurrencyCode());
			String unitPrice = form.getUnitPrice();
			if ((unitPrice != null) && !unitPrice.equals("")) {
				feedEntry.setUnitPrice(new BigDecimal(form.getUnitPrice()));
			}
			return feedEntry;
		}

		return null;
	}

	public CommonForm mapOBToForm(CommonForm arg0, Object arg1, HashMap arg2) throws MapperException {
		//Do nothing
		return null;
	}

}
