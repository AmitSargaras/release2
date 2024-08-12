/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccountry;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;

/**
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/28 09:02:07 $ Tag: $Name: $
 */

public class SecurityCountryMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SecurityCountryMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
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
		SecurityCountryForm aForm = (SecurityCountryForm) cForm;
		OBItem item = new OBItem();
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		item.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpDate()));
		item.setItemDesc(aForm.getDocDesc());
		item.setItemCode(aForm.getDocCode());
		item.setMonitorType(aForm.getMonitorType());
		item.setDocumentVersion(aForm.getDocVersion());
		item.setLoanApplicationType(aForm.getAppendLoanList());
		item.loadLoanAppTypes();
		if(aForm.getIsPreApprove().equals("Y")){
			item.setIsPreApprove(true);
		}else{
			item.setIsPreApprove(false);
		}
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
		SecurityCountryForm aForm = (SecurityCountryForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			ITemplateItem tempOb = (ITemplateItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setExpDate(DateUtil.formatDate(locale, tempOb.getItem().getExpiryDate()));
			aForm.setMonitorType(tempOb.getItem().getMonitorType());
			//aForm.setLoanApplicationType(tempOb.getItem().getLoanApplicationType());
			aForm.setDocVersion(tempOb.getItem().getDocumentVersion());
			if(tempOb.getItem().getIsPreApprove()){
				aForm.setIsPreApprove("Y");
			}else{
				aForm.setIsPreApprove("N");
			}
		//	aForm.setLoanApplicationList(tempOb.getItem().getLoanApplicationType().split("-"));
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
