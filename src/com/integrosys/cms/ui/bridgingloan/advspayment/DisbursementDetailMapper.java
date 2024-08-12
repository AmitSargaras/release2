package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursementDetail;
import com.integrosys.cms.app.bridgingloan.bus.OBDisbursementDetail;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 31, 2007 Time: 12:02:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisbursementDetailMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public DisbursementDetailMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "disbursementIndex", "java.lang.String", REQUEST_SCOPE },
				{ "disbursementDetailIndex", "java.lang.String", REQUEST_SCOPE }, });
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
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DisbursementDetailForm aForm = (DisbursementDetailForm) cForm;
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		int disbursementIndex;
		if (((String) map.get("disbursementIndex") != null)
				&& !String.valueOf(map.get("disbursementIndex")).equals("-1")
				&& !String.valueOf(map.get("disbursementIndex")).equals("")) {
			disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));
		}
		else {
			if (objBridgingLoan.getDisbursementList() != null) {
				disbursementIndex = objBridgingLoan.getDisbursementList().length - 1;
			}
			else {
				disbursementIndex = 0;
			}
		}
		DefaultLogger.debug(this, "disbursementIndex = " + disbursementIndex);
		IDisbursement[] objDisbursement = (IDisbursement[]) objBridgingLoan.getDisbursementList();

		if (DisbursementDetailAction.EVENT_CREATE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping - EVENT_CREATE ");

			IDisbursementDetail[] oldDisbursementDetail = (IDisbursementDetail[]) objDisbursement[disbursementIndex]
					.getDisbursementDetailList(); // Get whole list
			DefaultLogger.debug("******", "oldDisbursementDetail " + oldDisbursementDetail);
			ArrayList disbursementDetailList = new ArrayList();
			try {
				OBDisbursementDetail newDisbursementDetail = new OBDisbursementDetail();
				newDisbursementDetail.setDisbursementDate(DateUtil.convertDate(locale, aForm.getDisbursementDate()));
				newDisbursementDetail.setSubpurpose(aForm.getSubpurpose());
				newDisbursementDetail.setInvoiceNumber(aForm.getInvoiceNumber());
				if (!aForm.getInvoiceCurrency().equals("") && !aForm.getInvoiceAmount().equals("")) {
					newDisbursementDetail.setInvoiceAmount(CurrencyManager.convertToAmount(locale, aForm
							.getInvoiceCurrency(), aForm.getInvoiceAmount()));
				}
				if (!aForm.getDisburseCurrency().equals("") && !aForm.getDisburseAmount().equals("")) {
					newDisbursementDetail.setDisburseAmount(CurrencyManager.convertToAmount(locale, aForm
							.getDisburseCurrency(), aForm.getDisburseAmount()));
				}
				newDisbursementDetail.setDisbursementMode(aForm.getDisbursementMode());
				newDisbursementDetail.setPayee(aForm.getPayee());
				newDisbursementDetail.setReferenceNumber(aForm.getReferenceNumber());
				newDisbursementDetail.setGlReferenceNumber(aForm.getGlReferenceNumber());
				newDisbursementDetail.setRemarks(aForm.getRemarks());

				if ((oldDisbursementDetail != null) && (oldDisbursementDetail.length != 0)) {
					for (int i = 0; i < oldDisbursementDetail.length; i++) {
						OBDisbursementDetail objDisbursementDetail = (OBDisbursementDetail) oldDisbursementDetail[i];
						disbursementDetailList.add(objDisbursementDetail);
					}
				}
				disbursementDetailList.add(newDisbursementDetail);
			}
			catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception error: " + e);
			}
			objDisbursement[disbursementIndex].setDisbursementDetailList((IDisbursementDetail[]) disbursementDetailList
					.toArray(new IDisbursementDetail[0]));
			objBridgingLoan.setDisbursementList(objDisbursement);
			return objBridgingLoan;
		}
		else if (DisbursementDetailAction.EVENT_UPDATE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_UPDATE ");
			IDisbursementDetail[] newDisbursementDetail = (IDisbursementDetail[]) objDisbursement[disbursementIndex]
					.getDisbursementDetailList(); // Get whole list
			DefaultLogger.debug(this, "newDisbursementDetail " + newDisbursementDetail);
			int disbursementDetailIndex = Integer.parseInt((String) map.get("disbursementDetailIndex"));
			DefaultLogger.debug(this, "disbursementDetailIndex " + disbursementDetailIndex);

			if ((newDisbursementDetail != null) && (newDisbursementDetail.length != 0)) {
				DefaultLogger.debug(this, ">>>>>>>>>> In newDisbursementDetail");
				try {
					newDisbursementDetail[disbursementDetailIndex].setDisbursementDate(DateUtil.convertDate(locale,
							aForm.getDisbursementDate()));
					newDisbursementDetail[disbursementDetailIndex].setSubpurpose(aForm.getSubpurpose());
					newDisbursementDetail[disbursementDetailIndex].setInvoiceNumber(aForm.getInvoiceNumber());
					if (!aForm.getInvoiceCurrency().equals("") && !aForm.getInvoiceAmount().equals("")) {
						newDisbursementDetail[disbursementDetailIndex].setInvoiceAmount(CurrencyManager
								.convertToAmount(locale, aForm.getInvoiceCurrency(), aForm.getInvoiceAmount()));
					}
					if (!aForm.getDisburseCurrency().equals("") && !aForm.getDisburseAmount().equals("")) {
						newDisbursementDetail[disbursementIndex].setDisburseAmount(CurrencyManager.convertToAmount(
								locale, aForm.getDisburseCurrency(), aForm.getDisburseAmount()));
					}
					newDisbursementDetail[disbursementDetailIndex].setDisbursementMode(aForm.getDisbursementMode());
					newDisbursementDetail[disbursementDetailIndex].setPayee(aForm.getPayee());
					newDisbursementDetail[disbursementDetailIndex].setReferenceNumber(aForm.getReferenceNumber());
					newDisbursementDetail[disbursementDetailIndex].setGlReferenceNumber(aForm.getGlReferenceNumber());
					newDisbursementDetail[disbursementDetailIndex].setRemarks(aForm.getRemarks());
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "Exception error: " + e);
				}
				objDisbursement[disbursementIndex].setDisbursementDetailList(newDisbursementDetail);
				objBridgingLoan.setDisbursementList(objDisbursement);
				return objBridgingLoan;
			}
		}
		else if (DisbursementDetailAction.EVENT_DELETE.equals(event)) {
			DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for EVENT_DELETE");
			ArrayList disbursementDetailList = new ArrayList();
			IDisbursementDetail[] oldDisbursementDetail = (IDisbursementDetail[]) objDisbursement[disbursementIndex]
					.getDisbursementDetailList(); // Get whole list
			int disbursementDetailIndex = Integer.parseInt((String) map.get("disbursementDetailIndex"));
			DefaultLogger.debug(this, "disbursementIndex: " + disbursementIndex);
			DefaultLogger.debug(this, "disbursementDetailIndex: " + disbursementDetailIndex);

			try {
				if ((oldDisbursementDetail != null) && (oldDisbursementDetail.length > 0)) {
					for (int i = 0; i < oldDisbursementDetail.length; i++) {
						OBDisbursementDetail objDisbursementDetail = (OBDisbursementDetail) oldDisbursementDetail[i];
						if (disbursementDetailIndex == i) {
							if (objDisbursementDetail.getDisbursementID() != ICMSConstant.LONG_INVALID_VALUE) {
								objDisbursementDetail.setIsDeletedInd(true);
							}
							else {
								continue; // If record not available at db, skip
											// adding
							}
						}
						disbursementDetailList.add(objDisbursementDetail);
					}
					objDisbursement[disbursementIndex]
							.setDisbursementDetailList((IDisbursementDetail[]) disbursementDetailList
									.toArray(new IDisbursementDetail[0]));
					objBridgingLoan.setDisbursementList(objDisbursement);
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
			DefaultLogger.debug(this, "******************** inside mapOb to form");
			DisbursementDetailForm aForm = (DisbursementDetailForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;

				int disbursementIndex = 0;
				if (((String) map.get("disbursementIndex") != null)
						&& !String.valueOf(map.get("disbursementIndex")).equals("")
						&& !String.valueOf(map.get("disbursementIndex")).equals("-1")) {
					disbursementIndex = Integer.parseInt((String) map.get("disbursementIndex"));
				}
				DefaultLogger.debug(this, "disbursementIndex = " + disbursementIndex);

				IDisbursement[] objDisbursementList = (IDisbursement[]) objBridgingLoan.getDisbursementList();
				IDisbursementDetail[] objDisbursementDetailList = (IDisbursementDetail[]) objDisbursementList[disbursementIndex]
						.getDisbursementDetailList(); // Get whole list

				int disbursementDetailIndex = Integer.parseInt((String) map.get("disbursementDetailIndex"));
				DefaultLogger.debug(this, "disbursementDetailIndex = " + disbursementDetailIndex);

				if (objDisbursementDetailList != null) {
					aForm.setDisbursementDate(DateUtil.formatDate(locale,
							objDisbursementDetailList[disbursementDetailIndex].getDisbursementDate()));
					aForm.setSubpurpose(objDisbursementDetailList[disbursementDetailIndex].getSubpurpose());
					aForm.setInvoiceNumber(objDisbursementDetailList[disbursementDetailIndex].getInvoiceNumber());
					if (objDisbursementDetailList[disbursementDetailIndex].getInvoiceAmount() != null) {
						aForm.setInvoiceCurrency(objDisbursementDetailList[disbursementDetailIndex].getInvoiceAmount()
								.getCurrencyCode());
						aForm.setInvoiceAmount(CurrencyManager.convertToString(locale,
								objDisbursementDetailList[disbursementDetailIndex].getInvoiceAmount()));
					}
					if (objDisbursementDetailList[disbursementDetailIndex].getDisburseAmount() != null) {
						aForm.setDisburseCurrency(objDisbursementDetailList[disbursementDetailIndex]
								.getDisburseAmount().getCurrencyCode());
						aForm.setDisburseAmount(CurrencyManager.convertToString(locale,
								objDisbursementDetailList[disbursementDetailIndex].getDisburseAmount()));
					}
					aForm.setDisbursementMode(objDisbursementDetailList[disbursementDetailIndex].getDisbursementMode());
					aForm.setPayee(objDisbursementDetailList[disbursementDetailIndex].getPayee());
					aForm.setReferenceNumber(objDisbursementDetailList[disbursementDetailIndex].getReferenceNumber());
					aForm.setGlReferenceNumber(objDisbursementDetailList[disbursementDetailIndex]
							.getGlReferenceNumber());
					aForm.setRemarks(objDisbursementDetailList[disbursementDetailIndex].getRemarks());
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