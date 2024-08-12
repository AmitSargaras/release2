/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/item/StockItemMapper.java,v 1.3 2003/08/20 04:21:37 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stock.item;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.OBStockFeedEntry;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: btchng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/20 04:21:37 $ Tag: $Name: $
 */
public class StockItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form");

			StockItemForm aForm = (StockItemForm) form;
			//aForm.setCurrencyCode((String) object);

			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in StockItemMapper is" + e);
		}
		return null;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		StockItemForm form = (StockItemForm) aForm;
		String event = form.getEvent();

		if (StockItemAction.EVENT_SAVE.equals(event) || event.equals(StockItemAction.MAKER_EVENT_UPLOAD_STOCKITEM)) {
			// Will return a IStockFeedEntry.

			IStockFeedEntry feedEntry = new OBStockFeedEntry();

			feedEntry.setScriptCode(form.getScriptCode());
			feedEntry.setScriptName(form.getScriptName());
			
			//Phase 3 CR:comma separated
			String scriptValue = form.getScriptValue();
			scriptValue=UIUtil.removeComma(scriptValue);
			
			if ((scriptValue != null) && !scriptValue.equals("")) {
				feedEntry.setScriptValue(Double.parseDouble(scriptValue));
			}
				
//			if ((form.getScriptValue() != null) && !form.getScriptValue().equals("")) {
//				feedEntry.setScriptValue(Double.parseDouble(form.getScriptValue()));
//			}
			
			feedEntry.setStockExchangeName(form.getStockExchangeName());

			String faceValue = form.getFaceValue();
			
			//Phase 3 CR:comma separated
			faceValue=UIUtil.removeComma(faceValue);
			
			if ((faceValue != null) && !faceValue.equals("")) {
				feedEntry.setFaceValue(Double.parseDouble(faceValue));
			}

			if (!AbstractCommonMapper.isEmptyOrNull(form.getLastUpdateDate())) {
				Date returnDate = DateUtil.convertDate(locale, form.getLastUpdateDate());
				feedEntry.setLastUpdatedDate(returnDate);
			}
			else {
				feedEntry.setExpiryDate(new Date());
			}
			//Govind S:25/08/2011 , File Upload Master
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	        {
				feedEntry.setFileUpload(form.getFileUpload());
	        }

			return feedEntry;
			// } else if (StockItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}
}
