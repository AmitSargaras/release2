/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.OBRecurrentCheckListItem;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/08/04 10:00:09 $ Tag: $Name: $
 */

public class RecurrentMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public RecurrentMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "limitProfileId", "java.lang.String", REQUEST_SCOPE },
				{ "subProfileId", "java.lang.String", REQUEST_SCOPE },
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
		IRecurrentCheckListItem recurrentItem = null;
		RecurrentCheckListForm aForm = (RecurrentCheckListForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IRecurrentCheckList recChkLst = (IRecurrentCheckList) map.get("recChkLst");

		try {
			if ("add_recurrent".equals(cForm.getEvent())) {
				recurrentItem = new OBRecurrentCheckListItem();
			}
			else if ("update_recurrent".equals(aForm.getEvent())) {
				// recurrentItem =
				// recChkLst.getCheckListItemList()[Integer.parseInt
				// (aForm.getIndex())];
				recurrentItem = (IRecurrentCheckListItem) AccessorUtil
						.deepClone(recChkLst.getCheckListItemList()[Integer.parseInt(aForm.getIndex())]);
			}
			IItem item = null;
			item = recurrentItem.getItem();
			if (item == null) {
				item = new OBItem();
			}

			// Doc Desc
			item.setItemDesc(aForm.getRecurrentItemDesc());
			recurrentItem.setItem(item);

			// One-Off
			if (!isEmptyOrNull(aForm.getOneOff())) {
				recurrentItem.setIsOneOffInd(Boolean.valueOf(aForm.getOneOff()).booleanValue());
			}

			// Frequency
			DefaultLogger.debug(this, "Frequency " + aForm.getFrequency());
			if ((aForm.getFrequency() != null) && !aForm.getFrequency().trim().equals("")) {
				recurrentItem.setFrequency(Integer.parseInt(aForm.getFrequency().trim()));
			}
			else {
				recurrentItem.setFrequency(Integer.MIN_VALUE);
			}

			if ((aForm.getFrequencyUnit() != null) && !aForm.getFrequencyUnit().equals("")) {
				recurrentItem.setFrequencyUnit(aForm.getFrequencyUnit());
			}
			else {
				recurrentItem.setFrequencyUnit(null);
			}

			// Document End Date
			if ((aForm.getDocEndDate() != null) && !aForm.getDocEndDate().equals("")) {
				recurrentItem.setInitialDocEndDate(DateUtil.convertDate(locale, aForm.getDocEndDate()));
			}

			// Grace Period
			if ((aForm.getGracePeriod() != null) && !aForm.getGracePeriod().equals("")) {
				recurrentItem.setGracePeriod(Integer.parseInt(aForm.getGracePeriod().trim()));
			}
			else {
				recurrentItem.setGracePeriod(Integer.MIN_VALUE);
			}

			// Grace Period Unit
			if ((aForm.getGracePeriodUnit() != null) || !aForm.getGracePeriodUnit().equals("")) {
				recurrentItem.setGracePeriodUnit(aForm.getGracePeriodUnit());
			}
			else {
				recurrentItem.setGracePeriodUnit(null);
			}

			DefaultLogger.debug(this, "due date from form: " + aForm.getDueDate());
			recurrentItem.setInitialDueDate(DateUtil.convertDate(locale, aForm.getDueDate()));

			// Last Document Entry Date
			recurrentItem.setLastDocEntryDate(DateUtil.convertDate(locale, aForm.getLastDocEntryDate()));

			// Chaser / Reminder
			if (!isEmptyOrNull(aForm.getChaserReminder())) {
				recurrentItem.setChaseReminderInd(Boolean.valueOf(aForm.getChaserReminder()).booleanValue());
			}

			// Remarks
			recurrentItem.setRemarks(aForm.getRecurrentItemRemarks());
		}
		catch (Exception e) {
			//DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage(), e);
		}
		return recurrentItem;
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
		RecurrentCheckListForm aForm = (RecurrentCheckListForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if (obj != null) {
			IRecurrentCheckListItem tempOb = (IRecurrentCheckListItem) obj;
			IItem item = tempOb.getItem();

			// Document Description
			if (item != null) {
				aForm.setRecurrentItemDesc(item.getItemDesc());
			}

			// One-Off
			aForm.setOneOff(String.valueOf(tempOb.getIsOneOffInd()));

			// Due Date
			aForm.setDueDate(DateUtil.formatDate(locale, tempOb.getInitialDueDate()));

			// Remarks
			aForm.setRecurrentItemRemarks(tempOb.getRemarks());

			// Frequency
			if (tempOb.getFrequency() != Integer.MIN_VALUE) {
				aForm.setFrequency(String.valueOf(tempOb.getFrequency()));
			}
			aForm.setFrequencyUnit(tempOb.getFrequencyUnit());

			// Document End Date
			aForm.setDocEndDate(DateUtil.formatDate(locale, tempOb.getInitialDocEndDate()));

			// Grace Period
			if (tempOb.getGracePeriod() != Integer.MIN_VALUE) {
				aForm.setGracePeriod(String.valueOf(tempOb.getGracePeriod()));
			}
			aForm.setGracePeriodUnit(tempOb.getGracePeriodUnit());

			// Last Document Entry Date
			aForm.setLastDocEntryDate(DateUtil.formatDate(locale, tempOb.getLastDocEntryDate()));

			// Chaser / Reminder
			DefaultLogger.debug(this, "chaser/reminder: " + tempOb.getChaseReminderInd());
			aForm.setChaserReminder(String.valueOf(tempOb.getChaseReminderInd()));

			// For preventing changing of one-off
			if ((tempOb.getRecurrentCheckListSubItemList() != null)
					&& (tempOb.getRecurrentCheckListSubItemList().length > 1)) {
				aForm.setHasMoreSubItems("true");
			}

			return aForm;
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

}
