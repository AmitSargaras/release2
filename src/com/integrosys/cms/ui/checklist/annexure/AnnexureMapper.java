/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListSubItem;
import com.integrosys.cms.ui.common.FrequencyList;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/08/18 10:53:26 $ Tag: $Name: $
 */

public class AnnexureMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public AnnexureMapper() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "annexureItem", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckListItem", SERVICE_SCOPE },
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
		// IRecurrentCheckListItem recurrentItem = null;
		IRecurrentCheckListSubItem recurrentSubItem = null;
		AnnexureForm aForm = (AnnexureForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		if ("update_recurrent_item".equals(cForm.getEvent())) {
			try {
				recurrentSubItem = (IRecurrentCheckListSubItem) AccessorUtil.deepClone(map.get("recurrentSubItem"));
			}
			catch (IOException ex) {
				throw new MapperException("failed to clone recurrent sub item object [" + map.get("recurrentSubItem")
						+ "]", ex);
			}
			catch (ClassNotFoundException ex) {
				throw new MapperException("failed to find the class of recurrent sub item object ["
						+ map.get("recurrentSubItem") + "], possible? ", ex);
			}

			// Print Reminder
			recurrentSubItem.setIsPrintReminderInd(aForm.getIsPrintReminderSet());

			// Deferred Date
			if ((aForm.getDeferredDate() != null) && !aForm.getDeferredDate().equals("")) {
				DefaultLogger.debug(this, "deferred date: " + aForm.getDeferredDate());
				recurrentSubItem.setDeferredDate(compareDate(locale, recurrentSubItem.getDeferredDate(), aForm
						.getDeferredDate()));
			}
			else {
				// so that change of deferred date to empty string will be
				// persisted
				recurrentSubItem.setDeferredDate(null);
			}

			// Date Received
			if ((aForm.getDateReceived() != null) && !aForm.getDateReceived().equals("")) {
				// sets the date received value into recurrentSubItem
				DefaultLogger.debug(this, "date received: " + aForm.getDateReceived());
				recurrentSubItem.setReceivedDate(compareDate(locale, recurrentSubItem.getReceivedDate(), aForm
						.getDateReceived()));
			}
			else {
				// so that change of date received to empty string will be
				// persisted
				recurrentSubItem.setReceivedDate(null);
			}

		}

		// returns recurrent subItem instead of recurrent item, coz change is
		// made to subItem.
		return recurrentSubItem;
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
		AnnexureForm aForm = (AnnexureForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FrequencyList list = FrequencyList.getInstance();
		IRecurrentCheckListItem recurrentItem = null;
		IRecurrentCheckListSubItem recurrentSubItem = null;

		IItem item = null;
		if (obj != null) {

			// check that obj is an instance of IRecurrentCheckListItem before
			// casting it
			if (obj instanceof IRecurrentCheckListItem) {
				recurrentItem = (IRecurrentCheckListItem) obj;

				// Fields from IRecurrentCheckListItem
				if (recurrentItem != null) {
					item = recurrentItem.getItem();

					// Document Description
					if (item != null) {
						aForm.setAnnexureName(item.getItemDesc());
					}
				}
				return aForm;
			}
			else if (obj instanceof IRecurrentCheckListSubItem) {
				// check that obj is an instance of IRecurrentCheckListSubItem
				// before casting it
				recurrentSubItem = (IRecurrentCheckListSubItem) obj;

				if (recurrentSubItem != null) {

					// Due Date (subItem's dueDate)
					DefaultLogger.debug(this, "subItem dueDate: " + recurrentSubItem.getDueDate());
					if (recurrentSubItem.getDueDate() != null) {
						aForm.setDueDate(DateUtil.formatDate(locale, recurrentSubItem.getDueDate()));
					}

					// Status
					if ((recurrentSubItem.getStatus() != null) && !recurrentSubItem.getStatus().equals("")) {
						aForm.setRecurrentStatus(recurrentSubItem.getStatus());
					}

					// Deferred Date
					if (recurrentSubItem.getDeferredDate() != null) {
						aForm.setDeferredDate(DateUtil.formatDate(locale, recurrentSubItem.getDeferredDate()));
					}

					// Date Received
					if (recurrentSubItem.getReceivedDate() != null) {
						aForm.setDateReceived(DateUtil.formatDate(locale, recurrentSubItem.getReceivedDate()));
					}

					// Print Reminder
					aForm.setIsPrintReminderSet(recurrentSubItem.getIsPrintReminderInd());
				}
				return aForm;
			}
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	private static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}

		return returnDate;
	}

}
