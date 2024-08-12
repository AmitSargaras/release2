/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDevelopmentDoc;
import com.integrosys.cms.app.bridgingloan.bus.IProjectSchedule;
import com.integrosys.cms.app.bridgingloan.bus.OBDevelopmentDoc;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class DevelopmentDocMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public DevelopmentDocMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "developmentDocIndex", "java.lang.String", REQUEST_SCOPE },
				{ "progressStage", "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		DefaultLogger.debug(this, "event=" + event);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DevelopmentDocForm aForm = (DevelopmentDocForm) cForm;
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();
		IProjectSchedule[] objProjectSchedule = (IProjectSchedule[]) objBridgingLoan.getProjectScheduleList();

		String progressStage = (String) map.get("progressStage");
		int projectScheduleIndex = 0;
		String scheduleIndex = (String) map.get("projectScheduleIndex");

		if (scheduleIndex != null) {
			projectScheduleIndex = Integer.parseInt((String) map.get("projectScheduleIndex"));
		}

		for (int i = 0; i < objProjectSchedule.length; i++) {
			if (objProjectSchedule[i].getProgressStage().equals(progressStage) && (projectScheduleIndex == i)) {
				projectScheduleIndex = i;
				break;
			}
		}

		if (DevelopmentDocAction.EVENT_MAKER_REFRESH.equals(event)
				|| DevelopmentDocAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
				|| DevelopmentDocAction.EVENT_VIEW.equals(event)
				|| DevelopmentDocAction.EVENT_MAKER_PREPARE_DELETE.equals(event)
				|| DevelopmentDocAction.EVENT_CHECKER_VIEW.equals(event)) {
			IDevelopmentDoc[] oldDevelopmentDoc = null;
			List developmentDocList = new ArrayList();

			if ((objProjectSchedule != null) && (objProjectSchedule.length > 0)) {
				for (int i = 0; i < objProjectSchedule.length; i++) {
					if (objProjectSchedule[i].equals(progressStage)) { // Compare
																		// stored
																		// progressStage
																		// with
																		// pass
																		// -in
																		// progressStage
						oldDevelopmentDoc = objProjectSchedule[i].getDevelopmentDocList();
						if ((oldDevelopmentDoc != null) && (oldDevelopmentDoc.length > 0)) {
							developmentDocList = Arrays.asList(oldDevelopmentDoc);
						}

						objProjectSchedule[i].setDevelopmentDocList((IDevelopmentDoc[]) developmentDocList
								.toArray(new IDevelopmentDoc[0]));
					}
				}
				objBridgingLoan.setProjectScheduleList(objProjectSchedule);
			}
			return objBridgingLoan;
		}
		else if (DevelopmentDocAction.EVENT_CREATE.equals(event) || DevelopmentDocAction.EVENT_UPDATE.equals(event)
				|| DevelopmentDocAction.EVENT_DELETE.equals(event)) {
			return objBridgingLoan;
		}
		else if (DevelopmentDocAction.EVENT_CREATE_ITEM.equals(event)) {
			IDevelopmentDoc[] oldDevelopmentDoc = (IDevelopmentDoc[]) objProjectSchedule[projectScheduleIndex]
					.getDevelopmentDocList(); // Get whole list by index
			ArrayList developmentDocList = new ArrayList();
			try {
				OBDevelopmentDoc newDevelopmentDoc = new OBDevelopmentDoc();
				newDevelopmentDoc.setDocName(aForm.getDocName());
				newDevelopmentDoc.setDocRef(aForm.getDocRef());
				newDevelopmentDoc.setReceiveDate(DateUtil.convertDate(locale, aForm.getReceiveDate()));
				newDevelopmentDoc.setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
				newDevelopmentDoc.setRemarks(aForm.getRemarks());

				if ((oldDevelopmentDoc != null) && (oldDevelopmentDoc.length > 0)) {
					for (int i = 0; i < oldDevelopmentDoc.length; i++) {
						OBDevelopmentDoc objDevelopmentDoc = (OBDevelopmentDoc) oldDevelopmentDoc[i];
						developmentDocList.add(objDevelopmentDoc);
					}
				}
				developmentDocList.add(newDevelopmentDoc);
				objProjectSchedule[projectScheduleIndex].setDevelopmentDocList((IDevelopmentDoc[]) developmentDocList
						.toArray(new IDevelopmentDoc[0]));
				objBridgingLoan.setProjectScheduleList(objProjectSchedule);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (DevelopmentDocAction.EVENT_UPDATE_ITEM.equals(event)) {
			IDevelopmentDoc[] newDevelopmentDoc = (IDevelopmentDoc[]) objProjectSchedule[projectScheduleIndex]
					.getDevelopmentDocList();
			int developmentDocIndex = Integer.parseInt((String) map.get("developmentDocIndex"));

			if (newDevelopmentDoc != null) {
				try {
					newDevelopmentDoc[developmentDocIndex].setDocName(aForm.getDocName());
					newDevelopmentDoc[developmentDocIndex].setDocRef(aForm.getDocRef());
					newDevelopmentDoc[developmentDocIndex].setReceiveDate(DateUtil.convertDate(locale, aForm
							.getReceiveDate()));
					newDevelopmentDoc[developmentDocIndex].setDocDate(DateUtil.convertDate(locale, aForm.getDocDate()));
					newDevelopmentDoc[developmentDocIndex].setRemarks(aForm.getRemarks());

					objProjectSchedule[projectScheduleIndex].setDevelopmentDocList(newDevelopmentDoc);
					objBridgingLoan.setProjectScheduleList(objProjectSchedule);
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
				return objBridgingLoan;
			}
		}
		else if (DevelopmentDocAction.EVENT_DELETE_ITEM.equals(event)) {
			ArrayList developmentDocList = new ArrayList();
			IDevelopmentDoc[] oldDevelopmentDoc = objProjectSchedule[projectScheduleIndex].getDevelopmentDocList();
			int developmentDocIndex = Integer.parseInt((String) map.get("developmentDocIndex"));

			try {
				if ((oldDevelopmentDoc != null) && (oldDevelopmentDoc.length > 0)) {
					for (int i = 0; i < oldDevelopmentDoc.length; i++) {
						OBDevelopmentDoc objDevelopmentDoc = (OBDevelopmentDoc) oldDevelopmentDoc[i];
						if (developmentDocIndex == i) {
							if (objDevelopmentDoc.getDevDocID() != ICMSConstant.LONG_INVALID_VALUE) {
								objDevelopmentDoc.setIsDeletedInd(true);
							}
							else {
								continue; // If record not available at db, skip
											// adding
							}
						}
						developmentDocList.add(objDevelopmentDoc);
					}
				}
				objProjectSchedule[projectScheduleIndex].setDevelopmentDocList((IDevelopmentDoc[]) developmentDocList
						.toArray(new IDevelopmentDoc[0]));
				objBridgingLoan.setProjectScheduleList(objProjectSchedule);
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
			DefaultLogger.debug(this, "******************** inside mapOb to form");
			String event = (String) map.get(ICommonEventConstant.EVENT);
			DefaultLogger.debug(this, "event=" + event);
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			DevelopmentDocForm aForm = (DevelopmentDocForm) cForm;

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IProjectSchedule[] objProjectSchedule = (IProjectSchedule[]) objBridgingLoan.getProjectScheduleList();
				String progressStage = (String) map.get("progressStage");
				DefaultLogger.debug(this, "progressStage=" + progressStage);

				if (objProjectSchedule != null) {
					IDevelopmentDoc[] objDevelopmentDocList = null;
					DefaultLogger.debug(this, "getProjectScheduleList().length="
							+ objBridgingLoan.getProjectScheduleList().length);
					for (int index = 0; index < objBridgingLoan.getProjectScheduleList().length; index++) {
						if (objProjectSchedule[index].getProgressStage().equals(progressStage)) {
							objDevelopmentDocList = (IDevelopmentDoc[]) objProjectSchedule[index]
									.getDevelopmentDocList();
							DefaultLogger.debug(this, "objDevelopmentDocList=" + objDevelopmentDocList);
							DefaultLogger.debug(this, "objDevelopmentDocList.length=" + objDevelopmentDocList.length);
							break;
						}
					}

					if (!DevelopmentDocAction.EVENT_VIEW.equals(event)
							&& !DevelopmentDocAction.EVENT_MAKER_PREPARE_UPDATE.equals(event)
							&& !DevelopmentDocAction.EVENT_UPDATE.equals(event)
							&& !DevelopmentDocAction.EVENT_MAKER_PREPARE_DELETE.equals(event)
							&& !DevelopmentDocAction.EVENT_DELETE.equals(event)) {
						if (objDevelopmentDocList != null) {
							int developmentDocIndex = Integer.parseInt(String.valueOf(map.get("developmentDocIndex")));
							aForm.setDocName(objDevelopmentDocList[developmentDocIndex].getDocName());
							aForm.setDocRef(objDevelopmentDocList[developmentDocIndex].getDocRef());
							aForm.setReceiveDate(DateUtil.formatDate(locale, objDevelopmentDocList[developmentDocIndex]
									.getReceiveDate()));
							aForm.setDocDate(DateUtil.formatDate(locale, objDevelopmentDocList[developmentDocIndex]
									.getDocDate()));
							aForm.setRemarks(objDevelopmentDocList[developmentDocIndex].getRemarks());
						}
					}
				}
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.toString());
			e.printStackTrace();
		}
		return null;
	}
}