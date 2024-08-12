/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/item/ExchangeRateItemMapper.java,v 1.2 2003/08/20 04:21:37 btchng Exp $
 */
package com.integrosys.cms.ui.feed.exchangerate.item;

import java.math.BigDecimal;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/20 04:21:37 $ Tag: $Name: $
 */
public class ExchangeRateItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		return null; // To change body of implemented methods use Options | File
						// Templates.
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		ExchangeRateItemForm form = (ExchangeRateItemForm) aForm;
		String event = form.getEvent();

		if (ExchangeRateItemAction.EVENT_SAVE.equals(event)||event.equals(ExchangeRateItemAction.MAKER_EVENT_UPLOAD_EXCHANGERATEITEM)) {
			// Will return a IForexFeedEntry.

			IForexFeedEntry feedEntry = new OBForexFeedEntry();
			feedEntry.setSystemCode(form.getCurrencyCode());
			String unitPrice = form.getUnitPrice();
			
			//Phase 3 CR:comma separated
			unitPrice=UIUtil.removeComma(unitPrice);
			
			if ((unitPrice != null) && !unitPrice.equals("")) {
				feedEntry.setBuyRate(new BigDecimal(unitPrice));
			}
			if(!event.equals(ExchangeRateItemAction.MAKER_EVENT_UPLOAD_EXCHANGERATEITEM))
			{
				feedEntry.setCurrencyIsoCode(form.getCurrencyIsoCode().toUpperCase());
				feedEntry.setBuyCurrency(form.getCurrencyIsoCode().toUpperCase());
			}
			if((form.getRestrictionType()!=null) && !(form.getRestrictionType().equals(""))){
				feedEntry.setRestrictionType(form.getRestrictionType().toUpperCase());
			}
			feedEntry.setCurrencyDescription(form.getCurrencyDescription());
			feedEntry.setStatus(form.getStatus());
			//File Upload
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	        {
				feedEntry.setFileUpload(form.getFileUpload());
	        }
			return feedEntry;
			// } else if (ExchangeRateItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}
}
