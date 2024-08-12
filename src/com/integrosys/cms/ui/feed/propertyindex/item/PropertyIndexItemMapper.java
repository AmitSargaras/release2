/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/propertyindex/item/PropertyIndexItemMapper.java,v 1.1 2003/08/20 11:00:53 btchng Exp $
 */
package com.integrosys.cms.ui.feed.propertyindex.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.propertyindex.IPropertyIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.propertyindex.OBPropertyIndexFeedEntry;

/**
 * 
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 11:00:53 $ Tag: $Name: $
 */
public class PropertyIndexItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		PropertyIndexItemForm form = (PropertyIndexItemForm) aForm;
		String event = form.getEvent();

		if (PropertyIndexItemAction.EVENT_SAVE.equals(event)) {
			// Will return a IPropertyIndexFeedEntry.

			IPropertyIndexFeedEntry feedEntry = new OBPropertyIndexFeedEntry();

			feedEntry.setType(form.getType());
			feedEntry.setRegion(form.getRegion());
			String unitPrice = form.getUnitPrice();
			if ((unitPrice != null) && !unitPrice.equals("")) {
				feedEntry.setUnitPrice(Double.parseDouble(form.getUnitPrice()));
			}
			return feedEntry;
			// } else if (PropertyIndexItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}
}
