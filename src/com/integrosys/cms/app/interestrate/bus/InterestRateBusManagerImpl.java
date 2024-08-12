/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;
import java.util.Date;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This class facades the IInterestRateManager implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateBusManagerImpl implements IInterestRateBusManager {
	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#getInterestRate
	 */
	public IInterestRate[] getInterestRate(String intRateType, Date monthYear) throws InterestRateException {
		SBInterestRateBusManager theEjb = getBusManager();

		try {
			return theEjb.getInterestRate(intRateType, monthYear);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#getInterestRateByGroupID
	 */
	public IInterestRate[] getInterestRateByGroupID(long groupID) throws InterestRateException {
		SBInterestRateBusManager theEjb = getBusManager();

		try {
			return theEjb.getInterestRateByGroupID(groupID);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#createInterestRates
	 */
	public IInterestRate[] createInterestRates(IInterestRate[] assetLifes) throws InterestRateException {
		SBInterestRateBusManager theEjb = getBusManager();

		try {
			return theEjb.createInterestRates(assetLifes);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#updateInterestRates
	 */
	public IInterestRate[] updateInterestRates(IInterestRate[] assetLifes) throws InterestRateException {
		SBInterestRateBusManager theEjb = getBusManager();

		try {
			return theEjb.updateInterestRates(assetLifes);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * helper method to get an ejb object to interest rate business manager
	 * session bean.
	 * 
	 * @return interest rate manager ejb object
	 * @throws InterestRateException on errors encountered
	 */
	protected SBInterestRateBusManager getBusManager() throws InterestRateException {
		SBInterestRateBusManager theEjb = (SBInterestRateBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_INT_RATE_MGR_JNDI, SBInterestRateBusManagerHome.class.getName());

		if (theEjb == null) {
			throw new InterestRateException("SBInterestRateManager for Actual is null!");
		}

		return theEjb;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws InterestRateException on errors encountered
	 */
	protected void rollback() throws InterestRateException {
	}
}