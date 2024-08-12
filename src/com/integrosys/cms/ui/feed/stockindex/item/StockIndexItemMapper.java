/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stockindex/item/StockIndexItemMapper.java,v 1.3 2003/08/29 02:57:28 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stockindex.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.OBStockIndexFeedEntry;

/**
 * 
 * @author $Author: btchng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/29 02:57:28 $ Tag: $Name: $
 */
public class StockIndexItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		StockIndexItemForm form = (StockIndexItemForm) aForm;
		String event = form.getEvent();

		if (StockIndexItemAction.EVENT_SAVE.equals(event)) {
			// Will return a IStockIndexFeedEntry.

			IStockIndexFeedEntry feedEntry = new OBStockIndexFeedEntry();

			feedEntry.setName(form.getName());
			feedEntry.setIsinCode(form.getIsinCode());
			feedEntry.setRic(form.getRic());
			String unitPrice = form.getUnitPrice();
			if ((unitPrice != null) && !unitPrice.equals("")) {
				feedEntry.setUnitPrice(Double.parseDouble(form.getUnitPrice()));
			}
			return feedEntry;
			// } else if (StockIndexItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}
}
