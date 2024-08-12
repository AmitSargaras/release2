/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.proxy;

import java.util.Date;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This class facades the IInterestRateProxy implementation by delegating the
 * handling of requests to an ejb session bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateProxyImpl extends AbstractInterestRateProxy {

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#getInterestRateTrxValue
	 */
	public IInterestRateTrxValue getInterestRateTrxValue(ITrxContext ctx, String intRateType, Date monthYear)
			throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.getInterestRateTrxValue(ctx, intRateType, monthYear);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at getInterestRateTrxValue: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#getInterestRateTrxValueByTrxID
	 */
	public IInterestRateTrxValue getInterestRateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.getInterestRateTrxValueByTrxID(ctx, trxID);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at getInterestRateTrxValueByTrxID: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerUpdateInterestRate
	 */
	public IInterestRateTrxValue makerUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] assetLifes) throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.makerUpdateInterestRate(ctx, trxVal, assetLifes);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at makerUpdateInterestRate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerSaveInterestRate
	 */
	public IInterestRateTrxValue makerSaveInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] assetLifes) throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.makerSaveInterestRate(ctx, trxVal, assetLifes);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at makerSaveInterestRate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerCancelUpdateInterestRate
	 */
	public IInterestRateTrxValue makerCancelUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.makerCancelUpdateInterestRate(ctx, trxVal);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at makerCancelUpdateInterestRate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#checkerApproveUpdateInterestRate
	 */
	public IInterestRateTrxValue checkerApproveUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.checkerApproveUpdateInterestRate(ctx, trxVal);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at checkerApproveUpdateInterestRate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#checkerRejectUpdateInterestRate
	 */
	public IInterestRateTrxValue checkerRejectUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		try {
			SBInterestRateProxy proxy = getProxy();
			return proxy.checkerRejectUpdateInterestRate(ctx, trxVal);
		}
		catch (InterestRateException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new InterestRateException("Exception caught at checkerRejectUpdateInterestRate: " + e.toString());
		}
	}

	/**
	 * Method to rollback a transaction. Not implemented at online proxy level.
	 * 
	 * @throws InterestRateException for any errors encountered
	 */
	protected void rollback() throws InterestRateException {
	}

	/**
	 * Helper method to get ejb object of interestrate proxy session bean.
	 * 
	 * @return interestrate proxy ejb object
	 */
	private SBInterestRateProxy getProxy() throws InterestRateException {
		SBInterestRateProxy proxy = (SBInterestRateProxy) BeanController.getEJB(
				ICMSJNDIConstant.SB_INT_RATE_PROXY_JNDI, SBInterestRateProxyHome.class.getName());

		if (proxy == null) {
			throw new InterestRateException("SBInterestRateProxy is null!");
		}
		return proxy;
	}
}