/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.ccreceipt;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/10/07 07:53:01 $ Tag: $Name: $
 */

public class CheckListMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CheckListMapper() {
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		ICheckList checkList = (ICheckList) map.get("checkList");
		CCReceiptForm aForm = (CCReceiptForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		checkList.setLegalFirm(aForm.getLegalFirm());
		checkList.setRemarks(aForm.getMakerStatus());
		checkList.setDeficiencyDate(UIUtil.convertDate(locale, aForm.getDeficiencyDate()));
		checkList.setNextActionDate(UIUtil.convertDate(locale, aForm.getNextActionDate()));

		return checkList;
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

		CCReceiptForm aForm = (CCReceiptForm) cForm;
		ICheckList checkList = (ICheckList) obj;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		aForm.setLegalFirm(checkList.getLegalFirm());
		aForm.setMakerStatus(checkList.getRemarks());
		Date deficiencyDate = checkList.getDeficiencyDate();
		aForm.setDeficiencyDate(DateUtil.formatDate(locale, deficiencyDate));
		if (deficiencyDate != null) {
			Date todayDate = UIUtil.getDate();
			if (deficiencyDate.before(todayDate)) {
				aForm.setDaysDeficient(String.valueOf(CommonUtil.dateDiff(UIUtil.getDate(), deficiencyDate,
						Calendar.DATE)));
			}
			else {
				aForm.setDaysDeficient("0");
			}
		}
		aForm.setNextActionDate(DateUtil.formatDate(locale, checkList.getNextActionDate()));

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE }, });
	}
}
