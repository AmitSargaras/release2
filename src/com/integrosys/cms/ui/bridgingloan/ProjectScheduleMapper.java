/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IProjectSchedule;
import com.integrosys.cms.app.bridgingloan.bus.OBProjectSchedule;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ProjectScheduleMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public ProjectScheduleMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "projectScheduleIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB (ProjectScheduleMapper)");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		DefaultLogger.debug(this, "event = " + event);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
		ProjectScheduleForm aForm = (ProjectScheduleForm) cForm;

		if (PropertyTypeAction.EVENT_CREATE.equals(event)) {
			IProjectSchedule[] oldProjectSchedule = (IProjectSchedule[]) objBridgingLoan.getProjectScheduleList();
			ArrayList projectScheduleList = new ArrayList();
			try {
				OBProjectSchedule newProjectSchedule = new OBProjectSchedule();
				newProjectSchedule.setProgressStage(aForm.getProgressStage());
				if (!aForm.getStartDate().equals("")) {
					newProjectSchedule.setStartDate(DateUtil.convertDate(locale, aForm.getStartDate()));
				}
				if (!aForm.getEndDate().equals("")) {
					newProjectSchedule.setEndDate(DateUtil.convertDate(locale, aForm.getEndDate()));
				}
				if (!aForm.getActualStartDate().equals("")) {
					newProjectSchedule.setActualStartDate(DateUtil.convertDate(locale, aForm.getActualStartDate()));
				}
				if (!aForm.getActualEndDate().equals("")) {
					newProjectSchedule.setActualEndDate(DateUtil.convertDate(locale, aForm.getActualEndDate()));
				}
				newProjectSchedule.setActualEndDate(DateUtil.convertDate(locale, aForm.getActualEndDate()));
				newProjectSchedule.setRemarks(aForm.getRemarks());

				if ((oldProjectSchedule != null) && (oldProjectSchedule.length > 0)) {
					for (int i = 0; i < oldProjectSchedule.length; i++) {
						OBProjectSchedule objProjectSchedule = (OBProjectSchedule) oldProjectSchedule[i];
						projectScheduleList.add(objProjectSchedule);
					}
				}
				projectScheduleList.add(newProjectSchedule);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, "Exception error: " + e);
			}
			objBridgingLoan.setProjectScheduleList((IProjectSchedule[]) projectScheduleList
					.toArray(new IProjectSchedule[0]));
			return objBridgingLoan;
		}
		else if (PropertyTypeAction.EVENT_UPDATE.equals(event)) {
			IProjectSchedule[] newProjectSchedule = (IProjectSchedule[]) objBridgingLoan.getProjectScheduleList();
			int projectScheduleIndex = Integer.parseInt((String) map.get("projectScheduleIndex"));

			if (newProjectSchedule != null) {
				newProjectSchedule[projectScheduleIndex].setProgressStage(aForm.getProgressStage());
				if (!aForm.getStartDate().equals("")) {
					newProjectSchedule[projectScheduleIndex].setStartDate(DateUtil.convertDate(locale, aForm
							.getStartDate()));
				}
				if (!aForm.getEndDate().equals("")) {
					newProjectSchedule[projectScheduleIndex].setEndDate(DateUtil
							.convertDate(locale, aForm.getEndDate()));
				}
				if (!aForm.getActualStartDate().equals("")) {
					newProjectSchedule[projectScheduleIndex].setActualStartDate(DateUtil.convertDate(locale, aForm
							.getActualStartDate()));
				}
				if (!aForm.getActualEndDate().equals("")) {
					newProjectSchedule[projectScheduleIndex].setActualEndDate(DateUtil.convertDate(locale, aForm
							.getActualEndDate()));
				}
				newProjectSchedule[projectScheduleIndex].setRemarks(aForm.getRemarks());
				objBridgingLoan.setProjectScheduleList(newProjectSchedule);
				return objBridgingLoan;
			}
		}
		else if (PropertyTypeAction.EVENT_DELETE.equals(event)) {
			ArrayList projectScheduleList = new ArrayList();
			IProjectSchedule[] oldProjectSchedule = objBridgingLoan.getProjectScheduleList();
			int projectScheduleIndex = Integer.parseInt((String) map.get("projectScheduleIndex"));
			// TODO: Development not included in the deletion process
			try {
				if ((oldProjectSchedule != null) && (oldProjectSchedule.length > 0)) {
					for (int i = 0; i < oldProjectSchedule.length; i++) {
						// If child record is dependent to parent record, then
						// skip deletion
						if ((oldProjectSchedule[i].getDevelopmentDocList() == null)
								|| (oldProjectSchedule[i].getDevelopmentDocList().length > 0)) {
							OBProjectSchedule objPropertyType = (OBProjectSchedule) oldProjectSchedule[i];
							if (projectScheduleIndex == i) {
								if (objPropertyType.getScheduleID() != ICMSConstant.LONG_INVALID_VALUE) {
									objPropertyType.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							projectScheduleList.add(objPropertyType);
						}
					}
					objBridgingLoan.setProjectScheduleList((IProjectSchedule[]) projectScheduleList
							.toArray(new IProjectSchedule[0]));
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			return objBridgingLoan;
		}
		return null;
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
		try {
			DefaultLogger.debug(this, "******************** inside mapOb to form (ProjectScheduleMapper)");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			ProjectScheduleForm aForm = (ProjectScheduleForm) cForm;

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IProjectSchedule[] objProjectScheduleList = (IProjectSchedule[]) objBridgingLoan
						.getProjectScheduleList();
				int projectScheduleIndex = Integer.parseInt((String) map.get("projectScheduleIndex"));

				if (objProjectScheduleList != null) {
					DefaultLogger.debug(this, "getStartDate: "
							+ objProjectScheduleList[projectScheduleIndex].getStartDate());
					aForm.setProgressStage(objProjectScheduleList[projectScheduleIndex].getProgressStage());
					if (objProjectScheduleList[projectScheduleIndex].getStartDate() != null) {
						aForm.setStartDate(DateUtil.formatDate(locale, objProjectScheduleList[projectScheduleIndex]
								.getStartDate()));
					}
					if (objProjectScheduleList[projectScheduleIndex].getEndDate() != null) {
						aForm.setEndDate(DateUtil.formatDate(locale, objProjectScheduleList[projectScheduleIndex]
								.getEndDate()));
					}
					if (objProjectScheduleList[projectScheduleIndex].getActualStartDate() != null) {
						aForm.setActualStartDate(DateUtil.formatDate(locale,
								objProjectScheduleList[projectScheduleIndex].getActualStartDate()));
					}
					if (objProjectScheduleList[projectScheduleIndex].getActualEndDate() != null) {
						aForm.setActualEndDate(DateUtil.formatDate(locale, objProjectScheduleList[projectScheduleIndex]
								.getActualEndDate()));
					}
					aForm.setRemarks(objProjectScheduleList[projectScheduleIndex].getRemarks());
				}
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception error: " + e);
		}
		return null;
	}
}