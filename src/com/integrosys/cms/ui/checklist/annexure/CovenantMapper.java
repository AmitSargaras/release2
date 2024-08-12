/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 *//*
package com.integrosys.cms.ui.checklist.annexure;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IConvenantSubItem;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.FrequencyList;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
w
*//**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/08/18 10:53:26 $ Tag: $Name: $
 *//*

public class CovenantMapper extends AbstractCommonMapper {
	*//**
	 * Default Construtor
	 *//*
	public CovenantMapper() {
	}

	*//**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 *//*
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", SERVICE_SCOPE },
				{ "covenantSubItem", "com.integrosys.cms.app.recurrent.bus.IConvenantSubItem", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}

	*//**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 *//*
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "Inside Map Form to OB ");
		IConvenantSubItem convenantSubItem = null;
		AnnexureForm aForm = (AnnexureForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if ("update_covenant_item".equals(aForm.getEvent())) {
			try {
				convenantSubItem = (IConvenantSubItem) AccessorUtil.deepClone(map.get("covenantSubItem"));
			}
			catch (IOException ex) {
				throw new MapperException("failed to clone covenant sub item object [" + map.get("covenantSubItem")
						+ "]", ex);
			}
			catch (ClassNotFoundException ex) {
				throw new MapperException("failed to find the class of covenant sub item object ["
						+ map.get("covenantSubItem") + "], possible? ", ex);
			}

			// cr 26
			
			 * // Print Reminder convenantSubItem.setIsPrintReminderInd(aForm
			 * .getIsPrintReminderSet());
			 
			// verified indicator
			convenantSubItem.setIsVerifiedInd(aForm.getIsVerified());

			// Deferred Date
			if ((aForm.getDeferredDate() != null) && !aForm.getDeferredDate().equals("")) {
				DefaultLogger.debug(this, "deferred date: " + aForm.getDeferredDate());
				convenantSubItem.setDeferredDate(compareDate(locale, convenantSubItem.getDeferredDate(), aForm
						.getDeferredDate()));
			}
			else {
				// so that change of deferred date to empty string will be
				// persisted
				convenantSubItem.setDeferredDate(null);
			}

			// Date Checked
			if ((aForm.getDateChecked() != null) && !aForm.getDateChecked().equals("")) {
				// sets the date checked value into recurrentSubItem
				DefaultLogger.debug(this, "date checked: " + aForm.getDateChecked());
				convenantSubItem.setCheckedDate(compareDate(locale, convenantSubItem.getCheckedDate(), aForm
						.getDateChecked()));
				DefaultLogger.debug(this, "date checked: subitem" + convenantSubItem.getCheckedDate());
			}
			else {
				// so that change of date received to empty string will be
				// persisted
				convenantSubItem.setCheckedDate(null);
			}

			// Date Waived
			if (isEmptyOrNull(aForm.getDateWaived())) {
				convenantSubItem.setWaivedDate(null);
			}
			else {
				convenantSubItem.setWaivedDate(compareDate(locale, convenantSubItem.getWaivedDate(), aForm
						.getDateWaived()));
			}

			convenantSubItem.setRemarks(aForm.getRecurrentSubItemRemarks());
			convenantSubItem.setActionParty(aForm.getActionParty());
		}

		return convenantSubItem;
	}

	*//**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 *//*
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "inside mapOb to form ");
		AnnexureForm aForm = (AnnexureForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		FrequencyList list = FrequencyList.getInstance();
		IConvenant convenant = null;
		IConvenantSubItem convenantSubItem = null;
		try {

			if (obj != null) {
				// check that obj is an instance of IConvenant before casting it
				if (obj instanceof IConvenant) {
					convenant = (IConvenant) obj;

					// Fields from IConvenant
					if (convenant != null) {

						aForm.setCovenantItemRemarks(convenant.getRemarks());
						// aForm.setDateChecked(DateUtil.formatDate(locale,
						// convenant.getDateChecked()));
						// aForm.setIsVerified(convenant.getIsVerifiedInd());

						// Document Description
						if (convenant.getIsParameterizedDesc()) {
							aForm.setCovenantItemDesc(findLabelFromValue(convenant.getDescription(),
									"COVENANT_CONDITION"));
						}
						else {
							aForm.setCovenantItemDesc(convenant.getDescription());
						}

						// One-Off
						if (convenant.getIsOneOffInd()) {
							aForm.setOneOff("Yes");
						}
						else {
							aForm.setOneOff("No");
						}

						// Risk Trigger
						// Added by Pratheepa for CR234
						if (convenant.getRiskTrigger()) {
							aForm.setRiskTrigger("Yes");
						}
						else {
							aForm.setRiskTrigger("No");
						}

						// Fee
						if (convenant.getFee()) {
							aForm.setFee("Yes");
						}
						else {
							aForm.setFee("No");
						}

						// Frequency
						if (convenant.getFrequency() != Integer.MIN_VALUE) {
							aForm.setFrequency(String.valueOf(convenant.getFrequency()));
							String freUnit = list.getFrequencyValue(convenant.getFrequencyUnit());
							aForm.setFrequencyUnit(freUnit);
						}

						// Grace Period
						if (convenant.getGracePeriod() != Integer.MIN_VALUE) {
							aForm.setGracePeriod(String.valueOf(convenant.getGracePeriod()));
							String gracePeriodUnit = list.getFrequencyValue(convenant.getGracePeriodUnit());
							aForm.setGracePeriodUnit(gracePeriodUnit);
						}
						
						 * // Chaser / Reminder if
						 * (convenant.getChaseReminderInd()) {
						 * aForm.setChaserReminder("Yes"); } else {
						 * aForm.setChaserReminder("No"); }
						 
					}
					return aForm;
				}
				else if (obj instanceof IConvenantSubItem) {
					// check that obj is an instance of IConvenantSubItem before
					// casting it
					convenantSubItem = (IConvenantSubItem) obj;

					if (convenantSubItem != null) {
						// Fields from IRecurrentCheckListSubItem
						// Document End Date (subItem's docEndDate)
						if (convenantSubItem.getDocEndDate() != null) {
							aForm.setDocEndDate(DateUtil.formatDate(locale, convenantSubItem.getDocEndDate()));
						}

						// verified
						aForm.setIsVerified(convenantSubItem.getIsVerifiedInd());

						// Due Date (subItem's dueDate)
						if (convenantSubItem.getDueDate() != null) {
							aForm.setDueDate(DateUtil.formatDate(locale, convenantSubItem.getDueDate()));
						}

						if ((convenantSubItem.getStatus() != null) && !convenantSubItem.getStatus().equals("")) {
							aForm.setRecurrentStatus(convenantSubItem.getStatus());
						}

						// Deferred Date
						if (convenantSubItem.getDeferredDate() != null) {
							aForm.setDeferredDate(DateUtil.formatDate(locale, convenantSubItem.getDeferredDate()));
						}

						// Date Checked
						if (convenantSubItem.getCheckedDate() != null) {
							aForm.setDateChecked(DateUtil.formatDate(locale, convenantSubItem.getCheckedDate()));
						}

						// Date Waived
						aForm.setDateWaived(DateUtil.formatDate(locale, convenantSubItem.getWaivedDate()));

						// Print Reminder
						aForm.setIsPrintReminderSet(convenantSubItem.getIsPrintReminderInd());

						// Remarks
						aForm.setRecurrentSubItemRemarks(convenantSubItem.getRemarks());

						// Frequency
						if (convenantSubItem.getFrequency() != Integer.MIN_VALUE) {
							aForm.setFrequency(String.valueOf(convenantSubItem.getFrequency()));
							String freUnit = list.getFrequencyValue(convenantSubItem.getFrequencyUnit());
							aForm.setFrequencyUnit(freUnit);
						}

						aForm.setActionParty(convenantSubItem.getActionParty());

						// Grace Period
						if (convenantSubItem.getGracePeriod() != Integer.MIN_VALUE) {
							aForm.setGracePeriod(String.valueOf(convenantSubItem.getGracePeriod()));
							String gracePeriodUnit = list.getFrequencyValue(convenantSubItem.getGracePeriodUnit());
							aForm.setGracePeriodUnit(gracePeriodUnit);
						}
					}
					return aForm;
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}

	// cr26
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

	public String findValueFromLabel(String label, String commonCodeStr) {
		CommonCodeList commonCode = CommonCodeList.getInstance(commonCodeStr);

		String[] objectValue = (String[]) commonCode.getCommonCodeValues().toArray(new String[0]);
		String[] objectLabel = (String[]) commonCode.getCommonCodeLabels().toArray(new String[0]);

		int chosen = 0;
		for (int i = 0; i < objectValue.length; i++) {
			if (objectLabel[i].equals(label)) {
				chosen = i;
			}
		}
		return objectValue[chosen];
	}

	public String findLabelFromValue(String value, String commonCodeStr) {
		CommonCodeList commonCode = CommonCodeList.getInstance(commonCodeStr);

		String[] objectValue = (String[]) commonCode.getCommonCodeValues().toArray(new String[0]);
		String[] objectLabel = (String[]) commonCode.getCommonCodeLabels().toArray(new String[0]);

		int chosen = 0;
		for (int i = 0; i < objectValue.length; i++) {
			if (objectValue[i].equals(value)) {
				chosen = i;
			}
		}
		return objectLabel[chosen];
	}

}
*/