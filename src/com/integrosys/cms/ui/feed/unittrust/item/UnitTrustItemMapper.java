/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/unittrust/item/UnitTrustItemMapper.java,v 1.3 2003/08/20 04:21:37 btchng Exp $
 */
package com.integrosys.cms.ui.feed.unittrust.item;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedEntry;
import com.integrosys.base.techinfra.util.DateUtil;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/20 04:21:37 $ Tag: $Name: $
 */
public class UnitTrustItemMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
	
	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "entering mapOBToForm(...)");

		IUnitTrustFeedEntry iObj = (IUnitTrustFeedEntry) object;
		UnitTrustItemForm aForm = (UnitTrustItemForm) form;
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String event = form.getEvent();

		aForm.setName(iObj.getName());
		aForm.setIsinCode(iObj.getIsinCode());
		aForm.setFundCode(iObj.getFundCode());
		aForm.setRic(iObj.getRic());
		aForm.setCurrencyCode(iObj.getCurrencyCode());
		aForm.setUnitPrice(Double.toString(iObj.getUnitPrice()));
		aForm.setProductCode(iObj.getProductCode());
		aForm.setFundManagerCode(iObj.getFundManagerCode());
		aForm.setFundManagerName(iObj.getFundManagerName());
		if (iObj.getFundSize()==null) aForm.setFundSize(""); 
		else aForm.setFundSize(iObj.getFundSize()+"");
		if (locale!=null) {
			if (iObj.getFundSizeUpdateDate()!=null) 
				aForm.setFundSizeUpdateDate(DateUtil.formatDate(locale, iObj.getFundSizeUpdateDate()));
			if (iObj.getDateLaunched()!=null) 
				aForm.setDateLaunched(DateUtil.formatDate(locale, iObj.getDateLaunched()));
		}
		
		return aForm; // To change body of implemented methods use Options | File
						// Templates.
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		UnitTrustItemForm form = (UnitTrustItemForm) aForm;
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String event = form.getEvent();

		if (UnitTrustItemAction.EVENT_SAVE.equals(event)) {
			// Will return a IUnitTrustFeedEntry.

			IUnitTrustFeedEntry feedEntry = new OBUnitTrustFeedEntry();
			feedEntry.setName(form.getName());
			feedEntry.setIsinCode(form.getIsinCode());
			feedEntry.setFundCode(form.getFundCode());
			feedEntry.setRic(form.getRic());
			feedEntry.setCurrencyCode(form.getCurrencyCode());
			String unitPrice = form.getUnitPrice();
			if ((unitPrice != null) && !unitPrice.equals("")) {
				feedEntry.setUnitPrice(Double.parseDouble(form.getUnitPrice()));
			}

			feedEntry.setProductCode(form.getProductCode());
			feedEntry.setFundManagerCode(form.getFundManagerCode());
			feedEntry.setFundManagerName(form.getFundManagerName());
			if (!AbstractCommonMapper.isEmptyOrNull(form.getFundSize()))
				feedEntry.setFundSize(new Long(Long.parseLong(form.getFundSize())));
			feedEntry.setFundSizeUpdateDate(DateUtil.convertDate(locale, form.getFundSizeUpdateDate()));
			feedEntry.setDateLaunched(DateUtil.convertDate(locale, form.getDateLaunched()));

			return feedEntry;
			// } else if (UnitTrustItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}
}
