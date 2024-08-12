/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.transaction.UserTransaction;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.limit.bus.ICollateralAllocation;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.batch.collateralthreshold.CollateralRankingComparator;
import com.integrosys.cms.batch.collateralthreshold.CollateralThresholdDAO;

/**
 * Session bean implementation of the services provided by the Event Montitor
 * Controller.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/10/27 02:52:14 $ Tag: $Name: $
 */
public class SBEventMonitorFirstPassBean extends AbstractEventMonitorController implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext context = null;

	private String baseCurrency = CommonUtil.getBaseCurrency();

	private float variance = 1; // default is 1. anything less than 1 is ignored

	/**
	 * Default constructor.
	 */
	public SBEventMonitorFirstPassBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		context = sc;

	}

	public void startCollateralThresholdJob(Long[] collateralIDList) throws RemoteException {

		String strCommitloops = PropertyManager.getValue(ICMSConstant.BATCH_TRX_COMMIT_LOOPS_PROP);
		String strTrxTimeout = PropertyManager.getValue(ICMSConstant.BATCH_TRX_TIMEOUT_SEC_PROP);
		int trxTimeout = 0;
		CollateralThresholdDAO dao = new CollateralThresholdDAO();
		UserTransaction ut = context.getUserTransaction();
		int commitRecordsCount = 0;
		try {
			commitRecordsCount = Integer.parseInt(strCommitloops);
		}
		catch (Exception e) {
			commitRecordsCount = 10;
			DefaultLogger.error(this, "monitoring job commit loops not set, defaulting to 10 ");
		}
		try {
			trxTimeout = Integer.parseInt(strTrxTimeout);
		}
		catch (Exception e) {
			trxTimeout = 1000;
			DefaultLogger.error(this, "monitoring job Transaction Timeout not set, defaulting to 1000 seconds ");
		}
		try {

			ut.setTransactionTimeout(trxTimeout);
			ut.begin();
			int commitCount = 0;

			ICollateralProxy colProxy = CollateralProxyFactory.getProxy();

			// first pass: persist collateral allocation

			long startFirstTime = System.currentTimeMillis();
			for (int i = 0; i < collateralIDList.length; i++) {
				ICollateral col = null;
				try {

					// col =
					// colProxy.getCollateral((collateralIDList[i]).longValue(),
					// true); //direct call to dao and retrieval of data
					col = CollateralDAOFactory.getDAO().getCollateralLimitChanges(collateralIDList[i].longValue());
					if (col != null) {
						performChargeAllocation(col, dao); // this will persist
															// collateral
															// allocation and
															// security balance
					}
					if (commitCount++ == commitRecordsCount) {
						commitCount = 0;
						ut.commit();
						ut = context.getUserTransaction();
						ut.setTransactionTimeout(trxTimeout);
						ut.begin();
						DefaultLogger.debug(this, String.valueOf(commitCount));
					}

				}
				catch (Exception e) {
					DefaultLogger.info(this, "Collateral id failed:" + collateralIDList[i]);
					DefaultLogger.error(this, "", e);
					throw e;
				}
			}

			ut.commit();

		}
		catch (Exception ee) {
			DefaultLogger.error(this, "Event monitor SBBatchMonitorControllerBean encountered general errors", ee);
			try {
				ut.setRollbackOnly();
			}
			catch (Exception eee) {
				DefaultLogger.error(this, "Event monitor SBBatchMonitorControllerBean encountered general errors", eee);
			}

			throw new RemoteException(ee.getMessage());
		}

	}

	/**
	 * Method to allocation collateral FSV to limit charge
	 */
	private void performChargeAllocation(ICollateral col, CollateralThresholdDAO dao) throws Exception {
		DefaultLogger.debug(this, "Performing Charge Allocation...");
		Amount colAmt = col.getFSV();
		if (isNullAmount(colAmt)) {
			DefaultLogger.debug(this, "Collateral FSV is null. Returning.");
			return; // nothing to do since collateral amount is 0
		}
		else {
			// convert to base currency
			colAmt = convert(colAmt);
		}
		Amount originalAmt = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());

		ILimitCharge[] chargeList = col.getLimitCharges();
		Arrays.sort(chargeList, new CollateralRankingComparator()); // sort
																	// according
																	// to rank
																	// first
		HashMap chargeMap = new HashMap(); // to store the limit charge ID, and
											// the actual charge amount
		HashMap colMap = new HashMap();
		long collateralID = col.getCollateralID();
		Amount surplus = new Amount(0, baseCurrency);
		// first pass, reset all collateral charges amounts
		resetChargeAllocation(chargeList, dao);

		// next prepare for allocation
		for (int i = 0; i < chargeList.length; i++) {
			ILimitCharge charge = chargeList[i];
			Amount chargeAmt = charge.getChargeAmount();
			if (!(isNullAmount(chargeAmt))) {
				// convert to base currency

				chargeAmt = convert(chargeAmt);
			}
			else {
				DefaultLogger.debug(this,"ChargeAmount is null");
				continue;
			}
			if (charge.getSecurityRank() == 1) { // special flow for first rank
				Amount balance = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());
				/*
				 * if(collateralID == 100100776) { System.out.println();
				 * System.out
				 * .println("XXXXXXXXXXXXX balance before surplus at rank 1: " +
				 * balance); System.out.println("XXXXXXXXXXXXX charge amt: " +
				 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: " +
				 * surplus); }
				 */
				if (balance.getAmount() < chargeAmt.getAmount()) {
					balance = balance.add(surplus);
					colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
																							// becomes
																							// balance
					surplus = new Amount(0, baseCurrency); // allocate all
															// surplus to
															// balance
				}
				balance = balance.subtract(chargeAmt);

				if (computeLessThan(balance.getAmount(), 0)) {
					surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
					chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
							.getCurrencyCode()));
					colAmt = new Amount(0, balance.getCurrencyCode()); // reset
																		// to 0
					break; // no need to continue since already go below 0
				}
				else {
					colAmt = balance; // to reflect the new balance
					surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
					chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(), chargeAmt
							.getCurrencyCode()));
				}
				/*
				 * if(collateralID == 100100776) { System.out.println();
				 * System.out
				 * .println("XXXXXXXXXXXXX balance after surplus at rank 1: " +
				 * balance); System.out.println("XXXXXXXXXXXXX charge amt: " +
				 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: " +
				 * surplus); }
				 */
			}
			else {
				Amount priorAmt = charge.getPriorChargeAmount();
				if (!(isNullAmount(priorAmt))) { // not null,need to subtract
													// prior charge from
													// original
					priorAmt = convert(priorAmt);
					Amount balance = new Amount(originalAmt.getAmount(), originalAmt.getCurrencyCode());
					balance = balance.subtract(priorAmt);
					/*
					 * if(collateralID == 100100776) { System.out.println();
					 * System.out.println(
					 * "XXXXXXXXXXXXX balance before surplus at rank x: " +
					 * balance);
					 * System.out.println("XXXXXXXXXXXXX prior charge: " +
					 * priorAmt);
					 * System.out.println("XXXXXXXXXXXXX charge amt: " +
					 * chargeAmt); System.out.println("XXXXXXXXXXXXX surplus: "
					 * + surplus); }
					 */
					if (computeLessThan(balance.getAmount(), 0)) {
						colAmt = new Amount(0, balance.getCurrencyCode()); // implies
																			// zero
																			// balance
						// prior charge already took up all, can't assign
						// anymore to current charge
						break;
					}
					else {
						/*
						 * if(collateralID == 100100776) { System.out.println();
						 * System
						 * .out.println("XXXXXXXXXXXXX balance after prior charge: "
						 * + balance);
						 * System.out.println("XXXXXXXXXXXXX charge amt is : " +
						 * chargeAmt);
						 * System.out.println("XXXXXXXXXXXXX surplus : " +
						 * surplus); }
						 */
						colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
																								// becomes
																								// balance
						if (balance.getAmount() < chargeAmt.getAmount()) {
							balance = balance.add(surplus);
							colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // to
																									// include
																									// surplus
																									// into
																									// col
																									// Amt
							/*
							 * if(collateralID == 100100776) {
							 * System.out.println();System.out.println(
							 * "XXXXXXXXXXXXX balance after surplus : " +
							 * balance); }
							 */
							surplus = new Amount(0, baseCurrency);
						}
						balance = balance.subtract(chargeAmt); // next using
																// balance,
																// subtract
																// charge amt
						if (computeLessThan(balance.getAmount(), 0)) {
							surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
							chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
									.getCurrencyCode())); // assign whatever is
															// available
							colAmt = new Amount(0, balance.getCurrencyCode()); // reset
																				// to
																				// 0
							break; // no need to continue since already go below
									// 0
						}
						else {
							colAmt = balance; // to reflect the new balance
							surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
							chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(),
									colAmt.getCurrencyCode()));
						}
					}
				}
				else {
					// since prior charge is null, use current colAmt as the
					// balance
					DefaultLogger.info(this, "Prior Amount for Charge DetailID: " + charge.getChargeDetailID()
							+ " of collateralID: " + col.getCollateralID() + " is null!");
					Amount balance = new Amount(colAmt.getAmount(), colAmt.getCurrencyCode());
					if (balance.getAmount() < chargeAmt.getAmount()) {
						balance = balance.add(surplus);
						colAmt = new Amount(balance.getAmount(), balance.getCurrencyCode()); // current
																								// becomes
																								// balance
						surplus = new Amount(0, baseCurrency);
					}
					balance = balance.subtract(chargeAmt);
					if (computeLessThan(balance.getAmount(), 0)) {
						surplus = computeSurplus(surplus, charge, colAmt, colMap, collateralID, dao);
						chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(colAmt.getAmount(), colAmt
								.getCurrencyCode()));
						colAmt = new Amount(0, balance.getCurrencyCode()); // reset
																			// to
																			// 0
						break; // no need to continue since already go below 0
					}
					else {
						colAmt = balance; // to reflect the new balance
						surplus = computeSurplus(surplus, charge, chargeAmt, colMap, collateralID, dao);
						chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(chargeAmt.getAmount(), colAmt
								.getCurrencyCode()));
					}
				}
			}
		}
		// then persist all the charge allocation via hashmap and dao
		persistChargeAllocation(chargeMap, false, dao);

		// persist the collateral amount as FSV balance. This should be done
		// last as the charge allocation persistence
		// is not using the same connection as collateral persistence.
		// collateral persistence value is a displayable
		// field in UI. as such we don't want to update it unless we're sure the
		// charge allocation persistence is
		// successful
		persistCollateralBalance(col, colAmt, dao);
	}

	/**
	 * Method to compute charge allocation surplus
	 */
	private Amount computeSurplus(Amount surplus, ILimitCharge charge, Amount actual, HashMap colMap,
			long collateralID, CollateralThresholdDAO dao) throws Exception {
		if (ICMSConstant.CHARGE_TYPE_SPECIFIC.equals(charge.getChargeType())) {
			return surplus;
		}

		long chargeDetailID = charge.getChargeDetailID();
		// Amount chargeAmount = charge.getChargeAmount();
		Amount chargeAmount = new Amount(0, baseCurrency);

		ILimitProxy proxy = LimitProxyFactory.getProxy();
		HashMap profileMap = new HashMap();

		HashMap idMap = dao.getLimitAndProfileByCharge(chargeDetailID);
		Iterator it = idMap.keySet().iterator();
		while (it.hasNext()) {
			Long limitID = (Long) it.next();
			Long profileID = (Long) idMap.get(limitID);

			ILimitProfile profile = (ILimitProfile) profileMap.get(profileID);
			if (null == profile) {
				profile = proxy.getLimitProfileLimitsOnly(profileID.longValue());
				profileMap.put(profileID, profile);
			}
			ILimit[] limitList = profile.getLimits();
			ILimit limit = null;
			for (int i = 0; i < limitList.length; i++) {
				if (limitID.longValue() == limitList[i].getLimitID()) {
					limit = limitList[i];
					break;
				}
			}
			chargeAmount = chargeAmount.add(computeTotalActivatedLimits(limit, limitList, true));
		}

		// chargeAmount = convert(chargeAmount);
		surplus = convert(surplus);
		actual = convert(actual);

		colMap = dao.getMapByCollateral(colMap, collateralID, chargeDetailID);
		Double coverage = (Double) colMap.get(new Long(chargeDetailID));

		/*
		 * if(collateralID == 100100776) { System.out.println();
		 * System.out.println("XXXXXXXXXXXXX charge amt in compute: " +
		 * chargeAmount);
		 * System.out.println("XXXXXXXXXXXXX surplus in compute: " + surplus);
		 * System.out.println("XXXXXXXXXXXXX  actual in compute: " + actual);
		 * System.out.println("XXXXXXXXXXXXX  coverage: " + coverage); }
		 */

		if ((null == coverage) || (0 == coverage.doubleValue())) {
			coverage = new Double(100); // assume 100% for coverage that is null
										// or 0
		}

		double requiredDouble = (coverage.doubleValue() / 100) * chargeAmount.getAmount();
		double actualDouble = actual.getAmount();

		/*
		 * if(collateralID == 100100776) { System.out.println();
		 * System.out.println("XXXXXXXXXXXXX requiredDouble: " +
		 * requiredDouble); System.out.println("XXXXXXXXXXXXX actualDouble: " +
		 * actualDouble); }
		 */
		if (actualDouble > requiredDouble) {
			surplus = surplus.add(new Amount((actualDouble - requiredDouble), baseCurrency));
		}
		/*
		 * if(collateralID == 100100776) {
		 * System.out.println("XXXXXXXXXXXXX new surplus: " + surplus); }
		 */
		return surplus;
	}

	/**
	 * Method to persist charge Allocation
	 */
	private void persistChargeAllocation(HashMap chargeMap, boolean reset, CollateralThresholdDAO dao) throws Exception {
		DefaultLogger.debug(this, "Persisting Charge Allocation with " + chargeMap.size() + " number of records.");

		dao.updateActualChargeAmount(chargeMap, reset);
	}

	/**
	 * Optimized Method to persist the collateral balance value
	 */
	private void persistCollateralBalance(ICollateral col, Amount colAmt, CollateralThresholdDAO dao) throws Exception {
		if (colAmt.getAmount() < 0) {
			colAmt.setAmount(0);
		}
		dao.updateSTGFSVAmount(col, colAmt);
		dao.updateFSVAmount(col, colAmt);
	}

	/**
	 * Method to identify if an amount object is null
	 * 
	 * @return boolean
	 */
	private boolean isNullAmount(Amount amt) {
		if (null == amt) {
			return true;
		}
		else {
			if ((amt.getCurrencyCode() == null) && (amt.getAmount() == 0)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	/**
	 * Private method to help to convert amount objects
	 */
	private Amount convert(Amount amt) throws Exception {
		if (amt.getCurrencyCode().equals(baseCurrency)) {
			return amt;
		}
		else {
			SBForexManager fx = getSBForexManager();
			Amount result = fx.convert(amt, new CurrencyCode(baseCurrency));
			if (null == result) {
				throw new Exception("Forex conversion returns null, for FROM Currency: " + amt.getCurrencyCode()
						+ " and TO Currency: " + baseCurrency);
			}
			else {
				return result;
			}
		}
	}

	/**
	 * Method to reset all charge allocation data
	 */
	private void resetChargeAllocation(ILimitCharge[] chargeList, CollateralThresholdDAO dao) throws Exception {
		HashMap chargeMap = new HashMap();
		for (int i = 0; i < chargeList.length; i++) {
			ILimitCharge charge = chargeList[i];
			chargeMap.put(new Long(charge.getChargeDetailID()), new Amount(0, baseCurrency)); // reset
																								// to
																								// zero
		}
		persistChargeAllocation(chargeMap, true, dao);
	}

	/**
	 * Method to compute less than, given a variance for possible element of
	 * errors due to exchange conversion
	 */
	private boolean computeLessThan(double subtractFrom, double subtractUsing) {
		if (subtractFrom >= subtractUsing) {
			return false;
		}
		else {
			double diff = (subtractFrom - subtractUsing) * -1; // to make
																// positive
			if (diff <= variance) { // not considered less than since difference
									// is less than 1
				return false;
			}
			else {
				return true;
			}
		}
	}

	/*
	 * Method to compute total activated limits
	 */
	private Amount computeTotalActivatedLimits(ILimit[] limitList) throws Exception {
		Amount total = new Amount(0, baseCurrency);

		for (int i = 0; i < limitList.length; i++) {
			ILimit limit = limitList[i];
			total = total.add(computeTotalActivatedLimits(limit, limitList, true));
		}
		return total;
	}

	/**
	 * Method to compute total activated limits for 1 limit. exclude inner
	 * should be true if computing for total activated limit due to a limit,
	 * because the method will take care of inner limits. however the flag
	 * should be false when computing limit threshold, as it's possible for
	 * inner limits to be linked to collateral.
	 */
	private Amount computeTotalActivatedLimits(ILimit limit, ILimit[] limitList, boolean excludeInner) throws Exception {
		if (excludeInner) {
			if ((limit.getOuterLimitID() != ICMSConstant.LONG_MIN_VALUE) && (limit.getOuterLimitID() != 0)) {
				return new Amount(0, baseCurrency); // i.e. exclude this since
													// it's an inner limit
			}
		}

		Amount outActAmt = limit.getActivatedLimitAmount(); // outer limit
															// activated limit
		if (isSecuredByCommodity(limit)) {
			outActAmt = limit.getOperationalLimit();
		}
		Amount outApprAmt = convert(limit.getApprovedLimitAmount()); // outer
																		// limit
																		// approved
																		// amount

		ArrayList aList = new ArrayList();
		long limitID = limit.getLimitID();
		for (int i = 0; i < limitList.length; i++) {
			ILimit temp = limitList[i];
			if (temp.getOuterLimitID() == limitID) {
				aList.add(temp); // add inner limits
			}
		}
		Amount innerTotal = new Amount(0, baseCurrency);
		if (aList.size() > 0) {
			// compute total inner limits
			Iterator it = aList.iterator();
			while (it.hasNext()) {
				ILimit inner = (ILimit) it.next();
				Amount innerAmt = inner.getActivatedLimitAmount();
				if (isSecuredByCommodity(inner)) {
					innerAmt = inner.getOperationalLimit();
				}
				if (isNullAmount(innerAmt)) {
					continue; // ignore it
				}
				else {
					innerAmt = convert(innerAmt);
					innerTotal = innerTotal.add(innerAmt);
				}
			}
		}

		Amount actualAmt = null;

		if (isNullAmount(outActAmt)) { // limit has not been activated
			if (isFirstAmtHigherEqual(outApprAmt, innerTotal)) {
				actualAmt = innerTotal;
			}
			else {
				actualAmt = outApprAmt;
			}
		}
		else { // limit is activated
			outActAmt = convert(outActAmt);
			if (isFirstAmtHigherEqual(outActAmt, innerTotal)) {
				actualAmt = outActAmt;
			}
			else {
				if (isFirstAmtHigherEqual(innerTotal, outApprAmt)) {
					actualAmt = outApprAmt;
				}
				else {
					actualAmt = innerTotal;
				}
			}
		}
		actualAmt = new Amount(actualAmt.getAmountAsBigDecimal(), new CurrencyCode(actualAmt.getCurrencyCode()));
		return actualAmt;
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager - the remote handler for the forex manager session
	 *         bean
	 * @throws Exception for any errors encountered
	 */
	private SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}

	/**
	 * Helper method to check if amount 1 is greater or equals to amount 2.
	 * 
	 * @param amt1 of type Amount
	 * @param amt2 of type Amount
	 * @return true if amt1 is greater or equals to amt2, otherwise false
	 */
	private boolean isFirstAmtHigherEqual(Amount amt1, Amount amt2) {
		if (amt1.getAmountAsBigDecimal().compareTo(amt2.getAmountAsBigDecimal()) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Helper method to check if a limit is fully or partially secured by
	 * commodities.
	 * 
	 * @param limit of type ILimit
	 * @return true if the limit is secured fully or partially by commodities,
	 *         otherwise false
	 */
	private boolean isSecuredByCommodity(ILimit limit) {
		ICollateralAllocation[] alloc = limit.getCollateralAllocations();
		if (alloc != null) {
			for (int i = 0; i < alloc.length; i++) {
				ICollateral col = alloc[i].getCollateral();
				if (col instanceof ICommodityCollateral) {
					return true;
				}
			}
		}
		return false;
	}

}
