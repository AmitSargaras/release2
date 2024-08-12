/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.cashmargin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.trx.ICashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue;
import com.integrosys.cms.app.tradingbook.trx.TradingBookHelper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for Cash
 * Margin Description: Map the value from database to the screen or from the
 * screen that user key in to database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class CashMarginMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "CashMarginTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBCashMarginTrxValue", SERVICE_SCOPE },
				{ "InitialCashMargin", "java.lang.Object", FORM_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "SingleCashMarginTrxValue", "com.integrosys.cms.app.tradingbook.bus.OBCashMargin", SERVICE_SCOPE },
				{ "InitialSingleCashMargin", "com.integrosys.cms.app.tradingbook.bus.OBCashMargin", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE }, { "indexChanged", "java.lang.String", REQUEST_SCOPE }, });

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
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String indexChanged = (String) map.get("indexChanged");
		int indChanged = 0;
		if ((indexChanged != null) && !indexChanged.equals("")) {
			indChanged = Integer.parseInt(indexChanged);
		}
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		CashMarginForm aForm = (CashMarginForm) cForm;

		if ("maker_view_cashmargin_isdacsa".equals(event) || "maker_view_cashmargin_gmra".equals(event)
				|| CashMarginAction.EVENT_VIEW.equals(event)) {

			OBCashMargin obCashMargin = new OBCashMargin();

			obCashMargin.setAgreementID(Long.parseLong(aForm.getAgreementID()));

			return obCashMargin;

		}
		else if (event.equals("maker_close_allcashmargin_confirm")) {

			OBCashMargin obCashMargin = new OBCashMargin();
			return obCashMargin;

		}
		else if (event.equals("maker_update_allcashmargin_confirm") || event.equals("maker_edit_reject_confirm")) {

			OBCashMarginTrxValue oldTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");

			OBCashMargin[] newCashMargin = null;

			newCashMargin = (OBCashMargin[]) oldTrxValue.getStagingCashMargin();
			if (newCashMargin != null) {
				return Arrays.asList(newCashMargin);
			}
			return null;

		}
		else if ("maker_update_cashmargin".equals(event) || "maker_update_editreject".equals(event)) {

			OBCashMarginTrxValue obCashMarginTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");
			return obCashMarginTrxValue;

		}
		else if ("maker_create_cashmargin_confirm".equals(event) || "maker_create_editreject_confirm".equals(event)) {

			OBCashMarginTrxValue oldTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");
			// System.out.println(
			// "---------------------------------after oldTrxValue: " +
			// oldTrxValue);

			// copy all old values from ORIGINAL value int newBusinessValue.
			ICashMargin newCashMargin = new OBCashMargin();

			OBCashMargin[] oldCashMargin = null;

			if (indChanged > 0) {
				oldCashMargin = (OBCashMargin[]) oldTrxValue.getStagingCashMargin();
			}
			else {
				oldCashMargin = (OBCashMargin[]) oldTrxValue.getCashMargin();
			}

			try {

				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTrxDate())) {
					Date stageDate = DateUtil.convertDate(locale, aForm.getTrxDate());
					newCashMargin.setTrxDate(stageDate);
				}
				else {
					newCashMargin.setTrxDate(null);
				}

				Amount valAmt = new Amount();
				String curr = oldTrxValue.getCPAgreementDetail().getBaseCurrency();
				valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNapValue()), new CurrencyCode(curr));
				newCashMargin.setNAPAmount(valAmt);

				if (aForm.getNapSignAddValue().equals("Y")) {
					newCashMargin.setNAPSignAddInd(true);
				}
				else {
					newCashMargin.setNAPSignAddInd(false);
				}

			}
			catch (Exception e) {
				DefaultLogger.debug(this, "got exception in mapFormToOB" + e);
				throw (new MapperException(e.getMessage()));
			}

			ArrayList cashMarginList = new ArrayList();
			if ((oldCashMargin != null) && (oldCashMargin.length != 0)) {
				for (int i = 0; i < oldCashMargin.length; i++) {

					ICashMargin cm = oldCashMargin[i];

					if (DB2DateConverter.formatDate(cm.getTrxDate()).equals(
							DB2DateConverter.formatDate(newCashMargin.getTrxDate()))) {
						// sum up the value which has the same trx date
						newCashMargin = TradingBookHelper.sumCashMarginValue(newCashMargin, cm);

					}
					else {

						cashMarginList.add(cm);

					}
				}
			}

			cashMarginList.add(newCashMargin);
			// return Arrays.asList( cashMarginList.toArray (new
			// OBCashMargin[0]) );
			OBCashMarginTrxValue newTrxValue = oldTrxValue;
			newTrxValue.setStagingCashMargin((ICashMargin[]) cashMarginList.toArray(new OBCashMargin[0]));

			System.out.println("---------------------------------after newTrxValue.getCashMargin(): "
					+ AccessorUtil.printMethodValue(newTrxValue.getStagingCashMargin()));
			return newTrxValue;
			// }

		}
		else if ("maker_update_cashmargin_confirm".equals(event) || "maker_update_editreject_confirm".equals(event)) {

			OBCashMarginTrxValue oldTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");
			// System.out.println(
			// "---------------------------------after oldTrxValue: " +
			// oldTrxValue);

			// copy all old values from ORIGINAL value int newBusinessValue.
			OBCashMargin[] newCashMargin = null;
			OBCashMargin[] oldCashMargin = null;

			if (indChanged > 0) {
				newCashMargin = (OBCashMargin[]) oldTrxValue.getStagingCashMargin();
			}
			else {
				newCashMargin = (OBCashMargin[]) oldTrxValue.getCashMargin();
			}

			if (newCashMargin != null) {
				int ind = Integer.parseInt(aForm.getIndex());
				try {
					Amount valAmt = newCashMargin[ind].getNAPAmount();
					// System.out.println(
					// "************** inside maker_update_cashmargin_confirm valAmt : "
					// + valAmt);
					if (valAmt == null) {
						String curr = oldTrxValue.getCPAgreementDetail().getBaseCurrency();
						valAmt = new Amount(UIUtil.mapStringToBigDecimal(aForm.getNapValue()), new CurrencyCode(curr));
					}
					else {
						valAmt.setAmountAsBigDecimal(UIUtil.mapStringToBigDecimal(aForm.getNapValue()));
					}

					newCashMargin[ind].setNAPAmount(valAmt);
					System.out.println("aForm.getNapValue()=" + aForm.getNapValue());

					if (aForm.getNapSignAddValue().equals("Y")) {
						newCashMargin[ind].setNAPSignAddInd(true);
					}
					else {
						newCashMargin[ind].setNAPSignAddInd(false);
					}
				}
				catch (Exception e) {
					DefaultLogger.debug(this, "got exception in mapFormToOB" + e);
					throw (new MapperException(e.getMessage()));
				}
				// System.out.println("////////////////newCashMargin : " +
				// newCashMargin);

				OBCashMarginTrxValue newTrxValue = oldTrxValue;
				newTrxValue.setStagingCashMargin((ICashMargin[]) newCashMargin);

				return newTrxValue;
			}

		}
		else if ("maker_delete_cashmargin".equals(event) || "maker_delete_editreject".equals(event)) {

			// System.out.println(
			// "---------------------------------after map.get(CashMarginTrxValue): "
			// + map.get("CashMarginTrxValue"));
			OBCashMarginTrxValue oldTrxValue = (OBCashMarginTrxValue) map.get("CashMarginTrxValue");
			// System.out.println(
			// "---------------------------------after oldTrxValue: " +
			// oldTrxValue);

			OBCashMarginTrxValue newTrxValue = oldTrxValue;

			// copy all old values from ORIGINAL value int newBusinessValue.
			OBCashMargin[] oldCashMargin = null;
			OBCashMargin[] newCashMargin = null;

			if (indChanged > 0) {
				oldCashMargin = (OBCashMargin[]) oldTrxValue.getStagingCashMargin();
			}
			else {
				oldCashMargin = (OBCashMargin[]) oldTrxValue.getCashMargin();
			}

			if (oldCashMargin != null) {
				if (aForm.getDeletedCreditRating() != null) {
					String[] index = aForm.getDeletedCreditRating();

					if (index.length <= oldCashMargin.length) {
						int itemDeleted = 0;

						for (int k = 0; k < index.length; k++) {
							if (Integer.parseInt(index[k]) < oldCashMargin.length) {
								itemDeleted++;
							}
						}

						if (itemDeleted != 0) {
							newCashMargin = new OBCashMargin[oldCashMargin.length - itemDeleted];
							int i = 0;
							int j = 0;

							while (i < oldCashMargin.length) {
								if ((j < index.length) && (Integer.parseInt(index[j]) == i)) {
									j++;
								}
								else {
									newCashMargin[i - j] = oldCashMargin[i];
								}
								i++;
							}

							newTrxValue.setStagingCashMargin(newCashMargin);

						}
					}
				}

				return newTrxValue;
			}
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String index = (String) map.get("index");
		String indexChanged = (String) map.get("indexChanged");
		int indChanged = 0;
		if ((indexChanged != null) && !indexChanged.equals("")) {
			indChanged = Integer.parseInt(indexChanged);
		}
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {

			CashMarginForm aForm = (CashMarginForm) cForm;
			if (obj != null) {

				if ("maker_update_cashmargin".equals(event) || "maker_update_editreject".equals(event)) {
					ICashMargin sr = (ICashMargin) obj;
					int ind = Integer.parseInt(index);
					aForm.setIndex(index);
					aForm.setTrxDate(DateUtil.formatDate(locale, sr.getTrxDate()));
					aForm.setNapValue(UIUtil.formatAmount(sr.getNAPAmount(), 4, locale, false));

					if (sr.getNAPSignAddInd() == true) {
						aForm.setNapSignAddValue("Y");

					}
					else {
						aForm.setNapSignAddValue("N");
					}

				}
				else if ("maker_view_cashmargin_isdacsa".equals(event) || "maker_view_cashmargin_gmra".equals(event)
						|| "maker_create_cashmargin_confirm".equals(event) || "maker_delete_cashmargin".equals(event)
						|| "maker_update_cashmargin_confirm".equals(event) || "maker_delete_editreject".equals(event)
						|| "maker_create_editreject_confirm".equals(event)
						|| "maker_update_editreject_confirm".equals(event) || "view".equals(event)
						|| "to_track".equals(event) || "maker_close_allcashmargin".equals(event)
						|| "maker_edit_allcashmargin_reject".equals(event)) {
					ICashMarginTrxValue sr = (ICashMarginTrxValue) obj;

					ICashMargin[] sr1 = null;

					if ((indChanged == 0)
							&& ("maker_view_cashmargin_isdacsa".equals(event)
									|| "maker_view_cashmargin_gmra".equals(event) || "view".equals(event))) {
						sr1 = (ICashMargin[]) sr.getCashMargin();
					}
					else {
						sr1 = (ICashMargin[]) sr.getStagingCashMargin();
					}

					if (sr1.length != 0) {
						String displayCashMarginID[] = new String[sr1.length];
						String displayTrxDate[] = new String[sr1.length];
						String displayNapSignAddValue[] = new String[sr1.length];
						String displayNapValue[] = new String[sr1.length];
						String displayCashInterest[] = new String[sr1.length];

						for (int i = 0; i < sr1.length; i++) {
							displayCashMarginID[i] = String.valueOf(sr1[i].getCashMarginID());
							displayTrxDate[i] = DateUtil.formatDate(locale, sr1[i].getTrxDate());
							if (sr1[i].getNAPSignAddInd() == true) {
								displayNapSignAddValue[i] = "Y";
							}
							else {
								displayNapSignAddValue[i] = "N";
							}
							displayNapValue[i] = UIUtil.formatAmount(sr1[i].getNAPAmount(), 4, locale, false);
							if (sr1[i].getCashInterest() != null) {
								displayCashInterest[i] = UIUtil.formatNumber(sr1[i].getCashInterest(), 4, locale);
							}
							else {
								displayCashInterest[i] = null;
							}
						}

						aForm.setDisplayCashMarginID(displayCashMarginID);
						aForm.setDisplayTrxDate(displayTrxDate);
						aForm.setDisplayNapSignAddValue(displayNapSignAddValue);
						aForm.setDisplayNapValue(displayNapValue);
						aForm.setDisplayCashInterest(displayCashInterest);
						if (sr.getTotalCashInterest() != null) {
							String totalCashInterest = UIUtil.formatNumber(new BigDecimal(String.valueOf(sr
									.getTotalCashInterest())), 4, locale);
							aForm.setDisplayTotalCashInterest(totalCashInterest);
						}
						else {
							aForm.setDisplayTotalCashInterest(null);
						}
					}
					else {
						aForm.setDisplayCashMarginID(null);
						aForm.setDisplayTrxDate(null);
						aForm.setDisplayNapSignAddValue(null);
						aForm.setDisplayNapValue(null);
						aForm.setDisplayCashInterest(null);
						aForm.setDisplayTotalCashInterest("0.000");
					}

					System.out.println("inside mapOb to form aForm.getDisplayCashMarginID() = "
							+ aForm.getDisplayCashMarginID());

				}
				else {

					ICashMarginTrxValue sr = (ICashMarginTrxValue) obj;

					ICashMargin[] sr1 = (ICashMargin[]) sr.getCashMargin();

					if (sr1.length != 0) {
						int ind = Integer.parseInt(index);
						aForm.setIndex(index);
						aForm.setTrxDate(DateUtil.formatDate(locale, sr1[ind].getTrxDate()));
						aForm.setNapValue(UIUtil.formatAmount(sr1[ind].getNAPAmount(), 4, locale, false));

						if (sr1[ind].getNAPSignAddInd() == true) {
							aForm.setNapSignAddValue("Y");

						}
						else {
							aForm.setNapSignAddValue("N");
						}
					}

					DefaultLogger.debug(this, "Before putting vector result");
				}
			}
			else {

				aForm.setTrxDate(null);
				aForm.setNapValue(null);
				aForm.setNapSignAddValue(null);
				aForm.setDisplayCashMarginID(null);
				aForm.setDisplayTrxDate(null);
				aForm.setDisplayNapSignAddValue(null);
				aForm.setDisplayNapValue(null);
				aForm.setDisplayCashInterest(null);
				aForm.setDisplayTotalCashInterest("0.000");
			}

			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;

		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in CashMarginMapper is" + e);
		}

		return null;

	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {
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
