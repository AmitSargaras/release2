/**
 * Copyright Integro Technologies Pte Ltd
 */
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
import com.integrosys.cms.app.bridgingloan.bus.ISettlement;
import com.integrosys.cms.app.bridgingloan.bus.OBSettlement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Mapper class used to map form values to objects and vice versa
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SettlementMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public SettlementMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "settlementIndex", "java.lang.String", REQUEST_SCOPE }, });
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
		SettlementForm aForm = (SettlementForm) cForm;

		IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
		IBridgingLoan objBridgingLoan = (IBridgingLoan) trxValue.getStagingBridgingLoan();

		if (SettlementAction.EVENT_CREATE.equals(event)) {
			ISettlement[] oldSettlement = (ISettlement[]) objBridgingLoan.getSettlementList(); // Get
																								// whole
																								// list
			ArrayList settlementList = new ArrayList();
			try {
				OBSettlement newSettlement = new OBSettlement();
				newSettlement.setSettlementDate(DateUtil.convertDate(locale, aForm.getSettlementDate()));
				if (!aForm.getSettledCurrency().equals("") && !aForm.getSettledAmount().equals("")) {
					newSettlement.setSettledAmount(CurrencyManager.convertToAmount(locale, aForm.getSettledCurrency(),
							aForm.getSettledAmount()));
				}
				if (!aForm.getOutstandingCurrency().equals("") && !aForm.getOutstandingAmount().equals("")) {
					newSettlement.setOutstandingAmount(CurrencyManager.convertToAmount(locale, aForm
							.getOutstandingCurrency(), aForm.getOutstandingAmount()));
				}
				newSettlement.setRemarks(aForm.getRemarks());

				if ((oldSettlement != null) && (oldSettlement.length != 0)) {
					for (int i = 0; i < oldSettlement.length; i++) {
						OBSettlement objSettlement = (OBSettlement) oldSettlement[i];
						settlementList.add(objSettlement);
					}
				}
				settlementList.add(newSettlement);
				objBridgingLoan.setSettlementList((ISettlement[]) settlementList.toArray(new ISettlement[0]));
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.toString());
			}
			return objBridgingLoan;
		}
		else if (SettlementAction.EVENT_UPDATE.equals(event)) {
			ISettlement[] newSettlement = (ISettlement[]) objBridgingLoan.getSettlementList();
			int settlementIndex = Integer.parseInt((String) map.get("settlementIndex"));

			if ((newSettlement != null) && (newSettlement.length != 0)) {
				try {
					newSettlement[settlementIndex].setSettlementDate(DateUtil.convertDate(locale, aForm
							.getSettlementDate()));
					if (!aForm.getSettledCurrency().equals("") && !aForm.getSettledAmount().equals("")) {
						newSettlement[settlementIndex].setSettledAmount(CurrencyManager.convertToAmount(locale, aForm
								.getSettledCurrency(), aForm.getSettledAmount()));
					}
					if (!aForm.getOutstandingCurrency().equals("") && !aForm.getOutstandingAmount().equals("")) {
						newSettlement[settlementIndex].setOutstandingAmount(CurrencyManager.convertToAmount(locale,
								aForm.getOutstandingCurrency(), aForm.getOutstandingAmount()));
					}
					newSettlement[settlementIndex].setRemarks(aForm.getRemarks());
					objBridgingLoan.setSettlementList(newSettlement);
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
				}
				return objBridgingLoan;
			}
		}
		else if (SettlementAction.EVENT_DELETE.equals(event)) {
			ArrayList settlementList = new ArrayList();
			ISettlement[] oldSettlement = objBridgingLoan.getSettlementList();
			int settlementIndex = Integer.parseInt((String) map.get("settlementIndex"));

			if ((oldSettlement != null) && (oldSettlement.length != 0)) {
				try {
					if ((oldSettlement != null) && (oldSettlement.length != 0)) {
						for (int i = 0; i < oldSettlement.length; i++) {
							OBSettlement objSettlement = (OBSettlement) oldSettlement[i];
							if (settlementIndex == i) {
								if (objSettlement.getSettlementID() != ICMSConstant.LONG_INVALID_VALUE) {
									objSettlement.setIsDeletedInd(true);
								}
								else {
									continue; // If record not available at db,
												// skip adding
								}
							}
							settlementList.add(objSettlement);
						}
					}
					objBridgingLoan.setSettlementList((ISettlement[]) settlementList.toArray(new ISettlement[0]));
				}
				catch (Exception e) {
					DefaultLogger.debug(this, e.toString());
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
			SettlementForm aForm = (SettlementForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

			if (obj != null) {
				IBridgingLoan objBridgingLoan = (IBridgingLoan) obj;
				ISettlement[] objSettlementList = (ISettlement[]) objBridgingLoan.getSettlementList();
				int settlementIndex = Integer.parseInt((String) map.get("settlementIndex"));

				if (objSettlementList != null) {
					aForm.setSettlementDate(DateUtil.formatDate(locale, objSettlementList[settlementIndex]
							.getSettlementDate()));
					if (objSettlementList[settlementIndex].getSettledAmount() != null) {
						aForm.setSettledCurrency(objSettlementList[settlementIndex].getSettledAmount()
								.getCurrencyCode());
						aForm.setSettledAmount(CurrencyManager.convertToString(locale,
								objSettlementList[settlementIndex].getSettledAmount()));
					}
					if (objSettlementList[settlementIndex].getOutstandingAmount() != null) {
						aForm.setOutstandingCurrency(objSettlementList[settlementIndex].getOutstandingAmount()
								.getCurrencyCode());
						aForm.setOutstandingAmount(CurrencyManager.convertToString(locale,
								objSettlementList[settlementIndex].getOutstandingAmount()));
					}
					aForm.setRemarks(objSettlementList[settlementIndex].getRemarks());
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