/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.trx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager;
import com.integrosys.cms.app.interestrate.bus.InterestRateBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.ICashMargin;
import com.integrosys.cms.app.tradingbook.bus.ITradingBookBusManager;
import com.integrosys.cms.app.tradingbook.bus.OBCashMargin;
import com.integrosys.cms.app.tradingbook.bus.TradingBookBusManagerFactory;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of cash
 * margin trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractCashMarginTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a cash margin
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return cash margin specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICashMarginTrxValue
	 */
	protected ICashMarginTrxValue getCashMarginTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICashMarginTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICashMarginTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging cash margin records.
	 * 
	 * @param value is of type ICashMarginTrxValue
	 * @return cash margin transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICashMarginTrxValue createStagingCashMargin(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			long agreemtID = value.getAgreementID();
			ICashMargin[] actual = value.getCashMargin();
			ICashMargin[] staging = value.getStagingCashMargin();

			if (actual != null) {
				DefaultLogger.debug(this, " Actual length: " + actual.length);
			}
			DefaultLogger.debug(this, " AgreementID: " + agreemtID);

			if ((staging != null) && (staging.length > 0)) {
				DefaultLogger.debug(this, " Staging length: " + staging.length);

				for (int i = 0; i < staging.length; i++) {
					staging[i].setStatus(value.getToState());
				}

				ITradingBookBusManager mgr = TradingBookBusManagerFactory.getStagingTradingBookBusManager();
				staging = mgr.createCashMargin(agreemtID, staging);
				value.setStagingCashMargin(staging);
			}
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create actual cash margin records.
	 * 
	 * @param value is of type ICashMarginTrxValue
	 * @return cash margin transaction value
	 * @throws TrxOperationException on errors creating the cash margin
	 */
	protected ICashMarginTrxValue createActualCashMargin(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			ICashMargin[] cashMargin = value.getStagingCashMargin(); // create
																		// get
																		// from
																		// staging
			long agreemtID = value.getAgreementID();

			for (int i = 0; i < cashMargin.length; i++) {
				cashMargin[i].setStatus(value.getToState());
			}

			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();
			cashMargin = mgr.createCashMargin(agreemtID, cashMargin);
			value.setCashMargin(cashMargin); // set into actual
			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}

	}

	/**
	 * Updates a list of actual cash margin using a list of staging cash margin.
	 * 
	 * @param value of type ICashMarginTrxValue
	 * @return updated cash margin transaction value
	 * @throws TrxOperationException on errors updating the actual cash margin
	 */
	protected ICashMarginTrxValue updateActualCashMargin(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			ICashMargin[] actual = value.getCashMargin();
			ICashMargin[] staging = value.getStagingCashMargin();

			DefaultLogger.debug(this, " Actual length: " + actual.length);
			if (staging != null) {
				DefaultLogger.debug(this, " Staging length: " + staging.length);
			}
			ITradingBookBusManager mgr = TradingBookBusManagerFactory.getActualTradingBookBusManager();

			long agreemtID = value.getAgreementID();

			DefaultLogger.debug(this, " Agreement ID: " + agreemtID);

			ArrayList createList = new ArrayList();
			for (int i = 0; i < actual.length; i++) {
				boolean found = false;
				DefaultLogger.debug(this, " processing actual cash margin ID: "
						+ DB2DateConverter.formatDate(actual[i].getTrxDate()));

				if (staging != null) {
					for (int j = 0; j < staging.length; j++) {

						if (DB2DateConverter.formatDate(actual[i].getTrxDate()).equals(
								DB2DateConverter.formatDate(staging[j].getTrxDate()))) {

							actual[i].setStatus(value.getToState());

							DefaultLogger.debug(this, " Update cash margin ID: "
									+ DB2DateConverter.formatDate(actual[i].getTrxDate()));

							actual[i].setNAPAmount(staging[j].getNAPAmount());
							actual[i].setNAPSignAddInd(staging[j].getNAPSignAddInd());
							actual[i].setCashInterest(staging[j].getCashInterest());
							actual[i].setUpdateIndicator(OBCashMargin.UPDATE_INDICATOR);
							found = true;
							break;

						}
					}
				}
				if (!found) {
					DefaultLogger.debug(this, " Delete cash margin ID: "
							+ DB2DateConverter.formatDate(actual[i].getTrxDate()));

					actual[i].setStatus(ICMSConstant.STATE_DELETED);
					actual[i].setUpdateIndicator(OBCashMargin.DELETE_INDICATOR);

				}
				createList.add(actual[i]);
			}

			if (staging != null) {

				// create staging
				for (int j = 0; j < staging.length; j++) {
					boolean found = false;
					for (int i = 0; i < actual.length; i++) {

						if (DB2DateConverter.formatDate(actual[i].getTrxDate()).equals(
								DB2DateConverter.formatDate(staging[j].getTrxDate()))) {
							found = true;
							break;
						}
					}
					if (!found) {
						DefaultLogger.debug(this, " Create cash margin ID: "
								+ DB2DateConverter.formatDate(staging[j].getTrxDate()));
						staging[j].setUpdateIndicator(OBCashMargin.CREATE_INDICATOR);
						staging[j].setStatus(value.getToState());
						createList.add(staging[j]);
					}
				}
			}
			/*
			 * ICashMargin[] cmArray = new OBCashMargin[createList.size()];
			 * 
			 * for (int k = 0; k < createList.size(); k++) {
			 * 
			 * cmArray[k] = (ICashMargin)createList.get(k); DefaultLogger.debug
			 * (this, "Convert cash margin ID: " + DB2DateConverter.formatDate(
			 * cmArray[k].getTrxDate() ) ); DefaultLogger.debug (this,
			 * "Convert cash margin ID: " + cmArray[k].getUpdateIndicator() );
			 * DefaultLogger.debug (this, "Convert cash margin ID: " +
			 * cmArray[k] ); }
			 */
			actual = mgr.updateCashMargin(agreemtID, TradingBookHelper.toCashMarginArray(createList));

			value.setCashMargin(actual); // set into actual

			return value;
		}
		catch (TradingBookException e) {
			throw new TrxOperationException("TradingBookException caught!", e);
		}
	}

	protected ICashMarginTrxValue calcInterest(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			ICashMargin[] cashMargin = value.getStagingCashMargin(); // create
																		// get
																		// from
																		// staging
			String intRateType = value.getCPAgreementDetail().getIntRateType();
			DefaultLogger.debug(this, " Interest rate type : " + intRateType);

			Date monthYear = cashMargin[0].getTrxDate();

			IInterestRateBusManager mgr1 = InterestRateBusManagerFactory.getActualInterestRateBusManager();

			IInterestRate[] intList = mgr1.getInterestRate(intRateType, monthYear);

			for (int i = 0; i < cashMargin.length; i++) {
				DefaultLogger.debug(this, " Processing cash margin : "
						+ DB2DateConverter.formatDate(cashMargin[i].getTrxDate()));
				for (int j = 0; j < intList.length; j++) {

					DefaultLogger.debug(this, " Processing interest rate: "
							+ DB2DateConverter.formatDate(intList[j].getIntRateDate()));
					if (DB2DateConverter.formatDate(cashMargin[i].getTrxDate()).equals(
							DB2DateConverter.formatDate(intList[j].getIntRateDate()))) {
						// calculate
						BigDecimal cashInt = TradingBookHelper.calcCashMarginInterest(cashMargin[i], intList[j]);
						;
						DefaultLogger.debug(this, " Calc interest rate: "
								+ (cashInt != null ? cashInt.toString() : "no value"));
						cashMargin[i].setCashInterest(cashInt);

						// found = true;
						break;
					}
				}
			}
			value.setStagingCashMargin(cashMargin); // set into actual
			return value;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}

	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICashMarginTrxValue
	 * @return cash margin transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICashMarginTrxValue createTransaction(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBCashMarginTrxValue newValue = new OBCashMarginTrxValue(tempValue);
			newValue.setCashMargin(value.getCashMargin());
			newValue.setStagingCashMargin(value.getStagingCashMargin());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICashMarginTrxValue
	 * @return cash margin transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICashMarginTrxValue updateTransaction(ICashMarginTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBCashMarginTrxValue newValue = new OBCashMarginTrxValue(tempValue);
			newValue.setCashMargin(value.getCashMargin());
			newValue.setStagingCashMargin(value.getStagingCashMargin());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Prepares a result object to be returned.
	 * 
	 * @param value is of type ICashMarginTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICashMarginTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ICashMarginTrxValue
	 * @return cash margin transaction value
	 */
	private ICashMarginTrxValue prepareTrxValue(ICashMarginTrxValue value) {
		if (value != null) {
			ICashMargin[] actual = value.getCashMargin();
			ICashMargin[] staging = value.getStagingCashMargin();

			if ((actual != null)
					&& (actual.length != 0)
					&& (actual[0].getAgreementID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				DefaultLogger.debug(this, "PrepareTrxValue for actual=" + actual[0].getAgreementID());
				value.setReferenceID(String.valueOf(actual[0].getAgreementID()));
			}
			else {
				value.setReferenceID(null);
			}
			if ((staging != null)
					&& (staging.length != 0)
					&& (staging[0].getGroupID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)) {
				DefaultLogger.debug(this, "PrepareTrxValue for staging=" + staging[0].getGroupID());

				value.setStagingReferenceID(String.valueOf(staging[0].getGroupID()));
			}
			else {
				value.setStagingReferenceID(null);
			}
		}

		return value;
	}
}
