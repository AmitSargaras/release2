/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccountry;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/05 12:10:46 $ Tag: $Name: $
 */

public class CCCountryTemplateMapper extends AbstractCommonMapper {

	public static final String IS_BORROWER = "isBorrower";

	public static final String IS_PLEDGOR = "isPledgor";

	/**
	 * Default Construtor
	 */
	public CCCountryTemplateMapper() {
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
		CCCountryForm aForm = (CCCountryForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		OBItem item = new OBItem();
		item.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpDate()));
		item.setItemDesc(aForm.getDocDesc());
		item.setItemCode(aForm.getDocCode());
		item.setDocumentVersion(aForm.getDocVersion());
		item.setIsForBorrower(IS_BORROWER.equals(aForm.getIsForBorrower()));
		item.setIsForPledgor(IS_PLEDGOR.equals(aForm.getIsForPledgor()));
		item.setIsPreApprove(ICMSConstant.TRUE_VALUE.equals(aForm.getIsPreApprove()));
		item.setLoanApplicationType(aForm.getAppendLoanList());
		item.loadLoanAppTypes();
		
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
		CCCountryForm aForm = (CCCountryForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (obj != null) {
			ITemplateItem tempOb = (ITemplateItem) obj;
			aForm.setDocCode(tempOb.getItem().getItemCode());
			aForm.setDocDesc(tempOb.getItem().getItemDesc());
			aForm.setDocVersion(tempOb.getItem().getDocumentVersion());
			aForm.setExpDate(DateUtil.formatDate(locale, tempOb.getItem().getExpiryDate()));
			aForm.setIsForBorrower(tempOb.getItem().getIsForBorrower() ? IS_BORROWER : "");
			aForm.setIsForPledgor(tempOb.getItem().getIsForPledgor() ? IS_PLEDGOR : "");
			aForm.setIsPreApprove(tempOb.getItem().getIsPreApprove() ? ICMSConstant.TRUE_VALUE
					: ICMSConstant.FALSE_VALUE);
			//aForm.setLoanApplicationType(tempOb.getItem().getLoanApplicationType());
			//aForm.setLoanApplicationList(tempOb.getItem().getLoanApplicationType().split("-"));
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
