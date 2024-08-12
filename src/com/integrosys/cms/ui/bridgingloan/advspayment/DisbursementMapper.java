/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.bus.OBDisbursement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class DisbursementMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public DisbursementMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "disbursementIndex", "java.lang.String", REQUEST_SCOPE }, });
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
		DisbursementForm aForm = (DisbursementForm) cForm;

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		if (DisbursementAction.EVENT_CREATE.equals(event)
				|| DisbursementAction.EVENT_MAKER_PREPARE_SAVE_CREATE.equals(event)) {
			IDisbursement[] oldDisbursement = (IDisbursement[]) objBridgingLoan.getDisbursementList(); // Get
																										// whole
																										// list
			ArrayList disbursementList = new ArrayList();
			try {
				OBDisbursement newDisbursement = new OBDisbursement();
				newDisbursement.setPurpose(aForm.getPurpose());
				newDisbursement.setDisRemarks(aForm.getDisRemarks());

				if ((oldDisbursement != null) && (oldDisbursement.length != 0)) {
					for (int i = 0; i < oldDisbursement.length; i++) {
						OBDisbursement objDisbursement = (OBDisbursement) oldDisbursement[i];
						disbursementList.add(objDisbursement);
					}
				}
				disbursementList.add(newDisbursement);
				objBridgingLoan.setDisbursementList((IDisbursement[]) disbursementList.toArray(new IDisbursement[0]));
			}
			catch (Exception e) {
				DefaultLogger.error(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (DisbursementAction.EVENT_UPDATE.equals(event)) {
			IDisbursement[] newDisbursement = (IDisbursement[]) objBridgingLoan.getDisbursementList();
			int disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));

			if ((newDisbursement != null) && (newDisbursement.length > 0)) {
				try {
					newDisbursement[disbursementIndex].setPurpose(aForm.getPurpose());
					newDisbursement[disbursementIndex].setDisRemarks(aForm.getDisRemarks());
					objBridgingLoan.setDisbursementList(newDisbursement);
				}
				catch (Exception e) {
					DefaultLogger.error(this, e.toString());
				}
				return objBridgingLoan;
			}
		}
		else if (DisbursementAction.EVENT_DELETE.equals(event)) {
			ArrayList disbursementList = new ArrayList();
			IDisbursement[] oldDisbursement = objBridgingLoan.getDisbursementList();
			int disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));

			// TODO: Delete disbursementdetail not included yet
			try {
				if ((oldDisbursement != null) && (oldDisbursement.length > 0)) {
					for (int i = 0; i < oldDisbursement.length; i++) {
						// If child record is dependent to parent record, then
						// skip deletion
						if ((oldDisbursement[i].getDisbursementDetailList() == null)
								|| (oldDisbursement[i].getDisbursementDetailList().length > 0)) {
							OBDisbursement objDisbursement = (OBDisbursement) oldDisbursement[i];
							if (disbursementIndex == i) {
								if (objDisbursement.getDisbursementID() != ICMSConstant.LONG_INVALID_VALUE) {
									objDisbursement.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							disbursementList.add(objDisbursement);
						}
					}
				}
				objBridgingLoan.setDisbursementList((IDisbursement[]) disbursementList.toArray(new IDisbursement[0]));
			}
			catch (Exception e) {
				DefaultLogger.error(this, e.toString());
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
			DisbursementForm aForm = (DisbursementForm) cForm;

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IDisbursement[] objDisbursementList = (IDisbursement[]) objBridgingLoan.getDisbursementList();
				int disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));

				if (objDisbursementList != null) {
					aForm.setPurpose(objDisbursementList[disbursementIndex].getPurpose());
					aForm.setDisRemarks(objDisbursementList[disbursementIndex].getDisRemarks());
				}
			}
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, e.toString());
		}
		return null;
	}
}