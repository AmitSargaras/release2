/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/item/BondItemMapper.java,v 1.6 2004/03/29 10:44:10 sathish Exp $
 */
package com.integrosys.cms.ui.feed.mutualfunds.item;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsItemMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm form, Object object, HashMap hashMap) throws MapperException {
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form");

			MutualFundsItemForm aForm = (MutualFundsItemForm) form;
			aForm.setSchemeCode((String) object);

			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in MutualFundsItemMapper is" + e);
		}
		return null;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...)");

		MutualFundsItemForm form = (MutualFundsItemForm) aForm;
		String event = form.getEvent();

		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (MutualFundsItemAction.EVENT_SAVE.equals(event)|| event.equals(MutualFundsItemAction.MAKER_EVENT_UPLOAD_MUTUALFUNDSITEM)) {
			// Will return a IMutualFundsFeedEntry.

			IMutualFundsFeedEntry feedEntry = new OBMutualFundsFeedEntry();

			feedEntry.setSchemeCode(form.getSchemeCode());
			feedEntry.setSchemeName(form.getSchemeName());
			feedEntry.setSchemeType(form.getSchemeType());
			if(!event.equals(MutualFundsItemAction.MAKER_EVENT_UPLOAD_MUTUALFUNDSITEM))
			{
				//Phase 3 CR:comma separated
				String currentNAV = form.getCurrentNAV();
				currentNAV=UIUtil.removeComma(currentNAV);
				
			feedEntry.setCurrentNAV(Double.parseDouble(currentNAV));
			}
			
			if ((form.getStartDate() != null) && !form.getStartDate().equals("")) {
				feedEntry.setStartDate(DateUtil.convertDate(locale, form.getStartDate()));
			}
			
			if ((form.getExpiryDate() != null) && !form.getExpiryDate().equals("")) {
				feedEntry.setExpiryDate(DateUtil.convertDate(locale, form.getExpiryDate()));
			}
			
			if ((form.getLastUpdatedDate() != null) && !form.getLastUpdatedDate().equals("")) {
				feedEntry.setLastUpdatedDate(DateUtil.convertDate(locale, form.getLastUpdatedDate()));
			}else{
				feedEntry.setLastUpdatedDate(new Date());
			}
			//Govind S:25/08/2011 , File Upload Master
			if(form.getFileUpload()!=null && (!form.getFileUpload().equals("")))
	        {
				feedEntry.setFileUpload(form.getFileUpload());
	        }
			//Govind S: End here File Upload Master
			return feedEntry;
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
