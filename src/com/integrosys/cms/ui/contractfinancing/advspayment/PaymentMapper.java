/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.text.DecimalFormat;
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
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.contractfinancing.bus.IPayment;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;
import com.integrosys.cms.app.contractfinancing.bus.OBPayment;

/**
 * Mapper class used to map form values to objects and vice versa
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class PaymentMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public PaymentMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ "paymentIndex", "java.lang.String", REQUEST_SCOPE },
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
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");

		String event = (String) map.get(ICommonEventConstant.EVENT);

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		PaymentForm aForm = (PaymentForm) cForm;

		try {
			OBPayment newPayment = new OBPayment();

			if (PaymentAction.EVENT_UPDATE.equals(event)) { // store old data
															// into OBPayment
				OBAdvance obAdvance = (OBAdvance) map.get("obAdvance");
				String paymentIndex = (String) map.get("paymentIndex");
				newPayment = (OBPayment) obAdvance.getPaymentList()[Integer.parseInt(paymentIndex)];
			}

			if (PaymentAction.EVENT_CREATE.equals(event) || PaymentAction.EVENT_UPDATE.equals(event)) {

				newPayment.setReceiveDate(DateUtil.convertDate(locale, aForm.getReceiveDate()));
				newPayment.setReceiveAmount(CurrencyManager.convertToAmount(locale, aForm.getReceiveCurrency(), aForm
						.getReceiveAmount()));
				newPayment.setReceiveFrom(aForm.getReceiveFrom());
				newPayment.setDistributeDate(DateUtil.convertDate(locale, aForm.getDistributeDate()));
				newPayment.setDistributeAmount(CurrencyManager.convertToAmount(locale, aForm.getDistributeCurrency(),
						aForm.getDistributeAmount()));
				if (aForm.getFdrInd() == null) {
					newPayment.setIsToFDR(false);
				}
				else {
					newPayment.setIsToFDR(true);
					newPayment.setFDRAmount(CurrencyManager.convertToAmount(locale, aForm.getFdrCurrency(), aForm
							.getFdrAmount()));
				}
				if (aForm.getRepoInd() == null) {
					newPayment.setIsToRepo(false);
				}
				else {
					newPayment.setIsToRepo(true);
					newPayment.setRepoAmount(CurrencyManager.convertToAmount(locale, aForm.getRepoCurrency(), aForm
							.getRepoAmount()));
				}
				if (aForm.getApgInd() == null) {
					newPayment.setIsToAPG(false);
				}
				else {
					newPayment.setIsToAPG(true);
					newPayment.setAPGAmount(CurrencyManager.convertToAmount(locale, aForm.getApgCurrency(), aForm
							.getApgAmount()));
				}
				if (aForm.getTl1Ind() == null) {
					newPayment.setIsToTL1(false);
				}
				else {
					newPayment.setIsToTL1(true);
					newPayment.setTL1Amount(CurrencyManager.convertToAmount(locale, aForm.getTl1Currency(), aForm
							.getTl1Amount()));
				}
				if (aForm.getTl2Ind() == null) {
					newPayment.setIsToTL2(false);
				}
				else {
					newPayment.setIsToTL2(true);
					newPayment.setTL2Amount(CurrencyManager.convertToAmount(locale, aForm.getTl2Currency(), aForm
							.getTl2Amount()));
				}
				if (aForm.getTl3Ind() == null) {
					newPayment.setIsToTL3(false);
				}
				else {
					newPayment.setIsToTL3(true);
					newPayment.setTL3Amount(CurrencyManager.convertToAmount(locale, aForm.getTl3Currency(), aForm
							.getTl3Amount()));
				}
				if (aForm.getCollectionAccInd() == null) {
					newPayment.setIsToCollectionAccount(false);
				}
				else {
					newPayment.setIsToCollectionAccount(true);
					newPayment.setCollectionAccountAmount(CurrencyManager.convertToAmount(locale, aForm
							.getCollectionAccCurrency(), aForm.getCollectionAccAmount()));
				}
				if (aForm.getBcInd() == null) {
					newPayment.setIsToBC(false);
				}
				else {
					newPayment.setIsToBC(true);
					newPayment.setBCAmount(CurrencyManager.convertToAmount(locale, aForm.getBcCurrency(), aForm
							.getBcAmount()));
				}
				if (aForm.getOthInd() == null) {
					newPayment.setIsToOthers(false);
				}
				else {
					newPayment.setIsToOthers(true);
					newPayment.setOthersAmount(CurrencyManager.convertToAmount(locale, aForm.getOthCurrency(), aForm
							.getOthAmount()));
				}
				newPayment.setRemarks(aForm.getRemarks());
			}

			if (PaymentAction.EVENT_CREATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				ArrayList paymentList = new ArrayList();
				OBAdvance obAdvance = (OBAdvance) map.get("obAdvance");
				if (obAdvance != null) {
					IPayment[] oldPayment = obAdvance.getPaymentList();

					if ((oldPayment != null) && (oldPayment.length != 0)) {
						for (int i = 0; i < oldPayment.length; i++) {
							OBPayment obPayment = (OBPayment) oldPayment[i];
							paymentList.add(obPayment);
						}
					}
				}
				else {
					obAdvance = new OBAdvance();
				}
				paymentList.add(newPayment);

				obAdvance.setPaymentList((IPayment[]) paymentList.toArray(new OBPayment[0]));
				return obAdvance;
			}
			else if (PaymentAction.EVENT_UPDATE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				OBAdvance obAdvance = (OBAdvance) map.get("obAdvance");

				String paymentIndex = (String) map.get("paymentIndex");

				IPayment[] paymentList = obAdvance.getPaymentList();
				paymentList[Integer.parseInt(paymentIndex)] = newPayment;

				obAdvance.setPaymentList(paymentList);
				return obAdvance;
			}
			else if (PaymentAction.EVENT_DELETE.equals(event)) {

				DefaultLogger.debug(this, ">>>>>>>>>> In Mapping for event=" + event);

				String[] deletedBox = aForm.getDeletedBox();
				ArrayList paymentList = new ArrayList();

				OBAdvance obAdvance = (OBAdvance) map.get("obAdvance");

				IPayment[] oldPayment = obAdvance.getPaymentList();
				if ((oldPayment != null) && (oldPayment.length != 0)) {
					for (int i = 0; i < oldPayment.length; i++) {
						boolean userDelete = false;
						if (deletedBox != null) {
							for (int j = 0; j < deletedBox.length; j++) {
								if (deletedBox[j].equals(String.valueOf(i))) {
									userDelete = true;
								}
							}
						}
						if (userDelete) {
							OBPayment obPayment = (OBPayment) oldPayment[i];
							if (obPayment.getPaymentID() != ICMSConstant.LONG_INVALID_VALUE) {
								obPayment.setIsDeleted(true);
								paymentList.add(obPayment);
							}
							else {
								// not to add
							}
						}
						else {
							OBPayment obPayment = (OBPayment) oldPayment[i];
							paymentList.add(obPayment);
						}
					}
				}

				obAdvance.setPaymentList((IPayment[]) paymentList.toArray(new OBPayment[0]));
				return obAdvance;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
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
			DefaultLogger.debug(this, "inside mapOb to form");
			PaymentForm aForm = (PaymentForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			DefaultLogger.debug(this, "mapOBToForm obj=" + obj);

			if (obj != null) {
				OBPayment obPayment = (OBPayment) obj;

				aForm.setReceiveDate(DateUtil.formatDate(locale, obPayment.getReceiveDate()));
				aForm.setReceiveCurrency(obPayment.getReceiveAmount().getCurrencyCode());
				aForm.setReceiveAmount(new DecimalFormat("#").format(obPayment.getReceiveAmount().getAmount()));
				aForm.setReceiveFrom(obPayment.getReceiveFrom());
				aForm.setDistributeDate(DateUtil.formatDate(locale, obPayment.getDistributeDate()));
				aForm.setDistributeCurrency(obPayment.getDistributeAmount().getCurrencyCode());
				aForm.setDistributeAmount(new DecimalFormat("#").format(obPayment.getDistributeAmount().getAmount()));
				aForm.setFdrInd(String.valueOf(obPayment.getIsToFDR()));
				if (obPayment.getFDRAmount() != null) {
					aForm.setFdrCurrency(obPayment.getFDRAmount().getCurrencyCode());
					aForm.setFdrAmount(new DecimalFormat("#").format(obPayment.getFDRAmount().getAmount()));
				}
				if (obPayment.getRepoAmount() != null) {
					aForm.setRepoCurrency(obPayment.getRepoAmount().getCurrencyCode());
					aForm.setRepoAmount(new DecimalFormat("#").format(obPayment.getRepoAmount().getAmount()));
				}
				if (obPayment.getAPGAmount() != null) {
					aForm.setApgCurrency(obPayment.getAPGAmount().getCurrencyCode());
					aForm.setApgAmount(new DecimalFormat("#").format(obPayment.getAPGAmount().getAmount()));
				}
				if (obPayment.getTL1Amount() != null) {
					aForm.setTl1Currency(obPayment.getTL1Amount().getCurrencyCode());
					aForm.setTl1Amount(new DecimalFormat("#").format(obPayment.getTL1Amount().getAmount()));
				}
				if (obPayment.getTL2Amount() != null) {
					aForm.setTl2Currency(obPayment.getTL2Amount().getCurrencyCode());
					aForm.setTl2Amount(new DecimalFormat("#").format(obPayment.getTL2Amount().getAmount()));
				}
				if (obPayment.getTL3Amount() != null) {
					aForm.setTl3Currency(obPayment.getTL3Amount().getCurrencyCode());
					aForm.setTl3Amount(new DecimalFormat("#").format(obPayment.getTL3Amount().getAmount()));
				}
				if (obPayment.getCollectionAccountAmount() != null) {
					aForm.setCollectionAccCurrency(obPayment.getCollectionAccountAmount().getCurrencyCode());
					aForm.setCollectionAccAmount(new DecimalFormat("#").format(obPayment.getCollectionAccountAmount()
							.getAmount()));
				}
				if (obPayment.getBCAmount() != null) {
					aForm.setBcCurrency(obPayment.getBCAmount().getCurrencyCode());
					aForm.setBcAmount(new DecimalFormat("#").format(obPayment.getBCAmount().getAmount()));
				}
				if (obPayment.getOthersAmount() != null) {
					aForm.setOthCurrency(obPayment.getOthersAmount().getCurrencyCode());
					aForm.setOthAmount(new DecimalFormat("#").format(obPayment.getOthersAmount().getAmount()));
				}
				aForm.setRemarks(obPayment.getRemarks());
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in PaymentMapper is" + e);
		}
		return null;
	}
}
