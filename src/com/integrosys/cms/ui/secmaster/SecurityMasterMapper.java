/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.secmaster;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 08:58:58 $ Tag: $Name: $
 */

public class SecurityMasterMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SecurityMasterMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "secTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		SecurityMasterForm aForm = (SecurityMasterForm) cForm;
		OBItem item = new OBItem();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		item.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpDate()));
		item.setItemDesc(aForm.getDocDesc());
		item.setMonitorType(aForm.getMonitorType());
		

		DefaultLogger.debug(this, "Document object in Mapper" + item);
		return item;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		SecurityMasterForm aForm = (SecurityMasterForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			ITemplateItem tempOb = (ITemplateItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setExpDate(DateUtil.formatDate(locale, tempOb.getItem().getExpiryDate()));
			aForm.setTenureType(tempOb.getTenureType());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
