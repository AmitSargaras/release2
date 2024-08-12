/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrent;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.OBConvenant;
import com.integrosys.cms.ui.common.CommonCodeList;


/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/09/21 12:30:04 $ Tag: $Name: $
 */

public class CovenantMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CovenantMapper() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "covenantItem", "com.integrosys.cms.app.recurrent.bus.IConvenant", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } });
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
		IConvenant covenant = null;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		RecurrentCheckListForm aForm = (RecurrentCheckListForm) cForm;
		if ("add_covenant".equals(cForm.getEvent())) {
			covenant = new OBConvenant();
		}
		if ("update_covenant".equals(aForm.getEvent())) {
			try {
				covenant = (IConvenant) AccessorUtil.deepClone(map.get("covenantItem"));
			}
			catch (Exception e) {
				throw new MapperException(e.getClass().getName() + " : " + e.getMessage(), e);
			}
		}
		if (aForm.getIsParameterizedDesc().equals("true"))
			covenant.setDescription(aForm.getCovenantSelect());
		else covenant.setDescription(aForm.getCovenantItemDesc());
		covenant.setRemarks(aForm.getCovenantItemRemarks());
		// cr26
		// One-Off
		if (!isEmptyOrNull(aForm.getOneOff())) {
			covenant.setIsOneOffInd(Boolean.valueOf(aForm.getOneOff()).booleanValue());
		}
		if (!isEmptyOrNull(aForm.getRiskTrigger())) {
			covenant.setRiskTrigger(Boolean.valueOf(aForm.getRiskTrigger()).booleanValue());
		}
		// CR234
		// fee
		if (!isEmptyOrNull(aForm.getFee())) {
			covenant.setFee(Boolean.valueOf(aForm.getFee()).booleanValue());
		}

		if (!isEmptyOrNull(aForm.getIsParameterizedDesc())) {
			covenant.setIsParameterizedDesc(Boolean.valueOf(aForm.getIsParameterizedDesc()).booleanValue());
		}

		// Frequency
		// DefaultLogger.debug(this,"Frequency "+aForm.getFrequency());
//		 DefaultLogger.debug(this,"Frequency "+aForm.getFrequency());
//		 DefaultLogger.debug(this,"Frequency "+s);
		if ((aForm.getFrequency() != null) && !aForm.getFrequency().trim().equals("")) {
			covenant.setFrequency(Integer.parseInt(aForm.getFrequency().trim()));
		}
		else {
			covenant.setFrequency(Integer.MIN_VALUE);
		}

		if ((aForm.getFrequencyUnit() != null) && !aForm.getFrequencyUnit().equals("")) {
			covenant.setFrequencyUnit(aForm.getFrequencyUnit());
		}
		else {
			covenant.setFrequencyUnit(null);
		}

		// Document End Date
		if ((aForm.getDocEndDate() != null) && !aForm.getDocEndDate().equals("")) {
			covenant.setInitialDocEndDate(DateUtil.convertDate(locale, aForm.getDocEndDate()));
		}

		// Grace Period
		if ((aForm.getGracePeriod() != null) && !aForm.getGracePeriod().equals("")) {
			covenant.setGracePeriod(Integer.parseInt(aForm.getGracePeriod().trim()));
		}
		else { // cms 1737
			covenant.setGracePeriod(Integer.MIN_VALUE);
		}

		// Grace Period Unit
		if ((aForm.getGracePeriodUnit() != null) && !aForm.getGracePeriodUnit().equals("")) {
			covenant.setGracePeriodUnit(aForm.getGracePeriodUnit());
		}
		else { // cms 1737
			covenant.setGracePeriodUnit(null);
		}

		// Due Date
		// DefaultLogger.debug(this, "due date from form: " +
		// aForm.getDueDate());
		covenant.setInitialDueDate(DateUtil.convertDate(locale, aForm.getDueDate()));
		// }
		// Last Document Entry Date
		covenant.setLastDocEntryDate(DateUtil.convertDate(locale, aForm.getLastDocEntryDate()));

		// Chaser / Reminder
		if (!isEmptyOrNull(aForm.getChaserReminder())) {
			covenant.setChaseReminderInd(Boolean.valueOf(aForm.getChaserReminder()).booleanValue());
		}
		// cr26
		return covenant;
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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		RecurrentCheckListForm aForm = (RecurrentCheckListForm) cForm;
		try {
			if (obj != null) {
				IConvenant tempOb = (IConvenant) obj;
				if (!tempOb.getIsParameterizedDesc()) {
					aForm.setCovenantSelect("");
					aForm.setCovenantItemDesc(tempOb.getDescription());
				}
				else {
					aForm.setCovenantSelect(tempOb.getDescription());
					aForm.setCovenantItemDesc("");
				}

				aForm.setCovenantItemRemarks(tempOb.getRemarks());
				// cr 26
				// One-Off
				// DefaultLogger.debug(this, "tempob:~~~~~ " + tempOb);
				aForm.setOneOff(String.valueOf(tempOb.getIsOneOffInd()));
				aForm.setRiskTrigger(String.valueOf(tempOb.getRiskTrigger()));
				// cr234
				// fee new field
				aForm.setFee(String.valueOf(tempOb.getFee()));
				aForm.setIsParameterizedDesc(String.valueOf(tempOb.getIsParameterizedDesc()));

				// Due Date
				aForm.setDueDate(DateUtil.formatDate(locale, tempOb.getInitialDueDate()));

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
				// DefaultLogger.debug(this, "chaser/reminder: " +
				// tempOb.getChaseReminderInd());
				aForm.setChaserReminder(String.valueOf(tempOb.getChaseReminderInd()));
				// cr 26

				// For preventing changing of one-off
				if ((tempOb.getConvenantSubItemList() != null) && (tempOb.getConvenantSubItemList().length > 1)) {
					aForm.setHasMoreSubItems("true");
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
		}

		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
	
	public String findValueFromLabel(String label) {
		CommonCodeList commonCode = CommonCodeList.getInstance("COVENANT_CONDITION");
		
		String[] objectValue = (String[]) commonCode.getCommonCodeValues().toArray(new String[0]);
		String[] objectLabel = (String[]) commonCode.getCommonCodeLabels().toArray(new String[0]);
		
		int chosen=0;
		for (int i=0; i<objectValue.length; i++) {
			if (objectLabel[i].equals(label)) {
				chosen=i;
			}
		}
		return objectValue[chosen];
	}
	
	public String findLabelFromValue(String value) {
		CommonCodeList commonCode = CommonCodeList.getInstance("COVENANT_CONDITION");
		
		String[] objectValue = (String[]) commonCode.getCommonCodeValues().toArray(new String[0]);
		String[] objectLabel = (String[]) commonCode.getCommonCodeLabels().toArray(new String[0]);
		
		int chosen=0;
		for (int i=0; i<objectValue.length; i++) {
			if (objectValue[i].equals(value)) {
				chosen=i;
			}
		}
		return objectLabel[chosen];
	}

}
