/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/item/BondItemMapper.java,v 1.6 2004/03/29 10:44:10 sathish Exp $
 */
package com.integrosys.cms.ui.feed.bond.item;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.OBBondFeedEntry;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/03/29 10:44:10 $ Tag: $Name: $
 */
public class BondItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form");

			BondItemForm aForm = (BondItemForm) form;
			//aForm.setCurrencyCode((String) object);

			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in BondItemMapper is" + e);
		}
		return null;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		BondItemForm form = (BondItemForm) aForm;
		String event = form.getEvent();

		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (BondItemAction.EVENT_SAVE.equals(event) || event.equals(BondItemAction.MAKER_EVENT_UPLOAD_BONDITEM)) {
			// Will return a IBondFeedEntry.

			IBondFeedEntry feedEntry = new OBBondFeedEntry();

			feedEntry.setName(form.getName());
			feedEntry.setIsinCode(form.getIsinCode());
			
			feedEntry.setBondCode(form.getBondCode()); 
			
			if(form.getIssueDate() !=null && !form.getIssueDate().equals(""))
				feedEntry.setIssueDate(DateUtil.convertDate(locale,form.getIssueDate()));
			
			feedEntry.setRating(form.getRating());
			
			if ((form.getMaturityDate() != null) && !form.getMaturityDate().equals("")) {
				feedEntry.setMaturityDate(DateUtil.convertDate(locale, form.getMaturityDate()));
			}
			if(form.getCouponRate()!=null && !form.getCouponRate().equals(""))
				feedEntry.setCouponRate(Double.parseDouble(form.getCouponRate()));
			
			//Phase 3 CR:comma separated
				String unitPrice = form.getUnitPrice();
				unitPrice=UIUtil.removeComma(unitPrice);
				
			if(unitPrice != null && !unitPrice.equals(""))
				feedEntry.setUnitPrice(Double.parseDouble(unitPrice));
			//Govind S:25/08/2011 , File Upload Master
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	        {
				feedEntry.setFileUpload(form.getFileUpload());
	        }
			feedEntry.setLastUpdateDate(new Date());
			return feedEntry;
			// } else if (BondItemAction.EVENT_PREPARE.equals(event)) {
			// // Will return a String (currency code).
			// return form.getCurrencyCode();
		}

		return null;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the mapFormToOB method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale",
				GLOBAL_SCOPE }, };
	}
}
