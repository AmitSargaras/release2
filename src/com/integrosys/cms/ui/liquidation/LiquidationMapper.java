/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationMapper.java,v 1 2007/02/09 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.Constants;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.liquidation.bus.OBLiquidation;
import com.integrosys.cms.app.liquidation.bus.OBRecovery;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryIncome;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: Map the form to OB or OB to form for
 * Liquidation Description: Map the value from database to the screen or from
 * the screen that user key in to database
 * 
 * @author $Author: lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/09$ Tag: $Name$
 */

public class LiquidationMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "recoveryType", "java.lang.String", SERVICE_SCOPE },
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "InitialLiquidation", "com.integrosys.cms.app.liquidation.bus.ILiquidation", SERVICE_SCOPE },
				{ "LiquidationTrxValue", "com.integrosys.cms.app.liquidation.trx.OBLiquidationTrxValue", SERVICE_SCOPE } });

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
		LiquidationForm aForm = (LiquidationForm) cForm;
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		try {
			DefaultLogger.debug(this, "form event" + event);

			if (LiquidationAction.EV_ADD_RECOVERY_EXPENSE.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY_EXPENSE.equals(event)) {
				OBRecoveryExpense expense = new OBRecoveryExpense();
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpenseType())) {
					expense.setExpenseType(aForm.getExpenseType());
				}
				expense.setDateOfExpense(DateUtil.convertDate(locale, aForm.getDateOfExpense()));
				expense.setExpenseAmount(CurrencyManager.convertToAmount(locale, aForm.getExpenseAmtCurrency(), aForm
						.getExpenseAmt()));
				expense.setExpenseAmtCurrency(aForm.getExpenseAmtCurrency());
				expense.setRemarks(aForm.getExpenseRemarks());
				expense.setSettled(aForm.getSettled());
				return expense;
			}
			else if (LiquidationAction.EV_ADD_RECOVERY.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY.equals(event)) {
				OBRecovery recovery = new OBRecovery();
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getRecoveryType())) {
					recovery.setRecoveryType(aForm.getRecoveryType());
				}

				// income.setTotalAmountRecovered(CurrencyManager.convertToAmount
				// (locale, aForm.getTotalAmtRecoveredCurrency(),
				// aForm.getTotalAmtRecovered()));
				// income.setTotalAmtRecovered(Long.parseLong(aForm.
				// getTotalAmtRecovered()));
				// income.setTotalAmtRecoveredCurrency(aForm.
				// getTotalAmtRecoveredCurrency());
				recovery.setRemarks(aForm.getRecoveryRemarks());
				return recovery;
			}
			else if (LiquidationAction.EV_ADD_RECOVERY_INCOME.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY_INCOME.equals(event)) {
				OBRecoveryIncome income = new OBRecoveryIncome();
				// income.setRecoveryType(aForm.getRecoveryType());
				income.setTotalAmtRecoveredCurrency(aForm.getAmtRecoveredCurrency());
				income.setTotalAmountRecovered(CurrencyManager.convertToAmount(locale, aForm.getAmtRecoveredCurrency(),
						aForm.getAmtRecovered()));
				if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateAmtRecovered())) {
					income.setRecoveryDate(DateUtil.convertDate(locale, aForm.getDateAmtRecovered()));
				}
				income.setRemarks(aForm.getAmtRecoveryRemarks());
				return income;
			}
			else if (LiquidationAction.EVENT_LIST.equals(event) || LiquidationAction.EV_CHECKER_VIEW.equals(event)) {

				OBLiquidation obLiquidation = new OBLiquidation();
				return obLiquidation;

			}
			else if (LiquidationAction.EV_MKR_CLOSE_LIQ_CONFIRM.equals(event)) {

				OBLiquidation obLiquidation = new OBLiquidation();
				return obLiquidation;

			}
			else if (LiquidationAction.EV_MKR_EDIT_LIQ_CONFIRM.equals(event)
					|| LiquidationAction.EV_MKR_EDIT_LIQ_REJECT.equals(event)
					|| LiquidationAction.EV_MKR_EDIT_REJECT_LIQ_CONFIRM.equals(event)) {

				OBLiquidationTrxValue oldTrxValue = (OBLiquidationTrxValue) map.get("LiquidationTrxValue");

				// copy all old values from ORIGINAL value int newBusinessValue.
				OBLiquidation newLiquidations = null;

				if (LiquidationAction.EV_MKR_EDIT_LIQ_CONFIRM.equals(event)) {
					// copy all old values from ORIGINAL value int
					// newBusinessValue.
					newLiquidations = (OBLiquidation) oldTrxValue.getLiquidation();
				}
				else if (LiquidationAction.EV_MKR_EDIT_LIQ_REJECT.equals(event)
						|| LiquidationAction.EV_MKR_EDIT_REJECT_LIQ_CONFIRM.equals(event)) {
					// copy all old values from STAGING value int
					// newBusinessValue.
					newLiquidations = (OBLiquidation) oldTrxValue.getStagingLiquidation();
				}

				if (newLiquidations != null) {
					return newLiquidations;
				}
			}
			return null;
		}
		catch (Exception e) {
			MapperException me = new MapperException("failed to map form to ob");
			me.initCause(e);
			throw me;
		}

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
		String event = (String) map.get(ICommonEventConstant.EVENT);
		String recoveryType = (String) map.get("recoveryType");
		Locale locale = (Locale) map.get(Constants.GLOBAL_LOCALE_KEY);
		OBLiquidation obLiquidation = (OBLiquidation) map.get("InitialLiquidation");
		ILiquidationTrxValue liqTrxVal = (ILiquidationTrxValue) map.get("LiquidationTrxValue");
		try {
			LiquidationForm aForm = (LiquidationForm) cForm;
			DefaultLogger.debug(this, "inside event" + event);
			DefaultLogger.debug(this, "recoveryType" + recoveryType);

			if (LiquidationAction.EV_PREPARE_ADD_RECOVERY_EXPENSE.equals(event)) {

				aForm.setExpenseType("");
				aForm.setDateOfExpense("");
				aForm.setExpenseAmt("");
				aForm.setExpenseAmtCurrency("");
				aForm.setExpenseRemarks("");
				aForm.setSettled("");
			}
			else if (LiquidationAction.EV_PREPARE_ADD_RECOVERY.equals(event)) {

				aForm.setRecoveryType("");
				aForm.setTotalAmtRecovered("");
				aForm.setTotalAmtRecoveredCurrency("");
				aForm.setRecoveryRemarks("");
			}
			else if (LiquidationAction.EV_PREPARE_ADD_RECOVERY_INCOME.equals(event)) {

				aForm.setRecoveryType("");
				aForm.setAmtRecovered("");
				aForm.setAmtRecoveredCurrency("");
				aForm.setAmtRecoveryRemarks("");
			}
			else if (LiquidationAction.EV_VIEW_RECOVERY_EXPENSE.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY_EXPENSE.equals(event)
					|| LiquidationAction.EV_PREPARE_EDIT_RECOVERY_EXPENSE.equals(event)) {

				OBRecoveryExpense obExpense = (OBRecoveryExpense) obj;
				if (LiquidationAction.EV_VIEW_RECOVERY_EXPENSE.equals(event)) {
					aForm.setExpenseType(CommonDataSingleton.getCodeCategoryLabelByValue(
							ICMSConstant.CATEGORY_LIQ_EXPENSE_TYPE, obExpense.getExpenseType()));
				}
				else {
					aForm.setExpenseType(obExpense.getExpenseType());
				}

				aForm.setDateOfExpense(DateUtil.convertToDisplayDate(obExpense.getDateOfExpense()));
				// aForm.setExpenseAmt(CurrencyManager.convertToDisplayString(
				// locale,obExpense.getExpenseAmount()));
				aForm.setExpenseAmt(MapperUtil.mapDoubleToString(obExpense.getExpenseAmount().getAmount(), 2, locale));
				aForm.setExpenseAmtCurrency(obExpense.getExpenseAmtCurrency());
				aForm.setExpenseRemarks(obExpense.getRemarks());
				aForm.setSettled(obExpense.getSettled());
			}
			else if (LiquidationAction.EV_VIEW_RECOVERY_INCOME.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY_INCOME.equals(event)
					|| LiquidationAction.EV_ADD_RECOVERY_INCOME.equals(event) ||
					// LiquidationAction.EV_REMOVE_RECOVERY_INCOME.equals(event)
					// ||
					LiquidationAction.EV_PREPARE_EDIT_RECOVERY_INCOME.equals(event)) {

				OBRecoveryIncome income = (OBRecoveryIncome) obj;
				aForm.setDateAmtRecovered(DateUtil.convertToDisplayDate(income.getRecoveryDate()));
				aForm.setAmtRecovered(CurrencyManager.convertToDisplayString(locale, income.getTotalAmountRecovered()));
				aForm.setAmtRecoveredCurrency(income.getTotalAmountRecovered().getCurrencyCode());
				aForm.setAmtRecoveryRemarks(income.getRemarks());

				// Get Recovery info from session
				if (obLiquidation == null) {
					obLiquidation = (OBLiquidation) liqTrxVal.getStagingLiquidation();
				}
				Collection recovery = obLiquidation.getRecovery();
				for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
					OBRecovery o = (OBRecovery) iterator.next();
					if (o.getRecoveryType().equals(recoveryType)) {
						if (LiquidationAction.EV_VIEW_RECOVERY_INCOME.equals(event)) {
							aForm.setRecoveryType(CommonDataSingleton.getCodeCategoryLabelByValue(
									ICMSConstant.CATEGORY_LIQ_RECOVERY_TYPE, o.getRecoveryType()));
						}
						else {
							aForm.setRecoveryType(o.getRecoveryType());
						}

						Amount amount = null;
						Collection incomeColl = o.getRecoveryIncome();

						if (incomeColl != null) {
							for (Iterator incItr = incomeColl.iterator(); incItr.hasNext();) {
								OBRecoveryIncome obRecoveryIncome = (OBRecoveryIncome) incItr.next();
								if (amount == null) { // first time
									amount = new Amount(obRecoveryIncome.getTotalAmountRecovered().getAmount(),
											obRecoveryIncome.getTotalAmountRecovered().getCurrencyCode());
								}
								else {
									amount.addToThis(obRecoveryIncome.getTotalAmountRecovered());
								}
							}
						}
						if (amount != null) {
							// aForm.setAmtRecovered(CurrencyManager.
							// convertToDisplayString(locale, amount));
							// aForm.setAmtRecoveredCurrency(amount.
							// getCurrencyCode());
							aForm.setTotalAmtRecovered(CurrencyManager.convertToDisplayString(locale, amount));
							aForm.setTotalAmtRecoveredCurrency(amount.getCurrencyCode());
						}

						aForm.setRecoveryRemarks(o.getRemarks());
					}
				}

				// if recovery could not be found in staging, it has been
				// deleted, get from actual.
				if (!((aForm.getRecoveryType() != null) && (aForm.getRecoveryType().length() > 0))) {
					obLiquidation = (OBLiquidation) liqTrxVal.getLiquidation();
				}
				recovery = obLiquidation.getRecovery();
				for (Iterator iterator = recovery.iterator(); iterator.hasNext();) {
					OBRecovery o = (OBRecovery) iterator.next();
					if (o.getRecoveryType().equals(recoveryType)) {
						if (LiquidationAction.EV_VIEW_RECOVERY_INCOME.equals(event)) {
							aForm.setRecoveryType(CommonDataSingleton.getCodeCategoryLabelByValue(
									ICMSConstant.CATEGORY_LIQ_RECOVERY_TYPE, o.getRecoveryType()));
						}
						else {
							aForm.setRecoveryType(o.getRecoveryType());
						}
					}
				}
			}
			else if (LiquidationAction.EV_VIEW_RECOVERY.equals(event)
					|| LiquidationAction.EV_EDIT_RECOVERY.equals(event)
					|| LiquidationAction.EV_REMOVE_RECOVERY_INCOME.equals(event)
					|| LiquidationAction.EV_PREPARE_EDIT_RECOVERY.equals(event)) {
				OBRecovery recovery = (OBRecovery) obj;
				if (LiquidationAction.EV_VIEW_RECOVERY.equals(event)) {
					aForm.setRecoveryType(CommonDataSingleton.getCodeCategoryLabelByValue(
							ICMSConstant.CATEGORY_LIQ_RECOVERY_TYPE, recovery.getRecoveryType()));
				}
				else {
					aForm.setRecoveryType(recovery.getRecoveryType());
				}

				DefaultLogger.debug(this, "Recovery Type " + recovery.getRecoveryType());

				Amount amount = null;
				Collection incomeColl = recovery.getRecoveryIncome();

				if (incomeColl != null) {
					for (Iterator incItr = incomeColl.iterator(); incItr.hasNext();) {
						OBRecoveryIncome obRecoveryIncome = (OBRecoveryIncome) incItr.next();
						if (amount == null) { // first time
							amount = new Amount(obRecoveryIncome.getTotalAmountRecovered().getAmount(),
									obRecoveryIncome.getTotalAmountRecovered().getCurrencyCode());
//							System.out.println("=== null condition amount = " + amount);
						}
						else {
							amount.addToThis(obRecoveryIncome.getTotalAmountRecovered());
//							System.out.println("else condition amount = " + amount);
						}
					}
				}
				if (amount != null) {
					// aForm.setAmtRecovered(CurrencyManager.
					// convertToDisplayString(locale, amount));
					// aForm.setAmtRecoveredCurrency(amount.getCurrencyCode());
					aForm.setTotalAmtRecovered(CurrencyManager.convertToDisplayString(locale, amount));
					aForm.setTotalAmtRecoveredCurrency(amount.getCurrencyCode());

				}
				aForm.setRecoveryRemarks(recovery.getRemarks());
			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			MapperException me = new MapperException("failed to map ob to form");
			me.initCause(e);
			throw me;
		}
	}

}
