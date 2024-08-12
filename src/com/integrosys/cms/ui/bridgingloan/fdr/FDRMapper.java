/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.fdr;

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
import com.integrosys.cms.app.bridgingloan.bus.IFDR;
import com.integrosys.cms.app.bridgingloan.bus.OBFDR;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class FDRMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public FDRMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "fdrIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {});
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB (FDRMapper) ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this, "event=" + event);

		FDRForm aForm = (FDRForm) cForm;
		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		if (FDRAction.EVENT_CREATE.equals(event)) {
			IFDR[] oldFDR = (IFDR[]) objBridgingLoan.getFdrList(); // Get whole
																	// list
			ArrayList fdrList = new ArrayList();
			try {
				OBFDR newFDR = new OBFDR();
				if (!aForm.getFdrDate().equals("")) {
					newFDR.setFdrDate(DateUtil.convertDate(locale, aForm.getFdrDate()));
				}
				newFDR.setAccountNo(aForm.getAccountNo());
				if (!aForm.getFdrCurrency().equals("") && !aForm.getFdrAmount().equals("")) {
					newFDR.setFdrAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(), aForm
							.getFdrAmount()));
				}
				newFDR.setReferenceNo(aForm.getReferenceNo());
				newFDR.setRemarks(aForm.getRemarks());

				if ((oldFDR != null) && (oldFDR.length != 0)) {
					for (int i = 0; i < oldFDR.length; i++) {
						OBFDR objFDR = (OBFDR) oldFDR[i];
						fdrList.add(objFDR);
					}
				}
				fdrList.add(newFDR);
				objBridgingLoan.setFdrList((IFDR[]) fdrList.toArray(new IFDR[0]));
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (FDRAction.EVENT_UPDATE.equals(event)) {
			IFDR[] newFDR = (IFDR[]) objBridgingLoan.getFdrList();
			int fdrIndex = Integer.parseInt((String) map.get("fdrIndex"));

			if ((newFDR != null) && (newFDR.length != 0)) {
				try {
					if (!aForm.getFdrDate().equals("")) {
						newFDR[fdrIndex].setFdrDate(DateUtil.convertDate(locale, aForm.getFdrDate()));
					}
					newFDR[fdrIndex].setAccountNo(aForm.getAccountNo());
					if (!aForm.getFdrCurrency().equals("") && !aForm.getFdrAmount().equals("")) {
						newFDR[fdrIndex].setFdrAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(),
								aForm.getFdrAmount()));
					}

					newFDR[fdrIndex].setReferenceNo(aForm.getReferenceNo());
					newFDR[fdrIndex].setRemarks(aForm.getRemarks());
					objBridgingLoan.setFdrList(newFDR);
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
				return objBridgingLoan;
			}
		}
		else if (FDRAction.EVENT_DELETE.equals(event)) {
			ArrayList fdrList = new ArrayList();
			IFDR[] oldFDR = objBridgingLoan.getFdrList();
			int fdrIndex = Integer.parseInt((String) map.get("fdrIndex"));

			if ((oldFDR != null) && (oldFDR.length != 0)) {
				try {
					if ((oldFDR != null) && (oldFDR.length != 0)) {
						for (int i = 0; i < oldFDR.length; i++) {
							OBFDR objFDR = (OBFDR) oldFDR[i];
							if (fdrIndex == i) {
								if (objFDR.getFdrID() != ICMSConstant.LONG_INVALID_VALUE) {
									objFDR.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							fdrList.add(objFDR);
						}
					}
					objBridgingLoan.setFdrList((IFDR[]) fdrList.toArray(new IFDR[0]));
				}
				catch (Exception e) {
					e.printStackTrace();
				}
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
			FDRForm aForm = (FDRForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				IFDR[] objFDRList = (IFDR[]) objBridgingLoan.getFdrList();
				int fdrIndex = Integer.parseInt((String) map.get("fdrIndex"));

				if (objFDRList != null) {
					if (objFDRList[fdrIndex].getFdrDate() != null) {
						aForm.setFdrDate(DateUtil.formatDate(locale, objFDRList[fdrIndex].getFdrDate()));
					}
					aForm.setAccountNo(objFDRList[fdrIndex].getAccountNo());
					if ((objFDRList[fdrIndex].getFdrAmount().getCurrencyCode() != null)
							&& (objFDRList[fdrIndex].getFdrAmount() != null)) {
						aForm.setFdrCurrency(objFDRList[fdrIndex].getFdrAmount().getCurrencyCode());
						aForm
								.setFdrAmount(CurrencyManager.convertToString(locale, objFDRList[fdrIndex]
										.getFdrAmount()));
					}
					aForm.setReferenceNo(objFDRList[fdrIndex].getReferenceNo());
					aForm.setRemarks(objFDRList[fdrIndex].getRemarks());
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