/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/AbstractCommodityDealTrxOperation.java,v 1.24 2006/08/11 03:04:43 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.CollateralValuator;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.commodity.deal.bus.CollateralPoolDealNo;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealBusManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.app.commodity.deal.bus.DealStatus;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealBusManager;
import com.integrosys.cms.app.commodity.deal.bus.OBCommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.SpecificTransactionDealNo;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.deal.proxy.ICommodityDealProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of commodity
 * deal trx operations.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2006/08/11 03:04:43 $ Tag: $Name: $
 */
public abstract class AbstractCommodityDealTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	/**
	 * Helper method to cast a generic trx value object to a commodity deal
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return commodity deal specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICommodityDealTrxValue
	 */
	protected ICommodityDealTrxValue getCommodityDealTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICommodityDealTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICommodityDealTrxValue: " + e.toString());
		}
	}

	/**
	 * Revaluate commodity update deal approval.
	 * 
	 * @param dealTrx of type ICommodityDealTrxValue
	 */
	protected void revaluateCommodity(ICommodityDealTrxValue dealTrx) {
		ITrxContext trxCtx = dealTrx.getTrxContext();
		ICommodityDeal deal = dealTrx.getCommodityDeal();
		try {
			CommodityDealSearchCriteria criteria = new CommodityDealSearchCriteria();
			criteria.setCollateralID(deal.getCollateralID());
			criteria.setSearchByColID(true);
			ICommodityDealProxy dealProxy = CommodityDealProxyFactory.getProxy();
			SearchResult rs = dealProxy.searchDeal(trxCtx, criteria);
			ICommodityDeal[] deals = (ICommodityDeal[]) rs.getResultList().toArray(new OBCommodityDeal[0]);

			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();
			ICollateralTrxValue colTrx = colProxy.getCollateralTrxValue(trxCtx, deal.getCollateralID());
			CollateralValuator valuator = new CollateralValuator();
			valuator.setCommodityCMVFSV((ICommodityCollateral) colTrx.getCollateral(), deals, deal);
			if (colTrx.getCollateral().getValuation() != null) {
				colTrx.getCollateral().getValuation().setValuationID(ICMSConstant.LONG_INVALID_VALUE);
				colTrx.getStagingCollateral().setValuation(colTrx.getCollateral().getValuation());
			}
			colProxy.systemUpdateCollateral(trxCtx, colTrx);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "Error in cacl cmdt cmv/fsv", e);
		}
	}

	/**
	 * Create actual commodity deal record.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors creating the deal
	 */
	protected ICommodityDealTrxValue createActualDeal(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal deal = value.getStagingCommodityDeal(); // create get
																	// from
																	// staging
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
			// deal.setStatus (value.getToState());
			deal.setStatus(determineDealStatus(value)); // CR119
			IDealCashDeposit[] cashDeposits = deal.getCashDeposit();
			for (int i = 0; i < cashDeposits.length; i++) {
				cashDeposits[i].setLastUpdatedDate(DateUtil.getDate());
			}
			deal.setCashDeposit(cashDeposits);
			deal = mgr.createDeal(deal);
			value.setCommodityDeal(deal); // set into actual
			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create staging commodity deal record.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICommodityDealTrxValue createStagingDeal(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal deal = value.getStagingCommodityDeal();
			if ((deal.getDealNo() == null)) {
				deal.setDealNo(getNewDealNo(value));
			}
			// staging.setStatus (value.getToState()); //need to change to cater
			// for new status. refer to CR119.
			deal.setStatus(determineDealStatus(value)); // CR119
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getStagingCommodityDealBusManager();
			deal = mgr.createDeal(deal);
			value.setStagingCommodityDeal(deal);
			value.setDealNo(deal.getDealNo());
			DefaultLogger.debug(this, "dealNo: --------------------------------- " + value.getDealNo());
			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update staging deal record.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors updating the actual deal
	 */
	protected ICommodityDealTrxValue updateStagingDeal(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal staging = value.getStagingCommodityDeal();
			staging.setStatus(determineDealStatus(value)); // CR119
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getStagingCommodityDealBusManager();
			staging = mgr.updateDeal(staging);
			value.setStagingCommodityDeal(staging);
			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update staging deal status.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors updating the actual deal
	 */
	protected ICommodityDealTrxValue updateStagingDealStatus(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal staging = value.getStagingCommodityDeal();
			staging.setStatus(determineDealStatus(value)); // CR119
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getStagingCommodityDealBusManager();
			staging = mgr.updateDealStatus(staging);
			value.setStagingCommodityDeal(staging);
			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update staging deal status.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors updating the actual deal
	 */
	protected ICommodityDealTrxValue updateActualDealStatus(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal actual = value.getCommodityDeal();
			actual.setStatus(determineDealStatus(value)); // CR119
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
			actual = mgr.updateDealStatus(actual);
			value.setCommodityDeal(actual);
			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update actual deal record.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors updating the actual deal
	 */
	protected ICommodityDealTrxValue updateActualDeal(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			ICommodityDeal actual = value.getCommodityDeal();
			ICommodityDeal staging = value.getStagingCommodityDeal(); // update
																		// from
																		// staging

			long stageDealID = staging.getCommodityDealID();
			long stageVersion = staging.getVersionTime();

			staging.setCommodityDealID(actual.getCommodityDealID()); // but
																		// maintain
																		// actual
																		// 's pk
			staging.setVersionTime(actual.getVersionTime()); // and actual's
																// version time
			staging.setStatus(determineDealStatus(value)); // CR119
			ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
			IDealCashDeposit[] actualcashDeposits = actual.getCashDeposit();
			IDealCashDeposit[] stagingcashDeposits = staging.getCashDeposit();
			for (int i = 0; i < stagingcashDeposits.length; i++) {
				int count = 0;
				for (int j = 0; j < actualcashDeposits.length; j++) {
					int change = 0;
					if (actualcashDeposits[j].getCommonReferenceID() == stagingcashDeposits[i].getCommonReferenceID()) {
						count = 1;
						if (!RefCompare(actualcashDeposits[j].getReferenceNo(), stagingcashDeposits[i].getReferenceNo())) {
							change = 1;
						}
						if (!MatDtCompare(actualcashDeposits[j].getMaturityDate(), stagingcashDeposits[i]
								.getMaturityDate())) {
							change = 1;
						}
						if (!AmountCompare(actualcashDeposits[j].getAmount(), stagingcashDeposits[i].getAmount())) {
							change = 1;
						}
						if (change == 1) {
							stagingcashDeposits[i].setLastUpdatedDate(DateUtil.getDate());
						}
						else {
							stagingcashDeposits[i].setLastUpdatedDate(actualcashDeposits[j].getLastUpdatedDate());
						}
					}
				}
				if (count == 0) {
					stagingcashDeposits[i].setLastUpdatedDate(DateUtil.getDate());
				}
			}
			actual = mgr.updateDeal(staging);

			value.setCommodityDeal(actual); // set into actual

			// set back the staging deal id for transaction ref id
			value.getStagingCommodityDeal().setCommodityDealID(stageDealID);
			value.getStagingCommodityDeal().setVersionTime(stageVersion);

			return value;
		}
		catch (CommodityDealException e) {
			throw new TrxOperationException("CommodityDealException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICommodityDealTrxValue createTransaction(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBCommodityDealTrxValue newValue = new OBCommodityDealTrxValue(tempValue);
			newValue.setCommodityDeal(value.getCommodityDeal());
			newValue.setStagingCommodityDeal(value.getStagingCommodityDeal());
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
	 * @param value is of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICommodityDealTrxValue updateTransaction(ICommodityDealTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBCommodityDealTrxValue newValue = new OBCommodityDealTrxValue(tempValue);
			newValue.setCommodityDeal(value.getCommodityDeal());
			newValue.setStagingCommodityDeal(value.getStagingCommodityDeal());
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
	 * @param value is of type ICommodityDealTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICommodityDealTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Method to process to process notification when deal gets created or
	 * rejected.
	 * 
	 * @param deal
	 * @param value
	 */
	// protected void processNotification(ICommodityDeal deal,
	// ICommodityDealTrxValue value)
	// throws TrxOperationException
	// {
	// try {
	// OBCommodityDealNotifyInfo notifyInfo = new OBCommodityDealNotifyInfo();
	// notifyInfo.setTransactionID(value.getTransactionID());
	// notifyInfo.setCommodity(value.getCommodityCollateral());
	// notifyInfo.setDeal(deal);
	// notifyInfo.setCustomerID(value.getLegalID());
	// notifyInfo.setCustomerName(value.getLegalName());
	// notifyInfo.setOriginatingCountry(value.getOriginatingCountry());
	// notifyInfo.setOperationName(this.getOperationName());
	// notifyInfo.setTrxUserID(value.getUID());
	// notifyInfo.setTrxUserTeamID(value.getTeamID());
	//
	// CommodityDealBusManagerFactory.getActualCommodityDealBusManager().
	// processNotification(notifyInfo);
	// }
	// catch (CommodityDealException e) {
	// throw new TrxOperationException ("CommodityDealException caught!", e);
	// }
	// catch (Exception e) {
	// throw new TrxOperationException("Exception caught!", e);
	// }
	// }
	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ICommodityDealTrxValue
	 * @return commodity deal transaction value
	 */
	private ICommodityDealTrxValue prepareTrxValue(ICommodityDealTrxValue value) {
		if (value != null) {
			ICommodityDeal actual = value.getCommodityDeal();
			ICommodityDeal staging = value.getStagingCommodityDeal();

			value.setReferenceID(actual != null ? String.valueOf(actual.getCommodityDealID()) : null);
			value.setStagingReferenceID((staging != null)
					&& (staging.getCommodityDealID() != ICMSConstant.LONG_MIN_VALUE) ? String.valueOf(staging
					.getCommodityDealID()) : null);
		}
		return value;
	}

	private String getNewDealNo(ICommodityDealTrxValue trxValue) throws Exception {
		String ctryCode = trxValue.getOriginatingCountry();
		ICommodityDeal deal = trxValue.getStagingCommodityDeal();
		if (ICMSConstant.DEAL_TYPE_SPECIFIC_TRANSACTION.equals(deal.getDealTypeCode())) {
			return (new SpecificTransactionDealNo(ctryCode).getNewDealNo());
		}
		else if (ICMSConstant.DEAL_TYPE_COLLATERAL_POOL.equals(deal.getDealTypeCode())) {
			return (new CollateralPoolDealNo(ctryCode).getNewDealNo());
		}
		return null;
	}

	/**
	 * Determines the status of the staging commodity deal. If this is a newly
	 * created deal, its status will be set to DealStatus.NEW_LABEL. If this is
	 * a deal to be closed, its status will be set to DealStatus.CLOSED_LABEL.
	 * If the deal has no outstanding settlement amount, its status will be set
	 * to DealStatus.SETTLED_LABEL. If the deal has partial settlement, its
	 * status will be set to DealStatus.PARTIALLY_SETTLED_LABEL. If the deal
	 * amount has been increased, its status will be set to
	 * DealStatus.ENHANCED_LABEL. If the deal amount has been reduced, its
	 * status will be set to DealStatus.REDUCED_LABEL.
	 * 
	 * @param value is of type ICommodityDealTrxValue
	 */
	protected String determineDealStatus(ICommodityDealTrxValue value) {

		// there is no need to reverify the rules to close the deal since it has
		// already been verify and approved
		// before it can get to this stage
		if (getOperationName().equals(ICMSConstant.ACTION_MAKER_CLOSE_DEAL)) {
			return DealStatus.CLOSED_LABEL;
		}

		ICommodityDeal staging = value.getStagingCommodityDeal();
		if (getOperationName().equals(ICMSConstant.ACTION_MAKER_CREATE_DEAL)) { // new
																				// deal
																				// operation

			// Settled and Partially Settled are meant for 'New' & 'Update'
			// Operations.
			if ((staging.getBalanceDealAmt() != null) && (staging.getBalanceDealAmt().getAmount() <= 0)) {
				return DealStatus.SETTLED_LABEL;
			}

			if ((staging.getTotalSettlementAmt() != null) && (staging.getTotalSettlementAmt().getAmount() > 0)) {
				return DealStatus.PARTIALLY_SETTLED_LABEL;
			}

			return DealStatus.NEW_LABEL;
		}

		if (getOperationName().equals(ICMSConstant.ACTION_MAKER_UPDATE_DEAL)) { // update
																				// operation

			ICommodityDeal actual = value.getCommodityDeal();

			if ((staging.getBalanceDealAmt() != null) && (staging.getBalanceDealAmt().getAmount() <= 0)) {
				if ((actual.getTotalSettlementAmt() == null) || // catering for
																// "new"
																// scenarios
						((actual.getTotalSettlementAmt() != null) && // catering
																		// for
																		// originally
																		// in
																		// "settled"
																		// scenarios
						(staging.getTotalSettlementAmt().getAmount() != actual.getTotalSettlementAmt().getAmount()))) {
					return DealStatus.SETTLED_LABEL;
				}
			}

			if ((staging.getTotalSettlementAmt() != null) && (staging.getTotalSettlementAmt().getAmount() > 0)) {
				if ((actual.getTotalSettlementAmt() == null)
						|| (staging.getTotalSettlementAmt().getAmount() != actual.getTotalSettlementAmt().getAmount())) {
					return DealStatus.PARTIALLY_SETTLED_LABEL;
				}
			}

			if ((actual != null) && (staging.getDealAmt() != null) && (actual.getDealAmt() != null)) {

				if (staging.getDealAmt().getAmount() > actual.getDealAmt().getAmount()) { // deal
																							// amount
																							// increases
					return DealStatus.ENHANCED_LABEL;
				}
				else if (staging.getDealAmt().getAmount() < actual.getDealAmt().getAmount()) { // deal
																								// amount
																								// decreases
					return DealStatus.REDUCED_LABEL;
				}
			}
		}

		// ignoring all other operations such as 'Cancel','Save'
		return staging.getStatus();
	}

	/**
	 * To compare the string (Ref No.) If the actual and staging value is same
	 * then it returns true else returns false
	 * 
	 * @param value is of type String
	 */
	protected boolean RefCompare(String actual, String staging) {

		if ((actual == null) && (staging == null)) {
			return true;
		}
		// if either one is null then return false
		if ((actual == null) || (staging == null)) {
			return false;
		}
		if (actual.trim().equals(staging.trim())) {
			return true;
		}
		return false;

	}

	/**
	 * To compare the dates (Maturity Date) If the actual and staging value is
	 * same then it returns true else returns false
	 * 
	 * @param value is of type Date
	 */
	protected boolean MatDtCompare(Date actual, Date staging) {

		if ((actual == null) && (staging == null)) {
			return true;
		}
		// if either one is null then return false
		if ((actual == null) || (staging == null)) {
			return false;
		}
		if (actual.compareTo(staging) == 0) {
			return true;
		}
		return false;

	}

	/**
	 * To compare the Amount (Cash amount) If the actual and staging value is
	 * same then it returns true else returns false
	 * 
	 * @param value is of type Amount
	 */
	protected boolean AmountCompare(Amount actual, Amount staging) {

		if ((actual == null) && (staging == null)) {
			return true;
		}
		// if either one is null then return false
		if ((actual == null) || (staging == null)) {
			return false;
		}
		if (actual.compareTo(staging) == 0) {
			return true;
		}
		return false;

	}
}