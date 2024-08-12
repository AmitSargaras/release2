/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/AbstractCollateralBusManager.java,v 1.28 2006/04/05 08:03:43 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This abstract class provides helper services to subclasses that extends from
 * it. It provides implementation of ICollateralManager services such that the
 * implementation is non-mechanism specific.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.28 $
 * @since $Date: 2006/04/05 08:03:43 $ Tag: $Name: $
 */
public abstract class AbstractCollateralBusManager implements ICollateralBusManager {

	private static final long serialVersionUID = -6451594148582013420L;

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CollateralException on errors encountered
	 */
	protected abstract void rollback() throws CollateralException;

	/**
	 * Get all collateral types avaiable in CMS based on the criteria.
	 * 
	 * @return a list of collateral types
	 * @throws CollateralException on error getting the collateral types
	 */
	public ICollateralType[] getAllCollateralTypes() throws CollateralException {
		ICollateralDAO dao = CollateralDAOFactory.getDAO();
		return dao.getAllCollateralTypes();

	}

	/**
	 * Create a new collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral createCollateral(ICollateral collateral) throws CollateralException {
		if (collateral == null) {
			throw new CollateralException("ICollateral is null!");
		}

		try {
			CollateralValuator valuator = new CollateralValuator();
			valuator.setCollateralCMVFSV(collateral);
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "failed to calculate valuation for the collateral, ref id ["
					+ collateral.getSCISecurityID() + "] " + e);
		}

		boolean oldPerfection = collateral.getIsPerfected();
		ICollateralPerfector collateralPerfector = CollateralPerfectorFactory.getCollateralPerfector(collateral);
		boolean isPerfected = collateralPerfector.isCollateralPerfected(collateral);
		collateral.setIsPerfected(isPerfected);
		if ((oldPerfection == false) && (isPerfected == true)) {
			collateral.setPerfectionDate(new Date(System.currentTimeMillis()));
		}

		return collateral;
	}

	/**
	 * Update a collateral.
	 * 
	 * @param collateral is of type ICollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public ICollateral updateCollateral(ICollateral collateral) throws CollateralException {
		if (collateral == null) {
			throw new CollateralException("ICollateral is null!");
		}

		return collateral;
	}

	protected ICollateral setDerivedFields(ICollateral col) {
		IValuation val = col.getValuation();
		IValuation[] history = col.getValuationHistory();

		GregorianCalendar cal = new GregorianCalendar();

		if (val != null) {
			String timeFreq = val.getRevaluationFreqUnit();
			int timeFreqNum = val.getRevaluationFreq();

			if ((col instanceof IDocumentCollateral) && (val.getNonRevaluationFreqUnit() != null)
					&& (val.getNonRevaluationFreqUnit().length() > 0)
					&& (val.getNonRevaluationFreq() != ICMSConstant.INT_INVALID_VALUE)) {

				timeFreq = val.getNonRevaluationFreqUnit();
				timeFreqNum = val.getNonRevaluationFreq();
			}

			if (val.getValuationDate() != null) {
				cal.setTime(val.getValuationDate());
				val.setFSVEvaluationDate(cal.getTime());

				if (timeFreq != null) {
					if (timeFreq.equals(ICMSConstant.TIME_FREQ_YEAR)) {
						cal.add(Calendar.YEAR, timeFreqNum);
						val.setRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_MONTH)) {
						cal.add(Calendar.MONTH, timeFreqNum);
						val.setRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_WEEK)) {
						cal.add(Calendar.WEEK_OF_MONTH, timeFreqNum);
						val.setRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_DAY)) {
						cal.add(Calendar.DAY_OF_MONTH, timeFreqNum);
						val.setRevaluationDate(cal.getTime());
					}
				}
			}

			if ((history != null) && (history.length != 0)) {
				val.setLastEvaluationDate(history[history.length - 1].getValuationDate());
			}
			else {
				val.setLastEvaluationDate(val.getValuationDate());
			}

			if (val.getValuationDate() != null) {
				cal.setTime(val.getValuationDate());

				if (timeFreq != null) {
					if (timeFreq.equals(ICMSConstant.TIME_FREQ_YEAR)) {
						cal.add(Calendar.YEAR, timeFreqNum);
						val.setNextRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_MONTH)) {
						cal.add(Calendar.MONTH, timeFreqNum);
						val.setNextRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_WEEK)) {
						cal.add(Calendar.WEEK_OF_MONTH, timeFreqNum);
						val.setNextRevaluationDate(cal.getTime());
					}
					else if (timeFreq.equals(ICMSConstant.TIME_FREQ_DAY)) {
						cal.add(Calendar.DAY_OF_MONTH, timeFreqNum);
						val.setNextRevaluationDate(cal.getTime());
					}
				}
			}

			if (val.getBeforeMarginValue() != null) {
				val.setAfterMarginValue(col.getFSV());
			}

			if (val.getBeforeMarginValue() != null) {
				val.getBeforeMarginValue().setCurrencyCode(col.getCurrencyCode());
			}
			if (val.getAfterMarginValue() != null) {
				val.getAfterMarginValue().setCurrencyCode(col.getCurrencyCode());
			}
			if (val.getCMV() != null) {
				val.getCMV().setCurrencyCode(col.getCurrencyCode());
			}
			if (val.getFSV() != null) {
				val.getFSV().setCurrencyCode(col.getCurrencyCode());
			}
		}

		return col;
	}
}