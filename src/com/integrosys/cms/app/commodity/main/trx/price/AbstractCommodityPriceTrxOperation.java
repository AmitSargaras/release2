/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/AbstractCommodityPriceTrxOperation.java,v 1.5 2006/10/12 03:14:30 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of commodity
 * price trx operations.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/10/12 03:14:30 $ Tag: $Name: $
 */
public abstract class AbstractCommodityPriceTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Pre process. Prepares the transaction object for persistance
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		try {
			if (value.getTransactionID() == null) {
				ICommodityPriceTrxValue trxValue = getCommodityPriceTrxValue(value);
				ICommodityPrice[] stage = trxValue.getStagingCommodityPrice();
				CommodityPriceTrxDAO dao = new CommodityPriceTrxDAO();
				DefaultLogger.debug(this, "TrxType : " + trxValue.getTransactionType());
				ICommodityPriceTrxValue tempTrx = dao.getCommodityPriceTrxValue(stage, trxValue.getTransactionType(),
						true);
				if ((tempTrx.getStatus() != null) && !tempTrx.getStatus().equals(ICMSConstant.STATE_ND)) {
					throw new Exception("Concurrent Exception: Commodity Price Transaction exists!!!");
				}
			}
			return value;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a commodity price
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return commodity price specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICommodityPriceTrxValue
	 */
	protected ICommodityPriceTrxValue getCommodityPriceTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICommodityPriceTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICommodityPriceTrxValue: " + e.toString());
		}
	}

	/**
	 * Create actual commodity price record.
	 * 
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors creating the commodity price
	 */
	protected ICommodityPriceTrxValue createActualCommodityPrice(ICommodityPriceTrxValue value)
			throws TrxOperationException {
		try {
			ICommodityPrice[] prices = value.getStagingCommodityPrice(); // create
																			// get
																			// from
																			// staging
			ICommodityMainInfoManager mgr = getBusManager();
			prices = (ICommodityPrice[]) mgr.createInfo(prices);
			value.setCommodityPrice(prices); // set into actual
			return value;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create commodity price history records.
	 * 
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors creating the commodity price
	 */
	protected ICommodityPriceTrxValue createCommodityPriceHistory(ICommodityPriceTrxValue value)
			throws TrxOperationException {
		try {
			// history always get from actual.
			ICommodityPrice[] prices = value.getCommodityPrice();
			ICommodityMainInfoManager mgr = getBusManager();
			prices = mgr.createCommodityPriceHistory(prices);
			return value;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create staging commodity price record.
	 * 
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICommodityPriceTrxValue createStagingCommodityPrice(ICommodityPriceTrxValue value)
			throws TrxOperationException {
		try {
			ICommodityPrice[] prices = value.getStagingCommodityPrice();
			ICommodityMainInfoManager mgr = getStagingBusManager();
			prices = (ICommodityPrice[]) mgr.createInfo(prices);
			value.setStagingCommodityPrice(prices);
			return value;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update actual commodity price record.
	 * 
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors updating the actual commodity
	 *         price
	 */
	protected ICommodityPriceTrxValue updateActualCommodityPrice(ICommodityPriceTrxValue value)
			throws TrxOperationException {
		try {
			ICommodityPrice[] actual = value.getCommodityPrice();
			ICommodityPrice[] staging = value.getStagingCommodityPrice(); // update
																			// from
																			// staging

			long actualGroupID = getGroupID(actual);
			long stageGroupID = getGroupID(staging);

			if ((actual != null) && (staging != null)) {
				for (int i = 0; i < staging.length; i++) {
					ICommodityPrice actualPrice = getTheCommodityPrice(staging[i], actual);
					if (actualPrice == null) {
						staging[i].setCommodityPriceID(ICMSConstant.LONG_INVALID_VALUE);
					}
					else {
						staging[i].setCommodityPriceID(actualPrice.getCommodityPriceID()); // maintain
																							// actual
																							// 's
																							// pk
						staging[i].setVersionTime(actualPrice.getVersionTime()); // and
																					// actual
																					// 's
																					// version
																					// time
						Date today = new Date();
						if (isCurrentPriceChanged(actualPrice, staging[i])) {
							staging[i].setCurrentFirstUpdateDate(today);
						}
						if (isClosePriceChanged(actualPrice, staging[i])) {
							staging[i].setCloseFirstUpdateDate(today);
						}
					}
					staging[i].setGroupID(actualGroupID); // and actual's group
															// id
					// DefaultLogger.debug(this,"Close: "+staging[i].
					// getCloseFirstUpdateDate());
					// DefaultLogger.debug(this,"Current: "+staging[i].
					// getCurrentFirstUpdateDate());
				}

				ICommodityMainInfoManager mgr = getBusManager();
				actual = (ICommodityPrice[]) mgr.updateInfo(staging);
				value.setCommodityPrice(actual); // set into actual

				// set back the staging group id for transaction staging ref id
				value.getStagingCommodityPrice()[0].setGroupID(stageGroupID);
			}

			return value;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICommodityPriceTrxValue createTransaction(ICommodityPriceTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBCommodityPriceTrxValue newValue = new OBCommodityPriceTrxValue(tempValue);
			newValue.setCommodityPrice(value.getCommodityPrice());
			newValue.setStagingCommodityPrice(value.getStagingCommodityPrice());
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
	 * @param value is of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICommodityPriceTrxValue updateTransaction(ICommodityPriceTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);

			OBCommodityPriceTrxValue newValue = new OBCommodityPriceTrxValue(tempValue);
			newValue.setCommodityPrice(value.getCommodityPrice());
			newValue.setStagingCommodityPrice(value.getStagingCommodityPrice());
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
	 * Prepares a result object to be returned
	 * 
	 * @param value is of type ICMSTrxResult
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICommodityPriceTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ICommodityPriceTrxValue
	 * @return commodity price transaction value
	 */
	private ICommodityPriceTrxValue prepareTrxValue(ICommodityPriceTrxValue value) {
		if (value != null) {
			ICommodityPrice[] actual = value.getCommodityPrice();
			ICommodityPrice[] staging = value.getStagingCommodityPrice();

			long actualGroupID = getGroupID(actual);
			long stagingGroupID = getGroupID(staging);

			value.setReferenceID(actualGroupID != ICMSConstant.LONG_MIN_VALUE ? String.valueOf(actualGroupID) : null);
			value.setStagingReferenceID(stagingGroupID != ICMSConstant.LONG_MIN_VALUE ? String.valueOf(stagingGroupID)
					: null);
		}
		return value;
	}

	/**
	 * Helper method to get group id given a list of commodity prices.
	 * 
	 * @param prices a list of ICommodityPrice objects
	 * @return group id
	 */
	private long getGroupID(ICommodityPrice[] prices) {
		if (prices != null) {
			for (int i = 0; i < prices.length; i++) {
				if (prices[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return prices[i].getGroupID();
				}
			}
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to check if the commodity price is in the given array of
	 * commodity price objects.
	 * 
	 * @param o of type ICommodityPrice
	 * @param anArray a list of commodity price objects
	 * @return if found returns the object, otherwise returns null
	 */
	private ICommodityPrice getTheCommodityPrice(ICommodityPrice o, ICommodityPrice[] anArray) {
		for (int i = 0; i < anArray.length; i++) {
			if (o.getProfileID() == anArray[i].getProfileID()) {
				return anArray[i];
			}
		}
		return null;
	}

	/**
	 * Helper method to get handler to actual commodity main info manager.
	 * 
	 * @return the actual handler to bus manager
	 */
	private ICommodityMainInfoManager getBusManager() {
		return CommodityMainInfoManagerFactory.getManager();
	}

	/**
	 * Helper method to get handler to staging commodity main info manager.
	 * 
	 * @return the staging handler to bus manager
	 */
	private ICommodityMainInfoManager getStagingBusManager() {
		return CommodityMainInfoManagerFactory.getStagingManager();
	}

	private boolean isCurrentPriceChanged(ICommodityPrice newCmdtPrice, ICommodityPrice oldCmdtPrice) {
		BigDecimal newPrice = null;
		String newCcy = null;
		BigDecimal oldPrice = null;
		String oldCcy = null;
		if (newCmdtPrice.getCurrentPrice() != null) {
			newPrice = newCmdtPrice.getCurrentPrice().getAmountAsBigDecimal();
			newCcy = newCmdtPrice.getCurrentPrice().getCurrencyCode();
		}
		if (oldCmdtPrice.getCurrentPrice() != null) {
			oldPrice = oldCmdtPrice.getCurrentPrice().getAmountAsBigDecimal();
			oldCcy = oldCmdtPrice.getCurrentPrice().getCurrencyCode();
		}
		//DefaultLogger.debug(this,"Current Price ---------------------------");
		return isPriceChanged(newPrice, oldPrice, newCcy, oldCcy);
	}

	private boolean isClosePriceChanged(ICommodityPrice newCmdtPrice, ICommodityPrice oldCmdtPrice) {
		BigDecimal newPrice = null;
		String newCcy = null;
		BigDecimal oldPrice = null;
		String oldCcy = null;
		if (newCmdtPrice.getClosePrice() != null) {
			newPrice = newCmdtPrice.getClosePrice().getAmountAsBigDecimal();
			newCcy = newCmdtPrice.getClosePrice().getCurrencyCode();
		}
		if (oldCmdtPrice.getClosePrice() != null) {
			oldPrice = oldCmdtPrice.getClosePrice().getAmountAsBigDecimal();
			oldCcy = oldCmdtPrice.getClosePrice().getCurrencyCode();
		}
		// DefaultLogger.debug(this,"Close Price ---------------------------");
		return isPriceChanged(newPrice, oldPrice, newCcy, oldCcy);
	}

	private boolean isPriceChanged(BigDecimal newPrice, BigDecimal oldPrice, String newCcy, String oldCcy) {
		// DefaultLogger.debug(this,"NewPrice : "+newPrice);
		// DefaultLogger.debug(this,"OldPrice : "+oldPrice);
		// DefaultLogger.debug(this,"NewCcy : "+newCcy);
		// DefaultLogger.debug(this,"OldCcy : "+oldCcy);
		if (newPrice == null) {
			if (oldPrice != null) {
				return true;
			}
		}
		else {
			if (newPrice.compareTo(oldPrice) != 0) {
				return true;
			}
		}
		if (newCcy == null) {
			if (oldCcy != null) {
				return true;
			}
		}
		else {
			if (newCcy.compareTo(oldCcy) != 0) {
				return true;
			}
		}
		// DefaultLogger.debug(this,"Price is unchanged !");
		return false;
	}
}